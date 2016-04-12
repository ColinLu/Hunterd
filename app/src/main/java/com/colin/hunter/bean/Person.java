
package com.colin.hunter.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * A dummy item representing a piece of name.
 */
public class Person implements Parcelable {
    private int id;
    private String name;
    private int gender = 1; //0 女 ；1 男 ；
    private int age;
    private boolean is_annual_salary = true;//是年薪还是月薪
    private float present_salary;//现在薪水
    private float expect_salary;//期望薪水
    private String present_positon;//目前职位
    private String expect_positon;//期望职位
    private List<Education> educationList;//教育
    private List<WorkExperience> workExperienceList;//工作经历
    private String present_work_city;//目前工作城市
    private String expectt_work_city;//期望工作城市
    private String description;//推荐理由
    private List<String> labelList;//形容个人标签的；最多10个，最少不限；
    private String head;
    private boolean isLogin = false;

    public Person() {
    }

    public Person(int id, String name, String head) {
        this.id = id;
        this.name = name;
        this.head = head;
    }

    public Person(int id, String name, int gender, int age, boolean is_annual_salary, float present_salary, float expect_salary, String present_positon, String expect_positon, List<Education> educationList, List<WorkExperience> workExperienceList, String present_work_city, String expectt_work_city, String description, String head, boolean isLogin) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.is_annual_salary = is_annual_salary;
        this.present_salary = present_salary;
        this.expect_salary = expect_salary;
        this.present_positon = present_positon;
        this.expect_positon = expect_positon;
        this.educationList = educationList;
        this.workExperienceList = workExperienceList;
        this.present_work_city = present_work_city;
        this.expectt_work_city = expectt_work_city;
        this.description = description;
        this.head = head;
        this.isLogin = isLogin;
    }

    public Person(int id, String name, int gender, int age, boolean is_annual_salary, float present_salary, float expect_salary, String present_positon, String expect_positon, List<Education> educationList, List<WorkExperience> workExperienceList, String present_work_city, String expectt_work_city, String description, List<String> labelList, String head, boolean isLogin) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.is_annual_salary = is_annual_salary;
        this.present_salary = present_salary;
        this.expect_salary = expect_salary;
        this.present_positon = present_positon;
        this.expect_positon = expect_positon;
        this.educationList = educationList;
        this.workExperienceList = workExperienceList;
        this.present_work_city = present_work_city;
        this.expectt_work_city = expectt_work_city;
        this.description = description;
        this.labelList = labelList;
        this.head = head;
        this.isLogin = isLogin;
    }

    protected Person(Parcel in) {
        id = in.readInt();
        name = in.readString();
        gender = in.readInt();
        age = in.readInt();
        is_annual_salary = in.readByte() != 0;
        present_salary = in.readFloat();
        expect_salary = in.readFloat();
        present_positon = in.readString();
        expect_positon = in.readString();
        educationList = in.createTypedArrayList(Education.CREATOR);
        workExperienceList = in.createTypedArrayList(WorkExperience.CREATOR);
        present_work_city = in.readString();
        expectt_work_city = in.readString();
        description = in.readString();
        labelList = in.createStringArrayList();
        head = in.readString();
        isLogin = in.readByte() != 0;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean is_annual_salary() {
        return is_annual_salary;
    }

    public void setIs_annual_salary(boolean is_annual_salary) {
        this.is_annual_salary = is_annual_salary;
    }

    public float getPresent_salary() {
        return present_salary;
    }

    public void setPresent_salary(float present_salary) {
        this.present_salary = present_salary;
    }

    public float getExpect_salary() {
        return expect_salary;
    }

    public void setExpect_salary(float expect_salary) {
        this.expect_salary = expect_salary;
    }

    public String getPresent_positon() {
        return present_positon;
    }

    public void setPresent_positon(String present_positon) {
        this.present_positon = present_positon;
    }

    public String getExpect_positon() {
        return expect_positon;
    }

    public void setExpect_positon(String expect_positon) {
        this.expect_positon = expect_positon;
    }

    public List<Education> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<Education> educationList) {
        this.educationList = educationList;
    }

    public List<WorkExperience> getWorkExperienceList() {
        return workExperienceList;
    }

    public void setWorkExperienceList(List<WorkExperience> workExperienceList) {
        this.workExperienceList = workExperienceList;
    }

    public String getPresent_work_city() {
        return present_work_city;
    }

    public void setPresent_work_city(String present_work_city) {
        this.present_work_city = present_work_city;
    }

    public String getExpectt_work_city() {
        return expectt_work_city;
    }

    public void setExpectt_work_city(String expectt_work_city) {
        this.expectt_work_city = expectt_work_city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<String> labelList) {
        this.labelList = labelList;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(gender);
        dest.writeInt(age);
        dest.writeByte((byte) (is_annual_salary ? 1 : 0));
        dest.writeFloat(present_salary);
        dest.writeFloat(expect_salary);
        dest.writeString(present_positon);
        dest.writeString(expect_positon);
        dest.writeTypedList(educationList);
        dest.writeTypedList(workExperienceList);
        dest.writeString(present_work_city);
        dest.writeString(expectt_work_city);
        dest.writeString(description);
        dest.writeStringList(labelList);
        dest.writeString(head);
        dest.writeByte((byte) (isLogin ? 1 : 0));
    }
}