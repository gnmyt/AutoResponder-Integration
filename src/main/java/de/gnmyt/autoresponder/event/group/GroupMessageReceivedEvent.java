package de.gnmyt.autoresponder.event.group;

import de.gnmyt.autoresponder.SimpleAutoResponder;
import de.gnmyt.autoresponder.event.ResponderEvent;
import de.gnmyt.autoresponder.http.contexts.ResponderContext;
import de.gnmyt.autoresponder.http.controller.HttpResponseController;

public class GroupMessageReceivedEvent extends ResponderEvent {

    private final String group;
    private final String message;
    private final String sender;

    /**
     * @param responder            The current instance of your {@link SimpleAutoResponder}
     * @param context              The context of the request
     * @param appPackageName       The package name of the responder app
     * @param messengerPackageName The package name of your whatsapp instance
     * @param ruleId               The id of the rule that has been executed
     * @param responseController   The response controller of the executed request
     * @param group                The group in which the message has been sent
     * @param message              The message itself
     * @param sender               The sender which sent the message
     */
    public GroupMessageReceivedEvent(SimpleAutoResponder responder, ResponderContext context, String appPackageName, String messengerPackageName, int ruleId,
                                     HttpResponseController responseController, String group, String message, String sender) {
        super(responder, context, appPackageName, messengerPackageName, ruleId, responseController);
        this.group = group;
        this.message = message;
        this.sender = sender;
    }

    /**
     * Gets the group from the event
     *
     * @return the group from the event
     */
    public String getGroup() {
        return group;
    }

    /**
     * Gets the message from the event
     *
     * @return the message from the event
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the sender from the event
     *
     * @return the sender from the event
     */
    public String getSender() {
        return sender;
    }
}
