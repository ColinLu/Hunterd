package com.colin.hunter.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.colin.hunter.base.BaseApplication;
import com.colin.hunter.bean.Person;
import com.colin.hunter.db.MySQLiteOpener;
import com.colin.hunter.db.table.PersonTable;
import com.colin.hunter.help.LogHelp;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2016/1/21.
 */
public class PersonDao {

    private static MySQLiteOpener helper = null;
    private static PersonDao personDao;

    public PersonDao(Context context) {
        helper = BaseApplication.getInstance().getMySQLiteOpener(context);
    }

    public static PersonDao getInstance(Context context) {
        if (personDao == null) {
            synchronized (PersonDao.class) {
                if (personDao == null) {
                    personDao = new PersonDao(context);
                }
            }
        }
        return personDao;
    }

    private static SQLiteDatabase getWsd() {
        return helper.getWritableDatabase();
    }

    private static SQLiteDatabase getRsd() {
        return helper.getReadableDatabase();
    }


    public static boolean addPerson(Person person) {
        boolean flag = false;
        SQLiteDatabase database = getWsd();
        long change_id = -2;
        try {
            database = helper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(PersonTable.ID, person.getId());
            contentValues.put(PersonTable.NAME, person.getName());
            contentValues.put(PersonTable.HEAD, person.getHead());
            contentValues.put(PersonTable.GSON, new Gson().toJson(person));
            contentValues.put(PersonTable.IS_LOGIN, person.isLogin());
            change_id = database.replace(PersonTable.TABLE_NAME, null, contentValues);
            flag = (change_id != -2 ? true : false);
            LogHelp.d(String.valueOf(change_id));
        } catch (Exception e) {
            LogHelp.d(String.valueOf(e.getMessage()));
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    public static boolean addPerson(List<Person> personList) {
        boolean flag = false;
        return flag;
    }

    public static boolean removePerson(int person_id) {
        boolean flag = false;
        return flag;
    }

    public static boolean removePerson(Person person) {
        boolean flag = false;
        return flag;
    }

    public static boolean removePerson() {
        boolean flag = false;
        return flag;
    }


    public static Person getPerson(int person_id) {
        Person person = null;
        return person;
    }

    public static List<Person> getPerson() {
        List<Person> personList = null;
        return personList;
    }
}
