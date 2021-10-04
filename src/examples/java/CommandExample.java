import de.gnmyt.autoresponder.SimpleAutoResponder;
import de.gnmyt.autoresponder.commands.Arguments;
import de.gnmyt.autoresponder.commands.ResponderChatCommand;
import de.gnmyt.autoresponder.commands.ResponderGroupCommand;
import de.gnmyt.autoresponder.commands.annotations.CommandInfo;
import de.gnmyt.autoresponder.commands.usage.UsageType;
import de.gnmyt.autoresponder.entities.ChatCommand;
import de.gnmyt.autoresponder.entities.GroupCommand;
import de.gnmyt.autoresponder.exceptions.ResponderException;

import java.util.Random;

public class CommandExample {

    public static void main(String[] args) throws ResponderException {

        // Create an instance of the SimpleAutoResponder
        SimpleAutoResponder autoResponder = new SimpleAutoResponder();

        // Set the command prefix. The user must set this before the command to identify that this is a command (default: /)
        autoResponder.setPrefix("!");

        // Now lets register some commands

        // This an example group command. It "registers" a group. Here is the usage: !setup <name> <coins> [do_sth]. It will then ask you for the
        autoResponder.registerCommand(new ExampleGroupCommand());

        // Now we can add another command. This one gives a random number of coins out
        autoResponder.registerCommand(new ExamplePrivateCommand());

        // Now you can start the auto responder
        autoResponder.start();
    }

    @CommandInfo(triggers = {"setup", "create_info"})
    public static class ExampleGroupCommand extends ResponderGroupCommand {

        @Override
        public void usage() {
            // Here we can add the usage of the command

            // Here we are adding the required field "nickname" with a minimal length of 5, a maximal length of 10
            addUsage("nickname").minLength(5).maxLength(10).required(true).add();

            // Now we can add the example field "coins". It is required, must be an integer and is only allowed to contain 5, 2 or 9
            addUsage("coins").type(UsageType.INTEGER).allowedValues("5", "2", "9").required(true).add();

            // Now we add the "do_something" field. It is not required but if it is provided it must be a boolean
            addUsage("do_something").type(UsageType.BOOLEAN).required(false).add();
        }

        @Override
        public void execute(GroupCommand command, Arguments args) {
            // Now we can check if the argument "do_something" exists and if so we can read it
            boolean do_something = args.exists("do_something") && args.getBoolean("do_something");

            // Here we can run our register logic
            registerGroup(command.getGroup(), args.getString("nickname"), args.getInteger("coins"), do_something);

            // Now we can reply to the command
            command.reply("You successfully registered the group " + command.getGroup());
        }

        public void registerGroup(String group, String nickname, int coins, boolean do_something) {
            // Here you can add your custom register logic
        }

    }

    @CommandInfo(triggers = "coins", bannedUserNames = "+49 000 000000", description = "This command shows you the coins of a user")
    public static class ExamplePrivateCommand extends ResponderChatCommand {

        @Override
        public void execute(ChatCommand command, Arguments args) {

            // Here we can answer the command
            command.reply("Please provide the sender from which you want to get the coins");

            // Here we can wait for an answer. In this case to get a user
            // You could also use the usage for this process. I just wanted to show you the awaitAnswer method :)
            command.awaitAnswer(event -> event.reply("The user " + event.getMessage() + " has " + getCoins(event.getMessage()) + " coins."));
        }

        public int getCoins(String sender) {
            // Here you can implement your own coin-logic if you want to
            return new Random().nextInt(2000);
        }

    }

}
