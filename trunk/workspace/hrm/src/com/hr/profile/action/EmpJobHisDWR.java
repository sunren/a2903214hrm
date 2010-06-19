package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.profile.bo.IEmpJobHisBo;
import com.hr.profile.domain.Emphistoryjob;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

public class EmpJobHisDWR extends BaseAction {
    private static final long serialVersionUID = 1L;
    IEmpJobHisBo empJobHisBo;

    public String jobHisAdd(Emphistoryjob emphistoryjob, String empId) {
        String auth = DWRUtil.checkAuth("empjobhisdwr", "");

        if ("error".equalsIgnoreCase(auth))
            return "noauth";

        if ("self".equals(empId))
            empId = getCurrentEmpNo();

        if (("SUB".equals(auth)) && (!checkAuth(empId)))
            return "noauth";

        this.empJobHisBo = ((IEmpJobHisBo) SpringBeanFactory.getBean("empJobHisBo"));
        emphistoryjob.setEmployee(new Employee(empId));
        emphistoryjob.setEhjCreateBy(getCurrentEmpNo());
        emphistoryjob.setEhjCreateDate(new Date());
        emphistoryjob.setEhjLastChangeBy(getCurrentEmpNo());
        emphistoryjob.setEhjLastChangeTime(new Date());
        String ejhId = this.empJobHisBo.insert(emphistoryjob);

        return ejhId;
    }

    public String jobHisDel(String ehjId) {
        String auth = DWRUtil.checkAuth("empjobhisdwr", "");

        if ("error".equalsIgnoreCase(auth))
            return "noauth";

        this.empJobHisBo = ((IEmpJobHisBo) SpringBeanFactory.getBean("empJobHisBo"));
        String[] fetches = { Emphistoryjob.PROP_EMPLOYEE };
        Emphistoryjob emphj = this.empJobHisBo.load(ehjId, fetches);

        if (("SUB".equals(auth)) && (!checkAuth(emphj.getEmployee().getId()))) {
            return "noauth";
        }

        this.empJobHisBo.delete(emphj);

        return "success";
    }

    public String jobHisUpdate(Emphistoryjob emphistoryjob) {
        String auth = DWRUtil.checkAuth("empjobhisdwr", "");

        if ("error".equalsIgnoreCase(auth))
            return "noauth";

        this.empJobHisBo = ((IEmpJobHisBo) SpringBeanFactory.getBean("empJobHisBo"));
        String[] fetches = { Emphistoryjob.PROP_EMPLOYEE };
        Emphistoryjob empOldHJ = this.empJobHisBo.load(emphistoryjob.getEhjId(), fetches);

        if (empOldHJ == null)
            return "noId";

        if (("SUB".equals(auth)) && (!checkAuth(empOldHJ.getEmployee().getId()))) {
            return "noauth";
        }

        empOldHJ.setEhjCompany(emphistoryjob.getEhjCompany());
        empOldHJ.setEhjDateStart(emphistoryjob.getEhjDateStart());
        empOldHJ.setEhjDateEnd(emphistoryjob.getEhjDateEnd());
        empOldHJ.setEhjPosition(emphistoryjob.getEhjPosition());
        empOldHJ.setEhjDescription(emphistoryjob.getEhjDescription());
        emphistoryjob.setEhjLastChangeBy(getCurrentEmpNo());
        emphistoryjob.setEhjLastChangeTime(new Date());

        this.empJobHisBo.update(empOldHJ);
        return "success";
    }

    public Emphistoryjob loadJobHis(String ehjId) {
        if (StringUtils.isEmpty(ehjId)) {
            return null;
        }
        ehjId = ehjId.trim();
        this.empJobHisBo = ((IEmpJobHisBo) SpringBeanFactory.getBean("empJobHisBo"));
        Emphistoryjob result = this.empJobHisBo.load(ehjId, new String[0]);
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.EmpJobHisDWR JD-Core Version: 0.5.4
 */