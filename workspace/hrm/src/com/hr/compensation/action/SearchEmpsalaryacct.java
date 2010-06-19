package com.hr.compensation.action;

import com.hr.base.BaseDownloadAction;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.IEmpSalaryAcctversionBo;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.util.Pager;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchEmpsalaryacct extends BaseDownloadAction {
    private static final long serialVersionUID = 1L;
    private Pager page;
    private Empsalaryacct empsalaryacct;
    private String versionFlag;
    private List<Empsalaryacctversion> empsalaryacctversionList;
    private String searchOrExport;
    private FileInputStream docStream;
    private String contentDisposition;
    private String serverFileName;
    private String outmatchModelId;
    public static final String outputIoName = "OSalaryAcctAll";
    IEmpSalaryAcctversionBo esavBo;

    public SearchEmpsalaryacct() {
        this.page = new Pager();

        this.searchOrExport = null;
        this.docStream = null;
        this.contentDisposition = null;

        this.serverFileName = null;

        this.esavBo = ((IEmpSalaryAcctversionBo) getBean("empsalaryacctversionBo"));
    }

    public String execute() throws Exception {
        this.empsalaryacctversionList = this.esavBo.searchEsav(this.empsalaryacct, this.page,
                                                               this.versionFlag);

        IEmpSalaryAcctitemsBo esaiBo = (IEmpSalaryAcctitemsBo) getBean("empsalaryacctitemsBo");
        if ("export".equals(this.searchOrExport)) {
            if (this.empsalaryacctversionList.size() == 0) {
                addErrorInfo("无帐套项目数据可以导出！");
                return "success";
            }

            List esavIds = new ArrayList();
            for (Empsalaryacctversion version : this.empsalaryacctversionList) {
                esavIds.add(version.getId());
            }
            List allItemsList = esaiBo.getAllItemsByEsavIds(esavIds);
            if (allItemsList.size() == 0) {
                addErrorInfo("无帐套项目数据可以导出！");
                return "success";
            }
            if (excelDownload(allItemsList, "OSalaryAcctAll", this.outmatchModelId)) {
                clearErrorsAndMessages();
                return "download";
            }
            addActionError("数据导出失败!");
            return "success";
        }

        return "success";
    }

    public Empsalaryacct getEmpsalaryacct() {
        return this.empsalaryacct;
    }

    public void setEmpsalaryacct(Empsalaryacct empsalaryacct) {
        this.empsalaryacct = empsalaryacct;
    }

    public List<Empsalaryacctversion> getEmpsalaryacctversionList() {
        return this.empsalaryacctversionList;
    }

    public void setEmpsalaryacctversionList(List<Empsalaryacctversion> empsalaryacctversionList) {
        this.empsalaryacctversionList = empsalaryacctversionList;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public String getVersionFlag() {
        return this.versionFlag;
    }

    public void setVersionFlag(String versionFlag) {
        this.versionFlag = versionFlag;
    }

    public static String getOutputIoName() {
        return "OSalaryAcctAll";
    }

    public String getContentDisposition() {
        return this.contentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public FileInputStream getDocStream() {
        return this.docStream;
    }

    public void setDocStream(FileInputStream docStream) {
        this.docStream = docStream;
    }

    public String getOutmatchModelId() {
        return this.outmatchModelId;
    }

    public void setOutmatchModelId(String outmatchModelId) {
        this.outmatchModelId = outmatchModelId;
    }

    public String getSearchOrExport() {
        return this.searchOrExport;
    }

    public void setSearchOrExport(String searchOrExport) {
        this.searchOrExport = searchOrExport;
    }

    public String getServerFileName() {
        return this.serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.action.SearchEmpsalaryacct JD-Core Version: 0.5.4
 */