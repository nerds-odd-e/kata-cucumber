package com.odde.kata;

import java.util.Stack;

public class Rpn {

    public int calculate(String whole) {
        var stack = new Stack<String>();
        stack.addAll(java.util.Arrays.asList(whole.split(" ")));
        return calculate(stack);
    }

    private int calculate(Stack<String> stack) {
        var exp = stack.pop();
        switch (exp) {
            case "+" -> {
                var right = calculate(stack);
                return calculate(stack) + right;
            }
            case "-" -> {
                var right = calculate(stack);
                return calculate(stack) - right;
            }
            case "*" -> {
                var right = calculate(stack);
                return calculate(stack) * right;
            }
            case "/" -> {
                var right = calculate(stack);
                return calculate(stack) / right;
            }
            case "sqrt" -> {
                return (int) Math.sqrt(calculate(stack));
            }
            case "max" -> {
                return max(stack);
            }
            default -> {
                return Integer.parseInt(exp);
            }
        }
    }

    private int max(Stack<String> stack) {
        var right = calculate(stack);
        if (stack.isEmpty())
            return right;
        return Math.max(max(stack), right);
    }

}
