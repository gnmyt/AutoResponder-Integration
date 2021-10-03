package de.gnmyt.autoresponder.commands;

import java.util.HashMap;

public class Arguments {

    private final HashMap<String, Object> arguments;

    /**
     * Constructor of the {@link Arguments}
     *
     * @param arguments All the arguments from the request message
     */
    public Arguments(HashMap<String, Object> arguments) {
        this.arguments = arguments;
    }

    /**
     * Gets a string from the argument list
     *
     * @param name The name of the string you want to get
     * @return the string from the argument list
     */
    public String getString(String name) {
        return (String) arguments.get(name);
    }

    /**
     * Gets an integer from the argument list
     *
     * @param name The name of the integer you want to get
     * @return the integer from the argument list
     */
    public int getInteger(String name) {
        return (int) arguments.get(name);
    }

    /**
     * Gets a boolean from the argument list
     *
     * @param name The name of the boolean you want to get
     * @return the boolean from the argument list
     */
    public boolean getBoolean(String name) {
        return (boolean) arguments.get(name);
    }

    /**
     * Checks if a specific keys is in the argument list
     *
     * @param name The name of the object you want to check
     * @return <code>true</code> if the provided key is in the argument list, otherwise <code>false</code>
     */
    public boolean exists(String name) {
        return arguments.containsKey(name);
    }

}
