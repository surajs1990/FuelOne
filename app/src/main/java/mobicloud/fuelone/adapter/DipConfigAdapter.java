package mobicloud.fuelone.adapter;

/**

 */


import android.app.Activity;
import android.content.Context;
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
import mobicloud.fuelone.model.DipEntryModel;
import mobicloud.fuelone.model.MapingModel;


public class DipConfigAdapter extends RecyclerView.Adapter<DipConfigAdapter.MyViewHolder> {

    private Context context;
    HomeViewActivity homeViewActivity;
    LinearLayout layout;
    private Activity activity;
    private ArrayList<DipEntryModel> list;
    TextView  noz;
    /*tank,fule,*/
    LinearLayout.LayoutParams param;
    LayoutInflater inflater;
    View view;
    private TextView tankNameTxt, dipEntryTxt;

    public DipConfigAdapter(Context context, Activity activity, ArrayList<DipEntryModel> list) {
        this.context = context;
        this.activity = activity;
        this.list = list;
        homeViewActivity = new HomeViewActivity();

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dip_list_view, null);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.dateTxt.setText(list.get(position).getDate());
        holder.modetxt.setText(list.get(position).getShift().toUpperCase());

        for(int i =0;i<list.get(position).getTanks().size();i++){
            inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.infelater_view, null);
            tankNameTxt = (TextView) view.findViewById(R.id.tankNameTxt);
            dipEntryTxt = (TextView) view.findViewById(R.id.dipEntryTxt);

            tankNameTxt.setText(list.get(position).getTanks().get(i).getTankDipName());
            dipEntryTxt.setText(list.get(position).getTanks().get(i).getDipentry());
            holder.LayoutOne.addView(view);
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTxt, modetxt;
        public CardView card_view;
        private LinearLayout LayoutOne;


        public MyViewHolder(View itemView) {
            super(itemView);
            LayoutOne        = (LinearLayout) itemView.findViewById(R.id.LayoutOne);
            card_view        = (CardView) itemView.findViewById(R.id.card_view);
            dateTxt          = (TextView) itemView.findViewById(R.id.dateTxt);
            modetxt          = (TextView) itemView.findViewById(R.id.modetxt);
        }
    }

}
