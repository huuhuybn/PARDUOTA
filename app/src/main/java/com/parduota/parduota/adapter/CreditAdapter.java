package com.parduota.parduota.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parduota.parduota.R;
import com.parduota.parduota.model.Credit;

import java.util.ArrayList;

public class CreditAdapter extends BaseAdapter {


    private final ArrayList<Credit> credits;
    private final Context context;

    public CreditAdapter(ArrayList<Credit> credits, Context context) {
        this.credits = credits;
        this.context = context;
    }


    @Override
    public int getCount() {
        return credits.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.credit, viewGroup, false);


        TextView textView = view.findViewById(R.id.tvCredit);
        textView.setText(credits.get(i).getTitle());
        return view;

    }
}
