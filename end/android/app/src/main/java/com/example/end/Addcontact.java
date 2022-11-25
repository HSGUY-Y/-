package com.example.end;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dispose.Dispose;
import com.entity.AddmsgEntity;
import com.entity.CommonResult;
import com.entity.CommonVo;
import com.entity.UserEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import check.InputCheck;
import util.AddmsglistAdapter;
import util.ConlistAdapter;

public class Addcontact extends AppCompatActivity {
    private EditText searchtext;
    private ImageView search;
    private LinearLayout adduserlist;
    private ImageView headshot;
    private TextView nickname;
    private TextView account;
    private ListView addmsglist;
    private UserEntity userEntity;
    private AddmsgEntity[] addmsgEntities;
    private Inittimer inittimer =null;
    private Inittimer inittimer1=null;
    private Inittimer inittimer2=null;
    private ImageView back;
    private boolean conn = false;
    private String flag="";
    private TextView conns;
    private CommonResult commonResult;
    private String msgflag="";
    private int msgID=0;
    public static Addcontact addcontact;
    String[] name = {
            "icon" , "咕噜","咕噜01","咕噜02","咕噜03","咕噜04","咕噜05","咕噜06","咕噜07","咕噜08","咕噜09","咕噜10","咕噜11"
    };
    int [] pic = {R.drawable.imagebtn,R.drawable.gulu_end,R.drawable.gulu01,R.drawable.gulu02,R.drawable.gulu03,R.drawable.gulu04,R.drawable.gulu05,
            R.drawable.gulu06,R.drawable.gulu07,R.drawable.gulu08,R.drawable.gulu09,R.drawable.gulu10,R.drawable.gulu11};



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontact);
        searchtext =findViewById(R.id.searchtext);
        search = findViewById(R.id.search);
        adduserlist=findViewById(R.id.user);
        addmsglist=findViewById(R.id.addmsglist);
        conns=findViewById(R.id.conn);
        back=findViewById(R.id.back);
        headshot=findViewById(R.id.headshot);
        nickname=findViewById(R.id.nickname);
        account=findViewById(R.id.account);
        adduserlist.setVisibility(View.GONE);
        conns.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addcontact=this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle=intent.getExtras();
        addmsgEntities=(AddmsgEntity[]) bundle.getSerializable("addmsg");
        new Initaddmsg(addmsgEntities).init();
        if(addmsgEntities!=null){
            if(addmsgEntities[0]!=null){
                if(addmsgEntities[0].getMsgID()>0){
                    msgID=addmsgEntities[0].getMsgID();
                }
            }
        }
        CommonVo commonVo = new CommonVo();
        commonVo.setFlag("getaddmsg");
        UserEntity[] userEntities = new UserEntity[1];
        userEntities[0]=new UserEntity();
        userEntities[0].setAccount(addmsgEntities[0].getResiveacc());
        commonVo.setUserEntities(userEntities);
        //请求网络访问消息数据；
        inittimer1 = new Inittimer(handler);
        inittimer1.commonVoconn=commonVo;
        inittimer1.timer.schedule(inittimer1.task1,1,4*1000);
        inittimer2 = new Inittimer(handler);
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
        searchtext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    searchtext.setText("");
                }
                if(!hasFocus){//失去焦点，关闭输入法
                    InputMethodManager manger= (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    manger.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchtext.clearFocus();
                InputCheck inputCheck = new InputCheck();
                String string = inputCheck.accountCheck(searchtext.getText().toString());
                if(string.equals("手机号")||string.equals("邮箱")||string.equals("TickTalkID")){
                    CommonVo commonVo =new CommonVo();
                    UserEntity[] userEntities = new UserEntity[1];
                    userEntities[0]=new UserEntity();
                    userEntities[0].setAccount(searchtext.getText().toString());
                    commonVo.setFlag("getuser");
                    commonVo.setUserEntities(userEntities);
                    commonVo.setStatus(string);
                    //请求网络后，显示adduserlist
                    inittimer = new Inittimer(handler);
                    inittimer.commonVologin=commonVo;
                    inittimer.timer.schedule(inittimer.task,1);
                    searchtext.setText("搜索");


                } else{
                    Toast.makeText(Addcontact.this,string,Toast.LENGTH_SHORT).show();
                }
            }
        });
        adduserlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Addcontact.this,Adduserview.class);
                Bundle bundle = new Bundle();
                Log.i("Tag", userEntity.getHeadshot());
                bundle.putSerializable("user",userEntity);
                intent.putExtras(bundle);
                intent.putExtra("raccount",addmsgEntities[0].getResiveacc());
                startActivity(intent);
                adduserlist.setVisibility(View.GONE);
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
            conn=mainUI.mainui.conn;
            switch (msg.what){
                case 1:
                    if(conn)
                        getuser((CommonVo)msg.obj);
                    break;
                case 2:
                    if(conn)
                        getmsg((CommonVo)msg.obj);
                    break;
                case 3:
                    conncheck();
                    break;
            }
        }
        void getuser(final CommonVo commonVo){

            Thread thread = new Thread(){
                @Override
                public void run() {
                    commonResult= new Dispose().login(commonVo);
                    msgflag=commonResult.getFlag();

                }
            };
            Toast.makeText(Addcontact.this,"正在搜索",Toast.LENGTH_SHORT).show();
            thread.start();
            if(msgflag==null){
                msgflag="error";
            }
            while(msgflag.equals(""));
            if(commonResult!=null){
                if(commonResult.getUserEntities()!=null){
                    if(commonResult.getUserEntities()[0]!=null){
                        userEntity=commonResult.getUserEntities()[0];
                        for(int i=0;i<name.length;i++)
                            if(userEntity.getHeadshot().equals(name[i])){
                                headshot.setImageResource(pic[i]);
                                break;
                            }

                            account.setText(userEntity.getAccount());
                            nickname.setText(userEntity.getNickname());
                            adduserlist.setVisibility(View.VISIBLE);
                            Toast.makeText(Addcontact.this,msgflag,Toast.LENGTH_SHORT).show();
                            msgflag="";
                    }
                }
            }

        }
        void getmsg(final CommonVo commonVo){

            Thread thread = new Thread(){
                @Override
                public void run() {
                    commonResult = new Dispose().mainConnect(commonVo);
                    flag = commonResult.getFlag();
                }
            };
            thread.start();
            if(flag==null){
                flag="error";
            }
            if(commonResult!=null){
                if(commonResult.getAddmsgEntities()!=null){
                    Log.i("123", "getmsg: ");
                    if(commonResult.getAddmsgEntities()[0]!=null){
                        if(commonResult.getAddmsgEntities()[0].getMsgID()!=msgID) {
                            int pos=addmsglist.getFirstVisiblePosition();
                            new Initaddmsg(commonResult.getAddmsgEntities()).init();
                            msgID=commonResult.getAddmsgEntities()[0].getMsgID();
                            addmsglist.setSelection(pos);
                            flag="";
                        }
                    }else{
                        if(!flag.equals("成功"))
                        Toast.makeText(Addcontact.this,flag,Toast.LENGTH_SHORT).show();
                        flag="";
                        new Initaddmsg(commonResult.getAddmsgEntities()).init();
                    }
                }else if(commonResult.getAddmsgEntities()==null){
                    new Initaddmsg(commonResult.getAddmsgEntities()).init();
                }
            }

        }
        int count = 0;
        void conncheck(){
            conn=mainUI.mainui.conn;
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
    class Initaddmsg{
        AddmsgEntity[] addmsgEntities1;
        public Initaddmsg(AddmsgEntity[] addmsgEntities){
            this.addmsgEntities1=addmsgEntities;
        }
        void init(){
            List<Map<String,Object>> list  = new ArrayList<>();
            Map<String,Object> map;
            int count=0;
            for(int i=0;i<addmsgEntities1.length;i++)
                if(addmsgEntities1!=null){
                    if(addmsgEntities1[i]!=null)
                        if(addmsgEntities1[i].getSendacc()!=null)
                            count++;
                }
            Log.i("好友消息list", String.valueOf(count));
            if(addmsgEntities1!=null){
                Log.i("数据队列", "init: ");
                for(int i=count;i>=0;i--){
                    if(addmsgEntities1[i]!=null){
                    if(addmsgEntities1[i].getMsgID()>0&&addmsgEntities1[i].getHeadshot()!=null){
                        map=new HashMap<>();
                        for(int j=0 ;j<name.length;j++){
                            if(addmsgEntities1[i].getHeadshot().equals(name[j]))
                                map.put("headshot",pic[j]);
                        }
                        Log.i("好友消息list1", String.valueOf(addmsgEntities1[i].getMsgID()));
                        map.put("nickname",addmsgEntities1[i].getNickname());
                        map.put("message",addmsgEntities1[i].getMsg());
                        map.put("msgid",addmsgEntities1[i].getMsgID());
                        map.put("account",addmsgEntities1[i].getResiveacc());
                        map.put("raccount",addmsgEntities1[i].getSendacc());
                        list.add(map);
                    }
                }
                }
            }
            AddmsglistAdapter addmsglistAdapter = new AddmsglistAdapter(Addcontact.this,R.layout.usermsglistview,list);
            addmsglist.setAdapter(addmsglistAdapter);
        }

    }

}
