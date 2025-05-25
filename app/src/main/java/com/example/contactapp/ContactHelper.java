package com.example.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ContactHelper extends SQLiteOpenHelper {


    private final static String DATABASE="DATABASE";
    private  final static int VERSION=2;


    public ContactHelper(@Nullable Context context) {
        super(context, DATABASE, null, VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table my_table(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,number TEXT ,email TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists my_table");

    }


    public String getAllContactsAsText() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM my_table ORDER BY id DESC", null);
        StringBuilder sb = new StringBuilder();

        while (cursor.moveToNext()) {
            sb.append("Name: ").append(cursor.getString(1)).append("\n")
                    .append("Phone: ").append(cursor.getString(2)).append("\n")
                    .append("Email: ").append(cursor.getString(3)).append("\n\n");
        }

        cursor.close();
        return sb.toString();
    }


    public void insertData(String name,String number,String email){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("number",number);
        contentValues.put("email",email);
        database.insert("my_table ",null,contentValues);




    }

    public Cursor  showData(){

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from my_table order by id desc ",null);

        return cursor;





    }

    public void deleteData(String id){



        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("my_table","id=?",new String[]{id});




    }
    public void updateData(String name,String number,String email,String id){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("number",number);
        contentValues.put("email",email);

        sqLiteDatabase.update("my_table",contentValues,"id=?",new String[]{id});




    }

}