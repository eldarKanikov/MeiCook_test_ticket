package service.impl;

import enums.Currency;
import service.CurrencyService;

import java.math.BigDecimal;

public class CurrencyServiceImpl implements CurrencyService {
    @Override
    public BigDecimal convert(Currency currencyFrom, Currency currencyTo, BigDecimal amount) {
        double coefficient = getCurrentCoefficientExchange(currencyFrom, currencyTo);
        return amount.multiply(BigDecimal.valueOf(coefficient));
    }

    public double getCurrentCoefficientExchange(Currency from, Currency to) {
        if (from.equals(to)) {
            return 1;
        }
        //get exchange coefficient from official source
        //...

        //Mock: positive random
        return ((int) (Math.random() * 9)) + 1;
    }
}
