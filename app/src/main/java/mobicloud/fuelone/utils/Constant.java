package mobicloud.fuelone.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
*  Created by Suraj Shakya 2018/05/21
* */

public class Constant {


    public static String BASEURL = "http://ailogisticweb.azurewebsites.net/";
    public static final int ACTIVITY_RESULT = 1001, ACTIVITY_FINISH = 1002,            GALLERY = 111, CAMERA = 112, CROP = 113;
    public static final String IMAGE_DIRECTORY = "/DCIM/PICTURES";    public static final String IMAGE_DIRECTORY_CROP = "/DCIM/AI_LOGISTIC";
    /*Check for Email Validation*/
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    public static String EMAIL_PETTERN = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@\"\n" + "  + \"[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$";

    public static boolean emailValidation(String emailtxt) {
        boolean isValid = false;
        CharSequence inputStr = emailtxt;
        Pattern pattern = Pattern.compile(Constant.EMAIL_PETTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /*----Hide keyBoard----*/
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /*--- Check String is null or Not---*/
    public static boolean isStringNullOrBlank(String str) {
        if (str == null) {
            return true;
        } else if (str.equals("null") || str.equals("")) {
            return true;
        }
        return false;
    }


    /*--- Check For netWork Connetion---*/
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    /*--- Function for Make a call ----*/
    public static void doCall(Context context){

        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:1234567890"));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            context.startActivity(callIntent);

        }catch (Exception e){ e.printStackTrace();  }
    }

}