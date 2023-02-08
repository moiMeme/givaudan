package com.givaudan.contacts.web.rest.errors;


import java.io.Serial;

public class EmailAlreadyUsedException extends BadRequestAlertException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super(DEFAULT_TYPE, "Email is already in use!", "userManagement", "emailexists");
    }
}
