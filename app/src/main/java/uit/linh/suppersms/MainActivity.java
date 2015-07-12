package uit.linh.suppersms;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import helpers.SmsHelper;
import models.MessageTypes;


/**
 * table category
 * id
 * type
 *
 * table message
 * id
 * category id
 * content
 * date
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout tileLoveSMS, tileNewYear, tileChristmas, tileBirthday, tileWomenDay;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
//        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
//        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

//        ContentValues value = new ContentValues();
//        value.put(CategoryTable.COLUMN_ID, 0);
//        value.put(CategoryTable.COLUMN_TYPE, "love");
//        getContentResolver().insert(SmsContentProvider.CATEGORY_CONTENT_URI, value);

        getWidgets();
        registerEventsListeners();
    }

    private void getWidgets(){
        tileLoveSMS = (LinearLayout) findViewById(R.id.tile_love_sms);
        tileNewYear = (LinearLayout) findViewById(R.id.tile_new_year);
        tileChristmas = (LinearLayout) findViewById(R.id.tile_christmas);
        tileBirthday = (LinearLayout) findViewById(R.id.tile_birthday);
        tileWomenDay = (LinearLayout) findViewById(R.id.tile_women_day);
    }

    private void registerEventsListeners(){
        tileLoveSMS.setOnClickListener(this);
        tileNewYear.setOnClickListener(this);
        tileChristmas.setOnClickListener(this);
        tileBirthday.setOnClickListener(this);
        tileWomenDay.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.tile_love_sms:
                Toast.makeText(this, "love messages", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, ListMessage.class);
                intent.putExtra("category_id", MessageTypes.LOVE.getValue());
                startActivity(intent);                break;

            case R.id.tile_new_year:
                Toast.makeText(this, "new year messages", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, ListMessage.class);
                intent.putExtra("category_id", MessageTypes.NEW_YEAR.getValue());
                startActivity(intent);
                break;

            case R.id.tile_christmas:
                Toast.makeText(this, "christmas messages", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, ListMessage.class);
                intent.putExtra("category_id", MessageTypes.CHRISTMAS.getValue());
                startActivity(intent);
                break;

            case R.id.tile_birthday:
                Toast.makeText(this, "birthday messages", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, ListMessage.class);
                intent.putExtra("category_id", MessageTypes.BIRTHDAY.getValue());
                startActivity(intent);
                break;

            case R.id.tile_women_day:
                Toast.makeText(this, "women days messages", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, ListMessage.class);
                intent.putExtra("category_id", MessageTypes.WOMEN_DAY.getValue());
                startActivity(intent);
                break;

            default:
                Toast.makeText(this, "the selected tile doesn't exists!", Toast.LENGTH_LONG).show();
                Toast.makeText(this, "love messages", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, ListMessage.class);
                intent.putExtra("category_id", MessageTypes.LOVE.getValue());
                startActivity(intent);
                break;
        }
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }
}
