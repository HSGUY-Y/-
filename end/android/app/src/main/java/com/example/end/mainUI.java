package com.example.end;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.viewpager.widget.ViewPager;

import com.dispose.Dispose;
import com.entity.AddmsgEntity;
import com.entity.CommonResult;
import com.entity.CommonVo;
import com.entity.ContactEntity;
import com.entity.MessageEntity;
import com.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import servier.Mymuservier;
import util.FragmentAdater;
import util.FragmentChat;
import util.FragmentContact;
import util.FragmentPerson;


public class mainUI extends AppCompatActivity implements View.OnClickListener {
    private ImageView chat;
    private ImageView contact;
    private ImageView person;
    private ViewPager viewPager;
    private TextView Default;
    private ImageButton icon;
    private TextView messagesign;
    private TextView messagesign1;
    private String account;
    private TextView conns;
    public static mainUI mainui;
    private CommonResult commonResult;
    private Inittimer inittimer=null;
    private Inittimer inittimer1=null;
    private  int clum=0;
    public  int thischatpos=0;
    public int thiscontactpos=0;
    private  String page="聊天";
    private String flag="";//登录状态
    boolean conn=false;//网络状态
    public int countcc=0;
    public int countss=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainui=this;
        chat=findViewById(R.id.chat);
        contact=findViewById(R.id.contact);
        person=findViewById(R.id.person);
        Default=findViewById(R.id.deFault);
        icon=findViewById(R.id.icon);
        conns=findViewById(R.id.conn);
        viewPager=findViewById(R.id.viewpager);
        conns.setVisibility(View.GONE);
        messagesign=findViewById(R.id.messageSign);
        messagesign1=findViewById(R.id.messageSign1);
        messagesign1.setVisibility(View.GONE);
        messagesign.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        if(intent!=null){
            if(intent.getStringExtra("TickTalkID")!=null)
                account=intent.getStringExtra("TickTalkID");
        }
        Log.i("main", account);
        UserEntity[] userEntities = new UserEntity[1];
        userEntities[0]=new UserEntity();
        userEntities[0].setAccount(account);
        CommonVo commonVo = new CommonVo();
        commonVo.setUserEntities(userEntities);
        commonVo.setFlag("main");
        inittimer = new Inittimer(handler);
        inittimer.commonVologin = commonVo;
        inittimer.timer.schedule(inittimer.task,800,6*1000);
        inittimer1 = new Inittimer(handler);
        inittimer1.timer.schedule(inittimer1.task1,1,4*1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        chat.setOnClickListener(this);
        contact.setOnClickListener(this);
        person.setOnClickListener(this);
        Default.setOnClickListener(this);
        viewPager.setCurrentItem(clum);
        btnChange(clum);
        Default.setText(page);
        //添加滑动事件
        if(viewPager!=null)
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /*arg0 :当前页面，及你点击滑动的页面
                  arg1:当前页面偏移的百分比
                  arg2:当前页面偏移的像素位置*/
                //滑动时调用

            }

