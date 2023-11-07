package pc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.example.calculator;
import java.util.HashMap;
import java.util.Map;

public class calculatorTest {

    @Test
    public void testInfixToPostfix() {
        String infixExpression = "a+b*c";
        String expectedPostfix = "abc*+";
        Assertions.assertEquals(expectedPostfix, calculator.infixToPostfix(infixExpression));
    }

    @Test
    public void testCalculatePostfix() {
        String postfixExpression = "42*5+";
        double expectedValue = 13.0;
        Assertions.assertEquals(expectedValue, calculator.calculatePostfix(postfixExpression, null));
    }

    @Test
    public void testWithVariables() {
        String expression = "a+b*c";
        Map<Character, Double> variableValues = new HashMap<>();
        variableValues.put('a', 2.0);
        variableValues.put('b', 3.0);
        variableValues.put('c', 4.0);
        double expectedValue = 14.0;
        Assertions.assertEquals(expectedValue, calculator.calculate(expression, variableValues));
    }
}
