package com.parduota.parduota.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.parduota.parduota.R;

/**
 * Created by MAC2015 on 11/27/17.
 */

public class EditDialog extends DialogFragment {

    public boolean isNumberInput = false;

    public interface OnEditFinishListener {
        void onFinish(String text);
    }

    public OnEditFinishListener getOnEditFinishListener() {
        return onEditFinishListener;
    }

    public void setOnEditFinishListener(OnEditFinishListener onEditFinishListener) {
        this.onEditFinishListener = onEditFinishListener;
    }


    public Context context;
    public String tvContainer;

    public String getTvContainer() {
        return tvContainer;
    }

    public void setTvContainer(String tvContainer) {
        this.tvContainer = tvContainer;
    }

    public OnEditFinishListener onEditFinishListener;


    public EditText getEtContent() {
        return etContent;
    }

    public void setEtContent(EditText etContent) {
        this.etContent = etContent;
    }

    private EditText etContent;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit, null);

        etContent = (EditText) view.findViewById(R.id.etContent);
        etContent.setText(tvContainer);

        if (isNumberInput) etContent.setInputType(InputType.TYPE_CLASS_NUMBER);


        Editable editable = etContent.getText();
        Selection.setSelection(editable, tvContainer.length());


        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etContent, InputMethodManager.SHOW_FORCED);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        String content = etContent.getText().toString().trim();
                        if (content.matches("")) {
                            etContent.setError(getActivity().getString(R.string.notify_input));
                            return;
                        }
                        onEditFinishListener.onFinish(content);
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.btn_close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
