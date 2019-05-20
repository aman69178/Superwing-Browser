package com.example.aman.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class HistoryListAdapter extends BaseAdapter {
    private Context context;
    private List<String> videoItems;
    private List<String> url;

    LayoutInflater inflater;

    public HistoryListAdapter(Context _context,
                               List<String> _items,List<String> _url) {
        context = _context;
        videoItems = _items;
        url=_url;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return videoItems.size();
    }

    public Object getItem(int position) {
        return videoItems.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View videoRow = inflater.inflate(R.layout.historyrow, null);
        ImageView videoThumb = (ImageView) videoRow
                .findViewById(R.id.icon);





videoThumb.setImageResource(R.drawable.bookmarkicon);



        TextView videoTitle = (TextView)videoRow
                .findViewById(R.id.subtitle);

        TextView videosubtitle = (TextView)videoRow
                .findViewById(R.id.title);



videoTitle.setText(videoItems.get(position).toString());

videosubtitle.setText(url.get(position).toString());




        return videoRow;
    }

}