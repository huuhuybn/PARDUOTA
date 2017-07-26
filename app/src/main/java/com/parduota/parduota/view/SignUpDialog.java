package com.parduota.parduota.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;

import com.parduota.parduota.R;

/**
 * Created by huy_quynh on 7/8/17.
 */

public class SignUpDialog extends Dialog {

    public SignUpDialog(@NonNull Context context) {
        super(context);
    }

    public SignUpDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected SignUpDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    private Button btn_check_email, btn_cancel;

    public void initView() {
        setContentView(R.layout.dialog_sign_up);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_check_email = (Button) findViewById(R.id.btn_check_email);

        btn_check_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}
