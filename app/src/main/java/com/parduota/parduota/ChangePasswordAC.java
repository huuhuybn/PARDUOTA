package com.parduota.parduota;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.Login;
import com.parduota.parduota.model.Password;
import com.parduota.parduota.utils.SharePrefManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ChangePasswordAC extends MActivity implements FutureCallback {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_change_password;
    }

    private EditText etOldPassword;
    private EditText etNewPassword;
    private EditText etNewPasswordConfirm;

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        etOldPassword = (EditText) findViewById(R.id.et_old_password);
        etNewPassword = (EditText) findViewById(R.id.et_new_password);
        etNewPasswordConfirm = (EditText) findViewById(R.id.et_new_password_confirm);


    }

    public void savePassword() {

        String oldPass = etOldPassword.getText().toString().trim();
        String newPass = etNewPassword.getText().toString().trim();
        String etNewPassCon = etNewPasswordConfirm.getText().toString().trim();

        if (oldPass.matches("")) {
            etOldPassword.setError(getString(R.string.notify_input));
            return;
        }
        if (newPass.matches("")) {
            etNewPassword.setError(getString(R.string.notify_input));
            return;
        }
        if (etNewPassCon.matches("")) {
            etNewPasswordConfirm.setError(getString(R.string.notify_input));
            return;
        }
        if (!newPass.equals(etNewPassCon)) {
            Toast.makeText(this, getString(R.string.notify_not_match_password), Toast.LENGTH_SHORT).show();
            return;
        }
        showLoading();

        String token = SharePrefManager.getInstance(this).getAccessToken();
        ION.postDataWithToken(this, Constant.URL_UPDATE_PASSWORD, token, ION.changePass(oldPass, newPass, etNewPassCon), Password.class, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.change_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            // This ID represents the Home or Up button.
            savePassword();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCompleted(Exception e, Object result) {
        super.onCompleted(e, result);
        hideLoading();
        Password password = (Password) result;
        if (password != null) {
            String status = password.getStatus();
            if (status != null && status.equals(ERROR)) {
                Toast.makeText(this, getString(R.string.notify_wrong_password), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.notify_change_password_successful), Toast.LENGTH_SHORT).show();
                SharePrefManager.getInstance(this).clearAccessToken();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }
    }
}
