package mobicloud.fuelone.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobicloud.fuelone.R;

import mobicloud.fuelone.utils.Constant;


/**
 * Created by Suraj Shakya on 18/05/18.
 */

public class ContactFragments extends Fragment {

    /*Response Data Param*/
    private String CONTENTENCODING = "";
    private String CONTENTENTYPE = "";
    private String JSONRESQUESTBEHAVIOR = "";
    private String MAXJSONLENGHT = "";
    private String RECURSIONLIMIT = "";

    public static FragmentManager fragmentManager;
    public static Fragment fragment;
    public static View view;
    public static Context context;
    private RelativeLayout parentlayout;
    private EditText name_Edit, lastname_Edit, email_Edit, phone_Edit;
    private TextView updateTxt;

    public static Fragment getInstance(Context fragmentcontext, FragmentManager manager) {
        context = fragmentcontext;
        fragmentManager = manager;
        fragment = new ContactFragments();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.contact_fragment, container, false);
        initWidgets(view);
        addEventListeners();
        return view;
    }

    private void initWidgets(View view){

        parentlayout    = (RelativeLayout) view.findViewById(R.id.parentlayout);
    }

    private void addEventListeners(){

    }


    private boolean Validation(){
        if(name_Edit.getText().length()==0){
            Snackbar.make(parentlayout,context.getResources().getString(R.string.enter_name),Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(lastname_Edit.getText().length()==0){
            Snackbar.make(parentlayout,context.getResources().getString(R.string.enter_last_name),Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(email_Edit.getText().length()==0){
            Snackbar.make(parentlayout,context.getResources().getString(R.string.eater_email),Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(!Constant.isEmailValid(email_Edit.getText().toString())){
            Snackbar.make(parentlayout,context.getResources().getString(R.string.valid_email),Snackbar.LENGTH_SHORT).show();
            return false;
        }else if(phone_Edit.getText().length()==0){
            Snackbar.make(parentlayout,context.getResources().getString(R.string.enter_mobile),Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
    }


}