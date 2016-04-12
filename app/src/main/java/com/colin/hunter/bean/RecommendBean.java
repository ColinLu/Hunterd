package com.colin.hunter.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/3/15.
 * <p>
 * 猎头推荐记录
 */
public class RecommendBean implements Parcelable{

    private int id;
    private PositionBean positionBean;

    private long recommend_start_time;//开始推荐时间
    private long recommend_finish_time;//完成推荐时间
    private long recommend_stop_time;//暂停推荐时间
    private long recommend_end_time;//终止推荐时间

    private int state = 0;//推荐状态  0进程中；1简历不合适；2面试不合适；3未过保障期

    public RecommendBean() {
    }

    public RecommendBean(int id, PositionBean positionBean, int state, long recommend_start_time) {
        this.id = id;
        this.positionBean = positionBean;
        this.state = state;
        this.recommend_start_time = recommend_start_time;
    }

    public RecommendBean(int id, PositionBean positionBean, long recommend_start_time, long recommend_finish_time, long recommend_stop_time, long recommend_end_time, int state) {
        this.id = id;
        this.positionBean = positionBean;
        this.recommend_start_time = recommend_start_time;
        this.recommend_finish_time = recommend_finish_time;
        this.recommend_stop_time = recommend_stop_time;
        this.recommend_end_time = recommend_end_time;
        this.state = state;
    }

    protected RecommendBean(Parcel in) {
        id = in.readInt();
        positionBean = in.readParcelable(PositionBean.class.getClassLoader());
        recommend_start_time = in.readLong();
        recommend_finish_time = in.readLong();
        recommend_stop_time = in.readLong();
        recommend_end_time = in.readLong();
        state = in.readInt();
    }

    public static final Creator<RecommendBean> CREATOR = new Creator<RecommendBean>() {
        @Override
        public RecommendBean createFromParcel(Parcel in) {
            return new RecommendBean(in);
        }

        @Override
        public RecommendBean[] newArray(int size) {
            return new RecommendBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PositionBean getPositionBean() {
        return positionBean;
    }

    public void setPositionBean(PositionBean positionBean) {
        this.positionBean = positionBean;
    }

    public long getRecommend_start_time() {
        return recommend_start_time;
    }

    public void setRecommend_start_time(long recommend_start_time) {
        this.recommend_start_time = recommend_start_time;
    }

    public long getRecommend_finish_time() {
        return recommend_finish_time;
    }

    public void setRecommend_finish_time(long recommend_finish_time) {
        this.recommend_finish_time = recommend_finish_time;
    }

    public long getRecommend_stop_time() {
        return recommend_stop_time;
    }

    public void setRecommend_stop_time(long recommend_stop_time) {
        this.recommend_stop_time = recommend_stop_time;
    }

    public long getRecommend_end_time() {
        return recommend_end_time;
    }

    public void setRecommend_end_time(long recommend_end_time) {
        this.recommend_end_time = recommend_end_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecommendBean that = (RecommendBean) o;

        if (id != that.id) return false;
        if (recommend_start_time != that.recommend_start_time) return false;
        if (recommend_finish_time != that.recommend_finish_time) return false;
        if (recommend_stop_time != that.recommend_stop_time) return false;
        if (recommend_end_time != that.recommend_end_time) return false;
        if (state != that.state) return false;
        return !(positionBean != null ? !positionBean.equals(that.positionBean) : that.positionBean != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (positionBean != null ? positionBean.hashCode() : 0);
        result = 31 * result + (int) (recommend_start_time ^ (recommend_start_time >>> 32));
        result = 31 * result + (int) (recommend_finish_time ^ (recommend_finish_time >>> 32));
        result = 31 * result + (int) (recommend_stop_time ^ (recommend_stop_time >>> 32));
        result = 31 * result + (int) (recommend_end_time ^ (recommend_end_time >>> 32));
        result = 31 * result + state;
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(positionBean, flags);
        dest.writeLong(recommend_start_time);
        dest.writeLong(recommend_finish_time);
        dest.writeLong(recommend_stop_time);
        dest.writeLong(recommend_end_time);
        dest.writeInt(state);
    }
}
