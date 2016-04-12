package com.colin.hunter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colin.hunter.R;
import com.colin.hunter.bean.OrderBean;
import com.colin.hunter.bean.RecommendBean;
import com.colin.hunter.help.TimeHelp;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class RecyclerRecommendAdapter extends RecyclerView.Adapter<RecyclerRecommendAdapter.ViewHolder> {

    private List<RecommendBean> feedBackRecordBeanList;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    public RecyclerRecommendAdapter(List<RecommendBean> feedBackRecordBeanList) {
        this.feedBackRecordBeanList = feedBackRecordBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_recommend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final RecommendBean feedBackRecordBean = feedBackRecordBeanList.get(position);
        holder.text_item_recommend_time.setText(TimeHelp.getFeedbackTimeString(feedBackRecordBean.getRecommend_start_time()));
        holder.text_item_recommend_position.setText(feedBackRecordBean.getPositionBean().getPosition_name());
        holder.text_item_recommend_company.setText(feedBackRecordBean.getPositionBean().getCompany_name());
    }


    @Override
    public int getItemCount() {
        return feedBackRecordBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView text_item_recommend_time;
        public final TextView text_item_recommend_position;
        public final TextView text_item_recommend_company;
        public OrderBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            text_item_recommend_time = (TextView) view.findViewById(R.id.text_item_recommend_time);
            text_item_recommend_position = (TextView) view.findViewById(R.id.text_item_recommend_position);
            text_item_recommend_company = (TextView) view.findViewById(R.id.text_item_recommend_company);
        }
    }

}
