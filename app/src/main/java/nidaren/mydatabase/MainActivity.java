package nidaren.mydatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.Statement;

import nidaren.mydatabase.database.DataContract;
import nidaren.mydatabase.database.DataDbHelper;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public static DataDbHelper mDataDbHelper;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mDataDbHelper = new DataDbHelper(getActivity());
            final EditText editText = (EditText)rootView.findViewById(R.id.editText);
            final TextView textView = (TextView)rootView.findViewById(R.id.res_textView);
            textView.setTextSize(10);
            Button add_button = (Button)rootView.findViewById(R.id.sql_add);
            add_button.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view){
                    String input = editText.getText().toString();
                    String[] lines = input.split(",");
                    insertData(lines);
                    textView.append("insert " + input + "\n\n");
                }
            });

            Button select_button = (Button)rootView.findViewById(R.id.sql_select);
            select_button.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view) {
                    String input = editText.getText().toString();
                    Cursor cur = selectData(input);
                    cur.moveToPosition(-1);
                    textView.append("select " + input + "\n");
                    while(cur.moveToNext())
                    {
                        textView.append(cur.getString(1) + " " + cur.getString(2) + "\n");
                    }
                       textView.append("\n");
                }
            });

            Button delete_button = (Button)rootView.findViewById(R.id.sql_delete);
            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String input = editText.getText().toString();
                    deleteData(input);
                    textView.append(input + " deleted\n\n");
                }
            });

            Button update_button = (Button)rootView.findViewById(R.id.sql_update);
            update_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String input = editText.getText().toString();
                    String[] line = input.split(" ");
                    updateData(line[0], line[1]);
                    textView.append("updated: set " + line[0] + " where " + line[1] + "\n\n");
                }
            });

            return rootView;
        }

        public static void updateData(String set, String conds){
            SQLiteDatabase sq = mDataDbHelper.getReadableDatabase();
            String sql = "UPDATE " + DataContract.DataEntry.TABLE_NAME + " SET " + set + " WHERE " + conds;
            sq.execSQL(sql);
            Log.v("update", set + conds + " successfully");
        }

        public static void deleteData(String conds) {
            SQLiteDatabase sq = mDataDbHelper.getReadableDatabase();
            String sql = "DELETE FROM " + DataContract.DataEntry.TABLE_NAME + " WHERE " + conds;
            sq.execSQL(sql);
            Log.v("delete", conds + " successfully");
        }

        public static Cursor selectData(String conds) {
            SQLiteDatabase sq = mDataDbHelper.getReadableDatabase();
            String sql = "SELECT * FROM " + DataContract.DataEntry.TABLE_NAME;
            if (conds.compareTo("all") != 0) {
                sql += " WHERE " + conds;
            }
            Cursor row = sq.rawQuery(sql, null);
            Log.v("select", conds + " successfully");
            return row;
        }

        public static void insertData(String[] line){
            SQLiteDatabase sq = mDataDbHelper.getWritableDatabase();
            String name = line[0], num = line[1];
            String sql = "INSERT INTO " + DataContract.DataEntry.TABLE_NAME +
            "(" +
                            DataContract.DataEntry.COLUMN_USER_NAME + "," +
                            DataContract.DataEntry.COLUMN_USER_NUMBER +
            ")VALUES(?,?)";
            sq.execSQL(sql, new Object[]{
                    name, num
            });
            Log.v("INSERT ", name + " " + num + " insert successfully");
        }

    }
}
