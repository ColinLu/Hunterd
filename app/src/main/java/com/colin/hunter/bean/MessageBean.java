package com.colin.hunter.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/1/25.
 */
public class MessageBean implements Parcelable {
    /**
     * 消息编号
     */
    private int id;
    /**
     * 当前用户编号
     */
    private int user_id;
    /**
     * 当前用户头像
     */
    private String user_head;
    /**
     * 发送人的用户编号
     */
    private int send_id;
    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息时间
     */
    private long time;
    /**
     * 消息来源
     */
    private String from;

    /**
     * 消息类型
     */
    private Type type;

    public MessageBean() {
        super();
    }


    protected MessageBean(Parcel in) {
        id = in.readInt();
        user_id = in.readInt();
        user_head = in.readString();
        send_id = in.readInt();
        content = in.readString();
        time = in.readLong();
        from = in.readString();
    }

    public static final Creator<MessageBean> CREATOR = new Creator<MessageBean>() {
        @Override
        public MessageBean createFromParcel(Parcel in) {
            return new MessageBean(in);
        }

        @Override
        public MessageBean[] newArray(int size) {
            return new MessageBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_head() {
        return user_head;
    }

    public void setUser_head(String user_head) {
        this.user_head = user_head;
    }

    public int getSend_id() {
        return send_id;
    }

    public void setSend_id(int send_id) {
        this.send_id = send_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageBean that = (MessageBean) o;

        if (id != that.id) return false;
        if (user_id != that.user_id) return false;
        if (send_id != that.send_id) return false;
        if (time != that.time) return false;
        if (user_head != null ? !user_head.equals(that.user_head) : that.user_head != null)
            return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        return type == that.type;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + user_id;
        result = 31 * result + (user_head != null ? user_head.hashCode() : 0);
        result = 31 * result + send_id;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(user_id);
        dest.writeString(user_head);
        dest.writeInt(send_id);
        dest.writeString(content);
        dest.writeLong(time);
        dest.writeString(from);
    }

    /**
     * 消息类型
     * 客户端，人事,用户，猎头，
     */
    public enum Type {
        CLIENT(0), HR(1), USER(2), HEADHUNTING(3);

        // 默认客户端消息
        private int value = 0;

        // 必须是private的，否则编译错误
        private Type(int value) {
            this.value = value;
        }

        // 手写的从int到enum的转换函数
        public static Type valueOf(int value) {
            switch (value) {
                case 0:
                    return CLIENT;
                case 1:
                    return HR;
                case 2:
                    return USER;
                case 3:
                    return HEADHUNTING;
                default:
                    return CLIENT;
            }
        }

        public int value() {
            return this.value;
        }
    }

}
