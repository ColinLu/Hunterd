package com.colin.hunter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colin.hunter.R;
import com.colin.hunter.base.BaseFragment;
import com.colin.hunter.bean.OrderBean;
import com.colin.hunter.data.Constants;
import com.colin.hunter.help.TimeHelp;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class RecyclerOrderAdapter extends RecyclerView.Adapter<RecyclerOrderAdapter.ViewHolder> {

    private List<OrderBean> orderBeanList;
    private final BaseFragment.OnFragmentListener mListener;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private int fragment_id;

    public RecyclerOrderAdapter(List<OrderBean> orderBeanList, int fragment_id, BaseFragment.OnFragmentListener listener) {
        this.orderBeanList = orderBeanList;
        mListener = listener;
        this.fragment_id = fragment_id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final OrderBean orderBean = orderBeanList.get(position);
        holder.text_item_order_name.setText(orderBeanList.get(position).getPositionBean().getName());
        holder.text_item_order_date.setText(TimeHelp.getCurrentlyTime());
//        holder.text_item_order_report_to.setText("汇报对象：" + orderBeanList.get(position).getReport_to());
//        holder.text_item_order_company.setText("空缺：" + String.valueOf(orderBeanList.get(position).getNumber()));
//        holder.text_item_order_salary.setText("薪酬：" + String.valueOf(orderBeanList.get(position).getMin_salary()) + "~" + String.valueOf(orderBeanList.get(position).getMax_salary()) + "万");
//        holder.text_item_order_address.setText("工作地：" + orderBeanList.get(position).getWork_place());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentClick(fragment_id, position, true, orderBean);
                }
            }
        });
        holder.text_item_order_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentClick(Constants.FRAGMENT_ORDER_ITEM_FEEDBACK, position, true, orderBean);
                }
            }
        });
        holder.text_item_order_recommend_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentClick(Constants.FRAGMENT_ORDER_ITEM_RECOMMEND_STATE, position, true, orderBean);
                }
            }
        });
        holder.text_item_order_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFragmentClick(Constants.FRAGMENT_ORDER_ITEM_RECOMMEND, position, true, orderBean);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return orderBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView text_item_order_name;
        public final TextView text_item_order_report_to;
        public final TextView text_item_order_company;
        public final TextView text_item_order_salary;
        public final TextView text_item_order_address;
        public final TextView text_item_order_date;
        public final TextView text_item_order_feedback;
        public final TextView text_item_order_recommend;
        public final TextView text_item_order_recommend_state;

        public OrderBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            text_item_order_name = (TextView) view.findViewById(R.id.text_item_order_name);
            text_item_order_date = (TextView) view.findViewById(R.id.text_item_order_date);
            text_item_order_report_to = (TextView) view.findViewById(R.id.text_item_order_report_to);
            text_item_order_company = (TextView) view.findViewById(R.id.text_item_order_company);
            text_item_order_salary = (TextView) view.findViewById(R.id.text_item_order_salary);
            text_item_order_address = (TextView) view.findViewById(R.id.text_item_order_address);
            text_item_order_feedback = (TextView) view.findViewById(R.id.text_item_order_feedback);
            text_item_order_recommend = (TextView) view.findViewById(R.id.text_item_order_recommend);
            text_item_order_recommend_state = (TextView) view.findViewById(R.id.text_item_order_recommend_state);
            if (fragment_id == Constants.ORDER_TAB_TITLE_ID[0]) {
                text_item_order_feedback.setVisibility(View.VISIBLE);
                text_item_order_recommend.setVisibility(View.GONE);
                text_item_order_recommend_state.setVisibility(View.GONE);
            } else if (fragment_id == Constants.ORDER_TAB_TITLE_ID[1]) {
                text_item_order_feedback.setVisibility(View.VISIBLE);
                text_item_order_recommend.setVisibility(View.VISIBLE);
                text_item_order_recommend_state.setVisibility(View.VISIBLE);
            } else if (fragment_id == Constants.ORDER_TAB_TITLE_ID[2]) {
                text_item_order_feedback.setVisibility(View.VISIBLE);
                text_item_order_recommend.setVisibility(View.GONE);
                text_item_order_recommend_state.setVisibility(View.VISIBLE);
            }
        }
    }

}
