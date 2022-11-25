package com.example.end;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.dispose.Dispose;

import java.util.Timer;
import java.util.TimerTask;

import com.entity.CommonResult;
import com.entity.CommonVo;
import com.entity.UserEntity;



import check.InputCheck;

import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText account;
    private EditText password;
    private TextView Default;
    private TextView notFoundPS;
    private TextView regedit;
    private Button   login;
    private ImageButton icon;
    private TextView conns;
    private Inittimer inittimer = null;
    private Inittimer inittimer1 = null;
    public  static MainActivity mainActivity;
    private String flag="";//登录状态
    boolean conn=false;//网络状态
    private String acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mainActivity=this;
        account=findViewById(R.id.account);
        password=findViewById(R.id.password);
        Default=findViewById(R.id.deFault);
        notFoundPS=findViewById(R.id.notFoundPS);
        regedit=findViewById(R.id.regedit);
        login=findViewById(R.id.login);
        icon=findViewById(R.id.icon);
        conns = findViewById(R.id.conn);
        conns.setVisibility(View.GONE);
        //设置监听事件

    }

    @Override
    protected void onStart() {
        super.onStart();
        //请求网络连接，检查网络是否通畅

        Intent intent = getIntent();
        if(intent!=null){
            String accounts= intent.getStringExtra("account");
            if(accounts!=null){
                account.setText(accounts);
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        login.setOnClickListener(this);
        notFoundPS.setOnClickListener(this);
        regedit.setOnClickListener(this);
        icon.setOnClickListener(this);
        Default.setOnClickListener(this);

        inittimer1 = new Inittimer(handler);
        inittimer1.timer.schedule(inittimer1.task1,1,3*1000);//10秒后调用handler判断网络是否连接成功

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(inittimer!=null) {
            inittimer.cancle();
            inittimer= null;
        }
        if(inittimer1!=null){
            inittimer1.cancle();
            inittimer1=null;
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
    //timer+handler 实现子线程对进程的切换
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
                    CommonResult commonResult = new CommonResult();
                    commonResult=new Dispose().login(commonVo);
                    flag = commonResult.getFlag();
                    if(commonResult.getUserEntities()!=null)
                    acc = commonResult.getUserEntities()[0].getAccount();

                }
            };
            thread.start();
            if(flag==null){
                flag="error";
            }
            while(flag.equals(""));
            if (flag .equals( "登录成功")) {
                Toast.makeText(MainActivity.this,flag,Toast.LENGTH_SHORT).show();
                Intent intent;
                intent = new Intent(MainActivity.this, mainUI.class);
                intent.putExtra("TickTalkID", acc);
                startActivity(intent);
                finish();
                flag="";
            } else{
                Toast.makeText(MainActivity.this,flag,Toast.LENGTH_SHORT).show();
                flag="";
            }

        }
        int count = 0;
        void conncheck(final CommonVo commonVo){

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


//各控件的响应事件
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.login:
                /*输入校验*/
                InputCheck inputCheck = new InputCheck();
                String accounts=account.getText().toString().trim();
                String passwords=password.getText().toString().trim();
                boolean accountflag = inputCheck.accountCheck(accounts).equals("手机号")||
                        inputCheck.accountCheck(accounts).equals("邮箱")||
                        inputCheck.accountCheck(accounts).equals("TickTalkID");
                boolean passwordflag =inputCheck.passwordCheck(passwords).equals("密码");
                if(accountflag&&passwordflag)
                {
                    //切换界面中间加一个界面 等待网络验证账号
                    UserEntity[] userEntities = new UserEntity[1];
                    userEntities[0]=new UserEntity();
                    userEntities[0].setAccount(accounts);
                    userEntities[0].setPassword(passwords);
                    final CommonVo commonVo = new CommonVo(userEntities,"login",inputCheck.accountCheck(accounts));
                    inittimer = new Inittimer(handler);
                    inittimer.commonVologin=commonVo;
                    inittimer.timer.schedule(inittimer.task,1);
                    Toast.makeText(this,"正在连接！！！请稍后",Toast.LENGTH_SHORT).show();
                }else if(accountflag)
                {//换成dialog
                    Toast.makeText(this,inputCheck.passwordCheck(password.getText().toString().trim()),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,inputCheck.accountCheck(password.getText().toString().trim()),Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.deFault:
                break;
            case R.id.icon:
                break;
            case R.id.notFoundPS:
                intent=new Intent(MainActivity.this,NotfoundPS.class);
                startActivity(intent);

                break;
            case R.id.regedit:
                intent = new Intent(MainActivity.this,Regedit.class);
                startActivity(intent);

                break;
        }

    }
}
