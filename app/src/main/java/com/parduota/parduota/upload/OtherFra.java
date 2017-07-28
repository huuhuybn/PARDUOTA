package com.parduota.parduota.upload;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MFragment;
import com.parduota.parduota.model.Condition;

import java.util.ArrayList;

/**
 * Created by Dominic on 7/27/17.
 */

public class OtherFra extends MFragment {


    private EditText edt_price;
    private EditText edt_quantity;
    private EditText edt_address;
    private EditText category;
    private Spinner spinner_condition;
    private Button btn_submit;

    private AutoCompleteTextView tv_country;

    private ArrayList<Condition> conditions;


    @Override
    protected int setLayoutId() {
        return R.layout.fra_other;
    }

    @Override
    protected void initView(View view) {
        edt_price = (EditText) view.findViewById(R.id.edt_price);
        edt_address = (EditText) view.findViewById(R.id.edt_address);
        edt_quantity = (EditText) view.findViewById(R.id.edt_quantity);
        spinner_condition = (Spinner) view.findViewById(R.id.spinner_condition);

        conditions = new ArrayList<>();
        initCondition();

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity(), conditions);
        spinner_condition.setAdapter(spinnerAdapter);

        spinner_condition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Condition condition = conditions.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void setData(View view) {

    }

    public class SpinnerAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<Condition> conditions;

        public SpinnerAdapter(Context context, ArrayList<Condition> conditions) {
            this.conditions = conditions;
            this.context = context;
        }

        @Override
        public int getCount() {
            return conditions.size();
        }

        @Override
        public Object getItem(int i) {
            return conditions.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(context).inflate(R.layout.item_condition, viewGroup, false);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_title.setText(conditions.get(i).getName());
            return view;
        }
    }


    private void initCondition() {

        Condition new_ = new Condition();
        new_.setId(1000);
        new_.setName(getString(R.string.new_));
        conditions.add(new_);

        Condition new_other = new Condition();
        new_.setId(1500);
        new_.setName(getString(R.string.new_other));
        conditions.add(new_other);

        Condition new_with = new Condition();
        new_.setId(1750);
        new_.setName(getString(R.string.new_with));
        conditions.add(new_with);

        Condition manufacturer = new Condition();
        new_.setId(2000);
        new_.setName(getString(R.string.manufacturer));
        conditions.add(manufacturer);


        Condition seller = new Condition();
        new_.setId(2500);
        new_.setName(getString(R.string.seller));
        conditions.add(seller);


        Condition used = new Condition();
        new_.setId(3000);
        new_.setName(getString(R.string.used));
        conditions.add(used);


        Condition very_good = new Condition();
        new_.setId(4000);
        new_.setName(getString(R.string.very_good));
        conditions.add(very_good);


        Condition good = new Condition();
        new_.setId(5000);
        new_.setName(getString(R.string.good));
        conditions.add(good);


        Condition acceptable = new Condition();
        new_.setId(6000);
        new_.setName(getString(R.string.acceptable));
        conditions.add(acceptable);


        Condition working = new Condition();
        new_.setId(7000);
        new_.setName(getString(R.string.working));
        conditions.add(working);

    }
}
