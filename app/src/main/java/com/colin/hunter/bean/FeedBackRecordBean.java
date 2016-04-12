package com.colin.hunter.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/3/14.
 */
public class FeedBackRecordBean implements Parcelable{
    private int id ;
    private int human_resources_id;//人事的id
    private int hunter_id;//猎头的id
    private int candidate_id ;//候选人的id
    private String human_resources_name ;//人事的名字
    private String candidate_name ;///候选人的名字
    private String feedback_title ;//反馈的标签、标题
    private String feedback_content ;//反馈的内容
    private long time;//反馈的时间


    public FeedBackRecordBean(int id, int human_resources_id, int hunter_id, int candidate_id, String human_resources_name, String candidate_name, String feedback_title, String feedback_content, long time) {
        this.id = id;
        this.human_resources_id = human_resources_id;
        this.hunter_id = hunter_id;
        this.candidate_id = candidate_id;
        this.human_resources_name = human_resources_name;
        this.candidate_name = candidate_name;
        this.feedback_title = feedback_title;
        this.feedback_content = feedback_content;
        this.time = time;
    }

    protected FeedBackRecordBean(Parcel in) {
        id = in.readInt();
        human_resources_id = in.readInt();
        hunter_id = in.readInt();
        candidate_id = in.readInt();
        human_resources_name = in.readString();
        candidate_name = in.readString();
        feedback_title = in.readString();
        feedback_content = in.readString();
        time = in.readLong();
    }

    public static final Creator<FeedBackRecordBean> CREATOR = new Creator<FeedBackRecordBean>() {
        @Override
        public FeedBackRecordBean createFromParcel(Parcel in) {
            return new FeedBackRecordBean(in);
        }

        @Override
        public FeedBackRecordBean[] newArray(int size) {
            return new FeedBackRecordBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHuman_resources_id() {
        return human_resources_id;
    }

    public void setHuman_resources_id(int human_resources_id) {
        this.human_resources_id = human_resources_id;
    }

    public int getHunter_id() {
        return hunter_id;
    }

    public void setHunter_id(int hunter_id) {
        this.hunter_id = hunter_id;
    }

    public int getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(int candidate_id) {
        this.candidate_id = candidate_id;
    }

    public String getHuman_resources_name() {
        return human_resources_name;
    }

    public void setHuman_resources_name(String human_resources_name) {
        this.human_resources_name = human_resources_name;
    }

    public String getCandidate_name() {
        return candidate_name;
    }

    public void setCandidate_name(String candidate_name) {
        this.candidate_name = candidate_name;
    }

    public String getFeedback_title() {
        return feedback_title;
    }

    public void setFeedback_title(String feedback_title) {
        this.feedback_title = feedback_title;
    }

    public String getFeedback_content() {
        return feedback_content;
    }

    public void setFeedback_content(String feedback_content) {
        this.feedback_content = feedback_content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedBackRecordBean that = (FeedBackRecordBean) o;

        if (id != that.id) return false;
        if (human_resources_id != that.human_resources_id) return false;
        if (hunter_id != that.hunter_id) return false;
        if (candidate_id != that.candidate_id) return false;
        if (time != that.time) return false;
        if (human_resources_name != null ? !human_resources_name.equals(that.human_resources_name) : that.human_resources_name != null)
            return false;
        if (candidate_name != null ? !candidate_name.equals(that.candidate_name) : that.candidate_name != null)
            return false;
        if (feedback_title != null ? !feedback_title.equals(that.feedback_title) : that.feedback_title != null)
            return false;
        return !(feedback_content != null ? !feedback_content.equals(that.feedback_content) : that.feedback_content != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + human_resources_id;
        result = 31 * result + hunter_id;
        result = 31 * result + candidate_id;
        result = 31 * result + (human_resources_name != null ? human_resources_name.hashCode() : 0);
        result = 31 * result + (candidate_name != null ? candidate_name.hashCode() : 0);
        result = 31 * result + (feedback_title != null ? feedback_title.hashCode() : 0);
        result = 31 * result + (feedback_content != null ? feedback_content.hashCode() : 0);
        result = 31 * result + (int) (time ^ (time >>> 32));
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(human_resources_id);
        dest.writeInt(hunter_id);
        dest.writeInt(candidate_id);
        dest.writeString(human_resources_name);
        dest.writeString(candidate_name);
        dest.writeString(feedback_title);
        dest.writeString(feedback_content);
        dest.writeLong(time);
    }
}
