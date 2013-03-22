package com.chat;

import com.chat.model.user.User;

import java.util.HashSet;
import java.util.Set;

public final class DataHolder {
    private static User currentUser;
    private static final Set<User> usersOnline = new HashSet<>();

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        if (currentUser == null) {
            currentUser = user;
        }
    }

    public static Set<User> getUsersOnline() {
        return usersOnline;
    }
}
