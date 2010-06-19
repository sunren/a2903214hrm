package com.hr.security.bo.impl;

import com.hr.profile.domain.Employee;
import com.hr.security.bo.UserBo;
import com.hr.security.dao.UserDao;
import com.hr.security.domain.Authority;
import com.hr.security.domain.Role;
import com.hr.security.domain.Userinfo;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.MyTools;
import com.hr.util.Pager;
import com.hr.util.StringUtil;
import com.hr.util.SysConfigManager;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class UserBoImpl implements UserBo {
    private UserDao userDao;

    public UserDao getUserDao() {
        return this.userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean delUser(String userId) {
        if ((userId == null) || (userId.equals("")))
            return false;
        Userinfo u = (Userinfo) this.userDao.loadObject(Userinfo.class, userId, null,
                                                        new boolean[0]);
        if (u != null) {
            this.userDao.deleteObject(u);
            return true;
        }
        return false;
    }

    public List<Userinfo> listUser() {
        String[] fetch = { "employee" };
        return this.userDao.getObjects(Userinfo.class, fetch);
    }

    public boolean addUser(Userinfo user, Integer[] roleNo, String createrId) {
        if ((user == null) || (roleNo == null))
            return false;

        user.setUiStatus(Integer.valueOf(1));

        Date date = new Date();
        user.setUiCreateTime(date);
        user.setUiLastChangeTime(date);
        if (createrId != null) {
            user.setUiCreateBy(new Employee(createrId));
            user.setUiLastChangeBy(new Employee(createrId));
        }

        user.setUiRoleEncrypt(MyTools.arrayToStr(roleNo, ','));

        String newAuths = getAuthoritysByRole(MyTools.arrayToStr(roleNo, ','));
        user.setUiAuthEncrypt(newAuths);
        user.setUiIsGmHrCalc(newAuths);
        try {
            this.userDao.saveObject(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List getEmpNoByDepAuth(String depNo, String authString) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Userinfo.class);
        detachedCriteria.add(Restrictions
                .sqlRestriction("ui_emp_no in (select emp_no from employee  where emp_dept_no ='"
                        + depNo + "')"));

        String[] auths = authString.split("or");
        ArrayList list = new ArrayList();
        Set authSet = new HashSet();
        int authsLength = auths.length;
        for (int i = 0; i < authsLength; ++i) {
            list.add(auths[i].split(","));
        }
        String module = "";
        String condition = "";
        int listSize = list.size();
        for (int i = 0; i < listSize; ++i) {
            String[] con = (String[]) list.get(i);
            module = null;
            condition = null;
            if (con.length > 1) {
                module = con[0].trim();
                condition = con[1].trim();
                if ((condition != null) && (module != null)) {
                    DetachedCriteria temp = DetachedCriteria.forClass(Authority.class);
                    temp.add(Restrictions.and(Restrictions.eq("authorityModuleNo", module),
                                              Restrictions.eq("authorityConditionNo", Integer
                                                      .valueOf(Integer.parseInt(condition)))));

                    authSet.addAll(this.userDao.findByCriteria(temp));
                }
            } else if (con.length == 1) {
                module = con[0];
                if (module != null) {
                    DetachedCriteria temp = DetachedCriteria.forClass(Authority.class);
                    temp.add(Restrictions.eq("authorityModuleNo", module));
                    authSet.addAll(this.userDao.findByCriteria(temp));
                }
            }
        }
        List userinfoList = this.userDao.findByCriteria(detachedCriteria);
        List empNoList = new ArrayList();
        Set empNoSet = new HashSet();
        int userinfoSize = userinfoList.size();
        for (int i = 0; i < userinfoSize; ++i) {
            Userinfo userInfo = (Userinfo) userinfoList.get(i);
            List userAuthList = userInfo.getUiAuthList();
            Iterator itor = authSet.iterator();
            while (itor.hasNext()) {
                Authority auth = (Authority) itor.next();
                if (userAuthList.contains(auth.getId().toString())) {
                    empNoSet.add(userInfo.getId());
                    break;
                }
            }
        }
        empNoList.addAll(empNoSet);
        return empNoList;
    }

    public List<String> getEmpNoByAuth(String authString) {
        if (authString == null)
            return null;
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Userinfo.class);
        detachedCriteria.add(Restrictions.like(Userinfo.PROP_UI_IS_GM_HR, "%" + authString.trim()
                + "%"));
        List<Userinfo> list = this.userDao.findByCriteria(detachedCriteria);
        List result = new ArrayList();
        for (Userinfo userinfo : list) {
            result.add(userinfo.getId());
        }
        return result;
    }

    public List<Userinfo> getEmpByAuth(String authString) {
        if (authString == null)
            return null;
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Userinfo.class);
        detachedCriteria.setFetchMode("employee", FetchMode.JOIN);
        detachedCriteria.add(Restrictions.like(Userinfo.PROP_UI_IS_GM_HR, "%" + authString.trim()
                + "%"));
        List list = this.userDao.findByCriteria(detachedCriteria);
        return list;
    }

    public List searchAuthByEmpNo(String empNo) {
        DetachedCriteria detachedCriteriaA = DetachedCriteria.forClass(Userinfo.class);
        detachedCriteriaA.add(Restrictions.eq(Userinfo.PROP_ID, empNo.trim()));
        List result = this.userDao.findByCriteria(detachedCriteriaA);
        if (result.size() != 1)
            return null;
        Userinfo userinfo = (Userinfo) result.get(0);
        List authList = userinfo.getUiAuthList();
        if (authList == null)
            return null;
        DetachedCriteria detachedCriteriaB = DetachedCriteria.forClass(Authority.class);
        detachedCriteriaB.add(Restrictions.in(Authority.PROP_ID, authList));
        return this.userDao.findByCriteria(detachedCriteriaB);
    }

    public boolean existUserByName(String userName) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Userinfo.class);
        detachedCriteria.add(Restrictions.eq(Userinfo.PROP_UI_USERNAME, userName));
        List list = this.userDao.findByCriteria(detachedCriteria);
        return (list != null) && (list.size() == 1);
    }

    public Userinfo getUserByName(String userName) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Userinfo.class);
        detachedCriteria.add(Restrictions.eq(Userinfo.PROP_UI_USERNAME, userName));
        List list = this.userDao.findByCriteria(detachedCriteria);
        if ((list == null) || (list.size() < 1))
            return null;
        return (Userinfo) list.get(0);
    }

    public Userinfo getUserById(String userId) {
        String[] fetch = new String[0];
        return (Userinfo) this.userDao.loadObject(Userinfo.class, userId, fetch, new boolean[0]);
    }

    public List<Userinfo> getUserByIds(String[] ids) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Userinfo.class);
        detachedCriteria.add(Restrictions.in(Userinfo.PROP_ID, ids));
        return this.userDao.findByCriteria(detachedCriteria);
    }

    public String updateUser(Userinfo user, Integer[] roleNo, String createrId) {
        Userinfo oldUser = getUserById(user.getId());
        if ((!oldUser.getUiUsername().equals(user.getUiUsername()))
                && (existUserByName(user.getUiUsername())))
            return "已存在该用户各1�7!";

        oldUser.setUiUsername(user.getUiUsername());
        oldUser.setUiStatus(user.getUiStatus());
        if (user.getUiStatus() == null) {
            oldUser.setUiStatus(Integer.valueOf(1));
        }

        if (createrId != null) {
            oldUser.setUiLastChangeBy(new Employee(createrId));
        }
        oldUser.setUiLastChangeTime(new Date());

        oldUser.setUiRoleEncrypt(MyTools.arrayToStr(roleNo, ','));

        String newAuths = getAuthoritysByRole(MyTools.arrayToStr(roleNo, ','));
        oldUser.setUiAuthEncrypt(newAuths);
        oldUser.setUiIsGmHrCalc(newAuths);

        this.userDao.updateObject(oldUser);
        return "yes";
    }

    public String updateUserAuth(Userinfo user, String auth) {
        try {
            user.setUiAuthEncrypt(auth);
            user.setUiIsGmHrCalc(auth);

            this.userDao.updateObject(user);
            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "no";
    }

    public String doLogin(Userinfo user, String password, String macAddress, String loginModel) {
        Userinfo u = getUserByName(user.getUiUsername());
        if (u == null)
            return "无此用户＄1�7";
        user.setId(u.getId());
        user.setUiPasswordEncrypt(StringUtil.encodePassword(password));
        if (u.getUiPasswordDecrypt().trim().compareTo(user.getUiPasswordDecrypt().trim()) != 0)
            return "密码错误＄1�7";
        if (u.getUiStatus().intValue() == 0)
            return "用户已被停用＄1�7";

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.eq("id", u.getId()));
        List list = this.userDao.findByCriteria(detachedCriteria);
        if (((Employee) list.get(0)).getEmpStatus().intValue() == 0)
            return "离职员工不能登录系统！";
        HttpServletRequest request;
        if (ServletActionContext.getRequest() != null) {
            request = ServletActionContext.getRequest();
        } else {
            request = WebContextFactory.get().getHttpServletRequest();
        }
        String loginIp = request.getRemoteAddr();
        if ("127.0.0.1".equals(loginIp))
            try {
                loginIp = InetAddress.getLocalHost().toString()
                        .substring(InetAddress.getLocalHost().toString().lastIndexOf("/") + 1);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        user.setUiLastLoginIp(loginIp);

        if (!loginModel.equals("demo")) {
            String mac = macAddress;

            Integer level = u.getUiLevelRestrict();

            if ((level == null) || (level.intValue() == 0)) {
                u.setUiIpRestrict(loginIp);
            } else if ((level != null) && (level.intValue() == 1)) {
                if (!ipIsValid(u.getUiIpRestrict(), user.getUiLastLoginIp()))
                    return "您的登陆IP地址超出限制范围＄1�7";

            } else if ((level != null) && (level.intValue() == 2)) {
                if ((((mac == null) || (mac.length() < 17)))
                        && ("00-00-00-00-00-00".equals(u.getUiMacRestrict())))
                    return "mac";

                if ((u.getUiMacRestrict() == null)
                        || ("00-00-00-00-00-00".equals(u.getUiMacRestrict()))) {
                    u.setUiMacRestrict(mac);
                } else if (!macIsValid(u.getUiMacRestrict(), mac))
                    return "您的登陆机器地址超出限制范围＄1�7";

            } else if ((level != null) && (level.intValue() == 3)) {
                boolean ipRight = ipIsValid(u.getUiIpRestrict(), user.getUiLastLoginIp());
                if ((!ipRight) && (((mac == null) || (mac.length() < 17)))
                        && ("00-00-00-00-00-00".equals(u.getUiMacRestrict())))
                    return "mac";
                if ((!ipRight)
                        && (((u.getUiMacRestrict() == null) || ("00-00-00-00-00-00".equals(u
                                .getUiMacRestrict()))))) {
                    u.setUiMacRestrict(mac);
                } else if ((!ipRight) && (!macIsValid(u.getUiMacRestrict(), mac)))
                    return "您的登陆IP地址和机器地坄1�7均超出限制范围！";
            }
        }

        Date date = u.getUiCurrentLoginTime();
        if (date != null)
            u.setUiLastLoginTime(date);
        else
            u.setUiLastLoginTime(new Date());
        u.setUiCurrentLoginTime(new Date());
        u.setUiLastLoginIp(user.getUiLastLoginIp());
        this.userDao.updateObject(u);
        return "yes";
    }

    public boolean updatePassword(String userId, String password2) {
        Userinfo user = (Userinfo) this.userDao.loadObject(Userinfo.class, userId, null,
                                                           new boolean[0]);
        if (user == null)
            return false;
        user.setUiPasswordEncrypt(StringUtil.encodePassword(password2));
        this.userDao.updateObject(user);
        return true;
    }

    public boolean updateLimit(Userinfo user) {
        this.userDao.updateObject(user);
        return true;
    }

    public boolean updateStatus(String userId, int status, String createrId) {
        Userinfo user = (Userinfo) this.userDao.loadObject(Userinfo.class, userId, null,
                                                           new boolean[0]);
        if (user == null)
            return false;
        if (createrId != null) {
            user.setUiLastChangeBy(new Employee(createrId));
            user.setUiLastChangeTime(new Date());
        }
        user.setUiStatus(Integer.valueOf(status));
        return true;
    }

    public List getObjects(Class clas) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(clas);
        return this.userDao.findByCriteria(detachedCriteria);
    }

    public List searchUserList(String empName, Userinfo user, String depart, String manager,
            String role, Pager page) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Userinfo.class);

        if ((empName != null) && (empName.length() > 0)) {
            detachedCriteria
                    .add(Restrictions
                            .or(
                                Restrictions
                                        .sqlRestriction("ui_emp_no in (select emp_no from employee where emp_distinct_no like '%"
                                                + empName.trim() + "%')"),
                                Restrictions
                                        .sqlRestriction("ui_emp_no in (select emp_no from employee where emp_name like '%"
                                                + empName.trim() + "%')")));
        }

        if ((user != null) && (user.getUiLevelRestrict() != null)) {
            detachedCriteria.add(Restrictions.eq("uiLevelRestrict", Integer.valueOf(user
                    .getUiLevelRestrict().intValue())));
        }

        if ((depart != null) && (depart.trim().length() > 0)) {
            detachedCriteria
                    .add(Restrictions
                            .sqlRestriction(
                                            "(ui_emp_no in (select emp_no from employee, department where employee.emp_dept_no = department.department_no and department.department_no = ?))",
                                            depart, Hibernate.STRING));
        }

        if ((manager != null) && (manager.trim().length() > 0)) {
            detachedCriteria
                    .add(Restrictions
                            .sqlRestriction(
                                            "(ui_emp_no in (select emp_no from employee where emp_sup_no = ?))",
                                            manager, Hibernate.STRING));
        }

        if (page == null) {
            detachedCriteria.addOrder(Order.desc("uiCurrentLoginTime"));
            return this.userDao.findByCriteria(detachedCriteria);
        }
        checkOrderMethod(detachedCriteria, page.getOrder());
        if ((role == null) || (role.length() < 1)) {
            splitPage(detachedCriteria, page);
            List result = this.userDao.findByCriteria(detachedCriteria, page.getPageSize(), page
                    .getCurrentPage());
            return result;
        }

        List result = this.userDao.findByCriteria(detachedCriteria);
        List resultList = new ArrayList();
        int resultSize = result.size();
        for (int i = 0; i < resultSize; ++i) {
            List array = ((Userinfo) result.get(i)).getUiRoleList();
            int size = array.size();
            for (int j = 0; j < size; ++j) {
                if (((String) array.get(j)).trim().equals(role.trim())) {
                    resultList.add((Userinfo) result.get(i));
                    break;
                }
            }
        }
        listSplitPage(resultList, page);
        int readBegain = (page.getCurrentPage() - 1) * page.getPageSize();
        int readEnd = (page.getCurrentPage() * page.getPageSize() < resultList.size()) ? page
                .getCurrentPage()
                * page.getPageSize() : resultList.size();

        List pagedList = new ArrayList();
        for (int i = readBegain; i < readEnd; ++i)
            pagedList.add(resultList.get(i));
        return pagedList;
    }

    private void listSplitPage(List resultList, Pager page) {
        int size = resultList.size();

        SysConfigManager sysConfigManager = DatabaseSysConfigManager.getInstance();
        int pageSize = Integer.valueOf(sysConfigManager.getProperty("sys.split.pages")).intValue();
        page.init(size, pageSize);

        if ((page.getOperate() != null) && ("previous".equalsIgnoreCase(page.getOperate()))) {
            page.previous();
        }
        if ((page.getOperate() != null) && ("next".equalsIgnoreCase(page.getOperate()))) {
            page.next();
        }
        if ((page.getOperate() != null) && ("first".equalsIgnoreCase(page.getOperate()))) {
            page.first();
        }
        if ((page.getOperate() != null) && ("last".equalsIgnoreCase(page.getOperate())))
            page.last();
    }

    private void splitPage(DetachedCriteria detachedCriteria, Pager page) {
        int size = this.userDao.findRowCountByCriteria(detachedCriteria);

        SysConfigManager sysConfigManager = DatabaseSysConfigManager.getInstance();
        int pageSize = Integer.valueOf(sysConfigManager.getProperty("sys.split.pages")).intValue();
        detachedCriteria.setProjection(null);

        page.init(size, pageSize);

        if ((page.getOperate() != null) && ("previous".equalsIgnoreCase(page.getOperate()))) {
            page.previous();
        }
        if ((page.getOperate() != null) && ("next".equalsIgnoreCase(page.getOperate()))) {
            page.next();
        }
        if ((page.getOperate() != null) && ("first".equalsIgnoreCase(page.getOperate()))) {
            page.first();
        }
        if ((page.getOperate() != null) && ("last".equalsIgnoreCase(page.getOperate())))
            page.last();
    }

    public void checkOrderMethod(DetachedCriteria detachedCriteria, String order) {
        if ((order != null) && (order.trim().length() > 1) && (order.indexOf('-') != -1)) {
            String[] pram = order.split("-");
            if ((pram[0] == null) || (pram[0].length() < 1)) {
                detachedCriteria.addOrder(Order.desc("uiCurrentLoginTime"));
                return;
            }
            String[] fetch = pram[0].split("\\.");
            String orde = pram[0];
            if (fetch.length > 1) {
                String str = "";
                String fetc = "";
                for (int len = 0; len < fetch.length - 1; ++len) {
                    detachedCriteria.createAlias(fetc + fetch[len], "ord" + len);
                    fetc = fetc + fetch[len] + ".";
                    str = "ord" + len + ".";
                }
                orde = str + pram[0].substring(pram[0].lastIndexOf(".") + 1);
            }
            if ((pram.length == 2) && (pram[1].equalsIgnoreCase("up"))) {
                detachedCriteria.addOrder(Order.asc(orde));
            } else
                detachedCriteria.addOrder(Order.desc(orde));
        } else {
            detachedCriteria.addOrder(Order.desc("uiCurrentLoginTime"));
        }
    }

    public boolean checkAuthModule(Userinfo user, String authModule) {
        if (user == null)
            return false;
        List userAuth = user.getUiAuthList();
        if (userAuth == null)
            return false;
        DetachedCriteria detachedCriteriaA = DetachedCriteria.forClass(Authority.class);
        detachedCriteriaA.add(Restrictions.eq("authorityModuleNo", authModule));
        List<Authority> resultAuth = this.userDao.findByCriteria(detachedCriteriaA);
        if ((resultAuth == null) || (resultAuth.isEmpty()))
            return false;
        for (Authority auth : resultAuth) {
            if (userAuth.contains(auth.getId().toString()))
                return true;
        }
        return false;
    }

    public boolean checkAuthModule(Userinfo user, String authModule, String authCondition) {
        if (user == null)
            return false;
        List userAuth = user.getUiAuthList();
        if (userAuth == null)
            return false;
        DetachedCriteria detachedCriteriaA = DetachedCriteria.forClass(Authority.class);
        detachedCriteriaA.add(Restrictions.eq(Authority.PROP_AUTHORITY_MODULE_NO, authModule));
        detachedCriteriaA.add(Restrictions.eq(Authority.PROP_AUTHORITY_CONDITION_NO, Integer
                .valueOf(Integer.parseInt(authCondition))));
        List resultAuth = this.userDao.findByCriteria(detachedCriteriaA);
        if ((resultAuth == null) || (resultAuth.isEmpty()))
            return false;

        return userAuth.contains(((Authority) resultAuth.get(0)).getId().toString());
    }

    public int getAdminCount(String authModule) {
        int count = 0;
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Userinfo.class);
        List result = this.userDao.findByCriteria(detachedCriteria);

        DetachedCriteria detachedCriteriaA = DetachedCriteria.forClass(Authority.class);
        detachedCriteriaA.add(Restrictions.eq("authorityModuleNo", authModule));
        List resultAuth = this.userDao.findByCriteria(detachedCriteriaA);
        if ((resultAuth == null) || (resultAuth.isEmpty()))
            return 10;

        int resultAuthSize = resultAuth.size();
        int resultSize = result.size();
        for (int i = 0; i < resultSize; ++i) {
            List auths = ((Userinfo) result.get(i)).getUiAuthList();
            for (int j = 0; j < resultAuthSize; ++j) {
                String authId = ((Authority) resultAuth.get(0)).getId().toString();
                if (auths.contains(authId)) {
                    ++count;
                    break;
                }
            }
        }
        return count;
    }

    public String getAuthoritysByRole(String roles) {
        String[] roleNo = roles.split(",");
        if (roleNo == null)
            return null;
        List roleNoList = new ArrayList();
        for (int i = 0; i < roleNo.length; ++i) {
            roleNoList.add(Integer.valueOf(Integer.parseInt(roleNo[i])));
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
        detachedCriteria.add(Restrictions.in("roleNo", roleNoList));
        List roleList = this.userDao.findByCriteria(detachedCriteria);
        if ((roleList == null) || (roleList.size() == 0))
            return null;
        Iterator iter = roleList.iterator();
        Set authSet = new HashSet();
        while (iter.hasNext()) {
            String[] auths = ((Role) iter.next()).getRoleAuthority().split(",");
            for (int i = 0; i < auths.length; ++i) {
                authSet.add(auths[i]);
            }
        }
        if (authSet.size() == 0)
            return null;
        String authString = "";
        Iterator authItor = authSet.iterator();
        while (authItor.hasNext()) {
            authString = authString + authItor.next() + ",";
        }
        if (authString.length() == 0)
            return null;
        authString = authString.substring(0, authString.length() - 1);
        return authString;
    }

    public void save(Userinfo userinfo) {
        this.userDao.saveObject(userinfo);
    }

    public boolean updatePasswordBy(String userId, String userName, String password, String creatBy) {
        Userinfo user = (Userinfo) this.userDao.loadObject(Userinfo.class, userId, null,
                                                           new boolean[0]);
        if (user == null)
            return false;
        if (userName != null)
            user.setUiUsername(userName);
        user.setUiPasswordEncrypt(StringUtil.encodePassword(password));
        if (creatBy != null) {
            user.setUiLastChangeBy(new Employee(creatBy));
            user.setUiLastChangeTime(new Date());
        }
        this.userDao.updateObject(user);
        return true;
    }

    public boolean isRoleNoHasAuth(Integer[] roleNo, String moduleId) {
        List roleNoList = new ArrayList();
        for (int i = 0; i < roleNo.length; ++i) {
            roleNoList.add(roleNo[i]);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
        detachedCriteria.add(Restrictions.in("roleNo", roleNoList));
        List roleList = this.userDao.findByCriteria(detachedCriteria);
        if ((roleList == null) || (roleList.size() == 0))
            return false;
        Iterator iter = roleList.iterator();
        Set authSet = new HashSet();
        while (iter.hasNext()) {
            String[] auths = ((Role) iter.next()).getRoleAuthority().split(",");
            for (int i = 0; i < auths.length; ++i) {
                authSet.add(Integer.valueOf(Integer.parseInt(auths[i])));
            }
        }
        if (authSet.size() == 0)
            return false;
        List list = new ArrayList();
        list.addAll(authSet);
        DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(Authority.class);
        detachedCriteria2.add(Restrictions.in("id", list));
        List authList = this.userDao.findByCriteria(detachedCriteria2);
        if ((authList == null) || (authList.size() == 0))
            return false;
        Iterator authIter = authList.iterator();
        while (authIter.hasNext()) {
            String authString = ((Authority) authIter.next()).getAuthorityModuleNo();
            if (moduleId.equals(authString))
                return true;
        }
        return false;
    }

    public boolean ipIsValid(String rangeIP, String loginIP) {
        boolean isvalid = false;
        if (rangeIP == null)
            return true;
        if ((rangeIP.indexOf("-") == -1) && (rangeIP.indexOf("*") == -1)) {
            if (loginIP.equals(rangeIP)) {
                isvalid = true;
            }
        } else if ((rangeIP.indexOf("-") == -1) && (rangeIP.indexOf("*") == rangeIP.length() - 1)) {
            String[] rangeIPs = replacePoint(rangeIP).split("-");
            String[] loginIPs = replacePoint(loginIP).split("-");
            for (int i = 0; i < 3; ++i) {
                if (!loginIPs[i].equals(rangeIPs[i])) {
                    isvalid = false;
                    break;
                }

                isvalid = true;
            }

        } else if (rangeIP.indexOf("-") != -1) {
            String[] rangeIPmin = replacePoint(rangeIP.split("-")[0]).split("-");
            String[] rangeIPmax = replacePoint(rangeIP.split("-")[1]).split("-");
            String[] logip = replacePoint(loginIP).split("-");
            String rangeIP1 = "";
            String rangeIP2 = "";
            loginIP = "";
            for (int i = 0; i < 4; ++i) {
                if (rangeIPmin[i].length() == 1) {
                    rangeIPmin[i] = ("00" + rangeIPmin[i]);
                } else if (rangeIPmin[i].length() == 2) {
                    rangeIPmin[i] = ("0" + rangeIPmin[i]);
                }
                rangeIP1 = rangeIP1 + rangeIPmin[i];

                if (rangeIPmax[i].length() == 1) {
                    rangeIPmax[i] = ("00" + rangeIPmax[i]);
                } else if (rangeIPmax[i].length() == 2) {
                    rangeIPmax[i] = ("0" + rangeIPmax[i]);
                }
                rangeIP2 = rangeIP2 + rangeIPmax[i];
                if (logip[i].length() == 1) {
                    logip[i] = ("00" + logip[i]);
                } else if (logip[i].length() == 2) {
                    logip[i] = ("0" + logip[i]);
                }
                loginIP = loginIP + logip[i];
            }
            if ((loginIP.compareTo(rangeIP1) > 0) && (loginIP.compareTo(rangeIP2) < 0)) {
                isvalid = true;
            }
        }

        return isvalid;
    }

    public boolean macIsValid(String macRestrict, String loginMac) {
        return macRestrict.equals(loginMac);
    }

    private String replacePoint(String s) {
        String result = "";
        char[] ss = s.toCharArray();
        for (int i = 0; i < ss.length; ++i) {
            if (ss[i] == '.') {
                ss[i] = '-';
            }
            result = result + ss[i];
        }
        return result;
    }

    public String updateSignFree(String userId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Userinfo.class);
        detachedCriteria.add(Restrictions.eq(Userinfo.PROP_UI_USERNAME, userId));
        List list = this.userDao.findByCriteria(detachedCriteria);
        if ((list == null) || (list.isEmpty()))
            return "noUser";

        Userinfo userinfo = (Userinfo) list.get(0);
        if (userinfo.getUiStatus().intValue() == 0)
            return "hasStoped";

        detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.eq(Employee.PROP_EMP_DISTINCT_NO, userId));
        list = this.userDao.findByCriteria(detachedCriteria);
        Employee employee = (Employee) list.get(0);
        if (employee.getEmpStatus().intValue() == 0)
            return "hasLeave";

        HttpServletRequest request = ServletActionContext.getRequest();
        if (request == null)
            request = WebContextFactory.get().getHttpServletRequest();
        String loginIp = request.getRemoteAddr();
        if ("127.0.0.1".equals(loginIp)) {
            try {
                String tempIp = InetAddress.getLocalHost().toString();
                loginIp = tempIp.substring(tempIp.lastIndexOf("/") + 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Integer level = userinfo.getUiLevelRestrict();
        if ((level.intValue() == 1) && (!ipIsValid(userinfo.getUiIpRestrict(), loginIp)))
            return "ipError";
        userinfo.setUiLastLoginIp(loginIp);

        Date date = userinfo.getUiCurrentLoginTime();
        if (date != null)
            userinfo.setUiLastLoginTime(date);
        else
            userinfo.setUiLastLoginTime(new Date());
        userinfo.setUiCurrentLoginTime(new Date());
        userinfo.setUiLastLoginIp(userinfo.getUiLastLoginIp());
        this.userDao.updateObject(userinfo);
        return "success";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.bo.impl.UserBoImpl JD-Core Version: 0.5.4
 */