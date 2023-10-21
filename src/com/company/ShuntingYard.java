package com.company;

import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

public class ShuntingYard {
   public static String infixToPostFix(String expression) {
       Stack<String> stack = new Stack<>();
       Queue<String> queue = new LinkedList<>();
       if (! isValidExpression(expression)) {
           return "Please enter valid expression!";
       }
       while (!expression.isEmpty()) {
           String curr = getNext(expression);
           expression = expression.substring(1);
           if (isInteger(curr)) {
               queue.add(curr);
           }
           if (getValue(curr) != -1) {
               if (stack.isEmpty() || getValue(stack.peek()) < getValue(curr)) {
                   stack.add(curr);
               } else {
                   while (! stack.isEmpty() && getValue(stack.peek()) >= getValue(curr)) {
                       queue.add(stack.pop());
                   }
                   stack.push(curr);
               }
           }
           if (curr.equals("(")) {
               stack.push(curr);
           }
           if (curr.equals(")")) {
               while (!stack.peek().equals("(")) {
                   queue.add(stack.pop());
               }
               stack.pop();
           }
       }
       while (!stack.isEmpty()) {
           queue.add(stack.pop());
       }

       String output = "";
       while (!queue.isEmpty()) {
           output += queue.poll();
       }
       return output;
       }


    public static String postFixToInfix(String expression) {
       if (! isValidPostFix(expression)) {
           return "Invalid expression";
       }
       Stack<String> stack = new Stack<>();
       while (!expression.isEmpty()) {
           String curr = getNext(expression);
           if (getValue(curr) == -1) {
               stack.push(curr);
           } else {
               String s1 = stack.pop();
               String s2 = stack.pop();
               stack.push("(" + s2 + curr + s1 + ")");
           }
           expression = expression.substring(1);
       }
       String output = "";
       for (String x : stack) {
           output += x;
       }
       return output;
   }

    public static boolean isValidExpression(String expressionToCheck) {
        int numParenthesis = 0;
        if (expressionToCheck.isEmpty()) {
            return false;
        }
        int numOperands = 0;
        int numOperators = 0;
        for (int i = 0; i < expressionToCheck.length(); i++) {
            String c = expressionToCheck.charAt(i) + "";
            if (isOperand(c)) {
                numOperands++;
            }
            if (isOperator(c)) {
                numOperators++;
            }
        }
        if (numOperands - 1 != numOperators) {
            return false;
        }
        while (!expressionToCheck.isEmpty()) {
            String curr = getNext(expressionToCheck);
            expressionToCheck = expressionToCheck.substring(1);
            if (curr.equals("(")) {
                numParenthesis++;
            }
            if (curr.equals(")")) {
                numParenthesis--;
            }
            if ((! isInteger(curr) && getValue(curr) == -1) && (! curr.equals("(")) && (! curr.equals(")"))) {
                return false;
            }
            if (numParenthesis < 0) {
                return false;
            }
            if (!expressionToCheck.isEmpty()) {
                if ((getValue(curr) != -1) && getValue(expressionToCheck.substring(0,1)) != -1) {
                    return false;
                }
                if ((isInteger(curr)) && isInteger(expressionToCheck.substring(0,1))) {
                    return false;
                }
            }
        }
        return numParenthesis == 0;
    }

    public static double evaluatePostFix(String expression) {
       if (! isValidPostFix(expression)) {
           throw new IllegalArgumentException("Invalid expression");
       }
        Stack<String> stack = new Stack<>();
        while (!expression.isEmpty()) {
            String curr = getNext(expression);
            if (getValue(curr) == -1) {
                stack.push(curr);
            }
            else {
                double operator1 = Double.parseDouble(stack.pop());
                double operator2 = Double.parseDouble(stack.pop());
                if (curr.equals("+")) {
                    stack.push((operator1 + operator2) + "");
                }
                else if (curr.equals("-")) {
                    stack.push((operator2 - operator1) + "");
                }
                else if (curr.equals("*")) {
                    stack.push((operator1 * operator2) + "");
                }
                else if (curr.equals("/")) {
                    if (operator1 == 0) {
                        throw new IllegalArgumentException("Invalid expression: Cannot divide by 0");
                    }
                    stack.push((operator2 / operator1) + "");
                }
                else if (curr.equals("^")) {
                    stack.push((Math.pow(operator2, operator1)) + "");
                }
            }
            expression = expression.substring(1);
        }
        return Double.parseDouble(stack.pop());
    }

    public static int getValue(String symbol) {
        if (symbol.equals("^")) {
            return 3;
        }
        else if (symbol.equals("*") || symbol.equals("/")) {
            return 2;
        }
        else if (symbol.equals("+") || symbol.equals("-")) {
            return 1;
        }
        else {
            return -1;
        }
    }

    public static boolean isInteger(String str) {
        return str.matches("\\d+");
    }

    public static String getNext(String x) {
        return x.charAt(0) + "";
    }

    public static String stackToString(Stack<?> stack) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < stack.size(); i++) {
            sb.append(stack.get(i));
            if (i < stack.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    private static boolean isOperand(String token) {
        return token.matches("[0-9]+");
    }

    private static boolean isOperator(String token) {
        return "+-*/^".contains(token) && token.length() == 1;
    }

    public static boolean isValidPostFix(String expression) {
        int numParenthesis = 0;
        if (expression.isEmpty()) {
            return false;
        }
        int numOperands = 0;
        int numOperators = 0;
        for (int i = 0; i < expression.length(); i++) {
            String c = expression.charAt(i) + "";
            if (isOperand(c)) {
                numOperands++;
            }
            if (isOperator(c)) {
                numOperators++;
            }
        }
        if (numOperands - 1 != numOperators) {
            return false;
        }
        while (!expression.isEmpty()) {
            String curr = getNext(expression);
            expression = expression.substring(1);
            if (curr.equals("(")) {
                numParenthesis++;
            }
            if (curr.equals(")")) {
                numParenthesis--;
            }
            if ((! isInteger(curr) && getValue(curr) == -1) && (! curr.equals("(")) && (! curr.equals(")"))) {
                return false;
            }
            if (numParenthesis < 0) {
                return false;
            }
        }
        return numParenthesis == 0;
    }
}
