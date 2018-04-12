package com.parduota.parduota;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.parduota.parduota.abtract.MActivity;

import java.util.ArrayList;
import java.util.List;

import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.Login;
import com.parduota.parduota.model.User;

import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends MActivity implements LoaderCallbacks<Cursor> {


    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };


    public void loadEmailSaved() {

    }

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    CallbackManager callbackManager;

    private User user;

    @Override
    protected void initView() {
        // Set up the login form.
        mEmailView = findViewById(R.id.email);


        user = sharePrefManager.getUser();

        Log.e("ABC", new Gson().toJson(user));

        if (user != null) {
            String email = user.getEmail();
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

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                showLoading();
                Profile profile = Profile.getCurrentProfile();
                ION.postData(getApplicationContext(), ION.URL_LOGIN_FACEBOOK, ION.loginFacebook("", profile.getName(), profile.getId()), Login.class, new FutureCallback() {
                    @Override
                    public void onCompleted(Exception e, Object result) {
                        hideLoading();
                        if (e != null) {
                            Log.e("ABC", e.toString());
                            return;
                        }
                        Login login = (Login) result;
                        if (Constant.isDEBUG) Log.e("LOGIN CALLBACK", new Gson().toJson(login));
                        if (login.getError() != null) {
                            showToast(getString(R.string.notify_email_not_active));
                            return;
                        }
                        hideKeyBoard();
                        sharePrefManager.saveAccessToken(login.getToken());
                        sharePrefManager.saveUser(login.getUser());
                        startNewActivity(MainAC.class);
                        finish();
                    }
                });
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
                startNewActivityForResult(SignUpAC.class, 999);
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

            Ion.with(context)
                    .load(Constant.URL_LOGIN).setBodyParameters(ION.loginForm(email, password)).asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            hideLoading();
                            if (Constant.isDEBUG) Log.e("DATA_LOGIN", result);

                            if (e != null) {
                                Toast.makeText(getApplicationContext(), getString(R.string.notify_internet_connection), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // login success
                            try {

                                Login login = new Gson().fromJson(result, Login.class);

                                if (login.getUser().getBlock() == 1) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.notify_your_account_is_block), Toast.LENGTH_LONG).show();
                                    return;
                                }

                                hideKeyBoard();
                                if (Constant.isDEBUG) Log.e("TOKEN", login.getToken());


                                sharePrefManager.saveAccountLogin(login.getUser().getEmail());
                                sharePrefManager.saveAccessToken(login.getToken());
                                sharePrefManager.saveUser(login.getUser());
                                sharePrefManager.setVerifyStatus(true);

                                if (Constant.isDEBUG)
                                    Log.e("EMAIL", login.getUser().getEmail());


                                startNewActivity(MainAC.class);
                                finish();


                            } catch (Exception e1) {

                            }


                            // login success but not verified yet
//                            {
//                                "status": "error",
//                                    "code": 403,
//                                    "error": "verified"
//                            }

                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String status = jsonObject.getString("status");
                                String code = jsonObject.getString("code");
                                String error = jsonObject.getString("error");
                                if (error.equals("verified")) {

                                    sharePrefManager.saveAccountLogin(email);
                                    sharePrefManager.setVerifyStatus(false);
                                    sharePrefManager.saveUser(null);
                                    startNewActivity(MainAC.class);
                                    finish();
                                    return;

                                }

                            } catch (Exception e2) {

                            }

                            // login fail

                            //{"error":{"message":"403 Forbidden","status_code":403}}
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                JSONObject error = jsonObject.getJSONObject("error");
                                if (error != null) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.notify_wrong_password), Toast.LENGTH_SHORT).show();
                                }
                                return;


                            } catch (Exception e3) {

                            }

                        }
                    });

//            ION.postData(this, Constant.URL_LOGIN, ION.loginForm(email, password), Login.class, new FutureCallback() {
//                @Override
//                public void onCompleted(Exception e, Object result) {
//                    hideLoading();
//                    if (e != null) {
//                        return;
//                    }
//
//                    Login login = (Login) result;
//
//                    if (login.getError() != null) {
//                        showToast(getString(R.string.notify_wrong_password));
//                        return;
//                    }
//
//                    hideKeyBoard();
//                    if (Constant.isDEBUG) Log.e("TOKEN", login.getToken());
//
//                    sharePrefManager.saveAccountLogin(login.getUser().getEmail());
//                    sharePrefManager.saveAccessToken(login.getToken());
//                    sharePrefManager.saveUser(login.getUser());
//
//                    Log.e("EMAIL", login.getUser().getEmail());
//                    startNewActivity(MainAC.class);
//                    finish();
//
//
//                }
//            });
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
        int IS_PRIMARY = 1;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 999) {

            String email = data.getStringExtra(DATA);
            mEmailView.setText(email);

        }

    }
}

