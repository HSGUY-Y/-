package com.service;

import com.dao.CustomerDao;
import com.entity.CommonResult;
import com.entity.CommonVo;
import com.entity.UserEntity;

public class RegeditServer {
	public CommonResult regeditserver(CommonVo commonVo) {
		CommonResult commonResult = new CommonResult();
		CustomerDao customerDao = new CustomerDao();
		UserEntity [] userEntities = new UserEntity[1];
		userEntities[0] = new UserEntity();
		userEntities=commonVo.getUserEntities();
		if(commonVo.getUserEntities()[0].getHeadshot()==null) {
			if(commonVo.getUserEntities()[0].equals("ÄÐ")) {
				userEntities[0].setHeadshot("¹¾àà04");
				commonVo.setUserEntities(userEntities);
			}else {
				userEntities[0].setHeadshot("¹¾àà07");
				commonVo.setUserEntities(userEntities);
			}
		}
		if(commonVo.getUserEntities()[0].getNickname()==null) {
			userEntities[0].setNickname(" ");
			commonVo.setUserEntities(userEntities);
		}
		commonResult=customerDao.regedit(commonVo.getUserEntities()[0], commonVo.getStatus());	
		customerDao.close();
		return commonResult;
	}

}
