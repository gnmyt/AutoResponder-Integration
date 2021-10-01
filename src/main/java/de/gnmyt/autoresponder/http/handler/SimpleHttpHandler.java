package de.gnmyt.autoresponder.http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.gnmyt.autoresponder.http.controller.HttpResponseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class SimpleHttpHandler implements HttpHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleHttpHandler.class);

    /**
     * The execution-method that runs whenever the website has been loaded
     *
     * @param body       The body of the autoresponder-request
     * @param controller The created {@link HttpResponseController} to answer the message
     * @throws Exception For security reasons there could be an {@link Exception}
     */
    public abstract void execute(String body, HttpResponseController controller) throws Exception;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        StringBuilder body = new StringBuilder();

        int i;
        while ((i = exchange.getRequestBody().read()) != -1)
            body.append((char) i);

        try {
            execute(body.toString(), new HttpResponseController(exchange));
        } catch (Exception e) {
            LOG.error("Could not execute a request: {}", e.getMessage());
        }
    }

}
