package com.colin.hunter.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.colin.hunter.base.BaseApplication;
import com.colin.hunter.bean.City;
import com.colin.hunter.db.MySQLiteOpener;
import com.colin.hunter.db.table.CityTable;
import com.colin.hunter.help.LogHelp;

import java.util.ArrayList;
import java.util.List;


/**
 * User: qii Date: 13-2-4
 */
public class CityDao {
    private static MySQLiteOpener helper = null;
    private static CityDao cityDao;


    public CityDao(Context context) {
        helper = BaseApplication.getInstance().getMySQLiteOpener(context);
    }

    public static CityDao getInstance(Context context) {
        if (cityDao == null) {
            synchronized (CityDao.class) {
                if (cityDao == null) {
                    cityDao = new CityDao(context);
                }
            }
        }
        return cityDao;
    }

    private static SQLiteDatabase getWsd() {
        return helper.getWritableDatabase();
    }

    private static SQLiteDatabase getRsd() {
        return helper.getReadableDatabase();
    }

    /**
     * 对外公开：添加城市集合数据到数据库中
     *
     * @param cityList
     * @return
     */
    public static boolean initCity(List<City> cityList) {
        boolean flag = false;
        List<ContentValues> contentValuesList = new ArrayList<>();
        contentValuesList.clear();
        ContentValues contentvalues = null;
        for (City city : cityList) {
            contentvalues = new ContentValues();
            contentvalues.put(CityTable.CITY_ID, city.getId());
            contentvalues.put(CityTable.CITY_NAME, city.getName());
            contentvalues.put(CityTable.LONGITUDE, city.getLongitude());
            contentvalues.put(CityTable.LATITUDE, city.getLatitude());
            contentvalues.put(CityTable.POPULAR, city.getPopular());
            contentvalues.put(CityTable.HEADER, city.getSortLetters());
            contentValuesList.add(contentvalues);
        }
        flag = setContentValuesList(contentValuesList);
        return flag;
    }

    /**
     * 对外公开
     *
     * @param city
     */
    public static boolean add(City city) {
        return add(getWsd(), city);
    }

    /**
     * 对外公开
     *
     * @param cities
     */
    public static void add(List<City> cities) {
        initCity(cities);
    }

    private static boolean setContentValuesList(List<ContentValues> contentValuesList) {
        boolean flag = false;
        long change_id = -1;
        SQLiteDatabase db = getWsd();
        if (db.isOpen()) {
            try {
                db.beginTransaction();
                for (ContentValues contentValues : contentValuesList) {
                    change_id = db.replace(CityTable.TABLE_NAME, CityTable.CITY_ID, contentValues);
                    LogHelp.d(String.valueOf(change_id));
                }
                flag = change_id == -1 ? false : true;
                LogHelp.d(String.valueOf(flag));
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            } finally {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
        return flag;
    }


    private static boolean add(SQLiteDatabase db, City city) {
        long change_id = -1;
        ContentValues cv = new ContentValues();
        cv.put(CityTable.CITY_ID, city.getId());
        cv.put(CityTable.CITY_NAME, city.getName());
        cv.put(CityTable.LONGITUDE, city.getLongitude());
        cv.put(CityTable.LATITUDE, city.getLatitude());
        cv.put(CityTable.POPULAR, city.getPopular());
        cv.put(CityTable.HEADER, city.getSortLetters());
        change_id = db.replace(CityTable.TABLE_NAME, CityTable.CITY_ID, cv);
        if (db != null) {
            db.close();
            db = null;
        }
        return change_id == -1 ? false : true;
    }

    public static List<City> getAll() {
        return get(getWsd());
    }

    /**
     * 获取全部城市数据
     *
     * @param db
     * @return
     */
    private static List<City> get(SQLiteDatabase db) {
        List<City> cityList = new ArrayList<City>();
        String sql = "SELECT * FROM " + CityTable.TABLE_NAME + " ORDER BY " + CityTable.CITY_ID;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            City city = new City();
            int city_id = cursor.getInt(cursor.getColumnIndex(CityTable.CITY_ID));
            String city_name = cursor.getString(cursor.getColumnIndex(CityTable.CITY_NAME));
            int longitude = cursor.getInt(cursor.getColumnIndex(CityTable.LONGITUDE));
            int latitude = cursor.getInt(cursor.getColumnIndex(CityTable.LATITUDE));
            int popular = cursor.getInt(cursor.getColumnIndex(CityTable.POPULAR));
            String header = cursor.getString(cursor.getColumnIndex(CityTable.HEADER));
            city.setId(city_id);
            city.setName(city_name);
            city.setLongitude(longitude);
            city.setLatitude(latitude);
            city.setPopular(popular);
            city.setSortLetters(header);
            cityList.add(city);
        }
        cursor.close();
        if (db != null) {
            db.close();
            db = null;
        }
        return cityList;
    }

    public static City get(String cityId) {
        return get(getWsd(), cityId);
    }

    private static City get(SQLiteDatabase db, String cityId) {
        List<City> msgList = new ArrayList<City>();
        String sql = "select * from " + CityTable.TABLE_NAME + " where " + CityTable.CITY_ID + " = " + cityId
                + " order by " + CityTable.CITY_ID + " desc";
        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()) {
            City city = new City();

            int city_id = c.getInt(c.getColumnIndex(CityTable.CITY_ID));
            String city_name = c.getString(c.getColumnIndex(CityTable.CITY_NAME));
            int longitude = c.getInt(c.getColumnIndex(CityTable.LONGITUDE));
            int latitude = c.getInt(c.getColumnIndex(CityTable.LATITUDE));
            int popular = c.getInt(c.getColumnIndex(CityTable.POPULAR));
            String header = c.getString(c.getColumnIndex(CityTable.HEADER));

            city.setId(city_id);
            city.setName(city_name);
            city.setLongitude(longitude);
            city.setLatitude(latitude);
            city.setPopular(popular);
            city.setSortLetters(header);

            msgList.add(city);
        }

        c.close();

        if (msgList.size() > 0) {
            return msgList.get(0);
        } else {
            return null;
        }
    }

