package de.gnmyt.autoresponder.http.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.gnmyt.autoresponder.SimpleAutoResponder;
import de.gnmyt.autoresponder.commands.Arguments;
import de.gnmyt.autoresponder.commands.ResponderChatCommand;
import de.gnmyt.autoresponder.commands.ResponderCommand;
import de.gnmyt.autoresponder.commands.ResponderGroupCommand;
import de.gnmyt.autoresponder.commands.annotations.CommandInfo;
import de.gnmyt.autoresponder.commands.usage.UsageElement;
import de.gnmyt.autoresponder.commands.usage.UsageException;
import de.gnmyt.autoresponder.commands.usage.UsageType;
import de.gnmyt.autoresponder.entities.ChatCommand;
import de.gnmyt.autoresponder.entities.GroupCommand;
import de.gnmyt.autoresponder.entities.LockedChannel;
import de.gnmyt.autoresponder.event.ResponderEvent;
import de.gnmyt.autoresponder.event.chat.ChatMessageReceivedEvent;
import de.gnmyt.autoresponder.event.group.GroupMessageReceivedEvent;
import de.gnmyt.autoresponder.http.controller.HttpResponseController;
import de.gnmyt.autoresponder.http.handler.SimpleHttpHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.gnmyt.autoresponder.commands.usage.UsageExceptionType.*;

public class ResponderContext extends SimpleHttpHandler {

    public final ArrayList<LockedChannel> LOCKED_CHANNELS = new ArrayList<>();

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

        ResponderEvent event = getEvent(appPackageName, messengerPackageName, sender, message, isGroup, groupParticipant, ruleId, controller);

        if (executeUnlockAction(event)) return;

        try {
            if (message.startsWith(responder.getPrefix())
                    && runCommand(appPackageName, messengerPackageName, sender, message.substring(responder.getPrefix().length()), isGroup,
                    groupParticipant, ruleId, controller)) return;
        } catch (Exception e) {
            e.printStackTrace();
        }


        triggerEvent(event);

        if (!controller.isResponseSent()) sendNotFoundReply(isGroup ? groupParticipant : sender, message, controller);
    }

    /**
     * Executes the unlock action
     *
     * @param event The event given by the request
     * @return <code>true</code> if the channel was locked, otherwise false
     */
    public boolean executeUnlockAction(ResponderEvent event) {
        for (int i = 0; i < LOCKED_CHANNELS.size(); i++) {
            LockedChannel lockedChannel = LOCKED_CHANNELS.get(i);

            if (!((lockedChannel.isGroup() && event instanceof GroupMessageReceivedEvent)
                    || (!lockedChannel.isGroup() && event instanceof ChatMessageReceivedEvent)))
                continue;

            if (lockedChannel.isGroup()) {
                if (lockedChannel.getGroupName().equals(((GroupMessageReceivedEvent) event).getGroup())
                        && lockedChannel.getSender().equals(((GroupMessageReceivedEvent) event).getSender())) {
                    ((Consumer<GroupMessageReceivedEvent>) lockedChannel.getConsumer()).accept(((GroupMessageReceivedEvent) event));
                    LOCKED_CHANNELS.remove(i);
                    return true;
                }
            } else {
                if (lockedChannel.getSender().equals(((ChatMessageReceivedEvent) event).getSender())) {
                    ((Consumer<ChatMessageReceivedEvent>) lockedChannel.getConsumer()).accept((ChatMessageReceivedEvent) event);
                    LOCKED_CHANNELS.remove(i);
                    return true;
                }
            }

        }
        return false;
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
    public ResponderEvent getEvent(String appPackageName, String messengerPackageName, String sender, String message, boolean isGroup,
                                   String groupParticipant, int ruleId, HttpResponseController controller) {
        ResponderEvent event;

        if (isGroup) {
            event = new GroupMessageReceivedEvent(responder, this, appPackageName, messengerPackageName, ruleId, controller, sender,
                    message, groupParticipant);
        } else {
            event = new ChatMessageReceivedEvent(responder, this, appPackageName, messengerPackageName, ruleId, controller, sender, message);
        }

        return event;
    }

    /**
     * Triggers the provided event
     *
     * @param event The event you want to trigger
     */
    public void triggerEvent(ResponderEvent event) {
        event.call();
    }

    /**
     * Runs the command
     *
     * @param appPackageName       The package name of the responder app
     * @param messengerPackageName The package name of your whatsapp instance
     * @param sender               The sender of the message
     * @param commandMessage       The message without the prefix
     * @param isGroup              <code>true</code> if the provided message has been sent in a group, otherwise <code>false</code>
     * @param groupParticipant     (Optional) The group participant
     * @param ruleId               The id of the rule that has been executed
     * @param controller           The response controller of the executed request
     * @return <code>true</code> if the command was executed, otherwise <code>false</code>
     */
    public boolean runCommand(String appPackageName, String messengerPackageName, String sender, String commandMessage, boolean isGroup,
                              String groupParticipant, int ruleId, HttpResponseController controller) {
        for (ResponderCommand command : responder.getCommands()) {
            CommandInfo info = command.getClass().getAnnotation(CommandInfo.class);

            if ((command instanceof ResponderGroupCommand) && !isGroup || !(command instanceof ResponderGroupCommand) && isGroup)
                continue;

            String foundTrigger = null;
            for (String currentTrigger : info.triggers())
                if (commandMessage.split(" ")[0].equalsIgnoreCase(currentTrigger)) foundTrigger = currentTrigger;

            if (foundTrigger == null) continue;

            Arguments arguments = handleUsage(command, foundTrigger, commandMessage, controller);

            if (arguments == null) return true;

            if (command instanceof ResponderGroupCommand) {
                ((ResponderGroupCommand) command)
                        .execute(new GroupCommand(controller, this, appPackageName, messengerPackageName, ruleId, sender, commandMessage,
                                groupParticipant), arguments);
                return true;
            } else if (command instanceof ResponderChatCommand) {
                ((ResponderChatCommand) command)
                        .execute(new ChatCommand(controller, this, appPackageName, messengerPackageName, ruleId, sender, commandMessage), arguments);
                return true;
            }
        }
        return false;
    }

    /**
     * Handles the usage of a command
     *
     * @param command        The command you want to check
     * @param foundTrigger   The command trigger
     * @param commandMessage The command message
     * @param controller     The {@link HttpResponseController} to reply to possible errors
     * @return the arguments of the usage
     */
    public Arguments handleUsage(ResponderCommand command, String foundTrigger, String commandMessage, HttpResponseController controller) {
        ArrayList<Object> providedUsage;
        HashMap<String, Object> arguments = new HashMap<>();
        if (command.getRequiredElements().size() > 0) {
            try {
                if (commandMessage.length() <= foundTrigger.length() + 1)
                    throw new UsageException(USAGE_NOT_COMPLETE, "The provided usage is not complete", null);

                providedUsage = validateUsage(command, commandMessage.substring(foundTrigger.length() + 1));
            } catch (UsageException e) {
                sendUsageErrorReply(e, controller);
                return null;
            }

            for (int i = 0; i < providedUsage.size(); i++) {
                arguments.put(command.getUsageElements().get(i).getName(), providedUsage.get(i));
            }
        }

        return new Arguments(arguments);
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
                } else if (usageElement.getType() == UsageType.BOOLEAN) {
                    object = object.equals("yes") ? "true" : object;
                    object = object.equals("no") ? "false" : object;

                    if (object.equals("true") || object.equals("false")) {
                        createdUsage.set(i, Boolean.parseBoolean(object.toString()));
                    } else
                        throw new Exception("Could not parse the boolean value");
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
