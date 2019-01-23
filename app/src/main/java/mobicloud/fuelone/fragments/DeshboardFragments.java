package mobicloud.fuelone.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mobicloud.fuelone.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suraj Shakya on 18/05/18.
 */

public class DeshboardFragments extends Fragment {

    public static FragmentManager fragmentManager;
    public static Fragment fragment;
    public static View view;
    public static Context context;


    public static Fragment getInstance(Context fragmentcontext, FragmentManager manager) {
        context = fragmentcontext;
        fragmentManager = manager;
        fragment = new DeshboardFragments();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.dashboard_fragment, container, false);
        initWidgets(view);

        return view;
    }

    private void initWidgets(View view){

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
    }
}