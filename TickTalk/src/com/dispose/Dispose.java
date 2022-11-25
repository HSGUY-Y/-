package com.dispose;



import com.entity.CommonResult;
import com.entity.CommonVo;
import com.service.Agree;
import com.service.Changenikname;
import com.service.CheckPS;
import com.service.DeleteContact;
import com.service.Deleteperson;
import com.service.DisAgree;
import com.service.GetMSGServier;
import com.service.GetUser;
import com.service.Getmsg;
import com.service.LoginServer;
import com.service.MainServier;
import com.service.NotFoundPsServier;
import com.service.RegeditServer;
import com.service.SendAddmsg;
import com.service.Sendmsg;
import com.service.UpdateRemark;
import com.service.UpdateSign;
import com.service.Updatehsname;
import com.service.Updatenickname;

public class Dispose {
	CommonResult rs = new CommonResult();
	public CommonResult dispose(CommonVo commonVo) {
		System.out.println("dipose");
		switch (commonVo.getFlag()) {
		case "login": 
			System.out.println("login:");
			rs=new LoginServer().loginservier(commonVo);
			rs.setConn(true);
			System.out.println(rs.getFlag());
			System.out.println(rs.isConn());
			break;
		case "regedit":
			rs = new RegeditServer().regeditserver(commonVo);
			rs.setConn(true);
			break;
		case "conn":
			rs .setConn(true);
			break;
		case "headshot":
			System.out.println("headshot");
			rs = new Updatehsname().updatehs(commonVo);
			rs.setConn(true);
			break;
		case "nickname":
			rs = new Changenikname().changename(commonVo);
			rs.setConn(true);
			break;
		case "forgetps":
			rs = new NotFoundPsServier().checkAccount(commonVo);
			rs.setConn(true);
			break;
		case "forgetps1":
			rs = new NotFoundPsServier().checkPhoneoremail(commonVo);
			rs.setConn(true);
			break;
		case "forgetps2":
			rs = new NotFoundPsServier().updatePs(commonVo);
			rs.setConn(true);
			break;	
		case "main":
			rs=new MainServier().mainservier(commonVo);
			rs.setConn(true);
			System.out.println(rs.getFlag());
			break;
		case "sendmsg":
			rs=new Sendmsg().sendmsg(commonVo);
			rs.setConn(true);
			break;
		case "getmsg":
			rs=new Getmsg().getmsg(commonVo);
			rs.setConn(true);
			break;
		case "getuser":
			rs=new GetUser().getuser(commonVo);
			rs.setConn(true);
			break;
		case "getaddmsg":
			rs=new GetMSGServier().getmsg(commonVo);
			rs.setConn(true);
			break;
		case "agree":
			rs=new Agree().agree(commonVo);
			rs.setConn(true);
			break;
		case "disagree":
			rs=new DisAgree().disagree(commonVo);
			rs.setConn(true);
			break;
		case "addusermsg":
			rs = new SendAddmsg().sendaddmsg(commonVo);
			rs.setConn(true);
			break;
		case "deletecontact":
			System.out.println("delete"+commonVo.getContactEntities()[0].getFriendacc());
			rs=new DeleteContact().deletecontact(commonVo);
			rs.setConn(true);
			break;
		case "changeremark":
			rs=new UpdateRemark().Updateremark(commonVo);
			rs.setConn(true);
			break;
		case "changesign":
			rs=new UpdateSign().updateSign(commonVo);
			rs.setConn(true);
			break;
		case "CheckPS":
			rs=new CheckPS().checkps(commonVo);
			rs.setConn(true);
			break;
		case "changenickname":
			rs=new Updatenickname().updatenickname(commonVo);
			rs.setConn(true);
			break;
		case "deleteuser":
			rs=new Deleteperson().deleteuser(commonVo);
			rs.setConn(true);;
		default:
			break;
		}
		return rs;
	}

}
