package com.dyejeekis.gwf_mobile_test.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class Util {

    public static void displayShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String listToString(List<?> list, String separator) {
        String string = "";
        for (Object o : list) {
            string = string.concat(o.toString());
            if (list.indexOf(o) < list.size() - 1) string = string.concat(separator);
        }
        return string;
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static Object readObjectFromFile(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        fis.close();
        ois.close();
        return object;
    }

    public static void writeObjectToFile(Object object, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        fos.close();
        oos.close();
    }

    public static String safeJsonToString(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (Exception e) {}
        return null;
    }

    public static int safeJsonToInteger(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getInt(key);
        } catch (Exception e) {}
        return -1;
    }

    public static double safeJsonToDouble(JSONObject jsonObject, String key) {
        return safeJsonToDouble(jsonObject, key, -1);
    }

    public static double safeJsonToDouble(JSONObject jsonObject, String key, double defaultValue) {
        try {
            return jsonObject.getDouble(key);
        } catch (Exception e) {}
        return defaultValue;
    }

    public static boolean safeJsonToBoolean(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getBoolean(key);
        } catch (Exception e) {}
        return false;
    }
}
