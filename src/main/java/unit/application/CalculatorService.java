package unit.application;

import au.com.anz.fxcalculator.model.Currency;
import au.com.anz.fxcalculator.model.InputDetails;
import au.com.anz.fxcalculator.util.ReadFileUtil;

import java.math.BigDecimal;
import java.util.Map;

public class CalculatorService {

    private Map<String, BigDecimal> currencyRateMap = ReadFileUtil.getCurrencyRateMap("currency_rates.csv");
    private Map<Currency, Integer> currencyDecimalPointsMap = ReadFileUtil.getCurrencyDecimalPoints("currency_decimal_points.csv");
    private Map<String, Currency> crossCurrencyMap = ReadFileUtil.getCrossCurrencyMap("cross_currency.csv");

    public BigDecimal convertMoney(InputDetails inputDetails) {

        if (inputDetails.getFromCurrency() == inputDetails.getToCurrency()) {
            return inputDetails.getAmountToBeConverted();
        }

        String currencyKey = inputDetails.getFromCurrency().name() + inputDetails.getToCurrency().name();

        if (currencyRateMap.containsKey(currencyKey)) {
            BigDecimal conversionRate = currencyRateMap.get(currencyKey);
            Integer roundingPoint = currencyDecimalPointsMap.get(inputDetails.getToCurrency());
            return inputDetails.getAmountToBeConverted().multiply(conversionRate).setScale(roundingPoint, BigDecimal.ROUND_HALF_UP);
        }

        if (crossCurrencyMap.containsKey(currencyKey)) {
            Currency crossCurrency = crossCurrencyMap.get(currencyKey);
            BigDecimal money = convertMoney(new InputDetails(inputDetails.getFromCurrency(), crossCurrency, inputDetails.getAmountToBeConverted()));
            return convertMoney(new InputDetails(crossCurrency, inputDetails.getToCurrency(), money));
        }

        BigDecimal money = convertMoney(new InputDetails(inputDetails.getFromCurrency(), Currency.USD, inputDetails.getAmountToBeConverted()));
        return convertMoney(new InputDetails(Currency.USD, inputDetails.getToCurrency(), money));
    }
}
