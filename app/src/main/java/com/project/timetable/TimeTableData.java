package com.project.timetable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by hp on 10/12/2016.
 */
public class TimeTableData {
    public static final String KEY_TIME="time";
    public static final String KEY_COURSE="course";
    public static final String KEY_CLASSROOM="classroom";

    private static final String DATABASE_NAME="TimeTable";
    private static final int DATABASE_VERSION=1;
    private static final String MONDAY="Monday";
    private static final String TUESDAY="Tuesday";
    private static final String WEDNESDAY="Wednesday";
    private static final String THURSDAY="Thursday";
    private static final String FRIDAY="Friday";
    private static final String SATURDAY="Saturday";

    private DbHelper ourHelper;
    private  Context ourContext;
    private  SQLiteDatabase ourDatabase;

    public TimeTableData(Context c){
        ourContext=c;
    }

    private static class DbHelper extends SQLiteOpenHelper{

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+MONDAY +" ( "+KEY_TIME+" TEXT , "+
                    KEY_COURSE+" TEXT DEFAULT '----' , "+KEY_CLASSROOM+" TEXT DEFAULT '----'"+" );"
            );

            db.execSQL("CREATE TABLE "+TUESDAY +" ( "+KEY_TIME+" TEXT , "+
                    KEY_COURSE+" TEXT DEFAULT '----' , "+KEY_CLASSROOM+" TEXT DEFAULT '----'"+" );"
            );

            db.execSQL("CREATE TABLE "+WEDNESDAY +" ( "+KEY_TIME+" TEXT , "+
                    KEY_COURSE+" TEXT DEFAULT '----' , "+KEY_CLASSROOM+" TEXT DEFAULT '----'"+" );"
            );

            db.execSQL("CREATE TABLE "+THURSDAY +" ( "+KEY_TIME+" TEXT , "+
                    KEY_COURSE+" TEXT DEFAULT '----' , "+KEY_CLASSROOM+" TEXT DEFAULT '----'"+" );"
            );

            db.execSQL("CREATE TABLE "+FRIDAY +" ( "+KEY_TIME+" TEXT , "+
                    KEY_COURSE+" TEXT DEFAULT '----' , "+KEY_CLASSROOM+" TEXT DEFAULT '----'"+" );"
            );

            db.execSQL("CREATE TABLE "+SATURDAY +" ( "+KEY_TIME+" TEXT , "+
                    KEY_COURSE+" TEXT DEFAULT '----' , "+KEY_CLASSROOM+" TEXT DEFAULT '----'"+" );"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+MONDAY);
            db.execSQL("DROP TABLE IF EXISTS "+TUESDAY);
            db.execSQL("DROP TABLE IF EXISTS "+WEDNESDAY);
            db.execSQL("DROP TABLE IF EXISTS "+THURSDAY);
            db.execSQL("DROP TABLE IF EXISTS "+FRIDAY);
            db.execSQL("DROP TABLE IF EXISTS "+SATURDAY);
            onCreate(db);
        }
    }

    public TimeTableData open(String day) throws SQLException{
        ourHelper=new DbHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        if((getSomeTime(day)==false)){
            createLoops();
        }
        return this;
    }

    private void createLoops() {
        String[] time={"08:00-09:00","09:00-10:00","10:00-11:00","11:00-12:00","12:00-01:00","02:00-03:00",
                "03:00-04:00","04:00-05:00"};
        for(int i=0;i<8;i++){
            createEntry(time[i],MONDAY);
        }
        for(int i=0;i<8;i++){
            createEntry(time[i],TUESDAY);
        }
        for(int i=0;i<8;i++){
            createEntry(time[i],WEDNESDAY);
        }
        for(int i=0;i<8;i++){
            createEntry(time[i],THURSDAY);
        }
        for(int i=0;i<8;i++){
            createEntry(time[i],FRIDAY);
        }
        for(int i=0;i<8;i++){
            createEntry(time[i],SATURDAY);
        }
    }

    private long createEntry(String time, String tb) {
        ContentValues cv=new ContentValues();
        cv.put(KEY_TIME,time);
        return ourDatabase.insert(tb,null,cv);
    }

    public void close(){
        ourHelper.close();
    }

    public void updateEntry(String tim, String cou, String clas,String table) throws SQLException{
        ContentValues cv=new ContentValues();
        cv.put(KEY_COURSE,cou);
        cv.put(KEY_CLASSROOM,clas);
        switch (table){
            case "Monday":
                ourDatabase.update(MONDAY,cv,KEY_TIME+"=?",new String[]{tim});
                break;
            case "Tuesday":
                ourDatabase.update(TUESDAY, cv, KEY_TIME+"=?", new String[]{tim});
                break;
            case "Wednesday":
                ourDatabase.update(WEDNESDAY, cv, KEY_TIME+"=?", new String[]{tim});
                break;
            case "Thursday":
                ourDatabase.update(THURSDAY, cv, KEY_TIME+"=?", new String[]{tim});
                break;
            case "Friday":
                ourDatabase.update(FRIDAY, cv, KEY_TIME+"=?", new String[]{tim});
                break;
            case "Saturday":
                ourDatabase.update(SATURDAY, cv, KEY_TIME+"=?", new String[]{tim});
        }
    }

    public String getData(String table){
        String[] columns=new String[]{KEY_TIME,KEY_COURSE,KEY_CLASSROOM};
        Cursor c=ourDatabase.query(true,table,columns,null,null,null,null,null,null);
        String result="";
        int icourse=c.getColumnIndex(KEY_COURSE);
        int iclass=c.getColumnIndex(KEY_CLASSROOM);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result=result+c.getString(icourse)+", "+c.getString(iclass)+"\n";
        }
        return result;
    }

    private boolean getSomeTime(String table) {
        String[] columns=new String[]{KEY_TIME,KEY_COURSE,KEY_CLASSROOM};
        Cursor c=ourDatabase.query(true,table,columns,null,null,null,null,null,null);
        int i=c.getCount();
        if(i==0)
            return false;
        else
            return true;
    }

    public void deleteTable(String day) {
        // TODO Auto-generated method stub
        ourDatabase.delete(day, null, null);
    }
}
