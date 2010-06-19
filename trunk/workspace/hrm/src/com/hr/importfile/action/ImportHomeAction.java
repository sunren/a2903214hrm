package com.hr.importfile.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
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
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

public class ImportHomeAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private String iodefId;
    private List<Iomatch> chooseList;
    private String[][] showContent;
    private Iodef iodef;
    private int[] userChoose;
    private String paramString;
    private String fileExtends;
    private String inserted;
    private int columnCount;
    private String attdDateFrom;
    private String attdDateTo;
    private String year;
    private String month;

    public ImportHomeAction() {
        this.iodefId = null;

        this.chooseList = null;

        this.showContent = new String[3][3];

        this.iodef = null;

        this.userChoose = null;

        this.paramString = null;

        this.fileExtends = null;

        this.inserted = null;

        this.columnCount = 0;
    }

    public String execute() throws Exception {
        IIodefBo iodefBo = (IIodefBo) SpringBeanFactory.getBean("iodefbo");

        if ((this.iodefId == null) || (this.iodefId.length() < 1)) {
            return "params_error";
        }

        this.iodef = iodefBo.searchIodefByName(this.iodefId);

        if (this.iodef == null) {
            addErrorInfo("接口配置对象为空，请与客服联系！");
            return "success";
        }

        if (!hasMultipleAuth(this.iodef.getIoAuthority())) {
            addErrorInfo("对不资1�7,您不具有操作权限!");
            return "success";
        }

        String filePath = FileOperate.getFileHomePath()
                + PropertiesFileConfigManager.getInstance().getProperty("sys.importDir.path");
        if (this.iodef.getIoFilePath().intValue() == 1) {
            HttpSession session = ServletActionContext.getRequest().getSession();
            Employee currentEmployee = ((Userinfo) session.getAttribute("userinfo")).getEmployee();
            filePath = filePath + currentEmployee.getId() + "/";
        }

        IIomatchBo iomatchBo = (IIomatchBo) SpringBeanFactory.getBean("iomatchbo");

        Iomatch iomatch = new Iomatch();
        iomatch.setIomatchFieldDesc("请�1�7�择");
        iomatch.setIomatchRequired(Integer.valueOf(0));
        iomatch.setIomatchValidType("String");

        List tempList = iomatchBo.getIomatches(this.iodef.getIoId());
        if (tempList == null) {
            addErrorInfo("接口配置对象为空，请与客服联系！");
            return "success";
        }
        this.chooseList = new ArrayList();
        this.chooseList.add(iomatch);
        Iterator iterator = tempList.iterator();
        while (iterator.hasNext()) {
            this.chooseList.add((Iomatch) iterator.next());
        }

        ITransmitBo transmitBo = (ITransmitBo) SpringBeanFactory.getBean("transmitbo");

        String fileNameWithoutExtend = filePath + this.iodef.getIoName();

        File readFile = new File("fileNameWithoutExtend");

        if ((this.fileExtends == null) || (this.fileExtends.length() == 0)) {
            readFile = getExistFile(fileNameWithoutExtend);
            if (!readFile.exists()) {
                addErrorInfo("文件还未上传，请先上传一个导入文件！");
                return "success";
            }

        } else {
            readFile = new File(fileNameWithoutExtend + "." + this.fileExtends);
        }

        if ((this.inserted == null) || (!this.inserted.equals("YES"))) {
            if ((this.iodef.getIoMatchType().intValue() == 0)
                    && (this.iodef.getIoFileHasTitle().intValue() == 1)) {
                String[][] tempStringArray = transmitBo.getContent(0, readFile, 4);
                if ((tempStringArray == null) || (tempStringArray.length < 1)) {
                    addErrorInfo("导入文件为空或�1�7�不可读，请重新上传导入文件＄1�7");
                    return "success";
                }

                autoMatch(tempStringArray[0]);

                int rowsLength = tempStringArray.length;
                int columnsLength = tempStringArray[0].length;
                this.columnCount = (rowsLength - 1);
                this.showContent = new String[columnsLength][3];
                for (int i = 1; i < rowsLength; ++i) {
                    for (int j = 0; j < columnsLength; ++j) {
                        this.showContent[j][(i - 1)] = tempStringArray[i][j];
                    }
                }

                return "success";
            }

            String[][] tempStringArray = transmitBo.getContent(this.iodef.getIoFileHasTitle()
                    .intValue(), readFile, this.iodef.getIoFileHasTitle().intValue() + 3);

            if ((tempStringArray == null) || (tempStringArray.length < 1)) {
                addErrorInfo("导入文件为空或�1�7�不可读，请重新上传导入文件＄1�7");
                return "success";
            }

            int rowsLength = tempStringArray.length;
            int columnsLength = tempStringArray[0].length;
            this.columnCount = rowsLength;
            this.showContent = new String[columnsLength][3];
            for (int i = 0; i < rowsLength; ++i) {
                for (int j = 0; j < columnsLength; ++j)
                    this.showContent[j][i] = tempStringArray[i][j];
            }
        }
        return "success";
    }

    public List getChooseList() {
        return this.chooseList;
    }

    public void setChooseList(List<Iomatch> chooseList) {
        this.chooseList = chooseList;
    }

    public String[][] getShowContent() {
        return this.showContent;
    }

    public void setShowContent(String[][] showContent) {
        this.showContent = showContent;
    }

    private void autoMatch(String[] titleArray) {
        String chooseContent = null;
        if (this.userChoose == null)
            this.userChoose = new int[titleArray.length];
        String tempString = "";
        int index = 0;
        for (int i = 0; i < titleArray.length; ++i) {
            tempString = titleArray[i];
            index = tempString.indexOf("-");
            if (index != -1)
                tempString = tempString.substring(0, index);
            for (int j = 0; j < this.chooseList.size(); ++j) {
                chooseContent = ((Iomatch) this.chooseList.get(j)).getIomatchFieldDesc();
                if (chooseContent.equals(tempString)) {
                    this.userChoose[i] = j;
                    break;
                }
            }
        }
    }

    public File getExistFile(String filePath) {
        String[] extend = PropertiesFileConfigManager.getInstance()
                .getProperty("sys.importDir.type").split(",");
        File readFile = new File(filePath);
        for (int i = 0; i < extend.length; ++i) {
            readFile = new File(filePath + extend[i]);
            if (readFile.exists()) {
                break;
            }
        }
        return readFile;
    }

    public int[] getUserChoose() {
        return this.userChoose;
    }

    public void setUserChoose(int[] userChoose) {
        this.userChoose = userChoose;
    }

    public String getIodefId() {
        return this.iodefId;
    }

    public void setIodefId(String iodefId) {
        this.iodefId = iodefId;
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public Iodef getIodef() {
        return this.iodef;
    }

    public void setIodef(Iodef iodef) {
        this.iodef = iodef;
    }

    public String getParamString() {
        return this.paramString;
    }

    public void setParamString(String paramString) {
        this.paramString = paramString;
    }

    public String getFileExtends() {
        return this.fileExtends;
    }

    public void setFileExtends(String fileExtends) {
        this.fileExtends = fileExtends;
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

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.importfile.action.ImportHomeAction JD-Core Version: 0.5.4
 */