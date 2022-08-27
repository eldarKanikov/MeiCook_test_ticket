package entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class Transaction {

    private Long id;

    @NonNull
    private Instant creatingDateTime;

    @NonNull
    private Account accountFrom;

    @NonNull
    private Account accountTo;

    @NonNull
    private BigDecimal amount;

    @NonNull
    private BigDecimal convertedAmount;
}
