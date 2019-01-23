package mobicloud.fuelone.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobicloud.fuelone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import mobicloud.fuelone.fragments.AboutFragments;
import mobicloud.fuelone.fragments.ContactFragments;
import mobicloud.fuelone.fragments.DeshboardFragments;
import mobicloud.fuelone.fragments.DipFragments;
import mobicloud.fuelone.fragments.NozzelFragments;
import mobicloud.fuelone.fragments.ProfileFragments;
import mobicloud.fuelone.fragments.ReportFragments;
import mobicloud.fuelone.fragments.SettingFragments;
import mobicloud.fuelone.model.UserModel;
import mobicloud.fuelone.utils.ManageSession;


public class HomeViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean DOUBLEBACKTOEXIT = false;
    public static AdvanceDrawerLayout drawer;
    public static FrameLayout frame;
    public static FragmentManager fragmentManager;
    public static Context context;
    public static LinearLayout menuLayout;
    public static NavigationView navigationView;
    public static AdvanceDrawerLayout drawer_layout;
    public static LinearLayout calenderLay;
    public static TextView homeTxt, signoutTxt, contactTxt,
            aboutTxt, settingTxt, profileTxt,
            reposrtTxt,
            nozzelTxt, dipTxt, titleTxt, emailTxt;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view_activity);
        getSupportActionBar().hide();
        initWidgets();
        addEventListeners();
    }


    /*Declare widgtes or variables*/
    private void initWidgets(){
        context = this;
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("fuel_user").child( ManageSession.getPreference(context,"id"));

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(HomeViewActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        fragmentManager = getSupportFragmentManager();
        drawer_layout   = (AdvanceDrawerLayout) findViewById(R.id.drawer_layout);
        menuLayout      = (LinearLayout) findViewById(R.id.menuLayout);
        frame           = (FrameLayout) findViewById(R.id.frame);
        drawer          = (AdvanceDrawerLayout) findViewById(R.id.drawer_layout);
        navigationView  = (NavigationView) findViewById(R.id.nav_view);
        calenderLay     = (LinearLayout) findViewById(R.id.calenderLay);

        emailTxt        = (TextView) findViewById(R.id.emailTxt);
        homeTxt         = (TextView) findViewById(R.id.homeTxt);
        signoutTxt      = (TextView) findViewById(R.id.signoutTxt);
        contactTxt      = (TextView) findViewById(R.id.contactTxt);
        aboutTxt        = (TextView) findViewById(R.id.aboutTxt);
        settingTxt      = (TextView) findViewById(R.id.settingTxt);
        profileTxt      = (TextView) findViewById(R.id.profileTxt);
        reposrtTxt      = (TextView) findViewById(R.id.reposrtTxt);
        nozzelTxt       = (TextView) findViewById(R.id.nozzelTxt);
        dipTxt          = (TextView) findViewById(R.id.dipTxt);
        titleTxt        = (TextView) findViewById(R.id.titleTxt);

        drawer.setViewScale(Gravity.START, 0.9f);
        drawer.setViewElevation(Gravity.START, 20);
        drawer.useCustomBehavior(Gravity.END);
        emailTxt.setText(ManageSession.getPreference(context,"email"));

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    /*Call fist Deshboard functions*/
    private void callFragment(){
        setTtitle(context.getResources().getString(R.string.home_txt));
        calenderLay.setVisibility(View.GONE);
        Fragment fragment = DeshboardFragments.getInstance(context, fragmentManager);
        FragmentTransaction ft1 = fragmentManager.beginTransaction();
        ft1.replace(R.id.frame, fragment, "DeshboardFragments");
        ft1.addToBackStack(null);
        ft1.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /*---Click Listener---- */
    private void  addEventListeners(){

        menuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });


        homeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    callFragment();
                }
            }
        });

        signoutTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to Sign Out?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    signOut();
                                    ManageSession.getClearPreference(context);
                                    startActivity(new Intent(HomeViewActivity.this,LoginActivity.class));
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.setTitle("Logout");
                    alert.show();
                }

            }
        });
        contactTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    setTtitle(context.getResources().getString(R.string._contact_us));
                    calenderLay.setVisibility(View.GONE);
                    Fragment fragment = ContactFragments.getInstance(context, fragmentManager);
                    FragmentTransaction ft1 = fragmentManager.beginTransaction();
                    ft1.replace(R.id.frame, fragment, "ContactFragments");
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
            }
        });
        aboutTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    setTtitle(context.getResources().getString(R.string._about_us));
                    calenderLay.setVisibility(View.GONE);
                    Fragment fragment = AboutFragments.getInstance(context, fragmentManager);
                    FragmentTransaction ft1 = fragmentManager.beginTransaction();
                    ft1.replace(R.id.frame, fragment, "AboutFragments");
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
            }
        });
        settingTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    setTtitle(context.getResources().getString(R.string._setting));
                    calenderLay.setVisibility(View.GONE);
                    Fragment fragment = SettingFragments.getInstance(context, fragmentManager);
                    FragmentTransaction ft1 = fragmentManager.beginTransaction();
                    ft1.replace(R.id.frame, fragment, "SettingFragments");
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
            }
        });
        profileTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    setTtitle(context.getResources().getString(R.string._profile));
                    calenderLay.setVisibility(View.GONE);
                    Fragment fragment = ProfileFragments.getInstance(context, fragmentManager);
                    FragmentTransaction ft1 = fragmentManager.beginTransaction();
                    ft1.replace(R.id.frame, fragment, "ProfileFragments");
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
            }
        });
        reposrtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    setTtitle(context.getResources().getString(R.string._report));
                    calenderLay.setVisibility(View.GONE);
                    Fragment fragment = ReportFragments.getInstance(context, fragmentManager);
                    FragmentTransaction ft1 = fragmentManager.beginTransaction();
                    ft1.replace(R.id.frame, fragment, "ReportFragments");
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
            }
        });
        nozzelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    setTtitle(context.getResources().getString(R.string._nozzel));
                    calenderLay.setVisibility(View.GONE);
                    Fragment fragment = NozzelFragments.getInstance(context, fragmentManager);
                    FragmentTransaction ft1 = fragmentManager.beginTransaction();
                    ft1.replace(R.id.frame, fragment, "NozzelFragments");
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
            }
        });
        dipTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    setTtitle(context.getResources().getString(R.string._dip));
                    calenderLay.setVisibility(View.VISIBLE);
                    Fragment fragment = DipFragments.getInstance(context, fragmentManager);
                    FragmentTransaction ft1 = fragmentManager.beginTransaction();
                    ft1.replace(R.id.frame, fragment, "DipFragments");
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
            }
        });

    }

    public void signOut() {
        auth.signOut();
    }


    /*-----back Press function--------*/
    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            if(DOUBLEBACKTOEXIT){
                moveTaskToBack(true);
                Process.killProcess(Process.myPid());
                System.exit(1);
            }
            Snackbar.make(drawer_layout,"Please click BACK again to exit",Snackbar.LENGTH_SHORT).show();
            this.DOUBLEBACKTOEXIT = true;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    DOUBLEBACKTOEXIT=false;
                }
            }, 2000);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if(item.getItemId() == R.id.homeTxt){
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }


    /*
    * Set Fragment Title
    * */
    public static void setTtitle(String title){
        titleTxt.setText(title);
    }


}

