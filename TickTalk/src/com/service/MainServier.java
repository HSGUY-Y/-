package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;
import com.entity.UserEntity;

public class MainServier {
	public CommonResult mainservier(CommonVo commonVo) {
		CommonResult commonResult = new CommonResult();
		CustomerDao customerDao = new CustomerDao();
		UserEntity[] userEntities = new UserEntity[1];
		System.out.println(commonVo.getUserEntities()[0].getAccount());
		userEntities[0]=new UserEntity();
		userEntities[0]=customerDao.user(commonVo.getUserEntities()[0].getAccount());
		commonResult.setUserEntities(userEntities);
	
		customerDao.close();
		CustomerDao customerDao2 = new CustomerDao();
		commonResult.setContactEntities(customerDao2.contact(commonVo.getUserEntities()[0].getAccount()));
		customerDao2.close();
		CustomerDao customerDao3 = new CustomerDao();
		commonResult.setMessageEntities(customerDao3.message(commonVo.getUserEntities()[0].getAccount()));
		customerDao3.close();
		CustomerDao customerDao4= new CustomerDao();
		commonResult.setAddmsgEntities(customerDao4.checkaddmsg(commonVo.getUserEntities()[0].getAccount()));
		commonResult.setFlag("³É¹¦");
		return commonResult;
	}

}
