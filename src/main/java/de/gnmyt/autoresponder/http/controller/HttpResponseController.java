package de.gnmyt.autoresponder.http.controller;


import com.sun.net.httpserver.HttpExchange;
import de.gnmyt.autoresponder.http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class HttpResponseController {

    private static final Logger LOG = LoggerFactory.getLogger(HttpResponseController.class);

    private final Response response = new Response();
    private final HttpExchange exchange;

    /**
     * Constructor of the {@link HttpResponseController}
     *
     * @param exchange The {@link HttpExchange} from the {@link de.gnmyt.autoresponder.http.contexts.ResponderContext}
     */
    public HttpResponseController(HttpExchange exchange) {
        this.exchange = exchange;
    }

    /**
     * Adds some text to the output
     *
     * @param output The output you want to add
     * @return the current {@link HttpResponseController} instance
     */
    public HttpResponseController writeToOutput(String output) {
        response.addOutput(output);
        return this;
    }

    /**
     * Adds a new header to the response
     *
     * @param name  The name of the header you want to add
     * @param value The value the header should have
     * @return the current {@link HttpResponseController} instance
     */
    public HttpResponseController header(String name, String value) {
        response.addHeader(name, value);
        return this;
    }

    /**
     * Sends some text to the response
     *
     * @param text The text you want to send
     */
    public void text(String text) {
        writeToOutput(text);
        send();
    }

    /**
     * Sends the response to the AutoResponder
     */
    public void send() {
        OutputStream os = exchange.getResponseBody();

        response
                .addHeader("Server", "ResponderIntegration")
                .addHeader("Content-Type", "application/json")
                .addHeader("Allow-Control-Allow-Origin", "*");

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS"))
            response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, POST")
                    .addHeader("Access-Control-Allow-Headers", "*");

        response.getHeaders().forEach((key, value) -> exchange.getResponseHeaders().put(key, Collections.singletonList(value)));

        byte[] bs = response.getOutput().getBytes(StandardCharsets.UTF_8);

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1L);
            } else {
                exchange.sendResponseHeaders(response.getCode(), bs.length);
                os.write(bs);
            }
            os.close();
        } catch (IOException e) {
            LOG.error("Could not process response: " + e.getMessage());
        }
    }

}
