package de.gnmyt.autoresponder.commands.usage.handler;

import de.gnmyt.autoresponder.commands.usage.UsageException;

import java.util.List;

public abstract class UsageHandler {

    /**
     * Handles a usage exception
     *
     * @param exception The usage exception to handle
     * @return Your replies
     */
    public abstract List<String> handleUsageException(UsageException exception);

}
