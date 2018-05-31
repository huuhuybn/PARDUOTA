package com.parduota.parduota;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;

import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.add.FraAddDescription;
import com.parduota.parduota.add.FraAddDetail;
import com.parduota.parduota.add.FraAddDimension;
import com.parduota.parduota.add.FraAddPhoto;
import com.parduota.parduota.add.FraAddTitle;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.UploadItem;
import com.parduota.parduota.model.UploadResponse;
import com.parduota.parduota.view.NonSwipableViewPager;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

/**
 * Created by huy_quynh on 7/8/17.
 */

public class AddAC extends MActivity implements Constant {

    private NonSwipableViewPager viewPager;
    private AddPagerAdapter addPagerAdapter;

    public UploadItem getUploadItem() {
        return uploadItem;
    }

    private UploadItem uploadItem;


    private BroadcastReceiver broadcastReceiverUpload;

    private ArrayList<String> arrayIds;

    private String data;

    @Override
    protected int setLayoutId() {
        return R.layout.act_add;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.pagers);
        addPagerAdapter = new AddPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(addPagerAdapter);
        uploadItem = new UploadItem();

        try {
            data = getIntent().getStringExtra(DATA);
            if (data != null)
                uploadItem = new Gson().fromJson(getIntent().getStringExtra(DATA), UploadItem.class);

        } catch (Exception ignored) {

        }


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


        arrayIds = new ArrayList<>();
        broadcastReceiverUpload = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int id = intent.getIntExtra(UploadResponse.class.getName(), -1);
                if (id > -1) {
                    arrayIds.add("" + id);
                }

