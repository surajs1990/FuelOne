package mobicloud.fuelone.adapter;

/**

 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import mobicloud.fuelone.model.FileModel;
import mobicloud.fuelone.model.MapingModel;


public class MapTankAdapter extends RecyclerView.Adapter<MapTankAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<FileModel> list ;
    HomeViewActivity homeViewActivity;
    LinearLayout layout;
    private Activity activity;
    TextView  noz;
    /*tank,fule,*/
    LinearLayout.LayoutParams param;

    public MapTankAdapter(Context context,Activity activity, ArrayList<FileModel> list) {
        this.context = context;
        this.activity = activity;
        this.list = list;
        homeViewActivity = new HomeViewActivity();

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.maptank_view, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.fileName.setText(list.get(position).getDipChartName());

        holder.viewExel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = list.get(position).getDipChartURL();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });

        holder.selectExel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMessage = new Intent();
                intentMessage.putExtra("name",list.get(position).getDipChartName());
                intentMessage.putExtra("url",list.get(position).getDipChartURL());
                activity.setResult(2, intentMessage);
                activity.finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fileName, viewExel, selectExel;
        public CardView card_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            card_view   = (CardView) itemView.findViewById(R.id.card_view);
            fileName    = (TextView) itemView.findViewById(R.id.fileName);
            viewExel    = (TextView) itemView.findViewById(R.id.viewExel);
            selectExel  = (TextView) itemView.findViewById(R.id.selectExel);
        }
    }


}
