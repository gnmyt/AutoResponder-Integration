package de.gnmyt.autoresponder.commands.usage;

public class UsageElement {

    private String name;
    private int length;
    private boolean required;
    private UsageType type;
    private String description;
    private Object[] allowedValues;

    /**
     * The full constructor with all prefilled values
     *
     * @param name          The name of the usage element
     * @param length        The length of the usage element
     * @param required      <code>true</code> if the usage element is required, otherwise <code>false</code>
     * @param type          The type of the usage element
     * @param description   The description of the usage element
     * @param allowedValues All allowed values of the usage element
     */
    public UsageElement(String name, int length, boolean required, UsageType type, String description, Object[] allowedValues) {
        this.name = name;
        this.length = length;
        this.required = required;
        this.type = type;
        this.description = description;
        this.allowedValues = allowedValues;
    }

    /**
     * Constructor without any prefilled values
     */
    public UsageElement() {

    }

    /**
     * Gets the name of the usage element
     *
     * @return the name of the usage element
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the usage element
     *
     * @param name The new name you want to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the length of the usage element
     *
     * @return the length of the usage element
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the length of the usage element
     *
     * @param length The new length of the usage element
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Checks if the usage element is required
     *
     * @return <code>true</code> if the usage element is required, otherwise <code>false</code>
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Sets the usage element required
     *
     * @param required <code>true</code> if the element should be required, otherwise <code>false</code>
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * Gets the type of the usage element
     *
     * @return the type of the usage element
     */
    public UsageType getType() {
        return type;
    }

    /**
     * Sets the type of the usage element
     *
     * @param type The new type of the usage element
     */
    public void setType(UsageType type) {
        this.type = type;
    }

    /**
     * Gets the description of the usage element
     *
     * @return the description of the usage element
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the usage element
     *
     * @param description The new description of the usage element
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets all allowed values of the usage element
     *
     * @return all allowed values of the usage element
     */
    public Object[] getAllowedValues() {
        return allowedValues;
    }

    /**
     * Sets all allowed values of the usage element
     *
     * @param allowedValues The new allowed values of the usage element
     */
    public void setAllowedValues(Object[] allowedValues) {
        this.allowedValues = allowedValues;
    }
}
