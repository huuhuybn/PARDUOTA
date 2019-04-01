package com.parduota.parduota;

import android.content.Intent;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.parduota.parduota.abtract.MActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.parduota.parduota.face.OnFinish;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.model.Login;
import com.parduota.parduota.model.User;
import com.parduota.parduota.remote.RetrofitRequest;
import com.parduota.parduota.remote.RetrofitClient;
import com.parduota.parduota.utils.SharePrefManager;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends MActivity implements LoaderCallbacks<Cursor> {

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    private CallbackManager callbackManager;

    @Override
    protected void initView() {
        // Set up the login form.

        RadioGroup radioGroup = findViewById(R.id.radioLanguage);

        if (sharePrefManager.getLocale().equals(SharePrefManager.ENG)) {
            radioGroup.check(radioGroup.getChildAt(0).getId());
        } else {
            radioGroup.check(radioGroup.getChildAt(1).getId());
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                Log.e("ABC", i + "");
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.english:
                        sharePrefManager.setLocale(SharePrefManager.ENG);
                        recreate();
                        break;
                    case R.id.lithuania:
                        sharePrefManager.setLocale(SharePrefManager.LT);
                        recreate();
                        break;
                }

            }
        });


        LoginManager.getInstance().logOut();
        mEmailView = findViewById(R.id.email);

        User user = sharePrefManager.getUser();

        Log.e("ABC", new Gson().toJson(user));

        if (user != null) {
            String email = user.getEmail();
            if (!email.contains("domain"))
                mEmailView.setText(email);
        }

        String email = sharePrefManager.getAccountLogin();
        if (email != null) mEmailView.setText(email);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();

            }
        });

        View mLoginFormView = findViewById(R.id.login_form);
        View mProgressView = findViewById(R.id.login_progress);

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);


        loginButton.setReadPermissions(Collections.singletonList("public_profile"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                showLoading();

                //Profile profile = Profile.getCurrentProfile();

                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                                try {
                                    Log.e(TAG, "id" + user.optString("id"));
                                    Log.e(TAG, "id" + user.optString("first_name"));
                                    Log.e(TAG, "id" + user.optString("last_name"));


                                    RetrofitRequest apiService =
                                            RetrofitClient.getClient(LoginActivity.this).create(RetrofitRequest.class);

                                    apiService.loginWithFacebook(user.optString("first_name") + " " + user.optString("last_name"), user.optString("id"), FACEBOOK).enqueue(new Callback<Login>() {
                                        @Override
                                        public void onResponse(Call<Login> call, Response<Login> response) {
                                            hideLoading();

                                            if (Constant.isDEBUG)
                                                Log.e("LOGIN CALLBACK", new Gson().toJson(response));
                                            if ((response.body()).getError() != null) {
                                                showToast(getString(R.string.notify_email_not_active));
                                                return;
                                            }

                                            if ((response.body()).getUser().getBlock() == 1) {
                                                Toast.makeText(getApplicationContext(), getString(R.string.notify_your_account_is_block), Toast.LENGTH_LONG).show();
                                                return;
                                            }

                                            hideKeyBoard();
                                            sharePrefManager.saveAccessToken((response.body()).getToken());

                                            if ((response.body()).getUser().getVerified() == 0) {
                                                sharePrefManager.setVerifyStatus(2);
                                                (response.body()).getUser().setVerified(2);

                                            } else
                                                sharePrefManager.setVerifyStatus((response.body()).getUser().getVerified());

                                            sharePrefManager.saveUser((response.body()).getUser());

                                            sharePrefManager.setIsFacebookLogin(true);

                                            startNewActivity(MainAC.class);
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Call<Login> call, Throwable t) {
                                            hideLoading();
                                        }
                                    });


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("ERR", error.getMessage());
            }
        });

        findViewById(R.id.sign_up).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewActivityForResult(SignUpAC.class);
            }
        });

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        hideKeyBoard();


        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            showLoading();


            RetrofitRequest apiService =
                    RetrofitClient.getClient(LoginActivity.this).create(RetrofitRequest.class);

            apiService.loginViaEmail(email, password).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    hideLoading();
                    if (response.code() == 200) {
                        // login success
                        try {

                            Login login = new Gson().fromJson(response.body(), Login.class);

                            if (login.getUser().getBlock() == 1) {
                                Toast.makeText(getApplicationContext(), getString(R.string.notify_your_account_is_block), Toast.LENGTH_LONG).show();
                                return;
                            }

                            hideKeyBoard();
                            if (Constant.isDEBUG) Log.e("TOKEN", login.getToken());

                            sharePrefManager.saveAccountLogin(login.getUser().getEmail());
                            sharePrefManager.saveAccessToken(login.getToken());
                            sharePrefManager.saveUser(login.getUser());
                            sharePrefManager.setVerifyStatus(login.getUser().getVerified());

                            if (Constant.isDEBUG)
                                Log.e("EMAIL", login.getUser().getEmail());

                            startNewActivity(MainAC.class);

                            finish();

                        } catch (Exception e1) {

                            showToast(e1.toString());

                        }

                    }

                    if (response.code() == 403)

                    {

                        QuickAsyncTask quickAsyncTask = new QuickAsyncTask();
                        quickAsyncTask.setOnFinish(new OnFinish<String>() {
                            @Override
                            public void onFinished(String s) {

                                // login fail

                                //{"error":{"message":"403 Forbidden","status_code":403}}


                                try {
                                    JSONObject jsonObject = new JSONObject(s);

                                    JSONObject error = jsonObject.getJSONObject("error");
                                    if (error != null) {
                                        Toast.makeText(getApplicationContext(), getString(R.string.notify_wrong_password), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {

                                    showToast(e.toString());
                                }

                                try {
                                    // login success but not verified yet
//                            {
//                                "status": "error",
//                                    "code": 403,
//                                    "error": "verified"
//                            }


                                    JSONObject jsonObject = new JSONObject(s);
                                    String status = jsonObject.getString("status");
                                    String code = jsonObject.getString("code");
                                    String error = jsonObject.getString("error");
                                    if (error.equals("verified")) {

                                        showToast(getString(R.string.notify_verify_email));

                                    }

                                } catch (Exception ignored) {

                                }
                            }
                        });
                        try {
                            quickAsyncTask.execute(response.errorBody().bytes());
                        } catch (IOException e) {
                            e.printStackTrace();

                            showToast(e.toString());
                        }

                    }


                }


                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    hideLoading();

                    Log.e("BUG", t.getMessage());

                    showToast(t.getMessage());

                }

            });

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        //addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

//    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
//        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<>(LoginActivity.this,
//                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
//
//        mEmailView.setAdapter(adapter);
//    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 999) {

            if (resultCode == RESULT_OK) {
                String email = data.getStringExtra(DATA);
                mEmailView.setText(email);
            }

        }

    }


    public class QuickAsyncTask extends AsyncTask<byte[], Long, String> {


        public OnFinish<String> onFinish;


        public OnFinish<String> getOnFinish() {
            return onFinish;
        }

        public void setOnFinish(OnFinish<String> onFinish) {
            this.onFinish = onFinish;
        }

        @Override

        protected String doInBackground(byte[]... bytes) {
            String string = new String(bytes[0]);
            return string;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            onFinish.onFinished(s);

        }
    }
}