    public static City getByName(String cityName) {
        return getByName(getWsd(), cityName);
    }

    public static City getByName(SQLiteDatabase db, String cityName) {
        List<City> msgList = new ArrayList<City>();
        String sql = "select * from " + CityTable.TABLE_NAME + " where " + CityTable.CITY_NAME + " = '" + cityName
                + "' order by " + CityTable.CITY_ID + " desc";
        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()) {
            City city = new City();

            int city_id = c.getInt(c.getColumnIndex(CityTable.CITY_ID));
            String city_name = c.getString(c.getColumnIndex(CityTable.CITY_NAME));
            int longitude = c.getInt(c.getColumnIndex(CityTable.LONGITUDE));
            int latitude = c.getInt(c.getColumnIndex(CityTable.LATITUDE));
            int popular = c.getInt(c.getColumnIndex(CityTable.POPULAR));
            String header = c.getString(c.getColumnIndex(CityTable.HEADER));

            city.setId(city_id);
            city.setName(city_name);
            city.setLongitude(longitude);
            city.setLatitude(latitude);
            city.setPopular(popular);
            city.setSortLetters(header);

            msgList.add(city);
        }

        c.close();

        if (msgList.size() > 0) {
            return msgList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 查询热门城市
     *
     * @return
     */
    public static List<City> getPopular() {
        return getPopular(getWsd());
    }

    public static List<City> getPopular(SQLiteDatabase db) {
        List<City> msgList = new ArrayList<City>();
        String sql = "select * from " + CityTable.TABLE_NAME + " where " + CityTable.POPULAR + " = 1 order by " + CityTable.HEADER;
        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()) {
            City city = new City();

            int city_id = c.getInt(c.getColumnIndex(CityTable.CITY_ID));
            String city_name = c.getString(c.getColumnIndex(CityTable.CITY_NAME));
            int longitude = c.getInt(c.getColumnIndex(CityTable.LONGITUDE));
            int latitude = c.getInt(c.getColumnIndex(CityTable.LATITUDE));
            int popular = c.getInt(c.getColumnIndex(CityTable.POPULAR));
            String header = c.getString(c.getColumnIndex(CityTable.HEADER));

            city.setId(city_id);
            city.setName(city_name);
            city.setLongitude(longitude);
            city.setLatitude(latitude);
            city.setPopular(popular);
            city.setSortLetters(header);

            msgList.add(city);
        }

        c.close();

        return msgList;
    }
}
