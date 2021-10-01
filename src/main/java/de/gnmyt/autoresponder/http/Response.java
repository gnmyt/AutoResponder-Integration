package de.gnmyt.autoresponder.http;

import java.util.HashMap;

public class Response {

    private int code = 200;
    private String output = "";
    private HashMap<String, String> headers = new HashMap<>();

    /**
     * Basic constructor of the response
     */
    public Response() {

    }

    /**
     * Advanced constructor with prefilled values
     * @param code The response code
     * @param output The response output
     * @param headers The response headers
     */
    public Response(int code, String output, HashMap<String, String> headers) {
        this.code = code;
        this.output = output;
        this.headers = headers;
    }

    /**
     * Gets the response code
     * @return the response code
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets the response output
     * @return the response output
     */
    public String getOutput() {
        return output;
    }

    /**
     * Gets the response headers
     * @return the response headers
     */
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    /**
     * Sets the response code
     * @param code The new response code
     * @return the current {@link Response} instance
     */
    public Response setCode(int code) {
        this.code = code;
        return this;
    }

    /**
     * Sets the response output
     * @param output The new response output
     * @return the current {@link Response} instance
     */
    public Response setOutput(String output) {
        this.output = output;
        return this;
    }

    /**
     * Sets the response headers
     * @param headers The new response headers
     * @return the current {@link Response} instance
     */
    public Response setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    /**
     * Adds a header to the response
     * @param key The key of the response header
     * @param value The value of the response header
     * @return the current {@link Response} instance
     */
    public Response addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    /**
     * Adds a string to the output
     * @param output The output you want to add
     * @return the current {@link Response} instance
     */
    public Response addOutput(String output) {
        this.output += output;
        return this;
    }

}
