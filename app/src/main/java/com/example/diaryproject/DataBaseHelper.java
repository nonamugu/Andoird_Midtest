package com.example.diaryproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

//데이터 관리 클래스
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final  int DB_VERSION = 1;
    private static final String DB_NAME = "MyDiary.db";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //테이블 생성 (최초 1회만 생성)
        db.execSQL("CREATE TABLE IF NOT EXISTS DiaryInfo(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "title2 TEXT NOT NULL, " +
                "content TEXT NOT NULL, " +
                "weatherType INTEGER NOT NULL, " +
                "userDate TEXT NOT NULL, " +
                "userDate2 TEXT NOT NULL, " +
                "writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //데이터를 db에 저장 (insert)
    public void setInsertDiaryList(String _title, String _title2, String _content, int _weatherType, String _userDate, String _userDate2, String _writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO DiaryInfo (title, title2, content, weatherType, userDate, userDate2, writeDate) VALUES('" + _title + "','" + _title2 + "','" + _content + "', '" + _weatherType + "', '" + _userDate + "','" + _userDate2 + "', '" + _writeDate + "')");

    }

    // 데이터 조회 기능 select read

    public ArrayList<DiaryModel> getDiaryListFromDB(){
        ArrayList<DiaryModel> lstDiary = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DiaryInfo ORDER BY writeDate DESC", null);
        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String title2 = cursor.getString(cursor.getColumnIndexOrThrow("title2"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                int weatherType = cursor.getInt(cursor.getColumnIndexOrThrow("weatherType"));
                String userDate = cursor.getString(cursor.getColumnIndexOrThrow("userDate"));
                String userDate2 = cursor.getString(cursor.getColumnIndexOrThrow("userDate2"));
                String writeDate = cursor.getString(cursor.getColumnIndexOrThrow("writeDate"));

                //create data class
                DiaryModel diaryModel = new DiaryModel();
                diaryModel.setId(id);
                diaryModel.setTitle(title);
                diaryModel.setTitle2(title2);
                diaryModel.setContent(content);
                diaryModel.setWeatherType(weatherType);
                diaryModel.setUserDate(userDate);
                diaryModel.setUserDate2(userDate2);
                diaryModel.setWriteDate(writeDate);

                lstDiary.add(diaryModel);

            }
        }
        cursor.close();

        return lstDiary;
    }
    // 수정 메소드 UPDATE
    public void setUpdateDiaryList(String _title, String _title2, String _content, int _weatherType, String _userDate, String _userDate2, String _writeDate, String _beforeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE DiaryInfo SET title = '" + _title + "' , title2 = '" + _title2 + "' , content = '" + _content + "', weatherType = '" + _weatherType + "', userDate = '" + _userDate + "', userDate2 = '" + _userDate2 + "', writeDate = '" + _writeDate + "' WHERE writeDate = '" + _beforeDate + "' ");
    }

    // 기존 데이터 삭제 메소드 - DLELTE
    public void setDeleteDiaryList(String _writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM DiaryInfo WHERE writeDate = '" + _writeDate + "'");
    }
}
