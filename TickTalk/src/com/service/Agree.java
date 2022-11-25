package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;


public class Agree {
	public CommonResult agree(CommonVo commonVo) {
		CommonResult commonResult =new CommonResult();
		CustomerDao customerDao = new CustomerDao();
		commonResult= customerDao.agree(commonVo.getAddmsgEntities()[0].getMsgID(),
				commonVo.getAddmsgEntities()[0].getSendacc(), 
				commonVo.getAddmsgEntities()[0].getResiveacc());
		customerDao.close();
		return commonResult;
	}

}
