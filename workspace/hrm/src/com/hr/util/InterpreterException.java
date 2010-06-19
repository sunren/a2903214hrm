package com.hr.util;

import java.util.List;

public class InterpreterException extends Exception {
    private List<String> errorList;

    public InterpreterException(List<String> errorList) {
        this.errorList = errorList;
    }

    public InterpreterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterpreterException(String message) {
        super(message);
    }

    public InterpreterException(Throwable cause) {
        super(cause);
    }

    public List<String> getErrorList() {
        return this.errorList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.InterpreterException JD-Core Version: 0.5.4
 */