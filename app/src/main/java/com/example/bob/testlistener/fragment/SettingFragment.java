package com.example.bob.testlistener.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bob.testlistener.R;
import com.example.bob.testlistener.application.GlobalApplication;
import com.example.bob.testlistener.base.BaseFragment;
import com.example.bob.testlistener.database.greendao.DaoSession;
import com.example.bob.testlistener.database.greendao.User;
import com.example.bob.testlistener.database.greendao.UserDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bob on 2017/10/31.
 */

public class SettingFragment extends BaseFragment {

    @BindView(R.id.tv_text)
    TextView mTvText;

    private DaoSession mDaoSession;
    private UserDao userDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_setting,container,false);
        ButterKnife.bind(this,view);
        mContext = getActivity().getApplicationContext();
        mDaoSession = GlobalApplication.getInstance().getDaoSession();
        userDao = mDaoSession.getUserDao();
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initData() {
        User user = new User();
        user.setUserName("Alain").setPassword("1234");
        userDao.insert(user);
    }

    @OnClick(R.id.tv_text)
    void textClick(View view){
        initData();
        User user = new User();
        user.setUserName("Alex").setPassword("12345678").setGender(0);
        userDao.insertOrReplace(user);
        mDaoSession.clear();
//        List<User> userList =  userDao.queryBuilder().where(UserDao.Properties.Password.eq("1234")).list();
        List<User> userList = getAllUser();
        int size = userList.size();
        for (int i = 0; i < size; i++) {
            Toast.makeText(getActivity(),userList.get(i).getUserName(),Toast.LENGTH_LONG).show();
        }
    }

    private List<User> getAllUser(){
        return userDao.loadAll();
    }
}
