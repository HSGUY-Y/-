package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;

public class LoginServer {
	public CommonResult loginservier(CommonVo commonVo) {
		CustomerDao customerDao = new CustomerDao();
		CommonResult rs = new CommonResult();
		String account=commonVo.getUserEntities()[0].getAccount() ;
		String password=commonVo.getUserEntities()[0].getPassword();
		String type=commonVo.getStatus();
        rs=customerDao.login(account, password, type);
        System.out.println(rs.getFlag());
        customerDao.close();
		return rs;
		
	}

}
