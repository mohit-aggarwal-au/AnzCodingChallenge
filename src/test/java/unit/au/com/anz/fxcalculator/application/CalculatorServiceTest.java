package unit.au.com.anz.fxcalculator.application;

import au.com.anz.fxcalculator.application.CalculatorService;
import au.com.anz.fxcalculator.model.Currency;
import au.com.anz.fxcalculator.model.InputDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorServiceTest {

    private CalculatorService calculatorService = new CalculatorService();

    @ParameterizedTest
    @CsvFileSource(resources = "/calculator_service_test.csv", numLinesToSkip = 1)
    void calculatorService_wuthVaidValues_returnsSuccess(String fromCurrencyString, String toCurrencyString, String amount, String expected) {
        Currency fromCurrency = Currency.valueOf(fromCurrencyString);
        Currency toCurrency = Currency.valueOf(toCurrencyString);
        BigDecimal amountToBeConverted = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
        InputDetails inputDetails  = new InputDetails(fromCurrency, toCurrency, amountToBeConverted);
        assertEquals(expected, calculatorService.convertMoney(inputDetails).toString());

    }
}
