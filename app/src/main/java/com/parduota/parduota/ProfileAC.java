package com.parduota.parduota;

import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.Profile;
import com.parduota.parduota.model.ProfileResponse;
import com.parduota.parduota.model.User;
import com.parduota.parduota.utils.SharePrefManager;
import com.parduota.parduota.view.EditDialog;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ProfileAC extends MActivity implements FutureCallback, Constant {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_profile;
    }

    private TextView tvEmail;
    private RadioGroup gender;
    private RadioButton male;
    private RadioButton female;
    private TextView tvAddress;
    private ImageView btnEditAddress;
    private TextView tvBank;
    private ImageView btnEditBank;
    private TextView tvCompany;
    private ImageView btnEditCompany;
    private TextView tvPhone;
    private ImageView btnEditPhone;
    private TextView tvDescription;
    private ImageView btnEditDescription;
    private TextView tvCredit;


    private TextView tvFullName;
    private ImageView btnEditFullName;

    private TextView tvOther;
    private ImageView btnEditOther;


    private User user;

    private String token;

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        user = SharePrefManager.getInstance(this).getUser();
        token = SharePrefManager.getInstance(this).getAccessToken();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(user.getName());
        }
        init();


        String email = user.getEmail();
        String bank = user.getBankAccount();
        String address = user.getAddress();
        String phone = user.getPhone();
        String other = user.getOther();
        String credit = user.getCredit();
        String description = user.getDescription();
        String company = user.getCompany();
        String full_name = user.getFullName();

        String gender = user.getGender();

        if (gender.equals(MALE)) {
            this.gender.check(R.id.male);
        } else {
            this.gender.check(R.id.female);
        }

        Log.e("USER", new Gson().toJson(user));


        if (full_name != null) tvFullName.setText(full_name);

        if (company != null) tvCompany.setText(company);

        if (email != null) tvEmail.setText(email);

        if (bank != null) tvBank.setText(bank);

        if (address != null) tvAddress.setText(address);

        if (phone != null) tvPhone.setText(phone);

        if (credit != null) tvCredit.setText(credit);

        if (description != null) tvDescription.setText(description);

        if (other != null) tvOther.setText(other);

        setActionEdit(tvFullName, btnEditFullName, false);
        setActionEdit(tvAddress, btnEditAddress, false);
        setActionEdit(tvBank, btnEditBank, true);
        setActionEdit(tvCompany, btnEditCompany, false);
        setActionEdit(tvPhone, btnEditPhone, true);
        setActionEdit(tvDescription, btnEditDescription, false);
        setActionEdit(tvOther, btnEditOther, false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.change_password, menu);
        return true;
    }

    public void setActionEdit(final TextView textView, ImageView button, final boolean isNumberInput) {


        final String text = textView.getText().toString().trim();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditDialog newFragment = new EditDialog();
                newFragment.isNumberInput = isNumberInput;
                newFragment.tvContainer = text;
                newFragment.show(getFragmentManager(), "missiles");
                newFragment.setOnEditFinishListener(new EditDialog.OnEditFinishListener() {
                    @Override
                    public void onFinish(String text) {
                        textView.setText(text);
                    }
                });
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            // This ID represents the Home or Up button.
            saveProfile();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void saveProfile() {
        showLoading();
        String address = tvAddress.getText().toString().trim();
        String bank = tvBank.getText().toString().trim();
        String company = tvCompany.getText().toString().trim();
        String phone = tvPhone.getText().toString().trim();
        String description = tvDescription.getText().toString().trim();
        String full_name = tvFullName.getText().toString().trim();
        String other = tvOther.getText().toString().trim();
        String gender = "male";

        int id = this.gender.getCheckedRadioButtonId();
        if (id == R.id.male) gender = MALE;
        else gender = FEMALE;

        Log.e("ABC", gender + "  ");


        ION.postFormDataWithToken(this, Constant.URL_UPDATE_PROFILE, token, ION.updateProfile(bank, gender, full_name, company, address, phone, description, other), this);


    }

    @Override
    public void onCompleted(Exception e, Object result) {
        super.onCompleted(e, result);
        hideLoading();

        ProfileResponse profileResponse = new Gson().fromJson((JsonObject) result, ProfileResponse.class);

        if (profileResponse != null) {
            if (profileResponse.getStatus().equals("ok"))
                sharePrefManager.saveUser(profileResponse.getUser());
            Toast.makeText(this, getString(R.string.notify_update_profile_succsessful), Toast.LENGTH_SHORT).show();
        }


    }

    public void init() {

        gender = findViewById(R.id.gender);
        tvEmail = findViewById(R.id.tvEmail);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        tvAddress = findViewById(R.id.tv_address);
        btnEditAddress = findViewById(R.id.btn_edit_address);
        tvBank = findViewById(R.id.tv_bank);
        btnEditBank = findViewById(R.id.btn_edit_bank);
        tvCompany = findViewById(R.id.tvCompany);
        btnEditCompany = findViewById(R.id.btn_edit_company);
        tvPhone = findViewById(R.id.tvPhone);
        btnEditPhone = findViewById(R.id.btn_edit_phone);
        tvDescription = findViewById(R.id.tvDescription);
        btnEditDescription = findViewById(R.id.btn_edit_description);
        tvCredit = findViewById(R.id.tv_credit);
        tvFullName = findViewById(R.id.tvFullName);
        btnEditFullName = findViewById(R.id.btn_edit_full_name);
        tvOther = findViewById(R.id.tvOther);
        btnEditOther = findViewById(R.id.btn_edit_other);

    }

}
