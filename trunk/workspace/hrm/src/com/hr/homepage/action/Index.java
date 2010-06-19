package com.hr.homepage.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.configuration.bo.IClientBO;
import com.hr.configuration.domain.Infoclass;
import com.hr.homepage.bo.IIFindTip;
import com.hr.information.bo.IInformationBo;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.security.bo.AuthBo;
import com.hr.security.domain.Authority;
import com.hr.security.domain.Client;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.MyTools;
import com.hr.util.PropertiesFileConfigManager;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

public class Index extends BaseAction implements Constants {
    private static final long serialVersionUID = -4234940898871087425L;
    private static final int MAX_NUMBER;
    private List proclaimList;
    private String proclaimId;
    private String newsId;
    private String proclaimName;
    private int count;
    private String newsName;
    private List newsList;
    private Employee loginEmp;
    private List tipList;
    private String icon1;
    private String icon2;
    private String icon3;
    private String icon4;
    private String icon5;
    private String icon6;
    private String isDemo;

    public Index() {
        this.count = 0;

        this.icon1 = "no";

        this.icon2 = "no";

        this.icon3 = "no";

        this.icon4 = "no";

        this.icon5 = "no";

        this.icon6 = "no";

        this.isDemo = "no";
    }

    public String execute() throws Exception {
        Map session = ServletActionContext.getContext().getSession();
        Userinfo viewUser = (Userinfo) session.get("userinfo");
        if (viewUser == null)
            return "login";
        IInformationBo infoBo = (IInformationBo) getBean("informationBo");
        IEmployeeBo empbo = (IEmployeeBo) getBean("empBo");
        Employee emp = empbo.loadEmp(viewUser.getId(), null);
        IIFindTip findTip = (IIFindTip) getBean("findTip");
        String[] depNoArray = emp.getDeptInChargeOld();
        this.tipList = checkList(getTipListInit(findTip, viewUser.getId(), depNoArray));
        if ((hasAuth(801) == true) || (hasAuthModuleCondition(802, 3) == true)) {
            viewUser = null;
        }
        this.proclaimId = infoBo.getElementId("first");
        this.newsId = infoBo.getElementId("next");
        List list = infoBo.getInfoClassBySortId();
        if (list == null) {
            this.proclaimName = "信息不存在";
            this.newsName = "信息不存在";
        } else if (list.size() == 1) {
            this.proclaimName = ((Infoclass) list.get(0)).getInfoclassName();
            this.newsName = "信息不存在";
        } else if (list.size() > 1) {
            this.proclaimName = ((Infoclass) list.get(0)).getInfoclassName();
            this.newsName = ((Infoclass) list.get(1)).getInfoclassName();
        }
        this.proclaimList = checkList(infoBo.getListbyClass(this.proclaimId, viewUser));
        this.newsList = checkList(infoBo.getListbyClass(this.newsId, viewUser));
//        IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
//        HttpSession sessionClient = ServletActionContext.getRequest().getSession();
//        String clientIds = sessionClient.getAttribute("clientNo").toString();
//        Client client = clientBo.loadOneClient(clientIds);

//        if ((client.getClientStatus().intValue() == 7)
//                || (client.getClientStatus().intValue() == 0)) {
//            this.isDemo = "yes";
//        }
        checkClientAuth();

        return "success";
    }

    public List getNewsList() {
        return this.newsList;
    }

    public void setNewsList(List newsList) {
        this.newsList = newsList;
    }

    public List getProclaimList() {
        return this.proclaimList;
    }

    public void setProclaimList(List proclaimList) {
        this.proclaimList = proclaimList;
    }

    public Employee getLoginEmp() {
        return this.loginEmp;
    }

    public void setLoginEmp(Employee loginEmp) {
        this.loginEmp = loginEmp;
    }

