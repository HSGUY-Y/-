package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;

public class NotFoundPsServier {
	public CommonResult checkAccount(CommonVo commonVo) {
		CommonResult commonResult = new CommonResult();
		CustomerDao customerDao = new CustomerDao();
		commonResult = customerDao.checkUser(commonVo.getUserEntities()[0].getAccount());
		customerDao.close();
		return commonResult;
	}
	public CommonResult checkPhoneoremail(CommonVo commonVo) {
		CommonResult commonResult = new CommonResult();
		CustomerDao customerDao = new CustomerDao();
		commonResult = customerDao.checkPhoneOrEmail(commonVo.getUserEntities()[0].getAccount(),
				commonVo.getUserEntities()[0].getPhone(), commonVo.getUserEntities()[0].getEmail());
		customerDao.close();
		return commonResult;
	}
	public CommonResult updatePs(CommonVo commonVo) {
		CommonResult commonResult  =  new CommonResult();
		CustomerDao customerDao = new CustomerDao();
		commonResult = customerDao.updateps(commonVo.getUserEntities()[0].getAccount(),
				commonVo.getUserEntities()[0].getPassword());
		customerDao.close();
		return commonResult;
	}

}
