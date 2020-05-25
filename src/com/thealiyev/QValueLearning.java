package com.thealiyev;

import java.util.ArrayList;
import java.util.Random;

public class QValueLearning {
    private Random random = null;

    private double alpha, omega;
    private int theNumberOfActions, theNumberOfStates;
    private ArrayList<ArrayList<Double>> RTable, QTable;
    private double QValue, reward;
    private int initialState, finalState, currentState, action;

    public void iterate() {
        random = new Random();
        //Initialization starts here
        createEmptyQTable();
        currentState = initialState;
        QValue = QTable.get(currentState).get(currentState);
        //Iterations start here
        while (currentState != finalState) {
            System.out.println("Current State: " + currentState);
            //Find action for current state with the highest reward
            reward = RTable.get(currentState).get(0);
            action = 0;
            for (int stCounter = 0; stCounter < RTable.get(currentState).size(); stCounter++) {
                if (RTable.get(currentState).get(stCounter) > reward) {
                    reward = RTable.get(currentState).get(stCounter);
                    action = stCounter;
                } else if (RTable.get(currentState).get(stCounter) == reward) {
                    if (random.nextBoolean()) {
                        reward = RTable.get(currentState).get(stCounter);
                        action = stCounter;
                    }
                }
            }
            //Calculate Bellman Equation
            QValue = QValue + alpha * (reward + omega * QTable.get(currentState).get(action) - QValue);
            //Update Q values table
            QTable.get(currentState).set(action, QValue);
            //Update state
            currentState = action;
        }
        System.out.println("Current state: " + finalState);
    }

    //Creates empty Q values table each element equals to 0.0
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

    //Sets alpha, learning rate
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    //Sets omega, discount factor
    public void setOmega(double omega) {
        this.omega = omega;
    }

    //Sets # of actions
    public void setTheNumberOfActions(int theNumberOfActions) {
        this.theNumberOfActions = theNumberOfActions;
    }

    //Sets # of states
    public void setTheNumberOfStates(int theNumberOfStates) {
        this.theNumberOfStates = theNumberOfStates;
    }

    //Sets R, reward table
    public void setRTable(ArrayList<ArrayList<Double>> RTable) {
        this.RTable = RTable;
    }

    //Sets initial state
    public void setInitialState(int initialState) {
        this.initialState = initialState;
    }

    //Sets final state
    public void setFinalState(int finalState) {
        this.finalState = finalState;
    }

    //Get Q values table after iterations
    public ArrayList<ArrayList<Double>> getQTable() {
        return QTable;
    }
}