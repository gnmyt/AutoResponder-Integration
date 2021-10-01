package de.gnmyt.autoresponder.entities;

public class GroupCommandData extends CommandData {

    private final String group;
    private final String message;
    private final String sender;

    /**
     * Constructor of the {@link GroupCommandData}
     *
     * @param appPackageName       The package name of the responder app
     * @param messengerPackageName The package name of the messenger
     * @param ruleId               The id of the rule
     * @param group                The name of the group in which the message has been sent
     * @param message              The message itself
     * @param sender               The sender that sent the message
     */
    public GroupCommandData(String appPackageName, String messengerPackageName, int ruleId, String group, String message, String sender) {
        super(appPackageName, messengerPackageName, ruleId);
        this.group = group;
        this.message = message;
        this.sender = sender;
    }

    /**
     * Gets the name of the group
     *
     * @return the name of the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * Gets the message
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the sender of the message
     *
     * @return the sender of the message
     */
    public String getSender() {
        return sender;
    }
}
