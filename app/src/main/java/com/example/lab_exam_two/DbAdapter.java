package com.example.lab_exam_two;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class DbAdapter {
    myDbHelper myhelper;
    static final String TABLE_NAME = "friends";
    static class myDbHelper extends SQLiteOpenHelper
    {
        static final String DATABASE_NAME = "myDatabase";
        static final String TABLE_NAME = "friends";
        static final int DATABASE_Version = 3;
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
        contentValues.put(myhelper.NAME, name);
        contentValues.put(myhelper.NUMBER, number);
        contentValues.put(myhelper.EMAIL, email);
        long id = dbb.insert(myhelper.TABLE_NAME, null , contentValues);
        return id;
    }


    public Cursor getData(){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor cs = db.rawQuery("select * from friends", null);
        return cs;
    }


    public int delete(String deleteName, Context context)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={deleteName};

        int count =db.delete(myDbHelper.TABLE_NAME , myDbHelper.NAME+" = ?",whereArgs);
        return  count;
    }

    public void updatePersonRecord(String friendToUpdate, Context context, RetriveParams updated) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME+" SET name ='"+ updated.getName() + "', number ='" + updated.getNumber()+ "', email ='"+ updated.getEmail() + "'  WHERE name='" + friendToUpdate + "'");

    }

}
