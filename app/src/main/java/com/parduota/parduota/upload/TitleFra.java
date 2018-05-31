package com.parduota.parduota.upload;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MFragment;
import com.parduota.parduota.ion.Constant;

import java.util.Objects;

/**
 * Created by Dominic on 7/26/17.
 */

public class TitleFra extends MFragment implements Constant {

    private EditText edt_title;

    @Override
    protected int setLayoutId() {
        return R.layout.fra_title;
    }

    @Override
    protected void initView(View view) {

        edt_title = view.findViewById(R.id.edt_title);
        Button btn_next = view.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edt_title.getText().toString().trim();
                if (title.matches("") | title.length() < 10) {
                    edt_title.setError(getString(R.string.title_too_short));
                    return;
                }
                Intent intent = new Intent(ACTION_NEXT);
                intent.putExtra(DATA, title);
                intent.putExtra(INDEX,INPUT_TITLE);
                Objects.requireNonNull(getActivity()).sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void setData(View view) {

    }
}
