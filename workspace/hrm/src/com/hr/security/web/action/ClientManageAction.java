package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IClientBO;
import com.hr.security.domain.Client;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.MyTools;
import com.hr.util.Pager;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class ClientManageAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger(Client.class.getName());
    private List clientList;
    private Client client;
    private Pager pager;
    private String clientUpdateId;
    private String clientId;
    public String changeString;

    public ClientManageAction() {
        this.clientList = null;

        this.client = null;

        this.pager = new Pager();

        this.clientUpdateId = null;

        this.clientId = null;

        this.changeString = null;
    }

    public Pager getPager() {
        return this.pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public String execute() throws Exception {
        IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
        this.clientList = clientBo.searchAllClient(this.client, this.pager);
        HttpSession session = ServletActionContext.getRequest().getSession();
        Userinfo userinfo = (Userinfo) session.getAttribute("userinfo");
        logger.info(userinfo.getUiUsername() + " 圄1�7" + new Date() + "----执行查询客户功能.");
        return "success";
    }

    public String delete() throws Exception {
        IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
        System.out.println("delete client id ==== " + this.clientId);
        clientBo.deleteClient(this.clientId);
        HttpSession session = ServletActionContext.getRequest().getSession();
        Userinfo userinfo = (Userinfo) session.getAttribute("userinfo");
        logger.info(userinfo.getUiUsername() + " 圄1�7" + new Date() + "----执行删除客户功能.删除客户的Id为："
                + this.clientId + "〄1�7");
        addSuccessInfo("删除编号丄1�7 ＄1�7" + this.clientId + "的客户成功�1�7�1�7");
        return "success";
    }

    public String update() throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession();
        Userinfo userinfo = (Userinfo) session.getAttribute("userinfo");
        if (this.changeString == null) {
            logger
                    .info(userinfo.getUiUsername() + " 圄1�7" + new Date()
                            + "----执行更新客户功能.更新客户失败〄1�7");
            addActionError("更新内容为空＄1�7");
            return "input";
        }
        IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
        System.out.println("update client id ==== " + this.clientUpdateId);
        Client client = clientBo.loadOneClient(this.clientUpdateId.trim());

        if (client == null) {
            logger
                    .info(userinfo.getUiUsername() + " 圄1�7" + new Date()
                            + "----执行更新客户功能.更新客户失败〄1�7");
            addActionError("没有发现编号丄1�7 ＄1�7" + this.clientUpdateId + "的客户！");
            return "input";
        }

        String decrypt = MyTools.vigenere(this.changeString, MyTools.getUpKey(this.clientUpdateId,
                                                                              MyTools.STRING),
                                          MyTools.ENCRYPT_MODE);
        client.setClientLimit(decrypt);
        clientBo.updateClient(client);
        logger.info(userinfo.getUiUsername() + " 圄1�7" + new Date() + "----执行更新客户功能.更新客户的Id为："
                + this.clientUpdateId + "，更新客户成功�1�7�1�7");
        addSuccessInfo("修改编号丄1�7 ＄1�7" + this.clientUpdateId + "的客户成功�1�7�1�7");
        return "success";
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List getClientList() {
        return this.clientList;
    }

    public void setClientList(List clientList) {
        this.clientList = clientList;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getChangeString() {
        return this.changeString;
    }

    public void setChangeString(String changeString) {
        this.changeString = changeString;
    }

    public String getClientUpdateId() {
        return this.clientUpdateId;
    }

    public void setClientUpdateId(String clientUpdateId) {
        this.clientUpdateId = clientUpdateId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.action.ClientManageAction JD-Core Version: 0.5.4
 */