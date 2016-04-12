package com.colin.hunter.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/1/28.
 */
public class PositionBean implements Parcelable {
    private int id;//编号
    private String name;// 工程总监
    private String position_name;//职位名称
    private String company_name;//公司名字
    private boolean is_annual_salary;//是年薪还是月薪
    private String report_to;//汇报对象：总经理
    private float max_salary;//薪水
    private float min_salary;//薪水
    private String work_place;//工作地方
    private int number;//录取人数
    private boolean can_edit;//是否能编辑
    private int state;//状态
    private boolean isOpen;//是否开放

    public PositionBean() {
    }

    public PositionBean(int id, String name, boolean is_annual_salary, String report_to, float max_salary, float min_salary, String work_place, int number, boolean can_edit, int state) {
        this.id = id;
        this.name = name;
        this.is_annual_salary = is_annual_salary;
        this.report_to = report_to;
        this.max_salary = max_salary;
        this.min_salary = min_salary;
        this.work_place = work_place;
        this.number = number;
        this.can_edit = can_edit;
        this.state = state;
    }

    public PositionBean(int id, String name, String position_name, String company_name, boolean is_annual_salary, String report_to, float max_salary, float min_salary, String work_place, int number, boolean can_edit, int state, boolean isOpen) {
        this.id = id;
        this.name = name;
        this.position_name = position_name;
        this.company_name = company_name;
        this.is_annual_salary = is_annual_salary;
        this.report_to = report_to;
        this.max_salary = max_salary;
        this.min_salary = min_salary;
        this.work_place = work_place;
        this.number = number;
        this.can_edit = can_edit;
        this.state = state;
        this.isOpen = isOpen;
    }

    protected PositionBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        position_name = in.readString();
        company_name = in.readString();
        is_annual_salary = in.readByte() != 0;
        report_to = in.readString();
        max_salary = in.readFloat();
        min_salary = in.readFloat();
        work_place = in.readString();
        number = in.readInt();
        can_edit = in.readByte() != 0;
        state = in.readInt();
        isOpen = in.readByte() != 0;
    }

    public static final Creator<PositionBean> CREATOR = new Creator<PositionBean>() {
        @Override
        public PositionBean createFromParcel(Parcel in) {
            return new PositionBean(in);
        }

        @Override
        public PositionBean[] newArray(int size) {
            return new PositionBean[size];
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

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public boolean is_annual_salary() {
        return is_annual_salary;
    }

    public void setIs_annual_salary(boolean is_annual_salary) {
        this.is_annual_salary = is_annual_salary;
    }

    public String getReport_to() {
        return report_to;
    }

    public void setReport_to(String report_to) {
        this.report_to = report_to;
    }

    public float getMax_salary() {
        return max_salary;
    }

    public void setMax_salary(float max_salary) {
        this.max_salary = max_salary;
    }

    public float getMin_salary() {
        return min_salary;
    }

    public void setMin_salary(float min_salary) {
        this.min_salary = min_salary;
    }

    public String getWork_place() {
        return work_place;
    }

    public void setWork_place(String work_place) {
        this.work_place = work_place;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isCan_edit() {
        return can_edit;
    }

    public void setCan_edit(boolean can_edit) {
        this.can_edit = can_edit;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PositionBean that = (PositionBean) o;

        if (id != that.id) return false;
        if (is_annual_salary != that.is_annual_salary) return false;
        if (Float.compare(that.max_salary, max_salary) != 0) return false;
        if (Float.compare(that.min_salary, min_salary) != 0) return false;
        if (number != that.number) return false;
        if (can_edit != that.can_edit) return false;
        if (state != that.state) return false;
        if (isOpen != that.isOpen) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (position_name != null ? !position_name.equals(that.position_name) : that.position_name != null)
            return false;
        if (company_name != null ? !company_name.equals(that.company_name) : that.company_name != null)
            return false;
        if (report_to != null ? !report_to.equals(that.report_to) : that.report_to != null)
            return false;
        return !(work_place != null ? !work_place.equals(that.work_place) : that.work_place != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (position_name != null ? position_name.hashCode() : 0);
        result = 31 * result + (company_name != null ? company_name.hashCode() : 0);
        result = 31 * result + (is_annual_salary ? 1 : 0);
        result = 31 * result + (report_to != null ? report_to.hashCode() : 0);
        result = 31 * result + (max_salary != +0.0f ? Float.floatToIntBits(max_salary) : 0);
        result = 31 * result + (min_salary != +0.0f ? Float.floatToIntBits(min_salary) : 0);
        result = 31 * result + (work_place != null ? work_place.hashCode() : 0);
        result = 31 * result + number;
        result = 31 * result + (can_edit ? 1 : 0);
        result = 31 * result + state;
        result = 31 * result + (isOpen ? 1 : 0);
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
        dest.writeString(position_name);
        dest.writeString(company_name);
        dest.writeByte((byte) (is_annual_salary ? 1 : 0));
        dest.writeString(report_to);
        dest.writeFloat(max_salary);
        dest.writeFloat(min_salary);
        dest.writeString(work_place);
        dest.writeInt(number);
        dest.writeByte((byte) (can_edit ? 1 : 0));
        dest.writeInt(state);
        dest.writeByte((byte) (isOpen ? 1 : 0));
    }
}
