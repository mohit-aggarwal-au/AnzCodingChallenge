package unit.au.com.anz.fxcalculator.application;

import au.com.anz.fxcalculator.application.CalculatorService;
import au.com.anz.fxcalculator.model.Currency;
import au.com.anz.fxcalculator.model.InputDetails;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorServiceTest {

    private CalculatorService calculatorService = new CalculatorService();

    @ParameterizedTest
    @CsvFileSource(resources = "/calculator_service_test.csv", numLinesToSkip = 1)
    void convertAmount_withValidValues_returnsSuccess(String fromCurrencyString, String toCurrencyString, String amount, String expected) {
        Currency fromCurrency = Currency.valueOf(fromCurrencyString);
        Currency toCurrency = Currency.valueOf(toCurrencyString);
        BigDecimal amountToBeConverted = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
        InputDetails inputDetails  = new InputDetails(fromCurrency, toCurrency, amountToBeConverted);
        assertEquals(expected, calculatorService.convertAmount(inputDetails).toString());

    }
}
