package com.givaudan.contacts.service.user.dto;

import com.givaudan.contacts.domain.User;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
@Getter
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String login;

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
            "}";
    }
}
