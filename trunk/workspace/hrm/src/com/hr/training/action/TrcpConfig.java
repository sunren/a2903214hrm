package com.hr.training.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import com.hr.configuration.bo.IDepartmentBO;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.training.bo.ITrcourseBO;
import com.hr.training.bo.ITrcourseplanBO;
import com.hr.training.bo.ITremployeeplanBO;
import com.hr.training.domain.Trcourse;
import com.hr.training.domain.Trcourseplan;
import com.hr.util.Pager;
import java.io.File;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TrcpConfig extends BaseAction {
    private static final long serialVersionUID = 1L;
    private List trcpList;
    private Trcourseplan trcp;
    private String trcNo;
    private List deptList;
    private String[] deptListValue;
    private List empList;
    private String trcpId;
    private Pager page;
    private String fileFileName;
    private File file;

    public TrcpConfig() {
        this.page = new Pager();
    }

    public String execute() {
        ITrcourseplanBO trcourseplanBO = (ITrcourseplanBO) getBean("trcourseplanBO");
        if (this.trcp == null) {
            this.trcpList = trcourseplanBO.search(null, null, null, this.page);
        } else {
            this.trcpList = trcourseplanBO.search(this.trcp.getTrcpCourseNo().getTrcName(),
                                                  this.trcp.getTrcpLocation(), this.trcp
                                                          .getTrcpStatus(), this.page);
        }

        return "success";
    }

    public String trcpAddInit() {
        ITrcourseBO trcourseBO = (ITrcourseBO) BaseAction.getBean("trcourseBO");
        if (this.trcp == null) {
            this.trcp = new Trcourseplan();
        }
        if ((this.trcNo != null) && (this.trcNo.trim().length() > 0)) {
            Trcourse trcLoad = trcourseBO.loadTrc(this.trcNo);
            if (trcLoad.getTrcStatus().equals(Integer.valueOf(0))) {
                addActionError("该课程为关闭状态，不能新增课程计划！");

                getRequest().setAttribute("trcNo", this.trcp.getTrcpCourseNo().getTrcNo());
                return "error";
            }
            this.trcp.setTrcpCourseNo(trcLoad);
        }

        IDepartmentBO departmentBO = (IDepartmentBO) BaseAction.getBean("departmentBO");
        this.deptList = departmentBO.FindEnabledDepartment();
        IEmployeeBo empBo = (IEmployeeBo) BaseAction.getBean("empBo");
        this.empList = empBo.loadAll();
        return "success";
    }

    public String addTrcp() {
        if (this.trcp == null) {
            return "params_error";
        }
        ITrcourseplanBO trcourseplanBO = (ITrcourseplanBO) BaseAction.getBean("trcourseplanBO");

        this.trcp.setTrcpCourseNo(new Trcourse(this.trcp.getTrcpCourseNo().getTrcNo()));

        if ((this.trcp.getTrcpCoordinator().getId() != null)
                && (this.trcp.getTrcpCoordinator().getId().length() > 0)) {
            this.trcp.setTrcpCoordinator(new Employee(this.trcp.getTrcpCoordinator().getId()));
        } else {
            this.trcp.setTrcpCoordinator(null);
        }
        this.trcp.setTrcpCreateBy(getCurrentEmp());
        this.trcp.setTrcpLastChangeBy(getCurrentEmp());

        trcourseplanBO.save(this.trcp);
        addActionMessage("新增课程计划成功。");
        setTrcNo(this.trcp.getTrcpCourseNo().getTrcNo());
        return "success";
    }

    public void validate() {
        if ((this.trcp.getTrcpBudgetFee() != null) && (this.trcp.getTrcpBudgetYear() == null)) {
            addFieldError("trcp.trcpBudgetYear", "请填写预算年度！");
        }
        if (this.trcp.getTrcpBudgetHour() == null)
            addFieldError("trcp.trcpBudgetHour", "必填项！");
        if (this.trcp.getTrcpStartDate() == null)
            addFieldError("trcp.trcpStartDate", "必填项！");
        if (this.trcp.getTrcpEndDate() == null) {
            addFieldError("trcp.trcpEndDate", "必填项！");
        }
        if ((this.trcp.getTrcpBudgetYear() != null) && (this.trcp.getTrcpStartDate() != null)
                && (this.trcp.getTrcpEndDate() != null)
                && (this.trcp.getTrcpBudgetYear().trim().length() > 0)) {
            int year = 0;
            try {
                year = Integer.parseInt(this.trcp.getTrcpBudgetYear());
            } catch (NumberFormatException e) {
                System.out.println(e);
                addFieldError("trcp.trcpBudgetYear", "格式不对！");
            }
            int start = Integer.parseInt(this.trcp.getTrcpStartDate().toString()
                    .substring(this.trcp.getTrcpStartDate().toString().lastIndexOf(' ') + 1));

            int end = Integer.parseInt(this.trcp.getTrcpEndDate().toString()
                    .substring(this.trcp.getTrcpEndDate().toString().lastIndexOf(' ') + 1));

            if ((start > year) || (year > end)) {
                addFieldError("trcp.trcpBudgetYear", "预算年度应在培训开始和结束年度之间！");
            }
        }
        if ((this.trcp.getTrcpStartDate() != null) && (this.trcp.getTrcpEndDate() != null)
                && (this.trcp.getTrcpStartDate().after(this.trcp.getTrcpEndDate()))) {
            addFieldError("trcp.trcpEndDate", "培训结束日期不能早于培训开始日期！");
        }
        if ((this.trcp.getTrcpEnrollStartDate() != null)
                && (this.trcp.getTrcpEnrollEndDate() != null)
                && (this.trcp.getTrcpEnrollStartDate().after(this.trcp.getTrcpEnrollEndDate()))) {
            addFieldError("trcp.trcpEnrollEndDate", "报名结束日期不能早于报名开始日期！");
        }
        if ((this.trcp.getTrcpStartDate() != null) && (this.trcp.getTrcpEnrollEndDate() != null)
                && (this.trcp.getTrcpEnrollEndDate().after(this.trcp.getTrcpStartDate()))) {
            addFieldError("trcp.trcpEnrollEndDate", "报名结束日期不能晚于培训开始日！");
        }
        if ((this.trcp.getTrcpComments() != null) && (this.trcp.getTrcpComments().length() > 255))
            addFieldError("trcp.trcpComments", "备注长度不能超过255个字符！");
    }

    public String trcpUpdateInit() {
        if ((this.trcpId == null) && (this.trcp != null)) {
            this.trcpId = this.trcp.getTrcpId();
        }
        if (this.trcpId == null) {
            return "params_error";
        }

        ITrcourseplanBO trcourseplanBO = (ITrcourseplanBO) BaseAction.getBean("trcourseplanBO");
        this.trcp = trcourseplanBO.loadById(this.trcpId);
        if (this.trcp == null) {
            addActionError("该培训计划不存在！");
            return "error";
        }

        IDepartmentBO departmentBO = (IDepartmentBO) BaseAction.getBean("departmentBO");
        this.deptList = departmentBO.FindEnabledDepartment();
        IEmployeeBo empBo = (IEmployeeBo) BaseAction.getBean("empBo");
        this.deptListValue = this.trcp.getTrcpDeptLimit().split(", ");

        this.empList = empBo.loadAll();
        setTrcNo(this.trcp.getTrcpCourseNo().getTrcNo());
        return "success";
    }

    public String updateTrcp() {
        if (this.trcp == null) {
            return "params_error";
        }
        ITrcourseplanBO trcourseplanBO = (ITrcourseplanBO) BaseAction.getBean("trcourseplanBO");
        this.trcp.setTrcpCourseNo(new Trcourse(this.trcp.getTrcpCourseNo().getTrcNo()));

        if ((this.trcp.getTrcpCoordinator().getId() != null)
                && (this.trcp.getTrcpCoordinator().getId().length() > 0)) {
            this.trcp.setTrcpCoordinator(new Employee(this.trcp.getTrcpCoordinator().getId()));
        } else {
            this.trcp.setTrcpCoordinator(null);
        }
        this.trcp.setTrcpLastChangeBy(getCurrentEmp());

        if (this.fileFileName != null) {
            String localName = upload(this.fileFileName);
            try {
                if (localName.equals("error")) {
                    addActionError("上传文件失败，请重试！");
                    return "error";
                }
            } catch (Exception e) {
                e.printStackTrace();
                addActionError("上传文件失败，请重试！");
                return "error";
            }
            this.trcp.setTrcpFileName(localName);
        }

        trcourseplanBO.saveOrupdate(this.trcp);
        addActionMessage("修改课程计划成功。");

        setTrcNo(this.trcp.getTrcpCourseNo().getTrcNo());
        return "success";
    }

    public String deleteTrcp() {
        ITrcourseplanBO trcourseplanBO = (ITrcourseplanBO) BaseAction.getBean("trcourseplanBO");
        if (this.trcpId == null) {
            return "params_error";
        }
        this.trcp = trcourseplanBO.loadById(this.trcpId);
        if (this.trcp == null) {
            addActionError("该培训计划不存在！");
            return "error";
        }
        getRequest().setAttribute("trcNo", this.trcp.getTrcpCourseNo().getTrcNo());
        getSession().setAttribute("trcNoTemp", this.trcp.getTrcpCourseNo().getTrcNo());

        ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) BaseAction
                .getBean("tremployeeplanBO");

        if (tremployeeplanBO.hasAppliedEmp(this.trcpId) > 0) {
            addActionError("该课程计划有员工申请过，请先删除相应的员工培训计划！");
            return "error";
        }
        trcourseplanBO.delete(this.trcpId);
        addActionMessage("删除课程计划成功");
        return "success";
    }

    private String upload(String name) {
        String postfix = name.substring(name.lastIndexOf("."));
        String fileName = UUID.randomUUID() + postfix;

        String pathConfig = "sys.training.material.path";
        String typeConfig = "sys.training.material.type";
        String lengthConfig = "sys.training.material.length";
        try {
            if (!FileOperate.buildFile(this.file, pathConfig, fileName, typeConfig, lengthConfig)
                    .equals("success"))
                return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return fileName;
    }

    public String openTrcp() {
        return setTrcpStatus(new Integer(1));
    }

    public String closeTrcp() {
        return setTrcpStatus(new Integer(0));
    }

    private String setTrcpStatus(Integer status) {
        if (this.trcpId == null) {
            addActionError("请选定一个培训计划！");
            return "error";
        }
        ITrcourseplanBO trcourseplanBO = (ITrcourseplanBO) BaseAction.getBean("trcourseplanBO");
        Trcourseplan trcp = trcourseplanBO.loadById(this.trcpId);
        if (trcp == null) {
            addActionError("该培训计划不存在！");
            return "error";
        }
        trcp.setTrcpStatus(status);
        trcp.setTrcpLastChangeBy(getCurrentEmp());
        trcourseplanBO.saveOrupdate(trcp);
        getRequest().setAttribute("trcNo", trcp.getTrcpCourseNo().getTrcNo());
        return "success";
    }

    public String getTrcNo() {
        return this.trcNo;
    }

    public void setTrcNo(String trcNo) {
        this.trcNo = trcNo;
    }

    public Trcourseplan getTrcp() {
        return this.trcp;
    }

    public void setTrcp(Trcourseplan trcp) {
        this.trcp = trcp;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public List getTrcpList() {
        return this.trcpList;
    }

    public void setTrcpList(List trcpList) {
        this.trcpList = trcpList;
    }

    public List getDeptList() {
        return this.deptList;
    }

    public void setDeptList(List deptList) {
        this.deptList = deptList;
    }

    public String getTrcpId() {
        return this.trcpId;
    }

    public void setTrcpId(String trcpId) {
        this.trcpId = trcpId;
    }

    public List getEmpList() {
        return this.empList;
    }

    public void setEmpList(List empList) {
        this.empList = empList;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return this.fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String[] getDeptListValue() {
        return this.deptListValue;
    }

    public void setDeptListValue(String[] deptListValue) {
        this.deptListValue = deptListValue;
    }
}