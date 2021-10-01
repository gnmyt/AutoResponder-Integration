package de.gnmyt.autoresponder.authentication;

public class AuthenticationDetails {

    private final String username;
    private final String password;

    /**
     * Constructor of the {@link AuthenticationDetails}.
     * <p>
     * Used to help the {@link de.gnmyt.autoresponder.SimpleAutoResponder} for the authentication process
     *
     * @param username The name of the user you want to use for the authentication
     * @param password The password of the user you want to use for the authentication
     */
    public AuthenticationDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username of the authentication
     *
     * @return the username of the authentication
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the authentication
     *
     * @return the password of the authentication
     */
    public String getPassword() {
        return password;
    }

}
