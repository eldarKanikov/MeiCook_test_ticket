package entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Getter
@Setter
@RequiredArgsConstructor
public class User {

    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String login;

    private TimeZone timeZone = TimeZone.getDefault();

    @NonNull
    private String email;

    @NonNull
    private String password;

    private List<Account> accounts = new ArrayList<>();
}
