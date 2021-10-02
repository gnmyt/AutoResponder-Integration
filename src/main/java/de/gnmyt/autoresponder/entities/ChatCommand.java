package de.gnmyt.autoresponder.entities;

import de.gnmyt.autoresponder.event.chat.ChatMessageReceivedEvent;
import de.gnmyt.autoresponder.http.controller.HttpResponseController;

import java.util.function.Consumer;

public class ChatCommand extends Command {

    private final String sender;
    private final String message;

    /**
     * Constructor of the {@link ChatCommand}
     *
     * @param appPackageName       The package name of the responder app
     * @param messengerPackageName The package name of the messenger
     * @param ruleId               The id of the rule
     * @param sender               The sender of the message
     * @param message              The message itself
     */
    public ChatCommand(HttpResponseController controller, String appPackageName, String messengerPackageName, int ruleId, String sender, String message) {
        super(controller, appPackageName, messengerPackageName, ruleId);
        this.sender = sender;
        this.message = message;
    }

    public void awaitAnswer(Consumer<ChatMessageReceivedEvent> then) {
        then.accept(new ChatMessageReceivedEvent(null, null, null, 1, null, null, null));
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
