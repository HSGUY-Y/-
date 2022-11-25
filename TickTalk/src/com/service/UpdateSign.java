package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;

public class UpdateSign {
	public CommonResult updateSign(CommonVo commonVo) {
		CommonResult commonResult ;
		CustomerDao customerDao  = new CustomerDao();
		commonResult=customerDao.updatesign(commonVo.getUserEntities()[0].getAccount(),
				commonVo.getUserEntities()[0].getSign());
		customerDao.close();
		return commonResult;
	}

}
