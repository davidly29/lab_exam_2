package com.example.lab_exam_two;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbAdapter {
    myDbHelper myhelper;

    static class myDbHelper extends SQLiteOpenHelper
    {
        static final String DATABASE_NAME = "myDatabase";
        static final String TABLE_NAME = "friends";
        static final int DATABASE_Version = 2;
        static final String UID="_id";
        static final String EMAIL="email";
        static final String NAME = "name";
        static final String NUMBER= "number";
        static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT," + NUMBER + " TEXT," + EMAIL + " TEXT" + ")";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {

                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {

            }
        }
    }

    public DbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }
    public long insertFriend(String name, String email, String number)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myhelper.EMAIL, email);
        contentValues.put(myhelper.NAME, name);
        contentValues.put(myhelper.NUMBER, number);
        long id = dbb.insert(myhelper.TABLE_NAME, null , contentValues);
        return id;
    }
    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myhelper.EMAIL, myhelper.NAME, myhelper.NUMBER};
        Cursor cursor =db.query(myhelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myhelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myhelper.NAME));
            String email =cursor.getString(cursor.getColumnIndex(myhelper.EMAIL));
            String number =cursor.getString(cursor.getColumnIndex(myhelper.NUMBER));
            buffer.append(cid+ "   " + name + "   " + email + "   " + number + " \n");
        }
        return buffer.toString();
    }

    public  int delete(String deleteName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={deleteName};

        int count =db.delete(myDbHelper.TABLE_NAME , myDbHelper.NAME+" = ?",whereArgs);
        return  count;
    }


}