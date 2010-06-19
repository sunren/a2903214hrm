package com.hr.base;

import com.hr.compensation.bo.ICompaplanBo;
import com.hr.compensation.bo.IEmpBenefitPlanBo;
import com.hr.compensation.bo.IEmpSalaryAcctBo;
import com.hr.compensation.bo.IEmpsalaryperiodBo;
import com.hr.compensation.bo.ISalaryconfBo;
import com.hr.configuration.bo.IAttdMachineBO;
import com.hr.configuration.bo.IBenefitTypeBO;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.configuration.bo.IEcptypeBO;
import com.hr.configuration.bo.IEmpTypeBO;
import com.hr.configuration.bo.IJobTitleBo;
import com.hr.configuration.bo.IJobgradeBO;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.ContractType;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Location;
import com.hr.examin.bo.interfaces.ILeavetypeBO;
import com.hr.examin.bo.interfaces.IOvertimetypeBo;
import com.hr.profile.bo.IEmpAddConfBo;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IOrgheadsBo;
import com.hr.profile.bo.IPositionBaseBo;
import com.hr.profile.domain.Employee;
import com.hr.security.bo.AuthorityPosBo;
import com.hr.security.bo.IHasAuthBO;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.ObjectUtil;
import com.hr.util.Pager;
import com.hr.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

public class BaseAction extends ActionSupport implements Constants, Preparable {
    private static final long serialVersionUID = 1982313001L;
    public static final String LOGIN = "login";
    public static final String NOAUTH = "noauth";
    public static final String DOWNLOAD = "download";
    public static final String INVALID_TOKEN = "invalid.token";
    public static final String PARAMS_ERROR = "params_error";
    public static final String ChangeIodefPwd = "TengSourceAdmin";
    protected String authorityCondition;

    public BaseAction() {
        this.authorityCondition = null;
    }

    protected HttpServletRequest getRequest() {
        if (ServletActionContext.getRequest() != null) {
            return ServletActionContext.getRequest();
        }
        return WebContextFactory.get().getHttpServletRequest();
    }

