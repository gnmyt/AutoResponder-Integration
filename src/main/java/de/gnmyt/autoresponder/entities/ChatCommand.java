package de.gnmyt.autoresponder.entities;

import de.gnmyt.autoresponder.event.chat.ChatMessageReceivedEvent;
import de.gnmyt.autoresponder.http.contexts.ResponderContext;
import de.gnmyt.autoresponder.http.controller.HttpResponseController;

import java.util.function.Consumer;

public class ChatCommand extends Command {

    private final String sender;
    private final String message;

    /**
     * Constructor of the {@link ChatCommand}
     *
     * @param controller           The response controller
     * @param context              The responder context
     * @param appPackageName       The package name of the responder app
     * @param messengerPackageName The package name of the messenger
     * @param ruleId               The id of the rule
     * @param sender               The sender of the message
     * @param message              The message itself
     */
    public ChatCommand(HttpResponseController controller, ResponderContext context, String appPackageName, String messengerPackageName, int ruleId, String sender, String message) {
        super(controller, context, appPackageName, messengerPackageName, ruleId);
        this.sender = sender;
        this.message = message;
    }

    /**
     * Waits until a new message has been sent
     *
     * @param then The action that should be executed after the message has been sent
     */
    public void awaitAnswer(Consumer<ChatMessageReceivedEvent> then) {
        getResponderContext().LOCKED_CHANNELS.add(new LockedChannel(null, false, getSender(), then));
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
