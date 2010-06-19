package com.hr.base;

import com.hr.configuration.bo.IDepartmentBO;
import com.hr.io.bo.IInmatchBO;
import com.hr.io.bo.IIodefBo;
import com.hr.io.bo.IOutmatchBO;
import com.hr.io.bo.IOutmatchModelBO;
import com.hr.io.domain.InmatchModel;
import com.hr.io.domain.Iodef;
import com.hr.io.domain.OutmatchModel;
import com.hr.io.extend.ExportInmatchModelToExcel;
import com.hr.io.extend.ExportToExcel;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.output.FieldOperate;
import com.hr.util.output.ObjectListToListList;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

public class BaseDownloadAction extends BaseAction {
    private static final long serialVersionUID = 1982313001L;
    private FileInputStream docStream;
    private String contentDisposition;
    private String serverFileName;

    public BaseDownloadAction() {
        this.docStream = null;

        this.contentDisposition = null;

        this.serverFileName = null;
    }

    private Iodef getIodefByName(String ioName) {
        IIodefBo iodefBO = (IIodefBo) SpringBeanFactory.getBean("iodefbo");
        Iodef iodef = iodefBO.loadObjectByName(ioName);
        return iodef;
    }

    public boolean download(String fileNameWithPath, String showName) {
        try {
            File file = new File(fileNameWithPath);
            FileInputStream fileInputStream = new FileInputStream(file);

            setDocStream(fileInputStream);

            String downFileName = new String(showName.getBytes(), "ISO8859-1");
            setContentDisposition("filename=\"" + downFileName + "\"");

            setServerFileName(fileNameWithPath);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean excelDownload(List dbList, String ioName, String ommId,
            List<FieldOperate> fieldAddList) {
        if ((dbList == null) || (dbList.size() < 1)) {
            System.out.print("==========error : dbList==null");
            return false;
        }
        Iodef iodef = getIodefByName(ioName);
        if (iodef == null) {
            System.out.print("==========error : iodef==null");
            return false;
        }

        IOutmatchBO outmatchBO = (IOutmatchBO) SpringBeanFactory.getBean("outmatchBO");
        IOutmatchModelBO outmatchModelBO = (IOutmatchModelBO) SpringBeanFactory
                .getBean("outmatchModelBO");
        OutmatchModel outmatchModel;
        if ((ommId == null) || (ommId.length() < 1))
            outmatchModel = outmatchModelBO.loadDefaultObject(iodef);
        else {
            outmatchModel = outmatchModelBO.getObject(iodef, ommId);
        }
        if (outmatchModel == null) {
            System.out.print("==========error : outmatchModel==null");
            return false;
        }
        outmatchModel.setOmmIo(iodef);
        List fieldList = outmatchBO.getOutputList(outmatchModel);
        if (fieldAddList != null) {
            boolean addList = true;
            for (int i = 0; i < fieldList.size(); ++i) {
                if (((FieldOperate) fieldList.get(i)).getFieldName().indexOf("outPutList.") == 0) {
                    addList = false;
                    break;
                }
            }
            if (addList) {
                fieldList.addAll(fieldAddList);
            }
        }

        if (fieldList.size() < 1) {
            System.out.print("==========error : fieldList.size()==0");
            return false;
        }

        String filePath = FileOperate.getFileHomePath();
        String xslFileName = "export_" + UUID.randomUUID().toString()
                + PropertiesFileConfigManager.getInstance().getProperty("sys.saveDir.type").trim();

        filePath = filePath + xslFileName;
        try {
            ObjectListToListList LTSA = new ObjectListToListList(dbList, fieldList);
            LTSA.transferGroupOrderStatistic();
            String[][] strArr = LTSA.getListListToString();
            List statList = LTSA.getStatisticListToString();
            List groupPlace = LTSA.getGroupPlaceList();

            ExportToExcel exportToExcel = new ExportToExcel(filePath, strArr, fieldList, statList,
                    groupPlace, outmatchModel);
            exportToExcel.exportOnce();

            return download(filePath, outmatchModel.getOmmName() + ".xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean excelDownload(List dbList, String ioName, String ommId) {
        return excelDownload(dbList, ioName, ommId, null);
    }

    public String downloadToExcel(List dbList, String ioName, String ommId) {
        return downloadToExcel(dbList, ioName, ommId, null, null);
    }

    public String downloadToExcel(List dbList, String ioName, String ommId, String alias) {
        return downloadToExcel(dbList, ioName, ommId, alias, null);
    }

    public String downloadToExcel(List dbList, String ioName, String ommId, String alias,
            List<FieldOperate> fieldAddList) {
        IDepartmentBO deptBo = (IDepartmentBO) SpringBeanFactory.getBean("departmentBO");
        if (alias != null) {
            if ("".equals(alias)) {
                deptBo.setListDeptLevels(dbList, "empDeptNo", "empBranch", "empDeptNo",
                                         new String[0]);
            } else if ("transfer".equals(alias)) {
                deptBo.setListDeptLevels(dbList, "eftOldDeptNo", "eftOldBranch", "eftOldDeptNo",
                                         new String[] { "eftTransferDate" });
                deptBo.setListDeptLevels(dbList, "eftNewDeptNo", "eftNewBranch", "eftNewDeptNo",
                                         new String[0]);
            } else if ("empsalarypay".equals(alias)) {
                deptBo.setListDeptLevels(dbList, "espDept", "espBranch", "espDeptNo",
                                         new String[] { "espYearmonth" });
            } else {
                deptBo.setListDeptLevels(dbList, alias + ".empDeptNo", alias + ".empBranch", alias
                        + ".empDeptNo", new String[0]);
            }
        }

        if ((dbList == null) || (dbList.size() == 0)) {
            addActionError("没有数据可以导出＄1�7");
            return "success";
        }

        Iodef iodef = getIodefByName(ioName);
        if (iodef == null) {
            addActionError("导出接口不存在，请检查�1�7�系组1�7->数据接口配置”！");
            return "success";
        }

        if (excelDownload(dbList, ioName, ommId, fieldAddList)) {
            clearErrorsAndMessages();
            return "download";
        }
        addActionError("数据导出失败＄1�7");
        return "success";
    }

    public boolean excelModelDownload(InmatchModel inmatchModel) {
        IInmatchBO inmatchBO = (IInmatchBO) getBean("inmatchBO");
        List imList = inmatchBO.getInputList(inmatchModel);
        inmatchModel.setImList(imList);

        String filePath = FileOperate.getFileHomePath();
        String xslFileName = "export_" + UUID.randomUUID().toString()
                + PropertiesFileConfigManager.getInstance().getProperty("sys.saveDir.type").trim();

        filePath = filePath + xslFileName;
        try {
            ExportInmatchModelToExcel exportInmatchModelToExcel = new ExportInmatchModelToExcel(
                    filePath, inmatchModel);
            exportInmatchModelToExcel.exportOnce();

            return download(filePath, inmatchModel.getImmName() + ".xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public FileInputStream getDocStream() {
        return this.docStream;
    }

    public void setDocStream(FileInputStream docStream) {
        this.docStream = docStream;
    }

    public String getContentDisposition() {
        return this.contentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public String getServerFileName() {
        return this.serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.base.BaseDownloadAction JD-Core Version: 0.5.4
 */