package com.colin.hunter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.colin.hunter.data.Constants;
import com.colin.hunter.db.table.CityTable;
import com.colin.hunter.db.table.PersonTable;

/**
 * Created by Administrator on 2016/1/21.
 */
public class MySQLiteOpener extends SQLiteOpenHelper {
    private static MySQLiteOpener mySQLiteOpener;
    //  用户信息:根据用户ID创建
    static final String CREATE_PERSON_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + PersonTable.TABLE_NAME
            + "("
            + PersonTable.ID + " INTEGER PRIMARY KEY ,"
            + PersonTable.NAME + " TEXT,"
            + PersonTable.HEAD + " TEXT,"
            + PersonTable.GSON + " TEXT,"
            + PersonTable.IS_LOGIN + " BOOLEAN "
            + ");";
    //ID自增长
//    static final String CREATE_PERSON_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + PersonTable.TABLE_NAME
//            + "(_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + PersonTable.ID + " INTEGER , "
//            + PersonTable.NAME + " TEXT , "
//            + PersonTable.HEAD + " TEXT , "
//            + PersonTable.GSON + " TEXT, "
//            + PersonTable.IS_LOGIN + " BOOLEAN" +
//            ")";

//  城市信息:根据城市ID创建
    public static final String CREATE_CITY_TABLE_SQL = "CREATE TABLE " + CityTable.TABLE_NAME
            + "("
            + CityTable.CITY_ID + " INTEGER PRIMARY KEY ,"
            + CityTable.CITY_NAME + " TEXT ,"
            + CityTable.LONGITUDE + " INTEGER ,"
            + CityTable.LATITUDE + " INTEGER ,"
            + CityTable.POPULAR + " INTEGER ,"
            + CityTable.HEADER + " TEXT "
            + ");";

    /**
     * 线程安全 并发
     * 以后从AppLication中获取实例
     *
     * @param context
     * @return
     */
    public static MySQLiteOpener getInstance(Context context) {
        if (mySQLiteOpener == null) {
            synchronized (MySQLiteOpener.class) {
                if (mySQLiteOpener == null) {
                    mySQLiteOpener = new MySQLiteOpener(context);
                }
            }
        }
        return mySQLiteOpener;
    }

    public MySQLiteOpener(Context context) {
        super(context, Constants.SQLITE_NAME, null, Constants.SQLITE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        //开启事务
//        db.beginTransaction();
//        try {
//            db.execSQL(CREATE_PERSON_TABLE_SQL);
//            db.execSQL(CREATE_CITY_TABLE_SQL);
//            //设置事务标志为成功，当结束事务时就会提交事务
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            //结束事务
//            db.endTransaction();
//            db.close();
//        }

        db.execSQL(CREATE_PERSON_TABLE_SQL);
        db.execSQL(CREATE_CITY_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + PersonTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + CityTable.TABLE_NAME);
        }
    }
}
