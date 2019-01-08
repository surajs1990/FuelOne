package mobicloud.fuelone.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
    private EditText capacity, tankName;

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

        databaseTank    = FirebaseDatabase.getInstance().getReference(ManageSession.getPreference(context,"id")+"tank_");
        databasenozzel  = FirebaseDatabase.getInstance().getReference(ManageSession.getPreference(context,"id")+"nozzel_");
        databasemapping = FirebaseDatabase.getInstance().getReference(ManageSession.getPreference(context,"id")+"mapping_");

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

        try{
            Bundle extra = getIntent().getBundleExtra("list");
            mappinglist = (ArrayList<MapingModel>) extra.getSerializable("maplist");
        }catch (Exception e){
            e.printStackTrace();
        }

        fuleType_spinner.setItems(fuleType);
        fuleType_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                FUELTYPE = fuleType.get(position);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

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

                if(_tank.size()!=0){
                    tankName.setText( _tank.get(0).getTank_name());
                    tank_spinner.setItems(CampareAndRemoveFromList(mappinglist,TankName));
//                    tank_spinner.setItems(TankName);
                    tank_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                        @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                            TANKID = _tank.get(position).getTank_id();
                            TANKNAME =  _tank.get(position).getTank_name();
                            tankName.setText(TANKNAME);
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

                if(_nozzel.size()!=0){
                    spinner.setItems(NozzelName);
                    nozzel_spinner.setItems(NozzelName);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /*Click Listener function*/
    private void addEventListeners(){

        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+nozzel_spinner.getSelectedItemsAsString(),Toast.LENGTH_SHORT).show();
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

        if (TextUtils.isEmpty(_tankName)) {
            new GlideToast.makeToast(MappingActivity.this,"Enter tank name", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        if (TextUtils.isEmpty(_capacity)) {
            new GlideToast.makeToast(MappingActivity.this,"Enter capacity", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }

        String id = databasemapping.push().getKey();
        MapingModel mapingModel = new MapingModel();
        mapingModel.setUserId(ManageSession.getPreference(context,"id"));
        mapingModel.setTank_id(TANKID);
        mapingModel.setTankName(_tankName);
        mapingModel.setNozzel_name(NOZZELNAME);
        mapingModel.setFuletype(FUELTYPE);
        mapingModel.setCapacity(_capacity);
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
    public ArrayList<String> CampareAndRemoveFromList(ArrayList<MapingModel> mappinglist, ArrayList<String> list){
        ArrayList<String> strings = null;
        if(mappinglist.size()!=0){
            strings = new ArrayList<>();
            for(int i=0;i<mappinglist.size();i++){
                for(int j=0;j<list.size();j++){
                    if(!mappinglist.get(i).getTankName().equalsIgnoreCase(list.get(j))){
                        strings.add(list.get(j));
                    }
                }
            }
            return strings;
        }else {
            strings = new ArrayList<>();
            strings.addAll(list);
            return strings;
        }
    }

}
