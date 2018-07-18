package com.parduota.parduota.abtract;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Objects;

/**
 * Created by huy_quynh on 6/2/17.
 */

public abstract class MFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutId(), container, false);
        printFindViewById(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setData(view);
    }

    protected abstract int setLayoutId();

    protected abstract void initView(View view);

    protected abstract void setData(View view);

    private void printFindViewById(View view) {

    }

    protected void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    public void showLoading(){
        MActivity mActivity = (MActivity) getActivity();
        (mActivity).showLoading();
    }
    public void hideLoading(){
        MActivity mActivity = (MActivity) getActivity();
        (mActivity).hideLoading();
    }


}
