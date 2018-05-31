package com.parduota.parduota;

import com.parduota.parduota.abtract.MActivity;


/**
 * Created by huy_quynh on 6/13/17.
 */

public class SplashAC extends MActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.act_splash;
    }

    @Override
    protected void initView() {
        deQuiRequest();
    }

    private void deQuiRequest() {



        final String token = sharePrefManager.getAccessToken();

        if (token != null)
            startNewActivity(MainAC.class);
        else
            startNewActivity(LoginActivity.class);

        finish();

//        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this,
//                new PermissionsResultAction() {
//                    @Override
//                    public void onGranted() {
//                        // Proceed with initialization
//
//                    }
//
//                    @Override
//                    public void onDenied(String permission) {
//                        // Notify the user that you need all of the permissions
//                        deQuiRequest();
//                    }
//                });

    }


}
