package app.familyexpensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by user on 16/03/2018.
 */

public class dbHelper extends SQLiteOpenHelper {

    public static final String db_Name = "expenselist";
    public static final String table_Name = "expensetable";
    public static final String ID = "ID";
    public static final String person = "PERSON";
    public static final String date = "DATE";
    public static final String category = "CATEGORY";
    public static final String amount = "AMOUNT";


    public dbHelper(Context context) {
        super(context, db_Name, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ table_Name+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,PERSON TEXT,DATE TEXT ,CATEGORY TEXT, AMOUNT DOUBLE);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ table_Name);
        onCreate(db);

    }

    public boolean insertData(String ePerson, String eDate, String eCategory,Double eAmount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(person,ePerson);
        contentValues.put(date,eDate);
        contentValues.put(category,eCategory);
        contentValues.put(amount,eAmount);
       long result = db.insert(table_Name,null,contentValues);

       if(result ==-1)
           return false;
       else
           return true;


    }
    public Cursor getData(String person,String date,String category){
        String [] args = {person,date,category};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM expensetable WHERE PERSON = ?  AND  DATE >= ? AND CATEGORY = ? ORDER BY DATE DESC",args);
        return res;

    }

    public Cursor getData(String person,String date){
        String [] args = {person,date};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM expensetable WHERE PERSON = ?  AND  DATE >= ? ORDER BY DATE DESC",args);
        return res;

    }
}
