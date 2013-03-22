package com.chat.model.user;

import org.apache.commons.lang.Validate;

public enum UserStatus {
    ONLINE("Online"),
    AWAY("Away"),
    OFFLINE("Offline"),
    DO_NOT_DISTURB("Do not disturb");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static UserStatus getUserStatus(String status) {
        Validate.notNull(status, "Status shouldn't be null");

        for (UserStatus userStatus : UserStatus.values()) {
            if (userStatus.getStatus().equals(status)) {
                return userStatus;
            }
        }
        return null;
    }
}
