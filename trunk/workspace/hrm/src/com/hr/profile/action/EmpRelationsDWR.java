package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.profile.bo.IEmpRelationsBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Emprelations;
import com.hr.spring.SpringBeanFactory;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class EmpRelationsDWR extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Emprelations emprelations;
    private IEmpRelationsBo empRelationsBo;
    private String erelId;

    public String relationsAdd() throws Exception {
        if ("self".equals(this.emprelations.getEmployee().getId())) {
            this.emprelations.setEmployee(getCurrentEmp());
        }

        if (("SUB".equals(this.authorityCondition))
                && (!checkAuth(this.emprelations.getEmployee().getId()))) {
            addActionError("您没有增加权限执行本操作");
            return "error";
        }

        this.empRelationsBo = ((IEmpRelationsBo) SpringBeanFactory.getBean("empRelationsBo"));
        this.emprelations.setEmployee(this.emprelations.getEmployee());
        this.emprelations.setErelCreateBy(getCurrentEmpNo());
        this.emprelations.setErelCreateDate(new Date());
        this.emprelations.setErelLastChangeBy(getCurrentEmpNo());
        this.emprelations.setErelLastChangeTime(new Date());

        this.empRelationsBo.insert(this.emprelations);

        addActionMessage("社会关系增加成功");
        return "success";
    }

    public String relationsDel(String erelId) {
        String auth = DWRUtil.checkAuth("emprelationsdwr", "");
        if ("error".equalsIgnoreCase(auth)) {
            return "noauth";
        }
        this.empRelationsBo = ((IEmpRelationsBo) SpringBeanFactory.getBean("empRelationsBo"));
        String[] fetches = { Emprelations.PROP_EREL_EMPLOYEE };
        Emprelations empOldRelations = this.empRelationsBo.load(erelId, fetches);

        if (("SUB".equals(auth)) && (!checkAuth(empOldRelations.getEmployee().getId())))
            return "noauth";

        this.empRelationsBo.delete(empOldRelations);

        return "success";
    }

    public String relationsUpdate() throws Exception {
        this.empRelationsBo = ((IEmpRelationsBo) SpringBeanFactory.getBean("empRelationsBo"));
        List list = this.empRelationsBo.search(this.emprelations);
        if (list.size() == 0) {
            return "error";
        }
        Emprelations empOldRelations = (Emprelations) list.get(0);

        if (("SUB".equals(this.authorityCondition))
                && (!checkAuth(empOldRelations.getEmployee().getId()))) {
            addActionError("您没有修改权限执行本操作");
            return "error";
        }

        empOldRelations.setErelRelationship(this.emprelations.getErelRelationship());
        empOldRelations.setErelName(this.emprelations.getErelName());
        empOldRelations.setErelBirthday(this.emprelations.getErelBirthday());
        empOldRelations.setErelBirthdayRemind(this.emprelations.getErelBirthdayRemind());
        empOldRelations.setErelCompany(this.emprelations.getErelCompany());
        empOldRelations.setErelPosition(this.emprelations.getErelPosition());
        empOldRelations.setErelContactInfo(this.emprelations.getErelContactInfo());
        empOldRelations.setErelLastChangeBy(getCurrentEmpNo());
        empOldRelations.setErelLastChangeTime(new Date());

        this.empRelationsBo.update(empOldRelations);
        addSuccessInfo("修改员工社会关系成功");
        return "success";
    }

    public Emprelations loadTrainHis(String ehrId) {
        if (StringUtils.isEmpty(ehrId)) {
            return null;
        }
        this.empRelationsBo = ((IEmpRelationsBo) SpringBeanFactory.getBean("empRelationsBo"));
        Emprelations result = this.empRelationsBo.load(ehrId, new String[0]);
        return result;
    }

    public Emprelations getEmprelations() {
        return this.emprelations;
    }

    public void setEmprelations(Emprelations emprelations) {
        this.emprelations = emprelations;
    }

    public String getErelId() {
        return this.erelId;
    }

    public void setErelId(String erelId) {
        this.erelId = erelId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.EmpRelationsDWR JD-Core Version: 0.5.4
 */