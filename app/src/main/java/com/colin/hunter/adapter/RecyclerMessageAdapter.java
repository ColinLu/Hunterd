package com.colin.hunter.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.colin.hunter.R;
import com.colin.hunter.base.BaseFragment;
import com.colin.hunter.bean.MessageBean;
import com.colin.hunter.help.TimeHelp;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class RecyclerMessageAdapter extends RecyclerView.Adapter<RecyclerMessageAdapter.ViewHolder> {
    private List<MessageBean> messageBeanList;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private final int fragment_id;
    private BaseFragment.OnFragmentListener mListener;

    public RecyclerMessageAdapter(int fragment_id, List<MessageBean> messageBeanList, BaseFragment.OnFragmentListener listener) {
        this.messageBeanList = messageBeanList;
        mListener = listener;
        this.fragment_id = fragment_id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final MessageBean messageBean = messageBeanList.get(position);
        holder.setMessagebean(messageBean);
        holder.text_item_message_from.setText(String.valueOf(messageBean.getFrom()));
        holder.text_item_message_content.setText(messageBean.getContent());
        holder.text_item_message_time.setText(TimeHelp.getMessageTime(messageBean.getTime()));
        loadImage(holder.image_item_message_head, position);
    }

    private void loadImage(ImageView imageview, int position) {

        if (!TextUtils.isEmpty(messageBeanList.get(position).getUser_head())) {
            imageview.setTag(messageBeanList.get(position).getUser_head());
            imageview.setImageResource(R.mipmap.image_loading);
            if (imageview.getTag() != null && imageview.getTag().equals(messageBeanList.get(position).getUser_head())) {
                imageLoader.displayImage(messageBeanList.get(position).getUser_head(), imageview);
            }
        } else {
            imageview.setImageResource(R.mipmap.image_load_empty);
        }

    }

    @Override
    public int getItemCount() {
        return messageBeanList == null ? 0 : messageBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView image_item_message_head;
        public final TextView text_item_message_from;
        public final TextView text_item_message_time;
        public final TextView text_item_message_content;
        public MessageBean messagebean;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            image_item_message_head = (ImageView) view.findViewById(R.id.image_item_message_head);
            text_item_message_from = (TextView) view.findViewById(R.id.text_item_message_from);
            text_item_message_time = (TextView) view.findViewById(R.id.text_item_message_time);
            text_item_message_content = (TextView) view.findViewById(R.id.text_item_message_content);
        }

        public MessageBean getMessagebean() {
            return messagebean;
        }

        public void setMessagebean(MessageBean messagebean) {
            this.messagebean = messagebean;
        }
    }

}
