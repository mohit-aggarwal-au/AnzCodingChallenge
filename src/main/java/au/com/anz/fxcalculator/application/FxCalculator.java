package au.com.anz.fxcalculator.application;

import au.com.anz.fxcalculator.exception.InvalidParameterException;
import au.com.anz.fxcalculator.model.InputDetails;
import au.com.anz.fxcalculator.model.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FxCalculator {

    public static void main(String[] args) {
        InputDetails inputDetails = getInputDetails(args);
        if (inputDetails != null) {
            CalculatorService calculatorService = new CalculatorService();
            BigDecimal convertedAmount = calculatorService.convertMoney(inputDetails);
            System.out.print(inputDetails.getFromCurrency() + " " + inputDetails.getAmountToBeConverted() + " = " + inputDetails.getToCurrency() + " " + convertedAmount);
        }

    }
    private static InputDetails getInputDetails(String[] args) {
        if (args == null || args.length != 4) {
            throw new InvalidParameterException("Input parameters are not correct");
        }

        InputDetails inputDetails = null;
        try {
            Currency fromCurrency = Currency.valueOf(args[0].trim());
            Currency toCurrency = Currency.valueOf(args[3].trim());
            BigDecimal amountToBeConverted = new BigDecimal(args[1].trim()).setScale(2, RoundingMode.HALF_UP);
            inputDetails = new InputDetails(fromCurrency, toCurrency, amountToBeConverted);

        } catch (NumberFormatException exception) {
            throw new InvalidParameterException("Exception occurred while parsing amount to be converted:" + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            System.out.print(String.format("Unable to find rate for %s/%s", args[0].trim().toUpperCase(), args[3].trim().toUpperCase()));
            return null;
        } catch (Exception exception) {
            throw new InvalidParameterException("Exception occurred while parsing input values:" + exception.getMessage());
        }

        return inputDetails;
    }

}
