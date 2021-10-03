package de.gnmyt.autoresponder.commands.usage.handler;

import de.gnmyt.autoresponder.commands.usage.UsageException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleUsageErrorHandler extends UsageHandler {

    @Override
    public List<String> handleUsageException(UsageException exception) {
        return new ArrayList<>(Collections.singletonList(exception.getMessage()));
    }

}
