package com.example.clipz2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clipz2.Class.SingleItem_tv;
import com.example.clipz2.DetailActivity;
import com.example.clipz2.R;

import java.util.ArrayList;

public class SingleTvDataAdapter extends RecyclerView.Adapter<SingleTvDataAdapter.SingleItemTvRowHolder> {

    private ArrayList<SingleItem_tv> itemsTvList;
    private LayoutInflater mInflate;
    private Context mContext;


    public SingleTvDataAdapter(Context context, ArrayList<SingleItem_tv> itemsTvList) {
        this.itemsTvList = itemsTvList;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public SingleItemTvRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single, null);
        SingleItemTvRowHolder mh = new SingleItemTvRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemTvRowHolder holder, int i) {
        String url = "http://image.tmdb.org/t/p/w500" + itemsTvList.get(i).getPoster_path();
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.imageView_m);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click", String.valueOf(itemsTvList));
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("id", itemsTvList.get(holder.getAdapterPosition()).getId());
                intent.putExtra("name", itemsTvList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("original_name", itemsTvList.get(holder.getAdapterPosition()).getOriginal_name());
                intent.putExtra("poster_path", itemsTvList.get(holder.getAdapterPosition()).getPoster_path());
                intent.putExtra("overview", itemsTvList.get(holder.getAdapterPosition()).getOverview());
                intent.putExtra("first_air_date", itemsTvList.get(holder.getAdapterPosition()).getFirst_air_date());
                intent.putExtra("vote_average", itemsTvList.get(holder.getAdapterPosition()).getVote_average());
                intent.putIntegerArrayListExtra("genre_ids", itemsTvList.get(holder.getAdapterPosition()).getGenre_ids());
                Log.d("Adapter", "Clicked: " + holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemsTvList.size();
    }


    public class SingleItemTvRowHolder extends RecyclerView.ViewHolder {
        public ImageView imageView_m;

        public SingleItemTvRowHolder(View itemView) {
            super(itemView);
            imageView_m = (ImageView) itemView.findViewById(R.id.imageView_m);
        }
    }
}
