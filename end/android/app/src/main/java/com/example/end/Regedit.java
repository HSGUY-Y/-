package com.example.end;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dispose.Dispose;
import com.entity.CommonResult;
import com.entity.CommonVo;
import com.entity.UserEntity;
import com.socket.ClientSocket;

import java.util.Timer;
import java.util.TimerTask;

import check.InputCheck;

public class Regedit extends AppCompatActivity implements View.OnClickListener {
    public static Regedit regedits;
    private ImageButton icon;
    private Button regedit;
    private UserEntity userEntity = new UserEntity();
    private TextView phone;
    private TextView email;
    private RadioGroup sexGroup;
    private TextView password;
    private TextView repassword;
    private TextView inviteCode;
    private TextView conns;
    private String account;
    private String flag="";
    private boolean conn=false;
    private Inittimer inittimer =null;
    private Inittimer inittimer1 =null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regedit);
        icon=findViewById(R.id.icon);
        regedit=findViewById(R.id.regedit);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        sexGroup = findViewById(R.id.sexGroup);
        password =findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        inviteCode = findViewById(R.id.inviteCode);
        conns=findViewById(R.id.conn);
        conns.setVisibility(View.GONE);
        regedits=this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        inittimer1 = new Inittimer(handler);
        inittimer1.timer.schedule(inittimer1.task1,1,3*1000);//10????????????handler??????????????????????????????
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(inittimer!=null)
        {
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
        userEntity.setSex("");
        icon.setOnClickListener(this);
        regedit.setOnClickListener(this);
        sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                userEntity.setSex("");
                switch (checkedId){
                    case R.id.man:
                        userEntity.setSex("???");
                        break;
                    case R.id.woman:
                        userEntity.setSex("???");
                        break;
                }
            }
        });
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
                    //??????
                    CommonResult commonResult;
                    Log.i("handler", commonVo.getUserEntities()[0].getPhone());
                    commonResult= new Dispose().regedit(commonVo);
                    flag = commonResult.getFlag();
                    if(flag==null){
                        flag="";
                    }
                    if(flag.equals("????????????"))
                        account = commonResult.getUserEntities()[0].getAccount();
                }
            };
            thread.start();
            if(flag==null){
                flag="error";
            }
            while(flag.equals(""));
            if(flag.equals("????????????")){
                Toast.makeText(Regedit.this,flag,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Regedit.this,Regedit1.class);
                intent.putExtra("sex",userEntity.getSex());
                intent.putExtra("account",account);
                startActivity(intent);
                finish();
                flag="";
            }
            else {
                Toast.makeText(Regedit.this,flag,Toast.LENGTH_SHORT).show();
                flag="";
            }
        }
        int count = 0;
        void conncheck(){
            conn=MainActivity.mainActivity.conn;
            if(!conn){
                if(count==6){//????????????
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
            case R.id.icon:
                finish();
                break;
            case R.id.regedit:
                UserEntity[] userEntities = new UserEntity[1];
                InputCheck inputCheck = new InputCheck();
                userEntity.setAccount("1");
                userEntity.setPhone(phone.getText().toString());
                userEntity.setEmail(email.getText().toString());
                userEntity.setPassword(password.getText().toString());
                userEntities[0] = new UserEntity();
                userEntities[0]=userEntity;

                Log.i("regedit", userEntity.getPhone());
                final CommonVo commonVo = new CommonVo(userEntities,"regedit",inviteCode.getText().toString());
                //????????????
                boolean phonecheck = inputCheck.accountCheck(userEntity.getPhone()).equals("?????????");
                boolean emailcheck = inputCheck.accountCheck(userEntity.getEmail()).equals("??????");
                boolean passwordcheck = inputCheck.passwordCheck(userEntity.getPassword()).equals("??????");
                boolean reputcheck = userEntity.getPassword().equals(repassword.getText().toString());
                boolean sexcheck = userEntity.getSex().length()>0;
                boolean invitecheck = inputCheck.inviteCheck(inviteCode.getText().toString()).equals("?????????");
                if(passwordcheck&&phonecheck&&emailcheck&&reputcheck&&sexcheck&&invitecheck)
                {

                    inittimer = new Inittimer(handler);
                    inittimer.commonVologin = commonVo;
                    inittimer.timer.schedule(inittimer.task,1);
                    Toast.makeText(this,"???????????????????????????",Toast.LENGTH_LONG).show();
                }
                else if(!phonecheck)
                    Toast.makeText(this,inputCheck.accountCheck(userEntity.getPhone()),Toast.LENGTH_SHORT).show();
                else if(!emailcheck)
                    Toast.makeText(this,inputCheck.accountCheck(userEntity.getEmail()),Toast.LENGTH_SHORT).show();
                else if(!sexcheck)
                    Toast.makeText(this,"???????????????",Toast.LENGTH_SHORT).show();
                else if(!passwordcheck)
                    Toast.makeText(this,inputCheck.passwordCheck(userEntity.getPassword()),Toast.LENGTH_SHORT).show();
                else if(!reputcheck)
                    Toast.makeText(this,"???????????????????????????",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,inputCheck.inviteCheck(inviteCode.getText().toString()),Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
