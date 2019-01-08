package mobicloud.fuelone.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobicloud.fuelone.R;

import mobicloud.fuelone.activity.ChangePasswordActivity;
import mobicloud.fuelone.activity.PrivacyPolicyActivity;
import mobicloud.fuelone.activity.TankActivity;


/**
 * Created by Suraj Shakya on 18/05/18.
 */

public class SettingFragments extends Fragment implements View.OnClickListener {
    private static FragmentManager fragmentManager;
    private static Fragment fragment;
    private static View view;
    private static Context context;
    private TextView changePasswordTxt, privatePolicyTxt, tankLayout;


    public static Fragment getInstance(Context fragmentcontext, FragmentManager manager) {
        context = fragmentcontext;
        fragmentManager = manager;
        fragment = new SettingFragments();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.settings_fragment, container, false);
        changePasswordTxt  = (TextView) view.findViewById(R.id.changePasswordTxt);
        privatePolicyTxt   = (TextView) view.findViewById(R.id.privatePolicyTxt);
        tankLayout         = (TextView) view.findViewById(R.id.tankLayout);

        changePasswordTxt.setOnClickListener(this);
        privatePolicyTxt.setOnClickListener(this);
        tankLayout.setOnClickListener(this);

        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.changePasswordTxt){
            startActivity(new Intent(getActivity(),ChangePasswordActivity.class));
        }
        if(view.getId() == R.id.privatePolicyTxt){
            startActivity(new Intent(getActivity(),PrivacyPolicyActivity.class));
        }
        if(view.getId() == R.id.tankLayout){
            startActivity(new Intent(getActivity(),TankActivity.class));
        }
    }
}