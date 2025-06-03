package com.danyarov.library.exception;

public class InactiveAccountException extends ServiceException {
    public InactiveAccountException() {
        super("error.account.inactive");
    }
}
