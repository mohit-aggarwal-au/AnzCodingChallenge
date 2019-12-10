package unit.au.com.anz.fxcalculator.application;

import au.com.anz.fxcalculator.application.FxCalculator;
import au.com.anz.fxcalculator.exception.InvalidParameterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FxCalculatorTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void fxCalculator_withValidInput_returnsSuccess() {
        FxCalculator.main(new String[]{"AUD", "100.00", "in", "NOK"});
        assertEquals("AUD 100.00 = NOK 588.97", outContent.toString());
    }

    @ParameterizedTest
    @MethodSource("invalidCurrencyValues")
    public void fxCalculator_withInvalidCurrencyValue_returnsErrorMessage(String[] input, String fromCurrency, String toCurrency) {
        FxCalculator.main(input);
        assertEquals(String.format("Unable to find rate for %s/%s", fromCurrency, toCurrency), outContent.toString());
    }

    private static Stream<Arguments> invalidCurrencyValues() {
        return Stream.of(Arguments.of(new String[]{"AUD", "100.00", "in", "RED"}, "AUD", "RED"),
                Arguments.of(new String[]{"FJD", "100.00", "in", "DKK"}, "FJD", "DKK"),
                Arguments.of(new String[]{"KRW", "100.00", "in", "FJD"}, "KRW", "FJD"),
                Arguments.of(new String[]{"Random_value", "100.00", "in", "unknown_value"}, "RANDOM_VALUE", "UNKNOWN_VALUE")
        );
    }

    @ParameterizedTest
    @MethodSource("incorrectNumberOfParameterValue")
    public void fxCalculator_withIncorrectNumberOfParameterValue_throwsException(String[] input) {
        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () ->
                FxCalculator.main(input), "Expected to throw InvalidParameterException, but didn't throw it");
        exception.getMessage();
        assertTrue(exception.getMessage().contains("Input parameters are not correct"));
    }

    static Stream<Arguments> incorrectNumberOfParameterValue() {
        return Stream.of(
                Arguments.of((Object) new String[]{"AUD", "100.00", "in", "DKK", "extra_parameter"}),
                Arguments.of((Object) new String[]{"KRW", "100.00", "FJD"})
        );
    }

    @ParameterizedTest
    @MethodSource("invalidParameterValue")
    public void fxCalculator_withInvalidParameterValue_throwsException(String[] input) {
        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () ->
                FxCalculator.main(input), "Expected to throw InvalidParameterException, but didn't throw it");
        exception.getMessage();
        assertTrue(exception.getMessage().contains("Exception occurred while parsing input values"));
    }

    static Stream<Arguments> invalidParameterValue() {
        return Stream.of(
                Arguments.of((Object) new String[]{null, "100.00", "in", "DKK"}),
                Arguments.of((Object) new String[]{null, null, null, null})
        );
    }

}
