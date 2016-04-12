package com.colin.hunter.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/1/28.
 * 候选
 */
public class CandidateBean implements Parcelable {

    private int id;
    private int state;//候选人类型   4个状态:0放弃推荐，1offer阶段;2已入职;3已过保障期
    private Person person;
    private String introduce;//推荐理由

    public CandidateBean() {
    }

    public CandidateBean(int id, int state, Person person, String introduce) {
        this.id = id;
        this.state = state;
        this.person = person;
        this.introduce = introduce;
    }


    protected CandidateBean(Parcel in) {
        id = in.readInt();
        state = in.readInt();
        person = in.readParcelable(Person.class.getClassLoader());
        introduce = in.readString();
    }

    public static final Creator<CandidateBean> CREATOR = new Creator<CandidateBean>() {
        @Override
        public CandidateBean createFromParcel(Parcel in) {
            return new CandidateBean(in);
        }

        @Override
        public CandidateBean[] newArray(int size) {
            return new CandidateBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(state);
        dest.writeParcelable(person, flags);
        dest.writeString(introduce);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CandidateBean that = (CandidateBean) o;

        if (id != that.id) return false;
        if (state != that.state) return false;
        if (person != null ? !person.equals(that.person) : that.person != null) return false;
        return !(introduce != null ? !introduce.equals(that.introduce) : that.introduce != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + state;
        result = 31 * result + (person != null ? person.hashCode() : 0);
        result = 31 * result + (introduce != null ? introduce.hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
