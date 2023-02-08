package com.givaudan.contacts.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.io.Serial;

public class ImportExportException extends AbstractThrowableProblem {

    @Serial
    private static final long serialVersionUID = 1L;

    public ImportExportException() {
        super(DEFAULT_TYPE, "error in upload/download", Status.INTERNAL_SERVER_ERROR);
    }
}