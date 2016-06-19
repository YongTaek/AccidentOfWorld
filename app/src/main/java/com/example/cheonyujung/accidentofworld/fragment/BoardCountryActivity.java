package com.example.cheonyujung.accidentofworld.fragment;

import android.app.FragmentManager;
import android.os.Bundle;

import com.example.cheonyujung.accidentofworld.Base;
import com.example.cheonyujung.accidentofworld.R;

/**
 * Created by cheonyujung on 2016. 6. 19..
 */
public class BoardCountryActivity extends Base {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        super.setTitle("Country List");

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .replace(R.id.body, BoardCountryFragment.getInstence())
                .commit();

    }
}
