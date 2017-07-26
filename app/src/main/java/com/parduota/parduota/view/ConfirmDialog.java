package com.parduota.parduota.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import com.parduota.parduota.R;

/**
 * Created by huy_quynh on 7/6/17.
 */

class ConfirmDialog extends Dialog {
    public ConfirmDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public ConfirmDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init();
    }

    protected ConfirmDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }


    public void init() {
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        setContentView(R.layout.dialog_confirm);

    }

}
