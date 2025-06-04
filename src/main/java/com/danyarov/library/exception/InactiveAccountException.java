package com.danyarov.library.exception;

/**
 * Inactive account exception.
 */
public class InactiveAccountException extends ServiceException {
    public InactiveAccountException() {
        super("error.account.inactive");
    }
}
