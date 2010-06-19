package com.hr.training.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import com.hr.spring.SpringBeanFactory;
import com.hr.training.bo.ITrTypeBO;
import com.hr.training.bo.ITrcourseBO;
import com.hr.training.bo.ITrcourseplanBO;
import com.hr.training.domain.Trcourse;
import com.hr.training.domain.Trcourseplan;
import com.hr.training.domain.Trtype;
import com.hr.util.Pager;
import java.io.File;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TrcourseConfig extends BaseAction {
    private static final long serialVersionUID = 1L;
    private List<Trcourse> trcList;
    private Trcourse trc;
    private String trcNo;
    private List<Trcourseplan> trcpList;
    private String trcName;
    private List<Trtype> trTypeList;
    private String fileFileName;
    private File file;
    private Pager page;

    public TrcourseConfig() {
        this.page = new Pager();
    }

    public String execute() {
        ITrTypeBO trTypeBO = (ITrTypeBO) BaseAction.getBean("trTypeBO");
        this.trTypeList = trTypeBO.loadAll();
        ITrcourseBO trcourseBO = (ITrcourseBO) SpringBeanFactory.getBean("trcourseBO");
        this.trcList = trcourseBO.search(this.trc, this.page);
        return "success";
    }

    public String trcAddInit() {
        ITrTypeBO trTypeBO = (ITrTypeBO) BaseAction.getBean("trTypeBO");
        this.trTypeList = trTypeBO.loadAll();
        return "success";
    }

    public String trcourseAdd() {
        ITrcourseBO trcourseBO = (ITrcourseBO) BaseAction.getBean("trcourseBO");

        ITrTypeBO trTypeBO = (ITrTypeBO) BaseAction.getBean("trTypeBO");
        this.trTypeList = trTypeBO.loadAll();
        this.trc.setTrcCreateBy(getCurrentEmp());
        this.trc.setTrcLastChangeBy(getCurrentEmp());

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
            this.trc.setTrcFileName(localName);
        }
        trcourseBO.addTrc(this.trc);
        addActionMessage("新增课程" + this.trc.getTrcName() + "成功。");
        this.trc = new Trcourse();
        return "success";
    }

    public void validate() {
        if ((this.trc.getTrcName() == null) || (this.trc.getTrcName().trim().equals(""))) {
            addFieldError("trc.trcName", "必填项！");
        }
        if ((this.trc.getTrcType().getTrtNo() == null)
                || (this.trc.getTrcType().getTrtNo().trim().equals(""))) {
            addFieldError("trc.trcType.trtNo", "必选项！");
        }
        if ((this.trc.getTrcStatus() == null)
                || ((this.trc.getTrcStatus().intValue() < 0) && (this.trc.getTrcStatus().intValue() > 1))) {
            addFieldError("trc.trcStatus", "必选项！");
        }
        if ((this.trc.getTrcInfo() == null) || (this.trc.getTrcInfo().trim().equals("")))
            addFieldError("trc.trcInfo", "必填项！");
    }

    public String updateTrcInit() {
        ITrcourseBO trcourseBO = (ITrcourseBO) BaseAction.getBean("trcourseBO");
        if ((this.trcNo == null) && (this.trc != null)) {
            this.trcNo = this.trc.getTrcNo();
        }
        if (this.trcNo == null) {
            return "params_error";
        }
        this.trc = trcourseBO.loadTrc(this.trcNo);
        if (this.trc == null) {
            addActionError("课程" + this.trcNo + "不存在！");
            return "error";
        }
        ITrTypeBO trTypeBO = (ITrTypeBO) BaseAction.getBean("trTypeBO");
        this.trTypeList = trTypeBO.loadAll();
        return "success";
    }

    public String updateTrc() {
        ITrcourseBO trcourseBO = (ITrcourseBO) BaseAction.getBean("trcourseBO");
        ITrTypeBO trTypeBO = (ITrTypeBO) BaseAction.getBean("trTypeBO");
        if (this.trc == null)
            return "params_error";
        this.trc.setTrcType(new Trtype(this.trc.getTrcType().getTrtNo()));
        this.trc.setTrcLastChangeBy(getCurrentEmp());

        if (this.fileFileName != null) {
            Trcourse trcLoad = trcourseBO.loadTrc(this.trc.getTrcNo());
            if (trcLoad == null)
                return "params_error";
            String localName = upload(this.fileFileName);
            if (localName.equals("error"))
                return "error";
            this.trc.setTrcFileName(localName);
        }

        trcourseBO.updateTrc(this.trc);
        this.trTypeList = trTypeBO.loadAll();
        addActionMessage("修改课程" + this.trc.getTrcName() + "成功。");
        this.trc = new Trcourse();
        return "success";
    }

    public String deleteTrc() {
        ITrcourseBO trcourseBO = (ITrcourseBO) BaseAction.getBean("trcourseBO");
        String trcNo = getRequest().getParameter("trcNo");
        if (trcNo == null)
            return "params_error";
        Trcourse trcLoad = trcourseBO.loadTrc(trcNo);
        if (trcLoad == null) {
            addActionError("该课程不存在！");
            return "error";
        }

        ITrcourseplanBO trcourseplanBO = (ITrcourseplanBO) BaseAction.getBean("trcourseplanBO");
        if (trcourseplanBO.getTrcpByTrc(trcNo, null).size() > 0) {
            addActionError("课程" + trcLoad.getTrcName() + "存在课程计划，请先删除课程计划！");
            return "error";
        }

        if ((trcLoad.getTrcFileName() != null) && (trcLoad.getTrcFileName().length() > 0)) {
            String pathConfig = "sys.training.material.path";

            FileOperate.deleteFile(pathConfig, trcLoad.getTrcFileName());
        }
        trcourseBO.deleteTrc(trcNo);
        addActionMessage("课程" + trcLoad.getTrcName() + "已被删除。");
        ITrTypeBO trTypeBO = (ITrTypeBO) BaseAction.getBean("trTypeBO");
        this.trTypeList = trTypeBO.loadAll();
        this.trc = new Trcourse();
        return "success";
    }

    public String openTrc() {
        return setTrcStatus(new Integer(1));
    }

    public String closeTrc() {
        return setTrcStatus(new Integer(0));
    }

    private String setTrcStatus(Integer status) {
        if (this.trcNo == null) {
            addActionError("请选定一个课程记录！");
            return "error";
        }
        ITrcourseBO trcourseBO = (ITrcourseBO) BaseAction.getBean("trcourseBO");
        Trcourse trc = trcourseBO.loadTrc(this.trcNo);
        if (trc == null) {
            addActionError("该课程不存在！");
            return "error";
        }
        trc.setTrcStatus(status);
        trc.setTrcLastChangeBy(getCurrentEmp());

        trcourseBO.updateTrc(trc);
        return "success";
    }

    public String viewTrcTrcpInit() {
        if (this.trcNo == null) {
            this.trcNo = ((String) getSession().getAttribute("trcNoTemp"));
        }

        if (this.trcNo == null) {
            this.trcNo = ((String) getRequest().getAttribute("trcNo"));
        }

        if ((this.trcNo == null) && (this.trc != null)) {
            this.trcNo = this.trc.getTrcNo();
        }
        if (this.trcNo == null) {
            return "params_error";
        }
        ITrcourseBO trcourseBO = (ITrcourseBO) BaseAction.getBean("trcourseBO");
        this.trc = trcourseBO.loadTrc(this.trcNo);
        if (this.trc == null) {
            addActionError("该课程不存在！");
            return "error";
        }
        setTrcName(this.trc.getTrcName());
        ITrcourseplanBO trcourseplanBO = (ITrcourseplanBO) BaseAction.getBean("trcourseplanBO");
        this.trcpList = trcourseplanBO.getTrcpByTrc(this.trcNo, this.page);
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

    public List getTrcList() {
        return this.trcList;
    }

    public void setTrcList(List trcList) {
        this.trcList = trcList;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public Trcourse getTrc() {
        return this.trc;
    }

    public void setTrc(Trcourse trc) {
        this.trc = trc;
    }

    public List getTrTypeList() {
        return this.trTypeList;
    }

    public void setTrTypeList(List trTypeList) {
        this.trTypeList = trTypeList;
    }

    public String getTrcNo() {
        return this.trcNo;
    }

    public void setTrcNo(String trcNo) {
        this.trcNo = trcNo;
    }

    public List getTrcpList() {
        return this.trcpList;
    }

    public void setTrcpList(List trcpList) {
        this.trcpList = trcpList;
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

    public String getTrcName() {
        return this.trcName;
    }

    public void setTrcName(String trcName) {
        this.trcName = trcName;
    }
}