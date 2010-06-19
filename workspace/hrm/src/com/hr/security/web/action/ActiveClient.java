package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.configuration.bo.IClientBO;
import com.hr.registor.RegistClient;
import com.hr.security.domain.Client;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.MyTools;
import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.SysConfigManager;
import java.io.PrintStream;
import java.util.Date;
import javax.servlet.http.HttpSession;

public class ActiveClient extends BaseAction {
    private static final long serialVersionUID = -4234940898871087425L;

    public String testConnection() {
        String flt = DWRUtil.checkAuth("userList", "execute");
        if ("error".equalsIgnoreCase(flt))
            return "noAuth";
        try {
            SysConfigManager fileConfigManager = PropertiesFileConfigManager.getInstance();
            String host = fileConfigManager.getProperty("sys.registor.address");
            int port = Integer.parseInt(fileConfigManager.getProperty("sys.registor.port"));
            String testMessage = new RegistClient(host, port).testConnection();
            if (":C101".equals(testMessage)) {
                return "no";
            }
            if (":T201".equals(testMessage))
                return "no";
            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "no";
    }

    public String activeOperate(String clientNo, String clientId) {
        String flt = DWRUtil.checkAuth("userList", "execute");
        if ("error".equalsIgnoreCase(flt))
            return "noAuth";
        if ("demo".equals(getSession().getAttribute("loginModel")))
            return "demo";
        try {
            SysConfigManager fileConfigManager = PropertiesFileConfigManager.getInstance();
            String host = fileConfigManager.getProperty("sys.registor.address");
            int port = Integer.parseInt(fileConfigManager.getProperty("sys.registor.port"));
            String checkStr = MyTools.vigenere(clientNo,
                                               MyTools.getUpKey(clientId, MyTools.STRING),
                                               MyTools.ENCRYPT_MODE);

            System.out.println("濄1�7活的时�1�7�客户端向服务器端传入的数据昄1�7=" + clientNo + "#" + clientId + "#"
                    + checkStr);
            String testMessage = new RegistClient(host, port).activeConnection(clientNo + "#"
                    + clientId + "#" + checkStr);

            System.out.println("濄1�7活返回的数据昄1�7=" + testMessage);
            if (testMessage.length() < 7)
                return testMessage;
            String[] insertMessage = testMessage.split("#");

            System.out.println("经过#分割，激活传回来的数据的长度是：" + insertMessage.length);
            for (int i = 0; i < insertMessage.length; ++i) {
                System.out.println(i + 1 + "==" + insertMessage[i] + "==");
            }
            if (insertMessage.length != 16)
                return ":TE201";
            Client client = new Client();
            client.setId(insertMessage[0]);
            client.setClientId(insertMessage[1]);
            client.setClientName(insertMessage[2]);
            client.setClientShortName(insertMessage[3]);
            client.setClientAddress(insertMessage[4]);
            client.setClientZip(insertMessage[5]);
            client.setClientPhone(insertMessage[6]);
            client.setClientFax(insertMessage[7]);
            client.setClientEmail(insertMessage[8]);
            client.setClientContactName(insertMessage[9]);
            if ((insertMessage[10] != null) && (!"null".equals(insertMessage[10]))
                    && (insertMessage[10].trim().length() > 0))
                client.setClientActivateTime(new Date());

            if ((insertMessage[11] != null) && (!"null".equals(insertMessage[11]))
                    && (insertMessage[11].trim().length() > 0))
                client.setClientServiceMonths(Integer.valueOf(Integer.parseInt(insertMessage[11])));

            if ((insertMessage[12] != null) && (!"null".equals(insertMessage[12]))
                    && (insertMessage[12].trim().length() > 0))
                client.setClientServiceTimes(Integer.valueOf(Integer.parseInt(insertMessage[12])));

            client.setClientLimit(insertMessage[13]);
            if ((insertMessage[14] != null) && (!"null".equals(insertMessage[14]))
                    && (insertMessage[14].trim().length() > 0))
                client.setClientStatus(Integer.valueOf(8));

            client.setClientRemarks(insertMessage[15]);
            IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
            String oldClientId = getSession().getAttribute("clientNo").toString();
            clientBo.deleteClient(oldClientId);
            clientBo.insertClient(client);
            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "no";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.action.ActiveClient JD-Core Version: 0.5.4
 */