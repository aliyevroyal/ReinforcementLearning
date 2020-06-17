package com.thealiyev;

import java.util.ArrayList;
import java.util.Random;

public class QValueLearning {
    private Random random = null;

    private double alpha, omega;
    private int theNumberOfActions, theNumberOfStates;
    private ArrayList<ArrayList<Double>> RTable, QTable;
    private int initialState, finalState;

    /**
     * Iterations
     */
    public void iterate(int iterations) {
        random = new Random();
        for (int counter = 0; counter < iterations; counter = counter + 1) {
            System.out.println("Iteration number: " + counter);
            if (counter == 0) {
                createEmptyQTable();
            }
            initialState = random.nextInt(theNumberOfStates);
            episode();
        }
    }

    /**
     * An episode
     */
    public ArrayList<ArrayList<Double>> episode() {
        random = new Random();
        double QValue, MaxQValue, reward;
        ArrayList<Integer> possibleActions;
        int currentState, action;
        //Initialization starts here...
        currentState = initialState;
        //An episode start from here...
        while (currentState != finalState) {
            //Finds out all the possible actions for current state
            possibleActions = new ArrayList<>();
            for (int stCounter = 0; stCounter < RTable.get(currentState).size(); stCounter++) {
                if (RTable.get(currentState).get(stCounter) >= 0) {
                    possibleActions.add(stCounter);
                }
            }
            //Finds out an action for current state with the highest Q Value
            action = possibleActions.get(0);
            QValue = QTable.get(currentState).get(action);
            reward = RTable.get(currentState).get(action);
            for (int stCounter = 0; stCounter < possibleActions.size(); stCounter++) {
                if (QTable.get(currentState).get(possibleActions.get(stCounter)) > QValue) {
                    action = possibleActions.get(stCounter);
                    QValue = QTable.get(currentState).get(action);
                    reward = RTable.get(currentState).get(action);
                } else if (QTable.get(currentState).get(possibleActions.get(stCounter)) == QValue) {
                    if (random.nextBoolean()) {
                        action = possibleActions.get(stCounter);
                        QValue = QTable.get(currentState).get(action);
                        reward = RTable.get(currentState).get(action);
                    }
                }
            }
            //Finds out all the possible actions for next state
            possibleActions = new ArrayList<>();
            for (int stCounter = 0; stCounter < RTable.get(action).size(); stCounter++) {
                if (RTable.get(currentState).get(stCounter) >= 0) {
                    possibleActions.add(stCounter);
                }
            }
            //Finds Q max
            MaxQValue = QTable.get(action).get(possibleActions.get(0));
            for (int stCounter = 0; stCounter < possibleActions.size(); stCounter++) {
                if (QTable.get(currentState).get(possibleActions.get(stCounter)) > MaxQValue) {
                    MaxQValue = QTable.get(action).get(possibleActions.get(stCounter));
                } else if (QTable.get(currentState).get(possibleActions.get(stCounter)) == MaxQValue) {
                    if (random.nextBoolean()) {
                        MaxQValue = QTable.get(action).get(possibleActions.get(stCounter));
                    }
                }
            }
            //Calculate Bellman Equation
            QValue = QValue + alpha * (reward + omega * MaxQValue - QValue);
            //Update Q values table
            QTable.get(currentState).set(action, QValue);
            //Update state
            currentState = action;
        }
        return QTable;
    }

    /**
     * Creates empty Q values table each element equals to 0.0
     */
    public void createEmptyQTable() {
        QTable = new ArrayList<>();
        ArrayList<Double> vector = new ArrayList<>();

        for (int stCounter = 0; stCounter < theNumberOfStates; stCounter = stCounter + 1) {
            for (int ndCounter = 0; ndCounter < theNumberOfActions; ndCounter = ndCounter + 1) {
                vector.add(0.0);
            }
            QTable.add(vector);
            vector = new ArrayList<>();
        }
    }

    /**
     * Sets R, reward table
     * Sets # of states
     * Sets # of actions
     */
    public void setRTable(ArrayList<ArrayList<Double>> RTable) {
        this.RTable = RTable;
        this.theNumberOfStates = RTable.size();
        this.theNumberOfActions = RTable.get(0).size();
    }

    /**
     * Sets alpha, learning rate
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    /**
     * Sets omega, discount factor
     */
    public void setOmega(double omega) {
        this.omega = omega;
    }

    /**
     * Sets final state
     */
    public void setFinalState(int finalState) {
        this.finalState = finalState;
    }

    /**
     * Get Q values table after iterations
     */
    public ArrayList<ArrayList<Double>> getQTable() {
        return QTable;
    }
}