import de.gnmyt.autoresponder.SimpleAutoResponder;
import de.gnmyt.autoresponder.exceptions.ResponderException;

public class BasicAuthenticationExample {

    public static void main(String[] args) throws ResponderException {

        // Create an instance of the SimpleAutoResponder
        SimpleAutoResponder autoResponder = new SimpleAutoResponder();

        // If you want to, you can also set the port of the webserver (default: 8025)
        autoResponder.setPort(8031);

        // Set your username & password
        autoResponder.useAuthentication("username", "password");

        // Important: Don't forget to set the "Basic Auth" inputs in the AutoResponder app

        // Start the auto responder
        autoResponder.start();
    }

}
