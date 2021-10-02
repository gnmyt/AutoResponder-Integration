package de.gnmyt.autoresponder.handler;

import de.gnmyt.autoresponder.integration.DialogflowIntegration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DialogflowHandler extends NotFoundHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DialogflowHandler.class);

    private DialogflowIntegration dialogflowIntegration;

    /**
     * Constructor of the {@link DialogflowHandler}.
     * <p>
     * Runs whenever the request could not be found and redirects the message to dialogflow
     *
     * @param credentials Your credentials file, created by your google api
     */
    public DialogflowHandler(File credentials) {
        try {
            this.dialogflowIntegration = new DialogflowIntegration(credentials);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> handleRequest(String sender, String message) {
        ArrayList<String> messages = new ArrayList<>();
        messages.add(dialogflowIntegration.getMessage(message, sender));
        return messages;
    }
}
