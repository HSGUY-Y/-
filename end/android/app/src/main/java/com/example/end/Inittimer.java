package com.example.end;

import android.os.Handler;
import android.os.Message;

import com.entity.CommonVo;
import com.entity.UserEntity;

import java.util.Timer;
import java.util.TimerTask;

public class Inittimer{//用于开启线程请求网络服务
    Timer timer = new Timer();//定时器
    CommonVo commonVologin = new CommonVo();
    CommonVo commonVoconn = new CommonVo();
    UserEntity[] userEntities = new UserEntity[1];
    Handler handler;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what=1;
            message.obj=commonVologin;
            handler.sendMessage(message);
        }
    };
    TimerTask task1 = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what=2;
            message.obj=commonVoconn;
            handler.sendMessage(message);

        }
    };
    TimerTask task2= new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what=3;
            message.obj=commonVoconn;
            handler.sendMessage(message);
        }
    };
    public  Inittimer(Handler handler){
        this.handler=handler;
        userEntities[0] = new UserEntity();
        userEntities[0].setAccount("1111");
        commonVoconn.setFlag("conn");
        commonVoconn.setUserEntities(userEntities);
    }
    public void cancle(){
        timer.cancel();
    }
}
