package com.hr.util;

import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.domain.Department;
import com.hr.examin.bo.ExaminDateUtil;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.PositionBase;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.reflect.ObjectProperty;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.ObjectUtils;

public class BaseCrit {
    static final int splitIn = 500;

    public static void addDC(DetachedCriteria dc, String alias, String op, String[] argS) {
        if ((StringUtils.isEmpty(alias)) || (StringUtils.isEmpty(op)) || (argS == null)
                || (StringUtils.isEmpty(argS[0])) || (",".equals(argS[0].trim())))
            return;
        dc.add(addC(alias, op, argS));
    }

    public static Criterion addC(String alias, String op, String[] argS) {
        if (op == "like")
            return Restrictions.ilike(alias, "%" + argS[0].trim().replace("　", "") + "%");
        if (op == "eq")
            return Restrictions.eq(alias, argS[0].trim().replace("　", ""));
        if (op == "ge")
            return Restrictions.ge(alias, argS[0].trim().replace("　", ""));
        if (op == "gt")
            return Restrictions.gt(alias, argS[0].trim().replace("　", ""));
        if (op == "le")
            return Restrictions.le(alias, argS[0].trim().replace("　", ""));
        if (op == "lt")
            return Restrictions.lt(alias, argS[0].trim().replace("　", ""));
        if (op == "between")
            return Restrictions.between(alias, argS[0].trim().replace("　", ""), argS[1]);
        if (op == "in")
            return Restrictions.in(alias, argS);
        return null;
    }

    public static void addDC(DetachedCriteria dc, String alias, String op, Integer[] argI) {
        if (op == "between") {
            if ((argI[0] == null) && (argI[1] == null))
                return;
            if (argI[0] == null)
                argI[0] = new Integer(-999999999);
            if (argI[1] == null)
                argI[1] = new Integer(999999999);
        }
        if ((StringUtils.isEmpty(alias)) || (StringUtils.isEmpty(op)) || (argI == null)
                || (argI[0] == null))
            return;
        dc.add(addC(alias, op, argI));
    }

    public static Criterion addC(String alias, String op, Integer[] argI) {
        if (op == "eq")
            return Restrictions.eq(alias, argI[0]);
        if (op == "ge")
            return Restrictions.ge(alias, argI[0]);
        if (op == "gt")
            return Restrictions.gt(alias, argI[0]);
        if (op == "le")
            return Restrictions.le(alias, argI[0]);
        if (op == "lt")
            return Restrictions.lt(alias, argI[0]);
        if (op == "between")
            return Restrictions.between(alias, argI[0], argI[1]);
        if (op == "in")
            return Restrictions.in(alias, argI);
        return null;
    }

    public static void addDC(DetachedCriteria dc, String alias, String op, BigDecimal[] argBD) {
        if (op == "between") {
            if ((argBD[0] == null) && (argBD[1] == null))
                return;
            if (argBD[0] == null)
                argBD[0] = new BigDecimal(-99999999999.990005D);
            if (argBD[1] == null)
                argBD[1] = new BigDecimal(99999999999.990005D);
        }
        if ((StringUtils.isEmpty(alias)) || (StringUtils.isEmpty(op)) || (argBD == null)
                || (argBD[0] == null))
            return;
        dc.add(addC(alias, op, argBD));
    }

    public static Criterion addC(String alias, String op, BigDecimal[] argBD) {
        if (op == "eq")
            return Restrictions.eq(alias, argBD[0]);
        if (op == "ge")
            return Restrictions.ge(alias, argBD[0]);
        if (op == "gt")
            return Restrictions.gt(alias, argBD[0]);
        if (op == "le")
            return Restrictions.le(alias, argBD[0]);
        if (op == "lt")
            return Restrictions.lt(alias, argBD[0]);
        if (op == "between")
            return Restrictions.between(alias, argBD[0], argBD[1]);
        if (op == "in")
            return Restrictions.in(alias, argBD);
        return null;
    }

