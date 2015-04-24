package nidaren.mydatabase.database;

import android.content.Context;
import nidaren.mydatabase.database.DataContract.DataEntry;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ni on 2015/4/15.
 */
public class DataDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DataDemo";

    public DataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        final String SQL_CREATE_DATABASE_TABLE = "CREATE TABLE " + DataEntry.TABLE_NAME + "(" +
                DataEntry._ID + " INTEGER PRIMARY KEY," +
                DataEntry.COLUMN_USER_NAME + " TEXT NOT NULL," +
                DataEntry.COLUMN_USER_NUMBER + " TEXT NOT NULL" +
                ")";
        sqLiteDatabase.execSQL(SQL_CREATE_DATABASE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldversion, int newversion){
        sqLiteDatabase.execSQL("DROP TABLE IF EXITS " + DataEntry.TABLE_NAME);
    }
}
