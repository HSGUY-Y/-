package com.example.end;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class ChangePS extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private Button sure;
    private TextView conns;
    private EditText password;
    private EditText repassword;
    private Inittimer inittimer = null;
    private Inittimer inittimer1 =null;
    private String flag="";//登录状态
    private boolean conn=false;//网络状态
    private String account="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        back=findViewById(R.id.back);
        conns=findViewById(R.id.conn);
        password=findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        sure=findViewById(R.id.sure);
        conns.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if(intent!=null){
            if(intent.getStringExtra("account")!=null){
                account=intent.getStringExtra("account");
            }
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
        back.setOnClickListener(this);
        sure.setOnClickListener(this);
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
                    flag=new Dispose().chang(commonVo);
                }
            };
            thread.start();
            if(flag==null){
                flag="error";
            }
            while(flag.equals(""));
            if (flag .equals( "修改成功")) {
                Intent intent = new Intent(ChangePS.this,MainActivity.class);
                intent.putExtra("account",account);
                startActivity(intent);
                MainActivity.mainActivity.finish();
                finish();
                flag="";

            } else{
                Toast.makeText(ChangePS.this,flag,Toast.LENGTH_SHORT).show();
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
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.sure:
                InputCheck inputCheck = new InputCheck();
                String string = inputCheck.passwordCheck(password.getText().toString());
                if(string.equals("密码"))
                {
                    if(password.getText().toString().equals(repassword.getText().toString())){
                        UserEntity[] userEntities = new UserEntity[1];
                        userEntities[0] = new UserEntity();
                        userEntities[0].setAccount(account);
                        userEntities[0].setPassword(password.getText().toString());
                        final CommonVo commonVo = new CommonVo(userEntities,"forgetps2","");
                        inittimer=new Inittimer(handler);
                        inittimer.commonVologin=commonVo;
                        inittimer.timer.schedule(inittimer.task,1);
                        Toast.makeText(this,"正在连接！！！请稍后",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this,"俩次输入密码不一致",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,string,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
