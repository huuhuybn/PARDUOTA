package com.parduota.parduota;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.adapter.HomePagerAdapter;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.User;
import com.parduota.parduota.model.item.Medium;
import com.parduota.parduota.paypal.PaypalAct;
import com.parduota.parduota.utils.SharePrefManager;
import com.parduota.parduota.view.UploadDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainAC extends MActivity implements Constant {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_item:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    private ViewPager viewPager;
    private HomePagerAdapter homePagerAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    private User user;

    @Override
    protected void initView() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager = (ViewPager) findViewById(R.id.pager);
        homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(homePagerAdapter);
        user = sharePrefManager.getUser();
        setTitle(user.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_log_out:
                LoginManager.getInstance().logOut();
                sharePrefManager.clearAccessToken();
                startNewActivity(LoginActivity.class);
                finish();
                return true;
            case R.id.menu_add_credit:
                startNewActivity(PaypalAct.class);
                return true;
            case R.id.menu_add_item:
                final UploadDialog uploadDialog = new UploadDialog(this);
                uploadDialog.setOnCameraClick(new UploadDialog.OnCameraClick() {
                    @Override
                    public void onClick() {
                        uploadDialog.dismiss();
                        dispatchTakePictureIntent();
                    }
                });
                uploadDialog.setOnGalleryClick(new UploadDialog.OnGalleryClick() {
                    @Override
                    public void onCLick() {
                        uploadDialog.dismiss();
                        pickPhotoFromGalley();
                    }
                });
                uploadDialog.show();

                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Intent intent = new Intent(MainAC.this, UploadAC.class);
        intent.putExtra(DATA, 15);
        startActivity(intent);
        return;


//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Uri uri = data.getData();
//
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//
//                showLoading();
//
//                File file = new File(getRealPathFromURI(uri));
//
//                String token = SharePrefManager.getInstance(getApplicationContext()).getAccessToken();
//                ION.uploadFile(getApplicationContext(), URL_UPLOAD, token, file, new FutureCallback() {
//                    @Override
//                    public void onCompleted(Exception e, Object result) {
//                        hideLoading();
//                        Log.e("KET QUA", e.getMessage());
//                    }
//                });
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e("ABC", e.toString());
//            }
//        }
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
//            Uri uri = data.getData();
//
//            try {
//
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                showLoading();
//                File file = new File(getRealPathFromURI(uri));
//
//                String token = SharePrefManager.getInstance(getApplicationContext()).getAccessToken();
//                ION.uploadFile(getApplicationContext(), URL_UPLOAD, token, file, new FutureCallback() {
//                    @Override
//                    public void onCompleted(Exception e, Object result) {
//
//                        hideLoading();
//                        Log.e("KET QUA", e.getMessage());
//
//                    }
//                });
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e("ABC", e.getMessage());
//            }
//        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int PICK_IMAGE_REQUEST = 2;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void pickPhotoFromGalley() {
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_photo)), PICK_IMAGE_REQUEST);
    }


    public File scaleImage(File imgFileOrig) {

        File f = null;
        // we'll start with the original picture already open to a file

        Bitmap b = BitmapFactory.decodeFile(imgFileOrig.getAbsolutePath());
// original measurements
        int origWidth = b.getWidth();
        int origHeight = b.getHeight();

        final int destWidth = 600;//or the width you need

        if (origWidth > destWidth) {
            // picture is wider than we want it, we calculate its target height
            int destHeight = origHeight / (origWidth / destWidth);
            // we create an scaled bitmap so it reduces the image, not just trim it
            Bitmap b2 = Bitmap.createScaledBitmap(b, destWidth, destHeight, false);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            // compress to the format you want, JPEG, PNG...
            // 70 is the 0-100 quality percentage
            b2.compress(Bitmap.CompressFormat.JPEG, 70, outStream);
            // we save the file, at least until we have made use of it
            f = new File(Environment.getExternalStorageDirectory()
                    + File.separator + System.currentTimeMillis() + ".jpg");
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //write the bytes in file
            FileOutputStream fo = null;
            try {
                fo = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                fo.write(outStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
            // remember close de FileOutput
            try {
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

}

