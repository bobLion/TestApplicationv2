package com.example.bob.testlistener.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bob.testlistener.R;
import com.example.bob.testlistener.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bob on 2017/10/31.
 */

public class LearningFragment extends BaseFragment {

    @BindView(R.id.tv_my_shopcart)
    TextView mTvMyShopCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_learn,container,false);
        mContext = getActivity().getApplicationContext();
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setShopCart(String str){
        mTvMyShopCart.setText(str);
    }
}