    public static void addDC(DetachedCriteria dc, String alias, String op, Date[] argD) {
        if (op == "between") {
            if ((argD[0] == null) && (argD[1] == null))
                return;
            if (argD[0] == null)
                argD[0] = new Date(1898L);
            if (argD[1] == null)
                argD[1] = new Date(9997L);
        }
        if ((StringUtils.isEmpty(alias)) || (StringUtils.isEmpty(op)) || (argD == null)
                || (argD[0] == null))
            return;
        if (argD[1] == null)
            dc.add(addC(alias, op, argD));
    }

    public static Criterion addC(String alias, String op, Date[] argD) {
        if (op == "between") {
            if (argD[0] == null)
                argD[0] = new Date(1898L);
            if (argD[1] == null)
                argD[1] = new Date(9997L);
        }
        if (op == "eq")
            return Restrictions.eq(alias, argD[0]);
        if (op == "ge")
            return Restrictions.ge(alias, argD[0]);
        if (op == "gt")
            return Restrictions.gt(alias, argD[0]);
        if (op == "le")
            return Restrictions.le(alias, argD[0]);
        if (op == "lt")
            return Restrictions.lt(alias, argD[0]);
        if (op == "between")
            return Restrictions.between(alias, argD[0], argD[1]);
        if (op == "in")
            return Restrictions.in(alias, argD);
        return null;
    }

    public static void addDCDate(DetachedCriteria dc, String alias, String op, Date[] argD) {
        if (op == "between") {
            if ((argD[0] == null) && (argD[1] == null))
                return;
            if (argD[0] == null)
                argD[0] = new Date(1898L);
            if (argD[1] == null)
                argD[1] = new Date(9997L);
        }
        if ((StringUtils.isEmpty(alias)) || (StringUtils.isEmpty(op)) || (argD == null)
                || (argD[0] == null))
            return;
        dc.add(addCDate(alias, op, argD));
    }

    public static void addDCDateRange(DetachedCriteria dc, String alias, Date dLow, Date dHigh) {
        if (StringUtils.isEmpty(alias))
            return;
        if ((dLow != null) && (dHigh != null))
            dc.add(addCDate(alias, "between", new Date[] { dLow, dHigh }));
        else if (dLow != null)
            dc.add(addCDate(alias, "ge", new Date[] { dLow }));
        else if (dHigh != null)
            dc.add(addCDate(alias, "le", new Date[] { dHigh }));
    }

    public static void addDCDateExpire(DetachedCriteria dc, String alias, String op,
            Integer offsetDays, Date dCurr) {
        if (StringUtils.isEmpty(alias))
            return;
        if (dCurr == null)
            dCurr = new Date();
        Date dLow = dCurr;
        Date dHigh = dCurr;
        if (offsetDays != null)
            dHigh = DateUtil.dateAdd(dLow, offsetDays.intValue());
        if ("le".equals(op))
            dc.add(addCDate(alias, "le", new Date[] { dHigh }));
        else
            dc.add(addCDate(alias, "between", new Date[] { dLow, dHigh }));
    }

    public static Criterion addCDate(String alias, String op, Date[] argD) {
        if (op == "between") {
            if (argD[0] == null)
                argD[0] = new Date(1898L);
            if (argD[1] == null)
                argD[1] = new Date(9997L);

        }

        for (int i = 0; i < argD.length; ++i) {
            argD[i] = DateUtil.convDateTimeToDate(argD[i]);
        }

        if (op == "eq")
            return Restrictions.eq(alias, argD[0]);
        if (op == "ge")
            return Restrictions.ge(alias, argD[0]);
        if (op == "gt")
            return Restrictions.gt(alias, argD[0]);
        if (op == "le")
            return Restrictions.le(alias, argD[0]);
        if (op == "lt")
            return Restrictions.lt(alias, argD[0]);
        if (op == "between")
            return Restrictions.between(alias, argD[0], argD[1]);
        if (op == "in")
            return Restrictions.in(alias, argD);
        return null;
    }

