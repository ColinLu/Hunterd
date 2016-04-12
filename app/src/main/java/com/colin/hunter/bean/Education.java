package com.colin.hunter.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/1/28.
 */
public class Education implements Parcelable{
    private int id ;
    private String name ;
    private int degree ;//学位 0本科，1硕士，2博士
    private String school_name;

    public Education() {
    }

    public Education(int id, String name, int degree, String school_name) {
        this.id = id;
        this.name = name;
        this.degree = degree;
        this.school_name = school_name;
    }

    protected Education(Parcel in) {
        id = in.readInt();
        name = in.readString();
        degree = in.readInt();
        school_name = in.readString();
    }

    public static final Creator<Education> CREATOR = new Creator<Education>() {
        @Override
        public Education createFromParcel(Parcel in) {
            return new Education(in);
        }

        @Override
        public Education[] newArray(int size) {
            return new Education[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Education education = (Education) o;

        if (id != education.id) return false;
        if (degree != education.degree) return false;
        if (name != null ? !name.equals(education.name) : education.name != null) return false;
        return !(school_name != null ? !school_name.equals(education.school_name) : education.school_name != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + degree;
        result = 31 * result + (school_name != null ? school_name.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(degree);
        dest.writeString(school_name);
    }
}
