package de.gnmyt.autoresponder.entities;

public class CommandData {

    private final String appPackageName;
    private final String messengerPackageName;

    private final int ruleId;

    /**
     * Constructor of the {@link CommandData}
     *
     * @param appPackageName       The package name of the responder app
     * @param messengerPackageName The package name of the messenger
     * @param ruleId               The id of the rule
     */
    public CommandData(String appPackageName, String messengerPackageName, int ruleId) {
        this.appPackageName = appPackageName;
        this.messengerPackageName = messengerPackageName;
        this.ruleId = ruleId;
    }

    /**
     * Gets the app package name
     *
     * @return the app package name
     */
    public String getAppPackageName() {
        return appPackageName;
    }

    /**
     * Gets the messenger package name
     *
     * @return the messenger package name
     */
    public String getMessengerPackageName() {
        return messengerPackageName;
    }

    /**
     * Gets the rule id
     *
     * @return the rule id
     */
    public int getRuleId() {
        return ruleId;
    }
}
