package ru.russianpost.epslite.api;

import java.util.Objects;

/**
 * Client settings
 */
public class Client {

    private int id;

    private String shortName;

    private String displayName;

    private String inn;

    private boolean isEnable;

    private ProcessClientSettings processClientSettings;

    private int version;

    /**
     * Default constrictor
     * @param id client id
     * @param shortName client short name
     * @param displayName client display name
     * @param inn client inn
     * @param isEnable client permission for sending mails
     * @param processClientSettings client processing settings
     * @param version client settings version
     */
    public Client (
            int id,
            String shortName,
            String displayName,
            String inn,
            boolean isEnable,
            ProcessClientSettings processClientSettings,
            int version
    ) {
        this.id = id;
        this.shortName = shortName;
        this.displayName = displayName;
        this.inn = inn;
        this.isEnable = isEnable;
        this.version = version;
        this.processClientSettings = processClientSettings;
    }

    /**
     * Empty constructor
     */
    public Client() {
        this.id = 0;
        this.shortName = null;
        this.displayName = null;
        this.inn = null;
        this.isEnable = false;
        this.processClientSettings = null;
        this.version = 0;
    }

    /**
     * Set client id
     * @param id client id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set client short name
     * @param shortName client short name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Set client display name
     * @param displayName client display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Set client inn
     * @param inn client inn
     */
    public void setInn(String inn) {
        this.inn = inn;
    }

    /**
     * Set client permission for sending letters
     * @param enable - true if client is enable to send mails, false otherwise
     */
    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    /**
     * Set client process settings
     * @param processClientSettings Process client settings
     */
    public void setProcessClientSettings(ProcessClientSettings processClientSettings) {
        this.processClientSettings = processClientSettings;
    }

    /**
     * Set client settings version
     * @param version client settings version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * @return client id
     */
    public int getId() {
        return id;
    }

    /**
     * @return client short name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @return client display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return client inn
     */
    public String getInn() {
        return inn;
    }

    /**
     * Return true if client is enable to send mails,
     * otherwise return false
     * @return true if client is enable to send mails, false otherwise
     */
    public boolean isEnable() {
        return isEnable;
    }

    /**
     * Return processing client settings (e.g. allowed delivery type)
     * @return processSettings
     */
    public ProcessClientSettings getProcessClientSettings() {
        return processClientSettings;
    }

    /**
     * Return client settings version
     * @return client settings version
     */
    public int getVersion() {
        return version;
    }

    /**
     * Override from Object.class()
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "Customer:" + "\n" +
                "customer_ID: " + id + "\n" +
                "short_name:: " + shortName + "\n" +
                "display_name: " + displayName + "\n" +
                "inn: " + inn + "\n" +
                "enabled: " + isEnable + "\n" +
                "processing_settings: " + processClientSettings.getProcessSettings().toString() + "\n" +
                "version:" + version;
    }

    /**
     * Indicates whether some other object is "equal to" this one
     * @param o - the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                isEnable == client.isEnable &&
                version == client.version &&
                shortName.equals(client.shortName) &&
                displayName.equals(client.displayName) &&
                inn.equals(client.inn) &&
                processClientSettings.equals(client.processClientSettings);
    }

    /**
     * Returns a hash code value for the object.
     * This method is supported for the benefit of hash tables such as those provided by HashMap.
     * @return a hash code value for this client
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, shortName, displayName, inn, isEnable, processClientSettings, version);
    }
}
