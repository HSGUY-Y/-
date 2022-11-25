package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;

public class Deleteperson {
	public CommonResult deleteuser(CommonVo commonVo) {
		CommonResult commonResult ;
		CustomerDao customerDao = new CustomerDao();
		commonResult = customerDao.deleteUser(commonVo.getUserEntities()[0].getAccount(),
				commonVo.getUserEntities()[0].getPassword());
		customerDao.close();
		 return commonResult;
	}

}
