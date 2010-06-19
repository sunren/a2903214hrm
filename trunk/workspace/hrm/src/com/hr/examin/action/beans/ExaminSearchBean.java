package com.hr.examin.action.beans;

import java.util.Date;
import java.util.List;

public class ExaminSearchBean {
    private List<Object> result;
    private String typeNo;
    private Date startDate;
    private Date endDate;
    private String startDateStr;
    private String endDateStr;
    private String depNo;
    private String[] branchNos;
    private String[] depNos;
    private String[] areaNos;
    private String[] buNos;
    private String[] groupNos;
    private String[] orgNos;
    private String emp;
    private String empName;
    private String no;
    private String reason;
    private Integer statu;
    private int needGMAprove;
    private int isNotApprove;
    private String error;
    private int flag;
    private String approver;

    public ExaminSearchBean() {
        this.needGMAprove = 0;

        this.isNotApprove = 0;
    }

    public String[] getDepNos() {
        return this.depNos;
    }

    public void setDepNos(String[] depNos) {
        this.depNos = depNos;
    }

    public String getEmp() {
        return this.emp;
    }

    public void setEmp(String emp) {
        this.emp = ((emp == null) ? null : emp.trim());
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEndDateStr() {
        return this.endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public String getStartDateStr() {
        return this.startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = ((reason == null) ? null : reason.trim());
    }

    public String getNo() {
        return this.no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public List<Object> getResult() {
        return this.result;
    }

    public void setResult(List<Object> result) {
        this.result = result;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getStatu() {
        return this.statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    public String getTypeNo() {
        return this.typeNo;
    }

    public void setTypeNo(String typeNo) {
        this.typeNo = typeNo;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getFlag() {
        return this.flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getDepNo() {
        return this.depNo;
    }

    public void setDepNo(String depNo) {
        this.depNo = depNo;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public int getNeedGMAprove() {
        return this.needGMAprove;
    }

    public void setNeedGMAprove(int needGMAprove) {
        this.needGMAprove = needGMAprove;
    }

    public String[] getAreaNos() {
        return this.areaNos;
    }

    public void setAreaNos(String[] areaNos) {
        this.areaNos = areaNos;
    }

    public String[] getBuNos() {
        return this.buNos;
    }

    public void setBuNos(String[] buNos) {
        this.buNos = buNos;
    }

    public int getIsNotApprove() {
        return this.isNotApprove;
    }

    public void setIsNotApprove(int isNotApprove) {
        this.isNotApprove = isNotApprove;
    }

    public String[] getGroupNos() {
        return this.groupNos;
    }

    public void setGroupNos(String[] groupNos) {
        this.groupNos = groupNos;
    }

    public String getApprover() {
        return this.approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String[] getBranchNos() {
        return this.branchNos;
    }

    public void setBranchNos(String[] branchNos) {
        this.branchNos = branchNos;
    }

    public String[] getOrgNos() {
        return this.orgNos;
    }

    public void setOrgNos(String[] orgNos) {
        this.orgNos = orgNos;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.beans.ExaminSearchBean JD-Core Version: 0.5.4
 */