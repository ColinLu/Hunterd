package com.colin.hunter.myinterface;

/**
 * Created by Administrator on 2016/3/15.
 */
public interface OnRecyclerClickListener {
    //view_id编号；item位置，判断,回调数据
    void onClick(int view_id, int position, boolean b, Object object);
}
