package com.hr.security.bo.impl;

import com.hr.configuration.bo.IClientBO;
import com.hr.security.bo.RoleBo;
import com.hr.security.bo.UserBo;
import com.hr.security.dao.RoleDao;
import com.hr.security.domain.Authority;
import com.hr.security.domain.Role;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.StringUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class RoleBoImpl implements RoleBo {
    private RoleDao roleDao;

    public RoleDao getRoleDao() {
        return this.roleDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Role getRole(String roleId) {
        return (Role) this.roleDao.loadObject(Role.class, roleId, null, new boolean[0]);
    }

    public boolean isHasAuth(String empNo, Integer moduleId) {
        UserBo userBo = (UserBo) SpringBeanFactory.getBean("userService");
        Userinfo user = userBo.getUserById(empNo);
        if (user == null) {
            return false;
        }
        List auths = user.getUiAuthList();
        if (auths == null) {
            return false;
        }
        List authId = new ArrayList();
        while (auths.size() > 0) {
            authId.add(Integer.valueOf(Integer.parseInt(((String) auths.get(0)).trim())));
            auths.remove(0);
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Authority.class);
        detachedCriteria.add(Restrictions.in("id", authId));
        List authsList = this.roleDao.findByCriteria(detachedCriteria);
        String modelNo = null;
        while (authsList.size() > 0) {
            modelNo = ((Authority) authsList.get(0)).getAuthorityModuleNo();
            if ((modelNo != null) && (modelNo.equals(String.valueOf(moduleId))))
                return true;
            authsList.remove(0);
        }
        return false;
    }

    public List getRoleList() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
        detachedCriteria.addOrder(Order.asc("roleSortId"));
        detachedCriteria.addOrder(Order.asc("roleName"));
        return this.roleDao.findByCriteria(detachedCriteria);
    }

    public List<Role> getRoleList(Integer[] roles, String type) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
        if (type == "SortId")
            detachedCriteria.add(Restrictions.in("roleSortId", roles));
        else if (type == "No")
            detachedCriteria.add(Restrictions.in("roleNo", roles));
        detachedCriteria.addOrder(Order.asc("roleSortId"));
        detachedCriteria.addOrder(Order.asc("roleName"));
        return this.roleDao.findByCriteria(detachedCriteria);
    }

    public boolean addRole(Role role) {
        if ((role == null) || (role.getRoleName() == null) || (role.getRoleName().equals(""))) {
            return false;
        }
        this.roleDao.saveObject(role);
        return true;
    }

    public String addRole(Role role, Integer[] authId) {
        try {
            Set authorities = new HashSet();
            for (Integer s : authId) {
                authorities.add(String.valueOf(s));
            }
            Iterator itor = authorities.iterator();
            String roleAuthority = "";
            while (itor.hasNext()) {
                roleAuthority = roleAuthority + itor.next() + ",";
            }
            if (roleAuthority.length() == 0)
                return "noAuth";
            if (roleAuthority.length() > 255) {
                return "authOver";
            }
            int roleId = getSuitableNo().intValue();
            if (roleId > 99)
                return "roleOver";
            roleAuthority = roleAuthority.substring(0, roleAuthority.length() - 1);
            role.setRoleAuthority(roleAuthority);
            role.setRoleNo(roleId);
            this.roleDao.saveObject(role);
            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "no";
    }

    public String delRole(Integer roleNo, UserBo userBo) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
        detachedCriteria.add(Restrictions.eq("roleNo", roleNo));
        List roleList = this.roleDao.findByCriteria(detachedCriteria);
        if ((roleList == null) || (roleList.size() != 1)) {
            return "ref";
        }
        Role oldRole = (Role) roleList.get(0);
        DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(Userinfo.class);
        List userinfoList = this.roleDao.findByCriteria(detachedCriteria2);
        int userinfoSize = userinfoList.size();
        for (int i = 0; i < userinfoSize; ++i) {
            Userinfo userinfo = (Userinfo) userinfoList.get(i);
            List userRoles = userinfo.getUiRoleList();
            if ((userRoles != null) && (userRoles.contains(String.valueOf(roleNo)))) {
                return "err";
            }
        }
        this.roleDao.deleteObject(oldRole);
        return "yes";
    }

    public String updateRole(Role role, Integer[] authId, UserBo userBo) {
        try {
            Role oldRole = getRole(role.getId());

            if (!oldRole.getRoleName().equals(role.getRoleName())) {
                Role r = this.roleDao.findRoleByName(role.getRoleName());
                if (r != null) {
                    return "exist";
                }
            }

            oldRole.setRoleName(role.getRoleName());
            oldRole.setRoleDesc(role.getRoleDesc());
            oldRole.setRoleSortId(role.getRoleSortId());

            String authority = "";
            if ((authId != null) && (authId.length > 0)) {
                for (int i = 0; i < authId.length; ++i) {
                    authority = authority + String.valueOf(authId[i]) + ",";
                }
            }
            if (authority.length() == 0)
                return "noAuth";
            authority = authority.substring(0, authority.length() - 1);
            if (authority.length() > 255)
                return "authOver";

            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Userinfo.class);
            List<Userinfo> uiList = this.roleDao.findByCriteria(detachedCriteria);

            Integer[] users = { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0),
                    Integer.valueOf(0) };

            //      if (uiList.size() > 0)
            //      {
            //        for (Userinfo ui : uiList) {
            //          if (ui.getUiStatus().intValue() != 0);
            //          if (!ui.getUiRoleList().contains(String.valueOf(oldRole.getRoleNo()))) {
            //            continue;
            //          }
            //          Integer[] arrayOfInteger1 = users; Object localObject = arrayOfInteger1[3]; localInteger1 = arrayOfInteger1[3] =  = Integer.valueOf(arrayOfInteger1[3].intValue() + 1);
            //          String authType = StringUtil.authStringToType(ui.getUiAuthDecrypt());
            //          if (authType == "USERADM") {
            //            localObject = users; localInteger1 = localObject[0]; localInteger2 = localObject[0] =  = Integer.valueOf(localObject[0].intValue() + 1);
            //          }
            //
            //          if (authType == "USERMGR") {
            //            localObject = users; localInteger1 = localObject[1]; localInteger2 = localObject[1] =  = Integer.valueOf(localObject[1].intValue() + 1);
            //          }
            //
            //          localObject = users; localInteger1 = localObject[2]; Integer localInteger2 = localObject[2] =  = Integer.valueOf(localObject[2].intValue() + 1);
            //        }
            //      }
            String newAuthType = StringUtil.authStringToType(authority);
            IClientBO clientLimit = (IClientBO) SpringBeanFactory.getBean("clientBo");
            if ((newAuthType == "USERADM") && (users[1].intValue() + users[2].intValue() > 0)) {
                String limit = clientLimit.checkUserLimit(newAuthType, users[1].intValue()
                        + users[2].intValue());
                if (!"success".equals(limit)) {
                    return "no";
                }
            }
            if ((newAuthType == "USERMGR") && (users[2].intValue() > 0)) {
                String limit = clientLimit.checkUserLimit(newAuthType, users[1].intValue()
                        + users[2].intValue());
                if (!"success".equals(limit)) {
                    return "no";
                }

            }

            oldRole.setRoleAuthority(authority);
            this.roleDao.updateObject(oldRole);
            for (int i = 0; i < uiList.size(); ++i) {
                Userinfo userinfo = (Userinfo) uiList.get(i);
                List<String> userRoles = userinfo.getUiRoleList();
                if (userRoles.contains(String.valueOf(oldRole.getRoleNo()))) {
                    String newAuths = userBo.getAuthoritysByRole(userinfo.getUiRoleDecrypt());
                    userBo.updateUserAuth(userinfo, newAuths);
                }
            }

            return "yes";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String) "no";
    }

    public boolean findRoleByName(String roleName) {
        if ((roleName == null) || (roleName.equals(""))) {
            return false;
        }
        Role r = this.roleDao.findRoleByName(roleName);
        return r != null;
    }

    public boolean findRoleBySortid(int sortId) {
        return this.roleDao.findRoleBySortid(sortId);
    }

    public List getAllAuthoritys(List<Integer> list) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Authority.class);
        if (list != null) {
            detachedCriteria.add(Restrictions.in("id", list));
        }
        detachedCriteria.addOrder(Order.asc("authorityModuleNo"));
        detachedCriteria.addOrder(Order.desc("authorityConditionNo"));
        return this.roleDao.findByCriteria(detachedCriteria);
    }

    public Integer[] ListToInteger(List<String> list) {
        int size = list.size();
        if (size < 1)
            return null;
        Integer[] integer = new Integer[size];
        String listContent = "";
        for (int i = 0; i < size; ++i) {
            listContent = ((String) list.get(i)).trim();
            if (!listContent.equals(""))
                integer[i] = Integer.valueOf(Integer.parseInt(listContent));
        }
        return integer;
    }

    public Integer getSuitableNo() {
        for (int roleNo = 1; roleNo <= 99; ++roleNo) {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
            detachedCriteria.add(Restrictions.eq("roleNo", Integer.valueOf(roleNo)));
            int maxNoList = this.roleDao.findRowCountByCriteria(detachedCriteria);
            if (maxNoList <= 0)
                return Integer.valueOf(roleNo);
        }
        return Integer.valueOf(2147483647);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.bo.impl.RoleBoImpl JD-Core Version: 0.5.4
 */