                Log.e("IDDD", id + " ");

            }
        };

        IntentFilter intentFilter = new IntentFilter(UploadResponse.class.getName());
        registerReceiver(broadcastReceiverUpload, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiverUpload != null) unregisterReceiver(broadcastReceiverUpload);
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
        if (item.getItemId() == R.id.action_next) {

            commitData();
        }
        if (item.getItemId() == R.id.action_pre) {
            if (viewPager.getCurrentItem() > 0)
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);

        }

        return super.onOptionsItemSelected(item);
    }

    public String getData() {
        return data;
    }

    class AddPagerAdapter extends FragmentStatePagerAdapter {

        final SparseArray<Fragment> registeredFragments = new SparseArray<>();


        AddPagerAdapter(FragmentManager fm) {
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

        Fragment getRegisteredFragment(int position) {
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

    private void showDialogConfirmSaveDraft() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(getString(R.string.notify_save_draft));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getString((R.string.btn_save)),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        showLoading();
                        uploadItem.setStatus("draft");

                        Ion.with(getApplicationContext()).load(Constant.URL_ADD_ITEM).setHeader("Authorization", "Bearer" + " " + sharePrefManager.getAccessToken()).setBodyParameters(ION.addItem(uploadItem)).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {

                                hideLoading();
                                Log.e("DRAF", result.toString());

                                Toast.makeText(AddAC.this, getString(R.string.notify_save_draft_success), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

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

    private void commitData() {

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
                return;

            }
            String country = fraAddDescription.getTvCountry().getSelectedItem().toString();
            if (country.equals(getString(R.string.choose_country))) {
                showToast(getString(R.string.notify_choose_country));
                return;
            }
            String tvPrice = fraAddDescription.getTvPrice().getText().toString().trim();
            if (tvPrice.matches("")) {
                fraAddDescription.getTvPrice().setError(getString(R.string.notify_input));
                return;
            }
            String tvQuality = fraAddDescription.getTvQuality().getText().toString().trim();
            if (tvQuality.equals("")) {
                fraAddDescription.getTvQuality().setError(getString(R.string.notify_input));
                return;
            }
            String address = fraAddDescription.getTvAddress().getText().toString().trim();
            if (address.equals("")) {
                fraAddDescription.getTvAddress().setError(getString(R.string.notify_input));
                return;

            }

            uploadItem.setPrice(tvPrice);
            uploadItem.setQuantity(tvQuality);
            uploadItem.setLocation(address);
            uploadItem.setCondition(getConditionByCode(condition));
            uploadItem.setCountry(getCountryByCode(country));
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);

        } else if (mFragment instanceof FraAddDimension) {
            FraAddDimension fraAddDimension = ((FraAddDimension) mFragment);


            boolean isChariyy = fraAddDimension.getCbSellForCharity().isChecked();
            String weight = fraAddDimension.getEtWeight().getText().toString().trim();
            String width = fraAddDimension.getEtWidth().getText().toString().trim();
            String height = fraAddDimension.getEtHeight().getText().toString().trim();
            String length = fraAddDimension.getLength().getText().toString().trim();
            RadioGroup shippingType = fraAddDimension.getRgShipping();

            int radioButtonID = shippingType.getCheckedRadioButtonId();
            View radioButton = shippingType.findViewById(radioButtonID);
            int idx = shippingType.indexOfChild(radioButton);

            String shipping_type = null;

            Log.e("ID", " " + idx);
            if (idx == 0) {
                //local_pickup | freight |kg_and_dimentions
                shipping_type = "local_pickup";
            }
            if (idx == 1) {
                shipping_type = "freight";
            }
            if (idx == 2) {
                shipping_type = "kg_and_dimentions";
            }

            uploadItem.setShipping_type(shipping_type);
            uploadItem.setWeight(weight);
            uploadItem.setWidth(width);
            uploadItem.setHeight(height);
            uploadItem.setLength(length);

            if (isChariyy) {
                uploadItem.setSell_for_charity("1");

            } else {
                uploadItem.setSell_for_charity("0");
            }

            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);

            hideKeyBoard();

        } else if (mFragment instanceof FraAddPhoto) {
            FraAddPhoto fraAddPhoto = ((FraAddPhoto) mFragment);

            ArrayList<AlbumFile> files = fraAddPhoto.getmAlbumFiles();

            uploadItem.setPhotoArr(files);


            boolean isLoadAll = true;
            if (files.size() > 0)
                for (int i = 0; i < files.size(); i++) {
                    if (files.get(i).getWidth() == 0) {
                        isLoadAll = false;
                        break;
                    }
                }
            if (!isLoadAll) {
                Toast.makeText(this, getString(R.string.notify_wait), Toast.LENGTH_SHORT).show();
                return;
            }
            showLoading();

            if (uploadItem.isUpdate()) {

                if (Constant.isDEBUG) Log.e("OBJECT UPDATE", new Gson().toJson(uploadItem));
                ION.postFormDataWithToken(this, Constant.URL_UPDATE_ITEM + uploadItem.getId(), sharePrefManager.getAccessToken(), ION.addItem(uploadItem), new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        hideLoading();

                        if (Constant.isDEBUG)
                            Log.e("DATA", new Gson().toJson(result).toString());

                        Toast.makeText(AddAC.this, getString(R.string.notify_update_succsessful), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent();

                        intent.putExtra(DATA, result.toString());
                        setResult(UPDATE_SUCCESSFUL, intent);

                        finish();

                    }
                });
            } else {
                ION.postFormDataWithToken(this, Constant.URL_ADD_ITEM, sharePrefManager.getAccessToken(), ION.addItem(uploadItem), new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        hideLoading();

                        if (Constant.isDEBUG)
                            Log.e("DATA", new Gson().toJson(result));

                        // {"status":"error","message":"Not enough credit"}

                        try {
                            String message = result.get(MESSAGE).getAsString();
                            if (message.trim().equals("Not enough credit")) {
                                showNotEnoughCredit();
                            }
                        } catch (Exception notEnoughCredit) {
                            finish();
                            Toast.makeText(AddAC.this, getString(R.string.notify_upload_item_successful), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }
    }


    private void showNotEnoughCredit() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(getString(R.string.notify_not_enough_credit));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getString((R.string.btn_ok)),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(AddAC.this, BuyCreditAC.class);
                        startActivity(intent);
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

    @Override
    public void onCompleted(Exception e, Object result) {
        super.onCompleted(e, result);

    }
}
