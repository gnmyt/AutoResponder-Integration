import de.gnmyt.autoresponder.SimpleAutoResponder;
import de.gnmyt.autoresponder.event.api.EventListener;
import de.gnmyt.autoresponder.event.api.Listener;
import de.gnmyt.autoresponder.event.chat.ChatMessageReceivedEvent;
import de.gnmyt.autoresponder.event.group.GroupMessageReceivedEvent;
import de.gnmyt.autoresponder.exceptions.ResponderException;

public class ListenerExample {

    public static void main(String[] args) throws ResponderException {

        // Create an instance of the SimpleAutoResponder
        SimpleAutoResponder autoResponder = new SimpleAutoResponder();

        // Here we can register our example listener
        // You can add as much listener classes as you want to!
        autoResponder.registerListener(new ExampleListener());

        // Now you can start the auto responder
        autoResponder.start();
    }

    public static class ExampleListener implements Listener {

        /**
         * This method handles gives your group a name
         *
         * @param event The {@link GroupMessageReceivedEvent} that your listener needs to handle
         */
        @EventListener
        public void handle(GroupMessageReceivedEvent event) {

            // Here we can say that the listener should only be executed if its message is "Hi" and the group name is not "example"
            if (!event.getMessage().equalsIgnoreCase("Hi")) return;
            if (event.getGroup().equals("example")) return;

            // Now we can reply the first message
            event.reply("Hi! How can i call your group?");

            // And now we are waiting until the name was written.
            // Then we are going to reply to the message
            event.awaitAnswer(then -> then.reply("Awesome! I am gonna call this group " + then.getMessage()));
        }

        /**
         * This method gives your chat a name
         *
         * @param event The {@link ChatMessageReceivedEvent} that your listener needs to handle
         */
        @EventListener
        public void handle(ChatMessageReceivedEvent event) {

            // Here we can say that the listener should only be executed if its message is "Hi"
            if (!event.getMessage().equalsIgnoreCase("Hi")) return;

            // Now we can reply to the first message
            event.reply("Hi! How can i call this chat?");

            // And now we are waiting until the name was written.
            // Then we are going to reply to the message
            event.awaitAnswer(then -> then.reply("Awesome! I am gonna call you " + then.getMessage()));
        }

    }

}

