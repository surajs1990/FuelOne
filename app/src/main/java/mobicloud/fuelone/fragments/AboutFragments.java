package mobicloud.fuelone.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mobicloud.fuelone.R;


/**
 * Created by Suraj Shakya on 18/05/18.
 */

public class AboutFragments extends Fragment {

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

    public static Fragment getInstance(Context fragmentcontext, FragmentManager manager) {
        context = fragmentcontext;
        fragmentManager = manager;
        fragment = new AboutFragments();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.about_fragment, container, false);
        initWidgets(view);
        return view;
    }

    private void initWidgets(View view){
        parentlayout    = (RelativeLayout) view.findViewById(R.id.parentlayout);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
    }

}