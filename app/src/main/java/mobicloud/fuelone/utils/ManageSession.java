package mobicloud.fuelone.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

/**
 * Created by Suraj Shakya on 2018/05/17.
 */


public class ManageSession {

    private static final String PREFS_NAME = "App_Manage_Prefrence";
    private static final String LOGIN_PREFS = "Login_Status";
    public static SharedPreferences settings;
    public static SharedPreferences.Editor editor;

    private static final String SESSION_NAME = "CAMERAIMAGE";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor prefsEditor;

    public Uri getImageUri() {
        String imageUri = mSharedPreferences.getString("getImageUri", "");
        if (imageUri == null || imageUri.equals("")) return null;
        return Uri.parse(imageUri);
    }

    public void setImageUri(Uri imageUri) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getImageUri", imageUri.toString());
        prefsEditor.commit();
    }

    public String getImagePath() {
        return mSharedPreferences.getString("getImagePath", "");
    }

    public void setImagePath(String imagePath) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getImagePath", imagePath);
        prefsEditor.commit();
    }

    public String getCropImagePath() {
        return mSharedPreferences.getString("getCropImagePath", "");
    }

    public void setCropImagePath(String cropImagePath) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getCropImagePath", cropImagePath);
        prefsEditor.commit();
    }

    ManageSession(Context context) {
        mSharedPreferences = context.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        prefsEditor = mSharedPreferences.edit();
    }

    /*Set Login Prefrence*/
    public static boolean setLoginPreference(Context context, String key, String value) {
        settings = context.getSharedPreferences(LOGIN_PREFS, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(key, value);

        return editor.commit();
    }

    /*Get Login Prefrence*/
    public static String getLoginPreference(Context context, String key) {
        settings = context.getSharedPreferences(LOGIN_PREFS, Context.MODE_PRIVATE);
        return settings.getString(key, "");
    }

    /*Set App prefrences*/
    public static boolean setPreference(Context context, String key, String value) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /*Get App Prefrences*/
    public static String getPreference(Context context, String key) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, "");
    }

    /*Clear App Prefrences*/
    public static void getClearPreference(Context context) {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    /*Clear Login Prefrences*/
    public static void getClearLogout(Context context) {
        settings = context.getSharedPreferences(LOGIN_PREFS, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.commit();
    }

}
