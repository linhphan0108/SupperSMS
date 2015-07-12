package uit.linh.suppersms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import uit.linh.adapters.MessageAdapter;
import contentprovider.SmsContentProvider;
import models.MessageModel;
import sms.unitiy.AccentRemover;
import uit.linh.adapters.SmsTabsAdapter;
import uit.linh.fragments.EnglishFragment;
import uit.linh.fragments.TextEmoticonFragment;
import uit.linh.fragments.VietnameseFragment;


public class ListMessage extends ActionBarActivity implements AdapterView.OnItemClickListener,
        ActionBar.TabListener, ViewPager.OnPageChangeListener,
        VietnameseFragment.OnFragmentInteractionListener, EnglishFragment.OnFragmentInteractionListener,
        TextEmoticonFragment.OnFragmentInteractionListener{

    int categoryId;
    ViewPager viewPager;

    ActionBar actionBar;
    SmsTabsAdapter tabAdapter;
    String[] tabs = {"Vietnamese", "English", "Text Emoticon"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);

        categoryId = getIntent().getExtras().getInt("category_id");
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabAdapter = new SmsTabsAdapter(getSupportFragmentManager(), categoryId);
        viewPager.setAdapter(tabAdapter);
        viewPager.setOnPageChangeListener(this);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.setIcon(R.mipmap.ic_launcher);
            for (String tab : tabs) {
                actionBar.addTab(actionBar.newTab().setText(tab).setTabListener(this));
            }
        }


        setActivityBackground(categoryId);

    }
    private void setActivityBackground(int categoryId) {
        switch (categoryId){
            case 1:
                viewPager.setBackgroundResource(R.drawable.bg_love);
                actionBar.setTitle("Love");
                break;
            case 2:
                viewPager.setBackgroundResource(R.drawable.bg_new_year);
                actionBar.setTitle("New Year");
                break;

            case 3:
                viewPager.setBackgroundResource(R.drawable.bg_christmas);
                actionBar.setTitle("Christmas");
                break;
            case 4:
                viewPager.setBackgroundResource(R.drawable.bg_birthday);
                actionBar.setTitle("Birthday");
                break;

            case 5:
                actionBar.setTitle("Women's Day");
                viewPager.setBackgroundResource(R.drawable.bg_love);
                break;

            default:
                actionBar.setTitle("Love");
                viewPager.setBackgroundResource(R.drawable.bg_love);
        }
    }

    ArrayList<MessageModel> getResource(int cat, int type){
        ArrayList<MessageModel> arrMessage = new ArrayList<>();
        Cursor cursor = getContentResolver().query(SmsContentProvider.MESSAGE_CONTENT_URI,
                null, "category_id=? AND type=?", new String[]{cat+"", type+""}, null);
        if (cursor == null){
            Log.e("query", "error");
            return null;
        }else if(!cursor.moveToFirst()){
            Log.e("query", "empty");
            return null;
        }
        do{
            int id = cursor.getInt(0);
            int categoryId = cursor.getInt(1);
            String content = cursor.getString(2);
            arrMessage.add(new MessageModel(id, categoryId, content));
        }while (cursor.moveToNext());

        cursor.close();
        return arrMessage;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_love_message, menu);
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

        if(id == android.R.id.home){
            Toast.makeText(getBaseContext(), "home", Toast.LENGTH_SHORT).show();
            closeActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void sendSms(String content, boolean removeAccent){
        if (removeAccent){//converts a vietnamese string to none vietnamese accent string
            content = AccentRemover.removeAccent(content);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("sms_body", content);
        intent.setType("vnd.android-dir/mms-sms");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                closeActivity();
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void closeActivity(){
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    //== on tab select listeners
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        int a= 0;
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        int a= 0;

    }


    //==on page change listeners
    @Override
    public void onPageScrolled(int i, float v, int i2) {
        int a= 0;

    }

    @Override
    public void onPageSelected(int i) {
        actionBar.setSelectedNavigationItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        int a= 0;

    }

    @Override
    public void sendMessage(final String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("Do you want a vietnamese sms without accent?");
        builder.setPositiveButton("Yeah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendSms(content, true);
            }
        });
        builder.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendSms(content, false);
            }
        });
        builder.create().show();
    }

    @Override
    public ArrayList<MessageModel> getMessages(int cat, int type) {
        return getResource(cat, type);
    }
}
