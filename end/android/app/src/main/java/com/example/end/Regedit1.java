package com.example.end;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Regedit1 extends AppCompatActivity implements View.OnClickListener {
    private ImageButton icon;
    private Button finish;
    private ImageView headshot;
    private TextView nickname;
    private TextView conns;
    private String account="";
    private String headshotid="";
    private String sex="";
    private String flag="";
    private boolean conn=false;
    private Inittimer inittimer=null;
    private Inittimer inittimer1=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regedit1);
        icon=findViewById(R.id.icon);
        finish=findViewById(R.id.finish);
        headshot = findViewById(R.id.headshot);
        nickname = findViewById(R.id.nickname);
        conns = findViewById(R.id.conn);
        conns.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String[] name = {
                "咕噜","咕噜01","咕噜02","咕噜03","咕噜04","咕噜05","咕噜06","咕噜07","咕噜08","咕噜09","咕噜10","咕噜11"
        };
        int [] pic = {R.drawable.gulu_end,R.drawable.gulu01,R.drawable.gulu02,R.drawable.gulu03,R.drawable.gulu04,R.drawable.gulu05,
                R.drawable.gulu06,R.drawable.gulu07,R.drawable.gulu08,R.drawable.gulu09,R.drawable.gulu10,R.drawable.gulu11};
        if(intent!=null){
                     headshotid=intent.getStringExtra("headshot");
        for(int i=0;i<name.length;i++)
        {
            if(headshotid!=null)
            if(headshotid.equals(name[i])){
                headshot.setImageResource(pic[i]);
            }
        }
        if(intent.getStringExtra("sex")!=null){
            if(intent.getStringExtra("sex").equals("男")) {
                headshot.setImageResource(R.drawable.headshot_man);
                sex = intent.getStringExtra("sex");
            }
            if(intent.getStringExtra("sex").equals("女")){
                sex=intent.getStringExtra("sex");
                headshot.setImageResource(R.drawable.headshot_woman);
            }
        }

            if(intent.getStringExtra("account")!=null)
                account=intent.getStringExtra("account");
        }
        inittimer1 = new Inittimer(handler);
        inittimer1.timer.schedule(inittimer1.task1,1,3*1000);//10秒后调用handler判断网络是否连接成功
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
        finish.setOnClickListener(this);
        headshot.setOnClickListener(this);
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
                    if(conn)
                        change((CommonVo) msg.obj);
                    break;
                case 2:
                    conncheck();
                    break;
            }
        }
        void change(final CommonVo commonVo){

            Thread thread =  new Thread(){
                @Override
                public void run() {
                    //修改nickname 与headshot
                    Log.i("修改", commonVo.getUserEntities()[0].getAccount());
                    flag= new Dispose().chang(commonVo);
                    if(flag==null){
                        flag="";
                    }

                }
            };
            thread.start();
            if(flag==null){
                flag="error";
            }
            while (flag.equals(""));
            if(flag.equals("修改成功")){
                Toast.makeText(Regedit1.this,flag,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Regedit1.this,MainActivity.class);
                intent.putExtra("account",account);
                startActivity(intent);
                MainActivity.mainActivity.finish();
                finish();
                flag="";
            }
            else {
                Toast.makeText(Regedit1.this,flag,Toast.LENGTH_SHORT).show();
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
                intent = new Intent(this,Regedit.class);
                startActivity(intent);
                finish();
                break;
            case R.id.finish:
                String nicknames= new InputCheck().nicknameCheck(nickname.getText().toString());
                if(nicknames.equals("昵称")){
                    UserEntity[] userEntities = new UserEntity[1];
                    userEntities[0] = new UserEntity();
                    userEntities[0].setNickname(nickname.getText().toString());
                    if(headshotid==null){
                        headshotid="";
                    }
                    if(headshotid.equals("")){
                        if(sex.equals("男")){
                           userEntities[0].setHeadshot("咕噜04");
                        }
                        if(sex.equals("女")){
                            userEntities[0].setHeadshot("咕噜07");
                        }
                    }else
                        userEntities[0].setHeadshot(headshotid);
                    userEntities[0].setAccount(account);
                    CommonVo commonVo = new CommonVo();
                    commonVo.setUserEntities(userEntities);
                    commonVo.setFlag("nickname");
                    inittimer = new Inittimer(handler);
                    inittimer.commonVologin=commonVo;
                    inittimer.timer.schedule(inittimer.task,1);
                    Toast.makeText(this,"正在连接！！请稍后",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,nicknames,Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.headshot:
                intent = new Intent(this,Changeheadshot.class);
                intent.putExtra("account",account);
                Log.i("跳转", account);
                startActivity(intent);
                finish();
                break;
        }

    }
}