    protected HttpServletResponse getResponse() {
        if (ServletActionContext.getResponse() != null) {
            return ServletActionContext.getResponse();
        }
        return WebContextFactory.get().getHttpServletResponse();
    }

    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    public static Object getBean(String beanName) {
        try {
            return SpringBeanFactory.getBean(beanName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addSuccessInfo(String info) {
        addFieldError("success", info);
    }

    public void addSuccessInfo(String info, Object[] variables) {
        String errContent = StringUtil.messageNoErrCode(info, variables);
        addFieldError("success", errContent);
    }

    public void addErrorInfo(String err) {
        addFieldError("error", err);
    }

    public void addErrorInfo(String err, Object[] variables) {
        String errContent = StringUtil.messageNoErrCode(err, variables);
        addFieldError("error", errContent);
    }

    public void addActionErrorInfo(String err, Object[] variables) {
        String errContent = StringUtil.messageNoErrCode(err, variables);
        addActionError(errContent);
    }

    public void addSuccessInfo(List info) {
        if (info == null) {
            return;
        }
        Iterator iter = info.iterator();
        while (iter.hasNext())
            addFieldError("success", iter.next().toString());
    }

    public void addErrorInfo(List err) {
        if (err == null) {
            return;
        }
        Iterator iter = err.iterator();
        while (iter.hasNext())
            addFieldError("error", iter.next().toString());
    }

    public boolean addSubDC(DetachedCriteria dc, String alias, int module) {
        AuthorityPosBo authposBo = (AuthorityPosBo) getBean("authPosService");
        String[][] deptLocsInCharge = authposBo.getDeptAndLocInCharge(getCurrentPosNo(), Integer
                .valueOf(module));
        if (deptLocsInCharge == null) {
            if (!StringUtils.isEmpty(alias))
                alias = alias + ".";
            BaseCrit.addDC(dc, alias + Employee.PROP_ID, "eq", new String[] { getCurrentEmpNo() });
            return false;
        }

        String[] deptsInCharge = deptLocsInCharge[0];
        String[] locsInCharge = deptLocsInCharge[1];

        BaseCrit.addOrgDC(dc, alias, deptsInCharge, locsInCharge);
        return true;
    }

    public boolean checkEmpInCharge(Employee subEmp, int module) {
        AuthorityPosBo authposBo = (AuthorityPosBo) getBean("authPosService");
        String[][] deptLocsInCharge = authposBo.getDeptAndLocInCharge(getCurrentPosNo(), Integer
                .valueOf(module));

        if (deptLocsInCharge == null) {
            return false;
        }

        if (ObjectUtil.contains(deptLocsInCharge[0], subEmp.getEmpDeptNo().getId())) {
            return true;
        }

        return ObjectUtil.contains(deptLocsInCharge[1], subEmp.getEmpLocationNo().getId());
    }

    public static void addOrders(DetachedCriteria dc, Pager page, String[] defaultOrders) {
        if ((page != null) && (StringUtils.isNotEmpty(page.getOrder()))
                && (page.getOrder().indexOf("-") > 0)) {
            String[] param = page.getOrder().split("-");
            if (param.length > 1) {
                if ("up".equals(param[1]))
                    dc.addOrder(Order.asc(param[0]));
                else {
                    dc.addOrder(Order.desc(param[0]));
                }
            }
        }

        if ((defaultOrders != null) && (defaultOrders.length > 0))
            for (String orders : defaultOrders)
                if (orders != null) {
                    String[] param = orders.split("-");
                    if (param.length > 1)
                        if ("up".equals(param[1]))
                            dc.addOrder(Order.asc(param[0]));
                        else
                            dc.addOrder(Order.desc(param[0]));
                }
    }

    public static List getDrillDown(String className, String[] variables) {
        className = className.toLowerCase();

        if ("attdmachine".equals(className)) {
            IAttdMachineBO attdMachineBO = (IAttdMachineBO) SpringBeanFactory
                    .getBean("attdMachineBO");
            if ((variables == null) || (variables.length == 0) || ("0".equals(variables[0]))) {
                return attdMachineBO.FindEnabledAttdMachine();
            }
            return attdMachineBO.FindAllAttdMachine();
        }

        if ("benefittype".equals(className)) {
            IBenefitTypeBO benefitTypeBo = (IBenefitTypeBO) SpringBeanFactory
                    .getBean("benefitTypeBo");
            return benefitTypeBo.findAll();
        }

        if ("compaplanstatus".equals(className)) {
            ICompaplanBo compaplanBo = (ICompaplanBo) getBean("compaplanBo");
            return compaplanBo.getCompStatus();
        }

        if ("contracttype".equals(className)) {
            IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
            return empBo.getObjects(ContractType.class, null);
        }

        if ("costcenter".equals(className)) {
            ISalaryconfBo salaryConfBo = (ISalaryconfBo) getBean("salaryconfBo");
            return salaryConfBo.getAllCostCenter();
        }

        if ("department".equals(className)) {
            IDepartmentBO deptbo = (IDepartmentBO) getBean("departmentBO");
            if ((variables == null) || (variables.length == 0) || ("0".equals(variables[0]))) {
                return deptbo.FindEnabledDepartment();
            }
            return deptbo.FindAllDepartment();
        }

        if ("ebpyear".equals(className)) {
            IEmpBenefitPlanBo ebpBo = (IEmpBenefitPlanBo) getBean("empbenefitplanBo");
            return ebpBo.getAllYears();
        }

        if ("ecptype".equals(className)) {
            IEcptypeBO ecpbo = (IEcptypeBO) SpringBeanFactory.getBean("ecptypeBO");
            return ecpbo.FindAllEcptype();
        }

        if ("empaddconf".equals(className)) {
            IEmpAddConfBo empAddConfBo = (IEmpAddConfBo) getBean("empAddConfBo");
            if ((variables == null) || (variables.length == 0)) {
                return empAddConfBo.listByTable("empAdditional");
            }
            return empAddConfBo.listByTable(variables[0]);
        }

        if ("empsalaryacct".equals(className)) {
            IEmpSalaryAcctBo esaBo = (IEmpSalaryAcctBo) getBean("empsalaryacctBo");
            return esaBo.searchAllEmpsalaryacctVersionInUse();
        }

        if ("empsalaryperiod".equals(className)) {
            IEmpsalaryperiodBo salaryperiod = (IEmpsalaryperiodBo) getBean("empsalaryperiodBO");
            return salaryperiod.getAllYear();
        }

        if ("empstatus".equals(className)) {
            IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
            return empBo.getEmpStatus();
        }

        if ("emptype".equals(className)) {
            IEmpTypeBO emptypebo = (IEmpTypeBO) SpringBeanFactory.getBean("emptypeBO");
            if ((variables == null) || (variables.length == 0) || ("0".equals(variables[0]))) {
                return emptypebo.FindEnabledEmpType();
            }
            return emptypebo.FindAllEmpType();
        }

        if ("jobgrade".equals(className)) {
            IJobgradeBO gradeBo = (IJobgradeBO) SpringBeanFactory.getBean("jobgradeBO");
            return gradeBo.FindAllJobgrade();
        }

        if ("jobtitle".equals(className)) {
            IJobTitleBo jobTitleBo = (IJobTitleBo) getBean("jobTitleBo");
            if ((variables == null) || (variables.length == 0) || ("0".equals(variables[0]))) {
                return jobTitleBo.FindEnabledJobTitle();
            }
            return jobTitleBo.FindAllJobTitle();
        }

        if ("leavetype".equals(className)) {
            ILeavetypeBO lt_BO = (ILeavetypeBO) getBean("leavetypeBO");
            return lt_BO.FindAllLeaveType();
        }

        if ("location".equals(className)) {
            ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
            if ((variables == null) || (variables.length == 0) || ("0".equals(variables[0]))) {
                return localbo.FindEnabledLocation();
            }
            return localbo.FindAllLocation();
        }

        if ("overtimetype".equals(className)) {
            IOvertimetypeBo ot_BO = (IOvertimetypeBo) getBean("overtimetypeBO");
            return ot_BO.FindAllOtType();
        }

        if ("positionbase".equals(className)) {
            IPositionBaseBo pbBo = (IPositionBaseBo) SpringBeanFactory.getBean("positionBaseBo");
            if ((variables == null) || (variables.length == 0) || ("0".equals(variables[0]))) {
                return pbBo.findAllActivePb();
            }
            return pbBo.findAllPbs();
        }
        return null;
    }

    /** @deprecated */
    public static boolean checkOrgInCharge(Employee emp, String orgId) {
        if ((emp == null) || (orgId == null) || (orgId.trim().equals(""))) {
            return false;
        }
        IOrgheadsBo headsBo = (IOrgheadsBo) getBean("headsBo");
        String hql = "from Orgheads where orgheadsOrgNo='" + orgId + "' and orgheadsEmpNo='"
                + emp.getId() + "'";

        return headsBo.exeHqlList(hql).size() > 0;
    }

    /** @deprecated */
    public static boolean checkDeptInCharge(Employee emp, Department dept) {
        if ((emp == null) || (dept == null) || (emp.getId() == null) || (dept.getId() == null)) {
            return false;
        }
        return checkOrgInCharge(emp, dept.getId());
    }

    /** @deprecated */
    public static boolean checkAreaInCharge(Employee emp, Location location) {
        if ((emp == null) || (location == null) || (emp.getId() == null)
                || (location.getId() == null)) {
            return false;
        }
        return checkOrgInCharge(emp, location.getId());
    }

    /** @deprecated */
    public static boolean checkAreaInCharge(Employee employer, Employee employee) {
        if ((employer == null) || (employee == null))
            return false;
        try {
            IEmployeeBo employeeBo = (IEmployeeBo) getBean("empBo");
            employee = employeeBo.loadEmp(employee.getId(), null);
            IOrgheadsBo headsBo = (IOrgheadsBo) getBean("headsBo");

            String hql = "from Orgheads where orgheadsOrgNo='"
                    + employee.getEmpLocationNo().getId() + "' and orgheadsEmpNo='"
                    + employer.getId() + "'";

            return headsBo.exeHqlList(hql).size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /** @deprecated */
    public static boolean checkDeptInCharge(Employee emp, Employee employee) {
        if ((emp == null) || (employee == null)) {
            return false;
        }
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        employee = empBo.loadEmp(employee.getId(), new String[] { Employee.PROP_EMP_DEPT_NO });
        Department dept = employee.getEmpDeptNo();
        if ((dept == null)
                || ((dept.getDepartmentHead() == null) && (dept.getDepartmentOtherHeads() == null))) {
            return false;
        }
        String head = dept.getDepartmentHead();
        String otherHeads = dept.getDepartmentOtherHeads();
        if ((head != null) && (head.equals(emp.getId()))) {
            return true;
        }

        return (otherHeads != null) && (otherHeads.indexOf(emp.getId()) != -1);
    }

    /** @deprecated */
    public static boolean hasHeader(String orgId) {
        if (orgId == null) {
            return false;
        }
        IOrgheadsBo headsBo = (IOrgheadsBo) getBean("headsBo");
        String hql = "from Orgheads where orgheadsOrgNo='" + orgId + "'";
        return headsBo.exeHqlList(hql).size() > 0;
    }

    public boolean hasAuth(int moduleId) {
        IHasAuthBO bo = (IHasAuthBO) SpringBeanFactory.getBean("hasService");
        UsersAuthority userAuthority = new UsersAuthority(String.valueOf(moduleId));
        boolean has = false;
        try {
            has = bo.hasActionAuth(userAuthority);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return has;
    }

    public boolean hasAuthModuleCondition(int moduleId, int condition) {
        IHasAuthBO bo = (IHasAuthBO) SpringBeanFactory.getBean("hasService");
        UsersAuthority userAuthority = new UsersAuthority(String.valueOf(moduleId), String
                .valueOf(condition));

        boolean has = false;
        try {
            has = bo.hasActionAuth(userAuthority);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return has;
    }

    public boolean hasMultipleAuth(String authString) {
        if (authString == null)
            return false;
        IHasAuthBO hasAuthBO = (IHasAuthBO) SpringBeanFactory.getBean("hasService");
        String[] authArray = authString.split("#");
        List list = new ArrayList();
        String checkAuth = "";

        for (int i = 0; i < authArray.length; ++i) {
            checkAuth = authArray[i];
            if (checkAuth.indexOf(",") == -1) {
                UsersAuthority userAuthority = new UsersAuthority(checkAuth);
                list.add(userAuthority);
            } else {
                UsersAuthority userAuthority = new UsersAuthority(checkAuth.substring(0, checkAuth
                        .indexOf(",")), checkAuth.substring(checkAuth.indexOf(",") + 1));

                list.add(userAuthority);
            }
        }

        try {
            return hasAuthBO.hasActionAuth(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String checkActionAuth(String actionName, String methodName) {
        IHasAuthBO bo = (IHasAuthBO) SpringBeanFactory.getBean("hasService");
        List userAuthorityList = ActionAuths.getActionAuths(actionName, methodName);
        if ((userAuthorityList == null) || (userAuthorityList.size() < 1)) {
            return null;
        }
        Iterator iterator = userAuthorityList.iterator();
        while (iterator.hasNext()) {
            UsersAuthority userAuthority = (UsersAuthority) iterator.next();
            try {
                if (bo.hasActionAuth(userAuthority)) {
                    String filter = userAuthority.getFilter();
                    return filter;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }
        return "error";
    }

    public boolean checkAuth(String empId) {
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);
        addSubDC(dc, "", 1);
        BaseCrit.addDC(dc, Employee.PROP_ID, "eq", new String[] { empId });

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        List list = empBo.findByCriteria(dc);

        return (list != null) && (!list.isEmpty());
    }

    public String getCurrentPosNo() {
        return (String) getSession().getAttribute("positionId");
    }

    public String getCurrentEmpNo() {
        return ((Userinfo) getSession().getAttribute("userinfo")).getId();
    }

    public Employee getCurrentEmp() {
        return ((Userinfo) getSession().getAttribute("userinfo")).getEmployee();
    }

    public void setAuthorityCondition(String authorityCondition) {
        this.authorityCondition = authorityCondition;
    }

    public void prepare() throws Exception {
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.base.BaseAction JD-Core Version: 0.5.4
 */