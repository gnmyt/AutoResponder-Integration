package de.gnmyt.autoresponder.entities;

import de.gnmyt.autoresponder.event.ResponderEvent;

import java.util.function.Consumer;

public class LockedChannel {

    private final String groupName;
    private final boolean isGroup;
    private final String sender;
    private final Consumer<? extends ResponderEvent> consumer;

    /**
     * Constructor of the {@link LockedChannel}
     *
     * @param groupName The name of the group that sent the message
     * @param isGroup   <code>true</code> if the provided channel is a group, otherwise <code>false</code>
     * @param sender    The sender of the message
     * @param consumer  The action that should be executed after the message has been answered
     */
    public LockedChannel(String groupName, boolean isGroup, String sender, Consumer<? extends ResponderEvent> consumer) {
        this.consumer = consumer;
        this.groupName = groupName;
        this.isGroup = isGroup;
        this.sender = sender;
    }

    /**
     * Gets the name of the group
     *
     * @return the name of the group
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Checks if the provided channel is a group
     *
     * @return <code>true</code> if the provided channel is a group, otherwise <code>false</code>
     */
    public boolean isGroup() {
        return isGroup;
    }

    /**
     * Gets the sender
     *
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gets the consumer
     *
     * @return the consumer
     */
    public Consumer<? extends ResponderEvent> getConsumer() {
        return consumer;
    }
}
