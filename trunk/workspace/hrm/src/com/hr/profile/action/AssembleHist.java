package com.hr.profile.action;

import com.hr.configuration.domain.Department;
import com.hr.profile.bo.IDeptHistBo;
import com.hr.profile.bo.IEmpHistOrgBo;
import com.hr.profile.bo.IPositionBaseHistBo;
import com.hr.profile.domain.Depthist;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.text.SimpleDateFormat;
import java.util.List;

public class AssembleHist extends AssembleDeptList {
    private SimpleDateFormat dateFormat;

    public AssembleHist() {
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    protected void getDeptList() {
        IDeptHistBo deptHistBo = (IDeptHistBo) SpringBeanFactory.getBean("deptHistBo");
        List dhList = deptHistBo.getDhsBeforeQueryDate(this.queryDate);
        for (int i = 0; i < dhList.size(); ++i) {
            Depthist dh = (Depthist) dhList.get(i);
            this.allResultList.add(dh.assembleDeptByDh());
        }
        prepareInitialDeptList();
    }

    protected void setActualNum() {
        IEmpHistOrgBo empHistOrgBo = (IEmpHistOrgBo) SpringBeanFactory.getBean("empHistOrgBo");

        String dateString = this.dateFormat.format(this.queryDate);
        StringBuffer hql = new StringBuffer();
        hql.append("select e.ehoDeptNo,count(*) from EmpHistOrg e where e.ehoValidFrom<='");
        hql.append(dateString + "' ");
        hql.append("and (e.ehoValidTo>='" + dateString + "' or e.ehoValidTo is null) ");
        hql.append("group by e.ehoDeptNo");
        List deptActualNumList = empHistOrgBo.exeHqlList(hql.toString());
        for (int i = 0; i < this.deptList.size(); ++i) {
            Department dept = (Department) this.deptList.get(i);
            String deptId = dept.getId();
            for (int j = 0; j < deptActualNumList.size(); ++j) {
                Object[] deptActualNum = (Object[]) (Object[]) deptActualNumList.get(j);
                String turnId = ((Department) deptActualNum[0]).getId();
                if (deptId.equals(turnId)) {
                    dept.setActualNum(Integer.valueOf(((Long) deptActualNum[1]).intValue()));
                    break;
                }
            }
        }
    }

    protected void setOrderNum() {
        IPositionBaseHistBo pbhBo = (IPositionBaseHistBo) SpringBeanFactory
                .getBean("positionBaseHistBo");

        String dateString = this.dateFormat.format(this.queryDate);
        StringBuffer hql = new StringBuffer();
        hql
                .append("select e.pbhiDeptId,sum(e.pbhiMaxEmployee) from PositionBaseHist e where e.pbhiPbStatus=1 ");
        hql.append("and e.pbhiValidFrom <='" + dateString + "'");
        hql.append("and (e.pbhiValidTo>='" + dateString + "' or e.pbhiValidTo is null) ");
        hql.append("group by e.pbhiDeptId");
        List deptOrderNumList = pbhBo.exeHqlList(hql.toString());
        for (int i = 0; i < this.deptList.size(); ++i) {
            Department dept = (Department) this.deptList.get(i);
            String deptId = dept.getId();
            for (int j = 0; j < deptOrderNumList.size(); ++j) {
                Object[] deptOrderNum = (Object[]) (Object[]) deptOrderNumList.get(j);
                String turnId = ((Department) deptOrderNum[0]).getId();
                if (deptId.equals(turnId)) {
                    dept.setOrderNum(Integer.valueOf(((Long) deptOrderNum[1]).intValue()));
                    break;
                }
            }
        }
    }

    protected void setDeptHead() {
        IPositionBaseHistBo pbhBo = (IPositionBaseHistBo) SpringBeanFactory
                .getBean("positionBaseHistBo");

        String dateString = this.dateFormat.format(this.queryDate);
        StringBuffer hql = new StringBuffer();
        hql
                .append("select e.pbhiDeptId,p.empName from PositionBaseHist e ,EmpHistOrg o join o.ehoEmpNo p where e.pbhiPbId=p.empPbNo and e.pbhiInCharge=1 and e.pbhiPbStatus=1");
        hql.append("and e.pbhiValidFrom <='" + dateString + "'");
        hql.append("and (e.pbhiValidTo>='" + dateString + "' or e.pbhiValidTo is null) ");
        hql.append("group by e.pbhiDeptId");
        List deptHeadList = pbhBo.exeHqlList(hql.toString());
        for (int i = 0; i < this.deptList.size(); ++i) {
            Department dept = (Department) this.deptList.get(i);
            String deptId = dept.getId();
            for (int j = 0; j < deptHeadList.size(); ++j) {
                Object[] deptHead = (Object[]) (Object[]) deptHeadList.get(j);
                String turnId = ((Department) deptHead[0]).getId();
                if (deptId.equals(turnId)) {
                    Employee emp = new Employee();
                    emp.setEmpName((String) deptHead[1]);
                    dept.setDeptHead(emp);
                    break;
                }
            }
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.AssembleHist JD-Core Version: 0.5.4
 */