package com.sahajarora.urdriver;

import com.urdriver.sahajarora.myapplication.backend.userApi.model.User;

/**
 * Created by sahajarora on 16-05-14.
 */
public abstract class DataHolder {

    public static User currentUser = null;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        DataHolder.currentUser = currentUser;
    }





}
