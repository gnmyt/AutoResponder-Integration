package de.gnmyt.autoresponder.commands.usage;

public class UsageElement {

    private String name;
    private int minLength = -1;
    private int maxLength = -1;
    private boolean required = true;
    private UsageType type = UsageType.STRING;
    private String description;
    private Object[] allowedValues = new Object[0];

    /**
     * The full constructor with all prefilled values
     *
     * @param name          The name of the usage element
     * @param minLength     The min length of the usage element
     * @param maxLength     The max length of the usage element
     * @param required      <code>true</code> if the usage element is required, otherwise <code>false</code>
     * @param type          The type of the usage element
     * @param description   The description of the usage element
     * @param allowedValues All allowed values of the usage element
     */
    public UsageElement(String name, int minLength, int maxLength, boolean required, UsageType type, String description, Object[] allowedValues) {
        this.name = name;
        this.minLength = minLength;
        this.maxLength = maxLength;
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
     * Gets the minimal length of the usage element
     *
     * @return the minimal length of the usage element
     */
    public int getMinLength() {
        return minLength;
    }

    /**
     * Sets the minimal length of the usage element
     *
     * @param minLength The new minimal length of the usage element
     */
    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    /**
     * Gets the maximal length of the usage element
     * @return the maximal length of the usage element
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * Sets the maximal length of the usage element
     * @param maxLength The new maximal length of the usage element
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
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
