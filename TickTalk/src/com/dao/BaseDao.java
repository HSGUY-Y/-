package com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import com.entity.AddmsgEntity;
import com.entity.ContactEntity;
import com.entity.MessageEntity;
import com.entity.UserEntity;
import com.mysql.cj.jdbc.Blob;
import com.util.JDBCUtils;
//调用库中22个存储过程，实行app整体功能
public class BaseDao {
	JDBCUtils DBUtils = new JDBCUtils();
	Connection conn = DBUtils.getConnection();
	CallableStatement call = null;
	ResultSet  rs = null;
	//使用account登录检测
	public String  LoginCheck(String account,String password) {
		String flag="";
		try {
			call = conn.prepareCall("{call logincheck(?,?,?) }");
			//设置参数
			call.setString(1,account);
			call.setString(2, password);
			//设置输出参数
			call.registerOutParameter(3, Types.CHAR);
		    call.execute();
			flag=call.getString(3);
			System.out.println(flag);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}
	//使用account查询
	public ResultSet Account(String account) {
		try {
			call= conn.prepareCall("{call checkuser(?)}");
			call.setString(1, account);
			rs=call.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rs;
	}
	//使用手机号查询
	public ResultSet Phonecheck(String phone) {
		try {
			call = conn.prepareCall("{call checkphone(?)}");
			call.setString(1,phone);
			rs=call.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}
	//使用邮箱查询
	public ResultSet Emailcheck(String email) {
		try {
			call=conn.prepareCall("{call checkeamil(?)}");
			call.setString(1, email);
			rs=call.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	//account查询请求好友消息
	public ResultSet addmsgcheck(String account) {
		try {
			call=conn.prepareCall("{call checkaddmsg(?)}");
			call.setString(1, account);
			rs=call.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
		
	}
	//account查询联系人
	public ResultSet contactcheck(String account) {
		try {
			call=conn.prepareCall("{call checkcontact(?)}");
			call.setString(1, account);
			rs=call.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}
	//account查询聊天记录
	public ResultSet msgcheck(String account) {
		try {
			call=conn.prepareCall("{call checkmsg(?)}");
			call.setString(1, account);
			rs=call.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}
	//msgid删除好友请求
	public int deleteaddmsg(int msgid) {
		int num = 0;
		try {
			call=conn.prepareCall("{call deleteaddmsg(?)}");
			call.setInt(1,msgid);
			num=call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}
  //删除联系人
	public int deletecontact(String account,String raccount) {
		int num=0;
		try {
			call = conn.prepareCall("{call deletecontact(?,?) }");
			call.setString(1, account);
			call.setString(2, raccount);
			num=call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}
	//删除聊天记录
	public int deletemessage(int messageID) {
		int num=0;
		try {
			call=conn.prepareCall("{call deletemessage(?)}");
			call.setInt(1, messageID);
			num=call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}
	//删除用户
	public int deleteuser(String account) {
		int num=0;
		try {
			call = conn.prepareCall("{call deleteuser(?)}");
			call.setString(1, account);
			num=call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return num;
	}
	//插入用户记录
	public int insertuser(UserEntity userEntity) {
		int num=0;
		try {
			call = conn.prepareCall("{call insertuser(?,?,?,?,?,?,?,?)}");
			call.setString(1, userEntity.getAccount());
			call.setString(2, userEntity.getPhone());
			call.setString(3, userEntity.getEmail());
			call.setString(4, userEntity.getSex());
			call.setString(5,userEntity.getPassword());
			call.setString(6, userEntity.getNickname());
			call.setString(7, userEntity.getHeadshot());
			call.setString(8,userEntity.getSign());
			num = call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}
	//插入消息记录
	public int insertmessage(MessageEntity messageEntity) {
		int num = 0;
		try {
			call = conn.prepareCall("{call insertmessage(?,?,?,?,?,?)}");
			call.setString(1,messageEntity.getSendacc());
			call.setString(2, messageEntity.getResiveacc());
			call.setString(3, messageEntity.getMessage());
			call.setString(4,messageEntity.getPic());
			call.setTimestamp(5, messageEntity.getSendtime());
			call.setString(6, messageEntity.getMsgstatus());
			num = call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}
   //插入联系人记录
	public int insertcontact(ContactEntity contactEntity) {
		int num=0;
		try {
			call = conn.prepareCall("{call insertcontact(?,?,?)}");
			call.setString(1, contactEntity.getMainacc());
			call.setString(2,contactEntity.getFriendacc());
			call.setString(3,contactEntity.getRemark());
			num = call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}
	//插入好友请求记录
	public int insertaddmsg(AddmsgEntity addmsgEntity) {
		int num = 0;
		try {
			call = conn.prepareCall("call insertaddmsg(?,?,?,?,?)");
			call.setString(1, addmsgEntity.getSendacc());
			call.setString(2,addmsgEntity.getResiveacc());
			call.setString(3,addmsgEntity.getMsg());
			call.setTimestamp(4, addmsgEntity.getSendtime());
			call.setString(5, addmsgEntity.getAddmsgstatus());
		
			num = call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
		
	}
	//修改手机号
	public int updatephone(String account,String phone) {
		int num = 0;
		try {
			call = conn.prepareCall("{call updatephone(?,?)}");
			call.setString(1, account);
			call.setString(2, phone);
			num = call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}
	//修改email
	public int updateemail(String account,String email) {
		int num=0;
	    try {
			call = conn.prepareCall("{call updateemail(?,?)}");
			call.setString(1, account);
			call.setString(2, email);
			num = call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}
	//修改头像
	public int updateheadshot(String account,String headshot) {
		int num =0 ;
		try {
			System.out.println(account+"   "+headshot);
			call = conn.prepareCall("{call updateheadshot(?,?)}");
			call.setString(1, account);
			call.setString(2, headshot);
			num = call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num ;
	}
	//修改昵称
	public int updatenickname(String account,String nickname) {
	      int num = 0;
		try {
			System.out.println(account+"   "+nickname);
			call = conn.prepareCall("{call updatenickname(?,?)}");
			call.setString(1,account);
			call.setString(2, nickname);
			num = call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num ;
	}
	//修改密码
	public int updateps(String account,String password) {
		int num =0;
		try {
			call = conn.prepareCall("{call updateps(?,?)}");
			call.setString(1, account);
			call.setString(2, password);
			num = call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}
	//修改个签
	public int updatesign(String account,String sign) {
		int num =0;
		try {
			call =conn.prepareCall("{call updatesign(?,?)}");
			call.setString(1, account);
			call.setString(2, sign);
			num= call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}
	//修改信息状态
	public int updatemsgstatus(String sendacc,String resiveacc,String status) {
		int num =0;
		try {
			call = conn.prepareCall("{call updatemsgstatus(?,?,?)}");
			call.setString(1, sendacc);
			call.setString(2, resiveacc);
			call.setString(3, status);
			num = call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}
	//修改好友请求状态
	public int updateaddstatus(int msgID,String status) {
		int num =0;
		try {
			call = conn.prepareCall("{call updateaddstatus(?,?)}");
			call.setInt(1, msgID);
			call.setString(2, status);
			num = call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}
	//修改好友请求处理状态
	public int updateaddmsgstatus(int msgID,String status) {
		int num =0;
		try {
			call= conn.prepareCall("{call updateaddmsgstatus(?,?)}");
			call.setInt(1,msgID);
			call.setString(2, status);
			num=call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
		
	}
	//查询注册邀请码
	public String check() {
		String flag = "";
		try {
			call = conn.prepareCall("{call invitecode()}");
			rs = call.executeQuery();
			while(rs.next())
				flag = rs.getString("invite");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}
	//查询账户数目
	public int checkacc() {
		int num = 0;
		try {
			call = conn.prepareCall("{call accnum()}");
			rs = call.executeQuery();
			while(rs.next())
				num = rs.getInt("num");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return num;
	}
	//查询单对话信息
	public ResultSet onlyone(String account,String raccount) {
		try {
			call = conn.prepareCall("{call onlyonechat(?,?)}");
			call.setString(1, account);
			call.setString(2, raccount);
			rs = call.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}
	//查找俩人是否存在好友关系
	public ResultSet contacts(String account,String raccount) {
		try {
			call=conn.prepareCall("{call contacts(?,?)}");
			call.setString(1, account);
			call.setString(2,raccount);
			rs =call.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}
	//修改好友备注
	public int updateremark(String account,String raccount,String remark) {
		int num=0;
		try {
			call=conn.prepareCall("{call updateremark(?,?,?)}");
			call.setString(1, account);
			call.setString(2, raccount);
			call.setString(3, remark);
			num=call.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}
	
	public void closeAll() {
		DBUtils.closeAll();
	    try {
			conn.close();
			if(call!=null)
			call.close();
			if(rs!=null)
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	   
		
	}

}
