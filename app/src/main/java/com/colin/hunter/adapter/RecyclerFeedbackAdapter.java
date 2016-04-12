package com.colin.hunter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colin.hunter.R;
import com.colin.hunter.bean.FeedBackRecordBean;
import com.colin.hunter.bean.OrderBean;
import com.colin.hunter.help.TimeHelp;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class RecyclerFeedbackAdapter extends RecyclerView.Adapter<RecyclerFeedbackAdapter.ViewHolder> {

    private List<FeedBackRecordBean> feedBackRecordBeanList;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    public RecyclerFeedbackAdapter(List<FeedBackRecordBean> feedBackRecordBeanList) {
        this.feedBackRecordBeanList = feedBackRecordBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_feedback, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final FeedBackRecordBean feedBackRecordBean = feedBackRecordBeanList.get(position);
        holder.text_item_feedback_time.setText(TimeHelp.getFeedbackTimeString(feedBackRecordBean.getTime()));
        holder.text_item_feedback_hr.setText(feedBackRecordBean.getHuman_resources_name());
        holder.text_item_feedback_title.setText(feedBackRecordBean.getFeedback_title());
        holder.text_item_feedback_content.setText(feedBackRecordBean.getFeedback_content());
    }


    @Override
    public int getItemCount() {
        return feedBackRecordBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView text_item_feedback_time;
        public final TextView text_item_feedback_hr;
        public final TextView text_item_feedback_title;
        public final TextView text_item_feedback_content;
        public OrderBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            text_item_feedback_time = (TextView) view.findViewById(R.id.text_item_feedback_time);
            text_item_feedback_hr = (TextView) view.findViewById(R.id.text_item_feedback_hr);
            text_item_feedback_title = (TextView) view.findViewById(R.id.text_item_feedback_title);
            text_item_feedback_content = (TextView) view.findViewById(R.id.text_item_feedback_content);
        }
    }

}
