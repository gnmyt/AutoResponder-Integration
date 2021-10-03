package de.gnmyt.autoresponder.http.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.gnmyt.autoresponder.SimpleAutoResponder;
import de.gnmyt.autoresponder.commands.ResponderCommand;
import de.gnmyt.autoresponder.commands.usage.UsageElement;
import de.gnmyt.autoresponder.commands.usage.UsageException;
import de.gnmyt.autoresponder.commands.usage.UsageType;
import de.gnmyt.autoresponder.event.chat.ChatMessageReceivedEvent;
import de.gnmyt.autoresponder.event.group.GroupMessageReceivedEvent;
import de.gnmyt.autoresponder.http.controller.HttpResponseController;
import de.gnmyt.autoresponder.http.handler.SimpleHttpHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.gnmyt.autoresponder.commands.usage.UsageExceptionType.*;

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
     * Gets the clean usage.
     * <p>
     * That means that the optimized usage automatically removes the quotation marks from the usage.
     * It also moves the last provided usage elements to the needed usage element
     *
     * @param usageParts    Your current usage
     * @param usageElements The list of all usage elements needed to run this command
     * @return the clean usage
     */
    public ArrayList<Object> getOptimizedUsage(ArrayList<Object> usageParts, ArrayList<UsageElement> usageElements) {

        for (int i = usageParts.size() - 1; i >= usageElements.size(); i--) {
            usageParts.set(i - 1, usageParts.get(i - 1) + " " + usageParts.get(i));
            usageParts.remove(i);
        }

        for (int i = 0; i < usageParts.size(); i++) {
            if (usageParts.get(i).toString().startsWith("\""))
                usageParts.set(i, usageParts.get(i).toString().substring(1));
            if (usageParts.get(i).toString().endsWith("\""))
                usageParts.set(i, usageParts.get(i).toString().substring(0, usageParts.get(i).toString().length() - 1));
        }

        return usageParts;
    }

    /**
     * Validates the usage of a command & returns the optimized elements
     *
     * @param command The command that has been requested
     * @param usage   The usage that has been given to the command
     * @return the created usage
     * @throws UsageException Throws whenever there was an error in the usage
     */
    public ArrayList<Object> validateUsage(ResponderCommand command, String usage) throws UsageException {
        ArrayList<Object> usageParts = getUsageParts(usage);

        if (command.getRequiredElements().size() > usageParts.size())
            throw new UsageException(USAGE_NOT_COMPLETE, "The provided usage is not complete", null);

        ArrayList<Object> createdUsage = getOptimizedUsage(usageParts, command.getUsageElements());

        for (int i = 0; i < command.getUsageElements().size(); i++) {
            UsageElement usageElement = command.getUsageElements().get(i);
            if (createdUsage.size() <= i) continue;

            Object object = createdUsage.get(i);

            if (usageElement.getMinLength() != -1 && object.toString().length() < usageElement.getMinLength())
                throw new UsageException(BELOW_MIN_LENGTH, String.format("The length of the element %s should have %s chars",
                        usageElement.getName(), usageElement.getMinLength()), usageElement.getName());

            if (usageElement.getMaxLength() != -1 && object.toString().length() > usageElement.getMaxLength())
                throw new UsageException(MAX_LENGTH_EXCEEDED, String.format("The element %s should not have more than %s chars",
                        usageElement.getName(), usageElement.getMaxLength()), usageElement.getName());

            if (usageElement.getAllowedValues().length > 0)
                if (!Arrays.asList(usageElement.getAllowedValues()).contains(object))
                    throw new UsageException(NOT_IN_ALLOWED_VALUES, String.format("The element %s is not allowed to contain %s",
                            usageElement.getName(), object), usageElement.getName());

            try {
                if (usageElement.getType() == UsageType.INTEGER) {
                    createdUsage.set(i, Integer.parseInt(object.toString()));
                } else if (usageElement.getType() == UsageType.BOOLEAN && object.equals("true") || object.equals("false")) {
                    throw new Exception("Could not parse the integer value");
                }
            } catch (Exception e) {
                throw new UsageException(USAGE_TYPE_MISMATCH, String.format("The usage element %s must have the type %s", usageElement.getName(),
                        usageElement.getType()), usageElement.getName());
            }
        }

        return createdUsage;
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
