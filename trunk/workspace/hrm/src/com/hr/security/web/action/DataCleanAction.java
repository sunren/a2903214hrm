package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.security.bo.DataCleanBo;
import com.hr.security.domain.base.BaseDataClean;
import com.hr.spring.SpringBeanFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DataCleanAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private List<BaseDataClean> scanList;
    private String selectChoose;
    private String deleteChoose;
    private String begainCreate;
    private String endCreate;
    private String begainChange;
    private String endChange;
    private String modelChoose;

    public DataCleanAction() {
        this.scanList = null;

        this.selectChoose = null;

        this.deleteChoose = null;

        this.begainCreate = null;

        this.endCreate = null;

        this.begainChange = null;

        this.endChange = null;

        this.modelChoose = null;
    }

    public String execute() throws Exception {
        return "success";
    }

    public String scanData() throws Exception {
        if (this.modelChoose == null) {
            addSuccessInfo("请设置查询条件�1�7�1�7");
            return "input";
        }
        Date cbegain = getDate(this.begainCreate);
        Date cend = getDate(this.endCreate);
        Date chbegain = getDate(this.begainChange);
        Date chend = getDate(this.endChange);
        if ((cbegain != null) && (cend != null) && (cbegain.after(cend))) {
            addSuccessInfo("创建弄1�7始日期不能大于结束日期！");
            return "input";
        }
        if ((chbegain != null) && (chend != null) && (chbegain.after(chend))) {
            addSuccessInfo("朄1�7后一次修改开始日期不能大于结束日期！");
            return "input";
        }

        if ("None".equals(this.modelChoose)) {
            addSuccessInfo("请设置查询模块�1�7�1�7");
            return "input";
        }

        DataCleanBo dataCleanBo = (DataCleanBo) SpringBeanFactory.getBean("dataclean");
        this.scanList = dataCleanBo.searchData(this.modelChoose, this.selectChoose, cbegain, cend,
                                               chbegain, chend);
        return "success";
    }

    public String deleteData() throws Exception {
        DataCleanBo dataCleanBo = (DataCleanBo) SpringBeanFactory.getBean("dataclean");
        if ("success".equals(dataCleanBo.deleteData(this.deleteChoose))) {
            addSuccessInfo("删除信息成功〄1�7");
            return "success";
        }
        addActionError("删除信息失败＄1�7");
        return "input";
    }

    public Date getDate(String date) {
        String tempDate = "";
        if ((date == null) || (date.indexOf('-') == -1))
            return null;
        try {
            tempDate = date.trim();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(tempDate);
        } catch (Exception e) {
        }
        return null;
    }

    public List<BaseDataClean> getScanList() {
        return this.scanList;
    }

    public void setScanList(List<BaseDataClean> scanList) {
        this.scanList = scanList;
    }

    public String getSelectChoose() {
        return this.selectChoose;
    }

    public void setSelectChoose(String selectChoose) {
        this.selectChoose = selectChoose;
    }

    public String getDeleteChoose() {
        return this.deleteChoose;
    }

    public void setDeleteChoose(String deleteChoose) {
        this.deleteChoose = deleteChoose;
    }

    public String getBegainChange() {
        return this.begainChange;
    }

    public void setBegainChange(String begainChange) {
        this.begainChange = begainChange;
    }

    public String getBegainCreate() {
        return this.begainCreate;
    }

    public void setBegainCreate(String begainCreate) {
        this.begainCreate = begainCreate;
    }

    public String getEndChange() {
        return this.endChange;
    }

    public void setEndChange(String endChange) {
        this.endChange = endChange;
    }

    public String getEndCreate() {
        return this.endCreate;
    }

    public void setEndCreate(String endCreate) {
        this.endCreate = endCreate;
    }

    public String getModelChoose() {
        return this.modelChoose;
    }

    public void setModelChoose(String modelChoose) {
        this.modelChoose = modelChoose;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.action.DataCleanAction JD-Core Version: 0.5.4
 */