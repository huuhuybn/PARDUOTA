package com.parduota.parduota;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.parduota.parduota.abtract.BaseActivity;
import com.parduota.parduota.fragment.FraItem;
import com.parduota.parduota.ion.Constant;

/**
 * Created by MAC2015 on 2/7/18.
 */

public class ItemAC extends BaseActivity implements Constant {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ViewPager viewPager = findViewById(R.id.pagers);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        ItemPager itemPager = new ItemPager(getSupportFragmentManager());

        viewPager.setAdapter(itemPager);


        //Set nav drawer selected to first item in list
        mNavigationView.getMenu().getItem(2).setChecked(true);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ItemAC.this, AddAC.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    class ItemPager extends FragmentStatePagerAdapter {


        ItemPager(FragmentManager fm) {
            super(fm);
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return getString(R.string.menu_active);
                case 1:

                    return getString(R.string.menu_sold);
                case 2:


                    return getString(R.string.menu_pending);
                case 3:


                    return getString(R.string.menu_draft);
                case 4:

                    return getString(R.string.menu_reject);

                default:

                    return super.getPageTitle(position);
            }

        }

        final int Draft = -1;
        final int Pending = 0;
        final int Sold = 1;
        final int Reject = 2;
        final int Active = 3;

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return FraItem.instance(Active);
                case 1:
                    return FraItem.instance(Sold);
                case 2:
                    return FraItem.instance(Pending);
                case 3:
                    return FraItem.instance(Draft);
                case 4:
                    return FraItem.instance(Reject);
            }


            return FraItem.instance(position);
        }

        @Override
        public int getCount() {
            return 5;
        }

    }
}
