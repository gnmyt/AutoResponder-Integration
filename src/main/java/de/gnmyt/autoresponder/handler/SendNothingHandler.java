package de.gnmyt.autoresponder.handler;

import java.util.ArrayList;
import java.util.List;

public class SendNothingHandler extends NotFoundHandler {

    @Override
    public List<String> handleRequest(String sender, String message) {
        return new ArrayList<>();
    }
}
