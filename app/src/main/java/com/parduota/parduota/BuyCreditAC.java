package com.parduota.parduota;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.adapter.CreditAdapter;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.Credit;
import com.parduota.parduota.model.User;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalOAuthScopes;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class BuyCreditAC extends MActivity {

    private static final String TAG = "paymentExample";

    private final ArrayList<Credit> credits = new ArrayList<>();
    /**
     * - Set to PayPalConfiguration.ENVIRONMENT_PRODUCTION to move real money.
     * <p>
     * - Set to PayPalConfiguration.ENVIRONMENT_SANDBOX to use your test credentials
     * from https://developer.paypal.com
     * <p>
     * - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
     * without communicating to PayPal's servers.
     */
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;

    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "AWIvbsLsuQrJ5mtKGlombTEGU5781rcuwnxuGVKr5zzs5fBhTrCwYUU7VEo0qWnDDfQY2HJ3l4AA5ZTF";


    // test
    //private static final String CONFIG_CLIENT_ID = "AWytwwkCAZBCBUsfPv7F00I5Jc7qeBD20L8nq9EPF3VOa_g-4Cvdcq3_MS6VyT_BIZISvilTEUtwe4sa";


    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;

    private static final PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Parduota")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.parduota.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.parduota.com/legal"));

    private Spinner spinner;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_buy_credit_ac;
    }

    @Override
    protected void initView() {
        initCredit();


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        setTitle(getString(R.string.title_activity_buy_credit_ac));
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);


        spinner = findViewById(R.id.spinner_credit);

        CreditAdapter creditAdapter = new CreditAdapter(credits, this);
        spinner.setAdapter(creditAdapter);

        findViewById(R.id.btnBuy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int index = spinner.getSelectedItemPosition();
                onBuyPressed(view, credits.get(index));
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initCredit() {
        Credit credit100 = new Credit();

        credit100.setPrice("2.00");
        credit100.setTitle("100 Credit €2.00 EUR");


        Credit credit500 = new Credit();
        credit500.setPrice("7.50");
        credit500.setTitle("500 Credit €7.50 EUR");


        Credit credit1000 = new Credit();

        credit1000.setPrice("10.00");
        credit1000.setTitle("1000 Credit €10.00 EUR");

        Credit credit2000 = new Credit();
        credit2000.setPrice("15.00");
        credit2000.setTitle("2000 Credit €10.00 EUR");


        Credit credit10000 = new Credit();
        credit10000.setPrice("50.00");
        credit10000.setTitle("2000 Credit €50.00 EUR");


        credits.add(credit100);
        credits.add(credit500);
        credits.add(credit1000);
        credits.add(credit2000);
        credits.add(credit10000);
    }

    private void onBuyPressed(View pressed, Credit credit) {
        /*
         * PAYMENT_INTENT_SALE will cause the payment to complete immediately.
         * Change PAYMENT_INTENT_SALE to
         *   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
         *   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
         *     later via calls from your server.
         *
         * Also, to include additional payment details and an item list, see getStuffToBuy() below.
         */
        PayPalPayment thingToBuy = getThingToBuy(credit.getPrice());

        /*
         * See getStuffToBuy(..) for examples of some available payment options.
         */

        Intent intent = new Intent(BuyCreditAC.this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getThingToBuy(String price) {
        return new PayPalPayment(new BigDecimal(price), "EUR", getString(R.string.buy_credit),
                PayPalPayment.PAYMENT_INTENT_SALE);
    }


    private PayPalOAuthScopes getOauthScopes() {
        /* create the set of required scopes
         * Note: see https://developer.paypal.com/docs/integration/direct/identity/attributes/ for mapping between the
         * attributes you select for this app in the PayPal developer portal and the scopes required here.
         */
        Set<String> scopes = new HashSet<String>(
                Arrays.asList(PayPalOAuthScopes.PAYPAL_SCOPE_EMAIL, PayPalOAuthScopes.PAYPAL_SCOPE_ADDRESS));
        return new PayPalOAuthScopes(scopes);
    }

    private void displayResultText(String result) {
        Toast.makeText(
                getApplicationContext(),
                result, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_PAYMENT:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentConfirmation confirm =
                                data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

               /* {
                    "client": {
                    "environment": "live",
                            "paypal_sdk_version": "2.16.0",
                            "platform": "Android",
                            "product_name": "PayPal-Android-SDK"
                },
                    "response": {
                    "create_time": "2018-04-14T05:18:18Z",
                            "id": "PAY-5BG8095666977105MLLIY5PY",
                            "intent": "sale",
                            "state": "approved"
                },
                    "response_type": "payment"
                }*/

                        if (confirm != null) {
                            try {
                                Log.i(TAG, confirm.toJSONObject().toString(4));
                                Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));


                                Ion.with(this).load(Constant.URL_ADD_CREDIT).setHeader(ION.authHeader(sharePrefManager.getAccessToken())).setBodyParameters(ION.addCredit(confirm.toJSONObject())).as(User.class).setCallback(new FutureCallback<User>() {
                                    @Override
                                    public void onCompleted(Exception e, User result) {
                                        //Log.e("ABC", result.toString());
                                        sharePrefManager.saveUser(result);
                                        sendBroadcast(new Intent(URL_UPDATE_CREDIT));
                                        displayResultText(getString(R.string.notify_buy_credit_successful));
                                        finish();
                                    }
                                });


                                /**
                                 *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                                 * or consent completion.
                                 * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                                 * for more details.
                                 *
                                 * For sample mobile backend interactions, see
                                 * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
                                 */
                                //displayResultText("PaymentConfirmation info received from PayPal");

                            } catch (JSONException e) {
                                Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                            }
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "The user canceled.");
                        break;
                    case PaymentActivity.RESULT_EXTRAS_INVALID:
                        Log.i(
                                TAG,
                                "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
                        break;
                }
                break;
            case REQUEST_CODE_FUTURE_PAYMENT:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PayPalAuthorization auth =
                                data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                        if (auth != null) {
                            try {
                                Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));

                                String authorization_code = auth.getAuthorizationCode();
                                Log.i("FuturePaymentExample", authorization_code);

                                sendAuthorizationToServer(auth);
                                displayResultText("Future Payment code received from PayPal");

                            } catch (JSONException e) {
                                Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
                            }
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i("FuturePaymentExample", "The user canceled.");
                        break;
                    case PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID:
                        Log.i(
                                "FuturePaymentExample",
                                "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
                        break;
                }
                break;
            case REQUEST_CODE_PROFILE_SHARING:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PayPalAuthorization auth =
                                data.getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
                        if (auth != null) {
                            try {
                                Log.i("ProfileSharingExample", auth.toJSONObject().toString(4));

                                String authorization_code = auth.getAuthorizationCode();
                                Log.i("ProfileSharingExample", authorization_code);

                                sendAuthorizationToServer(auth);
                                displayResultText("Profile Sharing code received from PayPal");

                            } catch (JSONException e) {
                                Log.e("ProfileSharingExample", "an extremely unlikely failure occurred: ", e);
                            }
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i("ProfileSharingExample", "The user canceled.");
                        break;
                    case PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID:
                        Log.i(
                                "ProfileSharingExample",
                                "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
                        break;
                }
                break;
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

        /**
         * TODO: Send the authorization response to your server, where it can
         * exchange the authorization code for OAuth access and refresh tokens.
         *
         * Your server must then store these tokens, so that your server code
         * can execute payments for this user in the future.
         *
         * A more complete example that includes the required app-server to
         * PayPal-server integration is available from
         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
         */

    }

    public void onFuturePaymentPurchasePressed(View pressed) {
        // Get the Client Metadata ID from the SDK
        String metadataId = PayPalConfiguration.getClientMetadataId(this);

        Log.i("FuturePaymentExample", "Client Metadata ID: " + metadataId);

        // TODO: Send metadataId and transaction details to your server for processing with
        // PayPal...
        displayResultText("Client Metadata Id received from SDK");
    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }


}
