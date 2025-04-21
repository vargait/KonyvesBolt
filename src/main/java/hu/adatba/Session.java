package hu.adatba;

import hu.adatba.Model.User;

public class Session {
    private static User loggedInUser;

    public static void setUser(User user) {
        loggedInUser = user;
    }

    public static User getUser() {
        return loggedInUser;
    }

    public static void clear() {
        loggedInUser = null;
    }
}
