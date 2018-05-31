package com.parduota.parduota;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.fragment.PhotoFragment;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.UploadItem;
import com.parduota.parduota.model.item.Datum;
import com.parduota.parduota.model.item.Medium;
import com.parduota.parduota.model.notification.MetaData;
import com.parduota.parduota.model.updateitem.ItemResponse;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ItemDetailAC extends MActivity implements Constant {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_item_detail;
    }

    private Datum datum;


    private ViewPager pagers;
    private TextView tvTitle;
    private TextView tvDateTime;
    private TextView tvPrice;
    private TextView tvQuality;
    private TextView tvDetail;
    private TextView tvAddress;
    private TextView tvCondition;

    private TextView tvIndicator;

    private TextView tvShippingOption;

    private TextView tvWidth;
    private TextView tvHeight;
    private TextView tvWeight;
    private TextView tvLength;

    private String id;
    private String countryCode;
    private String conditionCode;
    private String isCharity;
    private String shipping_type;
    private ArrayList<AlbumFile> photoArr;

    private PhotoNotiAdapter photoNotiAdapter;

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        photoArr = new ArrayList<>();
        tvShippingOption = findViewById(R.id.tv_shipping_option);
        pagers = findViewById(R.id.pagers);
        tvTitle = findViewById(R.id.tv_title);
        tvDateTime = findViewById(R.id.tv_date_time);
        tvPrice = findViewById(R.id.tv_price);
        tvQuality = findViewById(R.id.tv_quality);
        tvDetail = findViewById(R.id.tv_detail);
        tvAddress = findViewById(R.id.tv_address);
        tvCondition = findViewById(R.id.tv_condition);
        tvIndicator = findViewById(R.id.tvIndicator);


        tvWidth = findViewById(R.id.tvWidth);
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        tvLength = findViewById(R.id.tvLength);


        String from = getIntent().getStringExtra(FROM);
        String type_from = from;
        int size = 0;

        if (Constant.isDEBUG) Log.e("Data", getIntent().getStringExtra(DATA));
        if (from.equals(ITEM_SCREEN)) {
            datum = new Gson().fromJson(getIntent().getStringExtra(DATA), Datum.class);
            if (actionBar != null) actionBar.setTitle(datum.getTitle());

            id = datum.getId() + "";
            countryCode = datum.getCountry();
            conditionCode = "" + datum.getCondition();
            isCharity = datum.getSellForCharity() + "";
            shipping_type = datum.getShippingTypeCustom();


            tvQuality.setText(datum.getQuantity() + "");
            tvDetail.setText(datum.getDescription());
            tvTitle.setText(datum.getTitle());
            tvDateTime.setText(datum.getCreatedAt());
            tvAddress.setText(datum.getLocation());
            tvPrice.setText(datum.getPrice());
            tvCondition.setText(getConditionByCode(datum.getCondition() + ""));

            tvShippingOption.setText(datum.getShippingTypeCustom());

            if (datum.getShippingTypeCustom().equals(KG_AND_DIMENSION)) {

                findViewById(R.id.layout_dimen).setVisibility(View.VISIBLE);
                tvWidth.setText(datum.getWidth() + "");
                tvHeight.setText(datum.getHeight() + "");
                tvWeight.setText(datum.getWeight() + "");
                tvLength.setText(datum.getLength() + "");

            }

            PhotoItemAdapter photoAdapter = new PhotoItemAdapter(getSupportFragmentManager(), datum.getMedia());
            pagers.setAdapter(photoAdapter);

            size = datum.getMedia().size();

            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    AlbumFile album = new AlbumFile();
                    album.setThumbPath(Constant.PHOTO_URL + datum.getMedia().get(i).getLink());
                    album.setPath(Constant.PHOTO_URL + datum.getMedia().get(i).getLink());
                    album.setWidth(datum.getMedia().get(i).getId());

                    photoArr.add(album);
                }
            }

        }
        if (from.equals(NOTIFICATION_SCREEN)) {
            MetaData metaData = new Gson().fromJson(getIntent().getStringExtra(DATA), MetaData.class);
            if (actionBar != null) actionBar.setTitle(metaData.getTitle());
            tvTitle.setText(metaData.getTitle());
            tvDateTime.setText(metaData.getCreatedAt());


            id = metaData.getId() + "";
            countryCode = metaData.getCountry();
            conditionCode = "" + metaData.getCondition();
            isCharity = metaData.getSellForCharity() + "";
            shipping_type = metaData.getShippingTypeCustom();

            tvCondition.setText(getConditionByCode(metaData.getCondition() + ""));
            tvShippingOption.setText(metaData.getShippingTypeCustom());


            if (metaData.getShippingTypeCustom().equals(KG_AND_DIMENSION)) {


                findViewById(R.id.layout_dimen).setVisibility(View.VISIBLE);
                tvWidth.setText(metaData.getWidth() + "");
                tvHeight.setText(metaData.getHeight() + "");
                tvWeight.setText(metaData.getWeight() + "");
                tvLength.setText(metaData.getLength() + "");

            }
            photoNotiAdapter = new PhotoNotiAdapter(getSupportFragmentManager(), metaData.getMedia());
            pagers.setAdapter(photoNotiAdapter);

            size = metaData.getMedia().size();

            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    AlbumFile album = new AlbumFile();
                    album.setThumbPath(Constant.PHOTO_URL + datum.getMedia().get(i).getLink());
                    album.setPath(Constant.PHOTO_URL + datum.getMedia().get(i).getLink());
                    album.setWidth(datum.getMedia().get(i).getId());

                    photoArr.add(album);
                }
            }

        }


        final int finalSize = size;

        tvIndicator.setText("1/" + finalSize);

        pagers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                tvIndicator.setText((position + 1) + "/" + finalSize);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(this, AddAC.class);

                UploadItem uploadItem = new UploadItem();
                uploadItem.setId(id);
                uploadItem.setTitle(tvTitle.getText().toString());
                uploadItem.setDescription(tvDetail.getText().toString());
                uploadItem.setPrice(tvPrice.getText().toString());
                uploadItem.setQuantity(tvQuality.getText().toString());
                uploadItem.setLocation(tvAddress.getText().toString());
                uploadItem.setCountry(countryCode);
                uploadItem.setCondition(conditionCode);
                uploadItem.setSell_for_charity(isCharity);
                uploadItem.setShipping_type_custom(shipping_type);
                uploadItem.setPhotoArr(photoArr);

                uploadItem.setUpdate(true);

                Log.e("ABC", new Gson().toJson(photoArr));


                intent.putExtra(DATA, new Gson().toJson(uploadItem));

                startActivityForResult(intent, UPDATE_SUCCESSFUL);
                return true;

            case android.R.id.home:
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_SUCCESSFUL) {

//
//            tvTitle.setText(itemResponse.getItem().getTitle());
//            tvDateTime.setText(itemResponse.getItem().getCreatedAt());
//            tvAddress.setText(itemResponse.getItem().getLocation());
//            tvPrice.setText(itemResponse.getItem().getPrice());
//            tvCondition.setText(getConditionByCode(itemResponse.getItem().getCondition() + ""));
//
//            tvShippingOption.setText(itemResponse.getItem().getShippingTypeCustom());
//
//            if (itemResponse.getItem().getShippingTypeCustom().equals(KG_AND_DIMENSION)) {
//                findViewById(R.id.layout_dimen).setVisibility(View.VISIBLE);
//                tvWidth.setText(itemResponse.getItem().getWidth() + "");
//                tvHeight.setText(itemResponse.getItem().getHeight() + "");
//                tvWeight.setText(itemResponse.getItem().getWeight() + "");
//                tvLength.setText(itemResponse.getItem().getLength() + "");
//            }

            String token = sharePrefManager.getAccessToken();
            Ion.with(this).load(Constant.URL_GET_ITEM_DETAIL + id).setHeader(ION.authHeader(token
            )).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {


                    final ItemResponse itemResponse = new Gson().fromJson(result, ItemResponse.class);


                    tvTitle.setText(itemResponse.getTitle());
                    tvDateTime.setText(itemResponse.getCreatedAt());
                    tvAddress.setText(itemResponse.getLocation());
                    tvPrice.setText(itemResponse.getPrice());
                    tvCondition.setText(getConditionByCode(itemResponse.getCondition() + ""));

                    tvShippingOption.setText(itemResponse.getShippingType());

                    if (itemResponse.getShippingTypeCustom().equals(KG_AND_DIMENSION)) {
                        findViewById(R.id.layout_dimen).setVisibility(View.VISIBLE);
                        tvWidth.setText(itemResponse.getWidth() + "");
                        tvHeight.setText(itemResponse.getHeight() + "");
                        tvWeight.setText(itemResponse.getWeight() + "");
                        tvLength.setText(itemResponse.getLength() + "");
                    }

                    photoNotiAdapter = new PhotoNotiAdapter(getSupportFragmentManager(), itemResponse.getMedia());
                    pagers.setAdapter(photoNotiAdapter);

                    pagers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {

                            tvIndicator.setText((position + 1) + "/" + itemResponse.getMedia().size());

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });

                }
            });
        }
    }

    class PhotoNotiAdapter extends FragmentStatePagerAdapter {

        final List<com.parduota.parduota.model.notification.Medium> mediums;

        PhotoNotiAdapter(FragmentManager fm, List<com.parduota.parduota.model.notification.Medium> mediums) {
            super(fm);
            this.mediums = mediums;
        }

        @Override
        public Fragment getItem(int position) {
            return PhotoFragment.createInstance(mediums.get(position).getLink());
        }

        @Override
        public int getCount() {
            return mediums != null ? mediums.size() : 0;
        }

    }

    class PhotoItemAdapter extends FragmentStatePagerAdapter {

        final List<Medium> mediums;

        PhotoItemAdapter(FragmentManager fm, List<Medium> mediums) {

            super(fm);
            this.mediums = mediums;

        }

        @Override
        public Fragment getItem(int position) {
            return PhotoFragment.createInstance(mediums.get(position).getLink());
        }

        @Override
        public int getCount() {
            return mediums != null ? mediums.size() : 0;
        }

    }

    private String getConditionByCode(String condition) {
        int i = -1;
        for (String cc : getResources().getStringArray(R.array.condition_code)) {
            i++;
            if (cc.equals(condition))
                break;
        }
        return getResources().getStringArray(R.array.condition)[i];
    }


}
