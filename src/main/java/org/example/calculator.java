package org.example;
import java.util.*;

public class calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите выражение: ");
        String expression = scanner.nextLine();

        // Запрос значений переменных, если они присутствуют
        Set<Character> variables = extractVariables(expression);
        Map<Character, Double> variableValues = new HashMap<>();

        for (char variable : variables) {
            System.out.print("Введите значение для переменной " + variable + ": ");
            double value = scanner.nextDouble();
            variableValues.put(variable, value);
        }

        double result = calculate(expression, variableValues);

        if (!Double.isNaN(result)) {
            System.out.println("Результат: " + result);
        } else {
            System.out.println("Ошибка: Некорректное выражение");
        }
    }

    // Метод для извлечения переменных из выражения
    public static Set<Character> extractVariables(String expression) {
        Set<Character> variables = new HashSet<>();
        for (char c : expression.toCharArray()) {
            if (Character.isLetter(c)) {
                variables.add(c);
            }
        }
        return variables;
    }

    // Метод для вычисления значения выражения
    public static double calculate(String expression, Map<Character, Double> variableValues) {
        try {
            // Преобразование выражения в постфиксную нотацию (обратная польская запись)
            String postfixExpression = infixToPostfix(expression);

            // Вычисление значения выражения на основе постфиксной нотации
            return calculatePostfix(postfixExpression, variableValues);
        } catch (Exception e) {
            return Double.NaN; // Обработка ошибок при вычислении выражения
        }
    }

    // Метод для преобразования инфиксного выражения в постфиксную нотацию
    public static String infixToPostfix(String expression) {
        StringBuilder postfix = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();
        Map<Character, Integer> precedence = Map.of('+', 1, '-', 1, '*', 2, '/', 2);

        for (char c : expression.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                stack.pop(); // Удаляем открывающую скобку из стека
            } else {
                while (!stack.isEmpty() && precedence.getOrDefault(c, 0) <= precedence.getOrDefault(stack.peek(), 0)) {
                    postfix.append(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }

        return postfix.toString();
    }

    public static double calculatePostfix(String postfixExpression, Map<Character, Double> variableValues) {
        Deque<Double> stack = new ArrayDeque<>();

        for (char c : postfixExpression.toCharArray()) {
            if (Character.isLetter(c)) {
                if (!variableValues.containsKey(c)) {
                    System.out.println("Ошибка: Не хватает значения для переменной " + c);
                    return Double.NaN;
                }
                stack.push(variableValues.get(c));
            } else if (Character.isDigit(c)) {
                stack.push((double)(c - '0'));
            } else {
                double val1 = stack.pop();
                double val2 = stack.pop();

                switch (c) {
                    case '+':
                        stack.push(val2 + val1);
                        break;
                    case '-':
                        stack.push(val2 - val1);
                        break;
                    case '*':
                        stack.push(val2 * val1);
                        break;
                    case '/':
                        stack.push(val2 / val1);
                        break;
                }
            }
        }

        return stack.pop();
    }
}
