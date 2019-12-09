package au.com.anz.fxcalculator.application;

import au.com.anz.fxcalculator.exception.InvalidParameterException;
import au.com.anz.fxcalculator.model.InputDetails;
import au.com.anz.fxcalculator.model.Currency;
import au.com.anz.fxcalculator.util.ReadFileUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class FxCalculator {

    private static Map<String, BigDecimal> currencyRateMap = ReadFileUtil.getCurrencyRateMap("currency_rates.csv");
    private static Map<Currency, Integer> currencyDecimalPointsMap = ReadFileUtil.getCurrencyDecimalPoints("currency_decimal_points.csv");
    private static Map<String, Currency> crossCurrencyMap = ReadFileUtil.getCrossCurrencyMap("cross_currency.csv");

    public static void main(String[] args) {
        InputDetails inputDetails = getInputDetails(args);
        BigDecimal convertedAmount = convertMoney(inputDetails);
        System.out.println(inputDetails.getFromCurrency() + " " + inputDetails.getAmountToBeConverted() + " = " + inputDetails.getToCurrency() + " " + convertedAmount);
    }

    private static BigDecimal convertMoney(InputDetails inputDetails) {

        if(inputDetails.getFromCurrency() == inputDetails.getToCurrency()){
            return inputDetails.getAmountToBeConverted();
        }

        String currencyKey = inputDetails.getFromCurrency().name() + inputDetails.getToCurrency().name();

        if(currencyRateMap.containsKey(currencyKey)) {
            BigDecimal conversionRate = currencyRateMap.get(currencyKey);
            Integer roundingPoint = currencyDecimalPointsMap.get(inputDetails.getToCurrency());
            return inputDetails.getAmountToBeConverted().multiply(conversionRate).setScale(roundingPoint, BigDecimal.ROUND_HALF_UP);
        }

        if(crossCurrencyMap.containsKey(currencyKey)) {
            Currency crossCurrency = crossCurrencyMap.get(currencyKey);
            BigDecimal money = convertMoney(new InputDetails(inputDetails.getFromCurrency(), crossCurrency, inputDetails.getAmountToBeConverted()));
            return convertMoney(new InputDetails(crossCurrency, inputDetails.getToCurrency(), money));
        }

        BigDecimal money = convertMoney(new InputDetails(inputDetails.getFromCurrency(), Currency.USD, inputDetails.getAmountToBeConverted()));
        return convertMoney(new InputDetails(Currency.USD, inputDetails.getToCurrency(), money));
    }

    private static InputDetails getInputDetails(String[] args) {
        if (args == null || args.length != 4) {
            throw new InvalidParameterException("Input parameters are not correct");
        }

        InputDetails inputDetails = null;
        try {
            Currency fromCurrency = Currency.valueOf(args[0].trim().toUpperCase());
            Currency toCurrency = Currency.valueOf(args[3].trim().toUpperCase());
            BigDecimal amountToBeConverted = new BigDecimal(args[1].trim()).setScale(2, RoundingMode.HALF_UP);
            inputDetails = new InputDetails(fromCurrency, toCurrency, amountToBeConverted);

        } catch (IllegalArgumentException exception) {
            throw new InvalidParameterException("Exception occurred while parsing input parameters:" + exception.getMessage());
        }
        return inputDetails;
    }
}
