package com.example.android.eclairiosappv3.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.eclairiosappv3.Activities.AttendanceDetails;
import com.example.android.eclairiosappv3.Models.Item;
import com.example.android.eclairiosappv3.Activities.LeaveRequest;
import com.example.android.eclairiosappv3.Activities.MarkAttendance;
import com.example.android.eclairiosappv3.R;
import com.example.android.eclairiosappv3.Fragments.DashboardFragment;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {
    private Context mContext;
    private List<Item> mData;
    private DashboardFragment dashboardFragment;

    public RVAdapter(Context mContext, List<Item> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public RVAdapter(DashboardFragment dashboardFragment, List<Item> Item1) {
        this.mContext = mContext;
        this.mData = Item1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        view = mInflater.inflate(R.layout.cardview_item_dashboard, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        holder.tv_DashCard_ID.setText(mData.get(position).getTitle());
        holder.img_DashCard_thumbnail.setImageResource(mData.get(position).getImage());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==0) {
                    Log.d("position","getPosition RV"+position);
                    Intent intent = new Intent(mContext, MarkAttendance.class);
                    mContext.startActivity(intent);
                }else if(position==1) {
                    Log.d("position","getPosition RV"+position);
                    Intent intent = new Intent(mContext, LeaveRequest.class);
                    mContext.startActivity(intent);
                }else if(position==2) {
                    Log.d("position","getPosition RV"+position);
                    Intent intent = new Intent(mContext, AttendanceDetails.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_DashCard_ID;
        ImageView img_DashCard_thumbnail;
        CardView cardview;


        public MyViewHolder(View itemView) {
            super(itemView);

            tv_DashCard_ID = (TextView) itemView.findViewById(R.id.dashCard_NameText_id);
            img_DashCard_thumbnail = (ImageView) itemView.findViewById(R.id.dashCard_img_id);
            cardview = (CardView) itemView.findViewById(R.id.cardview_id);

        }
    }
}