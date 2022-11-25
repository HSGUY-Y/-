package com.example.end;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dispose.Dispose;
import com.entity.CommonResult;
import com.entity.CommonVo;
import com.entity.ContactEntity;
import com.entity.UserEntity;

import servier.Mymuservier;

public class User extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private TextView title;
    private ImageView headshot;
    private TextView nickname;
    private int headshots;
    private String nicknames;
    private String accounts;
    private String rsiveaccount;
    private String signs;
    private String headshotstr;
    private TextView account;
    private EditText remark;
    private LinearLayout chat;
    private LinearLayout deletePerson;
    private TextView sign;
    private Button sure;
    private String remarkstr;
    private Inittimer inittimer;
    private Inittimer inittimer1;
    private Inittimer inittimer2;
    private TextView conns;
    private String flag="";
    private String remarkflag="";
    private boolean conn=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_view);
        back=findViewById(R.id.back);
        title=findViewById(R.id.title);
        headshot=findViewById(R.id.headshot);
        nickname=findViewById(R.id.nickname);
        account=findViewById(R.id.account);
        remark=findViewById(R.id.Remark);
        sign = findViewById(R.id.sign);
        sure = findViewById(R.id.sure);
        chat=findViewById(R.id.chat);
        conns=findViewById(R.id.conn);
        conns.setVisibility(View.GONE);
        deletePerson=findViewById(R.id.deletePerson);
        sure.setVisibility(View.GONE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //请求网络连接
        Intent intent = getIntent();
        nicknames= intent.getStringExtra("nickname");
        headshots=intent.getIntExtra("headshot",0);

        rsiveaccount=intent.getStringExtra("raccount");//好友id

        signs = intent.getStringExtra("sign");
        headshotstr=intent.getStringExtra("headshotstr");

        accounts=intent.getStringExtra("account");//用户id

        remarkstr=intent.getStringExtra("remark");
        title.setText(nicknames);
        nickname.setText(nicknames);
        headshot.setImageResource(headshots);
        account.setText(rsiveaccount);
        remark.setText(remarkstr);
        sign.setText(signs);
        inittimer2=new Inittimer(handler);
        inittimer2.timer.schedule(inittimer2.task2,1,4*1000);
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
        if(inittimer2!=null){
            inittimer2.cancle();
            inittimer2=null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        back.setOnClickListener(this);
        chat.setOnClickListener(this);
        deletePerson.setOnClickListener(this);
        remark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            boolean flag=false;
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    sure.setVisibility(View.VISIBLE);
                    Toast.makeText(User.this,"修改备注",Toast.LENGTH_SHORT).show();
                    flag=true;
                }
                if(!hasFocus){
                    InputMethodManager manager = ((InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                    manager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                }
        });
        sure.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chat:
                Intent intent = new Intent(this,ChatUI.class);
                CommonResult commonResult = new CommonResult();
                UserEntity[] userEntities = new UserEntity[1];
                userEntities[0]=new UserEntity();
                userEntities[0].setHeadshot(headshotstr);//用户头像
                userEntities[0].setNickname(nicknames);//好友昵称
                userEntities[0].setAccount(accounts);//用户ID
                ContactEntity[] contactEntities = new ContactEntity[1];
                contactEntities[0]=new ContactEntity();
                contactEntities[0].setFriendacc(rsiveaccount);//好友ID
                commonResult.setContactEntities(contactEntities);
                commonResult.setUserEntities(userEntities);
                Bundle bundle = new Bundle();
                bundle.putSerializable("message",commonResult);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
            case R.id.deletePerson:
                CommonVo commonVo1 = new CommonVo();
                ContactEntity[] contactEntities2 = new ContactEntity[1];
                contactEntities2[0]=new ContactEntity();
                contactEntities2[0].setFriendacc(rsiveaccount);
                contactEntities2[0].setMainacc(accounts);
                commonVo1.setFlag("deletecontact");
                commonVo1.setContactEntities(contactEntities2);
                inittimer1 = new Inittimer(handler);
                inittimer1.commonVoconn=commonVo1;
                Log.i("delete", commonVo1.getFlag());
                inittimer1.timer.schedule(inittimer1.task1,1);

                Toast.makeText(this,"正在连接",Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
               finish();
                break;
            case R.id.sure:
                remark.clearFocus();
                if(remark.getText().toString().length()>0){
                    if(!remark.getText().toString().equals(remarkstr)){
                        CommonVo commonVo = new CommonVo();
                        ContactEntity[] contactEntities1 = new ContactEntity[1];
                        contactEntities1[0]=new ContactEntity();
                        contactEntities1[0].setMainacc(accounts);
                        contactEntities1[0].setFriendacc(rsiveaccount);//好友ID
                        contactEntities1[0].setRemark(remark.getText().toString());
                        commonVo.setFlag("changeremark");
                        commonVo.setContactEntities(contactEntities1);
                        inittimer=new Inittimer(handler);
                        inittimer.commonVologin=commonVo;
                        inittimer.timer.schedule(inittimer.task,1);
                        Toast.makeText(User.this,"正在修改",Toast.LENGTH_SHORT).show();
                        sure.setVisibility(View.GONE);
                    }else{
                        Toast.makeText(User.this,"与原备注相同",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(User.this,"备注不为空",Toast.LENGTH_SHORT).show();
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
                        changeremark((CommonVo)msg.obj);
                    break;
                case 2:
                    if(conn)
                        deleteperson((CommonVo)msg.obj);
                    break;
                case 3:
                    conncheck();
                    break;
            }
        }
        void changeremark(final CommonVo commonVo){

            Thread thread = new Thread(){
                @Override
                public void run() {
                    remarkflag= new Dispose().chang(commonVo);
                }
            };
            thread.start();
            if(remarkflag==null){
                remarkflag="error";
            }
            while(remarkflag.equals(""));
            if(remarkflag.equals("修改成功")){
                remark.setText(commonVo.getContactEntities()[0].getRemark());
            }else
                Toast.makeText(User.this,remarkflag,Toast.LENGTH_SHORT);
            remarkflag="";
        }
        void deleteperson(final CommonVo commonVo){

            Thread thread = new Thread(){
                @Override
                public void run() {

                    flag = new Dispose().chang(commonVo);
                }
            };
            thread.start();
            if(flag==null){
                flag="error";
            }
            while (flag.equals(""));
            if(flag.equals("删除成功")){
                finish();
                Toast.makeText(mainUI.mainui,flag,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(User.this,flag,Toast.LENGTH_SHORT).show();
            }
            flag="";
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
}

