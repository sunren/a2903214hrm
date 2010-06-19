package com.hr.configuration.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.configuration.bo.IAttdMachineBO;
import com.hr.configuration.bo.IContractTypeBo;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IEcptypeBO;
import com.hr.configuration.bo.IEmpTypeBO;
import com.hr.configuration.bo.IInfoBO;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.bo.IJobgradeBO;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.bo.IStatBO;
import com.hr.configuration.domain.AttdMachine;
import com.hr.configuration.domain.ContractType;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Ecptype;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Infoclass;
import com.hr.configuration.domain.JobTitle;
import com.hr.configuration.domain.Jobgrade;
import com.hr.configuration.domain.Location;
import com.hr.configuration.domain.Statusconf;
import com.hr.configuration.domain.StatusconfPK;
import com.hr.examin.bo.interfaces.ILeavetypeBO;
import com.hr.examin.bo.interfaces.IOvertimetypeBo;
import com.hr.examin.domain.Leavetype;
import com.hr.examin.domain.Overtimetype;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IOrgheadsBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Orgheads;
import com.hr.spring.SpringBeanFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class BasicConfigAction extends BaseAction {
    private static final String ERROR = "ERROR";
    private static final String EXISTED = "EXISTED";
    private static final String SUCC = "SUCC";
    private static final String FAIL = "FAIL";
    private static final String DISABLEDERROR = "DISABLEDERROR";

    private boolean checkAuthForSystem() {
        String flt = DWRUtil.checkAuth("config", "execute");

        return !"error".equalsIgnoreCase(flt);
    }

    private boolean checkAuthForComp() {
        String flt = DWRUtil.checkAuth("salaryRatingConfig", "execute");

        return !"error".equalsIgnoreCase(flt);
    }

    private boolean checkAuthForEmployee() {
        String flt = DWRUtil.checkAuth("profileConfig", "execute");

        return !"error".equalsIgnoreCase(flt);
    }

    public String refreshEmptype(String id) {
        String[] emptype = { "empType" };
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        Employee employee = empBo.loadEmp(id, emptype);
        if (employee != null) {
            String type = employee.getEmpType().getId();
            return type;
        }
        return null;
    }

    public String addEmpType(Emptype emp) {
        if (checkAuthForEmployee()) {
            try {
                IEmpTypeBO bo = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
                if (bo.addEmptype(emp)) {
                    return emp.getId();
                }
                return "EXISTED";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String delEmpType(String id) {
        if (checkAuthForEmployee()) {
            try {
                IEmpTypeBO bo = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
                if (bo.delEmptype(Emptype.class, id)) {
                    return "SUCC";
                }
                return "FAIL";
            } catch (Exception e) {
                return "ERROR";
            }
        }
        return "noauth";
    }

    public String updateEmpType(Emptype emptype) {
        if (checkAuthForEmployee()) {
            try {
                IEmpTypeBO bo = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
                String flag = bo.updateEmptype(emptype);
                if ("0".equals(flag)) {
                    return "SUCC";
                }
                if ("1".equals(flag)) {
                    return "DISABLEDERROR";
                }
                return "EXISTED";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String saveEmpTypeIdByBatch(String[] ids) {
        IEmpTypeBO bo = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
        bo.saveEmpTypeIdByBatch(ids);
        return "SUCC";
    }

    public String saveEcpTypeByBatch(String[] ids) {
        IEcptypeBO bo = (IEcptypeBO) SpringBeanFactory.getBean("ecptypeBO");
        bo.saveEcpTypeByBatch(ids);
        return "SUCC";
    }

    public String addLocation(Location location) {
        if (checkAuthForEmployee()) {
            try {
                ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
                if (localbo.addLocation(location)) {
                    return location.getId();
                }
                return "EXISTED";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String delLocation(String id) {
        if (checkAuthForEmployee()) {
            ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
            return localbo.delLocation(Location.class, id);
        }
        return "noauth";
    }

    public String saveLocationSortIdByBatch(String[] ids) {
        if (checkAuthForEmployee()) {
            try {
                ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
                localbo.saveLocationSortIdByBatch(ids);
                return "SUCC";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String updateLocationSwapSortedId(String tr1Id, String tr2Id) {
        if (checkAuthForEmployee()) {
            try {
                ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
                localbo.updateLocationSwapSortedId(tr1Id, tr2Id);
                return "SUCC";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String updateLocation(Location location) {
        if (checkAuthForEmployee()) {
            try {
                ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
                String flag = localbo.updateLocation(location);
                if ("0".equals(flag))
                    return "SUCC";
                if ("1".equals(flag)) {
                    return "DISABLEDERROR";
                }

                return "EXISTED";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String addAttdMachine(AttdMachine attdMachine) {
        if (checkAuthForEmployee()) {
            try {
                IAttdMachineBO localbo = (IAttdMachineBO) SpringBeanFactory
                        .getBean("attdMachineBO");
                if (localbo.addAttdMachine(attdMachine)) {
                    return attdMachine.getMacId();
                }
                return "EXISTED";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String delAttdMachine(String id) {
        if (checkAuthForEmployee()) {
            IAttdMachineBO localbo = (IAttdMachineBO) SpringBeanFactory.getBean("attdMachineBO");
            return localbo.delAttdMachine(AttdMachine.class, id);
        }
        return "noauth";
    }

    public String saveAttdMachineSortIdByAttdMachine(String[] ids) {
        if (checkAuthForEmployee()) {
            try {
                IAttdMachineBO localbo = (IAttdMachineBO) SpringBeanFactory
                        .getBean("attdMachineBO");
                localbo.saveAttdMachineSortIdByAttdMachine(ids);
                return "SUCC";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String updateAttdMachine(AttdMachine attdMachine) {
        if (checkAuthForEmployee()) {
            try {
                IAttdMachineBO localbo = (IAttdMachineBO) SpringBeanFactory
                        .getBean("attdMachineBO");
                String flag = localbo.updateAttdMachine(attdMachine);
                if ("0".equals(flag))
                    return "SUCC";
                if ("1".equals(flag)) {
                    return "DISABLEDERROR";
                }
                return "EXISTED";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String addDepartment(Department dept) {
        if (checkAuthForEmployee()) {
            try {
                IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
                if (deptbo.addDepartment(dept)) {
                    return dept.getId();
                }
                return "EXISTED";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String delDepartment(String id) {
        if (checkAuthForEmployee()) {
            IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
            return deptbo.delDepartment(Department.class, id);
        }
        return "noauth";
    }

    public String saveDepartmentSortIdByBatch(String[] ids) {
        if (checkAuthForEmployee()) {
            try {
                IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
                deptbo.saveDepartmentSortIdByBatch(ids);
                return "SUCC";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String updateDepartment(Department dept) {
        if (checkAuthForEmployee()) {
            try {
                IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
                String result = deptbo.updateDepartment(dept);
                if (result.equals("success"))
                    return "SUCC";
                if (result.equals("exist"))
                    return "EXISTED";
                if (result.equals("error1")) {
                    return "DISABLEDERROR";
                }
                if (result.equals("error2")) {
                    return "DISABLEDERROR2";
                }
                return "FAIL";
            } catch (Exception e) {
                e.printStackTrace();
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String addStatus(Statusconf stat) {
        if (checkAuthForSystem()) {
            try {
                IStatBO statbo = (IStatBO) SpringBeanFactory.getBean("statBO");
                if (statbo.addStat(stat)) {
                    return String.valueOf(stat.getId().getStatusconfNo());
                }
                return "EXISTED";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String delStatus(StatusconfPK id) {
        if (checkAuthForSystem()) {
            try {
                IStatBO statbo = (IStatBO) SpringBeanFactory.getBean("statBO");
                if (statbo.delStat(Statusconf.class, id)) {
                    return "SUCC";
                }
                return "EXISTED";
            } catch (Exception e) {
                return "ERROR";
            }
        }
        return "noauth";
    }

    public String updateStatus(Statusconf stat) {
        if (checkAuthForSystem()) {
            try {
                IStatBO statbo = (IStatBO) SpringBeanFactory.getBean("statBO");
                if (statbo.updateStatus(stat)) {
                    return "SUCC";
                }
                return "EXISTED";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public List<Department> findAlldept() {
        if (checkAuthForEmployee()) {
            try {
                IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
                return deptbo.FindEnabledDepartment();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public String addInfo(Infoclass info) {
        if (checkAuthForSystem()) {
            try {
                IInfoBO infobo = (IInfoBO) SpringBeanFactory.getBean("infoBO");
                if (infobo.addInfo(info)) {
                    return String.valueOf(info.getId());
                }
                return "EXISTED";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String delInfo(String id) {
        if (checkAuthForSystem()) {
            try {
                IInfoBO infobo = (IInfoBO) SpringBeanFactory.getBean("infoBO");
                boolean rs = infobo.delInfo(Infoclass.class, id);
                if (rs) {
                    return "";
                }
                return "有数据关联！";
            } catch (Exception e) {
                return "系统异常＄1�7" + e.getMessage();
            }
        }
        return "无权限操作！";
    }

    public String updateInfo(Infoclass info) {
        if (checkAuthForSystem()) {
            try {
                IInfoBO infobo = (IInfoBO) SpringBeanFactory.getBean("infoBO");
                if (infobo.updateInfo(info)) {
                    return "SUCC";
                }
                return "EXISTED";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String saveInforclassIdByBatch(String[] ids) {
        if (checkAuthForSystem()) {
            try {
                IInfoBO infobo = (IInfoBO) SpringBeanFactory.getBean("infoBO");
                infobo.saveInforclassIdByBatch(ids);
                return "SUCC";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String addEcptype(Ecptype ecptype) {
        if (checkAuthForSystem()) {
            try {
                IEcptypeBO ecptypebo = (IEcptypeBO) SpringBeanFactory.getBean("ecptypeBO");
                List temp = ecptypebo.getEcptypeByName(ecptype.getEcptypeName().trim());
                if ((temp != null) && (temp.size() >= 1)) {
                    return "EXISTED";
                }
                if (ecptypebo.addEcptype(ecptype)) {
                    return ecptype.getId();
                }
                return "FAIL";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String delEcptype(String id) {
        if (checkAuthForSystem()) {
            try {
                IEcptypeBO ecptypebo = (IEcptypeBO) SpringBeanFactory.getBean("ecptypeBO");
                if (ecptypebo.delEcptype(Ecptype.class, id)) {
                    return "SUCC";
                }
                return "FAIL";
            } catch (Exception e) {
                return "ERROR";
            }
        }
        return "noauth";
    }

    public String updateEcptype(Ecptype ecptype) {
        if (checkAuthForSystem()) {
            try {
                IEcptypeBO ecptypebo = (IEcptypeBO) SpringBeanFactory.getBean("ecptypeBO");
                List temp = ecptypebo.getEcptypeList(ecptype);
                if ((temp != null) && (temp.size() >= 1)) {
                    return "EXISTED";
                }
                if (ecptypebo.updateEpcategory(ecptype)) {
                    return "SUCC";
                }
                return "EXISTED";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String addJobgrade(Jobgrade jobgrade) {
        if (checkAuthForComp()) {
            try {
                IJobgradeBO jobgradeBo = (IJobgradeBO) SpringBeanFactory.getBean("jobgradeBO");
                if (jobgradeBo.addJobgrade(jobgrade)) {
                    return jobgrade.getId();
                }
                return "EXISTED";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String delJobgrade(String id) {
        if (checkAuthForComp()) {
            try {
                IJobgradeBO jobgradeBo = (IJobgradeBO) SpringBeanFactory.getBean("jobgradeBO");
                if (jobgradeBo.delJobgrade(Jobgrade.class, id)) {
                    return "SUCC";
                }
                return "FAIL";
            } catch (Exception e) {
                return "ERROR";
            }
        }
        return "noauth";
    }

    public String updateJobgrade(Jobgrade jobgrade) {
        if (checkAuthForComp()) {
            try {
                IJobgradeBO jobgradeBo = (IJobgradeBO) SpringBeanFactory.getBean("jobgradeBO");
                if (jobgradeBo.updateJobgrade(jobgrade)) {
                    return "SUCC";
                }
                return "EXISTED";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String saveJobGradeIdByBatch(String[] ids) {
        if (checkAuthForComp()) {
            IJobgradeBO jobgradeBo = (IJobgradeBO) SpringBeanFactory.getBean("jobgradeBO");
            jobgradeBo.saveJobGradeIdByBatch(ids);
            return "SUCC";
        }
        return "noauth";
    }

    public String addLeaveType(Leavetype leaveType) {
        if (!checkAuthForSystem()) {
            return "noauth";
        }

        if (StringUtils.isNotEmpty(leaveType.getBalForward())) {
            try {
                leaveType.setLtBalForward(new SimpleDateFormat("yyyy-MM-dd").parse(leaveType
                        .getBalForward()));
            } catch (ParseException e) {
                leaveType.setLtBalForward(null);
            }
        }
        ILeavetypeBO lt_BO = (ILeavetypeBO) SpringBeanFactory.getBean("leavetypeBO");
        String rs = lt_BO.addLeaveType(leaveType);
        if ("SUCC".equals(rs)) {
            return leaveType.getLtNo();
        }
        return rs;
    }

    public String delLeaveType(String id) {
        if (!checkAuthForSystem()) {
            return "noauth";
        }

        ILeavetypeBO lt_BO = (ILeavetypeBO) SpringBeanFactory.getBean("leavetypeBO");
        return lt_BO.delLeaveType(Leavetype.class, id);
    }

    public String saveLeavetypeByBatch(String[] ids) {
        ILeavetypeBO bo = (ILeavetypeBO) SpringBeanFactory.getBean("leavetypeBO");
        bo.saveLeavetypeByBatch(ids);
        return "SUCC";
    }

    public String saveOvertimetypeByBatch(String[] ids) {
        IOvertimetypeBo bo = (IOvertimetypeBo) SpringBeanFactory.getBean("overtimetypeBO");
        bo.saveOvertimetypeByBatch(ids);
        return "SUCC";
    }

    public String updateLeaveType(Leavetype leaveType) {
        if (!checkAuthForSystem())
            return "noauth";
        try {
            if (StringUtils.isNotEmpty(leaveType.getBalForward())) {
                leaveType.setLtBalForward(new SimpleDateFormat("yyyy-MM-dd").parse(leaveType
                        .getBalForward()));
            }
            ILeavetypeBO lt_BO = (ILeavetypeBO) SpringBeanFactory.getBean("leavetypeBO");
            String rs = lt_BO.updateLeaveType(leaveType);
            return rs;
        } catch (Exception e) {
        }
        return "FAIL";
    }

    public Leavetype getLeaveTypeById(String id) {
        if (!checkAuthForSystem()) {
            return null;
        }
        ILeavetypeBO lt_BO = (ILeavetypeBO) SpringBeanFactory.getBean("leavetypeBO");
        return lt_BO.getLeavetype(id);
    }

    public String addOvertimetype(Overtimetype overtimetype) {
        if (checkAuthForSystem()) {
            try {
                IOvertimetypeBo ot_BO = (IOvertimetypeBo) SpringBeanFactory
                        .getBean("overtimetypeBO");
                List temp = ot_BO.findByName(overtimetype.getOtName().trim());
                if (temp.size() >= 1)
                    return "EXISTED";
                if (ot_BO.addOvertimetype(overtimetype)) {
                    return overtimetype.getOtNo();
                }
                return "FAIL";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String delOvertimetype(String id) {
        if (checkAuthForSystem()) {
            try {
                IOvertimetypeBo ot_BO = (IOvertimetypeBo) SpringBeanFactory
                        .getBean("overtimetypeBO");
                if (ot_BO.delOvertimetype(Overtimetype.class, id)) {
                    return "SUCC";
                }
                return "FAIL";
            } catch (Exception e) {
                return "ERROR";
            }
        }
        return "noauth";
    }

    public String updateOvertimetype(Overtimetype overtimetype) {
        if (checkAuthForSystem()) {
            try {
                IOvertimetypeBo ot_BO = (IOvertimetypeBo) SpringBeanFactory
                        .getBean("overtimetypeBO");
                List temp = ot_BO.findOvertimetypeList(overtimetype);
                if (temp.size() >= 1)
                    return "EXISTED";
                if (ot_BO.updateOvertimetype(overtimetype)) {
                    return "SUCC";
                }
                return "FAIL";
            } catch (Exception e) {
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String addContractType(ContractType conType) {
        if (checkAuthForSystem()) {
            IContractTypeBo conBo = (IContractTypeBo) SpringBeanFactory.getBean("contractTypeBo");
            List temp = conBo.findByName(conType.getEctName());
            if (temp != null) {
                int size = temp.size();
                if (size > 1)
                    return "EXISTED";
                if ((size == 1)
                        && (!conType.getId().equalsIgnoreCase(((ContractType) temp.get(0)).getId())))
                    return "EXISTED";
            }
            conBo.addContractType(conType);
            return conType.getId();
        }
        return "noauth";
    }

    public String deleteContractType(String id) {
        if (checkAuthForSystem()) {
            IContractTypeBo conBo = (IContractTypeBo) SpringBeanFactory.getBean("contractTypeBo");
            return conBo.delContractType(id);
        }
        return "noauth";
    }

    public String updateContractType(ContractType conType) {
        if (checkAuthForSystem()) {
            IContractTypeBo conBo = (IContractTypeBo) SpringBeanFactory.getBean("contractTypeBo");
            List temp = conBo.findByName(conType.getEctName());
            if (temp != null) {
                int size = temp.size();
                if (size > 1)
                    return "EXISTED";
                if ((size == 1)
                        && (!conType.getId().equalsIgnoreCase(((ContractType) temp.get(0)).getId())))
                    return "EXISTED";
            }
            conBo.updateContractType(conType);
            return "SUCC";
        }
        return "noauth";
    }

    public String saveContractIdByBatch(String[] ids) {
        if (checkAuthForEmployee()) {
            IContractTypeBo conBo = (IContractTypeBo) SpringBeanFactory.getBean("contractTypeBo");
            try {
                conBo.saveContractTypeIdByBatch(ids);
                return "SUCC";
            } catch (Exception ex) {
                ex.printStackTrace();
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String addJobTitle(JobTitle jobTitle) {
        if (checkAuthForEmployee()) {
            IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
            try {
                if (jobTitleBo.addJobTitle(jobTitle)) {
                    return jobTitle.getJobtitleNo();
                }
                return "EXISTED";
            } catch (Exception ex) {
                ex.printStackTrace();
                return "ERROR";
            }
        }
        return "noauth";
    }

    public String delJobTitle(String id) {
        if (checkAuthForEmployee()) {
            try {
                IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
                if (jobTitleBo.delJobTitle(JobTitle.class, id)) {
                    return "SUCC";
                }
                return "FAIL";
            } catch (Exception e) {
                e.printStackTrace();
                return "ERROR";
            }
        }
        return "noauth";
    }

    public String updateJobTitle(JobTitle jobTitle) {
        if (checkAuthForEmployee()) {
            try {
                IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
                String flag = jobTitleBo.updateJobTitle(jobTitle);
                if ("0".equals(flag))
                    return "SUCC";
                if ("1".equals(flag)) {
                    return "DISABLEDERROR";
                }

                return "EXISTED";
            } catch (Exception e) {
                e.printStackTrace();
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String saveJobTitleSortIdByBatch(String[] ids) {
        if (checkAuthForEmployee()) {
            try {
                IJobTitleBo jobTitleBo = (IJobTitleBo) SpringBeanFactory.getBean("jobTitleBo");
                jobTitleBo.saveJobTitleSortIdByBatch(ids);
                return "SUCC";
            } catch (Exception e) {
                e.printStackTrace();
                return "FAIL";
            }
        }
        return "noauth";
    }

    public List<String> getEmpNameByIds(String deptId) {
        if ((deptId == null) || (deptId.equals(""))) {
            return null;
        }
        IDepartmentBO deptbo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        Department dep = deptbo.loadDepartmentByNo(deptId, new String[0]);
        String ids = dep.getDepartmentHead();

        List result = new ArrayList();
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");

        if ((ids != null) && (!ids.equals("")))
            result.add(ids + "," + empBo.loadEmp(ids, null).getEmpName());
        else {
            result.add(null);
        }

        ids = dep.getDepartmentOtherHeads();
        if ((ids != null) && (!ids.equals(""))) {
            if (ids.indexOf(",") > 0) {
                String[] idarray = ids.split(",");
                for (String id : idarray)
                    result.add(id + "," + empBo.loadEmp(id, null).getEmpName());
            } else {
                result.add(ids + "," + empBo.loadEmp(ids, null).getEmpName());
            }
        }
        return result;
    }

    public String delDepHeadsById(String depId, String empId, int type) {
        try {
            IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
            String hql = "delete from Orgheads where orgheadsEmpNo ='" + empId
                    + "' and orgheadsOrgNo='" + depId + "'";

            empBo.exeHql(hql);
            return "success";
        } catch (Exception e) {
        }
        return "fail";
    }

    public String updateDeptManager(String depId, String empId, int type) {
        try {
            String dataType = (type == 0) ? "deptheader" : "deptdeputy";
            IOrgheadsBo headsBo = (IOrgheadsBo) SpringBeanFactory.getBean("headsBo");
            Orgheads head = new Orgheads();
            head.setOrgheadsEmpNo(empId);
            head.setOrgheadsResponsibility(dataType);
            head.setOrgheadsOrgNo(depId);
            headsBo.insert(head);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }

    public String updateBuManager(String buId, String empId) {
        try {
            IOrgheadsBo headsBo = (IOrgheadsBo) SpringBeanFactory.getBean("headsBo");
            Orgheads head = new Orgheads();
            head.setOrgheadsEmpNo(empId);
            head.setOrgheadsResponsibility("buheader");
            head.setOrgheadsOrgNo(buId);
            headsBo.insert(head);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.action.BasicConfigAction JD-Core Version: 0.5.4
 */