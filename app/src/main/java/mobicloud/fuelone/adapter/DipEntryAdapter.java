package mobicloud.fuelone.adapter;

/**

 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
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
import mobicloud.fuelone.model.FileModel;
import mobicloud.fuelone.model.MapingModel;
import mobicloud.fuelone.model.TankModel;


public class DipEntryAdapter extends RecyclerView.Adapter<DipEntryAdapter.MyViewHolder> {

    private Context context;
    HomeViewActivity homeViewActivity;
    LinearLayout layout;
    private Activity activity;
    private ArrayList<MapingModel> list;
    TextView  noz;
    /*tank,fule,*/
    LinearLayout.LayoutParams param;

    public DipEntryAdapter(Context context, Activity activity, ArrayList<MapingModel> list) {
        this.context = context;
        this.activity = activity;
        this.list = list;
        homeViewActivity = new HomeViewActivity();

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dip_entry_view, null);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tankNameTxt.setText(list.get(position).getTankDipName());
        holder.valueEdut.setText(list.get(position).getDipentry());
        holder.valueEdut.setSelection(holder.valueEdut.getText().length());

        holder.valueEdut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                list.get(position).setDipentry(holder.valueEdut.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
                list.get(position).setDipentry(holder.valueEdut.getText().toString().trim());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tankNameTxt;
        public EditText valueEdut;
        public CardView card_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            card_view        = (CardView) itemView.findViewById(R.id.card_view);
            tankNameTxt      = (TextView) itemView.findViewById(R.id.tankNameTxt);
            valueEdut        = (EditText) itemView.findViewById(R.id.valueEdut);
        }
    }

}
