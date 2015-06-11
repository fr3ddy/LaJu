package de.dhbw_loerrach.laju;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Frederik on 26.05.2015.
 */
public class User {

    private static User userInstance = null;
    public static String username;
    public static String firstname;
    public static String lastname;
    public static String email;
    private static Context context;
    private boolean isMainNavDrawer = true;

    private User(Context context, String username, String firstname, String lastname, String email) {
        User.username = username;
        User.firstname = firstname;
        User.lastname = lastname;
        User.email = email;
        User.context = context;
    }

    public static void login(Context context, String username, String firstname, String lastname, String email) {
        userInstance = new User(context, username, firstname, lastname, email);
        saveToPrefs();
    }

    public static void logout() {
        userInstance = null;
        User.username = "";
        User.firstname = "";
        User.lastname = "";
        User.email = "";
        saveToPrefs();
    }

    public static boolean isLoggedIn() {
        return userInstance != null;
    }

    public static User getInstance() {
        return userInstance;
    }

    public static void relogin(Context context) {
        User.context = context;
        if (!getFromPrefs("username", "").equals("")) {
            login(context, getFromPrefs("username", ""), getFromPrefs("firstname", ""), getFromPrefs("lastname", ""), getFromPrefs("email", ""));
        }
    }

    private static void saveToPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", username);
        editor.putString("firstname", firstname);
        editor.putString("lastname", lastname);
        editor.putString("email", email);
        editor.commit();
    }

    private static String getFromPrefs(String key, String defaultValue) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            return sharedPrefs.getString(key, defaultValue);
        } catch (Exception e) {
            //e.printStackTrace();
            return defaultValue;
        }
    }

    public boolean getIsMainNavDrawer() {
      return isMainNavDrawer;
    }

    public void setIsMainNavDrawer(boolean value) {
        isMainNavDrawer = value;
    }

}
