package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IClientBO;
import com.hr.registor.RegistClient;
import com.hr.security.domain.Client;
import com.hr.util.MyTools;
import com.hr.util.PropertiesFileConfigManager;
import java.io.PrintStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

public class ClientManage extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Client client;
    private int userLimit;
    private int userLimitADM;
    private int userLimitMGR;
    private int userLimitEMP;
    private int empLimit;
    private String clientId;
    private String clientStatus;
    private String clientDate;
    private String clientCount;
    private boolean check;

    public ClientManage() {
        this.userLimit = 0;
        this.userLimitADM = 0;
        this.userLimitMGR = 0;
        this.userLimitEMP = 0;

        this.empLimit = 0;

        this.clientDate = "none";

        this.clientCount = "none";

        this.check = false;
    }

    public String execute() throws Exception {
        IClientBO clientBo = (IClientBO) getBean("clientBo");
        try {
            Object obj = getSession().getAttribute("clientNo");
            if (obj != null)
                this.clientId = obj.toString();

//            PropertiesFileConfigManager reader = PropertiesFileConfigManager.getInstance();
//            Map fileMap = reader.getProperties();
//            String host = (String) fileMap.get("sys.registor.address");
//            int port = Integer.parseInt((String) fileMap.get("sys.registor.port"));
//            String headString = null;
//            if (this.client == null) {
//                this.client = clientBo.loadOneClient(this.clientId);
//            }
//            headString = ":NC401";
//
//            String registMessage = ":R321#" + headString + "#" + this.client.getClientName() + "#"
//                    + formatStr(this.client.getClientShortName()) + "#"
//                    + formatStr(this.client.getClientAddress()) + "#"
//                    + formatStr(this.client.getClientZip()) + "#" + this.client.getClientPhone()
//                    + "#" + formatStr(this.client.getClientFax()) + "#"
//                    + this.client.getClientEmail() + "#" + this.client.getClientContactName() + "#"
//                    + formatStr(this.client.getClientRemarks()) + "#";

//            System.out.println("客户端发送的注册信息昄1�7=" + registMessage);
//            String testMessage = "";//new RegistClient(host, port).registConnection(registMessage);
//            System.out.println("注册的时候返回的数据是：=" + testMessage + "==他的长度是：" + testMessage.length());

//            if (":R301".equals(testMessage)) {
//                addErrorInfo("公司注册失败＄1�7");
//                return "input";
//            }
//            if (testMessage.length() > 7) {
//                int index = testMessage.indexOf("#");
//                if (index == -1) {
//                    addErrorInfo("公司注册失败＄1�7");
//                    return "input";
//                }
//                String id = testMessage.substring(0, index);
//
//                testMessage = testMessage.substring(index + 1);
//                index = testMessage.indexOf("#");
//                if (index == -1) {
//                    addErrorInfo("公司注册失败＄1�7");
//                    return "input";
//                }
//                String uuid = testMessage.substring(0, index);

//                String limit = testMessage.substring(index + 1);

//                this.client.setId(id);
                this.client.setId("123456");
                this.client.setClientStatus(Integer.valueOf(1));
                this.clientStatus = "regist";
//                this.client.setClientId(uuid);
                this.client.setClientId("123456");
//                this.client.setClientLimit(limit);
                this.client.setClientLimit("");

                HttpSession sessionClient = ServletActionContext.getRequest().getSession();
                String clientIds = sessionClient.getAttribute("clientNo").toString();
                Client oldClientObj = clientBo.loadOneClient(clientIds);
                this.client.setClientActivateTime(oldClientObj.getClientActivateTime());
                clientBo.deleteClient(oldClientObj.getId());
                clientBo.insertClient(this.client);
                getSession().setAttribute("clientNo", this.client.getId());
//            } else {
//                addErrorInfo("公司注册失败,请填写完整！");
//                return "input";
//            }
        } catch (Exception e) {
            e.printStackTrace();
            addErrorInfo("公司注册失败！");
            return "input";
        }
        addSuccessInfo("公司注册成功，请退出系统重新登陆！");
        return "success";
    }

    public String formatStr(String str) {
        if ((str == null) || (str.trim().equals(""))) {
            str = "  ";
        }
        return str;
    }

    public String executeInit() throws Exception {
        IClientBO clientBo = (IClientBO) getBean("clientBo");
        String clientNo = getSession().getAttribute("clientNo").toString();
        setClient(clientBo.loadOneClient(clientNo));
        String emp = getSession().getAttribute("EMP").toString();
        if (emp != null)
            this.empLimit = Integer.valueOf(emp).intValue();

        String user = getSession().getAttribute("USER").toString();
        if (user != null)
            this.userLimit = Integer.valueOf(user).intValue();
        String userADM = getSession().getAttribute("USERADM").toString();
        if (userADM != null)
            this.userLimitADM = Integer.valueOf(userADM).intValue();
        if (getSession().getAttribute("USERMGR") != null)
            this.userLimitMGR = Integer.valueOf(getSession().getAttribute("USERMGR").toString())
                    .intValue();
        if (getSession().getAttribute("USEREMP") != null) {
            this.userLimitEMP = Integer.valueOf(getSession().getAttribute("USEREMP").toString())
                    .intValue();
        }
        if (isCheck()) {
            addSuccessInfo("公司注册成功,请退出重新登录！");
        }

        String limitString = MyTools.vigenere(this.client.getClientLimit(), MyTools
                .getUpKey(this.client.getClientId(), MyTools.STRING), MyTools.DECRYPT_MODE);

        int index = limitString.indexOf("DATE");
        if (index != -1) {
            this.clientDate = limitString.substring(index + 5, index + 15);
        }
        index = limitString.indexOf("COUNT");
        if (index != -1) {
            this.clientCount = limitString.substring(index + 6);
        }
        int status = this.client.getClientStatus().intValue();

        if (status == 7) {
            this.clientStatus = "demo";
        } else if (status == 1) {
            this.clientStatus = "regist";
        } else if (status == 8) {
            this.clientStatus = "active";
        } else if (status == 9) {
            this.clientStatus = "exception";
        }
        return "success";
    }

    public void validate() {
        super.validate();
        if (this.client == null)
            return;
        if ((this.client.getClientName() != null) && (this.client.getClientName().length() > 128)) {
            addFieldError("client.clientName", "长度不能超过128位！");
        } else if ((this.client.getClientName() == null)
                || (this.client.getClientName().trim().equals(""))
                || (this.client.getClientName().length() < 1)) {
            addFieldError("client.clientName", "公司名称为必填项！");
        } else {
            findErrorMessage("client.clientName", this.client.getClientName());
        }
        if ((this.client.getClientShortName() != null)
                && (this.client.getClientShortName().length() > 64)) {
            addFieldError("client.clientShortName", "长度不能超过64位！");
        } else {
            findErrorMessage("client.clientShortName", this.client.getClientShortName());
        }
        if ((this.client.getClientAddress() != null)
                && (this.client.getClientAddress().length() > 128)) {
            addFieldError("client.clientAddress", "长度不能超过128位！");
        } else {
            findErrorMessage("client.clientAddress", this.client.getClientAddress());
        }
        if ((this.client.getClientZip() != null) && (this.client.getClientZip().length() > 8)) {
            addFieldError("client.clientZip", "长度不能超过8位！");
        } else {
            findErrorMessage("client.clientZip", this.client.getClientZip());
        }
        if ((this.client.getClientPhone() != null) && (this.client.getClientPhone().length() > 32)) {
            addFieldError("client.clientPhone", "长度不能超过32位！");
        } else if ((this.client.getClientPhone() == null)
                || (this.client.getClientPhone().trim().equals(""))
                || (this.client.getClientPhone().length() < 1)) {
            addFieldError("client.clientPhone", "公司电话为必填项！");
        } else {
            findErrorMessage("client.clientPhone", this.client.getClientPhone());
        }
        if ((this.client.getClientFax() != null) && (this.client.getClientFax().length() > 32)) {
            addFieldError("client.clientFax", "长度不能超过32位！");
        } else {
            findErrorMessage("client.clientFax", this.client.getClientFax());
        }
        if (this.client.getClientEmail() != null) {
            String email = this.client.getClientEmail();
            if (email.length() > 64) {
                addFieldError("client.clientEmail", "长度不能超过64位！");
            }
            if ((email.indexOf("@") < 0) || (email.indexOf(".", email.indexOf("@")) < 0)) {
                addFieldError("client.clientEmail", "EMAIL格式错误！");
            }
        } else if ((this.client.getClientEmail() == null)
                || (this.client.getClientEmail().trim().equals(""))
                || (this.client.getClientEmail().length() < 1)) {
            addFieldError("client.clientEmail", "公司邮箱为必填项！");
        } else {
            findErrorMessage("client.clientEmail", this.client.getClientEmail());
        }

        if ((this.client.getClientContactName() != null)
                && (this.client.getClientContactName().length() > 64))
            addFieldError("client.clientContactName", "长度不能超过64位！");
        else if ((this.client.getClientContactName() == null)
                || (this.client.getClientContactName().trim().equals(""))) {
            addFieldError("client.clientContactName", "公司联系人为必填项！");
        } else
            findErrorMessage("client.clientContactName", this.client.getClientContactName());

        if ((this.client.getClientRemarks() != null)
                && (this.client.getClientRemarks().length() > 255)) {
            addFieldError("client.clientRemarks", "长度不能超过255位！");
        } else
            findErrorMessage("client.clientRemarks", this.client.getClientRemarks());
    }

    public void findErrorMessage(String place, String checkString) {
        if (checkString.indexOf("#") != -1)
            addFieldError(place, "含有保留字符#，请替换该字符！");
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getEmpLimit() {
        return this.empLimit;
    }

    public void setEmpLimit(int empLimit) {
        this.empLimit = empLimit;
    }

    public int getUserLimit() {
        return this.userLimit;
    }

    public void setUserLimit(int userLimit) {
        this.userLimit = userLimit;
    }

    public int getUserLimitADM() {
        return this.userLimitADM;
    }

    public void setUserLimitADM(int userLimitADM) {
        this.userLimitADM = userLimitADM;
    }

    public int getUserLimitEMP() {
        return this.userLimitEMP;
    }

    public void setUserLimitEMP(int userLimitEMP) {
        this.userLimitEMP = userLimitEMP;
    }

    public int getUserLimitMGR() {
        return this.userLimitMGR;
    }

    public void setUserLimitMGR(int userLimitMGR) {
        this.userLimitMGR = userLimitMGR;
    }

    public boolean isCheck() {
        return this.check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean checkInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public String getClientStatus() {
        return this.clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getClientCount() {
        return this.clientCount;
    }

    public void setClientCount(String clientCount) {
        this.clientCount = clientCount;
    }

    public String getClientDate() {
        return this.clientDate;
    }

    public void setClientDate(String clientDate) {
        this.clientDate = clientDate;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.action.ClientManage JD-Core Version: 0.5.4
 */