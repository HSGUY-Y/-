package com.example.end;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dispose.Dispose;
import com.entity.AddmsgEntity;
import com.entity.CommonVo;
import com.entity.UserEntity;

import java.sql.Timestamp;
import java.util.Date;

public class Adduserview extends AppCompatActivity implements View.OnClickListener {
    private ImageView headshot;
    private TextView nickname;
    private TextView account;
    private TextView sign;
    private TextView title;
    private ImageView back;
    private LinearLayout addperson;
    private UserEntity userEntity;//好友信息
    private String raccount;//用户ID
    private String flag="";
    private Boolean conn=false;
    private TextView conns;
    private Inittimer inittimer = null;
    private Inittimer inittimer1=null;
    String[] name = {
            "icon" , "咕噜","咕噜01","咕噜02","咕噜03","咕噜04","咕噜05","咕噜06","咕噜07","咕噜08","咕噜09","咕噜10","咕噜11"
    };
    int [] pic = {R.drawable.imagebtn,R.drawable.gulu_end,R.drawable.gulu01,R.drawable.gulu02,R.drawable.gulu03,R.drawable.gulu04,R.drawable.gulu05,
            R.drawable.gulu06,R.drawable.gulu07,R.drawable.gulu08,R.drawable.gulu09,R.drawable.gulu10,R.drawable.gulu11};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adduserview);
        headshot=findViewById(R.id.headshot);
        nickname=findViewById(R.id.nickname);
        account=findViewById(R.id.account);
        sign = findViewById(R.id.sign);
        back=findViewById(R.id.back);
        addperson=findViewById(R.id.addPerson);
        conns=findViewById(R.id.conn);
        conns.setVisibility(View.GONE);
        title=findViewById(R.id.title);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle bundle =intent.getExtras();
        userEntity=(UserEntity) bundle.getSerializable("user");
        raccount=intent.getStringExtra("raccount");
        for(int i=0;i<name.length;i++){
            if(userEntity.getHeadshot().equals(name[i]))
                headshot.setImageResource(pic[i]);
        }
        title.setText(userEntity.getNickname());
        nickname.setText(userEntity.getNickname());
        account.setText(userEntity.getAccount());
        sign.setText(userEntity.getSign());
        inittimer1 = new Inittimer(handler);
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
        addperson.setOnClickListener(this);
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
                    flag= new Dispose().chang(commonVo);


                }
            };
            thread.start();
            if(flag==null){
                flag="error";
            }
            while (flag.equals(""));
            if(flag.equals("发送成功")){
                Toast.makeText(Adduserview.this,flag,Toast.LENGTH_SHORT).show();
                finish();
                flag="";
            } else {
                Toast.makeText(Adduserview.this,flag,Toast.LENGTH_SHORT).show();
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
            case R.id.addPerson:
                if(raccount.equals(userEntity.getAccount())){
                    Toast.makeText(this,"这是本人账号！",Toast.LENGTH_SHORT).show();
                }else{
                CommonVo commonVo = new CommonVo();
                AddmsgEntity[] addmsgEntities = new AddmsgEntity[1];
                addmsgEntities[0]=new AddmsgEntity();
                addmsgEntities[0].setSendacc(raccount);
                addmsgEntities[0].setResiveacc(userEntity.getAccount());
                    Log.i("好友", userEntity.getAccount());
                addmsgEntities[0].setMsg("请求加为好友");
                addmsgEntities[0].setAddmsgstatus("new");
                addmsgEntities[0].setSendtime(new Timestamp(new Date().getTime()));
                commonVo.setAddmsgEntities(addmsgEntities);
                commonVo.setFlag("addusermsg");
                inittimer=new Inittimer(handler);
                inittimer.commonVologin=commonVo;
                inittimer.timer.schedule(inittimer.task,1);
                Toast.makeText(this,"正在处理",Toast.LENGTH_SHORT).show();
                break;
        }
        }
    }
}
