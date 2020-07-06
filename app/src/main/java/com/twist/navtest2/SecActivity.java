package com.twist.navtest2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecActivity extends AppCompatActivity {
    String qun;
    Button btn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        qun = getIntent().getStringExtra("num");
        btn = findViewById(R.id.bt_sec_quit);
        tv = findViewById(R.id.secid);
        tv.setText("群号码："+qun);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}