package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;

public class Updatenickname {
	public CommonResult updatenickname(CommonVo commonVo) {
		CommonResult commonResult;
		CustomerDao customerDao = new CustomerDao();
		commonResult= customerDao.updatenickname(commonVo.getUserEntities()[0].getAccount(),
				commonVo.getUserEntities()[0].getNickname());
		customerDao.close();
		return commonResult;
	}

}
