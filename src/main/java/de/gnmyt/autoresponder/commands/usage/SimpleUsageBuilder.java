package de.gnmyt.autoresponder.commands.usage;

import de.gnmyt.autoresponder.commands.ResponderCommand;

public class SimpleUsageBuilder {

    private final UsageElement usageElement = new UsageElement();

    private final ResponderCommand responderCommand;

    /**
     * Constructor of the {@link SimpleUsageBuilder}
     *
     * @param responderCommand The created {@link ResponderCommand} to add the created usage element
     */
    public SimpleUsageBuilder(ResponderCommand responderCommand) {
        this.responderCommand = responderCommand;
    }

    /**
     * Sets the name of the usage element
     *
     * @param name The new name of the usage element
     * @return the current {@link SimpleUsageBuilder} instance
     */
    public SimpleUsageBuilder name(String name) {
        usageElement.setName(name);
        return this;
    }

    /**
     * Sets the length of the usage element
     *
     * @param length The new length of the usage element
     * @return the current {@link SimpleUsageBuilder} instance
     */
    public SimpleUsageBuilder length(int length) {
        usageElement.setLength(length);
        return this;
    }

    /**
     * Sets the required-state of the usage element
     *
     * @param required The new required state of the usage element
     *                 <code>true</code> if the provided usage element should be required, otherwise <code>false</code>
     * @return the current {@link SimpleUsageBuilder} instance
     */
    public SimpleUsageBuilder required(boolean required) {
        usageElement.setRequired(required);
        return this;
    }

    /**
     * Sets the type of the usage element
     *
     * @param type The new type of the usage element
     * @return the current {@link SimpleUsageBuilder} instance
     */
    public SimpleUsageBuilder type(UsageType type) {
        usageElement.setType(type);
        return this;
    }

    /**
     * Sets the description of the usage element
     *
     * @param description The new description of the usage element
     * @return the current {@link SimpleUsageBuilder} instance
     */
    public SimpleUsageBuilder description(String description) {
        usageElement.setDescription(description);
        return this;
    }

    /**
     * Sets the allowed values of the usage element
     *
     * @param values The new allowed values of the usage element
     * @return the current {@link SimpleUsageBuilder} instance
     */
    public SimpleUsageBuilder allowedValues(Object... values) {
        usageElement.setAllowedValues(values);
        return this;
    }

    /**
     * Adds the provided usage element to the list of the {@link ResponderCommand}
     */
    public void add() {
        responderCommand.addUsageElement(usageElement);
    }

}
