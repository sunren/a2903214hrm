package com.hr.training.action;

import com.hr.base.BaseAction;
import com.hr.training.bo.ITrTypeBO;
import com.hr.training.bo.ITrcourseBO;
import com.hr.training.domain.Trcourse;
import com.hr.training.domain.Trtype;
import com.hr.util.Pager;
import java.util.List;

public class TrtConfig extends BaseAction {
    private Trtype trt;
    private String trtNo;
    private List trtList;

    public String execute() {
        ITrTypeBO trTypeBO = (ITrTypeBO) BaseAction.getBean("trTypeBO");
        this.trtList = trTypeBO.loadAll();
        return "success";
    }

    public String trtAddInit() {
        return "success";
    }

    public String trtAdd() {
        ITrTypeBO trTypeBO = (ITrTypeBO) BaseAction.getBean("trTypeBO");

        if ((this.trt.getTrtNo() != null) && (trTypeBO.load(this.trt.getTrtNo()) != null)) {
            addFieldError("trc.trcNo", "该类型编号与已有培训类型的编号重复！");
            return "error";
        }
        trTypeBO.save(this.trt);
        return "success";
    }

    public String trtUpdateInit() {
        if ((this.trtNo == null) || (this.trtNo.trim().length() <= 0)) {
            return "params_error";
        }
        ITrTypeBO trTypeBO = (ITrTypeBO) BaseAction.getBean("trTypeBO");
        this.trt = trTypeBO.load(this.trtNo);
        return "success";
    }

    public String trtUpdate() {
        ITrTypeBO trTypeBO = (ITrTypeBO) BaseAction.getBean("trTypeBO");
        trTypeBO.update(this.trt);
        return "success";
    }

    public String trtDelete() {
        if ((this.trtNo == null) || (this.trtNo.trim().length() <= 0)) {
            return "params_error";
        }
        ITrTypeBO trTypeBO = (ITrTypeBO) BaseAction.getBean("trTypeBO");
        Trtype trtLoad = trTypeBO.load(this.trtNo);
        if (trtLoad == null) {
            addActionError("该课程培训类型不存在！");
            return "error";
        }
        Trcourse trc = new Trcourse();
        trc.setTrcType(trtLoad);
        ITrcourseBO trcourseBO = (ITrcourseBO) BaseAction.getBean("trcourseBO");
        Pager page = new Pager();
        List trcourses = trcourseBO.search(trc, page);
        if ((trcourses != null) && (trcourses.size() > 0)) {
            addActionError("有课程关联到该课程培训类型，不能删除！");
            return "error";
        }
        trTypeBO.delete(this.trtNo);
        addActionMessage("删除课程培训类型成功。");
        return "success";
    }

    public void validate() {
        if ((this.trt.getTrtNo() == null) || (this.trt.getTrtNo().trim().length() <= 0))
            addFieldError("trt.trtNo", "必填项！");
        if ((this.trt.getTrtNo() != null) && (this.trt.getTrtNo().trim().length() > 0)
                && (!this.trt.getTrtNo().matches("^([a-zA-Z0-9]|[-_]){0,16}$"))) {
            addFieldError("trt.trtNo", "格式错误！（类型编号只允许字母、数字、\"-\"、\"_\"）");
        }
        if ((this.trt.getTrtName() == null) || (this.trt.getTrtName().trim().length() <= 0))
            addFieldError("trt.trtName", "必填项！");
        if ((this.trt.getTrtDesc() != null) && (this.trt.getTrtDesc().length() > 128))
            addFieldError("trt.trtDesc", "类型描述不能超过128个字符！");
    }

    public Trtype getTrt() {
        return this.trt;
    }

    public void setTrt(Trtype trt) {
        this.trt = trt;
    }

    public List getTrtList() {
        return this.trtList;
    }

    public void setTrtList(List trtList) {
        this.trtList = trtList;
    }

    public String getTrtNo() {
        return this.trtNo;
    }

    public void setTrtNo(String trtNo) {
        this.trtNo = trtNo;
    }
}