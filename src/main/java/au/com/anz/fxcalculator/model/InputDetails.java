package au.com.anz.fxcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
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

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }

    public BigDecimal getAmountToBeConverted() {
        return amountToBeConverted;
    }

    public void setAmountToBeConverted(BigDecimal amountToBeConverted) {
        this.amountToBeConverted = amountToBeConverted;
    }
}
