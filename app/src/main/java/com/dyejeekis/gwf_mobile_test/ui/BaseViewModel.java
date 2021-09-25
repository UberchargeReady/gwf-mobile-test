package com.dyejeekis.gwf_mobile_test.ui;

import androidx.lifecycle.ViewModel;

import com.dyejeekis.gwf_mobile_test.MyApp;
import com.dyejeekis.gwf_mobile_test.data.model.User;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public abstract class BaseViewModel extends ViewModel {

    protected ExecutorService getExecutor() {
        return MyApp.getInstance().getExecutorService();
    }

    protected User getUser() {
        return MyApp.getInstance().getCurrentUser();
    }

    protected void saveUser(User user)  {
        try {
            MyApp.getInstance().setCurrentUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
