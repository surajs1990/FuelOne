package mobicloud.fuelone.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

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
import java.util.Collections;
import java.util.Date;

import mobicloud.fuelone.activity.DipEntryActivity;
import mobicloud.fuelone.activity.HomeViewActivity;
import mobicloud.fuelone.activity.NozzelEntryActivity;
import mobicloud.fuelone.adapter.DipConfigAdapter;
import mobicloud.fuelone.adapter.NozzelConfigAdapter;
import mobicloud.fuelone.model.DipEntryModel;
import mobicloud.fuelone.utils.ManageSession;


/**
 * Created by Suraj Shakya on 18/05/18.
 */

public class NozzelFragments extends Fragment implements View.OnClickListener {

    public static FragmentManager fragmentManager;
    public static Fragment fragment;
    public static View view;
    public static Context context;

    private RelativeLayout parentlayout;
    private RecyclerView GetNozzelList;
    private LinearLayout addLayout;
    int mHour, mMinute, mYear, mMonth, mDay;
    DatePickerDialog datePickerDialog;
    private String DATE;
    private ProgressBar progressBar;
    private LinearLayout l1;
    private ImageView plusImage;
    private DatabaseReference getNozzels;
    private ArrayList<DipEntryModel> list;
    private NozzelConfigAdapter adapter;


    public static Fragment getInstance(Context fragmentcontext, FragmentManager manager) {
        context = fragmentcontext;
        fragmentManager = manager;
        fragment = new NozzelFragments();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.nozzel_fragment, container, false);
        initWidgets(view);
        addEventListeners();
        return view;
    }

    private void initWidgets(View view){

        addLayout       = (LinearLayout) view.findViewById(R.id.addLayout);
        l1              = (LinearLayout) view.findViewById(R.id.l1);
        plusImage       = (ImageView) view.findViewById(R.id.plusImage);
        parentlayout    = (RelativeLayout) view.findViewById(R.id.parentlayout);
        GetNozzelList   = (RecyclerView) view.findViewById(R.id.GetNozzelList);
        progressBar     = (ProgressBar) view.findViewById(R.id.progressBar);
        plusImage.setOnClickListener(this);
        addLayout.setOnClickListener(this);


        getNozzels     = FirebaseDatabase.getInstance().getReference("nozzel_entry")
                .child(ManageSession.getPreference(context,"id"));
        GetAllData();

        HomeViewActivity.calenderLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDatePicker();
            }
        });
    }

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
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void addEventListeners(){

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.plusImage){
            startActivity(new Intent(getActivity(), NozzelEntryActivity.class));
        }
        if(v.getId() == R.id.addLayout){
            startActivity(new Intent(getActivity(), NozzelEntryActivity.class));
        }

    }


    private void GetAllData(){
        progressBar.setVisibility(View.VISIBLE);

        getNozzels.addValueEventListener(new ValueEventListener() {

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
                    SetData(list);
                    l1.setVisibility(View.GONE);
                    GetNozzelList.setVisibility(View.VISIBLE);
                    addLayout.setVisibility(View.VISIBLE);
                }else {
                    l1.setVisibility(View.VISIBLE);
                    GetNozzelList.setVisibility(View.GONE);
                    addLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void SetData(ArrayList<DipEntryModel> list){
        Collections.reverse(list);
        adapter = new NozzelConfigAdapter(context,getActivity(), list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        GetNozzelList.setLayoutManager(mLayoutManager);
        GetNozzelList.setItemAnimator(new DefaultItemAnimator());
        GetNozzelList.setAdapter(adapter);
    }
}