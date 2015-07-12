package models;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 *
 * Created by linh on 15/05/2015.
 */
public class CategoryTable {
        public static final String TABLE_CATEGORY = "tbl_category";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "type";

    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORY +"(" +
            COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_TYPE +" TEXT NOT NULL" +
            ")";

    public static void onCreate(SQLiteDatabase database){
        database.execSQL(CREATE_TABLE_CATEGORY);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(CategoryTable.class.getName(), "Upgrading table "+TABLE_CATEGORY+" from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS "+ TABLE_CATEGORY);
        onCreate(database);
    }
}
