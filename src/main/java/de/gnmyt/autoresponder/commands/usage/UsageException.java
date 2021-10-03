package de.gnmyt.autoresponder.commands.usage;

public class UsageException extends Exception {

    public UsageExceptionType exceptionType;
    public String message;
    public String usageName;

    /**
     * Constructor of the {@link UsageExceptionType}
     *
     * @param exceptionType The type of the usage exception
     * @param message       The reason why this has been executed
     * @param usageName     The name of the usage element
     */
    public UsageException(UsageExceptionType exceptionType, String message, String usageName) {
        this.exceptionType = exceptionType;
        this.message = message;
        this.usageName = usageName;
    }

    /**
     * Gets the exception type
     *
     * @return the exception type
     */
    public UsageExceptionType getExceptionType() {
        return exceptionType;
    }

    /**
     * Gets the message of the exception
     *
     * @return the message of the exception
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Gets the name of the usage element
     *
     * @return the name of the usage element
     */
    public String getUsageName() {
        return usageName;
    }

    /**
     * Builds the usage exception into a string
     *
     * @return the usage exception into a string
     */
    @Override
    public String toString() {
        return exceptionType.toString() + "#" + getUsageName() +": " + message;
    }
}
