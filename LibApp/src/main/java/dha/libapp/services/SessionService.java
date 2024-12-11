package dha.libapp.services;

import dha.libapp.models.User;

/**
 * Service class that manages the session of a user in the application.
 * This class ensures that only one instance of the session is created (singleton pattern),
 * and provides methods to get and set the current user.
 */
public class SessionService {
    private static SessionService instance;

    /**
     * Returns the single instance of the SessionService.
     * If the instance does not exist, it will be created.
     *
     * @return The instance of SessionService.
     */
    public static SessionService getInstance() {
        if (instance == null) {
            instance = new SessionService();
        }
        return instance;
    }

    private SessionService() {
    }

    private User user;

    /**
     * Gets the current logged-in user.
     *
     * @return The current User object representing the logged-in user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the current logged-in user.
     * This method is used to log in a user and update the session.
     *
     * @param user The User object representing the logged-in user.
     */
    public void setUser(User user) {
        this.user = user;
        System.out.println("Login OK");
    }
}
