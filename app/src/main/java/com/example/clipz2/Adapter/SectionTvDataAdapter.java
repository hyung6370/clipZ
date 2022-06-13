package com.example.clipz2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clipz2.R;
import com.example.clipz2.SectionItem_tv;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SectionTvDataAdapter extends RecyclerView.Adapter<SectionTvDataAdapter.ItemTvRowHolder> {

    private ArrayList<SectionItem_tv> dataTvList;
    private Context mContext;

    public SectionTvDataAdapter(ArrayList<SectionItem_tv> dataTvList, Context context) {
        this.dataTvList = dataTvList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ItemTvRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_main, null);
        ItemTvRowHolder mh = new ItemTvRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemTvRowHolder holder, int i) {
        final String sectionName = dataTvList.get(i).getHeaderTitle();
        ArrayList singleSectionTvItems = dataTvList.get(i).getSingleItemList_tv();
        holder.itemTitle.setText(sectionName);
        SingleTvDataAdapter itemTvListDataAdapter = new SingleTvDataAdapter(mContext, singleSectionTvItems);
        holder.recycler_view_list.setHasFixedSize(true);
        holder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler_view_list.setAdapter(itemTvListDataAdapter);
    }

    @Override
    public int getItemCount() {
        return (null != dataTvList ? dataTvList.size() : 0);
    }

    public class ItemTvRowHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;
        protected RecyclerView recycler_view_list;

        public ItemTvRowHolder(@NonNull View itemView) {
            super(itemView);
            this.itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) itemView.findViewById(R.id.recycler_view_list);
        }

    }
}
