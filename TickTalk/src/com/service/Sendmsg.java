package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;

public class Sendmsg {
	public CommonResult sendmsg(CommonVo commonVo) {
		CommonResult commonResult = new CommonResult();
		CustomerDao customerDao = new CustomerDao();
		commonResult=customerDao.sendmsg(commonVo);
		customerDao.close();
		return commonResult;
	}

}
