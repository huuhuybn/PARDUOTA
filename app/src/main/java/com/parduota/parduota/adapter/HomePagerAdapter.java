package com.parduota.parduota.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.parduota.parduota.fragment.FraDashBoard;
import com.parduota.parduota.fragment.FraItem;
import com.parduota.parduota.fragment.FraNotifications;

/**
 * Created by huy_quynh on 6/13/17.
 */

class HomePagerAdapter extends FragmentStatePagerAdapter {

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FraDashBoard();
            case 1:
                return new FraItem();
            case 2:
                return new FraNotifications();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
