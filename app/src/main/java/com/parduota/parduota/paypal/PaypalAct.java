package com.parduota.parduota.paypal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.model.paypal.PayPal;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

/**
 * THIS FILE IS OVERWRITTEN BY `androidSDK/src/<general|partner>sampleAppJava.
 * ANY UPDATES TO THIS FILE WILL BE REMOVED IN RELEASES.
 * <p>
 * Basic sample using the SDK to make a payment or consent to future payments.
 * <p>
 * For sample mobile backend interactions, see
 * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
 */
public class PaypalAct extends MActivity implements Constant {
    private static final String TAG = "paymentExample";
    /**
     * - Set to PayPalConfiguration.ENVIRONMENT_PRODUCTION to move real money.
     * <p>
     * - Set to PayPalConfiguration.ENVIRONMENT_SANDBOX to use your test credentials
     * from https://developer.paypal.com
     * <p>
     * - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
     * without communicating to PayPal's servers.
     */
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "AZh7l13WTTy3QDa2PHsvYMo3DQ1sKqSgMuV9Tm6PXnCfe5a_QwYDv-GOtbMP3onN2fLRKI0ZiH5Tunqj";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;

    private static final PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Parduota Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.parduota.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.parduota.com/legal"));

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_paypal);
//
//        Intent intent = new Intent(this, PayPalService.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//        startService(intent);
//    }

    @Override
    protected int setLayoutId() {
        return R.layout.act_paypal;
    }

    private EditText editText;

    @Override
    protected void initView() {
        editText = findViewById(R.id.et_money);
        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = editText.getText().toString().trim();
                if (editText.getText().toString().trim().matches("")) {
                    editText.setError(getString(R.string.empty));
                    return;
                }
                onBuyPressed(amount);
            }
        });
    }

    private void onBuyPressed(String amount) {
        /* 
         * PAYMENT_INTENT_SALE will cause the payment to complete immediately.
         * Change PAYMENT_INTENT_SALE to 
         *   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
         *   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
         *     later via calls from your server.
         * 
         * Also, to include additional payment details and an item list, see getStuffToBuy() below.
         */
        PayPalPayment thingToBuy = getThingToBuy(amount);

        /*
         * See getStuffToBuy(..) for examples of some available payment options.
         */

        Intent intent = new Intent(this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getThingToBuy(String amount) {
        return new PayPalPayment(new BigDecimal(amount), "EUR", getString(R.string.buy_credit),
                PayPalPayment.PAYMENT_INTENT_SALE);
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
                        if (confirm != null) {
                            try {
                                Log.e(TAG, confirm.toJSONObject().toString(4));
                                Log.e(TAG, confirm.getPayment().toJSONObject().toString(4));
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
                                PayPal payPal = new Gson().fromJson(confirm.toJSONObject().toString(), PayPal.class);

                                String PayCode = payPal.getResponse().getId();

                                Intent intent = new Intent(CREDIT);
                                intent.putExtra(DATA, PayCode);
                                sendBroadcast(intent);

                                finish();
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

        /*
          TODO: Send the authorization response to your server, where it can
          exchange the authorization code for OAuth access and refresh tokens.

          Your server must then store these tokens, so that your server code
          can execute payments for this user in the future.

          A more complete example that includes the required app-server to
          PayPal-server integration is available from
          https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
         */

    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
