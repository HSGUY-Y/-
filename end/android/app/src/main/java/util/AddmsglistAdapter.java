package util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dispose.Dispose;
import com.entity.AddmsgEntity;
import com.entity.CommonVo;
import com.entity.UserEntity;
import com.example.end.Addcontact;
import com.example.end.Inittimer;
import com.example.end.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class AddmsglistAdapter extends BaseAdapter {
    private Context context;
    private int resourceId;
    private List<Map<String,Object>> list;
    private String TAG="AddMsglistAdater";
    public AddmsglistAdapter(Context context, int resourceId, List<Map<String,Object>> list){
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
            holder.agree=view.findViewById(R.id.agree);
            holder.disagree=view.findViewById(R.id.disagree);

            view.setTag(holder);
        }
        holder.headshot.setImageResource((int)list.get(position).get("headshot"));
        holder.nickname.setText(list.get(position).get("nickname").toString());
        holder.message.setText(list.get(position).get("message").toString());
        holder.agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //同意操作
                //需要数据，msgid,account,raccount
                final CommonVo commonVo = new CommonVo();
                AddmsgEntity[] addmsgEntities = new AddmsgEntity[1];
                addmsgEntities[0]=new AddmsgEntity();
                addmsgEntities[0].setMsgID((int)list.get(position).get("msgid"));
                addmsgEntities[0].setSendacc(list.get(position).get("account").toString());
                addmsgEntities[0].setResiveacc(list.get(position).get("raccount").toString());
                commonVo.setAddmsgEntities(addmsgEntities);
                Log.i(TAG, list.get(position).get("raccount").toString());
                commonVo.setFlag("agree");
                final String[] string = {""};
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                      string[0] = new Dispose().chang(commonVo);
                    }
                };
                thread.start();
                if(string[0]==null)
                    string[0]="error";

                Toast.makeText(Addcontact.addcontact,"正在处理",Toast.LENGTH_SHORT).show();
                while (string[0].equals(""));
                Toast.makeText(Addcontact.addcontact,string[0],Toast.LENGTH_SHORT).show();


            }
        });
        holder.disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拒绝操作
                //需要数据，msgid
                final CommonVo commonVo = new CommonVo();
                AddmsgEntity[] addmsgEntities = new AddmsgEntity[1];
                addmsgEntities[0]=new AddmsgEntity();
                addmsgEntities[0].setMsgID((int)list.get(position).get("msgid"));
                commonVo.setAddmsgEntities(addmsgEntities);
                commonVo.setFlag("disagree");
                final String[] string = new String[1];
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                       string[0] =  new Dispose().chang(commonVo);

                    }
                };
                thread.start();
                if(string[0]==null)
                    string[0]="error";
                Toast.makeText(Addcontact.addcontact,"正在处理",Toast.LENGTH_SHORT).show();
                while (string[0].equals(""));
                Toast.makeText(Addcontact.addcontact,string[0],Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    class ViewHolder {
        ImageView headshot;
        TextView nickname;
        TextView message;
        Button agree;
        Button disagree;

    }

}
