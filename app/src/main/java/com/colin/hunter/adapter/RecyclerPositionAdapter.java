package com.colin.hunter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colin.hunter.R;
import com.colin.hunter.bean.OrderBean;
import com.colin.hunter.bean.PositionBean;
import com.colin.hunter.data.Constants;
import com.colin.hunter.help.TimeHelp;
import com.colin.hunter.myinterface.OnRecyclerClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class RecyclerPositionAdapter extends RecyclerView.Adapter<RecyclerPositionAdapter.ViewHolder> {

    private List<PositionBean> positionBeanList;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private OnRecyclerClickListener onRecyclerClickListener = null;

    public RecyclerPositionAdapter(List<PositionBean> positionBeanList) {
        this.positionBeanList = positionBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_position, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PositionBean positionBean = positionBeanList.get(position);
        holder.text_item_position_name.setText(positionBean.getPosition_name());
        holder.text_item_position_date.setText(TimeHelp.getCurrentlyTime());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onRecyclerClickListener) {
                    onRecyclerClickListener.onClick(Constants.RECOMMEND_LIST_ITEM_CLICK_VIEW, position, true, positionBean);
                }
            }
        });
        holder.text_item_position_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onRecyclerClickListener) {
                    onRecyclerClickListener.onClick(Constants.RECOMMEND_LIST_ITEM_CLICK_DETAIL, position, true, positionBean);
                }
            }
        });
        holder.text_item_position_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onRecyclerClickListener) {
                    onRecyclerClickListener.onClick(Constants.RECOMMEND_LIST_ITEM_CLICK_RECOMMEND, position, true, positionBean);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return positionBeanList == null ? 0 : positionBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView text_item_position_name;
        public final TextView text_item_position_report_to;
        public final TextView text_item_position_company;
        public final TextView text_item_position_salary;
        public final TextView text_item_position_address;
        public final TextView text_item_position_date;
        public final TextView text_item_position_detail;
        public final TextView text_item_position_recommend;

        public OrderBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            text_item_position_name = (TextView) view.findViewById(R.id.text_item_position_name);
            text_item_position_date = (TextView) view.findViewById(R.id.text_item_position_date);
            text_item_position_report_to = (TextView) view.findViewById(R.id.text_item_position_report_to);
            text_item_position_company = (TextView) view.findViewById(R.id.text_item_position_company);
            text_item_position_salary = (TextView) view.findViewById(R.id.text_item_position_salary);
            text_item_position_address = (TextView) view.findViewById(R.id.text_item_position_address);
            text_item_position_detail = (TextView) view.findViewById(R.id.text_item_position_detail);
            text_item_position_recommend = (TextView) view.findViewById(R.id.text_item_position_recommend);
        }
    }

    public void setOnRecyclerClickListener(OnRecyclerClickListener onRecyclerClickListener) {
        this.onRecyclerClickListener = onRecyclerClickListener;
    }
}
