package com.example.bob.testlistener.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.bob.testlistener.util.ThemeHelper;
import com.example.bob.testlistener.util.Themed;
import com.example.bob.testlistener.util.ThemedViewHolder;

/**
 * Created by Administrator on 2017/11/22.
 */

public abstract class ThemedAdapter<VH extends ThemedViewHolder>  extends RecyclerView.Adapter<VH> implements Themed {

    private ThemeHelper themeHelper;

    public ThemedAdapter(Context context) {
        themeHelper = ThemeHelper.getInstanceLoaded(context);
    }

    public ThemeHelper getThemeHelper() {
        return themeHelper;
    }

    public void setThemeHelper(ThemeHelper themeHelper) {
        this.themeHelper = themeHelper;
    }

    @Override
    public void refreshTheme(ThemeHelper theme) {
        setThemeHelper(theme);
        notifyDataSetChanged();
    }
}