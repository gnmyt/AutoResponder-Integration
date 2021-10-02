package de.gnmyt.autoresponder.commands;

import de.gnmyt.autoresponder.entities.ChatCommandData;

public abstract class ResponderGroupCommand extends ResponderCommand {

    public abstract void execute(ChatCommandData command, Arguments args, GroupAnswerController controller);

}
