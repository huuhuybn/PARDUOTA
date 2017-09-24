package com.parduota.parduota.upload;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MFragment;
import com.parduota.parduota.ion.Constant;

/**
 * Created by huy_quynh on 7/27/17.
 */

public class DesFra extends MFragment implements Constant {

    @Override
    protected int setLayoutId() {
        return R.layout.fra_des;
    }


    private EditText edt_des;
    private Button btn_next;

    @Override
    protected void initView(View view) {

        edt_des = (EditText) view.findViewById(R.id.edt_des);

        btn_next = (Button) view.findViewById(R.id.btn_next);
        btn_next.setText(getString(R.string.btn_next));
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String des = edt_des.getText().toString().trim();
                if (des.matches("") | des.length() < 20) {
                    edt_des.setError(getString(R.string.title_too_short));
                    return;
                }
                Intent intent = new Intent(ACTION_NEXT);
                intent.putExtra(INDEX, INPUT_DES);
                intent.putExtra(DATA, des);
                getActivity().sendBroadcast(intent);

            }
        });
    }

    @Override
    protected void setData(View view) {

    }
}
