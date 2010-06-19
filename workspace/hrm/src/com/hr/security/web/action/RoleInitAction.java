package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IClientBO;
import com.hr.security.bo.AuthBo;
import com.hr.security.bo.RoleBo;
import com.hr.security.domain.Authority;
import com.hr.security.domain.Client;
import com.hr.security.domain.Role;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

public class RoleInitAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private List authList1;
    private List authList2;
    private List authList3;
    private List authList4;
    private List authList5;
    private List authList6;
    private List authList7;
    private List authList8;
    private List authList9;
    private Role role;
    private RoleBo roleBo;

    public RoleInitAction() {
        this.authList1 = new ArrayList();

        this.authList2 = new ArrayList();

        this.authList3 = new ArrayList();

        this.authList4 = new ArrayList();

        this.authList5 = new ArrayList();

        this.authList6 = new ArrayList();

        this.authList7 = new ArrayList();

        this.authList8 = new ArrayList();

        this.authList9 = new ArrayList();
    }

    public String execute() throws Exception {
        if ((this.role == null) || (this.role.getId() == null)) {
            addActionError("无效 roleId 参数");
            return "error";
        }

        this.role = getRoleBo().getRole(this.role.getId());
        if ((this.role == null) || (this.role.getId() == null)) {
            addActionError("无效 roleId 参数");
            return "error";
        }
        initShowAuthList();
        return "success";
    }

    public String addinit() throws Exception {
        initShowAuthList();
        return "success";
    }

    private void initShowAuthList() {
        HttpSession sessionClient = ServletActionContext.getRequest().getSession();
        String clientIds = sessionClient.getAttribute("clientNo").toString();
        IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
        Client client = clientBo.loadOneClient(clientIds);
        Hashtable clientTable = ClientLimit.getClientLimit(client);
        String[] authIds = ((String) clientTable.get("authLimit")).split(",");
        List authIdList = new ArrayList();
        for (String auth : authIds)
            authIdList.add(auth);

        AuthBo authBo = (AuthBo) SpringBeanFactory.getBean("authService");
        List list = authBo.getAuthList();

        for (int authIndex = 0; authIndex < list.size(); ++authIndex) {
            Authority auth = (Authority) list.get(authIndex);

            if (authIdList.contains(auth.getId().toString())) {
                auth.setAuthorityActionNo("yes");
            } else {
                auth.setAuthorityActionNo("no");
            }
            String firstModelString = auth.getAuthorityModuleNo().substring(0, 1);
            if ("1".equals(firstModelString)) {
                this.authList1.add(auth);
            } else if ("2".equals(firstModelString)) {
                this.authList2.add(auth);
            } else if ("3".equals(firstModelString)) {
                this.authList3.add(auth);
            } else if ("4".equals(firstModelString)) {
                this.authList4.add(auth);
            } else if ("5".equals(firstModelString)) {
                this.authList5.add(auth);
            } else if ("6".equals(firstModelString)) {
                this.authList6.add(auth);
            } else if ("7".equals(firstModelString)) {
                this.authList7.add(auth);
            } else if ("8".equals(firstModelString)) {
                this.authList8.add(auth);
            } else {
                if (!"9".equals(firstModelString))
                    continue;
                this.authList9.add(auth);
            }
        }
    }

    public RoleBo getRoleBo() {
        if (this.roleBo == null)
            this.roleBo = ((RoleBo) getBean("roleService"));
        return this.roleBo;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List getAuthList1() {
        return this.authList1;
    }

    public void setAuthList1(List authList1) {
        this.authList1 = authList1;
    }

    public List getAuthList2() {
        return this.authList2;
    }

    public void setAuthList2(List authList2) {
        this.authList2 = authList2;
    }

    public List getAuthList3() {
        return this.authList3;
    }

    public void setAuthList3(List authList3) {
        this.authList3 = authList3;
    }

    public List getAuthList4() {
        return this.authList4;
    }

    public void setAuthList4(List authList4) {
        this.authList4 = authList4;
    }

    public List getAuthList5() {
        return this.authList5;
    }

    public void setAuthList5(List authList5) {
        this.authList5 = authList5;
    }

    public List getAuthList6() {
        return this.authList6;
    }

    public void setAuthList6(List authList6) {
        this.authList6 = authList6;
    }

    public List getAuthList7() {
        return this.authList7;
    }

    public void setAuthList7(List authList7) {
        this.authList7 = authList7;
    }

    public List getAuthList8() {
        return this.authList8;
    }

    public void setAuthList8(List authList8) {
        this.authList8 = authList8;
    }

    public List getAuthList9() {
        return this.authList9;
    }

    public void setAuthList9(List authList9) {
        this.authList9 = authList9;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.action.RoleInitAction JD-Core Version: 0.5.4
 */