package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;

public class DeleteContact {
	public CommonResult deletecontact(CommonVo commonVo) {
		CommonResult commonResult;
		CustomerDao customerDao = new CustomerDao();
		commonResult=customerDao.deleteContact(commonVo.getContactEntities()[0].getMainacc()
				, commonVo.getContactEntities()[0].getFriendacc());
		customerDao.close();
		return commonResult;
	}

}
