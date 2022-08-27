package service.impl;

import entity.Account;
import entity.Transaction;
import exceptions.TransactionException;
import lombok.RequiredArgsConstructor;
import service.CurrencyService;
import service.TransactionService;

import java.math.BigDecimal;
import java.time.Instant;

@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final CurrencyService currencyService;

    @Override
    // Important! Need to use @Transactional annotation.
    // By default, amount in currency of accountFrom.
    public Transaction transaction(Account accountFrom, Account accountTo, BigDecimal amount) throws TransactionException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionException("The amount can't be zero or negative");
        }
        if (accountFrom.getBalance().compareTo(amount) >= 0) {
            BigDecimal convertedAmount;

            if (accountFrom.getCurrency().equals(accountTo.getCurrency())) {
                convertedAmount = amount;
            } else {
                convertedAmount = currencyService.convert(accountFrom.getCurrency(), accountTo.getCurrency(), amount);
            }

            accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
            accountTo.setBalance(accountTo.getBalance().add(convertedAmount));

            return new Transaction(
                    Instant.now(),
                    accountFrom,
                    accountTo,
                    amount,
                    convertedAmount);
        }
        throw new TransactionException("Insufficient funds to perform the operation");
    }
}
