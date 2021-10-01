package de.gnmyt.autoresponder.commands;

import de.gnmyt.autoresponder.commands.usage.SimpleUsageBuilder;
import de.gnmyt.autoresponder.commands.usage.UsageElement;
import de.gnmyt.autoresponder.commands.usage.UsageType;

import java.util.ArrayList;

public abstract class ResponderCommand {

    private final ArrayList<UsageElement> usageElements = new ArrayList<>();

    public void usage() {
        // The usage of the command
    }

    public SimpleUsageBuilder addUsage(UsageType type, String name) {
        return new SimpleUsageBuilder(this).type(type).name(name);
    }

    public SimpleUsageBuilder addUsage(String name) {
        return new SimpleUsageBuilder(this).name(name);
    }

    public void addUsageElement(UsageElement usageElement) {
        usageElements.add(usageElement);
    }


}
