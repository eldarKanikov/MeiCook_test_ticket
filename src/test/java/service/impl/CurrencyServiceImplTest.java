package service.impl;

import enums.Currency;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.CurrencyService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class CurrencyServiceImplTest {

    private final CurrencyService currencyService = Mockito.spy(CurrencyServiceImpl.class);

    @Test
    void convert() {
        BigDecimal testAmount = BigDecimal.valueOf(10);
        BigDecimal result = currencyService.convert(Currency.EUR, Currency.EUR, testAmount);
        assertEquals(result.compareTo(testAmount), 0);
    }

    @Test
    void getCurrentCoefficientExchange() {
        Mockito.doReturn(2d).when(currencyService)
                .getCurrentCoefficientExchange(any(Currency.class), any(Currency.class));
        BigDecimal result = currencyService.convert(Currency.EUR, Currency.GBP, BigDecimal.valueOf(10));
        assertEquals(result.compareTo(BigDecimal.valueOf(20)), 0);
    }
}