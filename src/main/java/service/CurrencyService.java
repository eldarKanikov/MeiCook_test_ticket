package service;

import enums.Currency;

import java.math.BigDecimal;

public interface CurrencyService {
    BigDecimal convert(Currency from, Currency to, BigDecimal amount);

    double getCurrentCoefficientExchange(Currency from, Currency to);
}
