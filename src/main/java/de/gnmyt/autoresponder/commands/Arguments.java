package de.gnmyt.autoresponder.commands;

import java.util.HashMap;

public class Arguments {

    private final HashMap<String, Object> arguments;

    public Arguments(HashMap<String, Object> arguments) {
        this.arguments = arguments;
    }

    public String getString(String name) {
        return (String) arguments.get(name);
    }

    public int getInteger(String name) {
        return (int) arguments.get(name);
    }

    public boolean getBoolean(String name) {
        return (boolean) arguments.get(name);
    }

    public boolean exists(String name) {
        return arguments.containsKey(name);
    }

}
