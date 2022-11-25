package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;

public class Getmsg {
	public CommonResult getmsg(CommonVo commonVo) {
		CommonResult commonResult = new CommonResult();
		CustomerDao customerDao = new CustomerDao();
		commonResult.setMessageEntities(customerDao.chatmsg(commonVo.getUserEntities()[0].getAccount(),
				commonVo.getContactEntities()[0].getFriendacc()));
		commonResult.setFlag("³É¹¦");
		System.out.println(commonVo.getUserEntities()[0].getAccount());
		System.out.println(commonVo.getContactEntities()[0].getFriendacc());
		customerDao.close();
		return commonResult;
	}

}
