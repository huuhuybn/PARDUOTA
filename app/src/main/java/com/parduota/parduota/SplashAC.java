package com.parduota.parduota;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.parduota.parduota.abtract.MActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


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

    public void deQuiRequest() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this,
                new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        // Proceed with initialization
                        final String token = sharePrefManager.getAccessToken();
                        if (token != null)
                            startNewActivity(HomeActivity.class);
                        else
                            startNewActivity(LoginActivity.class);
                        finish();
                    }

                    @Override
                    public void onDenied(String permission) {
                        // Notify the user that you need all of the permissions
                        deQuiRequest();
                    }
                });

    }


}
