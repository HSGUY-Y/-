package util;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.entity.AddmsgEntity;
import com.entity.ContactEntity;
import com.example.end.Addcontact;
import com.example.end.R;
import com.example.end.User;
import com.example.end.mainUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentContact extends Fragment {
    private ContactEntity[] contactEntities;
    public  ListView contactlist;
    private String headshotmain;//主用户头像
    private LinearLayout addcontact;
    private TextView messagesign;
    private AddmsgEntity[] addmsgEntities;
    public static FragmentContact fragmentContact;
    int pos=0;

    public FragmentContact(ContactEntity[] contactEntities,int pos,String headshotmain,AddmsgEntity[] addmsgEntities) {
        this.contactEntities = contactEntities;
        this.pos=pos;
        this.headshotmain=headshotmain;
        this.addmsgEntities=addmsgEntities;
    }
    public FragmentContact(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.contact_list_view,container,false);
        //使用getActivity获取上下文环境
        fragmentContact=this;
        final mainUI activity =(mainUI) getActivity();
        //设置listview
        addcontact = view.findViewById(R.id.addContact);
        contactlist =view.findViewById(R.id.contactlist);
        messagesign = view.findViewById(R.id.messageSign);
        messagesign.setVisibility(View.GONE);
        int count=0;
        for(int i=0;addmsgEntities!=null&&i<addmsgEntities.length;i++){
            if(addmsgEntities[i]!=null){
                if(addmsgEntities[i].getMsgID()>0&&addmsgEntities[i].getAddmsgstatus().equals("new")){
                    count++;
                }
            }
        }
        if(count>0){
            messagesign.setVisibility(View.VISIBLE);
            messagesign.setText(String.valueOf(count));
        }


        final List<HashMap<String,Object>> list = new ArrayList<>();
        HashMap<String,Object> map;
        String[] name = {
               "icon", "咕噜","咕噜01","咕噜02","咕噜03","咕噜04","咕噜05","咕噜06","咕噜07","咕噜08","咕噜09","咕噜10","咕噜11"
        };
        int [] pic = {R.drawable.imagebtn,R.drawable.gulu_end,R.drawable.gulu01,R.drawable.gulu02,R.drawable.gulu03,R.drawable.gulu04,R.drawable.gulu05,
                R.drawable.gulu06,R.drawable.gulu07,R.drawable.gulu08,R.drawable.gulu09,R.drawable.gulu10,R.drawable.gulu11};
        for(int i=0;contactEntities[i]!=null&&contactEntities[i].getFriendacc()!=null;i++){
            map= new HashMap<String, Object>();
            if(contactEntities[i].getContactID()>0){
            if(contactEntities[i].getHeadshot()!=null){
            for(int x=0;x<name.length;x++){
                if(contactEntities[i].getHeadshot().equals(name[x]))
                map.put("headshot",pic[x]);
            }}
            if(contactEntities[i].getRemark().equals(""))
            {map.put("nickname",contactEntities[i].getNickname());}
            else
                map.put("nickname",contactEntities[i].getRemark());
            map.put("account",contactEntities[i].getFriendacc());
            list.add(map);
        }}
        ConlistAdapter conlistAdapter = new ConlistAdapter(activity,R.layout.contact,list);
        contactlist.setAdapter(conlistAdapter);

        contactlist.setSelection(pos);

        //listview单击事件
        contactlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, User.class);
                intent.putExtra("headshot",(int)list.get(position).get("headshot"));//好友头像
                intent.putExtra("headshotstr",headshotmain);//用户头像
                intent.putExtra("nickname",list.get(position).get("nickname").toString());//好友昵称
                intent.putExtra("account",contactEntities[position].getMainacc());//用户ID
                intent.putExtra("raccount",list.get(position).get("account").toString());//好友ID
                intent.putExtra("sign",contactEntities[position].getSign());//好友个签
                intent.putExtra("remark",contactEntities[position].getRemark());
                startActivity(intent);
                Toast.makeText(activity,list.get(position).get("nickname").toString()+"打开用户面板",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加好友界面
                mainUI.mainui.countcc=0;
                Intent intent = new Intent(getActivity(), Addcontact.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("addmsg",addmsgEntities);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(getActivity(),"添加好友界面",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pos = contactlist.getFirstVisiblePosition();
        mainUI.mainui.thiscontactpos=pos;
    }


}
