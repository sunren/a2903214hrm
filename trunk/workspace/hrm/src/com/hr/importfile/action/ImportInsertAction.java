package com.hr.importfile.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import com.hr.importfile.bo.ErrorMessage;
import com.hr.importfile.bo.ITransmitBo;
import com.hr.io.bo.IIodefBo;
import com.hr.io.bo.IIomatchBo;
import com.hr.io.domain.Iodef;
import com.hr.io.domain.Iomatch;
import com.hr.profile.domain.Employee;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.PropertiesFileConfigManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

public class ImportInsertAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String fileExtends;
    private String paramString;
    private String inserted;
    private String iodefId;
    private Iodef iodef;
    private String matchString;
    private List<ErrorMessage> matchList;
    private List<ErrorMessage> logicList;
    private String attdDateFrom;
    private String attdDateTo;
    private String year;
    private String month;
    Map paramMap;

    public ImportInsertAction() {
        this.fileExtends = null;

        this.paramString = null;

        this.inserted = null;

        this.iodefId = null;

        this.iodef = null;

        this.matchString = null;

        this.matchList = null;

        this.logicList = null;

        this.paramMap = new HashMap();
    }

    public String execute() throws Exception {
        IIodefBo iodefBo = (IIodefBo) SpringBeanFactory.getBean("iodefbo");
        this.iodef = iodefBo.searchIodefByName(this.iodefId);

        if (this.iodef == null) {
            addErrorInfo("接口配置对象为空，请与客服联系！");
            return "success";
        }

        if (!hasMultipleAuth(this.iodef.getIoAuthority())) {
            addErrorInfo("对不起,您不具有操作权限!");
            return "success";
        }

        List list = new ArrayList();
        String[] params = this.paramString.split(";");
        for (int i = 0; i < params.length; ++i) {
            list.add(params[i]);
        }

        this.iodef.setTransmitList(list);

        String filePath = FileOperate.getFileHomePath()
                + PropertiesFileConfigManager.getInstance().getProperty("sys.importDir.path");
        if (this.iodef.getIoFilePath().intValue() == 1) {
            HttpSession session = ServletActionContext.getRequest().getSession();
            Employee currentEmployee = ((Userinfo) session.getAttribute("userinfo")).getEmployee();
            filePath = filePath + currentEmployee.getId() + "/";
        }

        String fileNameWithoutExtend = filePath + this.iodef.getIoName();

        File oldFile = new File("fileNameWithoutExtend");

        if ((this.fileExtends == null) || (this.fileExtends.length() == 0)) {
            oldFile = getExistFile(fileNameWithoutExtend);
            if (!oldFile.exists()) {
                addErrorInfo("文件还未上传，请先上传一个导入文件");
                return "success";
            }

        } else {
            oldFile = new File(fileNameWithoutExtend + "." + this.fileExtends);
        }

        ITransmitBo transmitBo = (ITransmitBo) SpringBeanFactory.getBean("transmitbo");
        IIomatchBo iomatchBo = (IIomatchBo) SpringBeanFactory.getBean("iomatchbo");

        Iomatch iomatch = new Iomatch();
        iomatch.setIomatchFieldDesc("请选择");
        iomatch.setIomatchRequired(Integer.valueOf(0));
        iomatch.setIomatchValidType("String");

        List chooseList = new ArrayList();
        chooseList.add(iomatch);
        List iomatches = iomatchBo.getIomatches(this.iodef.getIoId());
        chooseList.addAll(iomatches);

        if ((this.matchString == null) || (this.matchString.length() < 1)) {
            addErrorInfo("页面传输信息为空,请重试！");
            return "success";
        }

        String[] matchArray = this.matchString.split(",");
        int[] userChoose = new int[matchArray.length];
        for (int i = 0; i < matchArray.length; ++i) {
            userChoose[i] = Integer.valueOf(matchArray[i]).intValue();
        }

        String[][] content = transmitBo.getContent(this.iodef.getIoFileHasTitle().intValue(),
                                                   oldFile, 2147483647);

        Employee employee = ((Userinfo) getSession().getAttribute("userinfo")).getEmployee();

        this.paramMap.put("attdDateFrom", this.attdDateFrom);
        this.paramMap.put("attdDateTo", this.attdDateTo);

        this.iodef.getParamMap().put("year", this.year);
        this.iodef.getParamMap().put("month", this.month);
        Hashtable messageTable = transmitBo.insertTransmit(this.iodef, content, oldFile,
                                                           userChoose, chooseList, employee,
                                                           this.paramMap);

        if (messageTable == null) {
            addErrorInfo("导入文件为空或者不可读，请重新上传导入文件!");
            return "success";
        }

        this.matchList = ((List) messageTable.get("matchList"));
        this.logicList = ((List) messageTable.get("logicList"));
        List insertList = null;
        int insertedCount = content.length;
        if (messageTable.get("insertList") != null) {
            this.logicList.addAll((Collection) messageTable.get("insertList"));
        }

        FileOperate.deleteFile(oldFile);

        this.inserted = "YES";

        if ((this.matchList != null) && (this.matchList.size() > 0)) {
            addErrorInfo("数据导入检查未通过!");
            return "input";
        }

        if ((this.logicList != null) && (this.logicList.size() > 0)) {
            addErrorInfo("数据导入检查未通过!");
            return "input";
        }

        addSuccessInfo("共计" + insertedCount + "条数据导入全部成功。");
        return "success";
    }

    public File getExistFile(String filePath) {
        String[] extend = PropertiesFileConfigManager.getInstance()
                .getProperty("sys.importDir.type").split(",");
        File oldFile = new File(filePath);
        for (int i = 0; i < extend.length; ++i) {
            oldFile = new File(filePath + extend[i]);
            if (oldFile.exists()) {
                break;
            }
        }
        return oldFile;
    }

    public List<ErrorMessage> getLogicList() {
        return this.logicList;
    }

    public void setLogicList(List<ErrorMessage> logicList) {
        this.logicList = logicList;
    }

    public List<ErrorMessage> getMatchList() {
        return this.matchList;
    }

    public void setMatchList(List<ErrorMessage> matchList) {
        this.matchList = matchList;
    }

    public String getIodefId() {
        return this.iodefId;
    }

    public void setIodefId(String iodefId) {
        this.iodefId = iodefId;
    }

    public String getMatchString() {
        return this.matchString;
    }

    public void setMatchString(String matchString) {
        this.matchString = matchString;
    }

    public Iodef getIodef() {
        return this.iodef;
    }

    public void setIodef(Iodef iodef) {
        this.iodef = iodef;
    }

    public String getFileExtends() {
        return this.fileExtends;
    }

    public void setFileExtends(String fileExtends) {
        this.fileExtends = fileExtends;
    }

    public String getParamString() {
        return this.paramString;
    }

    public void setParamString(String paramString) {
        this.paramString = paramString;
    }

    public String getInserted() {
        return this.inserted;
    }

    public void setInserted(String inserted) {
        this.inserted = inserted;
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