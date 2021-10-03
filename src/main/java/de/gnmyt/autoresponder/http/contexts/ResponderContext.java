package de.gnmyt.autoresponder.http.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.gnmyt.autoresponder.SimpleAutoResponder;
import de.gnmyt.autoresponder.commands.usage.UsageException;
import de.gnmyt.autoresponder.event.chat.ChatMessageReceivedEvent;
import de.gnmyt.autoresponder.event.group.GroupMessageReceivedEvent;
import de.gnmyt.autoresponder.http.controller.HttpResponseController;
import de.gnmyt.autoresponder.http.handler.SimpleHttpHandler;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResponderContext extends SimpleHttpHandler {

    private final ObjectMapper mapper = new ObjectMapper();
    private final SimpleAutoResponder responder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Constructor of the {@link ResponderContext}
     *
     * @param responder Your current {@link SimpleAutoResponder} instance
     */
    public ResponderContext(SimpleAutoResponder responder) {
        this.responder = responder;
    }

    @Override
    public void execute(String body, HttpResponseController controller) throws Exception {
        JsonNode rootNode = mapper.readTree(body);

        JsonNode query = rootNode.get("query");

        CompletableFuture.runAsync(() -> run(rootNode.get("appPackageName").asText(), rootNode.get("messengerPackageName").asText(),
                query.get("sender").asText(), query.get("message").asText(), query.get("isGroup").asBoolean(),
                query.get("groupParticipant").asText(), query.get("ruleId").asInt(), controller));
    }

    /**
     * Runs the context with all variables
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
    public void run(String appPackageName, String messengerPackageName, String sender, String message, boolean isGroup,
                    String groupParticipant, int ruleId, HttpResponseController controller) {

        triggerEvent(appPackageName, messengerPackageName, sender, message, isGroup, groupParticipant, ruleId, controller);

        if (!controller.isResponseSent()) sendNotFoundReply(isGroup ? groupParticipant : sender, message, controller);
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

    /**
     * Gets the full message in parts (splits with spaces and quotation marks)
     *
     * @param input The message input
     * @return the full message in parts
     */
    public ArrayList<Object> getUsageParts(String input) {
        Matcher argumentMatcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);

        ArrayList<Object> arguments = new ArrayList<>();

        while (argumentMatcher.find()) {
            arguments.add(argumentMatcher.group(1));
        }

        return arguments;
    }

    /**
     * Sends the usage error reply
     *
     * @param exception  Your usage exception created from the command
     * @param controller The instance of the {@link HttpResponseController} to reply to the message
     */
    public void sendUsageErrorReply(UsageException exception, HttpResponseController controller) {
        ObjectNode object = objectMapper.createObjectNode();
        ArrayNode replies = object.withArray("replies");

        responder.getUsageHandler().handleUsageException(exception).forEach(currentMessage ->
                replies.add(mapper.createObjectNode().put("message", currentMessage)));

        controller.text(object.toString());
    }

    /**
     * Sends the "not found"-reply
     *
     * @param sender     The sender of the message
     * @param message    The message itself
     * @param controller The response controller from the request
     */
    public void sendNotFoundReply(String sender, String message, HttpResponseController controller) {
        ObjectNode object = objectMapper.createObjectNode();
        ArrayNode replies = object.withArray("replies");

        responder.getNotFoundHandler().handleRequest(sender, message).forEach(currentMessage ->
                replies.add(mapper.createObjectNode().put("message", currentMessage)));

        controller.text(object.toString());
    }
}
