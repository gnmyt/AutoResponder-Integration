package de.gnmyt.autoresponder.entities;

import de.gnmyt.autoresponder.http.contexts.ResponderContext;
import de.gnmyt.autoresponder.http.controller.HttpResponseController;

public class GroupCommand extends Command {

    private final String group;
    private final String message;
    private final String sender;

    /**
     * Constructor of the {@link GroupCommand}
     *
     * @param controller           The response controller
     * @param context              The responder context
     * @param appPackageName       The package name of the responder app
     * @param messengerPackageName The package name of the messenger
     * @param ruleId               The id of the rule
     * @param group                The name of the group in which the message has been sent
     * @param message              The message itself
     * @param sender               The sender that sent the message
     */
    public GroupCommand(HttpResponseController controller, ResponderContext context, String appPackageName, String messengerPackageName, int ruleId, String group, String message, String sender) {
        super(controller, context, appPackageName, messengerPackageName, ruleId);
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
