package nidaren.mydatabase.database;

import android.provider.BaseColumns;

/**
 * Created by ni on 2015/4/15.
 */
public class DataContract {

    public static final class DataEntry implements BaseColumns {
        public static  final String TABLE_NAME = "datademo";
        public static final String COLUMN_USER_NAME = "name";
        public static final String COLUMN_USER_NUMBER = "number";
    }
}
