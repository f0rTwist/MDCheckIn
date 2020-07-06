package com.twist.navtest2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.twist.navtest2.CreateQActivity;
import com.twist.navtest2.R;
import com.twist.navtest2.SecActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button btn1,btn2;
    EditText et;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btn1 = root.findViewById(R.id.bt_sec);
        btn2 = root.findViewById(R.id.bt_create);
        et = root.findViewById(R.id.et_qun);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SecActivity.class);
                intent.putExtra("num",et.getText().toString());
                startActivityForResult(intent,0);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateQActivity.class);
                startActivityForResult(intent,1);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                Toast.makeText(getActivity().getApplicationContext(),"验证通过，加入成功！",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(getActivity().getApplicationContext(),"创建签到群成功！",Toast.LENGTH_SHORT).show();
        }
    }
}