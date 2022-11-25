package util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.end.R;

import java.util.List;
import java.util.Map;

public class IconViewAdapter extends BaseAdapter {
    Context context;
    List<Map<String,Object>> list;

    public IconViewAdapter(Context context,List<Map<String,Object>> list){
        this.context=context;
        this.list =list;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = LayoutInflater.from(context).inflate(R.layout.iconview,null);
        ImageView headshot = view.findViewById(R.id.headshot);
        TextView name = view.findViewById(R.id.name);
        headshot.setImageResource((int)list.get(position).get("pic"));
        name.setText(list.get(position).get("name").toString());
        return view;
    }
}
