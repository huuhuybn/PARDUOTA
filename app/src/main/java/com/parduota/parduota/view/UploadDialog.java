package com.parduota.parduota.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;

import com.parduota.parduota.R;

/**
 * Created by huy_quynh on 7/21/17.
 */

public class UploadDialog extends Dialog {

    public UploadDialog(@NonNull Context context) {
        super(context);
        initView();
    }

    public UploadDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected UploadDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private Button btn_gallery, btn_camera;

    private OnCameraClick onCameraClick;
    private OnGalleryClick onGalleryClick;

    public OnCameraClick getOnCameraClick() {
        return onCameraClick;
    }

    public void setOnCameraClick(OnCameraClick onCameraClick) {
        this.onCameraClick = onCameraClick;
    }

    public OnGalleryClick getOnGalleryClick() {
        return onGalleryClick;
    }

    public void setOnGalleryClick(OnGalleryClick onGalleryClick) {
        this.onGalleryClick = onGalleryClick;
    }

    public interface OnCameraClick {

        void onClick();

    }

    public interface OnGalleryClick {
        void onCLick();
    }

    public void initView() {

        setContentView(R.layout.dialog_upload);
        btn_camera = (Button) findViewById(R.id.btn_capture);
        btn_gallery = (Button) findViewById(R.id.btn_gallery);

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onGalleryClick != null)
                    onGalleryClick.onCLick();
            }
        });
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCameraClick != null) {
                    onCameraClick.onClick();
                }
            }
        });

    }


}
