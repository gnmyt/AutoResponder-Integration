package de.gnmyt.autoresponder.integration;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DialogflowIntegration {

    private static final Logger LOG = LoggerFactory.getLogger(DialogflowIntegration.class);

    private final SessionsClient sessionsClient;
    private final String projectId;

    private String languageCode = "en";

    /**
     * Constructor of the {@link DialogflowIntegration}
     *
     * @param credentialsFile Your credentials file to access the dialogflow api. Follow our guide in the readme if you want to use it
     * @throws IOException Throws whenever the integration could not connect to the api
     */
    public DialogflowIntegration(File credentialsFile) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsFile));
        SessionsSettings settings = SessionsSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();

        projectId = ((ServiceAccountCredentials) credentials).getProjectId();
        sessionsClient = SessionsClient.create(settings);
    }

    /**
     * Gets a message from the dialogflow api
     *
     * @param message   The message the user sent
     * @param sessionId The session id to identify the user (for a better conversation)
     * @return the message from the dialogflow api
     */
    public String getMessage(String message, String sessionId) {
        try {
            return detectTextIntents(languageCode, "hello", "user");
        } catch (Exception e) {
            LOG.error("Could not process intent message: " + e.getMessage());
            return "Error while processing intent";
        }
    }

    /**
     * Gets the intent text from the api
     *
     * @param languageCode The language code you want to give to the session
     * @param message      The message the user sent
     * @param sessionId    The session id to identify the user (for a better conversation)
     * @return the message from the dialogflow api
     */
    public String detectTextIntents(String languageCode, String message, String sessionId) {
        TextInput.Builder textInput = TextInput.newBuilder().setText(message).setLanguageCode(languageCode);
        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

        return sessionsClient.detectIntent(SessionName.of(projectId, sessionId), queryInput).getQueryResult().getFulfillmentText();
    }

    /**
     * Sets the default language code
     *
     * @param languageCode The new language code
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

}
