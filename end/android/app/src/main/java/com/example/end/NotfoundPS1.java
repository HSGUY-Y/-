package com.example.end;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dispose.Dispose;
import com.entity.CommonVo;
import com.entity.UserEntity;

import java.util.Timer;
import java.util.TimerTask;

import check.InputCheck;

public class NotfoundPS1 extends AppCompatActivity implements View.OnClickListener {
    public static NotfoundPS1 notfoundPS1;
    private ImageButton icon;
    private Button complaint;
    private TextView conns;
    private EditText phoneoremail;
    private Inittimer inittimer = null;
    private Inittimer inittimer1 =null;
    private String flag="";//登录状态
    private boolean conn=false;//网络状态
    private String account="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notfoundps1);
        icon=findViewById(R.id.icon);
        complaint=findViewById(R.id.complaint);
        conns = findViewById(R.id.conn);
        conns.setVisibility(View.GONE);
        phoneoremail = findViewById(R.id.phoneoremial);
        notfoundPS1=this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if(intent!=null){
            if(intent.getStringExtra("account")!=null)
                account=intent.getStringExtra("account");
        }
        inittimer1= new Inittimer(handler);
        inittimer1.timer.schedule(inittimer1.task1,1,3*1000);

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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        icon.setOnClickListener(this);
        complaint.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            conn=MainActivity.mainActivity.conn;
            switch (msg.what){
                case 1:
                    if(conn)//如果网络连接有效，则开始登录，若没有此步骤，界面会卡死
                        change((CommonVo) msg.obj);
                    break;
                case 2:
                    conncheck();
                    break;
            }
            super.handleMessage(msg);
        }

        void change(final CommonVo commonVo) {

            Thread thread = new Thread(){//线程用于请求网络连接
                @Override
                public void run(){
                    flag=new Dispose().checkPhoneOrEmail(commonVo);

                }
            };
            thread.start();
            if(flag==null)
                flag="error";
            while (flag.equals(""));
            if (flag .equals( "手机号或邮箱正确")) {
                Toast.makeText(NotfoundPS1.this,flag,Toast.LENGTH_SHORT).show();
                Intent intent;
                intent = new Intent(NotfoundPS1.this, NotfoundPS2.class);
                intent.putExtra("account", account);
                startActivity(intent);
                finish();
                flag="";

            } else{
                Toast.makeText(NotfoundPS1.this,flag,Toast.LENGTH_SHORT).show();
                flag="";
            }

        }
        int count = 0;
        void conncheck(){
            conn=MainActivity.mainActivity.conn;
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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.icon:
                intent = new Intent(this,NotfoundPS.class);
                startActivity(intent);
                finish();
                break;
            case R.id.complaint:
                InputCheck inputCheck = new InputCheck();
                String string = inputCheck.accountCheck(phoneoremail.getText().toString());
                if(string.equals("手机号")||string.equals("邮箱")){
                    UserEntity[] userEntities =new UserEntity[1];
                    userEntities[0]=new UserEntity();
                    userEntities[0].setAccount(account);
                    if(string.equals("手机号"))
                    userEntities[0].setPhone(phoneoremail.getText().toString());
                    else
                        userEntities[0].setEmail(phoneoremail.getText().toString());
                    final  CommonVo commonVo = new CommonVo(userEntities,"forgetps1","");
                    inittimer = new Inittimer(handler);
                    inittimer.commonVologin = commonVo;
                    inittimer.timer.schedule(inittimer.task,1);
                }else if(string.equals("TickTalkID")){
                    Toast.makeText(this,"请输入手机号或者邮箱",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,string,Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
