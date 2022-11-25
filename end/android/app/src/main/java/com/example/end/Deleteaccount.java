package com.example.end;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dispose.Dispose;

import com.entity.CommonVo;
import com.entity.UserEntity;

public class Deleteaccount extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private Button  sure;
    private EditText password;
    private TextView conns;
    private boolean conn=false;
    private String flag="";
    private Inittimer inittimer;
    private String account;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rankps);
        back=findViewById(R.id.back);
        sure = findViewById(R.id.sure);
        password= findViewById(R.id.password);
        conns = findViewById(R.id.conn);
        conns.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //
        Intent intent = getIntent();
        account =   intent.getStringExtra("account");
        inittimer= new Inittimer(handler);
        inittimer.timer.schedule(inittimer.task1,1,4*1000);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                Intent intent = new Intent(Deleteaccount.this,Set.class);
                intent.putExtra("account",account);
                startActivity(intent);
                finish();
                break;
            case R.id.sure:
                CommonVo commonVo = new CommonVo();
                UserEntity[] userEntities = new UserEntity[1];
                userEntities[0]=new UserEntity();
                userEntities[0].setAccount(account);
                userEntities[0].setPassword(password.getText().toString());
                commonVo.setFlag("deleteuser");
                commonVo.setUserEntities(userEntities);
                inittimer.commonVologin=commonVo;
                inittimer.timer.schedule(inittimer.task,1);
                Toast.makeText(Deleteaccount.this,"删除账号",Toast.LENGTH_SHORT).show();
                break;

        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    if(conn)//如果网络连接有效，则开始登录，若没有此步骤，界面会卡死
                        delete((CommonVo) msg.obj);
                    break;
                case 2:
                    conncheck((CommonVo) msg.obj);
                    break;
            }
            super.handleMessage(msg);
        }

        void delete(final CommonVo commonVo) {


            Thread thread = new Thread(){//线程用于请求网络连接
                @Override
                public void run(){
                    flag = new Dispose().chang(commonVo);
                }
            };
            thread.start();
            if(flag==null){
                flag="error";
            }
            while(flag.equals(""));
            if (flag .equals( "删除成功")) {
                Toast.makeText(Deleteaccount.this,flag,Toast.LENGTH_SHORT).show();
                Intent intent;
                intent = new Intent(Deleteaccount.this, MainActivity.class);
                startActivity(intent);
                finish();
                mainUI.mainui.finish();
                flag="";
            } else{
                Toast.makeText(Deleteaccount.this,flag,Toast.LENGTH_SHORT).show();
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
}
