package uit.linh.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import uit.linh.fragments.EnglishFragment;
import uit.linh.fragments.TextEmoticonFragment;
import uit.linh.fragments.VietnameseFragment;

/**
 *
 * Created by linh on 19/05/2015.
 */
public class SmsTabsAdapter extends FragmentStatePagerAdapter {
    private final int TOTAL_TABS = 3;
    private int cat =1, type =1;

    public SmsTabsAdapter(FragmentManager fm, int category) {
        super(fm);
        this.cat = category;
    }

    @Override
    public int getCount() {
        return TOTAL_TABS;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                type =1;
                return VietnameseFragment.newInstance(cat, type);
            case 1:
                type =2;
                return EnglishFragment.newInstance(cat, type);

            case 2:
                type =3;
                return TextEmoticonFragment.newInstance(cat, type);
            default:
                Log.e("get item", "null");
                return null;
        }

    }
}
