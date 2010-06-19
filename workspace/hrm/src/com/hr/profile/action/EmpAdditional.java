package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.base.FileOperate;
import com.hr.profile.bo.IEmpAddConfBo;
import com.hr.profile.bo.IEmpAdditionalBo;
import com.hr.profile.bo.IEmpEduHisBo;
import com.hr.profile.bo.IEmpJobHisBo;
import com.hr.profile.bo.IEmpRelationsBo;
import com.hr.profile.bo.IEmpTrainHisBo;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Empaddconf;
import com.hr.profile.domain.Emphistoryedu;
import com.hr.profile.domain.Emphistoryjob;
import com.hr.profile.domain.Emphistorytrain;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Emprelations;
import com.hr.util.MyTools;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.apache.axis.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

public class EmpAdditional extends BaseAction {
    private static final long serialVersionUID = 1L;
    List<Emphistoryjob> ehjList;
    List<Emphistoryedu> eheList;
    List<Emphistorytrain> ehtList;
    List<Emprelations> erelList;
    private String filecFileName;
    private File filec;
    private String filecContentType;
    private String fileeFileName;
    private File filee;
    private String fileeContentType;
    private List<Empaddconf> empaddconfList;
    private String empNo;
    private String empName;
    private String resume1DownLoadName;
    private String resume2DownLoadName;
    private Employee employee;

    public String listAll() throws Exception {
        if ((StringUtils.isEmpty(this.empNo)) || (this.empNo.equals("null")))
            this.empNo = getCurrentEmpNo();

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        this.employee = empBo.loadEmp(this.empNo, null);
        if (this.employee == null)
            return "noemp";
        this.empName = this.employee.getEmpName();

        if (!this.empNo.equals(getCurrentEmpNo())) {
            if ("OWN".equals(this.authorityCondition))
                return "noauth";
            if (((("SUB".equals(this.authorityCondition)) || ("HRSUB"
                    .equals(this.authorityCondition))))
                    && (!checkEmpInCharge(this.employee, 1))) {
                return "noauth";
            }
        }

        IEmpJobHisBo empJobHisBo = (IEmpJobHisBo) BaseAction.getBean("empJobHisBo");
        this.ehjList = empJobHisBo.search(this.empNo);
        IEmpEduHisBo empEduHisBo = (IEmpEduHisBo) BaseAction.getBean("empEduHisBo");
        this.eheList = empEduHisBo.search(this.empNo);
        IEmpTrainHisBo empTrainHisBo = (IEmpTrainHisBo) BaseAction.getBean("empTrainHisBo");
        this.ehtList = empTrainHisBo.search(this.empNo);
        IEmpRelationsBo empRelationsBo = (IEmpRelationsBo) BaseAction.getBean("empRelationsBo");
        this.erelList = empRelationsBo.search(this.empNo);
        IEmpAddConfBo empAddConfBo = (IEmpAddConfBo) BaseAction.getBean("empAddConfBo");
        this.empaddconfList = empAddConfBo.listByTable("empAdditional");

        String resume = this.employee.getEmpResume1();
        if (resume != null) {
            this.resume1DownLoadName = ("中文箄1�7厄1�7" + resume.substring(resume.lastIndexOf('.')));
        }
        resume = this.employee.getEmpResume2();
        if (resume != null) {
            this.resume2DownLoadName = ("英文箄1�7厄1�7" + resume.substring(resume.lastIndexOf('.')));
        }

        int size = this.empaddconfList.size();
        for (int i = 0; i < size; ++i) {
            Empaddconf eac = (Empaddconf) this.empaddconfList.get(i);
            int index = eac.getEadcSeq().intValue();
            eac.setFieldName("empAdditional" + index);
            BeanUtils.setProperty(eac, "value", BeanUtils.getProperty(this.employee,
                                                                      "empAdditional" + index));
            if ("select".equals(eac.getEadcFieldType())) {
                eac.setFieldValueList(Arrays.asList(eac.getEadcFieldValue().split(",")));
            }
        }
        return "success";
    }

