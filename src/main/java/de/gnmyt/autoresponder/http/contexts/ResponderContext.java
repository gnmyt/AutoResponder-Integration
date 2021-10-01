package de.gnmyt.autoresponder.http.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.gnmyt.autoresponder.SimpleAutoResponder;
import de.gnmyt.autoresponder.event.chat.ChatMessageReceivedEvent;
import de.gnmyt.autoresponder.event.group.GroupMessageReceivedEvent;
import de.gnmyt.autoresponder.http.controller.HttpResponseController;
import de.gnmyt.autoresponder.http.handler.SimpleHttpHandler;

public class ResponderContext extends SimpleHttpHandler {

    private final ObjectMapper mapper = new ObjectMapper();
    private final SimpleAutoResponder responder;

    /**
     * Constructor of the {@link ResponderContext}
     * @param responder Your current {@link SimpleAutoResponder} instance
     */
    public ResponderContext(SimpleAutoResponder responder) {
        this.responder = responder;
    }

    @Override
    public void execute(String body, HttpResponseController controller) throws Exception {
        JsonNode rootNode = mapper.readTree(body);

        JsonNode query = rootNode.get("query");

        triggerEvent(rootNode.get("appPackageName").asText(), rootNode.get("messengerPackageName").asText(),
                query.get("sender").asText(), query.get("message").asText(), query.get("isGroup").asBoolean(),
                query.get("groupParticipant").asText(), query.get("ruleId").asInt(), controller);
    }

    /**
     * Triggers the correct event
     *
     * @param appPackageName       The package name of the responder app
     * @param messengerPackageName The package name of your whatsapp instance
     * @param sender               The sender of the message
     * @param message              The message itself
     * @param isGroup              <code>true</code> if the provided message has been sent in a group, otherwise <code>false</code>
     * @param groupParticipant     (Optional) The group participant
     * @param ruleId               The id of the rule that has been executed
     * @param controller           The response controller of the executed request
     */
    public void triggerEvent(String appPackageName, String messengerPackageName, String sender, String message, boolean isGroup,
                             String groupParticipant, int ruleId, HttpResponseController controller) {
        if (isGroup) {
            new GroupMessageReceivedEvent(responder, appPackageName, messengerPackageName, ruleId, controller, sender,
                    message, groupParticipant).call();
        } else {
            new ChatMessageReceivedEvent(responder, appPackageName, messengerPackageName, ruleId, controller, sender, message).call();
        }
    }
}
