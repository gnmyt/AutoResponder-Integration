package de.gnmyt.autoresponder;

import com.sun.net.httpserver.HttpServer;
import de.gnmyt.autoresponder.exceptions.ResponderException;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleAutoResponder {

    private HttpServer httpServer;

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

}
