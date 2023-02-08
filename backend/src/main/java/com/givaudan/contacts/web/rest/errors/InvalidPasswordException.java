package com.givaudan.contacts.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.io.Serial;

public class InvalidPasswordException extends AbstractThrowableProblem {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidPasswordException() {
        super(DEFAULT_TYPE, "Incorrect password", Status.BAD_REQUEST);
    }
}
