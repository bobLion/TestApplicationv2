package com.example.bob.testlistener.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bob.testlistener.R;
import com.example.bob.testlistener.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Bob on 2017/10/31.
 */

public class SettingFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_setting,container,false);
        mContext = getActivity().getApplicationContext();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