            @Override
            public void onPageSelected(int position) {
                //跳转完成调用
                switch (position){
                    case 0:
                        Default.setText("聊天");
                        btnChange(0);
                        break;
                    case 1:
                        Default.setText("联系人");
                        btnChange(1);
                        break;
                    case 2:
                        Default.setText("用户");
                        btnChange(2);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //state 1开始滑动，2滑动结束，0什么也没做


            }
        });
    }

    @Override
    protected void onRestart(){
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(inittimer!=null){
            inittimer.cancle();
            inittimer=null;
        }
        if(inittimer1!=null){
            inittimer1.cancle();
            inittimer1=null;
        }
    }

    @Override
    protected void onPause() {//活动切换时调用
        super.onPause();
        clum = viewPager.getCurrentItem();//记录切换前界面
        page = Default.getText().toString();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    if(conn)//如果网络连接有效，则开始登录，若没有此步骤，界面会卡死
                        change((CommonVo) msg.obj);
                    break;
                case 2:
                    conncheck((CommonVo) msg.obj);
                    break;
            }
            super.handleMessage(msg);
        }

        void change(final CommonVo commonVo) {
            Thread thread = new Thread(){//线程用于请求网络连接
                @Override
                public void run(){
                    commonResult = new Dispose().mainConnect(commonVo);
                    if(commonResult.getFlag()!=null)
                    flag=commonResult.getFlag();
                }
            };
            thread.start();
            if(flag==null||flag.equals(""))
                flag="error";
            if(!flag.equals("error")&&commonResult.getUserEntities()!=null){
            if(FragmentChat.fragmentChat!=null&&FragmentContact.fragmentContact!=null){
                thischatpos=FragmentChat.fragmentChat.chatlist.getFirstVisiblePosition();
                thiscontactpos=FragmentContact.fragmentContact.contactlist.getFirstVisiblePosition();
            }
            if(commonResult!=null&&!flag.equals("error")){
            UserEntity userEntity = new UserEntity();
            userEntity = commonResult.getUserEntities()[0];
            ContactEntity[] contactEntity = new ContactEntity[200];
            contactEntity = commonResult.getContactEntities();
            MessageEntity[] messageEntities = new MessageEntity[200];
            messageEntities = commonResult.getMessageEntities();
            AddmsgEntity[] addmsgEntities = new AddmsgEntity[200];
            addmsgEntities=commonResult.getAddmsgEntities();//保证数据不为空
            int countc=0;
                if (addmsgEntities[0] == null) {
                    addmsgEntities[0]=new AddmsgEntity();
                    addmsgEntities[0].setResiveacc(userEntity.getAccount());
                    addmsgEntities[0].setMsgID(0);
                }else{
                    if(addmsgEntities[0].getAddmsgstatus()!=null)
                    for(int i=0;i<addmsgEntities.length&&addmsgEntities[i]!=null;i++){
                        if(addmsgEntities[i].getAddmsgstatus().equals("new"))
                            countc++;
                    }
                }

         //   Log.i("main", commonResult.getContactEntities()[0].getFriendacc());
            MessageEntity[] messageEntities1 = new MessageEntity[200];
            for(int i =0,x=0;contactEntity[i]!=null;i++){//找到新消息
                for(int j=0;messageEntities[j]!=null;j++){
                    if(contactEntity[i]!=null){
                        if(contactEntity[i].getFriendacc().equals(messageEntities[j].getSendacc())||
                                contactEntity[i].getFriendacc().equals(messageEntities[j].getResiveacc())){
                            messageEntities1[x] = new MessageEntity();
                            messageEntities1[x]=messageEntities[j];
                            break;
                        }
                    }

                }
                x++;
            }
            int[] count = new  int[200];//新消息计数
            int counts=0;
            for(int i =0,x=0;contactEntity[i]!=null&&i<200;i++){
                count[x]=0;
                for(int j=0;messageEntities[j]!=null&&j<200;j++){
                    if(contactEntity[i]!=null){
                        if(contactEntity[i].getFriendacc().equals(messageEntities[j].getSendacc())){
                            if(messageEntities[j].getMsgstatus().equals("new")){
                                count[x]++;
                                counts++;
                            }
                        }
                    }
                }
                x++;
            }
            if(viewPager.getAdapter()!=null){
                clum = viewPager.getCurrentItem();
                page = Default.getText().toString();//记录更新前页面位置
                viewPager.removeAllViewsInLayout();
                viewPager.getAdapter().notifyDataSetChanged();
            }

            List<Fragment> list = new ArrayList<>();
            list.add(new FragmentChat(messageEntities,messageEntities1,count,account,userEntity.getHeadshot(),thischatpos));
            list.add(new FragmentContact(contactEntity,thiscontactpos,userEntity.getHeadshot(),addmsgEntities));
            list.add(new FragmentPerson(userEntity));
            FragmentAdater fragmentAdater = new FragmentAdater(getSupportFragmentManager(),list);
            viewPager.setAdapter(fragmentAdater);
            viewPager.setCurrentItem(clum);
            Default.setText(page);
            btnChange(clum);
            if(counts>0){
                messagesign.setVisibility(View.VISIBLE);
                messagesign.setText(String.valueOf(counts));
            }else{
                messagesign.setVisibility(View.GONE);
            }
            if(countc>0){
                messagesign1.setVisibility(View.VISIBLE);
                messagesign1.setText(String.valueOf(countc));
            }else{
                messagesign1.setVisibility(View.GONE);
            }
            if(counts>countss){
                countss=counts;
                //提示音
                Intent intent = new Intent(mainUI.this, Mymuservier.class);
                intent.putExtra("type",1);
                startService(intent);
            }
            if(countc>countcc){
                countcc=countc;
                //提示音
                Intent intent = new Intent(mainUI.this, Mymuservier.class);
                intent.putExtra("type",2);
                startService(intent);
            }
            }
            flag="";
        }
        }
        int count = 0;
        void conncheck(final  CommonVo commonVo){

            Thread thread = new Thread(){//此线程用于检查网络连接
                @Override
                public void run() {
                    conn=new Dispose().connect(commonVo);
                }
            };
            thread.start();
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


//底部三按钮的监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chat:
                btnChange(0);
                Default.setText("聊天");
                viewPager.setCurrentItem(0);
                break;
            case R.id.contact:
                btnChange(1);
                Default.setText("联系人");
                viewPager.setCurrentItem(1);
                break;
            case R.id.person:
                btnChange(2);
                Default.setText("用户");
                viewPager.setCurrentItem(2);
                break;
            case R.id.deFault:
                break;
            case R.id.icon:
                break;
        }
    }
    //按钮背景状态切换
    public void btnChange(int i){
        switch(i){
            case 0:
                chat.setImageResource(R.drawable.chat_change);
                contact.setImageResource(R.drawable.contact);
                person.setImageResource(R.drawable.person);
                break;
            case 1:
                chat.setImageResource(R.drawable.chat);
                contact.setImageResource(R.drawable.contact_change);
                person.setImageResource(R.drawable.person);
                break;
            case 2:
                chat.setImageResource(R.drawable.chat);
                contact.setImageResource(R.drawable.contact);
                person.setImageResource(R.drawable.person_change);
                break;
        }
    }

}
