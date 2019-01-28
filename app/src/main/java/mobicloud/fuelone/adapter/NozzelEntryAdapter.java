package mobicloud.fuelone.adapter;

/**

 */


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobicloud.fuelone.R;

import java.util.ArrayList;

import mobicloud.fuelone.activity.HomeViewActivity;
import mobicloud.fuelone.activity.NozzelEntryActivity;
import mobicloud.fuelone.model.MapingModel;


public class NozzelEntryAdapter extends RecyclerView.Adapter<NozzelEntryAdapter.MyViewHolder> {

    private Context context;
    HomeViewActivity homeViewActivity;
    LinearLayout layout;
    private Activity activity;
    private ArrayList<MapingModel> list;
    TextView  noz;
    /*tank,fule,*/
    LinearLayout.LayoutParams param;
    NozzelItemAdapter adapter;

    public NozzelEntryAdapter(Context context, Activity activity, ArrayList<MapingModel> list) {
        this.context = context;
        this.activity = activity;
        this.list = list;
        homeViewActivity = new HomeViewActivity();

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.nozzel_list_view, null);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tankNameTxt.setText(list.get(position).getTankDipName());
        holder.fuleType.setText(list.get(position).getFuletype());

        adapter = new NozzelItemAdapter(context, list.get(position).getNozzel_list());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        holder.ItemList.setLayoutManager(mLayoutManager);
        holder.ItemList.setItemAnimator(new DefaultItemAnimator());
        holder.ItemList.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fuleType, tankNameTxt;
        public EditText valueEdut;
        public CardView card_view;
        public RecyclerView ItemList;

        public MyViewHolder(View itemView) {
            super(itemView);
            card_view        = (CardView) itemView.findViewById(R.id.card_view);
            tankNameTxt      = (TextView) itemView.findViewById(R.id.tankNameTxt);
            fuleType         = (TextView) itemView.findViewById(R.id.fuleType);
            ItemList         = (RecyclerView) itemView.findViewById(R.id.ItemList);
        }
    }

}
