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
					commonResult.setFlag("��ѯ�ɹ�");
					System.out.println(commonResult.getUserEntities()[0].getAccount());
				}
				else {
					commonResult.setFlag("�û�������");
				}
			}else {
				commonResult.setFlag("�û�������");
			}
		}else {
			commonResult.setFlag("�û�������");
		}
		
		customerDao.close();
		return commonResult;
	}

}
