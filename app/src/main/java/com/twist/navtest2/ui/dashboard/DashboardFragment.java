package com.twist.navtest2.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.twist.navtest2.Bean.People;
import com.twist.navtest2.Chat;
import com.twist.navtest2.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private Button addchat;
    private int[] mimgs = new int[]{R.mipmap.ic_launcher};
    private newAdapter myAdapter;
    private String data[] = {"计算机1班签到群","计算机2班签到群","软件1班签到群","金融1班签到群","计算机3班签到群","软件2班签到群"};
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


        ListView listView = (ListView) root.findViewById(R.id.list_view);//在视图中找到ListView
        List<People> list = new ArrayList<People>();
        list.add(new People("计算机1班签到群","正在签到",mimgs[0]));
        list.add(new People("计算机2班签到群","",mimgs[0]));
        list.add(new People("Test签到群","",mimgs[0]));
        list.add(new People("111签到群","",mimgs[0]));
        myAdapter = new newAdapter(getActivity(),list);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), Chat.class);
                Bundle bundle=new Bundle();

                //利用上下文开启跳转
                startActivity(intent);
            }
        });
        return root;
    }
}