package service.impl;

import entity.Account;
import entity.User;
import enums.Currency;
import exceptions.TransactionException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.CurrencyService;
import service.TransactionService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class TransactionServiceImplTest {

    private final CurrencyService currencyService
            = Mockito.spy(CurrencyServiceImpl.class);

    private final TransactionService transactionService
            = new TransactionServiceImpl(currencyService);

    private User user = new User("Alex",
            "Smith",
            "asmith",
            "asmith@gmail.com",
            "$2a$12$Zi7ICO0ckKEIkzbY8KH.5.iqG2gqDXskFwnG9li1DKNbhLKwrbZkK");
    private Account account1 = new Account(
            BigDecimal.valueOf(1000),
            Currency.EUR,
            user);
    private Account account2 = new Account(
            BigDecimal.valueOf(1000),
            Currency.EUR,
            user);
    private Account account3 = new Account(
            BigDecimal.valueOf(1000),
            Currency.GBP,
            user);

    @BeforeEach
    public void setUp() {
        account1.setBalance(BigDecimal.valueOf(1000));
        account2.setBalance(BigDecimal.valueOf(1000));
        account3.setBalance(BigDecimal.valueOf(1000));
    }

    @SneakyThrows
    @Test
    void transactionBetweenAccountsWithSameCurrencies() {
        transactionService.transaction(account1, account2, BigDecimal.valueOf(10));
        assertEquals(account1.getBalance().compareTo(BigDecimal.valueOf(990)), 0);
        assertEquals(account2.getBalance().compareTo(BigDecimal.valueOf(1010)), 0);
    }

    @SneakyThrows
    @Test
    void transactionBetweenAccountsWithDifferentCurrenciesWithFixCoefficient_1() {
        Mockito.doReturn(2d).when(currencyService)
                .getCurrentCoefficientExchange(any(Currency.class), any(Currency.class));
        transactionService.transaction(account1, account3, BigDecimal.valueOf(10));
        assertEquals(account1.getBalance().compareTo(BigDecimal.valueOf(990)), 0);
        assertEquals(account3.getBalance().compareTo(BigDecimal.valueOf(1020)), 0);
    }

    @SneakyThrows
    @Test
    void transactionBetweenAccountsWithDifferentCurrenciesWithFixCoefficient_2() {
        Mockito.doReturn(2.53d).when(currencyService)
                .getCurrentCoefficientExchange(any(Currency.class), any(Currency.class));
        transactionService.transaction(account1, account3, BigDecimal.valueOf(10));
        assertEquals(account1.getBalance().compareTo(BigDecimal.valueOf(990)), 0);
        assertEquals(account3.getBalance().compareTo(BigDecimal.valueOf(1025.3)), 0);
    }

    @SneakyThrows
    @Test
    void transactionBetweenAccountsWithDifferentCurrenciesWithFixCoefficient_3() {
        Mockito.doReturn(0.09d).when(currencyService)
                .getCurrentCoefficientExchange(any(Currency.class), any(Currency.class));
        transactionService.transaction(account1, account3, BigDecimal.valueOf(10));
        assertEquals(account1.getBalance().compareTo(BigDecimal.valueOf(990)), 0);
        assertEquals(account3.getBalance().compareTo(BigDecimal.valueOf(1000.9)), 0);
    }

    @SneakyThrows
    @Test
    void transactionBetweenAccountsWithDifferentCurrenciesWithoutFixCoefficient() {
        transactionService.transaction(account1, account3, BigDecimal.valueOf(10));
        assertEquals(account1.getBalance().compareTo(BigDecimal.valueOf(1000)), -1);
        assertEquals(account3.getBalance().compareTo(BigDecimal.valueOf(1000)), 1);
    }

    @Test
    void transactionWithInvalidAmountThanInBalance() {
        //The amount is larger than in balance
        assertThrows(TransactionException.class,
                () -> transactionService.transaction(account1, account3, BigDecimal.valueOf(1001)));

        // The amount is zero
        assertThrows(TransactionException.class,
                () -> transactionService.transaction(account1, account3, BigDecimal.valueOf(0)));

        // The amount is negative
        assertThrows(TransactionException.class,
                () -> transactionService.transaction(account1, account3, BigDecimal.valueOf(-1)));
    }

}