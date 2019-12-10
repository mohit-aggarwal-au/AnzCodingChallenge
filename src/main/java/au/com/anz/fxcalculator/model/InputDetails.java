package au.com.anz.fxcalculator.model;

import java.math.BigDecimal;

public class InputDetails {

    public InputDetails(Currency fromCurrency, Currency toCurrency, BigDecimal amountToBeConverted) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amountToBeConverted = amountToBeConverted;
    }

    private Currency fromCurrency;

    private Currency toCurrency;

    private BigDecimal amountToBeConverted;

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public BigDecimal getAmountToBeConverted() {
        return amountToBeConverted;
    }

}
