package com.colin.hunter.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.colin.hunter.R;
import com.colin.hunter.help.WindowsHelp;

import java.util.ArrayList;
import java.util.List;


public class ListViewPopupwindow extends PopupWindow {
    public interface OnListViewInPopupwindowListener {
        void onItemCheck(int position, String string);
    }

    private Activity activity;
    private View view;
    private TextView text_pop_title;
    private ListView list_pop_content;
    private OnListViewInPopupwindowListener onListViewInPopupwindowListener = null;

    public ListViewPopupwindow(Activity activity, List<String> stirngList, String title) {
        super(activity);
        this.activity = activity;
        initView(title);
        initData(stirngList);
    }

    public ListViewPopupwindow(Activity activity, String[] strings, String title) {
        super(activity);
        this.activity = activity;
        initView(title);
        List<String> list = new ArrayList<String>();
        for (String string : strings) {
            list.add(string);
        }
        initData(list);
    }

    public ListViewPopupwindow(Activity activity, String title, int array_id) {
        super(activity);
        this.activity = activity;
        initView(title);
        String[] array_string = activity.getResources().getStringArray(array_id);
        List<String> list = new ArrayList<String>();
        for (String string : array_string) {
            list.add(string);
        }
        initData(list);
    }

    private void initView(String title) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.popupwindow_listview_choose, null);
        this.text_pop_title = (TextView) view.findViewById(R.id.text_pop_title);
        this.list_pop_content = (ListView) view.findViewById(R.id.list_pop_content);
        text_pop_title.setText(title);
        // 设置允许在外点击消失
        this.setOutsideTouchable(false);
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
//        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setHeight((WindowsHelp.getWindowsHeight(activity) / 5) * 2);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(android.R.style.Animation_InputMethod);
        // 设置背景色
        Resources resources = view.getResources();
        Drawable drawable = resources.getDrawable(android.R.color.white);
        this.setBackgroundDrawable(drawable);
    }

    private void initData(final List<String> stirngList) {
        list_pop_content.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_single_choice, android.R.id.text1,
                stirngList));
        list_pop_content.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onListViewInPopupwindowListener != null) {
                    onListViewInPopupwindowListener.onItemCheck(position, stirngList.get(position));
                    dismiss();
                }
            }
        });
    }

    public void setOnListViewInPopupwindowListener(OnListViewInPopupwindowListener listener) {
        onListViewInPopupwindowListener = listener;
    }

}
