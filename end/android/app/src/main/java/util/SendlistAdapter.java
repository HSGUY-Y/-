package util;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.end.R;

import java.util.HashMap;
import java.util.List;

public class SendlistAdapter extends BaseAdapter {
    private Context context;
    private int resourceId;
    private List<HashMap<String,Object>> list;
    private String TAG="MsglistAdater";
    public SendlistAdapter(Context context,int resourceId,List<HashMap<String,Object>> list){
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
            view= LayoutInflater.from(context).inflate(resourceId,parent,false);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder==null){
            holder= new ViewHolder();
            holder.rheadshot=view.findViewById(R.id.rheadshot);
            holder.sendmsg=view.findViewById(R.id.sendmsg);
            holder.sheadshot=view.findViewById(R.id.sheadshot);
            holder.resivemsg=view.findViewById(R.id.resivemsg);
            holder.left_layout=view.findViewById(R.id.left_layout);
            holder.right_layout=view.findViewById(R.id.right_layout);
            holder.sendpic =  view.findViewById(R.id.sendpic);
            holder.resivepic = view.findViewById(R.id.resivepic);
            view.setTag(holder);
        }
        holder.right_layout.setVisibility(View.GONE);
        holder.left_layout.setVisibility(View.GONE);
        if(list.get(position).get("Action")!=null)
        switch (list.get(position).get("Action").toString()){
            case "sendmsg":
                holder.right_layout.setVisibility(View.VISIBLE);
                holder.left_layout.setVisibility(View.GONE);
                holder.sheadshot.setImageResource((int)list.get(position).get("sheadshot"));
                holder.sendmsg.setText(list.get(position).get("sendmsg").toString());
                holder.sendpic.setVisibility(View.GONE);
                holder.sendmsg.setVisibility(View.VISIBLE);
                break;
            case "sendpic":
                holder.right_layout.setVisibility(View.VISIBLE);
                holder.left_layout.setVisibility(View.GONE);
                holder.sheadshot.setImageResource((int)list.get(position).get("sheadshot"));
                holder.sendpic.setVisibility(View.VISIBLE);
                holder.sendmsg.setVisibility(View.GONE);
                holder.sendpic.setImageResource((int)list.get(position).get("sendpic"));
                break;
            case "resivemsg":
                holder.left_layout.setVisibility(View.VISIBLE);
                holder.right_layout.setVisibility(View.GONE);
                holder.resivemsg.setVisibility(View.VISIBLE);
                holder.rheadshot.setImageResource((int)list.get(position).get("rheadshot"));
                holder.resivemsg.setText(list.get(position).get("resivemsg").toString());
                holder.resivepic.setVisibility(View.GONE);
                break;
            case "resivepic":
                holder.left_layout.setVisibility(View.VISIBLE);
                holder.right_layout.setVisibility(View.GONE);
                holder.resivepic.setVisibility(View.VISIBLE);
                holder.rheadshot.setImageResource((int)list.get(position).get("rheadshot"));
                holder.resivemsg.setVisibility(View.GONE);
                holder.resivepic.setImageResource((int)list.get(position).get("resivepic"));
                break;
        }

        holder.sheadshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开用户面板
                Toast.makeText(context,"好友",Toast.LENGTH_SHORT).show();
            }
        });
        holder.rheadshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"用户",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    class ViewHolder {
        ImageView rheadshot;
        ImageView sheadshot;
        TextView resivemsg;
        TextView sendmsg;
        ImageView sendpic;
        ImageView resivepic;
        LinearLayout left_layout;
        LinearLayout right_layout;
    }
}
