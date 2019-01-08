package mobicloud.fuelone.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mobicloud.fuelone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import mobicloud.fuelone.model.UserModel;
import mobicloud.fuelone.utils.ManageSession;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog progress;
    private RelativeLayout parentLayout;
    private Context context;
    private LinearLayout loginLayout, backLayout;
    private EditText fullNameEdit, emailEdit, userNameEdit,
            passwordEdit, confpassEdit, phoneEdit;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private DatabaseReference databaseArtists;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Sign Up");
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        initWidgets();
        addEventListeners();
    }

    /*Declare widgtes and other variables*/
    private void initWidgets(){
        context             = this;
        databaseArtists     = FirebaseDatabase.getInstance().getReference("fuel_user");
        parentLayout        = (RelativeLayout) findViewById(R.id.parentLayout);
        loginLayout         = (LinearLayout) findViewById(R.id.loginLayout);
        backLayout          = (LinearLayout) findViewById(R.id.backLayout);
        progressBar         = (ProgressBar) findViewById(R.id.progressBar);
        fullNameEdit        = (EditText) findViewById(R.id.fullNameEdit);
        emailEdit           = (EditText) findViewById(R.id.emailEdit);
        userNameEdit        = (EditText) findViewById(R.id.userNameEdit);
        passwordEdit        = (EditText) findViewById(R.id.passwordEdit);
        confpassEdit        = (EditText) findViewById(R.id.confpassEdit);
        phoneEdit           = (EditText) findViewById(R.id.phoneEdit);

        progress = new ProgressDialog(this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        loginLayout.setOnClickListener(this);
        backLayout.setOnClickListener(this);


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

            SignUp();
            /*startActivity(new Intent(SignUpActivity.this,HomeViewActivity.class));
            finish();*/
        }
        if(view.getId() == R.id.backLayout){
            finish();
        }
    }


    public void SignUp(){

        String fulname = fullNameEdit.getText().toString().trim();
        String email = emailEdit.getText().toString().trim();
        String userName = userNameEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        String conpassword = confpassEdit.getText().toString().trim();
        String phone = phoneEdit.getText().toString().trim();

        if (TextUtils.isEmpty(fulname)) {
            new GlideToast.makeToast(SignUpActivity.this,"Enter your full name", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            new GlideToast.makeToast(SignUpActivity.this,"Enter your email", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }  if (TextUtils.isEmpty(userName)) {
            new GlideToast.makeToast(SignUpActivity.this,"Enter your user name", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            new GlideToast.makeToast(SignUpActivity.this,"Enter your password", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        if (TextUtils.isEmpty(conpassword)) {
            new GlideToast.makeToast(SignUpActivity.this,"Enter your confirm password", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            new GlideToast.makeToast(SignUpActivity.this,"Enter your phone number", GlideToast.LENGTHTOOLONG, GlideToast.WARNINGTOAST).show();
            return;
        }

        String id = databaseArtists.push().getKey();

        //creating an Artist Object
        userModel = new UserModel();
        userModel.setId(id);
        userModel.setFullname(fulname);
        userModel.setEmail(email);
        userModel.setUsername(userName);
        userModel.setPassword(password);
        userModel.setMobile(phone);



        auth(email, password);
    }


    public void auth(String email, String password){
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        new GlideToast.makeToast(SignUpActivity.this,"createUserWithEmail:onComplete:" + task.isSuccessful(), GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST).show();
                        progressBar.setVisibility(View.GONE);

                        if (!task.isSuccessful()) {
                            new GlideToast.makeToast(SignUpActivity.this,"Authentication failed."+ task.getException() , GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                        } else {
                            Log.e("","TASK : "+task.getResult().getUser().getUid().toString());
                            databaseArtists.child(task.getResult().getUser().getUid().toString()).setValue(userModel);
                            ManageSession.setPreference(context,"id",task.getResult().getUser().getUid().toString());
                            auth.signOut();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
