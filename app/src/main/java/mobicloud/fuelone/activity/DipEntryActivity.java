package mobicloud.fuelone.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobicloud.fuelone.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mobicloud.fuelone.adapter.DipEntryAdapter;
import mobicloud.fuelone.model.DipEntryModel;
import mobicloud.fuelone.model.MapingModel;
import mobicloud.fuelone.utils.ManageSession;
import mobicloud.fuelone.utils.OutLook;
import mobicloud.fuelone.widget.SwitchButton;

public class DipEntryActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private RelativeLayout parentlayout;
    private DatabaseReference retriveTank, addDipEntry;
    private TextView dateTxt, nextButton;
    private static ArrayList<MapingModel> mappinglist;
    private static ArrayList<MapingModel> copyList;
    private SwitchButton Switchbtn;
    int mHour, mMinute, mYear, mMonth, mDay;
    DatePickerDialog datePickerDialog;
    private String DATE;
    private boolean shiftDay;
    private ProgressBar progressBar;
    private RecyclerView dipEntryList;
    private DipEntryAdapter adapter;
    private TextView messageTxt;
    private ArrayList<DipEntryModel> list;
    private static String nightMode ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dip_activity);
        setTitle("Dip Entry");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initWidgets();
    }

    /*Declare widgtes*/
    public void initWidgets(){

        context         = this;
        parentlayout    = (RelativeLayout) findViewById(R.id.parentlayout);

        messageTxt      = (TextView) findViewById(R.id.messageTxt);
        dipEntryList    = (RecyclerView) findViewById(R.id.dipEntryList);
        dateTxt         = (TextView) findViewById(R.id.dateTxt);
        nextButton      = (TextView) findViewById(R.id.nextButton);
        Switchbtn       = (SwitchButton) findViewById(R.id.Switchbtn);
        progressBar     = (ProgressBar) findViewById(R.id.progressBar);

        retriveTank     = FirebaseDatabase.getInstance().getReference("tank_nozzel_mapping").child(ManageSession.getPreference(context,"id"));
        addDipEntry     = FirebaseDatabase.getInstance().getReference("dip_entry").child(ManageSession.getPreference(context,"id"));

        nextButton.setOnClickListener(this);
        GetAllDipData();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = df.format(c);
        DATE = formattedDate;
        dateTxt.setText(formattedDate);

        Switchbtn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton s, boolean isChecked) {
                if(isChecked){
                    shiftDay = true;
                }else {
                    shiftDay = false;
                }
            }
        });

    }



    /*
    * Get All Dip Entry DATA
    * */
    private void GetAllDipData(){

        progressBar.setVisibility(View.VISIBLE);
        addDipEntry.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                list = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    System.out.print(""+postSnapshot.getChildren().toString());
                    DipEntryModel model = postSnapshot.getValue(DipEntryModel.class);
                    list.add(model);
                }
                if(list.size()!=0){
                    GetDataFilter(list,DATE);
                }else {
                    GetAllData();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public void GetDataFilter(ArrayList<DipEntryModel> list, String date){
        boolean flag = false;
        for(int i =0;i<list.size();i++){
            if(list.get(i).getDate().equalsIgnoreCase(date)){
                nightMode = list.get(i).getShift();
                SetData(list.get(i).getTanks());
                flag = true;
                break;
            }
        }

        if(!flag){
            GetAllData();
        }

    }

    /*
    * Get Calender
    * */
    public void GetDatePicker(){
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        c.setTime(new Date());
        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                final String date = dayOfMonth+"/"+String.format("%02d", monthOfYear+1)+"/"+String.format("%02d", year);
                SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date dateObj = curFormater.parse(date);
                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
                    String formattedDate = df.format(dateObj);
                    DATE = formattedDate;
                    GetAllDipData();
                    dateTxt.setText(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void SetData(ArrayList<MapingModel> list){
        if(nightMode.equalsIgnoreCase("day")){
            Switchbtn.setChecked(true);
        }else if(nightMode.equalsIgnoreCase("night")){
            Switchbtn.setChecked(false);
        }else {
            Switchbtn.setChecked(false);
        }
        copyList = new ArrayList<>();
        copyList.addAll(list);
        adapter = new DipEntryAdapter(context,DipEntryActivity.this, list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        dipEntryList.setLayoutManager(mLayoutManager);
        dipEntryList.setItemAnimator(new DefaultItemAnimator());
        dipEntryList.setAdapter(adapter);
    }

    private boolean ValidList(ArrayList<MapingModel> map){
        boolean flag = false;
        for(int i=0;i<map.size();i++){
            if(!map.get(i).getDipentry().trim().equals("")){
                flag = true;
            }else {
                flag = false;
            }
        }
        return flag;
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.nextButton){
            if(ValidList(copyList)){
                String DayMode = "";
                DipEntryModel entryModel = new DipEntryModel();
                entryModel.setUserId(ManageSession.getPreference(context,"id"));
                entryModel.setDate(DATE);
                if(shiftDay){
                    entryModel.setShift("day");
                    DayMode = "Day";
                }else {
                    entryModel.setShift("night");
                    DayMode = "Night";
                }
                entryModel.setTanks(copyList);
                addDipEntry.child(DATE+"_"+ManageSession.getPreference(context,"id")+"_"+DayMode).setValue(entryModel);
                ShowAlertDialog(DATE);
            }else {
                Snackbar.make(parentlayout,"Please Enter Dipentry",Snackbar.LENGTH_SHORT).show();
            }
        }
    }


    private void ShowAlertDialog(String date){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(" You have successfully entered dip entry for tank.")
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

    private void GetAllData(){
        progressBar.setVisibility(View.VISIBLE);
        retriveTank.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                mappinglist = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    MapingModel mapingModel = postSnapshot.getValue(MapingModel.class);
                    mappinglist.add(mapingModel);
                }
                if(mappinglist.size()!=0){
                    for(int i=0;i<mappinglist.size();i++){
                        mappinglist.get(i).setDipentry("");
                    }
                    SetData(mappinglist);
                    messageTxt.setVisibility(View.GONE);
                    dipEntryList.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                }else {
                    messageTxt.setVisibility(View.VISIBLE);
                    dipEntryList.setVisibility(View.GONE);
                    nextButton.setVisibility(View.GONE);
//                    Snackbar.make(parentlayout,"Please cinfiguration your tank",Snackbar.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }if (id == R.id.calenderMenu) {
            GetDatePicker();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.dip_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
