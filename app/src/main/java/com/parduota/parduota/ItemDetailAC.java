package com.parduota.parduota;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
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
import com.parduota.parduota.model.notification.Medium;
import com.parduota.parduota.model.notification.MetaData;
import com.parduota.parduota.model.updateitem.ItemResponse;
import com.parduota.parduota.remote.RetrofitClient;
import com.parduota.parduota.remote.RetrofitRequest;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ItemDetailAC extends MActivity implements Constant {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_item_detail;
    }

    //private Datum datum;

    private Button btnViewOnEbay;


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


    private int itemID = -1;

    private
    int size = 0;

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btnViewOnEbay = findViewById(R.id.btnViewInEbay);

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


        itemID = getIntent().getIntExtra(ID, -1);

        if (itemID > 0) {
            showLoading();
            RetrofitRequest retrofitRequest = RetrofitClient.getClient(ItemDetailAC.this).create(RetrofitRequest.class);

            String token = sharePrefManager.getAccessToken();

            retrofitRequest.getItemDetail(RetrofitRequest.PRE_TOKEN + token, itemID).enqueue(new Callback<ItemResponse>() {
                @Override
                public void onResponse(Call<ItemResponse> call, final Response<ItemResponse> response) {

                    hideLoading();
                    if (Constant.isDEBUG)
                        Log.e("Item Detail", new Gson().toJson(response.body()).toString());

                    setTitle(response.body().getTitle());

                    id = response.body().getId() + "";
                    countryCode = response.body().getCountry();
                    conditionCode = "" + response.body().getCondition();
                    isCharity = response.body().getSellForCharity() + "";
                    shipping_type = response.body().getShippingTypeCustom();


                    tvQuality.setText(response.body().getQuantity() + "");
                    tvDetail.setText(response.body().getDescription());
                    tvTitle.setText(response.body().getTitle());
                    tvDateTime.setText(response.body().getCreatedAt());
                    tvAddress.setText(response.body().getLocation());
                    tvPrice.setText(response.body().getPrice());
                    tvCondition.setText(getConditionByCode(response.body().getCondition() + ""));
                    tvShippingOption.setText(response.body().getShippingTypeCustom());

                    if (response.body().getShippingTypeCustom().equals(KG_AND_DIMENSION)) {

                        findViewById(R.id.layout_dimen).setVisibility(View.VISIBLE);
                        tvWidth.setText(response.body().getWidth() + "");
                        tvHeight.setText(response.body().getHeight() + "");
                        tvWeight.setText(response.body().getWeight() + "");
                        tvLength.setText(response.body().getLength() + "");

                    }

                    if (response.body().getEbayId() != null && !response.body().getEbayId().equals("")) {
                        btnViewOnEbay.setVisibility(View.VISIBLE);
                        btnViewOnEbay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String url = "http://cgi.ebay.de/ws/eBayISAPI.dll?ViewItem&item=" + response.body().getEbayId();
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);

                            }
                        });
                    } else btnViewOnEbay.setVisibility(View.GONE);


                    PhotoItemAdapter photoAdapter = new PhotoItemAdapter(getSupportFragmentManager(), response.body().getMedia());
                    pagers.setAdapter(photoAdapter);

                    size = response.body().getMedia().size();

                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            AlbumFile album = new AlbumFile();
                            album.setThumbPath(Constant.PHOTO_URL + response.body().getMedia().get(i).getLink());
                            album.setPath(Constant.PHOTO_URL + response.body().getMedia().get(i).getLink());
                            album.setWidth(response.body().getMedia().get(i).getId());
                            photoArr.add(album);
                        }
                    }


                    tvIndicator.setText("1/" + size);

                    pagers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {

                            tvIndicator.setText((position + 1) + "/" + size);

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });

                }

                @Override
                public void onFailure(Call<ItemResponse> call, Throwable t) {
                    hideLoading();
                }
            });
        }


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

                uploadItem.setWidth(tvWidth.getText().toString().trim());
                uploadItem.setHeight(tvHeight.getText().toString().trim());
                uploadItem.setLength(tvLength.getText().toString().trim());
                uploadItem.setWeight(tvWeight.getText().toString().trim());

                uploadItem.setUpdate(true);

                if (Constant.isDEBUG)
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
