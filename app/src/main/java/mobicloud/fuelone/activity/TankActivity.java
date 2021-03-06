package mobicloud.fuelone.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jeevandeshmukh.glidetoastlib.GlideToast;
import com.mobicloud.fuelone.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import mobicloud.fuelone.adapter.MappingAdapter;
import mobicloud.fuelone.model.FileModel;
import mobicloud.fuelone.model.MapingModel;
import mobicloud.fuelone.model.NozzelModel;
import mobicloud.fuelone.model.TankModel;
import mobicloud.fuelone.model.UserModel;
import mobicloud.fuelone.utils.ManageSession;

public class TankActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private LinearLayout parentlayout, addLayout;
    private ImageView plusImage;
    private EditText numberOfTank, numberOfNozzel;
    private TextView submit;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private DatabaseReference databaseTank,databaseNozzel;
    private DatabaseReference retriveTank,retriveNozzel;
    private DatabaseReference dipChart;
    private DatabaseReference mappingData;
    private static ArrayList<TankModel> tanklist;
    private ArrayList<MapingModel> mappinglist;
    private ArrayList<NozzelModel> nozzellist;
    private LinearLayout l2, l1;
    private Menu OptionMenu;
    private RecyclerView myTankData;
    private MappingAdapter adapter;
    private boolean mappingFlag;


    private String[] title ={"DipChart10kl",
    "DipChart15kl","DipChart20kl"};
    private String[] urls ={"https://firebasestorage.googleapis.com/v0/b/fuelone-4a4e9.appspot.com/o/DipChart10kl.xlsx?alt=media&token=ae9b5c6d-52d6-4b1a-a2b5-4ffd4679a6d7"
    ,"https://firebasestorage.googleapis.com/v0/b/fuelone-4a4e9.appspot.com/o/DipChart15kl.xlsx?alt=media&token=265f193e-c3c9-4f59-a75a-4848de5028a6"
    ,"https://firebasestorage.googleapis.com/v0/b/fuelone-4a4e9.appspot.com/o/DipChart20kl.xlsx?alt=media&token=7be1f210-10a9-4798-802b-982f7a8cdc87"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tank_config);
        setTitle("Tank Config");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initWidgets();
    }

    /*Declare widgtes*/
    public void initWidgets(){

        context                 = this;
        auth = FirebaseAuth.getInstance();
        dipChart             = FirebaseDatabase.getInstance().getReference("dip_Chart");

        databaseTank            = FirebaseDatabase.getInstance().getReference("tank_config").child(ManageSession.getPreference(context,"id"));
        databaseNozzel          = FirebaseDatabase.getInstance().getReference("nozzel_config").child(ManageSession.getPreference(context,"id"));

        mappingData             = FirebaseDatabase.getInstance().getReference("tank_nozzel_mapping").child(ManageSession.getPreference(context,"id"));

        myTankData              = (RecyclerView) findViewById(R.id.myTankData);
        progressBar             = (ProgressBar) findViewById(R.id.progressBar);
        plusImage               = (ImageView) findViewById(R.id.plusImage);
        numberOfTank            = (EditText) findViewById(R.id.numberOfTank);
        numberOfNozzel          = (EditText) findViewById(R.id.numberOfNozzel);
        submit                  = (TextView) findViewById(R.id.submit);
        parentlayout            = (LinearLayout) findViewById(R.id.parentlayout);
        addLayout               = (LinearLayout) findViewById(R.id.addLayout);
        l2                      = (LinearLayout) findViewById(R.id.l2);
        l1                      = (LinearLayout) findViewById(R.id.l1);

        getFirebaseData();
        plusImage.setOnClickListener(this);
        submit.setOnClickListener(this);
        addLayout.setOnClickListener(this);

//        InsertData();
   }





    @Override
    protected void onResume() {
        super.onResume();
    }



    /*
    * Get ALL Data from Firebase
    * */
    public void getFirebaseData(){

        progressBar.setVisibility(View.VISIBLE);
        mappingData.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mappinglist = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    MapingModel mapingModel = postSnapshot.getValue(MapingModel.class);
                    mappinglist.add(mapingModel);
                }
                if(mappinglist.size()!=0){

                    mappingFlag = true;
                    myTankData.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.GONE);
                    l1.setVisibility(View.GONE);
                    ShowDATA(mappinglist);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });

        databaseTank.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tanklist = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    TankModel tankModel = postSnapshot.getValue(TankModel.class);
                    if(tankModel.getUser_id().equalsIgnoreCase(ManageSession.getPreference(context,"id"))){
                        tanklist.add(tankModel);
                    }
                }
                if(tanklist.size()!=0){

                    if(mappingFlag){
                        myTankData.setVisibility(View.VISIBLE);
                        l2.setVisibility(View.GONE);
                        l1.setVisibility(View.GONE);
                    }else {
                        l2.setVisibility(View.VISIBLE);
                    }

                    submit.setText(context.getResources().getString(R.string._next));
                    numberOfTank.setText(""+tanklist.size());
                    numberOfTank.setEnabled(false);
                    numberOfNozzel.setEnabled(false);
                    addLayout.setVisibility(View.VISIBLE);
                }else {
                    addLayout.setVisibility(View.GONE);
                    l2.setVisibility(View.GONE);
                    l1.setVisibility(View.VISIBLE);
                    numberOfTank.setEnabled(true);
                    numberOfNozzel.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseNozzel.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nozzellist = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    NozzelModel nozzelModel = postSnapshot.getValue(NozzelModel.class);
                    if(nozzelModel.getUser_id().equalsIgnoreCase(ManageSession.getPreference(context,"id"))){
                        nozzellist.add(nozzelModel);
                    }
                }
                if(nozzellist.size()!=0){
                    /*l2.setVisibility(View.VISIBLE);
                    l1.setVisibility(View.GONE);*/
                    numberOfNozzel.setText(""+nozzellist.size());
                }else {
                    /*l2.setVisibility(View.GONE);
                    l1.setVisibility(View.VISIBLE);*/
                    numberOfTank.setEnabled(true);
                    numberOfNozzel.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

        this.invalidateOptionsMenu();
    }


    /*
    * Set Data Adapter
    * */
    private void ShowDATA(ArrayList<MapingModel> mappinglist){
        adapter = new MappingAdapter(context,mappinglist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        myTankData.setLayoutManager(mLayoutManager);
        myTankData.setItemAnimator(new DefaultItemAnimator());
        myTankData.setAdapter(adapter);
    }


    /*
    * insert Data
    * */
    public void InsertData(){
        for(int i=0;i<urls.length;i++){
            FileModel model = new FileModel();
            model.setDipChartName(title[i]);
            model.setDipChartURL(urls[i]);
            dipChart.child(dipChart.push().getKey()).setValue(model);
        }
    }

    /*
    * Set tank Configuration
    * */
    private void SetTankConfig(){
        String no_tank = numberOfTank.getText().toString().trim();
        String no_nozzel = numberOfNozzel.getText().toString().trim();

        if (TextUtils.isEmpty(no_tank)) {
            new GlideToast.makeToast(TankActivity.this,"Enter no of tank", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        if (TextUtils.isEmpty(no_nozzel)) {
            new GlideToast.makeToast(TankActivity.this,"Enter no of nozzel", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        setTank(Integer.parseInt(no_tank));
        setNozzel(Integer.parseInt(no_nozzel));

    }


    /*
    * Set Model to send over Database
    * */
    public void setTank(int tank){
        databaseTank.child(ManageSession.getPreference(context,"id"));
        for(int i=0;i<tank;i++){
            TankModel tankModel = new TankModel();
            tankModel.setUser_id(ManageSession.getPreference(context,"id"));
            tankModel.setTank_id(databaseTank.push().getKey());
            tankModel.setTank_name("Tank "+(i+1));
            tankModel.setTank_title("TankTitle "+(i+1));
            databaseTank.child(databaseTank.push().getKey()).setValue(tankModel);
        }

        if(mappinglist.size()!=0){
            Bundle extra = new Bundle();
            extra.putSerializable("maplist", mappinglist);
            startActivity(new Intent(TankActivity.this,MappingActivity.class)
                    .putExtra("list",extra));
        }else {
            Bundle extra = new Bundle();
            extra.putSerializable("maplist", mappinglist);
            startActivity(new Intent(TankActivity.this,MappingActivity.class)
                    .putExtra("list",extra));
        }

    }

    /*
    * Set Model to send over Database
    * */
    public void setNozzel(int nozzel){
        databaseNozzel.child(ManageSession.getPreference(context,"id"));
        for(int i=0;i<nozzel;i++){
            String id = databaseNozzel.push().getKey();
            NozzelModel model = new NozzelModel();
            model.setNozzel_id(id);
            model.setUser_id(ManageSession.getPreference(context,"id"));
            model.setNozzel_name("Nozzel "+(i+1));
            model.setNozzel_title("Nozzel Title "+(i+1));
            databaseNozzel.child(id).setValue(model);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }if (id == R.id.addnewOne) {
            if(tanklist.size()!=0){
                if(mappinglist.size()!=0){
                    Bundle extra = new Bundle();
                    extra.putSerializable("maplist", mappinglist);
                    startActivity(new Intent(TankActivity.this,MappingActivity.class)
                    .putExtra("list",extra));
                }else {
                    Bundle extra = new Bundle();
                    extra.putSerializable("maplist", mappinglist);
                    startActivity(new Intent(TankActivity.this,MappingActivity.class)
                    .putExtra("list",extra));
                }
            }else {
                l2.setVisibility(View.VISIBLE);
                l1.setVisibility(View.GONE);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu._menu, menu);
        menu.getItem(0).setVisible(false);

        /*try {
            if(tanklist.size()==0){
                menu.getItem(0).setVisible(false);
            }else {
                menu.getItem(0).setVisible(true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
        return super.onCreateOptionsMenu(menu);
    }

    private void updateMenuTitles() {
        MenuItem bedMenuItem = OptionMenu.findItem(R.id.addnewOne);
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu._menu, OptionMenu);
        try{
            if(tanklist.size()!=0){
                OptionMenu.getItem(0).setVisible(true);
            }else {
                OptionMenu.getItem(0).setVisible(false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.plusImage){
            l2.setVisibility(View.VISIBLE);
            l1.setVisibility(View.GONE);
        }
        if(v.getId() == R.id.submit){
            if(submit.getText().toString().equalsIgnoreCase(context.getResources().getString(R.string._next))){
                if(mappinglist.size()!=0){
                    Bundle extra = new Bundle();
                    extra.putSerializable("maplist", mappinglist);
                    startActivity(new Intent(TankActivity.this,MappingActivity.class)
                            .putExtra("list",extra));
                }else {
                    Bundle extra = new Bundle();
                    extra.putSerializable("maplist", mappinglist);
                    startActivity(new Intent(TankActivity.this,MappingActivity.class)
                            .putExtra("list",extra));
                }
            }else {
                SetTankConfig();
            }
        }if(v.getId() == R.id.addLayout){
            if(tanklist.size()!=0){
                if(mappinglist.size()!=0){
                    Bundle extra = new Bundle();
                    extra.putSerializable("maplist", mappinglist);
                    startActivity(new Intent(TankActivity.this,MappingActivity.class)
                            .putExtra("list",extra));
                }else {
                    Bundle extra = new Bundle();
                    extra.putSerializable("maplist", mappinglist);
                    startActivity(new Intent(TankActivity.this,MappingActivity.class)
                            .putExtra("list",extra));
                }
            }else {
                l2.setVisibility(View.VISIBLE);
                l1.setVisibility(View.GONE);
            }
        }
    }
}
