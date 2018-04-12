package com.parduota.parduota;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;

import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.add.FraAddDescription;
import com.parduota.parduota.add.FraAddDetail;
import com.parduota.parduota.add.FraAddDimension;
import com.parduota.parduota.add.FraAddPhoto;
import com.parduota.parduota.add.FraAddTitle;
import com.parduota.parduota.model.UploadItem;
import com.parduota.parduota.view.NonSwipeableViewPager;

/**
 * Created by huy_quynh on 7/8/17.
 */

public class AddAC extends MActivity {


    private int PICK_IMAGE = 999;

    private NonSwipeableViewPager viewPager;
    private AddPagerAdapter addPagerAdapter;
    private UploadItem uploadItem;

    @Override
    protected int setLayoutId() {
        return R.layout.act_add;
    }

    @Override
    protected void initView() {
        viewPager = (NonSwipeableViewPager) findViewById(R.id.pagers);
        addPagerAdapter = new AddPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(addPagerAdapter);
        uploadItem = new UploadItem();

//        findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, getString(R.string.select_photo)), PICK_IMAGE);
//            }
//        });

        setTitle(("1/5"));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle((position + 1) + "/5");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            showDialogConfirmSaveDraft();
            return true;
        }
        Fragment mFragment = addPagerAdapter.getRegisteredFragment(viewPager.getCurrentItem());
        if (mFragment instanceof FraAddTitle) {
            String text = ((FraAddTitle) mFragment).getEtTitle().getText().toString();
            if (text.trim().matches("")) {
                ((FraAddTitle) mFragment).getEtTitle().setError(getString(R.string.notify_input));
            } else {
                uploadItem.setTitle(text);
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        } else if (mFragment instanceof FraAddDetail) {
            String text = ((FraAddDetail) mFragment).getEtDetail().getText().toString();
            if (text.trim().matches("")) {
                ((FraAddDetail) mFragment).getEtDetail().setError(getString(R.string.notify_input));
            } else {
                uploadItem.setDescription(text);
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        } else if (mFragment instanceof FraAddDescription) {
            FraAddDescription fraAddDescription = ((FraAddDescription) mFragment);

            String condition = fraAddDescription.getTvCondition().getSelectedItem().toString();
            if (condition.equals(getString(R.string.choose_item_condition))) {
                showToast(getString(R.string.notify_choose_condition));
                return true;
            }
            String country = fraAddDescription.getTvCountry().getSelectedItem().toString();
            if (country.equals(getString(R.string.choose_country))) {
                showToast(getString(R.string.notify_choose_country));
                return true;
            }
            String tvPrice = fraAddDescription.getTvPrice().getText().toString().trim();
            if (tvPrice.matches("")) {
                fraAddDescription.getTvPrice().setError(getString(R.string.notify_input));
                return true;
            }
            String tvQuality = fraAddDescription.getTvQuality().getText().toString().trim();
            if (tvQuality.equals("")) {
                fraAddDescription.getTvQuality().setError(getString(R.string.notify_input));
                return true;
            }
            String address = fraAddDescription.getTvAddress().getText().toString().trim();
            if (address.equals("")) {
                fraAddDescription.getTvAddress().setError(getString(R.string.notify_input));
                return true;
            }
            uploadItem.setPrice(tvPrice);
            uploadItem.setQuantity(tvQuality);
            uploadItem.setLocation(address);
            uploadItem.setCondition(getConditionByCode(condition));
            uploadItem.setCountry(getCountryByCode(country));
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);

        } else if (mFragment instanceof FraAddDimension) {


            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);

        } else if (mFragment instanceof FraAddPhoto) {

        }
        return super.onOptionsItemSelected(item);
    }

    public class AddPagerAdapter extends FragmentStatePagerAdapter {

        SparseArray<Fragment> registeredFragments = new SparseArray<>();


        public AddPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FraAddTitle();

                case 1:

                    return new FraAddDetail();

                case 2:

                    return new FraAddDescription();

                case 3:

                    return new FraAddDimension();

                case 4:

                    return new FraAddPhoto();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }

    private String getCountryByCode(String country) {
        int i = -1;
        for (String cc : getResources().getStringArray(R.array.country)) {
            i++;
            if (cc.equals(country))
                break;
        }
        return getResources().getStringArray(R.array.country_code)[i];
    }

    private String getConditionByCode(String condition) {
        int i = -1;
        for (String cc : getResources().getStringArray(R.array.condition)) {
            i++;
            if (cc.equals(condition))
                break;
        }
        return getResources().getStringArray(R.array.condition_code)[i];
    }

    public void showDialogConfirmSaveDraft() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(getString(R.string.notify_save_draft));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getString((R.string.btn_save)),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                getString(R.string.btn_no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
