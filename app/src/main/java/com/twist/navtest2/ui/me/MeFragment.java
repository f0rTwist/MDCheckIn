package com.twist.navtest2.ui.me;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.twist.navtest2.LoginActivity;
import com.twist.navtest2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MeFragment extends Fragment {

    private MeViewModel meViewModel;

    protected ImageView headshotView;
    protected TextView nameTextView;
    protected TextView majorTextView;
    protected TextView dateTextView;//当前日期
    //GlobalInfoDao gInfoDao;
    //UserInfoDao uInfoDao;

    //GlobalInfo gInfo;//需要isFirstUse和activeUserUid
    //UserInfo uInfo;//需要username昵称,gender，phone，headshot，institute，major，year
    private SharedPreferences courseSettings ;


    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.i("hidden","1");
        refresh();
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        courseSettings = getActivity().getSharedPreferences("course_setting", Context.MODE_PRIVATE);


        refresh();
        initDate();

        NavigationView navView = (NavigationView) getView().findViewById(R.id.user_nav);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Menu_main_Login:
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivityForResult(intent, 1);
                        break;
                    default:
                }
                return true;
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            if(resultCode == 1)
            {
                String session = data.getStringExtra("session");
                String cookies = data.getStringExtra("cookie");
                String sid = data.getStringExtra("sid");
                String name = data.getStringExtra("name");
                String major = data.getStringExtra("major");

                courseSettings.edit().putString("session",session).commit();
                courseSettings.edit().putString("cookie",cookies).commit();
                courseSettings.edit().putString("sid",sid).commit();
                courseSettings.edit().putString("name",name).commit();
                courseSettings.edit().putString("major",major).commit();
                courseSettings.edit().commit();
                headshotView.setImageResource(R.drawable.nav_female);
                Toast.makeText(getContext(),"登录成功！",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getContext(),"登录失败！",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onPause() {
        refresh();
        super.onPause();
    }

    @Override
    public void onResume() {

        refresh();
        super.onResume();
    }

    private void refresh(){
        String name = courseSettings.getString("name","不知者来此");
        String major = courseSettings.getString("major","未登录");

        headshotView = (ImageView) getActivity().findViewById(R.id.User_icon_image);
        if(true){
            // TO DO:根据用户性别修改头像
            headshotView.setImageResource(R.drawable.nav_female);
        }
        else{
            headshotView.setImageResource(R.drawable.nav_icon_male);
        }
        nameTextView = (TextView)getActivity().findViewById(R.id.User_main_name);
        nameTextView.setText(name+"，你好");
        majorTextView = (TextView)getActivity().findViewById(R.id.User_main_major);
        majorTextView.setText(major);
        dateTextView = (TextView) getActivity().findViewById(R.id.User_main_textDate);
    }

    private void initDate() {
        Date currentTime = new Date();
        String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTime);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)  w = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日  ");
        String dateString = formatter.format(currentTime);
        dateTextView.setText(dateString + "星期" + weekDays[w]);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me,container,false);
    }
}