package com.hr.examin.action;

import com.hr.base.DWRUtil;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.profile.domain.Position;
import com.hr.security.bo.AuthorityPosBo;
import com.hr.security.domain.AuthorityPos;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.MyTools;

public class AuthorityPosAction {
    private static final String EXISTED = "EXISTED";
    private static final String NOAUTH = "NOAUTH";
    private static final String FAIL = "FAIL";

    public String addAuthPos(String posId, String deptId, String locId, String apType) {
        String auth = DWRUtil.checkAuth("authorityPosManage", "authorityPos");
        if (!"HR".equals(auth)) {
            return "NOAUTH";
        }

        AuthorityPosBo bo = (AuthorityPosBo) SpringBeanFactory.getBean("authPosService");

        AuthorityPos ap = new AuthorityPos();
        ap.setId(MyTools.getUUID());
        Position apPos = new Position(posId);
        ap.setApPos(apPos);

        if (apType.equals("0")) {
            if (bo.getAuthPosByDept(posId, deptId) != null)
                return "EXISTED";
            Department apDept = new Department(deptId);
            ap.setApDept(apDept);
            ap.setApAuthValidateEncrypt(deptId, "", String.valueOf(4));
        } else if (apType.equals("1")) {
            if (bo.getAuthPosByLoc(posId, locId) != null)
                return "EXISTED";
            Location apLoc = new Location(locId);
            ap.setApLoc(apLoc);
            ap.setApAuthValidateEncrypt("", locId, String.valueOf(4));
        }

        ap.setApModule(String.valueOf(4));
        try {
            if (bo.addAuthPos(ap))
                return ap.getId();
        } catch (Exception e) {
            return "FAIL";
        }

        return "";
    }

    public String updateAuthPos(String id, String deptId, String locId, String apType) {
        String auth = DWRUtil.checkAuth("authorityPosManage", "authorityPos");
        if (!"HR".equals(auth)) {
            return "NOAUTH";
        }

        AuthorityPosBo bo = (AuthorityPosBo) SpringBeanFactory.getBean("authPosService");
        AuthorityPos ap = bo.getAuthPosById(id);

        if (apType.equals("0")) {
            Department apDept = new Department(deptId);
            ap.setApDept(apDept);
            ap.setApLoc(null);
            ap.setApAuthValidateEncrypt(deptId, "", String.valueOf(4));
        }

        if (apType.equals("1")) {
            Location apLoc = new Location(locId);
            ap.setApLoc(apLoc);
            ap.setApDept(null);
            ap.setApAuthValidateEncrypt("", locId, String.valueOf(4));
        }
        try {
            bo.updateAuthPos(ap);
        } catch (Exception e) {
            return "FAIL";
        }

        return "";
    }

    public String deleteAuthPos(String apId) {
        String auth = DWRUtil.checkAuth("authorityPosManage", "authorityPos");
        if (!"HR".equals(auth)) {
            return "NOAUTH";
        }

        AuthorityPosBo bo = (AuthorityPosBo) SpringBeanFactory.getBean("authPosService");
        try {
            bo.delAuthPos(apId);
        } catch (Exception e) {
            return "FAIL";
        }
        return "";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.action.AuthorityPosAction JD-Core Version: 0.5.4
 */