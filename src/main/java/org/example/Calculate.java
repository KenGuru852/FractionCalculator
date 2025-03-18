package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class Calculate {

    private static String lastOperation = "";
    private static FractionNumber lastOperand = new FractionNumber(0, 1);
    private static Stack<String> stack = new Stack<>();
    private static StringBuilder temp = new StringBuilder();
    private static Boolean isLast = false;

    public static FractionNumber calculate(String text) {
        // 6|1 SQR + 2|1 SQR / 10|1 + 6|1

        System.out.println(text);

        isLast = false;
        Boolean isAction = false;

        stack.clear();
        temp = new StringBuilder();

        // 6|1^+2|1^/10|1+6|1

        for (char symbol : text.toCharArray()) {
            if (Character.isDigit(symbol) || symbol == '|') {
                temp.append(symbol);
            } else {
                if (!temp.isEmpty()) stack.add(temp.toString());
                temp = new StringBuilder();
                if (isAction && (symbol == '+' || symbol == '/' || symbol == '*' || symbol == '-')) {
                    performOperation();
                    isAction = false;
                }
                if (symbol == '^') {
                    FractionNumber number = parseFraction(stack.pop()).square();
                    stack.add(number.toString());
                    System.out.println(number);
                    lastOperation = "^";
                } else if (symbol == '¿') {
                    FractionNumber number = parseFraction(stack.pop()).reverse();
                    stack.add(number.toString());
                    System.out.println(number);
                    lastOperation = "¿";
                } else if (symbol == '=') {
                    if (Objects.equals(stack.peek(), "=")) {
                        stack.pop();
                    }
                    if (stack.size() == 2) {
                        String operation = stack.pop();
                        FractionNumber number = parseFraction(stack.pop());
                        switch (operation) {
                            case "+" -> stack.add(number.multiple(new FractionNumber(2, 1)).toString());
                            case "-" -> stack.add(new FractionNumber(0, 1).toString());
                            case "*" -> stack.add(number.square().toString());
                            case "/" -> stack.add(new FractionNumber(1, 1).toString());
                        }
                    }
                    if (stack.size() > 2) {
                        performOperation();
                        isLast = true;
                    }
                    return parseFraction(stack.pop());
                } else if (!isAction) {
                    stack.add(String.valueOf(symbol));
                    isAction = true;
                }  // 2 + 2 + 2
            }
        }
        return null;
    }

    private static void performOperation() {
        System.out.println(stack.size());
        FractionNumber rightOperand = parseFraction(stack.pop());
        String operation = stack.pop();
        FractionNumber leftOperand = parseFraction(stack.pop());

        switch (operation) {
            case "+" -> stack.add(leftOperand.add(rightOperand).toString());
            case "-" -> stack.add(leftOperand.subtract(rightOperand).toString());
            case "*" -> stack.add(leftOperand.multiple(rightOperand).toString());
            case "/" -> stack.add(leftOperand.divide(rightOperand).toString());
        }
        lastOperation = operation;
        lastOperand = rightOperand;
    }

    private static FractionNumber parseFraction(String text) {
        String[] parts = text.split("\\|");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Некорректный формат дроби. Используйте формат 'числитель|знаменатель'.");
        }
        try {
            int numerator = Integer.parseInt(parts[0]);
            int denominator = Integer.parseInt(parts[1]);
            return new FractionNumber(numerator, denominator);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат числа.");
        }
    }
}