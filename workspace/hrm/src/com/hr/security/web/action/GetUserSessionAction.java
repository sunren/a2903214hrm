package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.base.UsersAuthority;
import com.hr.configuration.bo.IClientBO;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IPositionBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.security.bo.AuthBo;
import com.hr.security.bo.UserBo;
import com.hr.security.domain.Authority;
import com.hr.security.domain.Client;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.opensymphony.xwork2.Action;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

public class GetUserSessionAction extends BaseAction implements Action, Constants {
    private static final long serialVersionUID = 1L;
    private Userinfo user;

    public String execute() throws Exception {
        HttpSession session = getSession();
        String clientIds = session.getAttribute("clientNo").toString();
        UserBo userBo = (UserBo) SpringBeanFactory.getBean("userService");

        if (session.getAttribute("userId") != null) {
            this.user = userBo.getUserByName(session.getAttribute("userId").toString());
            if (this.user == null)
                return "noauth";
        } else {
            this.user = userBo.getUserByName(this.user.getUiUsername());
            if (this.user == null)
                return "input";

        }

        List authIds = this.user.getUiAuthIntegerList();
        IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
        Client client = clientBo.loadOneClient(clientIds);

        Hashtable clientLimitTable = ClientLimit.getClientLimit(client);

        String[] clientLimitArray = ((String) clientLimitTable.get("authLimit")).split(",");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < clientLimitArray.length; ++i) {
            if (!authIds.contains(Integer.valueOf(clientLimitArray[i])))
                continue;
            stringBuffer.append(clientLimitArray[i] + ",");
        }

        int stringBufferLength = stringBuffer.length();
        String authString = stringBuffer.substring(0, stringBufferLength - 1);

        session.setAttribute("EMP", Integer.valueOf(Integer.parseInt((String) clientLimitTable
                .get("EMP"))));
        session.setAttribute("USER", Integer.valueOf(Integer.parseInt((String) clientLimitTable
                .get("USER"))));
        session.setAttribute("USERADM", Integer.valueOf(Integer.parseInt((String) clientLimitTable
                .get("USERADM"))));
        if (clientLimitTable.get("USERMGR") != null)
            session.setAttribute("USERMGR", Integer.valueOf(Integer
                    .parseInt((String) clientLimitTable.get("USERMGR"))));
        if (clientLimitTable.get("USEREMP") != null)
            session.setAttribute("USEREMP", Integer.valueOf(Integer
                    .parseInt((String) clientLimitTable.get("USEREMP"))));
        this.user.setUiAuthEncrypt(authString);

        AuthBo authBo = (AuthBo) SpringBeanFactory.getBean("authService");
        HashMap authMap = getUserSesionMap(authBo
                .findAuthByIdList(this.user.getUiAuthIntegerList()));
        session.setAttribute("authList", authMap);

        session.setAttribute("userinfo", this.user);

        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        session.setAttribute("empName", empBo.loadEmp(this.user.getId(), null).getEmpName());

        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        session.setAttribute("positionId", posBo.getPosByEmpNo(this.user.getId(), new String[0])
                .getId());

        String requiredURL = (String) session.getAttribute("requiredURL");
        if ((requiredURL != null) && (requiredURL.length() > 0)) {
            session.removeAttribute("requiredURL");
            ServletActionContext.getResponse().sendRedirect(requiredURL);
            return null;
        }
        return "success";
    }

    private HashMap<UsersAuthority, String> getUserSesionMap(List<Authority> inputAuthorityList) {
        if ((inputAuthorityList == null) || (inputAuthorityList.size() == 0))
            return new HashMap();

        HashMap returnHashMap = new HashMap();
        Iterator authorityIterator = inputAuthorityList.iterator();
        Authority tempAuthority = null;
        while (authorityIterator.hasNext()) {
            tempAuthority = (Authority) authorityIterator.next();
            returnHashMap.put(new UsersAuthority(tempAuthority.getAuthorityModuleNo().trim(),
                    tempAuthority.getAuthorityConditionNo() + ""), tempAuthority
                    .getAuthorityModuleNo().trim().substring(0, 1));
        }

        return returnHashMap;
    }

    public Userinfo getUser() {
        return this.user;
    }

    public void setUser(Userinfo user) {
        this.user = user;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.web.action.GetUserSessionAction JD-Core Version: 0.5.4
 */