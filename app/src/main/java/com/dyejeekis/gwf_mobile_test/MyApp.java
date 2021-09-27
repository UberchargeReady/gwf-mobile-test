package com.dyejeekis.gwf_mobile_test;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.dyejeekis.gwf_mobile_test.data.model.User;
import com.dyejeekis.gwf_mobile_test.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApp extends Application {

    public static final String USER_FILE_NAME = "user";

    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    private User currentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public User getCurrentUser() {
        if (currentUser == null) {
            try {
                currentUser = (User) Util.readObjectFromFile(new File(getFilesDir(), USER_FILE_NAME));
//                if (!currentUser.isRefreshTokenValid())
//                    currentUser = new User();
            } catch (IOException | ClassNotFoundException e) {
                currentUser = new User();
            }
        }
        return currentUser;
    }

    public void setCurrentUser(User currentUser) throws IOException {
        this.currentUser = currentUser;
        Util.writeObjectToFile(currentUser, new File(getFilesDir(), USER_FILE_NAME));
    }
}
