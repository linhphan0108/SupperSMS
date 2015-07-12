package contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.Arrays;
import java.util.HashSet;

import helpers.SmsHelper;
import models.CategoryTable;
import models.MessageTable;

/**
 *
 * Created by linh on 15/05/2015.
 */
public class SmsContentProvider extends ContentProvider {

    //database
    private SmsHelper database;
    // used for the UriMatcher
    private static final int CATEGORY = 10;
    private static final int CATEGORY_ID = 11;
    private static final int MESSAGE = 20;
    private static final int MESSAGE_ID = 21;

    private static final String AUTHORITY = "uit.linh.suppersms.provider";
    private static final String CATEGORY_BASE_PATH = "categories";
    public static final Uri CATEGORY_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CATEGORY_BASE_PATH);
    public static final String CATEGORY_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+ "/categories";
    public static final String CATEGORY_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/category";

    public static final String MESSAGE_BASE_PATH = "messages";
    public static final Uri MESSAGE_CONTENT_URI = Uri.parse("content://"+AUTHORITY +"/"+ MESSAGE_BASE_PATH);
    public static final String MESSAGE_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +"/messages" ;
    public static final String MESSAGE_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/message";


    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        URI_MATCHER.addURI(AUTHORITY, CATEGORY_BASE_PATH, CATEGORY);
        URI_MATCHER.addURI(AUTHORITY, CATEGORY_BASE_PATH + "/#", CATEGORY_ID);
        URI_MATCHER.addURI(AUTHORITY, MESSAGE_BASE_PATH, MESSAGE);
        URI_MATCHER.addURI(AUTHORITY, MESSAGE_BASE_PATH +"/#", MESSAGE_ID );
    }

    @Override
    public boolean onCreate() {
        database = new SmsHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        int uriType = URI_MATCHER.match(uri);

        // check if the caller has requested a column which does not exists
        checkColumns(projection, uriType);



        switch (uriType){
            case CATEGORY:
                builder.setTables(CategoryTable.TABLE_CATEGORY);
                break;

            case CATEGORY_ID:
                //set the table
                builder.setTables(CategoryTable.TABLE_CATEGORY);
                // adding the ID to the original query
                builder.appendWhere(CategoryTable.COLUMN_ID +"="+ uri.getLastPathSegment());
                break;

            case MESSAGE:
                builder.setTables(MessageTable.TABLE_MESSAGE);
                break;

            case MESSAGE_ID:
                //set the table
                builder.setTables(MessageTable.TABLE_MESSAGE);
                builder.appendWhere(MessageTable.COLUMN_ID +"="+ uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = URI_MATCHER.match(uri);
        SQLiteDatabase db = database.getWritableDatabase();
        int rowDeleted = 0;
        long id;
        switch (uriType){

            case CATEGORY:
                id = db.insert(CategoryTable.TABLE_CATEGORY, null, values);
                break;

            case MESSAGE:
                id = db.insert(MessageTable.TABLE_MESSAGE, null, values);
                break;


            default:
               throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
       return Uri.parse(CATEGORY_BASE_PATH +"/"+ id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private void checkColumns(String[] projection, int uriType) {
        String[] available = new String[0];
        switch (uriType) {
            case CATEGORY:
            case CATEGORY_ID:
                available = new String[]{
                                CategoryTable.COLUMN_ID,
                                CategoryTable.COLUMN_TYPE
                            };
                break;

            case MESSAGE:
            case MESSAGE_ID:
                available = new String[]{
                                MessageTable.COLUMN_ID,
                                MessageTable.COLUMN_CATEGORY,
                                MessageTable.COLUMN_CONTENT
                            };
                break;
        }
        if(projection != null){
            HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<>(Arrays.asList(available));
            // check if all columns which are requested are available
            if(!availableColumns.contains(requestedColumns)){
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
