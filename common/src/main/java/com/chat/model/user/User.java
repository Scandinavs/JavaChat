package com.chat.model.user;

import java.io.Serializable;

public class User implements Serializable {
    private final String name;
    private UserStatus status;

    public User(String name) {
        this.name = name;
        this.status = UserStatus.ONLINE;
    }

    public String getName() {
        return name;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return name.equals(user.name) && status == user.status;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("User: name=%s, status=%s", name, status);
    }
}
