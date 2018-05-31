package com.parduota.parduota;

import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.User;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GetVerifyAC extends MActivity implements FutureCallback, Constant {


    private EditText etSureName;
    private RadioGroup radioGender;
    private EditText etBankAccount;
    private EditText etPhone;
    private EditText etAddress;
    private EditText etPostCode;
    private SearchableSpinner tvCountry;
    private CheckBox checkbox;
    private LinearLayout layoutVip;
    private EditText etCompany;
    private EditText etVATNumber;
    private EditText etAddressCompany;
    private SearchableSpinner tvCountryBusiness;
    private EditText etFistName;
    private EditText etLastName;
    private EditText etPhoneCompany;
    private EditText etOther;
    private EditText etPostCodeCompany;

    private String token;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_get_verify;
    }

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        token = sharePrefManager.getAccessToken();

        etSureName = findViewById(R.id.etSureName);
        radioGender = findViewById(R.id.radioGender);

        etBankAccount = findViewById(R.id.etBankAccount);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etPostCode = findViewById(R.id.etPostCode);
        tvCountry = findViewById(R.id.tv_country);
        checkbox = findViewById(R.id.checkbox);


        // layout for Business User
        layoutVip = findViewById(R.id.layout_vip);
        etCompany = findViewById(R.id.etCompany);
        etPostCodeCompany = findViewById(R.id.etPostCodeCompany);
        etVATNumber = findViewById(R.id.etVATNumber);
        etAddressCompany = findViewById(R.id.etAddressCompany);
        tvCountryBusiness = findViewById(R.id.tvCountryBusiness);
        etFistName = findViewById(R.id.etFistName);
        etLastName = findViewById(R.id.etLastName);
        etPhoneCompany = findViewById(R.id.etPhoneCompany);
        etOther = findViewById(R.id.etOther);


        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    layoutVip.setVisibility(View.VISIBLE);
                } else layoutVip.setVisibility(View.GONE);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);//Menu Resource, Menu

        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:

                sendGetVerify();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void sendGetVerify() {
        String full_name = etSureName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String bank_account = etBankAccount.getText().toString().trim();


        int id = radioGender.getCheckedRadioButtonId();

        if (id < 0) {
            Toast.makeText(this, getString(R.string.notify_choose_gender), Toast.LENGTH_SHORT).show();
            return;
        } else if (full_name.trim().matches("")) {
            etSureName.setError(getString(R.string.notify_empty));
            return;
        } else if (address.trim().matches("")) {
            etAddress.setError(getString(R.string.notify_empty));
            return;
        } else if (phone.trim().matches("")) {
            etPhone.setError(getString(R.string.notify_empty));
            return;
        } else if (bank_account.trim().matches("")) {
            etBankAccount.setError(getString(R.string.notify_empty));
            return;
        }

        showLoading();

        View radioButton = radioGender.findViewById(id);
        int radioId = radioGender.indexOfChild(radioButton);
        RadioButton btn = (RadioButton) radioGender.getChildAt(radioId);

        String gender = btn.getText().toString().toLowerCase();

        String country = tvCountry.getSelectedItem().toString();
        String zip_code = etPostCode.getText().toString().trim();

        String business;

        if (checkbox.isChecked()) {
            business = "on";
        } else {

            business = "off";
        }

        String company = etCompany.getText().toString().trim();
        String company_zip_code = etPostCodeCompany.getText().toString().trim();
        String registered_number = etVATNumber.getText().toString().trim();

        String company_address = etAddressCompany.getText().toString().trim();

        String company_country = tvCountryBusiness.getSelectedItem().toString().trim();


        String p_name = etFistName.getText().toString().trim();

        String p_surname = etLastName.getText().toString().trim();

        String p_phone = etPhoneCompany.getText().toString().trim();

        String other = etOther.getText().toString().trim();

        ION.postFormDataWithToken(this, Constant.URL_GET_VERIFY, token, ION.getVerify(full_name, address, phone, bank_account, gender, country, zip_code, business, company, company_zip_code, registered_number, company_address, company_country, p_name, p_surname, p_phone, other), this);


    }

    @Override
    public void onCompleted(Exception e, Object result) {
        super.onCompleted(e, result);

        JsonObject jsonObject = (JsonObject) result;


        try {
            User user = new Gson().fromJson(jsonObject, User.class);
            sharePrefManager.saveUser(user);
            setResult(999);
            finish();
        } catch (Exception ignored) {

        }

        try {
            String status = jsonObject.get(STATUS).getAsString();
            if (status.equals(ERROR)) {
                Toast.makeText(this, getString(R.string.notify_you_sent_verify), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ignored) {

        }
        hideLoading();

    }
}
