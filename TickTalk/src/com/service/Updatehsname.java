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
	    	commonResult.setFlag("�޸ĳɹ�");
	    else {
	    	commonResult.setFlag("�޸�ʧ��");
			
		}
		System.out.println(commonResult.getFlag());
		return commonResult;
	}

}
