package entity;

import enums.Currency;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class Account {

    private Long id;

    @NonNull
    @Setter
    private BigDecimal balance;

    @NonNull
    private Currency currency;

    @NonNull
    private User user;

}
