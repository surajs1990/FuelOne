package mobicloud.fuelone.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobicloud.fuelone.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.util.ArrayList;
import java.util.Arrays;

import io.apptik.widget.multiselectspinner.MultiSelectSpinner;
import mobicloud.fuelone.adapter.MultiSelectionSpinner;
import mobicloud.fuelone.model.MapingModel;
import mobicloud.fuelone.model.NozzelModel;
import mobicloud.fuelone.model.TankModel;
import mobicloud.fuelone.utils.ManageSession;

public class MappingActivity extends AppCompatActivity {

    private Context context;
    private LinearLayout parentlayout;
    private MaterialSpinner tank_spinner,  fuleType_spinner;
    private MultiSelectionSpinner nozzel_spinner;
    private MultiSelectionSpinner spinner;
    private TextView submit, chart;
    private Activity activity;
    private ArrayList<String> fuleType;
    private ArrayList<String> TankName;
    private ArrayList<String> NozzelName;
    private ArrayList<MapingModel> mappinglist;

    private ArrayList<TankModel> _tank;
    private ArrayList<NozzelModel> _nozzel;

    private DatabaseReference databaseTank, databasenozzel, databasemapping;
    private EditText capacity, tankName, chartEdit;
    private String CHARTURL;

    private String TANKID, TANKNAME, TANKTITLE, NOZZELID, NOZZELNAME, FUELTYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapping_layout);
        setTitle("Mapping");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initWidgets();
        addEventListeners();
    }

    /*Declare widgtes*/
    public void initWidgets(){

        context                 = this;
        activity                = this;
        fuleType                = new ArrayList<>();

        databaseTank    = FirebaseDatabase.getInstance().getReference("tank_config").child(ManageSession.getPreference(context,"id"));
        databasenozzel  = FirebaseDatabase.getInstance().getReference("nozzel_config").child(ManageSession.getPreference(context,"id"));
        databasemapping = FirebaseDatabase.getInstance().getReference("tank_nozzel_mapping").child(ManageSession.getPreference(context,"id"));

        fuleType.add("Select Fule Type");
        fuleType.add("MS");
        fuleType.add("HSD");
        fuleType.add("Speed/Premium MS");
        fuleType.add("Speed/Premium HSD");
        fuleType.add("CNG");

        parentlayout            = (LinearLayout) findViewById(R.id.parentlayout);
        tank_spinner            = (MaterialSpinner) findViewById(R.id.tank_spinner);
        nozzel_spinner          = (MultiSelectionSpinner) findViewById(R.id.nozzel_spinner);
        fuleType_spinner        = (MaterialSpinner) findViewById(R.id.fuleType_spinner);
        spinner                 = (MultiSelectionSpinner) findViewById(R.id.spinner);
        submit                  = (TextView) findViewById(R.id.submit);
        chart                   = (TextView) findViewById(R.id.chart);
        capacity                = (EditText) findViewById(R.id.capacity);
        tankName                = (EditText) findViewById(R.id.tankName);
        chartEdit               = (EditText) findViewById(R.id.chartEdit);

        try{
            Bundle extra = getIntent().getBundleExtra("list");
            mappinglist = (ArrayList<MapingModel>) extra.getSerializable("maplist");
            /*Toast.makeText(context,""+mappinglist.size()
                    +"\n"+mappinglist.get(0).getTankName()
                    +"\n"+mappinglist.get(1).getTankName(),Toast.LENGTH_SHORT).show();*/
        }catch (Exception e){
            e.printStackTrace();
        }

        GetFirebaseData();
        fuleType_spinner.setItems(fuleType);
        fuleType_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                FUELTYPE = fuleType.get(position);
                if(!FUELTYPE.equalsIgnoreCase("Select Fule Type")){
                    tankName.setText(TANKNAME+" DIP "+FUELTYPE);
                }
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            String mane = data.getStringExtra( "name");
            chartEdit.setText(""+mane);
            CHARTURL    = data.getStringExtra( "url");
        }
    }


    public void SetTank(String Name){
        if(Name.equalsIgnoreCase("Select Tank")){
            new GlideToast.makeToast(MappingActivity.this,"Select Tank", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
        }else {
            for(int i=0;i<_tank.size();i++){
                if(_tank.get(i).getTank_name().equalsIgnoreCase(Name)){
                    TANKID = _tank.get(i).getTank_id();
                    TANKNAME =  _tank.get(i).getTank_name();
                    tankName.setText(TANKNAME +" DIP");
                    break;
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onStart() {
        super.onStart();


    }


    /*
    * Get Firebase Data
    * */
    private void GetFirebaseData(){

        TankName = new ArrayList<>();
        databaseTank.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                TankName = new ArrayList<>();
                _tank    = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    TankModel tank = postSnapshot.getValue(TankModel.class);
                    if(tank.getUser_id().equalsIgnoreCase(ManageSession.getPreference(context,"id"))){
                        TankName.add(tank.getTank_name());
                        _tank.add(tank);
                    }
                }
                if(TankName.size()!=0){
//                    tankName.setText( TankName.get(0));
                    Log.e("","Size : "+TankName.size());
                    tank_spinner.setItems(CampareAndRemoveFromList(mappinglist,TankName));

                    tank_spinner.getSelectedIndex();
                    tank_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                        @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                            SetTank(item);
                            /*TANKID = _tank.get(position).getTank_id();
                            TANKNAME =  _tank.get(position).getTank_name();
                            tankName.setText(TANKNAME);*/
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databasenozzel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                NozzelName = new ArrayList<>();
                _nozzel    = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    NozzelModel nozzel = postSnapshot.getValue(NozzelModel.class);
                    if(nozzel.getUser_id().equalsIgnoreCase(ManageSession.getPreference(context,"id"))){
                        NozzelName.add(nozzel.getNozzel_name());
                        _nozzel.add(nozzel);
                    }
                }

                if(NozzelName.size()!=0){
                    nozzel_spinner.setItems(RemoveNozzelFromList(getNozzels(mappinglist),NozzelName));
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        /*if(CampareAndRemoveFromList(mappinglist,TankName).size()==1){
            submit.setBackgroundColor(R.color.light_gray);
            submit.setEnabled(false);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("No tanks found!")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            AlertDialog alert = builder.create();
            alert.setTitle("Alert!");
            alert.show();
        }*/
    }

    /*Click Listener function*/
    private void addEventListeners(){

        chartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MappingActivity.this, TankExelActivity.class),101);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetMapConfig();
            }
        });

    }


    private void SetMapConfig(){

        String _capacity    = capacity.getText().toString().trim();
        String _tankName    = tankName.getText().toString().trim();
        NOZZELNAME          = nozzel_spinner.getSelectedItemsAsString();


        if (TextUtils.isEmpty(NOZZELNAME)) {
            new GlideToast.makeToast(MappingActivity.this,"Select Nozzel", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }

        if (TextUtils.isEmpty(FUELTYPE) || FUELTYPE.equalsIgnoreCase("Select Fule Type")) {
            new GlideToast.makeToast(MappingActivity.this,"Select fuel type", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }

        if (TextUtils.isEmpty(_tankName)) {
            new GlideToast.makeToast(MappingActivity.this,"Enter tank name", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        if (TextUtils.isEmpty(_capacity)) {
            new GlideToast.makeToast(MappingActivity.this,"Enter capacity", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        if (TextUtils.isEmpty(chartEdit.getText().toString())) {
            new GlideToast.makeToast(MappingActivity.this,"Please select chart", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }

        String id = databasemapping.push().getKey();
        MapingModel mapingModel = new MapingModel();
        mapingModel.setUserId(ManageSession.getPreference(context,"id"));
        mapingModel.setTank_id(TANKID);
        mapingModel.setTankDipName(_tankName);
        mapingModel.setNozzel_name(NOZZELNAME);
        mapingModel.setFuletype(FUELTYPE);
        mapingModel.setCapacity(_capacity);
        mapingModel.setSheet(chartEdit.getText().toString());
        mapingModel.setSheetUrl(CHARTURL);
        databasemapping.child(id).setValue(mapingModel);

        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    /*
    * Filter Data
    * */
    @SuppressLint("ResourceAsColor")
    public ArrayList<String> CampareAndRemoveFromList(ArrayList<MapingModel> mappinglist, ArrayList<String> list){
        ArrayList<String> strings = null;
        if(mappinglist.size()!=0){
            strings = new ArrayList<>();
            strings.add("Select Tank");
            for(int i=0;i<mappinglist.size();i++){
                for(int j=0;j<list.size();j++){
                    if(list.get(j).equalsIgnoreCase(mappinglist.get(i).getTankDipName())){
                        list.remove(j);
                    }
                }
            }
            strings.addAll(list);
            if(strings.size()==1){
                submit.setBackgroundColor(R.color.light_gray);
                submit.setEnabled(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("We have configured all tank with nozzels.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                finish();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.setTitle("Alert!");
                alert.show();
            }
            return strings;
        }else {
            strings = new ArrayList<>();
            strings.add("Select Tank");
            strings.addAll(list);
            if(strings.size()==1){
                submit.setBackgroundColor(R.color.light_gray);
                submit.setEnabled(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("We have configured all tank with nozzels.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Alert!");
                alert.show();
            }
            return strings;
        }
    }


    public ArrayList<String> getNozzels(ArrayList<MapingModel> mappinglist){
        ArrayList<String> nozzels = new ArrayList<>();
        for(int i=0;i<mappinglist.size();i++){
            try{
                String[] s = mappinglist.get(i).getNozzel_name().split(",");
                nozzels.addAll(Arrays.asList(s));
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
        }
        return nozzels;
    }


    @SuppressLint("ResourceAsColor")
    public ArrayList<String>  RemoveNozzelFromList(ArrayList<String> mappinglist, ArrayList<String> list){
        ArrayList<String> strings = null;
        if(mappinglist.size()!=0){
            strings = new ArrayList<>();
            strings.add("Select Nozzel");
            for(int i=0;i<mappinglist.size();i++){
                for(int j=0;j<list.size();j++){
                    if(list.get(j).equalsIgnoreCase(mappinglist.get(i).trim())){
                        list.remove(j);
                    }
                }
            }
            int sum = list.size();
            strings.addAll(list);
            if(strings.size()==1){
                submit.setBackgroundColor(R.color.light_gray);
                submit.setEnabled(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("We have configured all tank with nozzels.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                finish();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.setTitle("Alert!");
                alert.show();
            }
            return strings;
        }else {
            strings = new ArrayList<>();
            strings.add("Select Nozzel");
            strings.addAll(list);
            if(strings.size()==1){
                submit.setBackgroundColor(R.color.light_gray);
                submit.setEnabled(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("We have configured all tank with nozzels.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                finish();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.setTitle("Alert!");
                alert.show();
            }
            return strings;
        }
    }



}
