package main.java;

public class Controller {
    private String expression;
    private Model model;

    public Controller(String string) {
        expression = string;
        int i = findNumber(0);
        double result = Double.parseDouble(expression.substring(0, i));
        model = new Model();
        model.addNumber(result);
        while (expression.charAt(i) != '=') {
            char c = expression.charAt(i);
            int indexEndOfNum = findNumber(i + 1);
            double num;
            if (isOpenBracket(expression.charAt(i + 1)))
                num = Double.parseDouble(expression.substring(i + 2, indexEndOfNum - 1));
            else
                num = Double.parseDouble(expression.substring(i + 1, indexEndOfNum));
            model.addNumber(num);
            if (isPlus(c))
                model.addOperation(2);
            if (isMinus(c))
                model.addOperation(3);
            if (isDivision(c))
                model.addOperation(1);
            if (isMultiplication(c))
                model.addOperation(0);
            i = indexEndOfNum;
        }
    }

    private static boolean isPlus(char c) {
        return c == '+';
    }

    private boolean isMinus(char c) {
        return c == '-';
    }

    private boolean isDivision(char c) {
        return c == '÷';
    }

    private boolean isMultiplication(char c) {
        return c == '×';
    }

    private boolean isEquation(char c) {
        return c == '=';
    }

    private boolean isOpenBracket(char c) {
        return c == '(';
    }

    private int findNumber(int i) {
        if (isOpenBracket(expression.charAt(i)))
            ++i;
        if (isMinus(expression.charAt(i)))
            ++i;
        while (!isPlus(expression.charAt(i)) && !isMinus(expression.charAt(i)) && !isDivision(expression.charAt(i)) && !isMultiplication(expression.charAt(i)) && !isEquation(expression.charAt(i)))
            ++i;
        return i;
    }

    public String getResult() {
        return String.valueOf(model.getResult());
    }
}
