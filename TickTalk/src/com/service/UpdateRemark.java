package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;

public class UpdateRemark{
	public CommonResult Updateremark(CommonVo commonVo) {
		CommonResult commonResult ;
		CustomerDao customerDao = new CustomerDao();
		commonResult=customerDao.updateremark(commonVo.getContactEntities()[0].getMainacc(),
				commonVo.getContactEntities()[0].getFriendacc(),
				commonVo.getContactEntities()[0].getRemark());
		customerDao.close();
		return commonResult;
	}

}
