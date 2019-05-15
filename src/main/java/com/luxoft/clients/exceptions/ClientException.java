package com.luxoft.clients.exceptions;

public class ClientException extends Exception {

    private ClientErrorCode errorCode;

    public ClientException(ClientErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ClientErrorCode getErrorCode() {
        return errorCode;
    }
}
