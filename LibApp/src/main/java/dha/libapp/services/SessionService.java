package dha.libapp.services;

import dha.libapp.models.User;

public class SessionService {
    private static SessionService instance;

    public static SessionService getInstance() {
        if (instance == null) {
            instance = new SessionService();
        }
        return instance;
    }

    private SessionService() {}

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
