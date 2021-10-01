package de.gnmyt.autoresponder.event.api;

import de.gnmyt.autoresponder.event.ResponderEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class EventManager {

    private final HashMap<Class<? extends ResponderEvent>, ArrayList<EventData>> REGISTERED_EVENTS = new HashMap<>();

    /**
     * Checks if a method is a valid event method
     *
     * @param method The method you want to check
     * @return <code>true</code> if the provided method is valid, otherwise <code>false</code>
     */
    private boolean isValid(Method method) {
        return method.getParameterTypes().length == 1 || method.isAnnotationPresent(EventListener.class);
    }

    /**
     * Adds a new listener to the list
     *
     * @param listener The listener you want to add
     */
    public void addEventListener(Listener listener) {
        for (Method method : listener.getClass().getMethods()) if (isValid(method)) register(method, listener);
    }

    /**
     * Removes a listener from the list
     *
     * @param listener The listener you want to remove
     */
    public void removeEventListener(Listener listener) {
        for (ArrayList<EventData> registeredEvents : REGISTERED_EVENTS.values())
            for (int i = registeredEvents.size() - 1; i <= 0; i++)
                if (registeredEvents.get(i).object.equals(listener)) registeredEvents.remove(i);
    }

    /**
     * Registers a method into the <code>REGISTERED_EVENTS</code> list
     *
     * @param method   The method you want to register
     * @param listener The listener you want to register
     */
    public void register(Method method, Listener listener) {
        Class<?> clazz = method.getParameterTypes()[0];
        EventData methodData = new EventData(listener, method);
        if (!methodData.method.isAccessible()) methodData.method.setAccessible(true);

        if (REGISTERED_EVENTS.containsKey(clazz)) {
            if (!REGISTERED_EVENTS.get(clazz).contains(methodData)) REGISTERED_EVENTS.get(clazz).add(methodData);
        } else {
            REGISTERED_EVENTS.put((Class<? extends ResponderEvent>) clazz, new ArrayList<EventData>() {{
                this.add(methodData);
            }});
        }
    }

    /**
     * Gets all registered event listeners by an event
     *
     * @param clazz The event you want to get the event listeners from
     * @return all listeners from the event
     */
    public ArrayList<EventData> getEvents(final Class<? extends ResponderEvent> clazz) {
        return REGISTERED_EVENTS.get(clazz);
    }

}
