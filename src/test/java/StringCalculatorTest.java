import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringCalculatorTest {

    StringCalculator stringCalculator = new StringCalculator();

    @Test
    void addEmptyString () {
        String input = "";
        Integer expected = 0;

        Integer result = stringCalculator.add(input);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void addOneNumber () {
        String input = "28";
        Integer expected = 28;

        Integer result = stringCalculator.add(input);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void addMultipleNumbers () {
        String input = "2,8,10";
        Integer expected = 20;

        Integer result = stringCalculator.add(input);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void addMultipleLines () {
        String input = "10\n12,15";
        Integer expected = 37;

        Integer result = stringCalculator.add(input);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void addWithCustomDelimiter () {
        String input = "//;\n2;3";
        Integer expected = 5;

        Integer result = stringCalculator.add(input);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void addWithLongerDelimiter () {
        String input = "//[->]\n1->5->1";
        Integer expected = 7;

        Integer result = stringCalculator.add(input);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void addWithMultipleDelimiters () {
        String input = "//[ plus ][ and ]\n2 plus 7 and 3";
        Integer expected = 12;

        Integer result = stringCalculator.add(input);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void addWithNegatives () {
        String input = "0,0,-3,4,-2";
        String expected = "Negatives not allowed: -3,-2";

        Throwable thrown = Assertions.assertThrows(RuntimeException.class, ()->stringCalculator.add(input));
        Assertions.assertEquals(expected, thrown.getMessage());
    }

    @Test
    void addWithGreaterThan1000 () {
        String input = "1,10,100,1000,1001,1000,100,10,1";
        Integer expected = 2222;

        Integer result = stringCalculator.add(input);

        Assertions.assertEquals(expected, result);
    }
}
