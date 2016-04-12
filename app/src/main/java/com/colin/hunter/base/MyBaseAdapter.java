package com.colin.hunter.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    protected Activity activity;
    protected LayoutInflater layoutInflater;
    protected List<T> itemList = new ArrayList<T>();

    public MyBaseAdapter(Activity activity, List<T> itemList) {
        this.activity = activity;
        this.itemList = itemList;
        this.layoutInflater = LayoutInflater.from(activity);
    }

    /**
     * 判断数据是否为空
     *
     * @return 为空返回true，不为空返回false
     */
    public boolean isEmpty() {
        return itemList.isEmpty();
    }

    /**
     * 在原有的数据上添加新数据
     *
     * @param itemList
     */
    public void addItems(List<T> itemList) {
        if (itemList != null && itemList.size() > 0) {
            for (T t : itemList) {
                if (!this.itemList.contains(t)) {
                    this.itemList.add(t);
                }
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 设置为新的数据，旧数据会被清空
     *
     * @param itemList
     */
    public void refreshItems(List<T> itemList) {
        if (itemList != null && itemList.size() > 0) {
            this.itemList.clear();
        }
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clearItems() {
        if (itemList != null && itemList.size() > 0) {
            this.itemList.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Override
    public T getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    abstract public View getView(int position, View convertView, ViewGroup viewGroup);
}
