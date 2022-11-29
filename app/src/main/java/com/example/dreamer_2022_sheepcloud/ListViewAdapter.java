package com.example.dreamer_2022_sheepcloud;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    ArrayList<ListViewAdapterData> list = new ArrayList<ListViewAdapterData>();

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Cursor getItem(int i) {
//       return (Cursor) list.get(i);
        if(list.size() != 0) {
            return (Cursor) list.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {
        final Context context = viewgroup.getContext();


        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listv_item, viewgroup, false);
        }

        TextView titleText = view.findViewById(R.id.item_title);
        TextView contextText = view.findViewById(R.id.item_content);
        TextView cateText = view.findViewById(R.id.item_cate);

        ListViewAdapterData listItem = list.get(i);

        titleText.setText(listItem.getTitle());
        contextText.setText(listItem.getContent());
        cateText.setText(listItem.getCategory());

        return view;
    }

    public void addItemToList(String title, String content, String category) {
        ListViewAdapterData listdata = new ListViewAdapterData();
        
        listdata.setTitle(title);
        listdata.setContent(content);
        listdata.setCategory(category);

        list.add(listdata);
    }


}
