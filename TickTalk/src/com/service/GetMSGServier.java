package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;

public class GetMSGServier {
	public CommonResult getmsg(CommonVo commonVo){
		CommonResult commonResult = new CommonResult();
		CustomerDao customerDao = new CustomerDao();
		commonResult.setAddmsgEntities(customerDao.checkaddmsg(commonVo.getUserEntities()[0].getAccount()));
		customerDao.close();
		if(commonResult.getAddmsgEntities()!=null) {
			if(commonResult.getAddmsgEntities()[0]!=null) {
				for(int i=0;commonResult.getAddmsgEntities()[i]!=null;i++) {
					if(commonResult.getAddmsgEntities()[i].getAddmsgstatus()!=null) {
						if(commonResult.getAddmsgEntities()[i].getAddmsgstatus().equals("new")) {
							new CustomerDao().updateaddmsgstatus(commonResult.getAddmsgEntities()[i].getMsgID());
							System.out.println(commonResult.getAddmsgEntities()[i].getMsgID());
						}
					}
				}
			}
		}
		commonResult.setFlag("³É¹¦");

		customerDao.close();
		return commonResult;
	}

}
