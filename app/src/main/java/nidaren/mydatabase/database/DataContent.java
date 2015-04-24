package nidaren.mydatabase.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by ni on 2015/4/24.
 */
public class DataContent extends ContentProvider {
    private DataDbHelper dbOpenHelper;
    private final static UriMatcher sUriMatcher = buildUriMatcher();
    private final static int DATA = 100;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DataContract.CONTENT_AUTHORITY;
        matcher.addURI(authority,DataContract.PATH_CONTACTS,DATA);
        return matcher;
    }

    @Override
    public boolean onCreate(){
        dbOpenHelper = new DataDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings2, String s2){
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        long id = 0;
        Uri returnUri;
        switch(sUriMatcher.match(uri)){
            case DATA:{
                id = db.insert(DataContract.DataEntry.TABLE_NAME, null, contentValues);
                if (id > 0)
                    returnUri = ContentUris.withAppendedId(uri, id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        return returnUri;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings){
        return 0;
    }

    @Override
    public  int delete(Uri uri, String s, String[] strings){
        return 0;
    }

    @Override
    public String getType(Uri uri){
        return null;
    }
}
