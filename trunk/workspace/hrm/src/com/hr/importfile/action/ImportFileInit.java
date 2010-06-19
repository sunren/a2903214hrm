package com.hr.importfile.action;

import com.hr.base.BaseAction;
import com.hr.io.bo.IIodefBo;
import com.hr.io.domain.Iodef;
import com.hr.profile.bo.IEmpAddConfBo;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class ImportFileInit extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String iodefId;
    private Iodef iodef;
    private List empaddConfList;
    private String paramString;
    private String attdDateFrom;
    private String attdDateTo;
    private String year;
    private String month;

    public ImportFileInit() {
        this.iodefId = null;

        this.iodef = null;

        this.empaddConfList = null;

        this.paramString = null;
    }

    public String getParamString() {
        return this.paramString;
    }

    public void setParamString(String paramString) {
        this.paramString = paramString;
    }

    public String execute() throws Exception {
        IIodefBo iodefBo = (IIodefBo) SpringBeanFactory.getBean("iodefbo");
        this.iodef = iodefBo.searchIodefByName(this.iodefId);

        IEmpAddConfBo empAddConfBo = (IEmpAddConfBo) BaseAction.getBean("empAddConfBo");
        this.empaddConfList = empAddConfBo.findByTableName(this.iodef.getIoName());

        return "success";
    }

    public String getIodefId() {
        return this.iodefId;
    }

    public void setIodefId(String iodefId) {
        this.iodefId = iodefId;
    }

    public Iodef getIodef() {
        return this.iodef;
    }

    public void setIodef(Iodef iodef) {
        this.iodef = iodef;
    }

    public List getEmpaddConfList() {
        return this.empaddConfList;
    }

    public void setEmpaddConfList(List empaddConfList) {
        this.empaddConfList = empaddConfList;
    }

    public String getAttdDateFrom() {
        return this.attdDateFrom;
    }

    public void setAttdDateFrom(String attdDateFrom) {
        this.attdDateFrom = attdDateFrom;
    }

    public String getAttdDateTo() {
        return this.attdDateTo;
    }

    public void setAttdDateTo(String attdDateTo) {
        this.attdDateTo = attdDateTo;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.importfile.action.ImportFileInit JD-Core Version: 0.5.4
 */