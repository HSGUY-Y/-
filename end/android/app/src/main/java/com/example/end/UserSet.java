package com.example.end;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.entity.CommonVo;
import com.entity.UserEntity;

public class UserSet extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private LinearLayout changehs;
    private ImageView headshot;
    private EditText nickname;
    private EditText sign;
    private TextView phone;
    private TextView email;
    private LinearLayout changeps;
    private UserEntity userEntity;
    private Button sure;
    private Button sure1;
    private TextView conns;
    private boolean conn=false;
    private String nickflag="";
    private String signflag="";
    private Inittimer inittimer;
    private Inittimer inittimer1;

    String[] name = {
            "icon" , "咕噜","咕噜01","咕噜02","咕噜03","咕噜04","咕噜05","咕噜06","咕噜07","咕噜08","咕噜09","咕噜10","咕噜11"
    };
    int [] pic = {R.drawable.imagebtn,R.drawable.gulu_end,R.drawable.gulu01,R.drawable.gulu02,R.drawable.gulu03,R.drawable.gulu04,R.drawable.gulu05,
            R.drawable.gulu06,R.drawable.gulu07,R.drawable.gulu08,R.drawable.gulu09,R.drawable.gulu10,R.drawable.gulu11};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_set);
        back=findViewById(R.id.back);
        headshot=findViewById(R.id.headshot);
        changehs=findViewById(R.id.changehs);
        nickname=findViewById(R.id.nickname);
        sign=findViewById(R.id.sign);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        changeps=findViewById(R.id.changePS);
        sure=findViewById(R.id.sure);
        sure1=findViewById(R.id.sure1);
        conns=findViewById(R.id.conn);
        conns.setVisibility(View.GONE);
        sure.setVisibility(View.GONE);
        sure1.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle bundle;
        bundle = intent.getExtras();
        userEntity=(UserEntity) bundle.getSerializable("user");
        for(int i=0;i<name.length;i++){
            if(userEntity.getHeadshot().equals(name[i]))
                headshot.setImageResource(pic[i]);
        }
        nickname.setText(userEntity.getNickname());
        sign.setText(userEntity.getSign());
        phone.setText(userEntity.getPhone());
        email.setText(userEntity.getEmail());
        inittimer = new Inittimer(handler);
        inittimer.timer.schedule(inittimer.task2,1,4*1000);
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
        sure1.setOnClickListener(this);
        changeps.setOnClickListener(this);
        changehs.setOnClickListener(this);
        nickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    sure.setVisibility(View.VISIBLE);
                }
                if(!hasFocus){//失去焦点，关闭输入法
                    sure.setVisibility(View.GONE);
                    InputMethodManager manger= (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    manger.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        sign.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    sure1.setVisibility(View.VISIBLE);
                }
                if(!hasFocus){//失去焦点，关闭输入法
                    sure1.setVisibility(View.GONE);
                    InputMethodManager manger= (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    manger.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.sure:
                nickname.clearFocus();
                if(nickname.getText().toString().length()>0){
                    if(!nickname.getText().toString().equals(userEntity.getNickname())){
                        CommonVo commonVo = new CommonVo();
                        UserEntity[] userEntities = new UserEntity[1];
                        userEntities[0]=new UserEntity();
                        userEntities[0].setAccount(userEntity.getAccount());
                        userEntities[0].setNickname(nickname.getText().toString());
                        commonVo.setFlag("changenickname");
                        commonVo.setUserEntities(userEntities);
                        inittimer.commonVologin=commonVo;
                        inittimer.timer.schedule(inittimer.task,1);


                        Toast.makeText(UserSet.this,"正在修改",Toast.LENGTH_SHORT).show();
                        sure.setVisibility(View.GONE);
                    }else{
                        Toast.makeText(UserSet.this,"与原昵称相同",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(UserSet.this,"昵称不为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sure1:
                sign.clearFocus();
                if(sign.getText().toString().length()>0){
                    if(!sign.getText().toString().equals(userEntity.getSign())){
                        CommonVo commonVo = new CommonVo();
                        UserEntity[] userEntities = new UserEntity[1];
                        userEntities[0]=new UserEntity();
                        userEntities[0].setAccount(userEntity.getAccount());
                        userEntities[0].setSign(sign.getText().toString());
                        commonVo.setFlag("changesign");
                        commonVo.setUserEntities(userEntities);
                        inittimer1 = new Inittimer(handler);
                        inittimer1.commonVoconn=commonVo;
                        inittimer1.timer.schedule(inittimer1.task1,1);
                        Toast.makeText(UserSet.this,"正在修改",Toast.LENGTH_SHORT).show();
                        sure.setVisibility(View.GONE);
                    }else{
                        Toast.makeText(UserSet.this,"与原签名相同",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(UserSet.this,"签名不为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.changehs:
                Intent intent = new Intent(UserSet.this,mainChangehs.class);
                intent.putExtra("account",userEntity.getAccount());
                startActivity(intent);
                break;
            case R.id.changePS:
                Intent intent1 = new Intent(UserSet.this,CheckPS.class);
                intent1.putExtra("account",userEntity.getAccount());
                startActivity(intent1);
                break;
        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    if(conn)//如果网络连接有效，则开始登录，若没有此步骤，界面会卡死
                        changenickname((CommonVo) msg.obj);
                    break;
                case 2:
                    if(conn)
                        changesing((CommonVo)msg.obj);
                    break;
                case 3:
                    conncheck((CommonVo) msg.obj);
                    break;
            }
            super.handleMessage(msg);
        }

        void changenickname(final CommonVo commonVo) {


            Thread thread = new Thread(){//线程用于请求网络连接
                @Override
                public void run(){
                    nickflag = new Dispose().chang(commonVo);
                }
            };
            thread.start();
            if(nickflag==null){
                nickflag="error";
            }
            while(nickflag.equals(""));
            if (nickflag .equals( "修改成功")) {
                nickname.setText(commonVo.getUserEntities()[0].getNickname());
                nickflag="";
            } else{
                Toast.makeText(UserSet.this,nickflag,Toast.LENGTH_SHORT).show();
                nickflag="";
            }

        }
        void changesing(final CommonVo commonVo){
            Thread thread = new Thread(){
                @Override
                public void run() {
                     signflag=new Dispose().chang(commonVo);
                }
            };
            thread.start();
            if(signflag==null){
                signflag="error";
            }
            while (signflag.equals(""));
            if (signflag .equals( "修改成功")) {
                sign.setText(commonVo.getUserEntities()[0].getSign());
                signflag="";
            } else{
                Toast.makeText(UserSet.this,signflag,Toast.LENGTH_SHORT).show();
                signflag="";
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
