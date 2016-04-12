package com.colin.hunter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colin.hunter.R;

import java.util.List;

public class RecyclerCandidateLabelAdapter extends RecyclerView.Adapter<RecyclerCandidateLabelAdapter.ViewHolder> {

    private List<String> labelList;

    public RecyclerCandidateLabelAdapter(List<String> labelList) {
        this.labelList = labelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_candidate_label, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String label = labelList.get(position);
//        ViewGroup.LayoutParams params = holder.text_item_candidate_label.getLayoutParams();//得到item的LayoutParams布局参数
//        params.height = label.length() * 20;//把随机的高度赋予item布局
//        params.width = 20;//把随机的高度赋予item布局
//        holder.text_item_candidate_label.setLayoutParams(params);//把params设置给item布局

        holder.text_item_candidate_label.setText(label);
    }


    @Override
    public int getItemCount() {
        return labelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView text_item_candidate_label;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            text_item_candidate_label = (TextView) view.findViewById(R.id.text_item_candidate_label);
        }
    }

}
