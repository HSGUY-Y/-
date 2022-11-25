package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;

public class Updatehsname {
	public CommonResult updatehs(CommonVo commonVo) {
		CommonResult commonResult = new CommonResult();
		CustomerDao customerDao = new CustomerDao();
		int num= customerDao.changehs(commonVo.getUserEntities()[0].getAccount(), commonVo.getUserEntities()[0].getHeadshot());
		customerDao.close();
	    if(num!=0)
	    	commonResult.setFlag("修改成功");
	    else {
	    	commonResult.setFlag("修改失败");
			
		}
		System.out.println(commonResult.getFlag());
		return commonResult;
	}

}
