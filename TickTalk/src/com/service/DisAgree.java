package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;

public class DisAgree {
	public CommonResult disagree(CommonVo commonVo) {
		CommonResult commonResult = new CommonResult();
		CustomerDao customerDao = new CustomerDao();
		commonResult=customerDao.disagree(commonVo.getAddmsgEntities()[0].getMsgID());
		customerDao.close();
		return commonResult;
	}

}
