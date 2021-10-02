package de.gnmyt.autoresponder;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import de.gnmyt.autoresponder.authentication.AuthenticationDetails;
import de.gnmyt.autoresponder.authentication.ResponderAuthentication;
import de.gnmyt.autoresponder.commands.ResponderCommand;
import de.gnmyt.autoresponder.event.api.EventManager;
import de.gnmyt.autoresponder.event.api.Listener;
import de.gnmyt.autoresponder.exceptions.ResponderException;
import de.gnmyt.autoresponder.handler.NotFoundHandler;
import de.gnmyt.autoresponder.handler.SendNothingHandler;
import de.gnmyt.autoresponder.http.contexts.ResponderContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;

public class SimpleAutoResponder {

    private final EventManager eventManager = new EventManager();

    private HttpServer httpServer;
    private AuthenticationDetails authenticationDetails;

    private NotFoundHandler notFoundHandler = new SendNothingHandler();

    private final ArrayList<ResponderCommand> commands = new ArrayList<>();

    private int port = 8025;
    private String prefix = "/";

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
    private void registerContext() {
        HttpContext context = httpServer.createContext("/", new ResponderContext(this));

        if (authenticationDetails != null)
            context.setAuthenticator(new ResponderAuthentication(authenticationDetails));
    }

    /**
     * Registers the provided listeners
     *
     * @param listeners The listeners that you want to register
     * @return the current {@link SimpleAutoResponder} instance
     */
    public SimpleAutoResponder registerListener(Listener... listeners) {
        for (Listener listener : listeners)
            eventManager.addEventListener(listener);
        return this;
    }

    /**
     * Registers the provided responder command
     *
     * @param commands The commands you want to register
     * @return the current {@link SimpleAutoResponder} instance
     */
    public SimpleAutoResponder registerCommand(ResponderCommand... commands) {
        this.commands.addAll(Arrays.asList(commands));
        return this;
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
     * Sets the custom "not found handler". It executes whenever a message could not be found / answered
     *
     * @param notFoundHandler The new "not found handler"
     * @return the current {@link SimpleAutoResponder} instance
     */
    public SimpleAutoResponder useNotFoundHandler(NotFoundHandler notFoundHandler) {
        this.notFoundHandler = notFoundHandler;
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
     * Gets the current command prefix
     *
     * @return the current command prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets the command prefix
     *
     * @param prefix The new command prefix
     */
    public SimpleAutoResponder setPrefix(String prefix) {
        this.prefix = prefix;
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

    /**
     * Gets the handler which executes whenever the message could not be found
     *
     * @return the not found handler
     */
    public NotFoundHandler getNotFoundHandler() {
        return notFoundHandler;
    }

    /**
     * Gets all registered commands
     *
     * @return all registered commands
     */
    public ArrayList<ResponderCommand> getCommands() {
        return commands;
    }
}
