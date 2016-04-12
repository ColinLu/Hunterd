package com.colin.hunter.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable{
	public City() {
	}

	private int id; // 城市编号

	private String name; // 城市名称

	private int longitude; // 城市中心经度

	private int latitude; // 城市中心纬度

	private int popular; // 是否热门城市

	private String sortLetters; // 显示数据拼音的首字母

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

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public int getPopular() {
		return popular;
	}

	public void setPopular(int popular) {
		this.popular = popular;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", popular=" + popular + ", sortLetters=" + sortLetters + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeInt(longitude);
		dest.writeInt(latitude);
		dest.writeInt(popular);
		dest.writeString(sortLetters);
	}

	protected City(Parcel in) {
		id = in.readInt();
		name = in.readString();
		longitude = in.readInt();
		latitude = in.readInt();
		popular = in.readInt();
		sortLetters = in.readString();
	}

	public static final Creator<City> CREATOR = new Creator<City>() {
		@Override
		public City createFromParcel(Parcel in) {
			return new City(in);
		}

		@Override
		public City[] newArray(int size) {
			return new City[size];
		}
	};
}
