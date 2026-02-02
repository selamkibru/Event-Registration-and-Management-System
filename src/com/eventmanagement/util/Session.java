package com.eventmanagement.util;

import com.eventmanagement.model.AdminAccount;

public class Session {

    private static AdminAccount loggedInAdmin;

    public static void setLoggedInAdmin(AdminAccount admin) {
        loggedInAdmin = admin;
    }

    public static AdminAccount getLoggedInAdmin() {
        return loggedInAdmin;
    }
}