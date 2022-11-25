package check;

public class InputCheck {
    private String value="";
    /*输入信息是phone，email，account*/
    public String accountCheck(String account){
        if(account.length()==11){
            int num = account.length();
            while (num>0){
                if(account.charAt(num-1)<'0'||account.charAt(num-1)>'9')
                    value="请输入正确的手机号";
                num--;
            }
            if(!value.equals("请输入正确的手机号"))
                value="手机号";
        }
       else if(account.length()>11){
            String tickalkid="TickTalkID";
            String[] emailend={"@163.com","@qq.com"};
            if(account.substring(0,10).equals(tickalkid))
                value=tickalkid;
            else if(account.indexOf(emailend[0])==account.length()-emailend[0].length()||
                    account.indexOf(emailend[1])==account.length()-emailend[1].length()){
                value="邮箱";
            }
               else value="请输入正确的账号或邮箱";
        }
        else  value="请输入正确的手机号或邮箱";
        return value;
    }
    /*密码长度检查*/
    public String passwordCheck(String password){
        int num = password.length();
        if(num>0) {
            if (num <= 16) {
                while (num > 0) {
                    if (password.charAt(num - 1) < '0' || password.charAt(num - 1) > '9')
                        if (password.charAt(num - 1) < 'a' || password.charAt(num - 1) > 'z') {
                            value = "请输入正确的密码，（a~z,0~9）";
                        }
                    num--;
                }
                if (!value.equals("请输入正确的密码，（a~z,0~9）"))
                    value = "密码";
            } else
                value = "密码最大长度为16位";
        }
        else value="请输入密码";
        return value;
    }
    /*昵称不能超过10位*/
    public String nicknameCheck(String nickname){
        if(nickname.length()<=10)
            value="昵称";
        else
            value="昵称长度不能超过10位";
        return value;
    }
    /*邀请码为数字且为4位*/
    public String inviteCheck(String inviteCode){
        int num = inviteCode.length();
        if(num==4){
            while(num>0){
            if(inviteCode.charAt(num-1)>='0'&&inviteCode.charAt(num-1)<='9')
                value = "邀请码";
            else
                value = "请输入正确邀请码，‘0’~‘9’";
            num--;
            }
        }
        else
            value = "请输入4位邀请码";
        return value;
    }

}
