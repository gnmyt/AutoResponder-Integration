package de.gnmyt.autoresponder.entities;

public class ChatCommandData extends CommandData {

    private final String sender;
    private final String message;

    /**
     * Constructor of the {@link ChatCommandData}
     *
     * @param appPackageName       The package name of the responder app
     * @param messengerPackageName The package name of the messenger
     * @param ruleId               The id of the rule
     * @param sender               The sender of the message
     * @param message              The message itself
     */
    public ChatCommandData(String appPackageName, String messengerPackageName, int ruleId, String sender, String message) {
        super(appPackageName, messengerPackageName, ruleId);
        this.sender = sender;
        this.message = message;
    }

    /**
     * Gets the sender
     *
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gets the message
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
