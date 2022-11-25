package com.dao;

import java.sql.Timestamp;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.entity.AddmsgEntity;
import com.entity.CommonResult;
import com.entity.CommonVo;
import com.entity.ContactEntity;
import com.entity.MessageEntity;
import com.entity.UserEntity;

public class CustomerDao {
	BaseDao baseDao = new BaseDao();
	/*登录*/
	public CommonResult login(String account ,String password,String type) {
		CommonResult results = new CommonResult();
		ResultSet rs = null;
		
		if(type.equals("手机号")){
			try {
				System.out.println("手机号");
			    rs=baseDao.Phonecheck(account);
			    if(!rs.next())
			       results.setFlag("账号未注册");
			    else 
			    {
			    	 
				      results.setFlag(baseDao.LoginCheck(rs.getString("account"), password));
				      UserEntity[] userEntities = new UserEntity[1];
				      userEntities[0]=new UserEntity();
				      userEntities[0].setAccount(rs.getString("account"));
				      results.setUserEntities(userEntities);
                      System.out.println(rs.getString("account"));
			          System.out.println(results.getFlag());
			    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(type.equals("邮箱")) {
			try {
				rs=baseDao.Emailcheck(account);
				if(!rs.next())
					results.setFlag("账号未注册");
				else {
					results.setFlag(baseDao.LoginCheck(rs.getString("account"), password));
					UserEntity[] userEntities = new UserEntity[1];
				      userEntities[0]=new UserEntity();
				      userEntities[0].setAccount(rs.getString("account"));
				      results.setUserEntities(userEntities);
					System.out.println(rs.getString("account"));
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		if(type.equals("TickTalkID")) {
			try {
				results.setFlag(baseDao.LoginCheck(account, password));
				UserEntity[] userEntities = new UserEntity[1];
			      userEntities[0]=new UserEntity();
			      userEntities[0].setAccount(account);
			      results.setUserEntities(userEntities);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return results;
	}
   /*注册*/
	public CommonResult regedit(UserEntity userEntity,String invite) {
		CommonResult commonResult = new CommonResult();
		ResultSet rs= null;
		//检查邀请码
		if(invite.equals(baseDao.check())) {
			try {
				
				rs = baseDao.Phonecheck(userEntity.getPhone());//检查手机是否被注册
					if(!rs.next()) {
						rs= baseDao.Emailcheck(userEntity.getEmail());//检查邮箱是否被注册
						if(!rs.next()) {
							int num = baseDao.checkacc()+1;
							if(num<10)
								userEntity.setAccount("TickTalkID000"+num);
							else if(num<100)
								userEntity.setAccount("TickTalkID00"+num);
							else if(num<1000)
								userEntity.setAccount("TickTalkID0"+num);
							else {
								userEntity.setAccount("TickTalkID"+num);
							}
							System.out.println("注册");
							System.out.println(userEntity.getSex());
							UserEntity[] userEntities = new UserEntity[1];
							userEntities[0]= new UserEntity();
							userEntities[0].setAccount(userEntity.getAccount());
							if(baseDao.insertuser(userEntity)>0) {
								commonResult.setFlag("注册成功");
								commonResult.setUserEntities(userEntities);
								baseDao.closeAll();
							    baseDao=null;
							    baseDao = new BaseDao();
								MessageEntity messageEntity= new MessageEntity();//新号注册，发送欢迎信息
								messageEntity.setMessage("欢迎使用TickTalk");
								messageEntity.setSendacc("TickTalkIDadmin");
								messageEntity.setResiveacc(userEntity.getAccount());
								java.util.Date date = new java.util.Date();
								Timestamp timestamp =new Timestamp(date.getTime());
								messageEntity.setSendtime(timestamp);
								messageEntity.setMsgstatus("new");
						
								baseDao.insertmessage(messageEntity);
								baseDao.closeAll();
								baseDao=null;
								baseDao = new BaseDao();
								ContactEntity contactEntity = new ContactEntity();//加入联系人，
								contactEntity.setMainacc(userEntity.getAccount());
								contactEntity.setFriendacc("TickTalkIDadmin");
								contactEntity.setRemark("");
								baseDao.insertcontact(contactEntity);
								baseDao.closeAll();
								baseDao=null;
								baseDao=new BaseDao();
								ContactEntity contactEntity1 = new ContactEntity();//加入联系人，
								contactEntity1.setFriendacc(userEntity.getAccount());
								contactEntity1.setMainacc("TickTalkIDadmin");
								contactEntity1.setRemark("");
								baseDao.insertcontact(contactEntity1);
							}
					}else {
						commonResult.setFlag("邮箱已被注册");
					}
			}else {
						commonResult.setFlag("手机号已被注册");	
					}
}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		else {
			commonResult.setFlag("邀请码错误");
		}
		return commonResult;
	}
	/*修改头像与昵称*/
	public int changehs(String account ,String headshot) {

		return baseDao.updateheadshot(account, headshot);
	}
	public int changename(String account,String nickname) {
		return baseDao.updatenickname(account, nickname);
	}
	/*查询user是否存在*/
	public CommonResult checkUser(String account) {
		CommonResult commonResult = new CommonResult();
	    ResultSet rs = null;
		try {
			rs = baseDao.Account(account);
			if(rs.next()) {
				commonResult.setFlag("账号存在");
			}else {
				commonResult.setFlag("账号不存在");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return commonResult;
	}
	/*查询账号 手机号 邮箱 是否匹配*/
	public CommonResult checkPhoneOrEmail(String account , String phone,String email) {
		CommonResult commonResult = new CommonResult();
		ResultSet rs = null;
		try {
			rs=baseDao.Account(account);
			while(rs.next()) {
				if(phone!=null&&phone.equals(rs.getString("phone"))) {
					commonResult.setFlag("手机号或邮箱正确");
				}else if(email!=null &&email.equals(rs.getString("email"))) {
					commonResult.setFlag("手机号或邮箱正确");
				}else {
					commonResult.setFlag("手机号或邮箱与账号不匹配");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return commonResult;
	}
	/*修改密码*/
	public CommonResult updateps(String account,String password) {
		CommonResult commonResult = new CommonResult();
		int num =0;
		try {
			num = baseDao.updateps(account, password);
			if(num==0) {
				commonResult.setFlag("修改失败");
			}else {
				commonResult.setFlag("修改成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return commonResult;
	}
	/*查询用户信息*/
	public UserEntity user(String account) {
		System.out.println("查询用户");
		UserEntity userEntity = new UserEntity();
		ResultSet rs = null;
		try {
			rs=baseDao.Account(account);
			while(rs.next()) {
				userEntity.setAccount(rs.getString("account"));
				userEntity.setEmail(rs.getString("email"));
				userEntity.setHeadshot(rs.getString("headshot"));
				userEntity.setNickname(rs.getString("nickname"));
				userEntity.setPhone(rs.getString("phone"));
				userEntity.setSex(rs.getString("sex"));
				userEntity.setSign(rs.getString("sign"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println(userEntity.getAccount());
		return userEntity;
	}
	/*查询联系人*/
	public ContactEntity[] contact(String account) {
		ContactEntity[] contactEntities = new ContactEntity[200];
		ResultSet rs = null;
		
		int i=0;
		try {
			rs=baseDao.contactcheck(account);
			while(rs.next()) {
				contactEntities[i]=new ContactEntity();
				contactEntities[i].setContactID(rs.getInt("contactID"));
				contactEntities[i].setFriendacc(rs.getString("friendacc"));
				contactEntities[i].setMainacc(rs.getString("mainacc"));
				contactEntities[i].setRemark(rs.getString("remark"));
				i++;
				if(i>=200)
					break;
			}
			System.out.println(contactEntities.length);
			baseDao.closeAll();
			rs=null;
			BaseDao baseDao1=null;
			for(int x =0;contactEntities[x]!=null;x++) {
				baseDao1 = new BaseDao();
				rs = baseDao1.Account(contactEntities[x].getFriendacc());
				while(rs.next()) {
					contactEntities[x].setHeadshot(rs.getString("headshot"));
					contactEntities[x].setNickname(rs.getString("nickname"));
					contactEntities[x].setSign(rs.getString("sign"));
				}
				baseDao1.closeAll();
				baseDao1 = null;
				rs=null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return contactEntities;
	}
	/*查询聊天记录*/
	public MessageEntity[] message(String account) {
		MessageEntity[] messageEntities = new MessageEntity[200];
		ResultSet rs = null;
		int i=0;
		try {
			rs = baseDao.msgcheck(account);
			while(rs.next()) {
				messageEntities[i]=new MessageEntity();
				messageEntities[i].setMessageID(rs.getInt("messageID"));
				messageEntities[i].setMessage(rs.getString("message"));
				messageEntities[i].setMsgstatus(rs.getString("msgstatus"));
				messageEntities[i].setPic(rs.getString("pic"));
				messageEntities[i].setResiveacc(rs.getString("resiveacc"));
				messageEntities[i].setSendacc(rs.getString("sendacc"));
				messageEntities[i].setSendtime(rs.getTimestamp("sendtime"));
				i++;
				if(i>=200)
					break;
			}
		//	System.out.println(messageEntities[0].getSendacc());
			baseDao.closeAll();
			rs=null;
			BaseDao baseDao1 = null;
			for(int x = 0;messageEntities[x]!=null;x++) {
				baseDao1 = new BaseDao();
				
				if(messageEntities[x].getResiveacc().equals(account)) {
					rs = baseDao1.Account(messageEntities[x].getSendacc());
				}else {
					rs=baseDao1.Account(messageEntities[x].getResiveacc());
				}
				while(rs.next()) {
					messageEntities[x].setHeadshot(rs.getString("headshot"));
					messageEntities[x].setNickname(rs.getString("nickname"));
				}
				baseDao1.closeAll();
				baseDao1 = null;
				rs = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return messageEntities;
	}
	//对话信息查询;
public MessageEntity[] chatmsg(String account,String raccount) {
	MessageEntity[] messageEntities = new MessageEntity[200];
	ResultSet rs = null;
	int i=0;
	try {
		rs = baseDao.onlyone(account,raccount);
		while(rs.next()) {
			messageEntities[i]=new MessageEntity();
			messageEntities[i].setMessageID(rs.getInt("messageID"));
			messageEntities[i].setMessage(rs.getString("message"));
			messageEntities[i].setMsgstatus(rs.getString("msgstatus"));
			messageEntities[i].setPic(rs.getString("pic"));
			messageEntities[i].setResiveacc(rs.getString("resiveacc"));
			messageEntities[i].setSendacc(rs.getString("sendacc"));
			messageEntities[i].setSendtime(rs.getTimestamp("sendtime"));
			i++;
			if(i>=200)
				break;
		}
	//	System.out.println(messageEntities[0].getSendacc());
		baseDao.closeAll();
		rs=null;
		BaseDao baseDao1 = null;
		for(int x = 0;messageEntities[x]!=null;x++) {
			baseDao1 = new BaseDao();
			
			if(messageEntities[x].getResiveacc().equals(account)) {
				rs = baseDao1.Account(messageEntities[x].getSendacc());
				BaseDao baseDao3 = new BaseDao();
			    baseDao3.updatemsgstatus(messageEntities[x].getSendacc(), account,"old");//将已收到的信息记为old;
				baseDao3.closeAll();
			}else {
				rs=baseDao1.Account(messageEntities[x].getResiveacc());
			}
			while(rs.next()) {
				messageEntities[x].setHeadshot(rs.getString("headshot"));
				messageEntities[x].setNickname(rs.getString("nickname"));
			}
			baseDao1.closeAll();
			baseDao1 = null;
			rs = null;
		}
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return messageEntities;
}
//发送信息
public CommonResult sendmsg(CommonVo commonVo) {
	CommonResult commonResult = new CommonResult();
	int num=0;
	try {
		num = baseDao.insertmessage(commonVo.getMessageEntities()[0]);
		if(num>0) {
			commonResult.setFlag("发送成功");
		}
		else {
			commonResult.setFlag("发送失败");
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return commonResult;
}
//查询好友请求
public AddmsgEntity[] checkaddmsg(String account) {
	AddmsgEntity[] addmsgEntities = new AddmsgEntity[200];
	ResultSet rs=null;
	try {
		int i=0;
		rs =baseDao.addmsgcheck(account);
		while(rs.next()) {
			addmsgEntities[i]= new AddmsgEntity();
			addmsgEntities[i].setMsgID(rs.getInt("msgID"));
			addmsgEntities[i].setSendacc(rs.getString("sendacc"));
			addmsgEntities[i].setResiveacc(rs.getString("resiveacc"));
			addmsgEntities[i].setMsg(rs.getString("msg"));
			addmsgEntities[i].setSendtime(rs.getTimestamp("sendtime"));
			addmsgEntities[i].setAddmsgstatus(rs.getString("addmsgstatus"));
			i++;
			if(i>=200)
				break;
		}
		if(addmsgEntities!=null) {
			for(int j=0;addmsgEntities[j]!=null;j++) {
				if(addmsgEntities[j].getSendacc()!=null) {
					baseDao.closeAll();
					BaseDao baseDao1=new BaseDao();
					rs=baseDao1.Account(addmsgEntities[j].getSendacc());
					while(rs.next()) {
						addmsgEntities[j].setNickname(rs.getString("nickname"));
						addmsgEntities[j].setHeadshot(rs.getString("headshot"));
					}
				}
			}
			}
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	baseDao.closeAll();
	return addmsgEntities;
}
//查询用户
public UserEntity[] getuser(String account,String type) {
	UserEntity[] userEntities = new UserEntity[1];
	ResultSet rs=null;
	try {
		if(type.equals("手机号")) {
			rs=baseDao.Phonecheck(account);
			while(rs.next()) {
				userEntities[0]=new UserEntity();
				userEntities[0].setAccount(rs.getString("account"));
				userEntities[0].setEmail(rs.getString("email"));
				userEntities[0].setPhone(rs.getString("phone"));
				userEntities[0].setHeadshot(rs.getString("headshot"));
				userEntities[0].setNickname(rs.getString("nickname"));
				userEntities[0].setSex(rs.getString("sex"));
				userEntities[0].setSign(rs.getString("sign"));
			}
		}
		if(type.equals("邮箱")) {
			rs=baseDao.Emailcheck(account);
			while(rs.next()) {
				userEntities[0]=new UserEntity();
				userEntities[0].setAccount(rs.getString("account"));
				userEntities[0].setEmail(rs.getString("email"));
				userEntities[0].setPhone(rs.getString("phone"));
				userEntities[0].setHeadshot(rs.getString("headshot"));
				userEntities[0].setNickname(rs.getString("nickname"));
				userEntities[0].setSex(rs.getString("sex"));
				userEntities[0].setSign(rs.getString("sign"));
			}
		}
		if(type.equals("TickTalkID")) {
			rs=baseDao.Account(account);
			while(rs.next()) {
				userEntities[0]=new UserEntity();
				userEntities[0].setAccount(rs.getString("account"));
				userEntities[0].setEmail(rs.getString("email"));
				userEntities[0].setPhone(rs.getString("phone"));
				userEntities[0].setHeadshot(rs.getString("headshot"));
				userEntities[0].setNickname(rs.getString("nickname"));
				userEntities[0].setSex(rs.getString("sex"));
				userEntities[0].setSign(rs.getString("sign"));
			}
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return userEntities;
	
}
//请求消息更新标志
public int updateaddmsgstatus(int msgid) {
	int num=0;
	try {
		num = baseDao.updateaddmsgstatus(msgid, "old");
		System.out.println(msgid);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return num;
}
//同意添加好友//缺少限制条件是否存在请求记录
public CommonResult agree(int msgid,String account,String raccount) {
	CommonResult commonResult = new CommonResult();
	try {
		if(disagree(msgid).getFlag().equals("已拒绝")) {
			BaseDao baseDaos = new BaseDao();
		    int num=0;
		    int num1=0;
		    int num2=0;
		    if(!contacts(account, raccount)) {
		    ContactEntity contactEntity  = new ContactEntity();
		    contactEntity.setFriendacc(raccount);
		    contactEntity.setMainacc(account);
		    contactEntity.setRemark("");
		    num=baseDaos.insertcontact(contactEntity);
		    baseDaos.closeAll();
		    baseDaos =new BaseDao();
		    contactEntity.setFriendacc(account);
		    contactEntity.setMainacc(raccount);
		    contactEntity.setRemark("");
		    num1=baseDaos.insertcontact(contactEntity);
		    baseDaos.closeAll();
		    baseDaos =new BaseDao();
		    MessageEntity messageEntity = new MessageEntity();
		    messageEntity.setMessage("我们已经是好友了");
		    messageEntity.setSendacc(raccount);
		    messageEntity.setResiveacc(account);
		    messageEntity.setMsgstatus("new");
		    messageEntity.setSendtime(new Timestamp(new java.util.Date().getTime()));
		    num2=baseDaos.insertmessage(messageEntity);
		    }
		    if(num!=0&&num1!=0&&num2!=0) {
		    	commonResult.setFlag("已同意");
		    }else {
					commonResult.setFlag("已存在好友关系");
				}
		}else {
				commonResult.setFlag("处理失败");
			}
	
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return commonResult;
}
//拒绝添加好友
public CommonResult disagree(int msgid) {
	CommonResult commonResult = new CommonResult();
	int num=0;
	try {
		num=baseDao.deleteaddmsg(msgid);
		if(num!=0) {
			commonResult.setFlag("已拒绝");
		}else {
			commonResult.setFlag("处理失败");
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return commonResult;
}
//发送好友请求消息
public CommonResult sendaddmsg(AddmsgEntity addmsgEntity) {
	CommonResult commonResult = new CommonResult();
	int num=0;
	try {
		num=baseDao.insertaddmsg(addmsgEntity);
		if(num!=0) {
			commonResult.setFlag("发送成功");
		}else {
			commonResult.setFlag("发送失败");
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return commonResult;
}
//查询俩人是否存在好友关系
public boolean contacts(String account,String raccount) {
	boolean flag=false;
	ResultSet rs = null;
	try {
		rs=baseDao.contacts(account, raccount);
		if(rs.next()&&rs.getString("mainacc")!=null) {
			flag=true;
		}
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	System.out.println(flag);
	return flag;
}
//删除联系人
public CommonResult deleteContact(String account,String raccount) {
	CommonResult commonResult = new CommonResult();
	int num=0;
	try {
		num=baseDao.deletecontact(account, raccount);
		if(num!=0) {
			commonResult.setFlag("删除成功");
		}else {
			commonResult.setFlag("删除失败");
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	System.out.println(commonResult.getFlag());
	return commonResult;
}
public CommonResult updateremark(String account,String raccount,String remark) {
	CommonResult commonResult = new CommonResult();
	int num=0;
	try {
		num=baseDao.updateremark(account, raccount, remark);
		if(num!=0) {
			commonResult.setFlag("修改成功");
		}else {
			commonResult.setFlag("修改失败");
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return commonResult;
}
public CommonResult updatesign(String account,String sign) {
	CommonResult commonResult = new CommonResult();
	int num=0;
	try {
		num=baseDao.updatesign(account, sign);
		if(num!=0) {
			commonResult.setFlag("修改成功");
		}else {
			commonResult.setFlag("修改失败");
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return commonResult;
}
public CommonResult updatenickname(String account,String nickname) {
	CommonResult commonResult = new CommonResult();
	int num=0;
	try {
		num=baseDao.updatenickname(account, nickname);
		if(num!=0) {
			commonResult.setFlag("修改成功");
		}else {
			commonResult.setFlag("修改失败");
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return commonResult;
}
//验证密码
public CommonResult checkpassword(String account, String password) {
	CommonResult commonResult = new CommonResult();
	String flag="";
	try {
		flag=baseDao.LoginCheck(account, password);
		if(flag.equals("登录成功")) {
			commonResult.setFlag("验证成功");
		}else{
			commonResult.setFlag("验证失败");
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return commonResult;
}
//删除用户
public CommonResult deleteUser(String account,String password) {
	CommonResult commonResult =new CommonResult();
	String flag="";
	try {
		flag=baseDao.LoginCheck(account, password);
		if(flag.equals("登录成功")) {
			baseDao.closeAll();
			baseDao = new BaseDao();
			int num=0;
			num=baseDao.deleteuser(account);
			if(num!=0) {
				commonResult.setFlag("删除成功");
			}else {
				commonResult.setFlag("删除失败");
			}
		}else {
			commonResult.setFlag("密码验证失败");
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return commonResult;
}

	public void close() {
		baseDao.closeAll();
	}

}
