package org.pascalot.racePark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Currency;

/**
 * Created by hamisu on 11/23/15.
 */
public class Money implements Serializable {

    private static final String SELF = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = LoggerFactory.getLogger(SELF);

    public Money(Currency currency, Integer denomination) {
        this.currency = currency;
        this.denomination = denomination;
    }

    private Currency currency;
    private Integer denomination;

    public Integer getDenomination() {
        return denomination;
    }

    @Override
    public String toString()
    {
        return currency.getSymbol()+denomination.toString();
    }

}