    public static void addDCObjIds(DetachedCriteria dc, String alias, String propId, String[] argS) {
        if ((StringUtils.isEmpty(alias)) || (StringUtils.isEmpty(propId)) || (argS == null)
                || (argS.length == 0))
            return;
        if (argS.length == 1)
            dc.add(addC(alias + "." + propId, "eq", argS));
        else if (argS.length > 1)
            dc.add(addC(alias + "." + propId, "in", argS));
    }

    public static void addDC(DetachedCriteria dc, String alias, String propId, Object[] argO) {
        if ((StringUtils.isEmpty(alias)) || (argO == null) || (argO[0] == null))
            return;
        if (argO.length == 1) {
            try {
                String idValue = (String) ObjectProperty.getObjectFinalProperty(argO[0], propId);
                if (StringUtils.isEmpty(idValue))
                    return;
                dc.add(addC(alias, "eq", argO));
            } catch (Exception ex) {
                System.out.println("==========error: cannot convert object key" + argO);
                return;
            }
        } else if (argO.length > 1)
            dc.add(addC(alias, "in", argO));
    }

    public static Criterion addC(String alias, String op, Object[] argO) {
        try {
            if (op == "eq")
                return Restrictions.eq(alias, argO[0]);
            if (op == "in")
                return Restrictions.in(alias, argO);
        } catch (Exception ex) {
            System.out.println("==========error: cannot convert object" + argO[0]);
            return null;
        }
        return null;
    }

    public static void addOrgDC(DetachedCriteria dc, String alias, String[] argDepts,
            String[] argLocs) {
        if ((((argDepts == null) || (argDepts.length == 0)))
                && (((argLocs == null) || (argLocs.length == 0))))
            return;
        if (!StringUtils.isEmpty(alias))
            alias = alias + ".";
        else {
            alias = "";
        }
        dc.add(addOrgC(alias, argDepts, argLocs));
    }

    public static Criterion addOrgC(String alias, String[] argDepts, String[] argLocs) {
        if ((argLocs == null) || (argLocs.length == 0)) {
            return Restrictions.in(alias + Employee.PROP_EMP_DEPT_NO + ".id", argDepts);
        }
        if ((argDepts == null) || (argDepts.length == 0)) {
            return Restrictions.in(alias + Employee.PROP_EMP_LOCATION_NO + ".id", argLocs);
        }
        return Restrictions.disjunction().add(
                                              Restrictions.in(alias + Employee.PROP_EMP_DEPT_NO
                                                      + ".id", argDepts))
                .add(Restrictions.in(alias + Employee.PROP_EMP_LOCATION_NO + ".id", argLocs));
    }

    public static void addDeptDC(DetachedCriteria dc, String aliasDept, String aliasPB,
            Integer status, Department dept) {
        if ((dept == null) || (StringUtils.isEmpty(dept.getId()))
                || (StringUtils.isEmpty(aliasDept)))
            return;
        String[] argS = new String[3];
        argS = dept.getId().split(",");
        if (StringUtils.isEmpty(argS[0]))
            return;
        if (!StringUtils.isEmpty(argS[1])) {
            addDC(dc, aliasPB + "." + PositionBase.PROP_ID, "eq", new String[] { argS[1] });
        } else if ("1".equals(argS[2])) {
            IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
            List deptsInCharge = deptBo.getAllSubDepts(status, new String[] { argS[0] });
            addDC(dc, aliasDept, Department.PROP_ID, deptsInCharge.toArray());
        } else {
            addDC(dc, aliasDept + "." + Department.PROP_ID, "eq", new String[] { argS[0] });
        }
    }

    public static void addEmpDC(DetachedCriteria dc, String alias, String argS) {
        if (StringUtils.isEmpty(argS))
            return;
        if (!StringUtils.isEmpty(alias))
            alias = alias + ".";
        else
            alias = "";
        dc.add(addEmpC(alias, argS.trim().replace("　", "")));
    }

    public static Criterion addEmpC(String alias, String argS) {
        return Restrictions.disjunction().add(
                                              Restrictions.ilike(alias
                                                      + Employee.PROP_EMP_DISTINCT_NO, "%" + argS
                                                      + "%"))
                .add(Restrictions.ilike(alias + Employee.PROP_EMP_NAME, "%" + argS + "%"));
    }

