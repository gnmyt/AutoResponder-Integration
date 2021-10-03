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

    /**
     * Creates a new {@link SimpleUsageBuilder}, which adds a new usage element
     *
     * @param type The type of the usage element
     * @param name The name of the usage element
     * @return the created {@link SimpleUsageBuilder}
     */
    public SimpleUsageBuilder addUsage(UsageType type, String name) {
        return new SimpleUsageBuilder(this).type(type).name(name);
    }

    /**
     * Creates a new {@link SimpleUsageBuilder}, which adds a new usage element
     *
     * @param name The name of the usage element
     * @return the created {@link SimpleUsageBuilder}
     */
    public SimpleUsageBuilder addUsage(String name) {
        return new SimpleUsageBuilder(this).name(name);
    }

    /**
     * Adds a new usage element to the list
     *
     * @param usageElement The usage element you want to add
     */
    public void addUsageElement(UsageElement usageElement) {
        usageElements.add(usageElement);
    }

    /**
     * Gets all usage elements
     *
     * @return all usage elements
     */
    public ArrayList<UsageElement> getUsageElements() {
        return usageElements;
    }

    /**
     * Gets all required elements
     *
     * @return all required elements
     */
    public ArrayList<UsageElement> getRequiredElements() {
        ArrayList<UsageElement> requiredUsageElements = new ArrayList<>();
        for (UsageElement usageElement : usageElements)
            if (usageElement.isRequired()) requiredUsageElements.add(usageElement);
        return requiredUsageElements;
    }
}
