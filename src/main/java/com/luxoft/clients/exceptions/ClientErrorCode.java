package com.luxoft.clients.exceptions;

public enum  ClientErrorCode {
    NULL_CLIENT("Client is not initialized!"),
    WRONG_CLIENT_ID("ID does not match format"),
    WRONG_DISPLAYNAME("DISPLAY NAME does not match format"),
    WRONG_SHORTNAME("SHORT NAME does not match format"),
    WRONG_INN("INN does not match format"),
    WRONG_CLIENT_VERSION("Can't update client"),
    WRONG_PROCESS_CLIENT_SETTINGS("PROCESS SETTINGS does not match format");

    private String errorString;

    public String getErrorString() {
        return errorString;
    }

    ClientErrorCode (String errorString) {
        this.errorString = errorString;
    }
}
