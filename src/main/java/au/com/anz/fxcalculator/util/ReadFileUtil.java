package au.com.anz.fxcalculator.util;

import au.com.anz.fxcalculator.exception.InvalidInputFileException;
import au.com.anz.fxcalculator.exception.InvalidParameterException;
import au.com.anz.fxcalculator.model.Currency;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadFileUtil {

    public static Map<String, BigDecimal> getCurrencyRateMap(String fileName) {
        Map<String, BigDecimal> currencyRateMap = new HashMap<>();
        List<String> lines = getFileContentList(fileName);
        lines.forEach(line -> {
            String[] lineContent = getLineContent(line, 3, fileName);
            Currency fromCurrency = Currency.valueOf(lineContent[0].trim().toUpperCase());
            Currency toCurrency = Currency.valueOf(lineContent[1].trim().toUpperCase());
            BigDecimal conversionRate = new BigDecimal(lineContent[2].trim()).setScale(4, RoundingMode.HALF_UP);
            BigDecimal inverseRate = new BigDecimal(1).divide(conversionRate, 4, BigDecimal.ROUND_HALF_UP);
            currencyRateMap.put(fromCurrency.toString() + toCurrency.toString(), conversionRate);
            currencyRateMap.put(toCurrency.toString() + fromCurrency.toString(), inverseRate);
        });
        return currencyRateMap;
    }

    public static Map<String, Currency> getCrossCurrencyMap(String fileName) {
        Map<String, Currency> crossCurrencyMap = new HashMap<>();
        List<String> lines = getFileContentList(fileName);
        lines.forEach(line -> {
            String[] lineContent = getLineContent(line, 3, fileName);
            Currency fromCurrency = Currency.valueOf(lineContent[0].trim().toUpperCase());
            Currency toCurrency = Currency.valueOf(lineContent[1].trim().toUpperCase());
            Currency crossCurrency = Currency.valueOf(lineContent[2].trim().toUpperCase());

            crossCurrencyMap.put(fromCurrency.toString() + toCurrency.toString(), crossCurrency);
            crossCurrencyMap.put(toCurrency.toString() + fromCurrency.toString(), crossCurrency);
        });
        return crossCurrencyMap;
    }


    public static Map<Currency, Integer> getCurrencyDecimalPoints(String fileName) {
        Map<Currency, Integer> currencyDecimalPointsMap = new HashMap<>();
        List<String> lines = getFileContentList(fileName);
        lines.forEach(line -> {
            String[] lineContent = getLineContent(line, 2, fileName);
            Currency currency = Currency.valueOf(lineContent[0].trim().toUpperCase());
            Integer decimalPoint = Integer.parseInt(lineContent[1].trim().toUpperCase());
            currencyDecimalPointsMap.put(currency, decimalPoint);

        });
        return currencyDecimalPointsMap;
    }

    private static String[] getLineContent(String line, int expectedParameters, String fileName) {
        String[] lineContent = line.split(Utility.COMMA);
        if (lineContent.length != expectedParameters) {
            throw new InvalidParameterException("Exception occurred while parsing transactions file :" + fileName + " file format is not correct");
        }
        return lineContent;
    }

    private static List<String> getFileContentList(String fileName) {
        List<String> lines = null;
        try {
            URL resourceURL = ReadFileUtil.class.getClassLoader().getResource(fileName);
            if (resourceURL == null) {
                throw new InvalidInputFileException("Exception occurred while opening input file:" + fileName);
            }
            Path path = Paths.get(resourceURL.toURI());
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            //TODO: Do null check and all
            lines.remove(0);

        } catch (IOException | URISyntaxException exception) {
            throw new InvalidInputFileException("Exception occurred while opening input file " + fileName + ":" + exception.getMessage());
        }

        return lines;
    }

}