package com.thealiyev;

import java.util.ArrayList;
import java.util.Random;

public class QValueLearning {
    private Random random = null;

    private double alpha, omega;
    private int theNumberOfActions, theNumberOfStates;
    private ArrayList<ArrayList<Double>> RTable, QTable;
    private double QValue, MaxQValue, reward;
    ArrayList<Integer> possibleActions = new ArrayList<>();
    private int initialState, finalState, currentState, action;

    public void iterate() {
        random = new Random();
        //Initialization starts here
        currentState = initialState;
        //Iterations start here
        while (currentState != finalState) {
            System.out.println("Current State: " + currentState);
            //Find out all possible actions for current state
            possibleActions = new ArrayList<>();
            for (int stCounter = 0; stCounter < RTable.get(currentState).size(); stCounter++) {
                if (RTable.get(currentState).get(stCounter) >= 0) {
                    possibleActions.add(stCounter);
                }
            }
            System.out.println("All Possible Actions: " + possibleActions);

            //Find action for current state with the highest Q Value
            MaxQValue = QTable.get(currentState).get(possibleActions.get(0));
            action = possibleActions.get(0);
            reward = RTable.get(currentState).get(action);
            for (int stCounter = 0; stCounter < possibleActions.size(); stCounter++) {
                if (QTable.get(currentState).get(possibleActions.get(stCounter)) > MaxQValue) {
                    MaxQValue = QTable.get(currentState).get(possibleActions.get(stCounter));
                    action = possibleActions.get(stCounter);
                    reward = RTable.get(currentState).get(action);
                } else if (QTable.get(currentState).get(possibleActions.get(stCounter)) == MaxQValue) {
                    if (random.nextBoolean()) {
                        MaxQValue = QTable.get(currentState).get(possibleActions.get(stCounter));
                        action = possibleActions.get(stCounter);
                        reward = RTable.get(currentState).get(action);
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
        System.out.println("Current state: " + finalState);
    }

    /**
     * Creates empty Q values table each element equals to 0.0
     */
    private void createEmptyQTable() {
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
        createEmptyQTable();
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
     * Sets initial state
     */
    public void setInitialState(int initialState) {
        this.initialState = initialState;
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