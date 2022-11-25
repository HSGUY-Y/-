package com.example.end;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dispose.Dispose;
import com.entity.CommonResult;
import com.entity.CommonVo;
import com.entity.MessageEntity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import servier.Mymuservier;
import util.IconViewAdapter;
import util.SendlistAdapter;


public class ChatUI extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private TextView title;
    private ListView sendlist;
    private EditText sendtext;
    private Button button;
    private Inittimer inittimer;
    private Inittimer inittimer1;
    private Inittimer inittimer2;
    private String nickname;
    private String account;
    private String headshot;
    private CommonResult commonResult1 ;
    private String flag="";
    private String sendflag="";
    private String resiveacc;
    private GridView emoj;
    private ImageView iconbtn;
    private boolean conn=false;
    private TextView conns;
    private int positon=200;//保存当前listview首条记录的位置；
    private int chatID;
    private boolean flags=false;
    private String[] name = {"OK","棒！","加班","灵光一现","努力学习",
            "上课","收到","下课","谢谢","学霸喷雾","压力山大","耶！！"};
    private int[] pic={R.drawable.icon01,R.drawable.icon02,R.drawable.icon03,R.drawable.icon04,
            R.drawable.icon05,R.drawable.icon06,R.drawable.icon07, R.drawable.icon08,
            R.drawable.icon09,R.drawable.icon010,R.drawable.icon011,R.drawable.icon012};
    private int[] pic2={R.drawable.icon1,R.drawable.icon2,R.drawable.icon3,R.drawable.icon4,
            R.drawable.icon5,R.drawable.icon6,R.drawable.icon7, R.drawable.icon8,
            R.drawable.icon9,R.drawable.icon10,R.drawable.icon11,R.drawable.icon12};
    private String[] name1 = {
            "icon","咕噜","咕噜01","咕噜02","咕噜03","咕噜04","咕噜05","咕噜06","咕噜07","咕噜08","咕噜09","咕噜10","咕噜11"
    };
    private int [] pic1 = {R.drawable.imagebtn,R.drawable.gulu_end,R.drawable.gulu01,R.drawable.gulu02,R.drawable.gulu03,R.drawable.gulu04,R.drawable.gulu05,
            R.drawable.gulu06,R.drawable.gulu07,R.drawable.gulu08,R.drawable.gulu09,R.drawable.gulu10,R.drawable.gulu11};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_view);
        back=findViewById(R.id.back);
        title=findViewById(R.id.title);
        sendlist=findViewById(R.id.sendList);
        sendtext=findViewById(R.id.sendText);
        button=findViewById(R.id.button);
        emoj = findViewById(R.id.emoj);
        iconbtn=findViewById(R.id.iconbtn);
        emoj.setVisibility(View.GONE);
        conns=findViewById(R.id.conn);
        conns.setVisibility(View.GONE);

    }
    @Override
    protected void onStart() {
        super.onStart();
        //请求网络连接，检查网络是否通畅
        Intent intent = getIntent();
        CommonResult commonResult;

        Bundle bundle =intent.getExtras();
        commonResult=(CommonResult) bundle.getSerializable("message");
        //Log.i("聊天", commonResult.getMessageEntities()[0].getMsgstatus());
        nickname = commonResult.getUserEntities()[0].getNickname();//对方昵称
        account = commonResult.getUserEntities()[0].getAccount();//用户id查询主要字
        headshot =commonResult.getUserEntities()[0].getHeadshot();//用户头像
        resiveacc = commonResult.getContactEntities()[0].getFriendacc();//查询主要字
        Log.i("main转chat", commonResult.getContactEntities()[0].getFriendacc());

        if(commonResult.getMessageEntities()!=null){
            if(commonResult.getMessageEntities()[0]!=null){
                chatID=commonResult.getMessageEntities()[0].getMessageID();//记录最新消息ID
            }
        }
        new Listinit(commonResult).init();
        View v = sendlist.getChildAt(0);
        sendlist.setSelectionFromTop(positon,v==null?0:v.getTop());//设置listview开始位置
        title.setText(nickname);
        CommonVo commonVo = new CommonVo();
        commonVo.setUserEntities(commonResult.getUserEntities());
        commonVo.setContactEntities(commonResult.getContactEntities());
        commonVo.setFlag("getmsg");
        Log.i("commonv-", commonVo.getContactEntities()[0].getFriendacc());
        inittimer1 = new Inittimer(handler);
        inittimer1.commonVoconn = commonVo;
        inittimer1.timer.schedule(inittimer1.task1,1,2*1000);
        inittimer2 = new Inittimer(handler);
        inittimer2.timer.schedule(inittimer2.task2,1,4*1000);


        final List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map;
        for(int i =0;i<name.length;i++){
            map=new HashMap<>();
            map.put("name",name[i]);
            map.put("pic",pic[i]);
            list.add(map);
        }
        IconViewAdapter iconViewAdapter = new IconViewAdapter(this,list);
        emoj.setAdapter(iconViewAdapter);
        emoj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                CommonVo commonVo = new CommonVo();
                MessageEntity[] messageEntities = new MessageEntity[1];
                messageEntities[0]=new MessageEntity();
                messageEntities[0].setPic(name[position]);
                messageEntities[0].setMsgstatus("new");
                messageEntities[0].setSendacc(account);
                Log.i("发送表情", account);
                messageEntities[0].setResiveacc(resiveacc);
                Date date = new Date();
                Timestamp t = new Timestamp(date.getTime());
                messageEntities[0].setSendtime(t);
                commonVo.setFlag("sendmsg");
                commonVo.setMessageEntities(messageEntities);
                inittimer = new Inittimer(handler);
                inittimer.commonVologin=commonVo;
                inittimer.timer.schedule(inittimer.task,2);
                emoj.setVisibility(View.GONE);
                sendlist.setSelection(200);
            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        back.setOnClickListener(this);
        button.setOnClickListener(this);
        iconbtn.setOnClickListener(this);
        sendtext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){//失去焦点，关闭输入法
                    InputMethodManager manger= (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    manger.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        if(inittimer!=null){
            inittimer.cancle();
            inittimer=null;
        }
        if(inittimer1!=null){
            inittimer1.cancle();
            inittimer1=null;
        }
        if(inittimer2!=null){
            inittimer2.cancle();
            inittimer2=null;
        }

    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.button:
                //发送功能
                sendtext.clearFocus();
                if(sendtext.getText().toString().length()>0){
                    CommonVo commonVo = new CommonVo();
                    MessageEntity[] messageEntities = new MessageEntity[1];
                    messageEntities[0]=new MessageEntity();
                    messageEntities[0].setMessage(sendtext.getText().toString());
                    messageEntities[0].setMsgstatus("new");
                    messageEntities[0].setSendacc(account);
                    messageEntities[0].setResiveacc(resiveacc);
                    Date date = new Date();
                    Timestamp t = new Timestamp(date.getTime());
                    messageEntities[0].setSendtime(t);
                    commonVo.setFlag("sendmsg");
                    commonVo.setMessageEntities(messageEntities);
                    positon=sendlist.getFirstVisiblePosition();
                    inittimer = new Inittimer(handler);
                    inittimer.commonVologin=commonVo;
                    inittimer.timer.schedule(inittimer.task,1);
                    Toast.makeText(this,"正在发送",Toast.LENGTH_SHORT).show();
                    sendtext.setText("");
                }else{
                    Toast.makeText(this,"不能发送空消息",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iconbtn:
                if(!flags){
                    emoj.setVisibility(View.VISIBLE);
                    flags=true;
                }else{
                    emoj.setVisibility(View.GONE);
                    flags=false;
                }
                break;
        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            conn=mainUI.mainui.conn;
            switch (msg.what){
                case 1:
                    if(conn)
                    sendmsg((CommonVo)msg.obj);
                    break;
                case 2:
                    if(conn)
                    getmsg((CommonVo)msg.obj);
                    break;
                case 3:
                    conncheck();
                    break;
            }
        }
        void sendmsg(final CommonVo commonVo){

            Thread thread = new Thread(){
                @Override
                public void run() {
                    sendflag= new Dispose().chang(commonVo);
                }
            };
            thread.start();
            if(sendflag==null){
                sendflag="error";
            }
            while (sendflag.equals(""));
            Toast.makeText(ChatUI.this,sendflag,Toast.LENGTH_SHORT).show();
            sendflag="";
        }
        void getmsg(final CommonVo commonVo){

            Thread thread = new Thread(){
                @Override
                public void run() {
                    commonResult1 = new Dispose().mainConnect(commonVo);
                    flag = commonResult1.getFlag();
                }
            };
            thread.start();
            if(flag==null){
                flag="error";
            }
            if(commonResult1!=null){
            if(commonResult1.getMessageEntities()!=null){
                if(commonResult1.getMessageEntities()[0]!=null){
                    if(commonResult1.getMessageEntities()[0].getMessageID()!=chatID){
                        new Listinit(commonResult1).init();//消息有变动，刷新页面

                        //数据刷新后设置listview 项目位置
                        if(commonResult1.getMessageEntities()[0].getMessageID()>chatID&&commonResult1.getMessageEntities()[0].getResiveacc().equals(account)){
                            Intent intent = new Intent(ChatUI.this, Mymuservier.class);
                            intent.putExtra("type",1);
                            startService(intent);
                        }
                        View v = sendlist.getChildAt(0);
                        sendlist.setSelectionFromTop(200,v==null?0:v.getTop());//有新消息则翻到最底位置
                        chatID=commonResult1.getMessageEntities()[0].getMessageID();//更新chatID
                        flag="";
                    }
                }
            }
        }
        }
        int count = 0;
        void conncheck(){
            conn=mainUI.mainui.conn;
            if(!conn){
                if(count==6){//超时提示
                    conns.setVisibility(View.VISIBLE);
                    count=0;
                }
                count++;
            }
            else {
                if(count==6){
                    conn=false;
                    count=0;
                }
                count++;
                conns.setVisibility(View.GONE);

            }
        }
    };
    class  Listinit{
        private  CommonResult commonResult;
        public Listinit(CommonResult commonResult){
              this.commonResult = commonResult;
        }



        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String,Object> map ;
        public void init(){
            int count=0;
            for(int i=0;i<commonResult.getMessageEntities().length;i++)
                if(commonResult.getMessageEntities()!=null){
                    if(commonResult.getMessageEntities()[i]!=null)
                        if(commonResult.getMessageEntities()[i].getSendacc()!=null)
                            count++;
                }
            for(int i=count;i>=0&&commonResult.getMessageEntities()!=null;i--)
            {
                if(commonResult.getMessageEntities()[i]!=null&&commonResult.getMessageEntities()[i].getSendacc()!=null){
                    map=new HashMap<String, Object>();
                    if(commonResult.getMessageEntities()[i].getSendacc().equals(account)){
                        for(int j = 0;j<name1.length;j++)
                        {
                            if(headshot.equals(name1[j]))
                                map.put("sheadshot",pic1[j]);
                        }
                        if(commonResult.getMessageEntities()[i].getMessage()!=null){
                            map.put("sendmsg",commonResult.getMessageEntities()[i].getMessage());
                            Log.i("发送信息", "init: ");
                            map.put("Action","sendmsg");
                            list.add(map);
                        }else{
                            if(commonResult.getMessageEntities()[i].getPic()!=null){
                                for(int j = 0;j<name.length;j++){
                                    if(commonResult.getMessageEntities()[i].getPic().equals(name[j])){
                                        map.put("sendpic",pic2[j]);
                                        Log.i("发送图片", "init: ");
                                        map.put("Action","sendpic");
                                        list.add(map);
                                    }
                                }
                            }
                        }

                    }else if(commonResult.getMessageEntities()[i].getResiveacc().equals(account)){
                        for (int j = 0; j < name1.length; j++) {
                            if (commonResult.getMessageEntities()[i].getHeadshot().equals(name1[j])) {
                                map.put("rheadshot", pic1[j]);
                            }
                        }
                        if (commonResult.getMessageEntities()[i].getMessage()!= null) {
                            map.put("resivemsg", commonResult.getMessageEntities()[i].getMessage());
                            Log.i("接收信息", "init: ");
                            map.put("Action", "resivemsg");
                            list.add(map);
                        } else {
                            if (commonResult.getMessageEntities()[i].getPic() != null) {
                                for (int j = 0; j < name.length; j++) {
                                    if (commonResult.getMessageEntities()[i].getPic().equals(name[j])) {
                                        map.put("resivepic", pic2[j]);
                                        Log.i("接收图片", "init: ");
                                        map.put("Action", "resivepic");
                                        list.add(map);
                                    }
                                }
                            }
                        }
                    }

                }
            }
          //  Log.i("聊天适配器", commonResult.getMessageEntities()[0].getNickname());
            SendlistAdapter sendlistAdapter = new SendlistAdapter(ChatUI.this,R.layout.chatlist,list);
            sendlist.setAdapter(sendlistAdapter);
        }

    }
}
