package ru.russianpost.epslite.api;

import java.util.Objects;

public enum ProcessSettings {

    /**
     *
     */
    PRINT("PRINT"),

    ELECTRONIC("ELECTRONIC"),

    BOTH("BOTH");


    private final String type;

    ProcessSettings(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static ProcessSettings fromString(String settingsCode) throws Exception {
        for (ProcessSettings ps : ProcessSettings.values()) {
            if (Objects.equals(settingsCode, ps.getType())){
                return valueOf(settingsCode);
            }
        }
        throw new IllegalArgumentException("Invali value: " + settingsCode);
    }
}
