package com.hr.security.bo.impl;

import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.profile.domain.Position;
import com.hr.security.bo.AuthorityPosBo;
import com.hr.security.dao.AuthorityPosDao;
import com.hr.security.domain.AuthorityPos;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.ObjectUtil;
import com.hr.util.StringUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class AuthorityPosBoImpl implements AuthorityPosBo {
    private AuthorityPosDao authorityPosDao;

    public List<AuthorityPos> getAuthPosInfoList(String posId, String module) {
        DetachedCriteria dc = DetachedCriteria.forClass(AuthorityPos.class);
        dc.createAlias(AuthorityPos.PROP_AP_POS, AuthorityPos.PROP_AP_POS, 1);
        dc.createAlias(AuthorityPos.PROP_AP_POS + "." + Position.PROP_POSITION_PB_ID,
                       AuthorityPos.PROP_AP_POS + "." + Position.PROP_POSITION_PB_ID, 1);
        dc.createAlias(AuthorityPos.PROP_AP_DEPT, AuthorityPos.PROP_AP_DEPT, 1);
        dc.createAlias(AuthorityPos.PROP_AP_LOC, AuthorityPos.PROP_AP_LOC, 1);
        dc.add(Restrictions.eq(AuthorityPos.PROP_AP_POS, new Position(posId)));
        dc.add(Restrictions.or(Restrictions.eq(AuthorityPos.PROP_AP_MODULE, module), Restrictions
                .eq(AuthorityPos.PROP_AP_MODULE, "99")));

        List authPosList = this.authorityPosDao.findByCriteria(dc);

        if (authPosList.size() == 0)
            return null;

        return authPosList;
    }

    public List<AuthorityPos> getAuthPosList(String posId, String module) {
        DetachedCriteria dc = DetachedCriteria.forClass(AuthorityPos.class);
        dc.createAlias(AuthorityPos.PROP_AP_POS, AuthorityPos.PROP_AP_POS, 1);
        dc.add(Restrictions.eq(AuthorityPos.PROP_AP_POS, new Position(posId)));
        dc.add(Restrictions.or(Restrictions.eq(AuthorityPos.PROP_AP_MODULE, module), Restrictions
                .eq(AuthorityPos.PROP_AP_MODULE, "99")));

        List<AuthorityPos> authPosList = this.authorityPosDao.findByCriteria(dc);

        for (AuthorityPos authPos : authPosList) {
            if ((authPos.getApDept() != null) && (authPos.getApDept().getId() != null)) {
                if (!authPos.hasApAuthValidateDecrypt(authPos.getApDept().getId(), "", module)) {
                    authPosList.remove(authPos);
                }
            } else if (!authPos.hasApAuthValidateDecrypt("", authPos.getApLoc().getId(), module)) {
                authPosList.remove(authPos);
            }

        }

        if (authPosList.size() == 0)
            return null;

        return authPosList;
    }

    public String[][] getDeptAndLocInCharge(String posId, Integer module) {
        Set deptNos = new HashSet();
        Set locNos = new HashSet();

        if (module == null)
            return (String[][]) null;

        List<AuthorityPos> authPosList = getAuthPosList(posId, module.toString());
        if (authPosList != null) {
            for (AuthorityPos authPos : authPosList) {
                if ((authPos.getApDept() != null) && (authPos.getApDept().getId() != null))
                    deptNos.add(authPos.getApDept().getId());
                if ((authPos.getApLoc() != null) && (authPos.getApLoc().getId() != null)) {
                    locNos.add(authPos.getApLoc().getId());
                }
            }
        }

        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        String deptInCharge = deptBo.getDeptInCharge(posId);
        if (deptInCharge != null)
            deptNos.add(deptInCharge);

        String[] deptsInCharge = null;
        String[] locsInCharge = null;

        if (deptNos.size() > 0) {
            deptsInCharge = (String[]) deptNos.toArray(new String[deptNos.size()]);
            deptsInCharge = deptBo.getAllSubDeptIds(deptsInCharge);
        }
        if (locNos.size() > 0) {
            locsInCharge = (String[]) locNos.toArray(new String[locNos.size()]);
        }

        if ((((deptsInCharge == null) || (deptsInCharge.length == 0)))
                && (((locsInCharge == null) || (locsInCharge.length == 0)))) {
            return (String[][]) null;
        }
        return StringUtil.merge(new String[][] { deptsInCharge, locsInCharge });
    }

    public boolean checkDeptAndLocInCharge(String posId, Integer module, String dept, String loc) {
        String[] deptsInCharge = new String[0];
        String[] locsInCharge = new String[0];

        if (getDeptAndLocInCharge(posId, module) != null) {
            return false;
        }
        if ((deptsInCharge.length > 0) && (ObjectUtil.contains(deptsInCharge, dept))) {
            return true;
        }

        return (locsInCharge.length > 0) && (ObjectUtil.contains(locsInCharge, loc));
    }

    public boolean checkDeptAndLocInCharge(String posId, Integer module, String[] depts,
            String[] locs) {
        String[] deptsInCharge = new String[0];
        String[] locsInCharge = new String[0];

        if (getDeptAndLocInCharge(posId, module) != null) {
            return false;
        }
        for (int i = 0; i < depts.length; ++i) {
            if ((deptsInCharge.length > 0) && (ObjectUtil.contains(deptsInCharge, depts[i]))) {
                continue;
            }
            if ((locsInCharge.length <= 0) || (!ObjectUtil.contains(locsInCharge, locs[i]))) {
                return false;
            }
        }
        return true;
    }

    public AuthorityPosDao getAuthorityPosDao() {
        return this.authorityPosDao;
    }

    public void setAuthorityPosDao(AuthorityPosDao authorityPosDao) {
        this.authorityPosDao = authorityPosDao;
    }

    public boolean addAuthPos(AuthorityPos auth) {
        this.authorityPosDao.saveObject(auth);
        return true;
    }

    public boolean updateAuthPos(AuthorityPos auth) {
        this.authorityPosDao.updateObject(auth);
        return false;
    }

    public AuthorityPos getAuthPosById(String apId) {
        return (AuthorityPos) this.authorityPosDao.loadObject(AuthorityPos.class, apId, null,
                                                              new boolean[0]);
    }

    public String delAuthPos(String apId) {
        StringBuffer msg = new StringBuffer();
        try {
            this.authorityPosDao.deleteObject(AuthorityPos.class, apId);
        } catch (Exception e) {
            msg.append("系统异常：" + e.getMessage());
        }
        return msg.toString();
    }

    public AuthorityPos getAuthPosByDept(String posId, String deptId) {
        DetachedCriteria dc = DetachedCriteria.forClass(AuthorityPos.class);
        dc.add(Restrictions.eq(AuthorityPos.PROP_AP_POS, new Position(posId)));
        dc.add(Restrictions.eq(AuthorityPos.PROP_AP_DEPT, new Department(deptId)));
        List authList = this.authorityPosDao.findByCriteria(dc);
        if ((authList == null) || (authList.isEmpty())) {
            return null;
        }
        return (AuthorityPos) authList.get(0);
    }

    public AuthorityPos getAuthPosByLoc(String posId, String locId) {
        DetachedCriteria dc = DetachedCriteria.forClass(AuthorityPos.class);
        dc.add(Restrictions.eq(AuthorityPos.PROP_AP_POS, new Position(posId)));
        dc.add(Restrictions.eq(AuthorityPos.PROP_AP_LOC, new Location(locId)));
        List authList = this.authorityPosDao.findByCriteria(dc);
        if ((authList == null) || (authList.isEmpty())) {
            return null;
        }
        return (AuthorityPos) authList.get(0);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.bo.impl.AuthorityPosBoImpl JD-Core Version: 0.5.4
 */