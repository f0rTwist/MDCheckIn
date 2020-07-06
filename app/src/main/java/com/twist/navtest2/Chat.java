package com.twist.navtest2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.twist.navtest2.Bean.People;
import com.twist.navtest2.ui.dashboard.newAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

public class Chat extends Activity {
    private TextView chat_name;
    private Button sign;
    String name;
    private int[] mimgs = new int[]{R.drawable.nav_female};
    List<People> list = new ArrayList<People>();
    newAdapter myAdapter;
    ListView listView;
    private Handler mHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chat_name=findViewById(R.id.chatname);
        name=getIntent().getStringExtra("chatname");
        myAdapter = new newAdapter(this,list);

        mHandler = new Handler();
        chat_name.setText("计算机1班签到群");
        sign=findViewById(R.id.sign);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("chatname",name);
                intent.setClass(Chat.this,MapActivity.class);
                startActivityForResult(intent, 1);

            }
        });


        listView = (ListView) findViewById(R.id.chat_lv);//在视图中找到ListView

        list.add(new People("test1","2020-06-13 16:31:29 签到",mimgs[0]));

        listView.setAdapter(myAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        list.add(new People("测试用户",simpleDateFormat.format(date)+ " 签到",mimgs[0]));
        myAdapter.notifyDataSetChanged();
    }
}
