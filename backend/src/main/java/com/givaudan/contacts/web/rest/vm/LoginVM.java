package com.givaudan.contacts.web.rest.vm;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginVM {

    @NotNull
    @Size(min = 1, max = 50)
    private String username;
    @NotNull
    @Size(min = 4, max = 100)
    private String password;
    private boolean rememberMe;

    // prettier-ignore
    @Override
    public String toString() {
        return "LoginVM{" +
            "username='" + username + '\'' +
            ", rememberMe=" + rememberMe +
            '}';
    }
}
