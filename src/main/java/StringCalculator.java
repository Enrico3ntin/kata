import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    private final static String DEFAULT_DELIMITER = "[,\n]";
    private final static int IGNORE_THRESHOLD = 1000;
    private final static Pattern customDelimiterPattern = Pattern.compile("^//(.|\\[.+\\])\n(.*)$");

    public int add (String input) {
        Matcher matcher = customDelimiterPattern.matcher(input);

        if (matcher.matches()) {
            String delimiters = parseCustomDelimiters(matcher.group(1));
            String numbers = matcher.group(2);
            return add(numbers, delimiters);
        }

        return add(input, DEFAULT_DELIMITER);
    }

    private String parseCustomDelimiters (String input) {
        if (input.length() == 1) {
            return Pattern.quote(input);
        }

        String[] tokenizedInput = input
                .substring(1, input.length() -1)
                .split("]\\[");

        return Arrays.stream(tokenizedInput)
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));
    }

    public int add (String numbers, String delimiter) {
        if (numbers.isEmpty()) {
            return 0;
        }

        int[] numericValues = extractIntegersSmallerThanThreshold(numbers, delimiter);
        validateForPositiveValues(numericValues);
        return Arrays.stream(numericValues).sum();
    }

    private int[] extractIntegersSmallerThanThreshold (String numbers, String delimiters) {
        return Arrays.stream(numbers.split(delimiters))
                .mapToInt(Integer::parseInt)
                .filter(number -> number<=IGNORE_THRESHOLD)
                .toArray();
    }

    private void validateForPositiveValues (int[] numericValues) {
        String negatives = Arrays.stream(numericValues)
                .filter(number -> number < 0)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));

        if (!negatives.isEmpty()) {
            throw new RuntimeException("Negatives not allowed: " + negatives);
        }
    }

}
