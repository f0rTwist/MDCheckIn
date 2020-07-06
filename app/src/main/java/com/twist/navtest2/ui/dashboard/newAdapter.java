package com.twist.navtest2.ui.dashboard;

import com.twist.navtest2.Bean.People;
import com.twist.navtest2.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class newAdapter extends BaseAdapter {
    private List<People> list;
    private Context context;
    private LayoutInflater mInflater;
    private int current_index = -1;

    public newAdapter(Context context, List<People> list){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
    }
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    public void setCurrent_index(int current_index) {
        this.current_index = current_index;
    }

    @Override
    public long getItemId(int position) {
        return position;
        }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final People people = (People) getItem(position);
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_items,null);
            holder.tvName = convertView.findViewById(R.id.commend_item_name);
            holder.tvs2 = convertView.findViewById(R.id.commend_item_content);
            holder.ivphoto = convertView.findViewById(R.id.commend_item_tou_xiang);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        setItem(holder,people);


        return convertView;
    }

    private void setItem(ViewHolder holder, People people) {
        holder.tvName.setText(people.getName());
        holder.tvs2.setText(people.getT02());
        holder.ivphoto.setImageResource(people.getImgRes());
    }

    class ViewHolder{
        private TextView tvName;
        private ImageView ivphoto;
        private TextView tvs2;
    }
}
