package ru.russianpost.api;

import java.util.Objects;

public class ProcessClientSettings {
    private ProcessSettings processSettings;

    public ProcessClientSettings(ProcessSettings processSettings) {
        this.processSettings = processSettings;
    }
    public ProcessClientSettings(String processSettings) {
        try {
            this.processSettings = ProcessSettings.fromString(processSettings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ProcessClientSettings() {
        this.processSettings = null;
    }

    public ProcessSettings getProcessSettings() {
        return processSettings;
    }

    public void setProcessSettings(ProcessSettings processSettings) {
        this.processSettings = processSettings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessClientSettings that = (ProcessClientSettings) o;
        return processSettings == that.processSettings;
    }

    @Override
    public int hashCode() {
        return Objects.hash(processSettings);
    }
}
