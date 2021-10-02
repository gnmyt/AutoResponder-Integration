package de.gnmyt.autoresponder.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.gnmyt.autoresponder.SimpleAutoResponder;
import de.gnmyt.autoresponder.event.api.EventData;
import de.gnmyt.autoresponder.http.controller.HttpResponseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ResponderEvent {

    private static final Logger LOG = LoggerFactory.getLogger(ResponderEvent.class);

    private final SimpleAutoResponder responder;

    private final String appPackageName;
    private final String messengerPackageName;

    private final HttpResponseController responseController;

    private final int ruleId;

    /**
     * Constructor of the {@link ResponderEvent}
     *
     * @param responder            The current instance of your {@link SimpleAutoResponder}
     * @param appPackageName       The package name of the responder app
     * @param messengerPackageName The package name of your whatsapp instance
     * @param ruleId               The id of the rule that has been executed
     * @param responseController   The response controller of the executed request
     */
    public ResponderEvent(SimpleAutoResponder responder, String appPackageName, String messengerPackageName, int ruleId, HttpResponseController responseController) {
        this.responder = responder;
        this.appPackageName = appPackageName;
        this.messengerPackageName = messengerPackageName;
        this.ruleId = ruleId;
        this.responseController = responseController;
    }

    /**
     * Calls all registered event listeners
     */
    public void call() {
        ArrayList<EventData> eventList = responder.getEventManager().getEvents(this.getClass());

        if (eventList == null) return;

        for (EventData data : eventList) {
            try {
                data.method.invoke(data.object, this);
            } catch (Exception e) {
                LOG.error("Could not execute event: {}", e.getMessage());
            }
        }
    }

    /**
     * Replies to the event
     * @param messages The messages you want to send
     */
    public void reply(String... messages) {
        responseController.reply(messages);
    }

    /**
     * Gets the app package name of the responder
     *
     * @return the app package name of the responder
     */
    public String getAppPackageName() {
        return appPackageName;
    }

    /**
     * Gets the package name of your whatsapp instance
     *
     * @return the package name of your whatsapp instance
     */
    public String getMessengerPackageName() {
        return messengerPackageName;
    }

    /**
     * Gets the id of the rule provided by the responder
     *
     * @return the id of the rule provided by the responder
     */
    public int getRuleId() {
        return ruleId;
    }
}
