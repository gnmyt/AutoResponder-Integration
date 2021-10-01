package de.gnmyt.autoresponder.authentication;

import com.sun.net.httpserver.BasicAuthenticator;

public class ResponderAuthentication extends BasicAuthenticator {

    private final AuthenticationDetails details;

    /**
     * Constructor of the {@link ResponderAuthentication}
     *
     * @param details Your authentication details from the {@link de.gnmyt.autoresponder.SimpleAutoResponder}
     */
    public ResponderAuthentication(AuthenticationDetails details) {
        super("/");
        this.details = details;
    }

    /**
     * Checks the credentials of the request
     *
     * @param username The name of the user from the request
     * @param password The password of the user from the request
     * @return <code>true</code> if the provided credentials are correct, otherwise <code>false</code>
     */
    @Override
    public boolean checkCredentials(String username, String password) {
        return details.getUsername().equals(username) && details.getPassword().equals(password);
    }
}