    public List getTipListInit(IIFindTip findTip, String userId, String[] depNoArray) {
        List tempList = new ArrayList();

        if (hasAuth(101)) {
            setIcon1("yes");
            if (tempList.size() < MAX_NUMBER)
                tempList.addAll(findTip.getTip1012());
            List additionalTipList = findTip.getEmployeeAdditionalTip();
            if (additionalTipList.size() >= 3) {
                if (tempList.size() == 1)
                    tempList.addAll(additionalTipList.subList(0, 2));
                else
                    tempList.addAll(additionalTipList.subList(0, 3));
            } else {
                tempList.addAll(additionalTipList);
            }
            if (tempList.size() < MAX_NUMBER)
                tempList.addAll(findTip.getTip101());
        }
        if (hasAuthModuleCondition(101, 2)) {
            setIcon1("yes");
        }

        if (hasAuth(111)) {
            setIcon1("yes");
            if (tempList.size() < MAX_NUMBER)
                tempList.addAll(findTip.getTip111(userId));
        }

        if (hasAuth(301)) {
            setIcon4("yes");
            if (tempList.size() < MAX_NUMBER)
                tempList.addAll(findTip.getTip301());
        }

        if (hasAuth(601)) {
            setIcon5("yes");
            if (tempList.size() < MAX_NUMBER)
                tempList.addAll(findTip.getTip601());
        }

        if (hasAuth(201)) {
            setIcon2("yes");
            if (tempList.size() < MAX_NUMBER)
                tempList.addAll(findTip.getTip201());
        }

        if (hasAuth(401)) {
            setIcon3("yes");
            if (tempList.size() < MAX_NUMBER)
                tempList.addAll(findTip.getTip401());
        }

        if ((((hasAuthModuleCondition(411, 2)) || (hasAuthModuleCondition(411, 3))))
                && (tempList.size() < MAX_NUMBER))
            tempList.addAll(findTip.getTip4112(getCurrentPosNo()));

        if (hasAuth(411)) {
            setIcon3("yes");
            if (tempList.size() < MAX_NUMBER)
                tempList.addAll(findTip.getTip411(userId));
        }

        if (hasAuthModuleCondition(301, 2)) {
            setIcon4("yes");
            if (tempList.size() < MAX_NUMBER)
                tempList.addAll(findTip.getTip3012());
        }

        if ((hasAuthModuleCondition(311, 2)) && (depNoArray != null)
                && (tempList.size() < MAX_NUMBER))
            tempList.addAll(findTip.getTip3112(depNoArray));

        if (hasAuth(311)) {
            setIcon4("yes");
            if (tempList.size() < MAX_NUMBER)
                tempList.addAll(findTip.getTip311(userId));
        }

        if (hasAuthModuleCondition(601, 2)) {
            setIcon5("yes");
            if (tempList.size() < MAX_NUMBER)
                tempList.addAll(findTip.getTip6012());
        }

        if ((hasAuthModuleCondition(611, 2)) && (depNoArray != null)
                && (tempList.size() < MAX_NUMBER))
            tempList.addAll(findTip.getTip6112(depNoArray));

        if (hasAuth(611)) {
            setIcon5("yes");
            if (tempList.size() < MAX_NUMBER)
                tempList.addAll(findTip.getTip611(userId));
        }

        if (hasAuthModuleCondition(201, 2)) {
            setIcon2("yes");
            if (tempList.size() < MAX_NUMBER)
                tempList.addAll(findTip.getTip2012());
        }

        if (hasAuth(211)) {
            setIcon2("yes");
            if (tempList.size() < MAX_NUMBER)
                tempList.addAll(findTip.getTip2112(userId));
            if (tempList.size() < MAX_NUMBER)
                tempList.addAll(findTip.getTip211(userId));
        }

        if (hasAuth(221)) {
            setIcon2("yes");
        }

        if ((hasAuth(911)) || (hasAuth(921)) || (hasAuth(931)) || (hasAuth(951)) || (hasAuth(952))) {
            setIcon6("yes");
        }
        return tempList;
    }

