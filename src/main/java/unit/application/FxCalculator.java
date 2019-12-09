package unit.application;

import au.com.anz.fxcalculator.exception.InvalidParameterException;
import au.com.anz.fxcalculator.model.InputDetails;
import au.com.anz.fxcalculator.model.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FxCalculator {

   public static void main(String[] args) {
        InputDetails inputDetails = getInputDetails(args);
        CalculatorService calculatorService = new CalculatorService();
        BigDecimal convertedAmount = calculatorService.convertMoney(inputDetails);
        System.out.print(inputDetails.getFromCurrency() + " " + inputDetails.getAmountToBeConverted() + " = " + inputDetails.getToCurrency() + " " + convertedAmount);
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
