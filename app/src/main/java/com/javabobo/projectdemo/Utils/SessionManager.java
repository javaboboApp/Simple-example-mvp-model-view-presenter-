package com.javabobo.projectdemo.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.javabobo.projectdemo.Models.User;

/**
 * Created by luis on 20/02/2017.
 */
public class SessionManager {
    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context _context;

    private User user;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";


    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    public static final String KEY_USERNAME = "name";
    public static final String KEY_ID_USER = "id_user";

    public static final String KEY_PATH = "path";

    private static SessionManager sessionManager;

    // Constructor
    private SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static SessionManager getInstance(Context context) {
        if (sessionManager == null) {
            sessionManager = new SessionManager(context);
        }
        return sessionManager;
    }


    /**
     * Create login session
     */
    public void createLoginSession(User user) {

        String userName = user.getNameUser();
        String email = user.getEmail();
        String path = user.getPathDir();
        int idUser = user.getIdUser();
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);


        editor.putString(KEY_USERNAME, userName);

        editor.putString(KEY_PATH, path);
        editor.putString(KEY_EMAIL, email);
        editor.putInt(KEY_ID_USER, idUser);


        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     */
    public User getCurrentUser() {

        User user = new User();
        user.setEmail(pref.getString(KEY_EMAIL, null));
        user.setUserName(pref.getString(KEY_USERNAME, null));
        user.setPathDir(pref.getString(KEY_PATH, null));
        user.setIdUser(pref.getInt(KEY_ID_USER,0));
        return user;
    }


    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

    }


    /**
     * Quick check for login
     **/

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}