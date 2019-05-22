package ru.russianpost.adminbackend.exceptions;

public class ClientException extends Exception {

    private ClientErrorCode errorCode;

    public ClientException(ClientErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ClientErrorCode getErrorCode() {
        return errorCode;
    }
}
