package de.gnmyt.autoresponder;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import de.gnmyt.autoresponder.authentication.AuthenticationDetails;
import de.gnmyt.autoresponder.authentication.ResponderAuthentication;
import de.gnmyt.autoresponder.event.api.EventManager;
import de.gnmyt.autoresponder.exceptions.ResponderException;
import de.gnmyt.autoresponder.http.contexts.ResponderContext;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleAutoResponder {

    private final EventManager eventManager = new EventManager();

    private HttpServer httpServer;
    private AuthenticationDetails authenticationDetails;

    private int port = 8025;

    /**
     * Starts the auto responder server
     *
     * @throws ResponderException Throws whenever the webserver could not start
     */
    public void start() throws ResponderException {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            throw new ResponderException("Could not open a webserver under the port " + port + ": " + e.getMessage());
        }

        registerContext();

        httpServer.start();
    }

    /**
     * Registers the responder context
     */
    public void registerContext() {
        HttpContext context = httpServer.createContext("/", new ResponderContext(this));

        if (authenticationDetails != null)
            context.setAuthenticator(new ResponderAuthentication(authenticationDetails));
    }

    /**
     * Tells the responder to use an authentication.
     * <p>
     * If you set this you should also set the authentication parameters in the AutoResponder app
     *
     * @param username The name of the user you want to use for the authentication
     * @param password The password of the user you want to use for the authentication
     * @return the current {@link SimpleAutoResponder} instance
     */
    public SimpleAutoResponder useAuthentication(String username, String password) {
        authenticationDetails = new AuthenticationDetails(username, password);
        return this;
    }

    /**
     * Gets the port of the webserver
     *
     * @return the port of the webserver
     */
    public int getPort() {
        return port;
    }

    /**
     * Updates the port of the webserver
     *
     * @param port The new port you want to set
     * @return the current {@link SimpleAutoResponder} instance
     */
    public SimpleAutoResponder setPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * Gets the event manager
     *
     * @return the event manager
     */
    public EventManager getEventManager() {
        return eventManager;
    }
}
