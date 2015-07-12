package helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

import models.CategoryTable;
import models.MessageTable;

/**
 *
 * Created by linh on 15/05/2015.
 */
public class SmsHelper extends SQLiteOpenHelper {

    private static final String DUMP_DATA = "linh.zip";
    private static final String DATABASE_NAME = "super_cute_sms.db";
    private static final int DATABASE_VERSION = 1;
    private  Context context;

    public SmsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * method is called during creation of the database
     * @param db database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            SQLiteDBDeploy.deploy(db, context, DUMP_DATA);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        CategoryTable.onCreate(db);
//        MessageTable.onCreate(db);
    }

    /**
     * method is called during an upgrade of the database
     * @param db database
     * @param oldVersion the old version
     * @param newVersion the new version
     *
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        CategoryTable.onUpgrade(db, oldVersion, newVersion);
    }
}
