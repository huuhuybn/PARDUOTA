package com.parduota.parduota.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parduota.parduota.R;

import java.util.Objects;

/**
 * Created by huy_quynh on 7/24/17.
 */

class DetailDialog extends Dialog {
    public DetailDialog(@NonNull Context context) {
        super(context);
        initView(context, "");
    }

    public DetailDialog(@NonNull Context context, String detail) {
        super(context);
        initView(context, detail);
    }


    public DetailDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        initView(context, "");
    }

    protected DetailDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context, "");
    }


    private void initView(Context context, String detail
    ) {
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        setContentView(R.layout.dialog_detail);
        Button btn_close = findViewById(R.id.btn_close);
        TextView tv_detail = findViewById(R.id.tv_detail);
        if (detail != null)
            tv_detail.setText(detail);


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
