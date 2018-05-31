package com.parduota.parduota.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.parduota.parduota.PhotoViewAC;
import com.parduota.parduota.R;
import com.parduota.parduota.ion.Constant;

import java.util.Objects;

/**
 * Created by MAC2015 on 11/1/17.
 */

public class PhotoFragment extends Fragment implements Constant {


    public static Fragment createInstance(String url) {

        PhotoFragment photoFragment = new PhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DATA, url);
        photoFragment.setArguments(bundle);
        return photoFragment;

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_photo, container, false);

        ImageView img = view.findViewById(R.id.img);

        final String url = PHOTO_URL + Objects.requireNonNull(getArguments()).getString(DATA);

        Glide.with(this).load(url).into(img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PhotoViewAC.class);
                intent.putExtra(DATA, url);
                startActivity(intent);
            }
        });

        return view;

    }
}
