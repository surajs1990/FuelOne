package mobicloud.fuelone.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.mobicloud.fuelone.R;

import mobicloud.fuelone.utils.OutLook;

public class ChangePasswordActivity extends AppCompatActivity {

    private Context context;
    private LinearLayout backLayout,parentlayout;
    private TextView updateTxt;
    private EditText newpasswordedit, confirmPasswrodedit, oldpasswodedit;
    private ImageView eye_confirm, eye_new, eye_old;
    private ProgressDialog progress;
    private boolean oldpass, newpass, conpass = false;

    /*Response Data Param*/
    private String CONTENTENCODING = "";
    private String CONTENTENTYPE = "";
    private String JSONRESQUESTBEHAVIOR = "";
    private String MAXJSONLENGHT = "";
    private String RECURSIONLIMIT = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_layout);
        getSupportActionBar().hide();
        initWidgets();
        addEventListeners();
    }

    /*Declare widgtes*/
    public void initWidgets(){

        context                 = this;
        newpasswordedit         = (EditText) findViewById(R.id.newpasswordedit);
        confirmPasswrodedit     = (EditText) findViewById(R.id.confirmPasswrodedit);
        oldpasswodedit          = (EditText) findViewById(R.id.oldpasswodedit);
        updateTxt               = (TextView) findViewById(R.id.updateTxt);
        backLayout              = (LinearLayout) findViewById(R.id.backLayout);
        parentlayout            = (LinearLayout) findViewById(R.id.parentlayout);
        eye_confirm             = (ImageView) findViewById(R.id.eye_confirm);
        eye_new                 = (ImageView) findViewById(R.id.eye_new);
        eye_old                 = (ImageView) findViewById(R.id.eye_old);

        progress = new ProgressDialog(this);
        progress.setMessage("Please wait..");
        progress.setCancelable(false);

    }


    /*Click Listener function*/
    private void addEventListeners(){

        eye_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contogglePass();
            }
        });

        eye_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newtogglePass();
            }
        });

        eye_old.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OldtogglePass();
            }
        });

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        updateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutLook.hideSoftKeyboard(ChangePasswordActivity.this);
            }
        });
    }


    /*---------Validation------------*/
    private boolean validation(){
        if(newpasswordedit.getText().length()==0){
            Snackbar.make(parentlayout,context.getResources().getString(R.string.enter_new_password), Toast.LENGTH_SHORT).show();
            return false;
        }else if(oldpasswodedit.getText().length()==0){
            Snackbar.make(parentlayout,context.getResources().getString(R.string.enter_old_password), Toast.LENGTH_SHORT).show();
            return false;
        }else if(!confirmPasswrodedit.getText().toString().trim().equals(newpasswordedit.getText().toString().trim())){
            Snackbar.make(parentlayout,context.getResources().getString(R.string.password_same), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void OldtogglePass() {
        if (oldpass) {
            String pass = oldpasswodedit.getText().toString();
            oldpasswodedit.setTransformationMethod(PasswordTransformationMethod.getInstance());
            oldpasswodedit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            oldpasswodedit.setText(pass);
            oldpasswodedit.setSelection(pass.length());
        } else {
            String pass = oldpasswodedit.getText().toString();
            oldpasswodedit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            oldpasswodedit.setInputType(InputType.TYPE_CLASS_TEXT);
            oldpasswodedit.setText(pass);
            oldpasswodedit.setSelection(pass.length());
        }
        oldpass= !oldpass;
    }


    private void newtogglePass() {
        if (newpass) {
            String pass = newpasswordedit.getText().toString();
            newpasswordedit.setTransformationMethod(PasswordTransformationMethod.getInstance());
            newpasswordedit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            newpasswordedit.setText(pass);
            newpasswordedit.setSelection(pass.length());
        } else {
            String pass = newpasswordedit.getText().toString();
            newpasswordedit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            newpasswordedit.setInputType(InputType.TYPE_CLASS_TEXT);
            newpasswordedit.setText(pass);
            newpasswordedit.setSelection(pass.length());
        }
        newpass= !newpass;
    }



    private void contogglePass() {
        if (conpass) {
            String pass = confirmPasswrodedit.getText().toString();
            confirmPasswrodedit.setTransformationMethod(PasswordTransformationMethod.getInstance());
            confirmPasswrodedit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            confirmPasswrodedit.setText(pass);
            confirmPasswrodedit.setSelection(pass.length());
        } else {
            String pass = confirmPasswrodedit.getText().toString();
            confirmPasswrodedit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            confirmPasswrodedit.setInputType(InputType.TYPE_CLASS_TEXT);
            confirmPasswrodedit.setText(pass);
            confirmPasswrodedit.setSelection(pass.length());
        }
        conpass= !conpass;
    }


}
