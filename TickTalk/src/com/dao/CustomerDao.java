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
	/*��¼*/
	public CommonResult login(String account ,String password,String type) {
		CommonResult results = new CommonResult();
		ResultSet rs = null;
		
		if(type.equals("�ֻ���")){
			try {
				System.out.println("�ֻ���");
			    rs=baseDao.Phonecheck(account);
			    if(!rs.next())
			       results.setFlag("�˺�δע��");
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
		if(type.equals("����")) {
			try {
				rs=baseDao.Emailcheck(account);
				if(!rs.next())
					results.setFlag("�˺�δע��");
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
   /*ע��*/
	public CommonResult regedit(UserEntity userEntity,String invite) {
		CommonResult commonResult = new CommonResult();
		ResultSet rs= null;
		//���������
		if(invite.equals(baseDao.check())) {
			try {
				
				rs = baseDao.Phonecheck(userEntity.getPhone());//����ֻ��Ƿ�ע��
					if(!rs.next()) {
						rs= baseDao.Emailcheck(userEntity.getEmail());//��������Ƿ�ע��
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
							System.out.println("ע��");
							System.out.println(userEntity.getSex());
							UserEntity[] userEntities = new UserEntity[1];
							userEntities[0]= new UserEntity();
							userEntities[0].setAccount(userEntity.getAccount());
							if(baseDao.insertuser(userEntity)>0) {
								commonResult.setFlag("ע��ɹ�");
								commonResult.setUserEntities(userEntities);
								baseDao.closeAll();
							    baseDao=null;
							    baseDao = new BaseDao();
								MessageEntity messageEntity= new MessageEntity();//�º�ע�ᣬ���ͻ�ӭ��Ϣ
								messageEntity.setMessage("��ӭʹ��TickTalk");
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
								ContactEntity contactEntity = new ContactEntity();//������ϵ�ˣ�
								contactEntity.setMainacc(userEntity.getAccount());
								contactEntity.setFriendacc("TickTalkIDadmin");
								contactEntity.setRemark("");
								baseDao.insertcontact(contactEntity);
								baseDao.closeAll();
								baseDao=null;
								baseDao=new BaseDao();
								ContactEntity contactEntity1 = new ContactEntity();//������ϵ�ˣ�
								contactEntity1.setFriendacc(userEntity.getAccount());
								contactEntity1.setMainacc("TickTalkIDadmin");
								contactEntity1.setRemark("");
								baseDao.insertcontact(contactEntity1);
							}
					}else {
						commonResult.setFlag("�����ѱ�ע��");
					}
			}else {
						commonResult.setFlag("�ֻ����ѱ�ע��");	
					}
}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		else {
			commonResult.setFlag("���������");
		}
		return commonResult;
	}
	/*�޸�ͷ�����ǳ�*/
	public int changehs(String account ,String headshot) {

		return baseDao.updateheadshot(account, headshot);
	}
	public int changename(String account,String nickname) {
		return baseDao.updatenickname(account, nickname);
	}
	/*��ѯuser�Ƿ����*/
	public CommonResult checkUser(String account) {
		CommonResult commonResult = new CommonResult();
	    ResultSet rs = null;
		try {
			rs = baseDao.Account(account);
			if(rs.next()) {
				commonResult.setFlag("�˺Ŵ���");
			}else {
				commonResult.setFlag("�˺Ų�����");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return commonResult;
	}
	/*��ѯ�˺� �ֻ��� ���� �Ƿ�ƥ��*/
	public CommonResult checkPhoneOrEmail(String account , String phone,String email) {
		CommonResult commonResult = new CommonResult();
		ResultSet rs = null;
		try {
			rs=baseDao.Account(account);
			while(rs.next()) {
				if(phone!=null&&phone.equals(rs.getString("phone"))) {
					commonResult.setFlag("�ֻ��Ż�������ȷ");
				}else if(email!=null &&email.equals(rs.getString("email"))) {
					commonResult.setFlag("�ֻ��Ż�������ȷ");
				}else {
					commonResult.setFlag("�ֻ��Ż��������˺Ų�ƥ��");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return commonResult;
	}
	/*�޸�����*/
	public CommonResult updateps(String account,String password) {
		CommonResult commonResult = new CommonResult();
		int num =0;
		try {
			num = baseDao.updateps(account, password);
			if(num==0) {
				commonResult.setFlag("�޸�ʧ��");
			}else {
				commonResult.setFlag("�޸ĳɹ�");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return commonResult;
	}
	/*��ѯ�û���Ϣ*/
	public UserEntity user(String account) {
		System.out.println("��ѯ�û�");
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
	/*��ѯ��ϵ��*/
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
	/*��ѯ�����¼*/
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
	//�Ի���Ϣ��ѯ;
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
			    baseDao3.updatemsgstatus(messageEntities[x].getSendacc(), account,"old");//�����յ�����Ϣ��Ϊold;
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
//������Ϣ
public CommonResult sendmsg(CommonVo commonVo) {
	CommonResult commonResult = new CommonResult();
	int num=0;
	try {
		num = baseDao.insertmessage(commonVo.getMessageEntities()[0]);
		if(num>0) {
			commonResult.setFlag("���ͳɹ�");
		}
		else {
			commonResult.setFlag("����ʧ��");
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return commonResult;
}
//��ѯ��������
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
//��ѯ�û�
public UserEntity[] getuser(String account,String type) {
	UserEntity[] userEntities = new UserEntity[1];
	ResultSet rs=null;
	try {
		if(type.equals("�ֻ���")) {
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
		if(type.equals("����")) {
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
//������Ϣ���±�־
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
//ͬ����Ӻ���//ȱ�����������Ƿ���������¼
public CommonResult agree(int msgid,String account,String raccount) {
	CommonResult commonResult = new CommonResult();
	try {
		if(disagree(msgid).getFlag().equals("�Ѿܾ�")) {
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
		    messageEntity.setMessage("�����Ѿ��Ǻ�����");
		    messageEntity.setSendacc(raccount);
		    messageEntity.setResiveacc(account);
		    messageEntity.setMsgstatus("new");
		    messageEntity.setSendtime(new Timestamp(new java.util.Date().getTime()));
		    num2=baseDaos.insertmessage(messageEntity);
		    }
		    if(num!=0&&num1!=0&&num2!=0) {
		    	commonResult.setFlag("��ͬ��");
		    }else {
					commonResult.setFlag("�Ѵ��ں��ѹ�ϵ");
				}
		}else {
				commonResult.setFlag("����ʧ��");
			}
	
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return commonResult;
}
//�ܾ���Ӻ���
public CommonResult disagree(int msgid) {
	CommonResult commonResult = new CommonResult();
	int num=0;
	try {
		num=baseDao.deleteaddmsg(msgid);
		if(num!=0) {
			commonResult.setFlag("�Ѿܾ�");
		}else {
			commonResult.setFlag("����ʧ��");
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return commonResult;
}
//���ͺ���������Ϣ
public CommonResult sendaddmsg(AddmsgEntity addmsgEntity) {
	CommonResult commonResult = new CommonResult();
	int num=0;
	try {
		num=baseDao.insertaddmsg(addmsgEntity);
		if(num!=0) {
			commonResult.setFlag("���ͳɹ�");
		}else {
			commonResult.setFlag("����ʧ��");
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return commonResult;
}
//��ѯ�����Ƿ���ں��ѹ�ϵ
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
//ɾ����ϵ��
public CommonResult deleteContact(String account,String raccount) {
	CommonResult commonResult = new CommonResult();
	int num=0;
	try {
		num=baseDao.deletecontact(account, raccount);
		if(num!=0) {
			commonResult.setFlag("ɾ���ɹ�");
		}else {
			commonResult.setFlag("ɾ��ʧ��");
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
			commonResult.setFlag("�޸ĳɹ�");
		}else {
			commonResult.setFlag("�޸�ʧ��");
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
			commonResult.setFlag("�޸ĳɹ�");
		}else {
			commonResult.setFlag("�޸�ʧ��");
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
			commonResult.setFlag("�޸ĳɹ�");
		}else {
			commonResult.setFlag("�޸�ʧ��");
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return commonResult;
}
//��֤����
public CommonResult checkpassword(String account, String password) {
	CommonResult commonResult = new CommonResult();
	String flag="";
	try {
		flag=baseDao.LoginCheck(account, password);
		if(flag.equals("��¼�ɹ�")) {
			commonResult.setFlag("��֤�ɹ�");
		}else{
			commonResult.setFlag("��֤ʧ��");
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return commonResult;
}
//ɾ���û�
public CommonResult deleteUser(String account,String password) {
	CommonResult commonResult =new CommonResult();
	String flag="";
	try {
		flag=baseDao.LoginCheck(account, password);
		if(flag.equals("��¼�ɹ�")) {
			baseDao.closeAll();
			baseDao = new BaseDao();
			int num=0;
			num=baseDao.deleteuser(account);
			if(num!=0) {
				commonResult.setFlag("ɾ���ɹ�");
			}else {
				commonResult.setFlag("ɾ��ʧ��");
			}
		}else {
			commonResult.setFlag("������֤ʧ��");
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
