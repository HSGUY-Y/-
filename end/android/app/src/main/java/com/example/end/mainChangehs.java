package com.example.end;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dispose.Dispose;
import com.entity.CommonVo;
import com.entity.UserEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.GridViewAdapter;

public class mainChangehs extends AppCompatActivity {
    private GridView gridView ;
    private String account="";
    private List<Map<String,Object>> list ;
    private ImageView back;
    private TextView conns;
    private String flag="";
    private boolean conn=false;
    private Inittimer inittimer =null;
    private Inittimer inittimer1 = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picchange);
        gridView=findViewById(R.id.picchange);
        back = findViewById(R.id.back);
        conns=findViewById(R.id.conn);
        conns.setVisibility(View.GONE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent= getIntent();
        account = intent.getStringExtra("account");
        Log.i("跳转后", account);
        list=new ArrayList<>();
        Map<String,Object> map ;

        String[] name = {
                "咕噜","咕噜01","咕噜02","咕噜03","咕噜04","咕噜05","咕噜06","咕噜07","咕噜08","咕噜09","咕噜10","咕噜11"
        };
        int [] pic = {R.drawable.gulu_end,R.drawable.gulu01,R.drawable.gulu02,R.drawable.gulu03,R.drawable.gulu04,R.drawable.gulu05,
                R.drawable.gulu06,R.drawable.gulu07,R.drawable.gulu08,R.drawable.gulu09,R.drawable.gulu10,R.drawable.gulu11};
        for(int i=0;i<name.length;i++){
            map = new HashMap<String, Object>();
            map.put("name",name[i]);
            map.put("pic",pic[i]);
            list.add(map);
        }
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this,list);
        gridView.setAdapter(gridViewAdapter);
        inittimer1 = new Inittimer(handler);
        inittimer1.timer.schedule(inittimer1.task1,1,3*1000);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonVo commonVo = new CommonVo();
                UserEntity[] userEntities = new UserEntity[1];
                userEntities[0] = new UserEntity();
                commonVo.setFlag("headshot");
                userEntities[0].setAccount(account);
                userEntities[0].setHeadshot(list.get(position).get("name").toString());
                commonVo.setUserEntities(userEntities);
                inittimer = new Inittimer(handler);
                inittimer.commonVologin=commonVo;
                inittimer.timer.schedule(inittimer.task,1);
                Toast.makeText(mainChangehs.this,"正在上传,上传成功后头像需要一定时间加载",Toast.LENGTH_SHORT).show();
            }
        });
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
                    Log.i("修改", commonVo.getUserEntities()[0].getAccount());
                    flag= new Dispose().chang(commonVo);

                }
            };
            thread.start();
            if(flag==null){
                flag="error";
            }
            while (flag.equals(""));
            if(flag.equals("修改成功")){
                Toast.makeText(mainChangehs.this,flag,Toast.LENGTH_SHORT).show();
                finish();
                flag="";
            }
            else {
                Toast.makeText(mainChangehs.this,flag,Toast.LENGTH_SHORT).show();
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
}
