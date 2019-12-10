package au.com.anz.fxcalculator.application;

import au.com.anz.fxcalculator.model.Currency;
import au.com.anz.fxcalculator.model.InputDetails;
import au.com.anz.fxcalculator.util.ReadFileUtil;

import java.math.BigDecimal;
import java.util.Map;

public class CalculatorService {

    //Loading currency_rate, currency_decimal_point and cross_currency values in memory from csv file.
    private Map<String, BigDecimal> currencyRateMap = ReadFileUtil.getCurrencyRateMap("currency_rates.csv");
    private Map<Currency, Integer> currencyDecimalPointsMap = ReadFileUtil.getCurrencyDecimalPoints("currency_decimal_points.csv");
    private Map<String, Currency> crossCurrencyMap = ReadFileUtil.getCrossCurrencyMap("cross_currency.csv");

    private static final Integer DECIMAL_POINT = 2;

    //Recursive function to convert amount
    public BigDecimal convertAmount(InputDetails inputDetails) {

        // if from_currency = to_currency, return same amount
        if (inputDetails.getFromCurrency() == inputDetails.getToCurrency()) {
            return inputDetails.getAmountToBeConverted();
        }

        // CurrencyKey is combination of strings fromCurrency and toCurrency. CurrencyKey is used in maps to store currency_rate and cross_currency values
        String currencyKey = inputDetails.getFromCurrency().name() + inputDetails.getToCurrency().name();

        //if direct conversion is available, use the conversion rate and return calculated amount by using correct decimal point
        if (currencyRateMap.containsKey(currencyKey)) {
            BigDecimal conversionRate = currencyRateMap.get(currencyKey);
            Integer roundingPoint = currencyDecimalPointsMap.get(inputDetails.getToCurrency());
            if(roundingPoint == null) {
                // If decimal rounding point is not available for a particular currency, use the default value of 2
                roundingPoint = DECIMAL_POINT;
            }
            return inputDetails.getAmountToBeConverted().multiply(conversionRate).setScale(roundingPoint, BigDecimal.ROUND_HALF_UP);
        }

        // if cross currency value is available in crossCurrencyMap, use the cross currency and invoke currencyAmount method recursively
        if (crossCurrencyMap.containsKey(currencyKey)) {
            Currency crossCurrency = crossCurrencyMap.get(currencyKey);
            BigDecimal money = convertAmount(new InputDetails(inputDetails.getFromCurrency(), crossCurrency, inputDetails.getAmountToBeConverted()));
            return convertAmount(new InputDetails(crossCurrency, inputDetails.getToCurrency(), money));
        }

        // if cross currency value is not available in crossCurrencyMap, use USD as default cross currency and invoke currencyAmount method recursively
        BigDecimal money = convertAmount(new InputDetails(inputDetails.getFromCurrency(), Currency.USD, inputDetails.getAmountToBeConverted()));
        return convertAmount(new InputDetails(Currency.USD, inputDetails.getToCurrency(), money));
    }
}
