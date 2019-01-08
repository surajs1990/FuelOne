package mobicloud.fuelone.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobicloud.fuelone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import mobicloud.fuelone.utils.ManageSession;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private ProgressDialog progress;
    private RelativeLayout parentLayout;
    private EditText emailEdit, passEdit;
    private TextView signupLink;
    private Context context;
    private LinearLayout loginLayout;
    private FirebaseAuth auth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, HomeViewActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Login");
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        initWidgets();
        addEventListeners();
    }

    /*Declare widgtes and other variables*/
    private void initWidgets(){
        context             = this;
        parentLayout        = (RelativeLayout) findViewById(R.id.parentLayout);
        loginLayout         = (LinearLayout) findViewById(R.id.loginLayout);
        signupLink          = (TextView) findViewById(R.id.signupLink);
        progressBar         = (ProgressBar) findViewById(R.id.progressBar);
        emailEdit           = (EditText) findViewById(R.id.emailEdit);
        passEdit            = (EditText) findViewById(R.id.passEdit);

        progress = new ProgressDialog(this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        signupLink.setOnClickListener(this);
        loginLayout.setOnClickListener(this);

    }

    /*Click Listener Function*/
    private void addEventListeners(){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.loginLayout){
            Login();
            /*startActivity(new Intent(LoginActivity.this,HomeViewActivity.class));
            finish();*/
        }
        if(view.getId() == R.id.signupLink){
            startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
        }
    }

    public void Login(){

        String email = emailEdit.getText().toString().trim();
        final String password = passEdit.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            new GlideToast.makeToast(LoginActivity.this,"Enter your email", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            new GlideToast.makeToast(LoginActivity.this,"Enter your password", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                new GlideToast.makeToast(LoginActivity.this,getString(R.string.minimum_password), GlideToast.LENGTHLONG, GlideToast.WARNINGTOAST).show();
                            } else {
                                new GlideToast.makeToast(LoginActivity.this,getString(R.string.auth_failed), GlideToast.LENGTHLONG, GlideToast.FAILTOAST).show();
                            }
                        } else {
                            ManageSession.setPreference(context,"id",task.getResult().getUser().getUid().toString());
                            Intent intent = new Intent(LoginActivity.this, HomeViewActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
