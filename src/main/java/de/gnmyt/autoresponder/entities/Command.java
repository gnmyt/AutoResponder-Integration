package de.gnmyt.autoresponder.entities;

import de.gnmyt.autoresponder.http.contexts.ResponderContext;
import de.gnmyt.autoresponder.http.controller.HttpResponseController;

public class Command {

    private final HttpResponseController responseController;
    private final ResponderContext responderContext;

    private final String appPackageName;
    private final String messengerPackageName;

    private final int ruleId;

    /**
     * Constructor of the {@link Command}
     *
     * @param responseController   The response controller
     * @param responderContext     The responder context
     * @param appPackageName       The package name of the responder app
     * @param messengerPackageName The package name of the messenger
     * @param ruleId               The id of the rule
     */
    public Command(HttpResponseController responseController, ResponderContext responderContext, String appPackageName, String messengerPackageName, int ruleId) {
        this.responseController = responseController;
        this.responderContext = responderContext;
        this.appPackageName = appPackageName;
        this.messengerPackageName = messengerPackageName;
        this.ruleId = ruleId;
    }

    public void reply(String... messages) {
        responseController.reply(messages);
    }

    /**
     * Gets the app package name
     *
     * @return the app package name
     */
    public String getAppPackageName() {
        return appPackageName;
    }

    /**
     * Gets the messenger package name
     *
     * @return the messenger package name
     */
    public String getMessengerPackageName() {
        return messengerPackageName;
    }

    /**
     * Gets the responder context
     *
     * @return the responder context
     */
    protected ResponderContext getResponderContext() {
        return responderContext;
    }

    /**
     * Gets the rule id
     *
     * @return the rule id
     */
    public int getRuleId() {
        return ruleId;
    }
}
