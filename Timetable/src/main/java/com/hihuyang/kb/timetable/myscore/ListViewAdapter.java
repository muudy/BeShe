package com.hihuyang.kb.timetable.myscore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.hihuyang.kb.timetable.R;

import java.util.List;


public class ListViewAdapter extends BaseAdapter {
    List<Item1> list;
    Context context;

    public ListViewAdapter(Context context, List<Item1> list) {
        this.list = list;
        this.context = context;
        //this.minflater=LayoutInflater.from(context);
    }

    public ListViewAdapter(List<Item1> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        // 自定义list
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // 返回空
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // // 返回0
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup arg2) {
        ViewHolder holder = new ViewHolder();
        //LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflater.inflate(resource, imageListView, true);


        //空对象？？？？
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //调用布局文件的
        view = inflater.inflate(R.layout.item_list1,
                null);
        //找到item布局文件中的id
        holder.tv_money = (TextView) view.findViewById(R.id.tv_money1);
        holder.tv_date = (TextView) view.findViewById(R.id.tv_date1);
        //给item中的控件赋值
        holder.tv_money.setText(list.get(position).getMoney());
        holder.tv_date.setText(list.get(position).getDate());
        return view;
        //view表示的是返回一行的view
    }

    //内部类
    class ViewHolder {
        TextView tv_money;
        TextView tv_date;
    }

}