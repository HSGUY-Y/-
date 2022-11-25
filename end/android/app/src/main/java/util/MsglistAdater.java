package util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.end.R;

import java.util.HashMap;
import java.util.List;

public class MsglistAdater extends BaseAdapter {
    private Context context;
    private int resourceId;
    private List<HashMap<String,Object>> list;
    private String TAG="MsglistAdater";
    public MsglistAdater(Context context,int resourceId,List<HashMap<String,Object>> list){
        this.context=context;
        this.resourceId=resourceId;
        this.list=list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if(convertView!=null){
            view=convertView;
        }
        else{
            view=LayoutInflater.from(context).inflate(resourceId,parent,false);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder==null){
            holder= new ViewHolder();
            holder.headshot=view.findViewById(R.id.headshot);
            holder.nickname=view.findViewById(R.id.nickname);
            holder.message=view.findViewById(R.id.message);
            holder.messageSign=view.findViewById(R.id.messageSign);
            view.setTag(holder);
        }
        if(list.get(position).get("headshot")!=null){
            holder.headshot.setVisibility(View.VISIBLE);
            holder.nickname.setVisibility(View.VISIBLE);
            holder.message.setVisibility(View.VISIBLE);
            holder.messageSign.setVisibility(View.VISIBLE);
            holder.headshot.setImageResource((int)list.get(position).get("headshot"));
            holder.nickname.setText(list.get(position).get("nickname").toString());
            holder.message.setText(list.get(position).get("message").toString());
            holder.messageSign.setText(list.get(position).get("messageSign").toString());
            if(holder.messageSign.getText().toString().equals("0")){
               holder.messageSign.setVisibility(View.GONE);
            }else{
               holder.messageSign.setVisibility(View.VISIBLE);
            }
        }else{
            holder.headshot.setVisibility(View.GONE);
            holder.nickname.setVisibility(View.GONE);
            holder.message.setVisibility(View.GONE);
            holder.messageSign.setVisibility(View.GONE);
        }
        return view;
    }

    class ViewHolder {
        ImageView headshot;
        TextView nickname;
        TextView message;
        TextView messageSign;
    }
}

