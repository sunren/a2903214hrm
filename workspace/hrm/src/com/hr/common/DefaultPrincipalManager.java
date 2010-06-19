package com.hr.common;

import com.hr.profile.bo.IEmployeeBo;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class DefaultPrincipalManager extends AbstractPrincipalManager {
    private static final String DEFAULT_ALL_ORG_HEADERS = "'braheader', 'bradeputy', 'deptheader', 'deptdeputy', 'buheader', 'budeputy', 'grpheader', 'grpdeputy'";

    public List<String> queryEmployeesInChargeByOrgheads(String empNo) {
        if (StringUtils.isEmpty(empNo)) {
            return new ArrayList(0);
        }

        String hql = "select distinct orgheadsOrgNo from Orgheads where orgheadsEmpNo='" + empNo
                + "'";

        hql = hql
                + " and orgheadsResponsibility in ('braheader', 'bradeputy', 'deptheader', 'deptdeputy', 'buheader', 'budeputy', 'grpheader', 'grpdeputy')";
        IEmployeeBo empBo = getEmployeeBo();
        List list = empBo.exeHqlList(hql, new int[0]);
        if ((list != null) && (list.size() > 0)) {
            String ids = listToString(list);
            String sql = "select id from Employee where empDeptNo.id in (" + ids + ")";
            sql = sql + " or branch.id in (" + ids + ")";
            sql = sql + " or empGroup.groupNo in (" + ids + ")";
            sql = sql + " or empBuNo.id in(" + ids + ")";
            return empBo.exeHqlList(sql, new int[0]);
        }
        return new ArrayList(0);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.common.DefaultPrincipalManager JD-Core Version: 0.5.4
 */