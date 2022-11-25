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

public class NotfoundPS extends AppCompatActivity implements View.OnClickListener {
    public static NotfoundPS notfoundPS;
    private ImageButton icon;
    private EditText account;
    private Button next;
    private TextView conns;
    private Inittimer inittimer = null;
    private Inittimer inittimer1=null;
    private String flag="";//登录状态
    private boolean conn=false;//网络状态
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notfoundps);
        icon=findViewById(R.id.icon);
        next=findViewById(R.id.next);
        notfoundPS=this;
        conns=findViewById(R.id.conn);
        conns.setVisibility(View.GONE);
        account=findViewById(R.id.account);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        next.setOnClickListener(this);
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
                    flag=new Dispose().checkAccount(commonVo);
                }
            };
            thread.start();
            if(flag==null){
                flag="error";
            }
            while (flag.equals(""));
            if (flag .equals( "账号存在")) {
                Toast.makeText(NotfoundPS.this,flag,Toast.LENGTH_SHORT).show();
                Intent intent;
                intent = new Intent(NotfoundPS.this, NotfoundPS1.class);
                intent.putExtra("account", account.getText().toString().trim());
                startActivity(intent);
                finish();
                flag="";

            } else{
                Toast.makeText(NotfoundPS.this,flag,Toast.LENGTH_SHORT).show();
                flag="";
            }

        }
        int count = 0;
        void conncheck(){
            conn=MainActivity.mainActivity.conn;
            if(!conn){
                if(count==3){//超时提示
                    conns.setVisibility(View.VISIBLE);
                    count=0;
                }
                count++;
            }
            else {
                conns.setVisibility(View.GONE);
                conn=false;
                count=0;
            }
        }
    };

    @Override
    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.icon:
                finish();
                break;
            case R.id.next:
                InputCheck inputCheck = new InputCheck();
                String check = inputCheck.accountCheck(account.getText().toString());
                if(check.equals("TickTalkID")){
                    UserEntity[] userEntities = new UserEntity[1];
                    userEntities[0] = new UserEntity();
                    userEntities[0].setAccount(account.getText().toString());
                    final  CommonVo commonVo = new CommonVo(userEntities,"forgetps","");
                    inittimer = new Inittimer(handler);
                    inittimer.commonVologin = commonVo;
                    inittimer.timer.schedule(inittimer.task,1);
                    Toast.makeText(this,"正在连接！！！请稍后",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(this,"请输入正确的TickTalkID",Toast.LENGTH_SHORT).show();

                break;
        }
    }

}
