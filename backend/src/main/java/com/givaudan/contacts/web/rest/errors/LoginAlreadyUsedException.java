package com.givaudan.contacts.web.rest.errors;

import java.io.Serial;

public class LoginAlreadyUsedException extends BadRequestAlertException {

    @Serial
    private static final long serialVersionUID = 1L;

    public LoginAlreadyUsedException() {
        super(DEFAULT_TYPE, "Login name already used!", "userManagement", "userexists");
    }
}
