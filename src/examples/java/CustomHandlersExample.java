import de.gnmyt.autoresponder.SimpleAutoResponder;
import de.gnmyt.autoresponder.commands.usage.UsageException;
import de.gnmyt.autoresponder.commands.usage.handler.SimpleUsageErrorHandler;
import de.gnmyt.autoresponder.commands.usage.handler.UsageHandler;
import de.gnmyt.autoresponder.exceptions.ResponderException;
import de.gnmyt.autoresponder.handler.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomHandlersExample {

    public static void main(String[] args) throws ResponderException {

        // Create an instance of the SimpleAutoResponder
        SimpleAutoResponder autoResponder = new SimpleAutoResponder();

        // Here you can set the "not found handler".
        // You can use it to send specific replies if the provided message could not be identified by a listener or command

        // The random error handler. Use it to send random messages if the message could not be found
        autoResponder.useNotFoundHandler(new RandomErrorHandler("Error message 1", "Error message 2", "Error message 3"));

        // The default error handler. Use it to send all message to the user if the message could not be found
        autoResponder.useNotFoundHandler(new SendErrorHandler("First message", "Second message", "Third message"));

        // The "send nothing" handler. Use it to send no message if the message could not be found (default)
        autoResponder.useNotFoundHandler(new SendNothingHandler());

        // The dialogflow error handler. Use it to send the message directly to dialogflow if it could not be found
        autoResponder.useNotFoundHandler(new DialogflowHandler(new File("credentials.json")));

        // Or create your own handler (you can also create an own class for the handler)
        autoResponder.useNotFoundHandler(new NotFoundHandler() {
            @Override
            public List<String> handleRequest(String sender, String message) {
                return new ArrayList<>(Arrays.asList("Hello " + sender + ".", "You wrote " + message));
            }
        });


        // There is also a handler for the unexpected usage

        // Here is the default handler. It just sends the reason into the chat (default)
        autoResponder.useCommandUsageHandler(new SimpleUsageErrorHandler());

        // But you can also create your own handler
        autoResponder.useCommandUsageHandler(new UsageHandler() {
            @Override
            public List<String> handleUsageException(UsageException exception) {
                String reason;

                switch (exception.getExceptionType()) {
                    case NOT_IN_ALLOWED_VALUES:
                        reason = "You are not allowed to use this value";

                        // Here you can add all cases to change the message or for example the language

                    default:
                        reason = "An unexpected error happened";
                }

                return new ArrayList<>(Collections.singletonList(reason));
            }
        });

        // Now you can start the auto responder
        autoResponder.start();
    }

}