    public static void addDeptEmpDC(DetachedCriteria dc, String alias, String argS) {
        if (StringUtils.isEmpty(argS))
            return;
        if (!StringUtils.isEmpty(alias))
            alias = alias + ".";
        else
            alias = "";
        dc.add(addDeptEmpC(alias, argS.trim().replace("　", "")));
    }

    public static Criterion addDeptEmpC(String alias, String argS) {
        return Restrictions.disjunction().add(
                                              Restrictions.ilike(alias + Employee.PROP_EMP_DEPT_NO
                                                      + "." + Department.PROP_DEPARTMENT_NAME, "%"
                                                      + argS + "%"))
                .add(Restrictions.ilike(alias + Employee.PROP_EMP_DISTINCT_NO, "%" + argS + "%"))
                .add(Restrictions.ilike(alias + Employee.PROP_EMP_NAME, "%" + argS + "%"));
    }

    public static void addStatusEmpDC(DetachedCriteria dc, String alias, String yearmonth,
            String[] argS) {
        if (StringUtils.isEmpty(yearmonth))
            return;
        if (!StringUtils.isEmpty(alias))
            alias = alias + ".";
        else
            alias = "";
        Criterion crit = addStatusEmpC(alias, yearmonth, argS);
        if (crit == null)
            return;
        dc.add(crit);
    }

    public static Criterion addStatusEmpC(String alias, String yearmonth, String[] argS) {
        String config = DatabaseSysConfigManager.getInstance().getProperty("sys.examin.month.sum");
        Date[] dateArr = ExaminDateUtil.getDateArray(yearmonth, config);

        if ((ObjectUtils.isEmpty(argS)) || (StringUtils.isEmpty(argS[0]))) {
            return Restrictions.disjunction().add(
                                                  Restrictions.and(Restrictions
                                                          .le(alias + Employee.PROP_EMP_JOIN_DATE,
                                                              dateArr[1]), Restrictions.eq(alias
                                                          + Employee.PROP_EMP_STATUS, Integer
                                                          .valueOf(1))))
                    .add(Restrictions.ge(alias + Employee.PROP_EMP_TERMINATE_DATE, dateArr[0]));
        }

        if ("newIn".equals(argS[0])) {
            return addCDate(alias + Employee.PROP_EMP_JOIN_DATE, "between", dateArr);
        }

        if ("newOut".equals(argS[0])) {
            return addCDate(alias + Employee.PROP_EMP_TERMINATE_DATE, "between", dateArr);
        }

        if ("normal".equals(argS[0])) {
            return Restrictions.disjunction().add(
                                                  Restrictions
                                                          .lt(alias + Employee.PROP_EMP_JOIN_DATE,
                                                              dateArr[0]))
                    .add(Restrictions.gt(alias + Employee.PROP_EMP_TERMINATE_DATE, dateArr[1]));
        }

        return null;
    }

    public static void addEmpEngDC(DetachedCriteria dc, String alias, String argS) {
        if (StringUtils.isEmpty(argS))
            return;
        if (!StringUtils.isEmpty(alias))
            alias = alias + ".";
        else
            alias = "";
        dc.add(addEmpEngC(alias, argS.trim().replace("　", "")));
    }

    public static Criterion addEmpEngC(String alias, String argS) {
        return Restrictions.disjunction().add(
                                              Restrictions.ilike(alias
                                                      + Employee.PROP_EMP_DISTINCT_NO, "%" + argS
                                                      + "%"))
                .add(Restrictions.ilike(alias + Employee.PROP_EMP_NAME, "%" + argS + "%"))
                .add(Restrictions.ilike(alias + Employee.PROP_EMP_FNAME, "%" + argS + "%"))
                .add(Restrictions.ilike(alias + Employee.PROP_EMP_MNAME, "%" + argS + "%"))
                .add(Restrictions.ilike(alias + Employee.PROP_EMP_LNAME, "%" + argS + "%"));
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name: com.hr.util.BaseCrit
 * JD-Core Version: 0.5.4
 */