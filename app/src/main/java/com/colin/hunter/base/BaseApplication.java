package com.colin.hunter.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.colin.hunter.R;
import com.colin.hunter.bean.City;
import com.colin.hunter.data.Constants;
import com.colin.hunter.db.MySQLiteOpener;
import com.colin.hunter.db.dao.CityDao;
import com.colin.hunter.help.ImageLoaderHelp;
import com.colin.hunter.help.LogHelp;
import com.colin.hunter.help.SharedPreferencesHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局变量，整个App运行的时候都会存在
 *
 * @author Colin
 */
public class BaseApplication extends Application {
    private static BaseApplication mBaseApplication = new BaseApplication();
    private MySQLiteOpener mySQLiteOpener = null;
    private List<Activity> activities = new ArrayList<Activity>();
    // 原sportguru变量
    public static Context applicationContext;
    /**
     * 是否登录
     */
    private boolean isLogin = false;

    public BaseApplication() {
        super();
    }

    private Context context;

    /**
     * 单例模式 构造方法
     *
     * @return mBaseApplication
     */
    public static BaseApplication getInstance() {
        return mBaseApplication;
    }

    @Override
    public void onCreate() {
        getInstance();
        init();
        super.onCreate();
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    /**
     * App启动初始化一些数据
     */
    private void init() {
        context = this;
//		初始化图片加载类
        ImageLoaderHelp.initImageLoader(getApplicationContext());
        //		初始化数据库
        initSqlite();
    }

    private void initSqlite() {
        if (!SharedPreferencesHelp.get(this, Constants.INIT_CITY_SQLITE, false)) {
            initCity();
        }

    }

    private void initCity() {
        String[] cityStringArray = this.getResources().getStringArray(R.array.array_cities);
        List<City> cityList = new ArrayList<City>();
        cityList.clear();
        int length = cityStringArray.length;
        for (int i = 0; i < length; i++) {
            String[] array_string = cityStringArray[i].split(",");
            int city_id = Integer.valueOf(array_string[0]).intValue();
            String city_name = array_string[1];
            int longitude = Integer.valueOf(array_string[2]).intValue();
            int latitude = Integer.valueOf(array_string[3]).intValue();
            int popular = Integer.valueOf(array_string[4]).intValue();
            String header = array_string[5];
            City city = new City();
            city.setId(city_id);
            city.setName(city_name);
            city.setLongitude(longitude);
            city.setLatitude(latitude);
            city.setPopular(popular);
            city.setSortLetters(header);
            cityList.add(city);
        }
        if (cityList != null && cityList.size() > 0) {
            boolean b = CityDao.getInstance(this).initCity(cityList);
            LogHelp.d(String.valueOf(b));
            SharedPreferencesHelp.put(this, Constants.INIT_CITY_SQLITE, b);
        }
    }


    /**
     * 管理数据库，初始化数据库
     *
     * @param context
     * @return
     */
    public MySQLiteOpener getMySQLiteOpener(Context context) {
        if (mySQLiteOpener == null) {
            mySQLiteOpener = MySQLiteOpener.getInstance(context);
        }
        return mySQLiteOpener;
    }


    public void exit() {
        // 关闭数据库,数据库账户信息推出状态
        setLogin(false);
        try {
            for (Activity activity : activities) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

}