    public List checkList(List tempList) {
        if (tempList.size() > MAX_NUMBER) {
            List temp = new ArrayList();
            for (int i = 0; i < MAX_NUMBER; ++i) {
                temp.add(tempList.get(i));
            }
            return temp;
        }
        return tempList;
    }

    public void checkClientAuth() {
        IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
        HttpSession session = ServletActionContext.getRequest().getSession();
        Client client = clientBo.loadOneClient(session.getAttribute("clientNo").toString());

        String limit = MyTools.vigenere(client.getClientLimit(), MyTools.getUpKey(client
                .getClientId(), MyTools.STRING), MyTools.DECRYPT_MODE);       

        String[] limitString = limit.split("#");
        String authStr = limitString[2];
        authStr = authStr.substring(authStr.indexOf("AUTH") + 5);
        String[] authString = authStr.split(",");
        List authList = new ArrayList();
        for (int i = 0; i < authString.length; ++i) {
            authList.add(Integer.valueOf(Integer.parseInt(authString[i])));
        }

        AuthBo authBo = (AuthBo) SpringBeanFactory.getBean("authService");
        List list = authBo.findAuthByIdList(authList);

        Iterator iterator = list.iterator();
        Set moduleList = new HashSet();
        while (iterator.hasNext()) {
            moduleList.add(((Authority) iterator.next()).getAuthorityModuleNo());
        }

        this.count = moduleList.size();

        if (("no".equals(this.icon1)) && (!moduleList.contains("101"))) {
            this.icon1 = "none";
            this.count -= 1;
        }

        if (("no".equals(this.icon2)) && (!moduleList.contains("201"))) {
            this.icon2 = "none";
            this.count -= 1;
        }

        if (("no".equals(this.icon3)) && (!moduleList.contains("401"))) {
            this.icon3 = "none";
            this.count -= 1;
        }

        if (("no".equals(this.icon4)) && (!moduleList.contains("301"))) {
            this.icon4 = "none";
            this.count -= 1;
        }

        if (("no".equals(this.icon5)) && (!moduleList.contains("601"))) {
            this.icon5 = "none";
            this.count -= 1;
        }
    }

    public List getTipList() {
        return this.tipList;
    }

    public void setTipList(List tipList) {
        this.tipList = tipList;
    }

    public String getIcon1() {
        return this.icon1;
    }

    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }

    public String getIcon2() {
        return this.icon2;
    }

    public void setIcon2(String icon2) {
        this.icon2 = icon2;
    }

    public String getIcon3() {
        return this.icon3;
    }

    public void setIcon3(String icon3) {
        this.icon3 = icon3;
    }

    public String getIcon4() {
        return this.icon4;
    }

    public void setIcon4(String icon4) {
        this.icon4 = icon4;
    }

    public String getIcon5() {
        return this.icon5;
    }

    public void setIcon5(String icon5) {
        this.icon5 = icon5;
    }

    public String getIcon6() {
        return this.icon6;
    }

    public void setIcon6(String icon6) {
        this.icon6 = icon6;
    }

    public String getNewsId() {
        return this.newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getProclaimId() {
        return this.proclaimId;
    }

    public void setProclaimId(String proclaimId) {
        this.proclaimId = proclaimId;
    }

    public String getNewsName() {
        return this.newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getProclaimName() {
        return this.proclaimName;
    }

    public void setProclaimName(String proclaimName) {
        this.proclaimName = proclaimName;
    }

    public String getIsDemo() {
        return this.isDemo;
    }

    public void setIsDemo(String isDemo) {
        this.isDemo = isDemo;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    static {
        String strNumber = PropertiesFileConfigManager.getInstance()
                .getProperty("sys.homepage.tips");
        MAX_NUMBER = Integer.valueOf(StringUtils.defaultIfEmpty(strNumber, "3")).intValue();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.homepage.action.Index JD-Core Version: 0.5.4
 */