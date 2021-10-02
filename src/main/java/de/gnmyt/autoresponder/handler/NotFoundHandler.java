package de.gnmyt.autoresponder.handler;

import java.util.List;

public abstract class NotFoundHandler {

    /**
     * Handles the specific request whenever the message could not be found
     *
     * @param sender  The sender of the message / the creator of the request
     * @param message The message that the author sent
     * @return the list of messages that you want to reply
     */
    public abstract List<String> handleRequest(String sender, String message);

}
