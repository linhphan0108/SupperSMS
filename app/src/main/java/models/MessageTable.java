package models;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 *
 * Created by linh on 15/05/2015.
 */
public class MessageTable{

    public static final String TABLE_MESSAGE = "tbl_message";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CATEGORY = "category_id";
    public static final String COLUMN_CONTENT = "content";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_MESSAGE +"( " +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY + " INTEGER NOT NULL CONSTRAINT "+COLUMN_CATEGORY+" REFERENCES "+
                CategoryTable.TABLE_CATEGORY +"("+CategoryTable.COLUMN_ID+") ON DELETE CASCADE, " +
            COLUMN_CONTENT +" TEXT NOT NULL" +
            ")";
    public static void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(CategoryTable.class.getName(), "Upgrading table "+TABLE_MESSAGE+" from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MESSAGE);
        onCreate(db);
    }

}
