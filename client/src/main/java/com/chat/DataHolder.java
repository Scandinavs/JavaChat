package com.chat;

import com.chat.model.User;

import java.util.HashSet;
import java.util.Set;

public final class DataHolder {
    public static User currentUser;
    public static Set<User> usersOnline = new HashSet<>();
}
