package com.colin.hunter.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/1/28.
 */
public class OrderBean implements Parcelable {
    private int id;//订单编号
    private long order_start_time;//订单生成时间
    private long order_get_time;//订单领取时间  猎头的某个候选人领取时间
    private long order_finish_time;//订单完成时间   猎头的某个候选人完成时间
    private long order_end_time;//订单结束时间    猎头的某个候选人结束时间：终止，不派单了其他因素造成派单不再继续了
    private PositionBean positionBean ;//职位

    public OrderBean() {
    }

    public OrderBean(int id, PositionBean positionBean) {
        this.id = id;
        this.positionBean = positionBean;
    }

    protected OrderBean(Parcel in) {
        id = in.readInt();
        order_start_time = in.readLong();
        order_get_time = in.readLong();
        order_finish_time = in.readLong();
        order_end_time = in.readLong();
        positionBean = in.readParcelable(PositionBean.class.getClassLoader());
    }

    public static final Creator<OrderBean> CREATOR = new Creator<OrderBean>() {
        @Override
        public OrderBean createFromParcel(Parcel in) {
            return new OrderBean(in);
        }

        @Override
        public OrderBean[] newArray(int size) {
            return new OrderBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getOrder_start_time() {
        return order_start_time;
    }

    public void setOrder_start_time(long order_start_time) {
        this.order_start_time = order_start_time;
    }

    public long getOrder_get_time() {
        return order_get_time;
    }

    public void setOrder_get_time(long order_get_time) {
        this.order_get_time = order_get_time;
    }

    public long getOrder_finish_time() {
        return order_finish_time;
    }

    public void setOrder_finish_time(long order_finish_time) {
        this.order_finish_time = order_finish_time;
    }

    public long getOrder_end_time() {
        return order_end_time;
    }

    public void setOrder_end_time(long order_end_time) {
        this.order_end_time = order_end_time;
    }

    public PositionBean getPositionBean() {
        return positionBean;
    }

    public void setPositionBean(PositionBean positionBean) {
        this.positionBean = positionBean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderBean orderBean = (OrderBean) o;

        if (id != orderBean.id) return false;
        if (order_start_time != orderBean.order_start_time) return false;
        if (order_get_time != orderBean.order_get_time) return false;
        if (order_finish_time != orderBean.order_finish_time) return false;
        if (order_end_time != orderBean.order_end_time) return false;
        return !(positionBean != null ? !positionBean.equals(orderBean.positionBean) : orderBean.positionBean != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (order_start_time ^ (order_start_time >>> 32));
        result = 31 * result + (int) (order_get_time ^ (order_get_time >>> 32));
        result = 31 * result + (int) (order_finish_time ^ (order_finish_time >>> 32));
        result = 31 * result + (int) (order_end_time ^ (order_end_time >>> 32));
        result = 31 * result + (positionBean != null ? positionBean.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(order_start_time);
        dest.writeLong(order_get_time);
        dest.writeLong(order_finish_time);
        dest.writeLong(order_end_time);
        dest.writeParcelable(positionBean, flags);
    }
}
