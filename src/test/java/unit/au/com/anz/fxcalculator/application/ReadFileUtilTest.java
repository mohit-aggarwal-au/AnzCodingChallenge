package unit.au.com.anz.fxcalculator.application;

import au.com.anz.fxcalculator.exception.InvalidInputFileException;
import au.com.anz.fxcalculator.model.Currency;
import au.com.anz.fxcalculator.util.ReadFileUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReadFileUtilTest {

    @ParameterizedTest
    @ValueSource(strings = {"nonExistentFile.csv",
            "valid_input#$%#$abc.csv"})
    public void getCrossCurrencyMap_invalidFile_throwsInvalidParameterException(String fileName) {
        InvalidInputFileException exception = assertThrows(InvalidInputFileException.class, () ->
                        ReadFileUtil.getCrossCurrencyMap(fileName)
                , "Expected to throw InvalidInputFileException, but didn't throw it");
        exception.getMessage();
        assertTrue(exception.getMessage().contains("Exception occurred while opening input file"));
    }

    @Test
    public void getCrossCurrencyMap_validFile_returnSuccess() {
        Map<String, Currency> crossCurrencyMap = ReadFileUtil.getCrossCurrencyMap("cross_currency_test.csv");
        assertEquals(4, crossCurrencyMap.size());
    }

    @Test
    public void getCurrencyRateMap_validFile_returnSuccess() {
        Map<String, BigDecimal> currencyRateMap = ReadFileUtil.getCurrencyRateMap("currency_rates_test.csv");
        assertEquals(4, currencyRateMap.size());
    }

    @Test
    public void getCurrencyDecimalPoints_validFile_returnSuccess() {
        Map<Currency, Integer> currencyDecimalPointsMap = ReadFileUtil.getCurrencyDecimalPoints("currency_decimal_points_test.csv");
        assertEquals(2, currencyDecimalPointsMap.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"nonExistentFile.csv",
            "valid_input#$%#$abc.csv"})
    public void getCurrencyRateMap_invalidFile_throwsInvalidParameterException(String fileName) {
        InvalidInputFileException exception = assertThrows(InvalidInputFileException.class, () ->
                        ReadFileUtil.getCurrencyRateMap(fileName)
                , "Expected to throw InvalidInputFileException, but didn't throw it");
        exception.getMessage();
        assertTrue(exception.getMessage().contains("Exception occurred while opening input file"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"nonExistentFile.csv",
            "valid_input#$%#$abc.csv"})
    public void getCurrencyDecimalPoints_invalidFile_throwsInvalidParameterException(String fileName) {
        InvalidInputFileException exception = assertThrows(InvalidInputFileException.class, () ->
                        ReadFileUtil.getCurrencyDecimalPoints(fileName)
                , "Expected to throw InvalidInputFileException, but didn't throw it");
        exception.getMessage();
        assertTrue(exception.getMessage().contains("Exception occurred while opening input file"));
    }
}

