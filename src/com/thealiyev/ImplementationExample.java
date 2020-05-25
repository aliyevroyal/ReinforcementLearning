package com.thealiyev;

import java.util.ArrayList;

public class ImplementationExample {
    public static void main(String[] args) {
        //Rewards table initialization
        ArrayList<ArrayList<Double>> QTable, RTable = new ArrayList<>();
        ArrayList<Double> vector = new ArrayList<>();
        for (int stCounter = 0; stCounter < 4; stCounter++) {
            for (int ndCounter = 0; ndCounter < 4; ndCounter++) {
                if (stCounter == ndCounter) {
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
        RTable.get(3).set(0, -1.0);
        RTable.get(3).set(1, -1.0);
        RTable.get(2).set(3, 100.0);

        QValueLearning QValueLearning = new QValueLearning();
        QValueLearning.setInitialState(0);
        QValueLearning.setFinalState(3);
        QValueLearning.setAlpha(0.7);
        QValueLearning.setOmega(0.8);
        QValueLearning.setTheNumberOfActions(4);
        QValueLearning.setTheNumberOfStates(4);
        QValueLearning.setRTable(RTable);
        QValueLearning.iterate();

        System.out.println("\nQ-values table after iterations:");
        QTable = QValueLearning.getQTable();
        for (int stCounter = 0; stCounter < QTable.size(); stCounter++) {
            for (int ndCounter = 0; ndCounter < QTable.get(stCounter).size(); ndCounter++) {
                System.out.print(QTable.get(stCounter).get(ndCounter) + " ");
            }
            System.out.println();
        }
    }
}
