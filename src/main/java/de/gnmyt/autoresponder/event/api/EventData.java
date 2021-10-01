package de.gnmyt.autoresponder.event.api;

import java.lang.reflect.Method;

public class EventData {

    public final Object object;
    public final Method method;

    /**
     * Constructor of the {@link EventData}
     *
     * @param object The registered event object
     * @param method The registered event method
     */
    public EventData(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

}
