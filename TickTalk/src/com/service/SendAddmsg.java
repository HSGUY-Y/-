package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;

public class SendAddmsg {
	public CommonResult sendaddmsg(CommonVo commonVo) {
		CommonResult commonResult=new CommonResult();
		CustomerDao customerDao1=new CustomerDao();
		if(customerDao1.contacts(commonVo.getAddmsgEntities()[0].getSendacc(), 
				commonVo.getAddmsgEntities()[0].getResiveacc())) {
			commonResult.setFlag("已存在好友关系");
			customerDao1.close();
		}else {
			CustomerDao customerDao = new CustomerDao();
			commonResult = customerDao.sendaddmsg(commonVo.getAddmsgEntities()[0]);
			customerDao.close();
		}
		System.out.println(commonResult.getFlag());
		return commonResult;
	}

}
