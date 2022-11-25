package com.socket;

import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.entity.CommonResult;
import com.entity.CommonVo;

public class ClientSocket {
    String TAG = "客户端";
    Socket clientsocket = null;
    public CommonResult client(CommonVo commonVo){
        CommonResult commonResult = new CommonResult();

        try{
            clientsocket = new Socket(InetAddress.getByName("服务端IP地址"),5000);
            Log.i(TAG, "连接上服务器");
            ObjectOutputStream oos = new ObjectOutputStream(clientsocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(clientsocket.getInputStream());
            Log.i(TAG, "写数据");
            if(commonVo!=null){
//                Log.i(TAG, commonVo.getFlag());
//                Log.i(TAG, commonVo.getStatus());
//                Log.i(TAG, commonVo.getUserEntities()[0].getAccount());
                oos.writeObject(commonVo);
                oos.flush();
                Log.i(TAG, "读数据");
                commonResult = (CommonResult)ois.readObject();
//               try{ Log.i(TAG, commonResult.getFlag());}catch (Exception e) {e.printStackTrace();}
            }

        }catch (Exception e){
            e.printStackTrace();

        }
        return commonResult;
    }
    public void close(){
        try{
            if(clientsocket!=null)
                clientsocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
