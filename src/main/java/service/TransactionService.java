package service;

import entity.Account;
import entity.Transaction;
import exceptions.TransactionException;

import java.math.BigDecimal;

public interface TransactionService {
    Transaction transaction(Account from, Account to, BigDecimal amount) throws TransactionException;
}
