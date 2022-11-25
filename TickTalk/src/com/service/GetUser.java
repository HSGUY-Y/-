package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;

public class GetUser {
	public CommonResult getuser(CommonVo commonVo) {
		CommonResult commonResult = new CommonResult();
		CustomerDao customerDao = new CustomerDao();
		commonResult.setUserEntities(customerDao.getuser(commonVo.getUserEntities()[0].getAccount(),
				commonVo.getStatus()));
		if(commonResult.getUserEntities()!=null) {
			if(commonResult.getUserEntities()[0]!=null) {
				if(commonResult.getUserEntities()[0].getAccount()!=null) {
					commonResult.setFlag("查询成功");
					System.out.println(commonResult.getUserEntities()[0].getAccount());
				}
				else {
					commonResult.setFlag("用户不存在");
				}
			}else {
				commonResult.setFlag("用户不存在");
			}
		}else {
			commonResult.setFlag("用户不存在");
		}
		
		customerDao.close();
		return commonResult;
	}

}
