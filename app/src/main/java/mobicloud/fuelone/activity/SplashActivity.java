package mobicloud.fuelone.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.mobicloud.fuelone.R;


public class SplashActivity extends AppCompatActivity {

    private Context context;
    private final int SPLASH_DISPLAY_SECONDS = 2;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        context     = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor( getResources().getColor(R.color.colorPrimary) );
        }

        navigateToNext();


        /*if (checkPermission()) {
            navigateToNext();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermission();
            }
        }*/

    }


    /*Request for permission*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {
        requestPermissions(
                new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.CALL_PHONE
                },
                PERMISSIONS_MULTIPLE_REQUEST);
    }

    /*----Check Permission---*/
    public boolean checkPermission() {

        int CAMERA = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int INTERNET = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);
        int WRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int ACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int ACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int ACCESS_NETWORK_STATE = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_NETWORK_STATE);
        int READ_CONTACTS = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS);
        int CALL_PHONE = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);


        return  CAMERA == PackageManager.PERMISSION_GRANTED &&
                READ_EXTERNAL_STORAGE == PackageManager.PERMISSION_GRANTED &&
                INTERNET == PackageManager.PERMISSION_GRANTED &&
                WRITE_EXTERNAL_STORAGE == PackageManager.PERMISSION_GRANTED &&
                ACCESS_FINE_LOCATION == PackageManager.PERMISSION_GRANTED &&
                ACCESS_COARSE_LOCATION == PackageManager.PERMISSION_GRANTED &&
                ACCESS_NETWORK_STATE == PackageManager.PERMISSION_GRANTED &&
                READ_CONTACTS == PackageManager.PERMISSION_GRANTED &&
                CALL_PHONE == PackageManager.PERMISSION_GRANTED;

    }


    /*--- result grant permission---*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST:
                if (grantResults.length > 0) {

                    boolean CAMERA = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean READ_EXTERNAL_STORAGE = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean INTERNET = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean WRITE_EXTERNAL_STORAGE = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean ACCESS_FINE_LOCATION = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean ACCESS_COARSE_LOCATION = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    boolean ACCESS_NETWORK_STATE = grantResults[6] == PackageManager.PERMISSION_GRANTED;
                    boolean READ_CONTACTS = grantResults[7] == PackageManager.PERMISSION_GRANTED;
                    boolean CALL_PHONE = grantResults[8] == PackageManager.PERMISSION_GRANTED;

                    if (CAMERA && READ_EXTERNAL_STORAGE && INTERNET && WRITE_EXTERNAL_STORAGE
                            && ACCESS_FINE_LOCATION && ACCESS_COARSE_LOCATION && ACCESS_NETWORK_STATE && READ_CONTACTS && CALL_PHONE) {
                        navigateToNext();
                        Log.e("LogView", "Permission Granted");
                    } else {
                        Log.e("LogView", "Permission Denied");
                        requestPermission();
                    }
                }
                break;
        }
    }

    /*---Move to next Activity */
    public void navigateToNext() {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                    Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(loginIntent);
                    SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_SECONDS*1000);
    }

}
