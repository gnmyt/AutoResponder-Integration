package de.gnmyt.autoresponder.commands;

import de.gnmyt.autoresponder.entities.GroupCommandData;

public abstract class ResponderChatCommand extends ResponderCommand {

    public abstract void execute(GroupCommandData command, Arguments args, ChatAnswerController controller);

}
