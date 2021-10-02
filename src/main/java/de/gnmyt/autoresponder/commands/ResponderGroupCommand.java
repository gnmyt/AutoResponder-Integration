package de.gnmyt.autoresponder.commands;

import de.gnmyt.autoresponder.entities.GroupCommand;

public abstract class ResponderGroupCommand extends ResponderCommand {

    public abstract void execute(GroupCommand command, Arguments args);

}
