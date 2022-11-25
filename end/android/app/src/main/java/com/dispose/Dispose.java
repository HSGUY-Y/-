package com.dispose;

import com.socket.ClientSocket;

import com.entity.CommonResult;
import com.entity.CommonVo;

public class Dispose {
    public boolean connect(CommonVo commonVo){
        CommonResult commonResult = new CommonResult();
        ClientSocket clientSocket = new ClientSocket();
        commonResult = clientSocket.client(commonVo);
        clientSocket.close();
        return commonResult.isConn();
    }
    public CommonResult login(CommonVo commonVo){
        CommonResult commonResult = new CommonResult();
        ClientSocket clientSocket = new ClientSocket();
        commonResult = clientSocket.client(commonVo);
        clientSocket.close();
        return commonResult;
    }
    public  CommonResult regedit(CommonVo commonVo){
        CommonResult commonResult = new CommonResult();
        ClientSocket clientSocket = new ClientSocket();
        commonResult = clientSocket.client(commonVo);
        clientSocket.close();
        return commonResult;
    }
    public String chang(CommonVo commonVo){//修改用户信息
        CommonResult commonResult = new CommonResult();
        ClientSocket clientSocket = new ClientSocket();
        commonResult = clientSocket.client(commonVo);
        clientSocket.close();
        return commonResult.getFlag();

    }
    public String checkAccount(CommonVo commonVo){//账号存在与否
        CommonResult commonResult = new CommonResult();
        ClientSocket clientSocket = new ClientSocket();
        commonResult = clientSocket.client(commonVo);
        clientSocket.close();
        return commonResult.getFlag();
    }
    public String checkPhoneOrEmail(CommonVo commonVo){//检查对应手机号或邮箱是否正确
        CommonResult commonResult = new CommonResult();
        ClientSocket clientSocket = new ClientSocket();
        commonResult = clientSocket.client(commonVo);
        clientSocket.close();
        return commonResult.getFlag();
    }
    public CommonResult mainConnect(CommonVo commonVo){
        CommonResult commonResult = new CommonResult();
        ClientSocket clientSocket = new ClientSocket();
        commonResult = clientSocket.client(commonVo);
        clientSocket.close();
        return commonResult;
    }

}
