package com.colin.hunter.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/1/28.
 *
 * 工作经历
 */
public final class WorkExperience implements Parcelable{

    private int id;
    private String company_name ;
    private String company_address;
    private float work_time ;

    public WorkExperience(int id, String company_name, String company_address, float work_time) {
        this.id = id;
        this.company_name = company_name;
        this.company_address = company_address;
        this.work_time = work_time;
    }

    protected WorkExperience(Parcel in) {
        id = in.readInt();
        company_name = in.readString();
        company_address = in.readString();
        work_time = in.readFloat();
    }

    public static final Creator<WorkExperience> CREATOR = new Creator<WorkExperience>() {
        @Override
        public WorkExperience createFromParcel(Parcel in) {
            return new WorkExperience(in);
        }

        @Override
        public WorkExperience[] newArray(int size) {
            return new WorkExperience[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public float getWork_time() {
        return work_time;
    }

    public void setWork_time(float work_time) {
        this.work_time = work_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(company_name);
        dest.writeString(company_address);
        dest.writeFloat(work_time);
    }
}
