package com.example.bob.testlistener.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Bob on 2017/10/31.
 */

public class BaseFragment extends Fragment {
    public int dialogTheme;

    public Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
