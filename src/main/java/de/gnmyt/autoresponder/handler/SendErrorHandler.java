package de.gnmyt.autoresponder.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SendErrorHandler extends NotFoundHandler {

    private final ArrayList<String> messages = new ArrayList<>();

    /**
     * Constructor of the {@link SendErrorHandler}.
     *
     * It sends error message sto the author if the message could not be found
     * @param errorMessages The error messages you want to send
     */
    public SendErrorHandler(String... errorMessages) {
        messages.addAll(Arrays.asList(errorMessages));
    }

    @Override
    public List<String> handleRequest(String sender, String message) {
        return messages;
    }
}