    public String resumeUpload() throws Exception {
        if ((!this.empNo.equals(getCurrentEmpNo())) && ("SUB".equals(this.authorityCondition))
                && (!checkAuth(this.empNo))) {
            addActionError("您没有新增权限执行本操作＄1�7");
            return "error";
        }

        if ((this.empNo == null) || (this.empNo.trim().length() == 0)) {
            this.empNo = getCurrentEmpNo();
        }

        String pathConfig = "sys.profile.file.path";
        String typeConfig = "sys.profile.file.type";
        String lengthConfig = "sys.profile.file.length";
        String resume1 = null;
        String resume2 = null;
        try {
            if (this.filecFileName != null) {
                String postfix = this.filecFileName.substring(this.filecFileName.lastIndexOf("."));
                this.filecFileName = (UUID.randomUUID() + postfix);
                String UploadResult = FileOperate.buildFile(this.filec, pathConfig,
                                                            this.filecFileName, typeConfig,
                                                            lengthConfig);
                if ("property".equals(UploadResult)) {
                    addActionError("中文箄1�7历的上传错误＄1�7");
                    return "error";
                }
                if ("fileExtendNameError".equals(UploadResult)) {
                    addActionError("中文箄1�7历的后缀名不合法＄1�7");
                    return "error";
                }
                resume1 = this.filecFileName;
            }
            if (this.fileeFileName != null) {
                String postfix = this.fileeFileName.substring(this.fileeFileName.lastIndexOf("."));
                this.fileeFileName = (UUID.randomUUID() + postfix);
                String UploadResult = FileOperate.buildFile(this.filee, pathConfig,
                                                            this.fileeFileName, typeConfig,
                                                            lengthConfig);
                if ("property".equals(UploadResult)) {
                    addActionError("英文箄1�7历的上传错误＄1�7");
                    return "error";
                }
                if ("fileExtendNameError".equals(UploadResult)) {
                    addActionError("英文箄1�7历的后缀名不合法＄1�7");
                    return "error";
                }
                resume2 = this.fileeFileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        IEmpAdditionalBo empAddtionalBo = (IEmpAdditionalBo) getBean("empAdditionalBo");
        empAddtionalBo.saveResume(resume1, resume2, this.empNo);
        addActionMessage("箄1�7历文件上传成劄1�7");
        return "success";
    }

    public String resumeDelete() throws Exception {
        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(this.empNo))) {
            addActionError("您没有新增权限执行本操作＄1�7");
            return "error";
        }

        if ((this.empNo == null) || (this.empNo.equals(""))) {
            this.empNo = ((String) getSession().getAttribute("curr_oper_no"));
        }
        if ((this.empNo == null) || (this.empNo.trim().length() == 0)) {
            this.empNo = getCurrentEmpNo();
        }
        IEmpAdditionalBo empAdditionalBo = (IEmpAdditionalBo) getBean("empAdditionalBo");
        if (this.filecFileName != null) {
            FileOperate.deleteFile("sys.profile.file.path", this.filecFileName);
            empAdditionalBo.deleteResume(this.empNo, 1);
        }
        if (this.fileeFileName != null) {
            FileOperate.deleteFile("sys.profile.file.path", this.fileeFileName);
            empAdditionalBo.deleteResume(this.empNo, 2);
        }
        addActionMessage("箄1�7历删除成功�1�7�1�7");
        return "success";
    }

    public String updateAdditional(Employee emp, String empNo) throws Exception {
        if ((empNo == null) || (empNo.equals(""))) {
            empNo = (String) getSession().getAttribute("curr_oper_no");
        }
        if ((empNo == null) || (empNo.trim().length() == 0)) {
            empNo = getCurrentEmpNo();
        }
        if (empNo == "self") {
            empNo = getCurrentEmpNo();
        }
        String auth = DWRUtil.checkAuth("empAdditionaldwr", "");

        if (("SUB".equals(auth)) && (!checkAuth(empNo))) {
            addActionError("您没有增加权限执行本操作＄1�7");
            return "error";
        }

        IEmpAdditionalBo empAddtionalBo = (IEmpAdditionalBo) getBean("empAdditionalBo");
        empAddtionalBo.save(emp, empNo);
        return "success";
    }

    public String getWorkOrJoinYear(String dateStr) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Float temp = MyTools.getWorkYearProfile(date, new Date());
        result = getYearOrMonth(temp);

        return result;
    }

    public String getYearOrMonth(Float joinYear) {
        int value = 0;
        String result = "";
        if (joinYear.floatValue() >= 1.0F) {
            value = Integer.parseInt(joinYear.intValue() + "");
            result = result + value + "幄1�7";
        } else {
            joinYear = Float.valueOf(joinYear.floatValue() * 100.0F);
            value = Integer.parseInt(joinYear.intValue() + "");
            result = result + value + "个月";
        }

        return result;
    }

    public List<Emphistoryjob> getEhjList() {
        return this.ehjList;
    }

    public void setEhjList(List<Emphistoryjob> ehjList) {
        this.ehjList = ehjList;
    }

    public List<Emphistoryedu> getEheList() {
        return this.eheList;
    }

    public void setEheList(List<Emphistoryedu> eheList) {
        this.eheList = eheList;
    }

    public List<Emphistorytrain> getEhtList() {
        return this.ehtList;
    }

    public void setEhtList(List<Emphistorytrain> ehtList) {
        this.ehtList = ehtList;
    }

    public List<Empaddconf> getEmpaddconfList() {
        return this.empaddconfList;
    }

    public void setEmpaddconfList(List<Empaddconf> empaddconfList) {
        this.empaddconfList = empaddconfList;
    }

    public String getEmpNo() {
        return this.empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getFilecFileName() {
        return this.filecFileName;
    }

    public void setFilecFileName(String filecFileName) {
        this.filecFileName = filecFileName;
    }

    public File getFilec() {
        return this.filec;
    }

    public void setFilec(File filec) {
        this.filec = filec;
    }

    public String getFileeFileName() {
        return this.fileeFileName;
    }

    public void setFileeFileName(String fileeFileName) {
        this.fileeFileName = fileeFileName;
    }

    public File getFilee() {
        return this.filee;
    }

    public void setFilee(File filee) {
        this.filee = filee;
    }

    public String getResume1DownLoadName() {
        return this.resume1DownLoadName;
    }

    public void setResume1DownLoadName(String resume1DownLoadName) {
        this.resume1DownLoadName = resume1DownLoadName;
    }

    public String getResume2DownLoadName() {
        return this.resume2DownLoadName;
    }

    public void setResume2DownLoadName(String resume2DownLoadName) {
        this.resume2DownLoadName = resume2DownLoadName;
    }

    public List<Emprelations> getErelList() {
        return this.erelList;
    }

    public void setErelList(List<Emprelations> erelList) {
        this.erelList = erelList;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getFilecContentType() {
        return this.filecContentType;
    }

    public void setFilecContentType(String filecContentType) {
        this.filecContentType = filecContentType;
    }

    public String getFileeContentType() {
        return this.fileeContentType;
    }

    public void setFileeContentType(String fileeContentType) {
        this.fileeContentType = fileeContentType;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.EmpAdditional JD-Core Version: 0.5.4
 */