package com.luxoft.clients.processSettings;

import java.util.Objects;

public enum ProcessSettings {
    PRINT("PRINT"), ELECTRONIC("ELECTRONIC"), BOTH("BOTH");
    ProcessSettings(String type) {
    }
    public static ProcessSettings fromString(String settingsCode) throws Exception{
        for (ProcessSettings type: ProcessSettings.values()) {
            if (Objects.equals(settingsCode, type.toString())) {
                return valueOf(settingsCode);
            }
        }
        throw new Exception();
    }
}
