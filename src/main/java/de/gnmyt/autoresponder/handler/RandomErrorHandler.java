package de.gnmyt.autoresponder.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomErrorHandler extends NotFoundHandler {

    private final ArrayList<String> messages = new ArrayList<>();

    /**
     * Constructor of the {@link RandomErrorHandler}.
     *
     * It sends a random error message to the author if the message could not be found
     * @param randomErrorMessages The error messages you want to add
     */
    public RandomErrorHandler(String... randomErrorMessages) {
        messages.addAll(Arrays.asList(randomErrorMessages));
    }

    @Override
    public List<String> handleRequest(String sender, String message) {
        Collections.shuffle(messages);
        return Collections.singletonList(messages.get(0));
    }

}
