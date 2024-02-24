package com.example.touniversity2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String UN_DB = "university.db";
    private static final String UN_TABLE = "university";

    private static final int VERSION = 1;
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_POINTSFREE = "pointsf";
    private static final String COLUMN_POINTSPAID = "pointsp";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_CALLEDUNI = "called_uni";
    private static final String COLUMN_CALLEDPRO = "called_pro";
    private static final String COLUMN_LERNFORM = "learning_form";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_SUBJECT = "subject";
    private static final String COLUMN_LINK = "link";
    private static final String COLUMN_DESCLINK = "link";
    private static final String COLUMN_SUBJECT_VALUE = "subject_value";
    private static final String COLUMN_DVI = "dvi";

    public DbHelper(@Nullable Context context) {
        super(context, UN_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE " + UN_TABLE + " (" + COLUMN_CALLEDUNI + " TEXT, "
                +  COLUMN_CALLEDPRO + " TEXT, " +  COLUMN_POINTSFREE + " INTEGER, "
                +  COLUMN_POINTSPAID + " INTEGER, " +  COLUMN_SUBJECT + " TEXT, "
                +  COLUMN_LERNFORM + " INTEGER, " +  COLUMN_CITY + " TEXT, "
                +  COLUMN_PRICE + " TEXT, " + COLUMN_IMAGE + " TEXT, " + COLUMN_LINK + " TEXT, " + COLUMN_DESCLINK + " TEXT, " + COLUMN_SUBJECT_VALUE +  " INTEGER, "  + COLUMN_LINK + " TEXT);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void AddData(UniversityData universitydata){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CALLEDUNI, universitydata.getCalled_university());
        contentValues.put(COLUMN_CALLEDPRO, universitydata.getCalled_program());
        contentValues.put(COLUMN_POINTSFREE, universitydata.getPoint_tofree());
        contentValues.put(COLUMN_POINTSPAID, universitydata.getPoint_topaid());
        contentValues.put(COLUMN_SUBJECT, universitydata.getSubjectonEGE());
        contentValues.put(COLUMN_LERNFORM, universitydata.getLearning_form());
        contentValues.put(COLUMN_CITY, universitydata.getCity());
        contentValues.put(COLUMN_PRICE, universitydata.getPrice());
        contentValues.put(COLUMN_IMAGE, universitydata.getImage());
        contentValues.put(COLUMN_LINK, universitydata.getLink());
        contentValues.put(COLUMN_DESCLINK, universitydata.getDesclink());
        contentValues.put(COLUMN_SUBJECT_VALUE, universitydata.getSubject_value());
        contentValues.put(COLUMN_DVI, universitydata.getDvi());

        db.insert(UN_TABLE,null,contentValues);
       // db.close();
    }
    public LinkedList GetData(){
        LinkedList<UniversityData> list=  new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(UN_TABLE,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int id_calleduni = cursor.getColumnIndex(COLUMN_CALLEDUNI);
                int id_calledpro = cursor.getColumnIndex(COLUMN_CALLEDPRO);
                int id_pointsf = cursor.getColumnIndex(COLUMN_POINTSFREE);
                int id_pointsp = cursor.getColumnIndex(COLUMN_POINTSPAID);
                int id_subject = cursor.getColumnIndex(COLUMN_SUBJECT);
                int id_learning_form = cursor.getColumnIndex(COLUMN_LERNFORM);
                int id_city = cursor.getColumnIndex(COLUMN_CITY);
                int id_price = cursor.getColumnIndex(COLUMN_PRICE);
                int id_image = cursor.getColumnIndex(COLUMN_IMAGE);
                int id_link = cursor.getColumnIndex(COLUMN_LINK);
                int id_desclink = cursor.getColumnIndex(COLUMN_DESCLINK);
                int id_subject_value = cursor.getColumnIndex(COLUMN_SUBJECT_VALUE);
                int id_dvi = cursor.getColumnIndex(COLUMN_DVI);

                UniversityData universityData = new UniversityData(cursor.getString(id_calleduni),
                        cursor.getString(id_calledpro), cursor.getInt(id_pointsf),
                        cursor.getInt(id_pointsp), cursor.getString(id_subject),
                        cursor.getInt(id_learning_form), cursor.getString(id_city),
                        cursor.getInt(id_price), cursor.getString(id_image), cursor.getString(id_link),  cursor.getString(id_desclink), cursor.getInt(id_subject_value), cursor.getString(id_dvi));


                list.add(universityData);
            }while (cursor.moveToNext());
        }



        cursor.close();
        db.close();
        return list;
    }
    public void DeleteDb(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UN_TABLE, null,null);
        //db.close();


    }

}
