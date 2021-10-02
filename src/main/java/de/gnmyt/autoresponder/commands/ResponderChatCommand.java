package de.gnmyt.autoresponder.commands;

import de.gnmyt.autoresponder.entities.ChatCommand;

public abstract class ResponderChatCommand extends ResponderCommand {

    public abstract void execute(ChatCommand command, Arguments args);

}
