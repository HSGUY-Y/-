package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;

public class Changenikname {
	public CommonResult changename(CommonVo commonVo) {
		CommonResult commonResult = new CommonResult();
		CustomerDao customerDao2 = new CustomerDao();
		int num=customerDao2.changename(commonVo.getUserEntities()[0].getAccount(),commonVo.getUserEntities()[0].getNickname());
		customerDao2.close();
		if(num!=0)
			commonResult.setFlag("修改成功");
		else {
			commonResult.setFlag("修改失败");
		}
		return commonResult;
	}
	
}
