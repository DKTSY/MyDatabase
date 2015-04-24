package nidaren.mydatabase.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ni on 2015/4/15.
 */
public class DataContract {
    public static final String CONTENT_AUTHORITY = "nidaren.mydatabse";
    public static final String PATH_CONTACTS = "datademo";

    public static final class DataEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/" + PATH_CONTACTS);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_CONTACTS;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item" + CONTENT_AUTHORITY + "/" + PATH_CONTACTS;
        public static final String TABLE_NAME = "datademo";
        public static final String COLUMN_USER_NAME = "name";
        public static final String COLUMN_USER_NUMBER = "number";
    }
}

