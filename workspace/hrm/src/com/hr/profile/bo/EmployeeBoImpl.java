package com.hr.profile.bo;

import com.hr.base.Constants;
import com.hr.base.FileOperate;
import com.hr.compensation.domain.Empbenefit;
import com.hr.compensation.domain.Empsalaryadj;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.configuration.bo.IClientBO;
import com.hr.configuration.bo.IEmailsendBO;
import com.hr.configuration.dao.IStatusDAO;
import com.hr.configuration.domain.BenefitType;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emailsend;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Location;
import com.hr.configuration.domain.Statusconf;
import com.hr.examin.domain.Attendmonthly;
import com.hr.examin.domain.Leavebalance;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Overtimerequest;
import com.hr.profile.dao.IEmployeeDAO;
import com.hr.profile.domain.Empcontract;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empquit;
import com.hr.profile.domain.Emptransfer;
import com.hr.profile.domain.Orgheads;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.PositionBase;
import com.hr.profile.domain.SimpleEmployee;
import com.hr.report.action.profile.support.EmpSumReport;
import com.hr.security.bo.RoleBo;
import com.hr.security.bo.UserBo;
import com.hr.security.domain.Role;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.training.domain.Tremployeeplan;
import com.hr.util.DateUtil;
import com.hr.util.GenericValidator;
import com.hr.util.Pager;
import com.hr.util.StringUtil;
import com.jenkov.prizetags.tree.impl.Tree;
import com.jenkov.prizetags.tree.impl.TreeNode;
import com.jenkov.prizetags.tree.itf.ITree;
import com.jenkov.prizetags.tree.itf.ITreeNode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class EmployeeBoImpl implements IEmployeeBo, Constants {
    private IEmployeeDAO employeeDAO;
    private IStatusDAO statusDAO;

    public IEmployeeDAO getEmployeeDAO() {
        return this.employeeDAO;
    }

    public List<Statusconf> getEmpStatus() {
        return this.statusDAO.getStatusByTable("employee");
    }

    public Employee loadEmpByDistinctNo(String empDistinctNo) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.eq(Employee.PROP_EMP_DISTINCT_NO, empDistinctNo));
        List list = this.employeeDAO.findByCriteria(detachedCriteria);
        if (list.size() == 1) {
            return (Employee) list.get(0);
        }
        return null;
    }

    public List exeHqlList(String hql, int[] page) {
        if (page.length == 0) {
            return this.employeeDAO.exeHqlList(hql);
        }
        int currPage = 1;
        if (page.length > 1) {
            currPage = page[1];
        }
        return this.employeeDAO.exeHqlList(hql, page[0], currPage);
    }

    public List exeHqlList(String hql, String[] fields, String[] conditions) {
        return this.employeeDAO.exeHqlList(hql, fields, conditions);
    }

    public Object exeHql(String hql) {
        return this.employeeDAO.exeHql(hql);
    }

    public List findByCriteria(DetachedCriteria detachedCriteria) {
        return this.employeeDAO.findByCriteria(detachedCriteria);
    }

    public <T> void saveOrupdate(Collection<T> objs) {
        this.employeeDAO.saveOrupdate(objs);
    }

    public void setEmployeeDAO(IEmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public List<String> deleteEmp(Employee emp) {
        List errors = checkEmp(emp, "delete");
        if (errors.size() < 1) {
            FileOperate.deleteFile("sys.user.image.path", emp.getEmpImage());

            if (StringUtils.isNotEmpty(emp.getEmpResume1())) {
                FileOperate.deleteFile("sys.profile.file.path", emp.getEmpResume1());
            }
            if (StringUtils.isNotEmpty(emp.getEmpResume2())) {
                FileOperate.deleteFile("sys.profile.file.path", emp.getEmpResume2());
            }

            String deleteHistoryEduHql = "delete from Emphistoryedu as ee where ee.employee.id='"
                    + emp.getId() + "'";
            this.employeeDAO.updateHqlQuery(deleteHistoryEduHql);

            String deleteHistoryJobHql = "delete from Emphistoryjob as eh where eh.employee.id='"
                    + emp.getId() + "'";
            this.employeeDAO.updateHqlQuery(deleteHistoryJobHql);

            String deleteHistoryTrainingHql = "delete from Emphistorytrain as et where et.employee.id='"
                    + emp.getId() + "'";
            this.employeeDAO.updateHqlQuery(deleteHistoryTrainingHql);

            String deleteTransferHql = "delete from Emptransfer as e where e.employee.id='"
                    + emp.getId() + "'";
            this.employeeDAO.updateHqlQuery(deleteTransferHql);

            String deleteEvalHql = "delete from Empeval as e where e.employeeByEeEmpNo.id='"
                    + emp.getId() + "'";
            this.employeeDAO.updateHqlQuery(deleteEvalHql);

            String deleteRewardHql = "delete from Empreward as r where r.employee.id='"
                    + emp.getId() + "'";
            this.employeeDAO.updateHqlQuery(deleteRewardHql);

            String deleteQuitHql = "delete from Empquit as e where e.employee.id='" + emp.getId()
                    + "'";
            this.employeeDAO.updateHqlQuery(deleteQuitHql);

            String deleteUserHql = "delete from Userinfo as u where u.id='" + emp.getId() + "'";
            this.employeeDAO.updateHqlQuery(deleteUserHql);

            String deleteEmpHistOrg = "delete from EmpHistOrg where ehoEmpNo.id='" + emp.getId()
                    + "'";
            this.employeeDAO.updateHqlQuery(deleteEmpHistOrg);

            String deleteEmpPosition = "update Position set positionEmpNo.id=null where positionEmpNo.id='"
                    + emp.getId() + "'";
            this.employeeDAO.updateHqlQuery(deleteEmpPosition);

            String deleteEmpHql = "delete Employee as e where e.id='" + emp.getId() + "'";
            this.employeeDAO.updateHqlQuery(deleteEmpHql);
        }

        return errors;
    }

    public List getEmployees() {
        List result = this.employeeDAO
                .exeHqlList("select employee.id,employee.empSupNo.id from Employee employee");

        return result;
    }

    public <T> List<T> getObjects(Class<T> clas, String[] fetch) {
        List result = this.employeeDAO.getObjects(clas, fetch);
        return result;
    }

    public List<Employee> getOrderEmps(String fieldName, String methods) {
        return this.employeeDAO.exeHqlList("from Employee order by " + fieldName + " " + methods);
    }

    private List<String> checkEmp(Employee emp, String perat) {
        List errors = new ArrayList();
        if (!perat.equalsIgnoreCase("delete")) {
            if ((emp.getEmpBirthDate() != null)
                    && (emp.getEmpBirthDate().getTime() > new Date().getTime() - 86400000L)) {
                errors.add("出生日期必须小于今天＄1�7");
            }
            if ((emp.getEmpBirthDate() != null) && (emp.getEmpWorkDate() != null)
                    && (emp.getEmpBirthDate().compareTo(emp.getEmpWorkDate()) > 0)) {
                errors.add("出生日期不能晚于参加工作日期＄1�7");
            }
            if ((emp.getEmpJoinDate() != null) && (emp.getEmpWorkDate() != null)
                    && (emp.getEmpJoinDate().compareTo(emp.getEmpWorkDate()) < 0)) {
                errors.add("参加工作日期不能晚于入职日期＄1�7");
            }
            if ((emp.getEmpConfirmDate() != null) && (emp.getEmpJoinDate() != null)
                    && (emp.getEmpConfirmDate().compareTo(emp.getEmpJoinDate()) < 0)) {
                errors.add("入职日期不能晚于转正日期＄1�7");
            }
            if ((emp.getEmpWorkPhone() != null) && (emp.getEmpWorkPhone().trim().length() > 0)
                    && (!GenericValidator.isPhone(emp.getEmpWorkPhone()))) {
                errors.add("工作电话格式错误！只允许数字、\"-\"＄1�7");
            }
            if ((emp.getEmpHomePhone() != null) && (emp.getEmpHomePhone().trim().length() > 0)
                    && (!GenericValidator.isPhone(emp.getEmpHomePhone()))) {
                errors.add("家庭电话格式错误！只允许数字、\"-\"、\"_\"＄1�7");
            }
        }

        if (perat.equals("update")) {
            String hql = "from Employee where empDistinctNo = '" + emp.getEmpDistinctNo().trim()
                    + "' and id <> '" + emp.getId() + "'";

            List emps = this.employeeDAO.exeHqlList(hql);
            if ((emps != null) && (emps.size() > 0)) {
                errors.add("员工编号" + emp.getEmpDistinctNo().trim() + "已经存在＄1�7");
            }

            if (StringUtils.isNotEmpty(emp.getEmpShiftNo())) {
                String sql = "from Employee where empShiftNo = '" + emp.getEmpShiftNo().trim()
                        + "' and id <> '" + emp.getId() + "'";

                List list = this.employeeDAO.exeHqlList(sql);
                if (!list.isEmpty()) {
                    errors.add("考勤卡号" + emp.getEmpShiftNo().trim() + "已经存在＄1�7");
                }
            }
            return errors;
        }
        if (perat.equals("create")) {
            String hql = "from Employee where empDistinctNo = '" + emp.getEmpDistinctNo().trim()
                    + "'";
            List emps = this.employeeDAO.exeHqlList(hql);
            if ((emps != null) && (emps.size() > 0)) {
                errors.add("员工编号" + emp.getEmpDistinctNo().trim() + "已经存在＄1�7");
            }

            if (StringUtils.isNotEmpty(emp.getEmpShiftNo())) {
                String sql = "from Employee where empShiftNo = '" + emp.getEmpShiftNo().trim()
                        + "'";
                List list = this.employeeDAO.exeHqlList(sql);
                if (!list.isEmpty()) {
                    errors.add("考勤卡号" + emp.getEmpShiftNo().trim() + "已经存在＄1�7");
                }
            }
            return errors;
        }
        if (perat.equals("delete")) {
            int count = 0;
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Orgheads.class);

            detachedCriteria = DetachedCriteria.forClass(Empcontract.class);
            detachedCriteria.add(Restrictions.eq("employee.id", emp.getId()));
            count = this.employeeDAO.findRowCountByCriteria(detachedCriteria);
            if (count > 0) {
                errors.add(emp.getEmpName().trim() + "存在员工人事合同信息＄1�7");
            }

            detachedCriteria = DetachedCriteria.forClass(Empsalaryadj.class);
            detachedCriteria.add(Restrictions.eq("esaEmpno.id", emp.getId()));
            count = this.employeeDAO.findRowCountByCriteria(detachedCriteria);
            if (count > 0) {
                errors.add(emp.getEmpName().trim() + "存在调薪计划＄1�7");
            }

            detachedCriteria = DetachedCriteria.forClass(Empsalaryconfig.class);
            detachedCriteria.add(Restrictions.eq("id", emp.getId()));
            count = this.employeeDAO.findRowCountByCriteria(detachedCriteria);
            if (count > 0) {
                errors.add(emp.getEmpName().trim() + "存在薪资信息＄1�7");
            }

            detachedCriteria = DetachedCriteria.forClass(Empsalarypay.class);
            detachedCriteria.add(Restrictions.eq("espEmpno.id", emp.getId()));
            count = this.employeeDAO.findRowCountByCriteria(detachedCriteria);
            if (count > 0) {
                errors.add(emp.getEmpName().trim() + "有薪资发放记录！");
            }

            detachedCriteria = DetachedCriteria.forClass(Empbenefit.class);

            detachedCriteria.createAlias("employee", "employee").add(
                                                                     Restrictions.eq("employee.id",
                                                                                     emp.getId()));

            count = this.employeeDAO.findRowCountByCriteria(detachedCriteria);
            if (count > 0) {
                errors.add(emp.getEmpName().trim() + "有社保记录！");
            }

            detachedCriteria = DetachedCriteria.forClass(Leavebalance.class);
            detachedCriteria.add(Restrictions.eq("lbEmpNo.id", emp.getId()));
            count = this.employeeDAO.findRowCountByCriteria(detachedCriteria);
            if (count > 0) {
                errors.add(emp.getEmpName().trim() + "有年假记录！");
            }

            detachedCriteria = DetachedCriteria.forClass(Leaverequest.class);
            detachedCriteria.add(Restrictions.eq("lrEmpNo.id", emp.getId()));
            count = this.employeeDAO.findRowCountByCriteria(detachedCriteria);
            if (count > 0) {
                errors.add(emp.getEmpName().trim() + "有请假信息！");
            }

            detachedCriteria = DetachedCriteria.forClass(Overtimerequest.class);
            detachedCriteria.add(Restrictions.eq("orEmpNo.id", emp.getId()));
            count = this.employeeDAO.findRowCountByCriteria(detachedCriteria);
            if (count > 0) {
                errors.add(emp.getEmpName().trim() + "有加班信息！");
            }

            detachedCriteria = DetachedCriteria.forClass(Attendmonthly.class);
            detachedCriteria.add(Restrictions.eq("attmEmpId.id", emp.getId()));
            count = this.employeeDAO.findRowCountByCriteria(detachedCriteria);
            if (count > 0) {
                errors.add(emp.getEmpName().trim() + "有每月�1�7�勤数据＄1�7");
            }

            detachedCriteria = DetachedCriteria.forClass(Tremployeeplan.class);
            detachedCriteria.add(Restrictions.eq("trpTraineeNo.id", emp.getId()));
            count = this.employeeDAO.findRowCountByCriteria(detachedCriteria);
            if (count > 0) {
                errors.add(emp.getEmpName().trim() + "有培训计划！");
            }

            return errors;
        }

        return new ArrayList();
    }

    public void autoFill(Employee emp, Employee user) {
        if (emp.getEmpCreateBy() == null) {
            emp.setEmpCreateBy(user);
            emp.setEmpCreateTime(new Date());
        }
        emp.setEmpLastChangeBy(user);
        emp.setEmpLastChangeTime(new Date());
    }

    public boolean saveOrUpdateEmpInfo(List<Employee> empList, List<Empquit> quitList,
            List<Empcontract> contractList, List<Position> posList) {
        try {
            this.employeeDAO.saveOrupdate(empList);
            this.employeeDAO.saveOrupdate(quitList);
            this.employeeDAO.saveOrupdate(contractList);
            this.employeeDAO.saveOrupdate(posList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String saveCreateEmp(Employee emp, Employee currentEmp, String createUser) {
        List errors = insertEmp(emp, currentEmp);
        if (errors.size() > 0)
            return (String) errors.get(0);

        String info = "SUCC";
        if ("true".equals(createUser)) {
            info = createUser(emp, currentEmp);
        }

        return info;
    }

    public List<String> insertEmp(Employee emp, Employee currentEmp) {
        List errors = checkEmp(emp, "create");
        if (!errors.isEmpty())
            return errors;

        formatEmployeeInfo(emp);
        emp.setEmpDistinctNo(emp.getEmpDistinctNo().trim());
        emp.setEmpName(emp.getEmpName().trim());
        emp.setEmpCreateTime(new Date());
        emp.setEmpCreateBy(currentEmp);
        emp.setEmpLastChangeBy(currentEmp);
        emp.setEmpLastChangeTime(new Date());
        String id = UUID.randomUUID().toString();
        emp.setId(id);

        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        Position pos = posBo.getPosById(emp.getPosition().getId(),
                                        new String[] { Position.PROP_POSITION_PB_ID });

        if ((pos.getPositionPbId().getPbInCharge().intValue() == 1)
                && (pos.getPositionEmpNo() != null)) {
            errors.add("负责职位不空缺，不能修改＄1�7");
            return errors;
        }

        emp.setEmpPbNo(pos.getPositionPbId());

        emp.setEmpMachineNo(findEmployeeMaxMachineNo());
        this.employeeDAO.saveObject(emp);

        String parentPosId = (pos.getPositionParentId() != null) ? pos.getPositionParentId()
                .getId() : null;
        String info = posBo.batchSetEmpPos(parentPosId, pos.getPositionPbId().getId(),
                                           new String[] { emp.getId() });
        if (!"SUCC".equals(info))
            errors.add(info);

        IEmpHistOrgBo emphistorgBo = (IEmpHistOrgBo) SpringBeanFactory.getBean("empHistOrgBo");
        emphistorgBo.saveEmpHistOrg(emp, emp.getEmpDeptNo(), emp.getEmpPbNo());

        return errors;
    }

    public String createUser(Employee emp, Employee currentEmp) {
        RoleBo roleBo = (RoleBo) SpringBeanFactory.getBean("roleService");
        IClientBO clientLimit = (IClientBO) SpringBeanFactory.getBean("clientBo");
        List defRoles = roleBo.getRoleList(Constants.DEFAULT_USER_AUTHORITY, "SortId");
        Integer[] defaultRoles = new Integer[defRoles.size()];
        for (int i = 0; i < defRoles.size(); ++i) {
            defaultRoles[i] = Integer.valueOf(((Role) defRoles.get(i)).getRoleNo());
        }

        String limit = clientLimit.checkUserLimit(defaultRoles, 1);
        if (!"success".equals(limit)) {
            return limit;
        }

        String DEFAULT_USER_PASSWORD = RandomStringUtils.random(6, true, true);
        UserBo userService = (UserBo) SpringBeanFactory.getBean("userService");
        Userinfo user = new Userinfo();
        user.setId(emp.getId());
        user.setUiUsername(emp.getEmpDistinctNo());
        user.setUiPasswordEncrypt(StringUtil.encodePassword(DEFAULT_USER_PASSWORD));
        user.setUiStatus(Integer.valueOf(1));
        user.setUiLevelRestrict(Integer.valueOf(0));
        user.setUiCreateBy(emp);
        user.setUiCreateTime(new Date());
        user.setUiLastChangeBy(emp);
        user.setUiLastChangeTime(new Date());
        user.setEmployee(emp);

        boolean insertSuccess = userService.addUser(user, DEFAULT_USER_AUTHORITY, currentEmp
                .getId());
        if (!insertSuccess)
            return "添加用户失败!";
        if (StringUtils.isEmpty(emp.getEmpEmail()))
            return "SUCC";

        IEmailsendBO emailsendBo = (IEmailsendBO) SpringBeanFactory.getBean("emailsendBO");
        Map params = new HashMap();
        params.put("SENDER", currentEmp);
        params.put("URL", "configuration/login.jsp");
        params.put("APPLIER", emp);
        params.put("USERNAME", emp.getEmpDistinctNo());
        params.put("PASSWORD", DEFAULT_USER_PASSWORD);
        Emailsend send = new Emailsend();
        send.setEsCc(currentEmp.getEmpEmail());
        send.setEsTo(emp.getEmpEmail());
        Map contentMap = emailsendBo.getEmailContent("NewUser", params);
        send.setEsTitle(((String) contentMap.get("email_title")).toString());
        send.setEsContent(((String) contentMap.get("email_content")).toString());
        emailsendBo.sendEmail(send);
        return "SUCC";
    }

    private void formatEmployeeInfo(Employee emp) {
        if ((emp.getConfig() != null) && (StringUtils.isEmpty(emp.getConfig().getId()))) {
            emp.setConfig(null);
        }
        if ((emp.getContract() != null) && (StringUtils.isEmpty(emp.getContract().getEctId()))) {
            emp.setContract(null);
        }
        if ((emp.getEmpBenefitType() != null)
                && (StringUtils.isEmpty(emp.getEmpBenefitType().getId()))) {
            emp.setEmpBenefitType(null);
        }
        if ((emp.getQuit() != null) && (StringUtils.isEmpty(emp.getQuit().getEqId()))) {
            emp.setQuit(null);
        }
        if ((emp.getBenefit() != null) && (StringUtils.isEmpty(emp.getBenefit().getEbfId()))) {
            emp.setBenefit(null);
        }

        if ("".equals(emp.getEmpPolitics())) {
            emp.setEmpPolitics(null);
        }
        if ("".equals(emp.getEmpDiploma())) {
            emp.setEmpDiploma(null);
        }
        if ("".equals(emp.getEmpBlood()))
            emp.setEmpBlood(null);
    }

    public Employee loadEmp(Object id, String[] fetch) {
        return (Employee) this.employeeDAO.loadObject(Employee.class, id, fetch,
                                                      new boolean[] { false });
    }

    public List<String> batchUpdateOrDel(Employee emp, Pager page, String empNo) {
        List errors = new ArrayList();
        if ((emp.getIds() == null) || (emp.getIds().length() < 1)) {
            return errors;
        }
        String[] changIds = emp.getIds().split(",");
        Employee temp = null;

        if ("update".equalsIgnoreCase(page.getOperate())) {
            for (int len = 0; len < changIds.length; ++len) {
                if ((changIds[len] == null) || (changIds[len].length() < 1))
                    continue;
                if ("0".equals(changIds[len].trim())) {
                    continue;
                }

                String[] tempArray = changIds[len].trim().split("&");
                temp = (Employee) this.employeeDAO.loadObject(Employee.class, tempArray[0], null,
                                                              new boolean[0]);
                if (temp == null)
                    continue;
                if (temp.getId() == null) {
                    continue;
                }

                List err = checkEmp(temp, "update");
                if ((err == null) || (err.size() < 1)) {
                    if ((emp.getDeptNo() != null) && (emp.getDeptNo().length() > 1)) {
                        temp.setEmpDeptNo(new Department(emp.getDeptNo()));
                    }
                    if ((emp.getLocationNo() != null) && (emp.getLocationNo().trim().length() > 0)) {
                        temp.setEmpLocationNo(new Location(emp.getLocationNo().trim()));
                    }
                    if ((emp.getTypeNo() != null) && (emp.getTypeNo().trim().length() > 0)) {
                        temp.setEmpType(new Emptype(emp.getTypeNo().trim()));
                    }
                    if ((emp.getEmpProfileLoc() != null)
                            && (emp.getEmpProfileLoc().trim().length() > 0)) {
                        temp.setEmpProfileLoc(emp.getEmpProfileLoc().trim());
                    }
                    if ((emp.getEmpShiftType() != null)
                            && (!emp.getEmpShiftType().equals(new Integer(0)))) {
                        if (emp.getEmpShiftType().equals(Integer.valueOf(1)))
                            temp.setEmpShiftType(Integer.valueOf(0));
                        else {
                            temp.setEmpShiftType(emp.getEmpShiftType());
                        }
                    }
                    if (empNo != null) {
                        Employee empLastChangeBy = new Employee();
                        empLastChangeBy.setId(empNo);
                        temp.setEmpLastChangeBy(empLastChangeBy);
                    }
                    temp.setEmpLastChangeTime(new Date());

                    this.employeeDAO.updateObject(temp);
                } else {
                    errors.addAll(err);
                }
            }
            page.setOperate(null);
        }

        if ("delete".equalsIgnoreCase(page.getOperate())) {
            for (int len = 0; len < changIds.length; ++len) {
                if ((changIds[len] == null) || (changIds[len].length() < 1))
                    continue;
                if ("0".equals(changIds[len].trim())) {
                    continue;
                }

                String[] tempArray = changIds[len].trim().split("&");
                temp = (Employee) this.employeeDAO.loadObject(Employee.class, tempArray[0], null,
                                                              new boolean[0]);
                if (temp == null)
                    continue;
                if (temp.getId() == null) {
                    continue;
                }
                List tempError = deleteEmp(temp);
                errors.addAll(tempError);
            }
            page.setOperate(null);
        }
        return errors;
    }

    public List<Employee> searchAndExportEmp(DetachedCriteria dc, Pager page, String searchOrExport) {
        if ((page == null) || ("export".equals(searchOrExport))) {
            return this.employeeDAO.findByCriteria(dc);
        }

        page.splitPage(dc);
        return this.employeeDAO.findByCriteria(dc, page.getPageSize(), page.getCurrentPage());
    }

    public List<String> updateEmpByempInfo(Employee emp, String empNo) {
        List errors = checkEmp(emp, "update");
        if (!errors.isEmpty())
            return errors;

        formatEmployeeInfo(emp);
        if (empNo != null)
            emp.setEmpLastChangeBy(new Employee(empNo));

        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        Position pos = posBo.getPosById(emp.getPosition().getId(),
                                        new String[] { Position.PROP_POSITION_PB_ID });

        Employee empInDB = loadEmp(emp.getId(), null);
        if ((empInDB != null) && (empInDB.getEmpPbNo() != null)
                && (!empInDB.getEmpPbNo().getId().equals(pos.getPositionPbId().getId()))) {
            if (empInDB.getEmpStatus().intValue() == 1) {
                if ((pos.getPositionPbId().getPbInCharge().intValue() == 1)
                        && (pos.getPositionEmpNo() != null)) {
                    errors.add("负责职位不空缺，不能修改＄1�7");
                    return errors;
                }

                String parentPosId = (pos.getPositionParentId() != null) ? pos
                        .getPositionParentId().getId() : null;
                posBo.batchSetEmpPos(parentPosId, pos.getPositionPbId().getId(), new String[] { emp
                        .getId() });
            }

            IEmpHistOrgBo emphistorgBo = (IEmpHistOrgBo) SpringBeanFactory.getBean("empHistOrgBo");
            emphistorgBo.saveLatestEmpHistOrg(empInDB, pos.getPositionPbId().getPbDeptId(), pos
                    .getPositionPbId());
        }

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String hql = "update Employee set empDistinctNo='"
                + emp.getEmpDistinctNo().trim()
                + "' , empName='"
                + emp.getEmpName().trim()
                + "' , empFname="
                + ((emp.getEmpFname() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpFname().trim()).append("'").toString())
                + " , empMname="
                + ((emp.getEmpMname() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpMname().trim()).append("'").toString())
                + " , empLname="
                + ((emp.getEmpLname() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpLname().trim()).append("'").toString())
                + " , empBirthDate='"
                + dateformat.format(emp.getEmpBirthDate())
                + "' , empMarital="
                + emp.getEmpMarital()
                + " , empGender="
                + emp.getEmpGender()
                + " , empBlood="
                + ((emp.getEmpBlood() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpBlood().trim()).append("'").toString())
                + " , empPolitics="
                + ((emp.getEmpPolitics() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpPolitics().trim()).append("'").toString())
                + " , empDiploma="
                + ((emp.getEmpDiploma() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpDiploma().trim()).append("'").toString())
                + " , empSchool="
                + ((emp.getEmpSchool() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpSchool().trim()).append("'").toString())
                + " , empSpeciality="
                + ((emp.getEmpSpeciality() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpSpeciality().trim()).append("'").toString())
                + " , empCityNo="
                + ((emp.getEmpCityNo() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpCityNo().trim()).append("'").toString())
                + " , empNation="
                + ((emp.getEmpNation() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpNation().trim()).append("'").toString())
                + " , empResidenceLoc="
                + ((emp.getEmpResidenceLoc() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpResidenceLoc().trim()).append("'").toString())
                + " , empProfileLoc="
                + ((emp.getEmpProfileLoc() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpProfileLoc().trim()).append("'").toString())
                + " , empIdentificationType="
                + emp.getEmpIdentificationType()
                + " , empIdentificationNo='"
                + emp.getEmpIdentificationNo().trim()
                + "' , empBenefitType.id="
                + ((emp.getEmpBenefitType() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpBenefitType().getId()).append("'").toString())
                + " , empHomePhone="
                + ((emp.getEmpHomePhone() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpHomePhone().trim()).append("'").toString())
                + " , empMobile="
                + ((emp.getEmpMobile() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpMobile().trim()).append("'").toString())
                + " , empCurrAddr="
                + ((emp.getEmpCurrAddr() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpCurrAddr().trim()).append("'").toString())
                + " , empCurrZip="
                + ((emp.getEmpCurrZip() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpCurrZip().trim()).append("'").toString())
                + " , empHomeAddr="
                + ((emp.getEmpHomeAddr() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpHomeAddr().trim()).append("'").toString())
                + " , empHomeZip="
                + ((emp.getEmpHomeZip() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpHomeZip().trim()).append("'").toString())
                + " , empEmail="
                + ((emp.getEmpEmail() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpEmail().trim()).append("'").toString())
                + " , empMsn="
                + ((emp.getEmpMsn() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpMsn().trim()).append("'").toString())
                + " , empWorkPhone="
                + ((emp.getEmpWorkPhone() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpWorkPhone().trim()).append("'").toString())
                + " , empShiftType="
                + ((emp.getEmpShiftType() == null) ? "null" : emp.getEmpShiftType())
                + " , empShiftNo="
                + ((emp.getEmpShiftNo() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpShiftNo().trim()).append("'").toString())
                + " , empType.id='"
                + emp.getEmpType().getId()
                + "' , empImage="
                + ((emp.getEmpImage() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpImage().trim()).append("'").toString())
                + " , empWorkDate="
                + ((emp.getEmpWorkDate() == null) ? "null" : new StringBuilder().append("'")
                        .append(dateformat.format(emp.getEmpWorkDate())).append("'").toString())
                + " , empJoinDate='"
                + dateformat.format(emp.getEmpJoinDate())
                + "' , empConfirmDate="
                + ((emp.getEmpConfirmDate() == null) ? "null" : new StringBuilder().append("'")
                        .append(dateformat.format(emp.getEmpConfirmDate())).append("'").toString())
                + " , empLocationNo.id='"
                + emp.getEmpLocationNo().getId()
                + "' , empDeptNo.id='"
                + emp.getEmpDeptNo().getId()
                + "' , empPbNo.id='"
                + pos.getPositionPbId().getId()
                + "' , empUrgentContact="
                + ((emp.getEmpUrgentContact() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpUrgentContact().trim()).append("'").toString())
                + " , empUrgentConMethod="
                + ((emp.getEmpUrgentConMethod() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpUrgentConMethod().trim()).append("'").toString())
                + " , empComments="
                + ((emp.getEmpComments() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpComments().trim()).append("'").toString())
                + " , empLastChangeBy.id="
                + ((emp.getEmpLastChangeBy() == null) ? "null" : new StringBuilder().append("'")
                        .append(emp.getEmpLastChangeBy().getId()).append("'").toString())
                + " , empLastChangeTime='" + dateformat2.format(new Date()) + "' where id='"
                + emp.getId() + "'";

        this.employeeDAO.updateHqlQuery(hql);

        return errors;
    }

    public List<String> updateEmp(Employee emp, String empNo) {
        List errors = checkEmp(emp, "update");
        if (!errors.isEmpty()) {
            return errors;
        }
        formatEmployeeInfo(emp);
        emp.setEmpName(emp.getEmpName().trim());
        if (empNo != null) {
            Employee employee = new Employee();
            employee.setId(empNo);
            emp.setEmpLastChangeBy(employee);
        }
        emp.setEmpLastChangeTime(new Date());
        this.employeeDAO.updateObject(emp);
        return errors;
    }

    public List<Employee> searchEmpContract(String[] empNos) {
        if ((empNos == null) || (empNos.length == 0))
            return new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.setFetchMode(Employee.PROP_EMP_CONTRACT, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.in(Employee.PROP_ID, empNos));
        List result = this.employeeDAO.findByCriteria(detachedCriteria);
        return result;
    }

    public List<Employee> searchEmpArray(String[] empNos) {
        if ((empNos == null) || (empNos.length == 0))
            return new ArrayList();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.in(Employee.PROP_ID, empNos));
        List result = this.employeeDAO.findByCriteria(detachedCriteria);
        return result;
    }

    public IStatusDAO getStatusDAO() {
        return this.statusDAO;
    }

    public void setStatusDAO(IStatusDAO statusDAO) {
        this.statusDAO = statusDAO;
    }

    public List getAllRootNodes() {
        List empList = this.employeeDAO
                .exeHqlList("select id, empName, empType.id, empDistinctNo from Employee  where empStatus=1 and (empSupNo is null or empSupNo.id=id) order by empName asc");

        return empList;
    }

    public ITree generateAllTree(List roots) {
        ITree tree = new Tree();
        tree.setSingleSelectionMode(true);

        ITreeNode root = new TreeNode("rootId", "组织结构", "root");
        tree.setRoot(root);

        List emps = findEmpWithActiveStatus();

        Iterator iter = roots.iterator();
        while (iter.hasNext()) {
            List list = new ArrayList();
            ITreeNode rootNode = new TreeNode();
            Object[] emp = (Object[]) (Object[]) iter.next();
            rootNode.setId((String) emp[0]);
            rootNode.setName((String) emp[1]);
            rootNode.setType((String) emp[2]);

            root.addChild(rootNode);
            rootNode.setToolTip("NO:" + (String) emp[3]);

            list.add(rootNode);

            while (!list.isEmpty()) {
                ITreeNode node = new TreeNode();
                node = (ITreeNode) list.get(0);
                Iterator iterator = emps.iterator();
                while (iterator.hasNext()) {
                    Object[] employee = (Object[]) (Object[]) iterator.next();
                    if (node.getId().equals((String) employee[0]))
                        continue;
                    if (node.getId().equals((String) employee[4])) {
                        ITreeNode childNode = new TreeNode();
                        childNode.setId((String) employee[0]);
                        childNode.setName((String) employee[1]);
                        childNode.setType((String) employee[2]);
                        childNode.setToolTip("NO:" + (String) employee[3]);
                        ((ITreeNode) list.get(0)).addChild(childNode);
                        list.add(childNode);
                    }
                }
                list.remove(0);
            }
        }
        return tree;
    }

    public List findEmpWithActiveStatus() {
        List empList = this.employeeDAO
                .exeHqlList("select id, empName, empType.id, empDistinctNo, empSupNo.id from Employee  where empStatus=1 order by empName asc");

        return empList;
    }

    public List<Employee> findDirectEmps(String id) {
        String hql = "select distinct positionEmpNo.id from Position pos where pos.positionParentId in ( select id from Position pos1 where pos1.positionEmpNo.id ='"
                + id + "')";

        List empIdList = this.employeeDAO.exeHqlList(hql);
        if ((empIdList == null) || (empIdList.size() == 0))
            return null;

        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);
        dc.setFetchMode(Employee.PROP_EMP_PB_NO, FetchMode.JOIN);
        dc.add(Restrictions.in(Employee.PROP_ID, empIdList));
        dc.addOrder(Order.asc(Employee.PROP_EMP_DISTINCT_NO));
        return this.employeeDAO.findByCriteria(dc);
    }

    public int countDirectEmpNumbers(String id, Integer status) {
        int i = 0;
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);
        dc.add(Restrictions.eq("empSupNo.id", id));
        dc.add(Restrictions.ne(Employee.PROP_ID, id));
        if (status != null) {
            dc.add(Restrictions.eq("empStatus", status));
        }
        i = this.employeeDAO.findRowCountByCriteria(dc);
        return i;
    }

    public int countAllEmpNumbers(String id) {
        if ((id == null) || (id.trim().length() < 1)) {
            return 0;
        }

        DetachedCriteria dc = DetachedCriteria.forClass(Position.class);
        dc.add(Restrictions.isNotNull(Position.PROP_POSITION_EMP_NO));
        List posList = this.employeeDAO.findByCriteria(dc);

        List selfPosList = new ArrayList();
        for (int i = 0; (posList != null) && (i < posList.size()); ++i) {
            Position pos = (Position) posList.get(i);
            if ((pos.getPositionEmpNo() != null) && (id.equals(pos.getPositionEmpNo().getId()))) {
                selfPosList.add(pos);
            }
        }
        if (selfPosList.size() == 0)
            return 0;

        List subPosList = new ArrayList();

        for (int i = 0; i < selfPosList.size(); ++i) {
            Position selfPos = (Position) selfPosList.get(i);
            List tempSubPosList = new ArrayList();

            tempSubPosList.add(selfPos);

            for (int j = 0; j < tempSubPosList.size(); ++j) {
                Position pos = (Position) tempSubPosList.get(j);
                Iterator iter = posList.iterator();
                while (iter.hasNext()) {
                    Position posTemp = (Position) iter.next();
                    if ((pos != null) && (posTemp != null)
                            && (posTemp.getPositionParentId() != null)
                            && (pos.getId().equals(posTemp.getPositionParentId().getId()))) {
                        tempSubPosList.add(posTemp);
                    }
                }
            }

            tempSubPosList.remove(0);
            subPosList.addAll(tempSubPosList);
        }

        return subPosList.size();
    }

    public List findNouserEmps() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.eq("empStatus", new Integer(1)));
        detachedCriteria
                .add(Restrictions
                        .sqlRestriction("emp_no not in (select ui_emp_no from userinfo) order by emp_name"));

        return this.employeeDAO.findByCriteria(detachedCriteria);
    }

    public List<String> allSubnos(String no) {
        if ((no == null) && (no.trim().length() < 1)) {
            return new ArrayList();
        }
        return allSubnosHighPerformance(no);
    }

    public boolean updatePassword(String userId, String password2) {
        Userinfo user = (Userinfo) this.employeeDAO.loadObject(Userinfo.class, userId, null,
                                                               new boolean[0]);
        if (user == null) {
            return false;
        }
        user.setUiPasswordEncrypt(StringUtil.encodePassword(password2));
        this.employeeDAO.saveObject(user);
        return true;
    }

    public List loadAll() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.addOrder(Order.asc(Employee.PROP_EMP_NAME));
        return this.employeeDAO.findByCriteria(detachedCriteria);
    }

    public List getPbActiveEmpNos(String pbId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.eq(Employee.PROP_EMP_PB_NO + "." + PositionBase.PROP_ID,
                                             pbId));

        detachedCriteria.add(Restrictions.eq("empStatus", new Integer(1)));
        detachedCriteria.setProjection(Projections.property(Employee.PROP_EMP_NAME));
        return this.employeeDAO.findByCriteria(detachedCriteria);
    }

    public List<String> allSubnosHighPerformance(String empNo) {
        List allEmployee = this.employeeDAO
                .exeHqlList("select employee.id,employee.empSupNo.id from Employee employee");

        List subnosQueue = new LinkedList();
        subnosQueue.add(empNo);

        for (int i = 0; i < subnosQueue.size(); ++i) {
            Iterator iteratorEmployee = allEmployee.iterator();
            String emp = (String) subnosQueue.get(i);
            while (iteratorEmployee.hasNext()) {
                Object[] temp = (Object[]) (Object[]) iteratorEmployee.next();
                if ((temp == null) || (temp[1] == null)
                        || (temp[1].toString().equals(temp[0].toString()))
                        || (!temp[1].toString().equals(emp))) {
                    continue;
                }
                subnosQueue.add(temp[0].toString());
            }
        }

        subnosQueue.remove(0);
        return subnosQueue;
    }

    /** @deprecated */
    public List<SimpleEmployee> getSubManagers() {
        List list = new ArrayList();

        List allEmployee = this.employeeDAO
                .exeHqlList("select id, empSupNo.id, empDistinctNo, empName, empGender from Employee  where empStatus=1 order by empName asc");

        Map empObjMap = new HashMap();
        Set managerSet = new TreeSet();
        SimpleEmployee tempEmp = null;
        for (Iterator i$ = allEmployee.iterator(); i$.hasNext();) {
            Object emp = i$.next();
            tempEmp = new SimpleEmployee(((Object[]) (Object[]) emp)[0].toString(),
                    ((Object[]) (Object[]) emp)[1].toString(), ((Object[]) (Object[]) emp)[2]
                            .toString(), ((Object[]) (Object[]) emp)[3].toString(),
                    (Integer) ((Object[]) (Object[]) emp)[4]);

            empObjMap.put(tempEmp.getId(), tempEmp);

            managerSet.add(((Object[]) (Object[]) emp)[1].toString());
        }

        Iterator iterator = managerSet.iterator();
        String managerId = null;
        while (iterator.hasNext()) {
            managerId = (String) iterator.next();
            list.add(empObjMap.get(managerId));
        }

        return list;
    }

    public List<SimpleEmployee> getSubManagers(String empNo) {
        List<SimpleEmployee> allManagersList = getSubManagers();

        SimpleEmployee emp = null;
        for (SimpleEmployee semp : allManagersList) {
            if (empNo.equals(semp.getId())) {
                emp = semp;
                break;
            }

        }

        List managerQueue = new ArrayList();
        if (emp == null) {
            return managerQueue;
        }

        managerQueue.add(emp);
        SimpleEmployee curremp = null;
        for (int i = 0; i < managerQueue.size(); ++i) {
            curremp = (SimpleEmployee) managerQueue.get(i);
            for (SimpleEmployee currMgr : allManagersList) {
                if ((curremp.getId().equals(currMgr.getEmpSupNo()))
                        && (!currMgr.getId().equals(currMgr.getEmpSupNo()))) {
                    managerQueue.add(currMgr);
                }
            }

        }

        return managerQueue;
    }

    private String[][] getOrgInCharge(String supervisor) {
        IOrgheadsBo headsBo = (IOrgheadsBo) SpringBeanFactory.getBean("headsBo");
        List orgheadList = headsBo.FindOrgheadsByEmpId(supervisor);
        if (orgheadList == null)
            return (String[][]) null;

        String[][] orgInCharge = new String[orgheadList.size()][2];
        for (int i = 0; i < orgheadList.size(); ++i) {
            orgInCharge[i][0] = ((Orgheads) orgheadList.get(i)).getOrgheadsOrgNo();
            String responsible = ((Orgheads) orgheadList.get(i)).getOrgheadsResponsibility();
            if (("braheader".equals(responsible)) || ("bradeputy".equals(responsible)))
                orgInCharge[i][1] = "13";
            else if (("deptheader".equals(responsible)) || ("deptdeputy".equals(responsible)))
                orgInCharge[i][1] = "12";
            else if (("buheader".equals(responsible)) || ("budeputy".equals(responsible)))
                orgInCharge[i][1] = "11";
            else {
                orgInCharge[i][1] = "10";
            }
        }
        return orgInCharge;
    }

    private String[][] getSupInCharge(String supervisor) {
        List submanagerList = convertSEListToIdList(getSubManagers(supervisor));
        if (submanagerList.size() == 0)
            submanagerList.add(supervisor);

        String[][] supInCharge = new String[submanagerList.size()][2];
        for (int i = 0; i < submanagerList.size(); ++i) {
            supInCharge[i][0] = ((String) submanagerList.get(i));
            supInCharge[i][1] = "6";
        }

        return supInCharge;
    }

    private List<String> convertSEListToIdList(List<SimpleEmployee> sempList) {
        List idList = new ArrayList();
        for (SimpleEmployee semp : sempList) {
            idList.add(semp.getId());
        }

        return idList;
    }

    /** @deprecated */
    public String[][] getAllInCharge(String supervisor, String approveType) {
        if ("0".equals(approveType))
            return getSupInCharge(supervisor);
        if ("1".equals(approveType))
            return getOrgInCharge(supervisor);
        if ("2".equals(approveType)) {
            return StringUtil.merge2D(getSupInCharge(supervisor), getOrgInCharge(supervisor));
        }

        return (String[][]) null;
    }

    public boolean checkEmpInCharge(Employee employer, Employee employee) {
        if ((employer == null) || (employee == null))
            return false;

        if (employer.getId().equals(employee.getId()))
            return true;

        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        Position employerPos = posBo.getPosByEmpNo(employer.getId(),
                                                   new String[] { Position.PROP_POSITION_PB_ID });

        List subPosList = posBo.getAllSubPositions(null, new String[] { employerPos.getId() });
        for (int i = 0; (subPosList != null) && (i < subPosList.size()); ++i) {
            Position pos = (Position) subPosList.get(i);
            if ((pos.getPositionEmpNo() != null)
                    && (employee.getId().equals(pos.getPositionEmpNo().getId())))
                return true;

        }

        return false;
    }

    public List<Employee> loadEmpDetails(String[] ids) {
        if ((null == ids) || (ids.length == 0)) {
            return new ArrayList();
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.in("id", ids));
        return this.employeeDAO.findByCriteria(detachedCriteria);
    }

    public List<String> batchTransfer(Emptransfer transfer, String[] ids, String empNo,
            Position newPosition) {
        List errors = new ArrayList();

        Department newDept = null;
        if ((transfer.getEftNewDeptNo() != null)
                && (StringUtils.isNotEmpty(transfer.getEftNewDeptNo().getId()))) {
            newDept = (Department) this.employeeDAO.loadObject(Department.class, transfer
                    .getEftNewDeptNo().getId(), null, new boolean[0]);
        }

        List list = loadEmpDetails(ids);
        for (int i = 0; i < list.size(); ++i) {
            Employee emp = (Employee) list.get(i);
            if ((emp.getEmpPbNo() != null)
                    && (emp.getEmpPbNo().getId().equals(newPosition.getPositionPbId().getId()))
                    && (!transfer.getEftTransferType().equals("4"))) {
                errors.add(emp.getEmpName() + "的变动前职位与变动后职位相同，不能移动！");
                return errors;
            }

        }

        IPositionBo posBo = (IPositionBo) SpringBeanFactory.getBean("positionBo");
        String parentPosId = (newPosition.getPositionParentId() != null) ? newPosition
                .getPositionParentId().getId() : null;
        String info = posBo.batchSetEmpPos(parentPosId, newPosition.getPositionPbId().getId(), ids);
        if (!"SUCC".equals(info)) {
            errors.add(info);
            return errors;
        }

        int size = list.size();
        for (int i = 0; i < size; ++i) {
            Employee emp = (Employee) list.get(i);
            info = saveTransfer(emp, transfer, empNo, newPosition, newDept);
            if (!"SUCC".equals(info)) {
                errors.add(info);
                return errors;
            }
        }

        return errors;
    }

    private String saveTransfer(Employee employee, Emptransfer transfer, String empNo,
            Position newPosition, Department newDept) {
        Emptransfer trans = new Emptransfer();
        trans.setEmployee(employee);
        trans.setEftReason(transfer.getEftReason());
        trans.setEftTransferDate(transfer.getEftTransferDate());
        trans.setEftTransferType(transfer.getEftTransferType());
        trans.setEftComments(transfer.getEftComments());
        trans.setEftCreateBy(empNo);
        trans.setEftLastChangeBy(empNo);
        trans.setEftCreateDate(new Date());
        trans.setEftLastChangeTime(new Date());

        trans.setEftOldDeptNo(employee.getEmpDeptNo());
        trans.setEftNewDeptNo(newDept);
        trans.setEftOldPbId(employee.getEmpPbNo());
        trans.setEftNewPbId(newPosition.getPositionPbId());

        if (transfer.getEftTransferType().equals("4")) {
            if (employee.getEmpType().getId().equals("REG")) {
                return "员工已经是正式员工，不能再次转正＄1�7";
            }
            trans.setEftOldEmpType(employee.getEmpType());
            trans.setEftNewEmpType(new Emptype("REG"));
            employee.setEmpType(new Emptype("REG"));
        }
        this.employeeDAO.saveObject(trans);

        IEmpHistOrgBo emphistorgBo = (IEmpHistOrgBo) SpringBeanFactory.getBean("empHistOrgBo");
        emphistorgBo.saveEmpHistOrg(employee, newDept, newPosition.getPositionPbId());

        employee.setEmpDeptNo(newDept);
        employee.setEmpPbNo(newPosition.getPositionPbId());
        employee.setEmpLastChangeTime(new Date());
        employee.setEmpLastChangeBy(new Employee(empNo));
        this.employeeDAO.saveOrupdate(employee);
        return "SUCC";
    }

    public Map<String, String> getEmployeeMap(Collection<String> empIds) {
        if ((empIds == null) || (empIds.isEmpty())) {
            return new HashMap(0);
        }
        StringBuffer hqlBuffer = new StringBuffer();
        hqlBuffer.append("select id,empName from Employee where");
        Iterator it = empIds.iterator();
        while (it.hasNext()) {
            hqlBuffer.append(" id='");
            hqlBuffer.append((String) it.next());
            hqlBuffer.append("' or");
        }
        String hql = hqlBuffer.toString();
        hql = hql.substring(0, hql.length() - 2);
        List<Object[]> emps = this.employeeDAO.exeHqlList(hql);
        Map resultMap = new HashMap();
        for (Object[] emp : emps) {
            resultMap.put(emp[0].toString(), emp[1].toString());
        }
        return resultMap;
    }

    public List<Employee> getEmpsNeedCard(String[] empNoArray) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.add(Restrictions.eq(Employee.PROP_EMP_STATUS, Integer.valueOf(1)));
        detachedCriteria.add(Restrictions.or(Restrictions.eq(Employee.PROP_EMP_SHIFT_TYPE, Integer
                .valueOf(2)), Restrictions.eq(Employee.PROP_EMP_SHIFT_TYPE, Integer.valueOf(3))));
        if ((empNoArray != null) && (empNoArray.length > 0)) {
            detachedCriteria.add(Restrictions.in(Employee.PROP_ID, empNoArray));
        }

        return this.employeeDAO.findByCriteria(detachedCriteria);
    }

    public List<Employee> findAllActiveEmployees(String year) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);

        Date startDate = DateUtil.getYearFirstDay(Integer.parseInt(year));
        Date endDate = DateUtil.getYearEndDay(Integer.parseInt(year));

        detachedCriteria.add(Restrictions.le(Employee.PROP_EMP_JOIN_DATE, endDate));
        detachedCriteria.add(Restrictions.or(Restrictions.eq(Employee.PROP_EMP_STATUS, Integer
                .valueOf(1)), Restrictions.and(Restrictions.eq(Employee.PROP_EMP_STATUS, Integer
                .valueOf(0)), Restrictions.ge(Employee.PROP_EMP_TERMINATE_DATE, startDate))));

        return this.employeeDAO.findByCriteria(detachedCriteria);
    }

    public synchronized Integer findEmployeeMaxMachineNo() {
        String hql = "select max(empMachineNo) from Employee";
        List maxNoList = this.employeeDAO.exeHqlList(hql);
        Integer maxID;
        if ((maxNoList == null) || (maxNoList.size() < 1) || (maxNoList.get(0) == null))
            maxID = Integer.valueOf(1);
        else {
            maxID = Integer.valueOf(1 + ((Integer) (Integer) maxNoList.get(0)).intValue());
        }
        return maxID;
    }

    public List<EmpSumReport> getEmpSumReport(String startDate, String endDate, String groupBy) {
        List<Date> dateList;
        if (groupBy.equals("0"))
            dateList = DateUtil
                    .getIntervalByMonth(DateUtil.parseDateByFormat(startDate, "yyyy-MM-dd"),
                                        DateUtil.parseDateByFormat(endDate, "yyyy-MM-dd"));
        else {
            dateList = DateUtil
                    .getIntervalByYear(DateUtil.parseDateByFormat(startDate, "yyyy-MM-dd"),
                                       DateUtil.parseDateByFormat(endDate, "yyyy-MM-dd"));
        }
        if ((dateList == null) || (dateList.size() < 1))
            return null;

        List empList = loadAll();

        List reportList = new ArrayList();

        if ((groupBy.equals("0")) && (dateList.size() > 12))
            dateList = dateList.subList(0, 12);

        Map dateSumMap = getEmpSumForReport(dateList, groupBy, empList);
        for (Date date : dateList) {
            String name;
            if (groupBy.equals("0"))
                name = DateUtil.getYear(date) + "/" + DateUtil.getMonth(date);
            else {
                name = DateUtil.getYear(date) + "";
            }
            EmpSumReport reportObj = new EmpSumReport();
            reportObj.setName(name);
            reportObj.setTotal(((Integer) dateSumMap.get(date)).intValue());
            reportList.add(reportObj);
        }

        return reportList;
    }

    public List<EmpSumReport> findDimissionEmpInStartAndEnd(String startDate, String endDate,
            String groupBy) {
        List<Date> dateList;
        if (groupBy.equals("0"))
            dateList = DateUtil
                    .getIntervalByMonth(DateUtil.parseDateByFormat(startDate, "yyyy-MM-dd"),
                                        DateUtil.parseDateByFormat(endDate, "yyyy-MM-dd"));
        else {
            dateList = DateUtil
                    .getIntervalByYear(DateUtil.parseDateByFormat(startDate, "yyyy-MM-dd"),
                                       DateUtil.parseDateByFormat(endDate, "yyyy-MM-dd"));
        }
        if ((dateList == null) || (dateList.size() < 1))
            return null;

        List empList = findAllDimissionEmployees();

        List reportList = new ArrayList();

        if ((groupBy.equals("0")) && (dateList.size() > 12))
            dateList = dateList.subList(0, 12);

        Map dateSumMap = getEmpDimSumForReport(dateList, groupBy, empList);
        for (Date date : dateList) {
            String name;
            if (groupBy.equals("0"))
                name = DateUtil.getYear(date) + "/" + DateUtil.getMonth(date);
            else {
                name = DateUtil.getYear(date) + "";
            }
            EmpSumReport reportObj = new EmpSumReport();
            reportObj.setName(name);
            reportObj.setTotal(((Integer) dateSumMap.get(date)).intValue());
            reportList.add(reportObj);
        }

        return reportList;
    }

    private Map<Date, Integer> getEmpSumForReport(List<Date> dateList, String groupBy,
            List<Employee> empList) {
        Map dateSumMap = new HashMap();
        for (Date date : dateList) {
            Date borderDate;
            if (groupBy.equals("0"))
                borderDate = DateUtil.getYearMonthEndDay(DateUtil.getYear(date), DateUtil
                        .getMonth(date));
            else {
                borderDate = DateUtil.getYearMonthFirstDay(DateUtil.getYear(date), DateUtil
                        .getMonth(date));
            }

            int sum = 0;
            for (Employee emp : empList) {
                if ((emp.getEmpJoinDate().compareTo(borderDate) <= 0)
                        && (((emp.getEmpTerminateDate() == null) || (emp.getEmpTerminateDate()
                                .compareTo(borderDate) > 0)))) {
                    ++sum;
                }
            }
            dateSumMap.put(date, Integer.valueOf(sum));
        }
        return dateSumMap;
    }

    private Map<Date, Integer> getEmpNewForReport(List<Date> dateList, String groupBy,
            List<Employee> empList) {
        Map dateSumMap = new HashMap();
        for (Date date : dateList) {
            Date lowDate;
            Date upperDate;
            if (groupBy.equals("0")) {
                upperDate = DateUtil.getYearMonthFirstDay(DateUtil.getYear(date), DateUtil
                        .getMonth(date));
                lowDate = DateUtil.getYearMonthEndDay(DateUtil.getYear(date), DateUtil
                        .getMonth(date));
            } else {
                upperDate = DateUtil.getYearMonthFirstDay(DateUtil.getYear(date), DateUtil
                        .getMonth(date));
                lowDate = DateUtil.getYearMonthEndDay(DateUtil.getYear(date), 11);
            }

            int sum = 0;
            for (Employee emp : empList) {
                if ((emp.getEmpJoinDate().compareTo(upperDate) >= 0)
                        && (emp.getEmpJoinDate().compareTo(lowDate) <= 0)) {
                    ++sum;
                }
            }
            dateSumMap.put(date, Integer.valueOf(sum));
        }
        return dateSumMap;
    }

    private Map<Date, Integer> getEmpDimSumForReport(List<Date> dateList, String groupBy,
            List<Employee> empList) {
        Map dateSumMap = new HashMap();
        for (Date date : dateList) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Date firstDate;
            Date borderDate;
            if (groupBy.equals("0")) {
                borderDate = DateUtil.getYearMonthEndDay(DateUtil.getYear(date), DateUtil
                        .getMonth(date));
                firstDate = DateUtil.getFirstDayInMonth(cal).getTime();
            } else {
                borderDate = DateUtil.getLastDayInYear(cal).getTime();
                firstDate = DateUtil.getYearMonthFirstDay(DateUtil.getYear(date), DateUtil
                        .getMonth(date));
            }

            int sum = 0;
            for (Employee emp : empList) {
                if ((emp.getEmpTerminateDate() != null)
                        && (emp.getEmpTerminateDate().compareTo(borderDate) <= 0)
                        && (emp.getEmpTerminateDate().compareTo(firstDate) >= 0)) {
                    ++sum;
                }
            }
            dateSumMap.put(date, Integer.valueOf(sum));
        }
        return dateSumMap;
    }

    public List<EmpSumReport> getEmpNewReport(String startDate, String endDate, String groupBy) {
        List<Date> dateList;
        if (groupBy.equals("0"))
            dateList = DateUtil
                    .getIntervalByMonth(DateUtil.parseDateByFormat(startDate, "yyyy-MM-dd"),
                                        DateUtil.parseDateByFormat(endDate, "yyyy-MM-dd"));
        else {
            dateList = DateUtil
                    .getIntervalByYear(DateUtil.parseDateByFormat(startDate, "yyyy-MM-dd"),
                                       DateUtil.parseDateByFormat(endDate, "yyyy-MM-dd"));
        }
        if ((dateList == null) || (dateList.size() < 1))
            return null;

        List empList = loadAll();

        List reportList = new ArrayList();

        if ((groupBy.equals("0")) && (dateList.size() > 12))
            dateList = dateList.subList(0, 12);

        Map dateSumMap = getEmpNewForReport(dateList, groupBy, empList);
        for (Date date : dateList) {
            String name;
            if (groupBy.equals("0"))
                name = DateUtil.getYear(date) + "/" + DateUtil.getMonth(date);
            else {
                name = DateUtil.getYear(date) + "";
            }
            EmpSumReport reportObj = new EmpSumReport();
            reportObj.setName(name);
            reportObj.setTotal(((Integer) dateSumMap.get(date)).intValue());
            reportList.add(reportObj);
        }
        return reportList;
    }

    public List<EmpSumReport> getEmpNewRateReport(String startDate, String endDate, String groupBy) {
        List<Date> dateList;
        if (groupBy.equals("0"))
            dateList = DateUtil
                    .getIntervalByMonth(DateUtil.parseDateByFormat(startDate, "yyyy-MM-dd"),
                                        DateUtil.parseDateByFormat(endDate, "yyyy-MM-dd"));
        else {
            dateList = DateUtil
                    .getIntervalByYear(DateUtil.parseDateByFormat(startDate, "yyyy-MM-dd"),
                                       DateUtil.parseDateByFormat(endDate, "yyyy-MM-dd"));
        }
        if ((dateList == null) || (dateList.size() < 1))
            return null;

        List empList = loadAll();

        List reportList = new ArrayList();

        if ((groupBy.equals("0")) && (dateList.size() > 12))
            dateList = dateList.subList(0, 12);

        Map dateSumMap = getEmpSumForReport(dateList, groupBy, empList);
        Map dateNewMap = getEmpNewForReport(dateList, groupBy, empList);
        for (Date date : dateList) {
            int newSum = ((Integer) dateNewMap.get(date)).intValue();
            int sum = ((Integer) dateSumMap.get(date)).intValue();

            int rate = 0;
            if ((sum == 0) && (newSum != 0))
                rate = 100;
            if (sum != 0)
                rate = newSum * 100 / sum;
            String name;
            if (groupBy.equals("0"))
                name = DateUtil.getYear(date) + "/" + DateUtil.getMonth(date);
            else {
                name = DateUtil.getYear(date) + "";
            }
            EmpSumReport reportObj = new EmpSumReport();
            reportObj.setName(name);
            reportObj.setTotal(rate);
            reportList.add(reportObj);
        }
        return reportList;
    }

    public List<EmpSumReport> getEmpNetRateReport(String startDate, String endDate, String groupBy) {
        List<Date> dateList;
        if (groupBy.equals("0"))
            dateList = DateUtil
                    .getIntervalByMonth(DateUtil.parseDateByFormat(startDate, "yyyy-MM-dd"),
                                        DateUtil.parseDateByFormat(endDate, "yyyy-MM-dd"));
        else {
            dateList = DateUtil
                    .getIntervalByYear(DateUtil.parseDateByFormat(startDate, "yyyy-MM-dd"),
                                       DateUtil.parseDateByFormat(endDate, "yyyy-MM-dd"));
        }
        if ((dateList == null) || (dateList.size() < 1))
            return null;

        List empList = loadAll();

        List reportList = new ArrayList();

        if ((groupBy.equals("0")) && (dateList.size() > 12))
            dateList = dateList.subList(0, 12);

        Map dateSumMap = getEmpSumForReport(dateList, groupBy, empList);
        Map dateNewMap = getEmpNewForReport(dateList, groupBy, empList);
        Map dateDimMap = getEmpDimSumForReport(dateList, groupBy, empList);
        for (Date date : dateList) {
            int newNum = ((Integer) dateNewMap.get(date)).intValue();
            int sum = ((Integer) dateSumMap.get(date)).intValue();
            int dimNum = ((Integer) dateDimMap.get(date)).intValue();

            int rate = 0;
            int net = newNum - dimNum;
            if (sum != 0)
                rate = net * 100 / sum;
            String name;
            if (groupBy.equals("0"))
                name = DateUtil.getYear(date) + "/" + DateUtil.getMonth(date);
            else {
                name = DateUtil.getYear(date) + "";
            }
            EmpSumReport reportObj = new EmpSumReport();
            reportObj.setName(name);
            reportObj.setTotal(rate);
            reportList.add(reportObj);
        }
        return reportList;
    }

    public List<EmpSumReport> getEmpDimissionRateReport(String startDate, String endDate,
            String groupBy) {
        List<Date> dateList;
        if (groupBy.equals("0"))
            dateList = DateUtil
                    .getIntervalByMonth(DateUtil.parseDateByFormat(startDate, "yyyy-MM-dd"),
                                        DateUtil.parseDateByFormat(endDate, "yyyy-MM-dd"));
        else {
            dateList = DateUtil
                    .getIntervalByYear(DateUtil.parseDateByFormat(startDate, "yyyy-MM-dd"),
                                       DateUtil.parseDateByFormat(endDate, "yyyy-MM-dd"));
        }
        if ((dateList == null) || (dateList.size() < 1))
            return null;

        List<Employee> empList = loadAll();

        List reportList = new ArrayList();

        if ((groupBy.equals("0")) && (dateList.size() > 12))
            dateList = dateList.subList(0, 12);

        Map dateSumMap = getEmpDimSumForReport(dateList, groupBy, empList);
        for (Date date : dateList) {
            Date borderDate;
            if (groupBy.equals("0"))
                borderDate = DateUtil.getYearMonthEndDay(DateUtil.getYear(date), DateUtil
                        .getMonth(date));
            else {
                borderDate = DateUtil.getYearMonthFirstDay(DateUtil.getYear(date), DateUtil
                        .getMonth(date));
            }

            int newSum = 0;
            for (Employee emp : empList) {
                if ((emp.getEmpJoinDate().compareTo(borderDate) <= 0)
                        && (((emp.getEmpTerminateDate() == null) || (emp.getEmpTerminateDate()
                                .compareTo(borderDate) > 0)))) {
                    ++newSum;
                }
            }

            int rate = 0;
            int sum = ((Integer) dateSumMap.get(date)).intValue();
            if (sum == 0)
                rate = 0;
            else if ((sum != 0) && (newSum != 0))
                rate = sum * 100 / newSum;
            String name;
            if (groupBy.equals("0"))
                name = DateUtil.getYear(date) + "/" + DateUtil.getMonth(date);
            else {
                name = DateUtil.getYear(date) + "";
            }
            EmpSumReport reportObj = new EmpSumReport();
            reportObj.setName(name);
            reportObj.setTotal(rate);
            reportList.add(reportObj);
        }
        return reportList;
    }

    public List<Employee> findAllDimissionEmployees() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.addOrder(Order.asc(Employee.PROP_EMP_NAME));
        detachedCriteria.add(Restrictions.isNotNull(Employee.PROP_EMP_TERMINATE_DATE));
        return this.employeeDAO.findByCriteria(detachedCriteria);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.bo.EmployeeBoImpl JD-Core Version: 0.5.4
 */