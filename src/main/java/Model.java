package main.java;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Double> numbers;
    private List<Integer> operations;

    public Model() {
        numbers = new ArrayList<>();
        operations = new ArrayList<>();
    }

    public void addNumber(double num) {
        numbers.add(num);
    }

    public void addOperation(int oper) {
        operations.add(oper);
    }

    private void changesInLists(int operationIndex, double result) {
        numbers.remove(operationIndex);
        numbers.remove(operationIndex);
        numbers.add(operationIndex, result);
        operations.remove(operationIndex);
    }

    public double getResult() {
        int multiplicationIndex = operations.indexOf(0);
        int divisionIndex = operations.indexOf(1);
        while (multiplicationIndex != -1  || divisionIndex != -1) {
            if ((multiplicationIndex < divisionIndex && multiplicationIndex > -1 && divisionIndex > -1) || divisionIndex == -1)
                changesInLists(multiplicationIndex, numbers.get(multiplicationIndex) * numbers.get(multiplicationIndex + 1));
            if ((divisionIndex < multiplicationIndex && divisionIndex > -1 && multiplicationIndex > -1) || multiplicationIndex == -1)
                changesInLists(divisionIndex, numbers.get(divisionIndex) / numbers.get(divisionIndex + 1));
            multiplicationIndex = operations.indexOf(0);
            divisionIndex = operations.indexOf(1);
        }
        int plusIndex = operations.indexOf(2);
        int minusIndex = operations.indexOf(3);
        while (plusIndex != -1 || minusIndex != -1) {
            if ((plusIndex < minusIndex && plusIndex > -1 && minusIndex > -1) || minusIndex == -1)
                changesInLists(plusIndex, numbers.get(plusIndex) + numbers.get(plusIndex + 1));
            if ((minusIndex < plusIndex && minusIndex > -1 && plusIndex > -1) || plusIndex == -1)
                changesInLists(minusIndex, numbers.get(minusIndex) - numbers.get(minusIndex + 1));
            plusIndex = operations.indexOf(2);
            minusIndex = operations.indexOf(3);
        }
        return numbers.get(0);
    }
}
