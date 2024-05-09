package runtimezero.tricount.util;


import runtimezero.tricount.enums.TricountErrorCode;

public class TricountApiException extends RuntimeException{
    private TricountErrorCode errorCode = TricountErrorCode.UNCATEGORIZED;

    public TricountApiException(String message, TricountErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public TricountErrorCode getErrorCode() {
        return errorCode;
    }
}
