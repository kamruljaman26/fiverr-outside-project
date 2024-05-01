package com.ais.recruit.aisr.dao;

import com.ais.recruit.aisr.model.User;

public class UserDetails {
    public static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserDetails.currentUser = currentUser;
    }
}
