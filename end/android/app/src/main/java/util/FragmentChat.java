package util;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.entity.CommonResult;
import com.entity.ContactEntity;
import com.entity.MessageEntity;
import com.entity.UserEntity;
import com.example.end.ChatUI;
import com.example.end.MainActivity;
import com.example.end.R;
import com.example.end.mainUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentChat extends Fragment {
    private MessageEntity[] messageEntities ;
    private MessageEntity[] messageEntities1;
    private int[]  count ;
    private String account;
    private String headshot;
    private String friendacc;
    public static FragmentChat fragmentChat;
    public ListView chatlist;
    private int pos=0;//存放当前listview位置

    public FragmentChat(MessageEntity[] messageEntities, MessageEntity[] messageEntities1, int[] count,
                        String account,String headshot,int pos) {
        this.messageEntities = messageEntities;
        this.messageEntities1 = messageEntities1;
        this.count = count;
        this.account = account;
        this.headshot = headshot;
        this.pos = pos ;
    }
    public FragmentChat(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.message_view,container,false);
        fragmentChat=this;
        //使用getActivity获取上下文环境
        final mainUI activity =(mainUI) getActivity();
        //设置listview
         chatlist =view.findViewById(R.id.chatlist);

        final List<HashMap<String,Object>> list = new ArrayList<>();
        HashMap<String,Object> map;
        String[] name = {
              "icon" , "咕噜","咕噜01","咕噜02","咕噜03","咕噜04","咕噜05","咕噜06","咕噜07","咕噜08","咕噜09","咕噜10","咕噜11"
        };
        int [] pic = {R.drawable.imagebtn,R.drawable.gulu_end,R.drawable.gulu01,R.drawable.gulu02,R.drawable.gulu03,R.drawable.gulu04,R.drawable.gulu05,
                R.drawable.gulu06,R.drawable.gulu07,R.drawable.gulu08,R.drawable.gulu09,R.drawable.gulu10,R.drawable.gulu11};
        MessageEntity messageEntity = new MessageEntity();
        int temp;
        for(int i=0;i<count.length-1;i++){//冒泡排序把未读消息放置在前
            for(int j=0;j<count.length-1-i;j++){
                if(count[j]<count[j+1]){
                    temp = count[j];
                    count[j]=count[j+1];
                    count[j+1]=temp;
                    messageEntity = messageEntities1[j];
                    messageEntities1[j]=messageEntities1[j+1];
                    messageEntities1[j+1]=messageEntity;
                }
            }
        }
        if(messageEntities1!=null)
        for(int i=0;messageEntities1[i]!=null;i++){
            if(messageEntities1[i].getMessageID()>0){
                map= new HashMap<String, Object>();
                if(messageEntities1[i].getHeadshot()!=null){
                for(int x=0;x<name.length;x++){
                    if(messageEntities1[i].getHeadshot().equals(name[x])){
                        map.put("headshot",pic[x]);
                    }
                }
                }
                map.put("nickname",messageEntities1[i].getNickname());
                if(messageEntities1[i].getMessage()==null&&messageEntities1[i].getPic()!=null){
                    map.put("message",messageEntities1[i].getPic());
                }else{
                    map.put("message",messageEntities1[i].getMessage());
                }
                map.put("messageSign",count[i]);
                list.add(map);
            }
        }
        MsglistAdater msglistAdater = new MsglistAdater(activity,R.layout.message,list);
        chatlist.setAdapter(msglistAdater);
        chatlist.setSelection(pos);//设置初始位置；
        //listview单击事件
        chatlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(activity,list.get(position).get("nickname").toString()+"打开聊天面板",Toast.LENGTH_SHORT).show();
                if(mainUI.mainui.countss>0){
                    mainUI.mainui.countss-=count[position];

                }
                Log.i("消息提示asdfasdfad",String.valueOf( mainUI.mainui.countss));
                //对信息进行拆分，只拿当前对话信息
                CommonResult commonResult = new CommonResult();
                MessageEntity[] messageEntitiess=new MessageEntity[200];
                for(int i=0,x=0;messageEntities[i]!=null;i++){

                    if(messageEntities[i]!=null&&messageEntities1[position]!=null){
                        if(messageEntities1[position].getSendacc().equals(account)){
                            if(messageEntities[i].getSendacc().equals(messageEntities1[position].getResiveacc())||
                                    messageEntities[i].getResiveacc().equals(messageEntities1[position].getResiveacc())){
                                messageEntitiess[x] = new MessageEntity();
                                messageEntitiess[x]=messageEntities[i];
                                x++;
                            }
                        }else{
                            if(messageEntities[i].getSendacc().equals(messageEntities1[position].getSendacc())||
                                    messageEntities[i].getResiveacc().equals(messageEntities1[position].getSendacc())){
                                messageEntitiess[x] = new MessageEntity();
                                messageEntitiess[x]=messageEntities[i];
                                x++;
                            }
                        }
                    }
                }
                if(messageEntities1[position]!=null){
                    if(messageEntities1[position].getResiveacc().equals(account)){
                        friendacc=messageEntities1[position].getSendacc();
                    }else
                        friendacc = messageEntities1[position].getResiveacc();
                }
                commonResult.setMessageEntities(messageEntitiess);
                UserEntity[] userEntities = new UserEntity[1];
                userEntities[0]=new UserEntity();
                userEntities[0].setAccount(account);
                userEntities[0].setNickname(list.get(position).get("nickname").toString());
                userEntities[0].setHeadshot(headshot);
                ContactEntity[] contactEntities = new ContactEntity[1];
                contactEntities[0]=new ContactEntity();
                contactEntities[0].setFriendacc(friendacc);
                commonResult.setUserEntities(userEntities);
                commonResult.setContactEntities(contactEntities);
                Bundle bundle = new Bundle();
                bundle.putSerializable("message",commonResult);
                Intent intent = new Intent(activity, ChatUI.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //listview长按事件
        chatlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(activity,list.get(position).get("nickname")+"删除信息",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        if(view !=null)
        {
            ViewGroup group = (ViewGroup) view.getParent();
            if(group!=null)
            group.removeView(view);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pos = chatlist.getFirstVisiblePosition();
        mainUI.mainui.thischatpos=pos;//传回main
    }
}
