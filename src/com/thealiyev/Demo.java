package com.thealiyev;

import java.util.ArrayList;

public class Demo {
    public static void main(String[] args) {
        /**
         * Example shows how to initialize Rewards table
         */
        ArrayList<ArrayList<Double>> QTable, RTable = new ArrayList<>();
        ArrayList<Double> vector = new ArrayList<>();
        for (int stCounter = 0; stCounter < 4; stCounter++) {
            for (int ndCounter = 0; ndCounter < 4; ndCounter++) {
                if (stCounter == ndCounter || ndCounter < stCounter) {
                    vector.add(-1.0);
                } else {
                    vector.add(0.0);
                }
            }
            RTable.add(vector);
            vector = new ArrayList<>();
        }
        RTable.get(0).set(1, 70.0);
        RTable.get(0).set(3, -1.0);
        RTable.get(1).set(3, -1.0);
        RTable.get(2).set(3, 100.0);
        System.out.println("Reward table:");
        for (int stCounter = 0; stCounter < RTable.size(); stCounter++) {
            for (int ndCounter = 0; ndCounter < RTable.get(stCounter).size(); ndCounter++) {
                System.out.print(RTable.get(stCounter).get(ndCounter) + " ");
            }
            System.out.println();
        }

        /**
         * Example shows how to Q-value learning library is being called and all essential arguments are being set
         */
        QValueLearning QValueLearning = new QValueLearning();
        QValueLearning.setFinalState(3);
        QValueLearning.setAlpha(0.7);
        QValueLearning.setGamma(0.8);
        QValueLearning.setRTable(RTable);
        QValueLearning.iterate(130);

        /**
         * Example shows how to get Q-value table at the end of iterations calling getQTable() method so you can integrate it in your application
         */
        System.out.println("Q-values table after iterations:");
        QTable = QValueLearning.getQTable();
        for (int stCounter = 0; stCounter < QTable.size(); stCounter++) {
            for (int ndCounter = 0; ndCounter < QTable.get(stCounter).size(); ndCounter++) {
                System.out.print(QTable.get(stCounter).get(ndCounter) + " ");
            }
            System.out.println();
        }
    }
}
