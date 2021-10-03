package de.gnmyt.autoresponder.event.chat;

import de.gnmyt.autoresponder.SimpleAutoResponder;
import de.gnmyt.autoresponder.event.ResponderEvent;
import de.gnmyt.autoresponder.http.contexts.ResponderContext;
import de.gnmyt.autoresponder.http.controller.HttpResponseController;

public class ChatMessageReceivedEvent extends ResponderEvent {

    private final String sender;
    private final String message;

    /**
     * Constructor of the {@link ChatMessageReceivedEvent}
     *
     * @param responder            The current instance of your {@link SimpleAutoResponder}
     * @param context              The context of the request
     * @param appPackageName       The package name of the responder app
     * @param messengerPackageName The package name of your whatsapp instance
     * @param ruleId               The id of the rule that has been executed
     * @param responseController   The response controller of the executed request
     * @param sender               The sender which sent the message
     * @param message              The message itself
     */
    public ChatMessageReceivedEvent(SimpleAutoResponder responder, ResponderContext context, String appPackageName, String messengerPackageName, int ruleId,
                                    HttpResponseController responseController, String sender, String message) {
        super(responder, context, appPackageName, messengerPackageName, ruleId, responseController);
        this.sender = sender;
        this.message = message;
    }

    /**
     * Gets the sender of the message from the event
     *
     * @return the sender of the message from the event
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gets the message from the event
     *
     * @return the message from the event
     */
    public String getMessage() {
        return message;
    }
}
