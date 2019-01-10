package mobicloud.fuelone.adapter;

/**

 */


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobicloud.fuelone.R;

import java.util.ArrayList;

import mobicloud.fuelone.activity.HomeViewActivity;
import mobicloud.fuelone.model.MapingModel;


public class MappingAdapter extends RecyclerView.Adapter<MappingAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<MapingModel> list ;
    HomeViewActivity homeViewActivity;
    LinearLayout layout;
    TextView  noz;
    /*tank,fule,*/
    LinearLayout.LayoutParams param;

    public MappingAdapter(Context context, ArrayList<MapingModel> list) {
        this.context = context;
        this.list = list;
        homeViewActivity = new HomeViewActivity();

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.mapping_view, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tankNameTxt.setText(list.get(position).getTankName());
        holder.fueltxt.setText(list.get(position).getFuletype());
        holder.LayoutOne.setPadding(2,2, 2 ,2);

        String[] nozzel=null;
        try {
            nozzel = list.get(position).getNozzel_name().split(",");
        }catch (Exception e){
            e.printStackTrace();
        }

        /*
        * Create Dynamic View
        * */
        if(nozzel.length>0){

            for(int i=0;i<nozzel.length;i++){
                layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                /*tank = new TextView(context);
                fule = new TextView(context);*/
                noz  = new TextView(context);

                param = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
                /*tank.setLayoutParams(param);
                fule.setLayoutParams(param);*/
                noz.setLayoutParams(param);
                noz.setGravity(Gravity.LEFT);
                /*tank.setTextSize(12);
                fule.setTextSize(12);*/
                noz.setTextSize(12);

                /*
                * Set Text view Value
                * */
                /*tank.setText(list.get(position).getTankName());
                fule.setText(list.get(position).getFuletype());*/
                noz.setText(nozzel[i].trim());

                /*
                * Add textView in Layout
                * */
                /*layout.addView(tank);
                layout.addView(fule);*/
                layout.addView(noz);

                /*
                * Add Layouts on Main Layout
                * */
                holder.LayoutOne.addView(layout);
            }
        }else {

            layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.HORIZONTAL);

            /*tank = new TextView(context);
            fule = new TextView(context);*/
            noz  = new TextView(context);

            param = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
            /*tank.setLayoutParams(param);
            fule.setLayoutParams(param);*/
            noz.setGravity(Gravity.LEFT);
            noz.setLayoutParams(param);

            /*
            * Set Text view Value
            * */
            /*tank.setText(list.get(position).getTankName());
            fule.setText(list.get(position).getFuletype());*/
            noz.setText(list.get(position).getNozzel_name());

            /*
            * Add textView in Layout
            * */
            /*layout.addView(tank);
            layout.addView(tank);*/
            layout.addView(noz);

            /*
            * Add Layouts on Main Layout
            * */
            holder.LayoutOne.addView(layout);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tankNameTxt, fueltxt;
        public LinearLayout LayoutOne;
        public CardView card_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            card_view   = (CardView) itemView.findViewById(R.id.card_view);
            tankNameTxt = (TextView) itemView.findViewById(R.id.tankNameTxt);
            fueltxt     = (TextView) itemView.findViewById(R.id.fueltxt);
            LayoutOne   = (LinearLayout) itemView.findViewById(R.id.LayoutOne);
        }
    }


}
