package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.base.FileOperate;
import com.hr.profile.bo.IEmpEduHisBo;
import com.hr.profile.domain.Emphistoryedu;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;

public class EmpEduHisDWR extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Emphistoryedu emphistoryedu;
    private IEmpEduHisBo empEduHisBo;
    private String eheId;
    private File file;
    private String fileFileName;
    private String empNo;

    public String eduHisAdd() throws Exception {
        if ("self".equals(this.emphistoryedu.getEmployee().getId())) {
            this.emphistoryedu.setEmployee(getCurrentEmp());
        }

        if (("SUB".equals(this.authorityCondition))
                && (!checkAuth(this.emphistoryedu.getEmployee().getId()))) {
            addActionError("您没有增加权限执行本操作！");
            return "error";
        }
        this.empEduHisBo = ((IEmpEduHisBo) SpringBeanFactory.getBean("empEduHisBo"));
        if ((this.fileFileName != null) && (!"".equals(this.fileFileName))) {
            try {
                String pathConfig = "sys.profile.file.path";
                String typeConfig = "sys.profile.file.type";
                String lengthConfig = "sys.profile.file.length";

                String postfix = this.fileFileName.substring(this.fileFileName.lastIndexOf("."));
                this.fileFileName = (UUID.randomUUID() + postfix);
                String UploadResult = FileOperate.buildFile(this.file, pathConfig,
                                                            this.fileFileName, typeConfig,
                                                            lengthConfig);
                if ("property".equals(UploadResult)) {
                    addActionError("文件的上传错误！");
                    return "error";
                }
                if ("fileExtendNameError".equals(UploadResult)) {
                    addActionError("文件的后缀名不合法！");
                    return "error";
                }
                this.emphistoryedu.setEheAttachment(this.fileFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.emphistoryedu.setEmployee(this.emphistoryedu.getEmployee());
        this.emphistoryedu.setEheCreateBy(getCurrentEmpNo());
        this.emphistoryedu.setEheCreateDate(new Date());
        this.emphistoryedu.setEheLastChangeBy(getCurrentEmpNo());
        this.emphistoryedu.setEheLastChangeTime(new Date());

        this.empEduHisBo.insert(this.emphistoryedu);

        addActionMessage("教育背景增加成功。");
        return "success";
    }

    public String eduHisDel(String ehjId) {
        String auth = DWRUtil.checkAuth("empeduhisdwr", "");

        if ("error".equalsIgnoreCase(auth)) {
            return "noauth";
        }

        this.empEduHisBo = ((IEmpEduHisBo) SpringBeanFactory.getBean("empEduHisBo"));
        Emphistoryedu eduSearch = new Emphistoryedu();
        eduSearch.setEheId(ehjId);
        List list = this.empEduHisBo.search(eduSearch);
        Emphistoryedu empHE = (Emphistoryedu) list.get(0);

        if (("SUB".equals(auth)) && (!checkAuth(empHE.getEmployee().getId())))
            return "noauth";

        this.empEduHisBo.deleteAttach(ehjId, empHE.getEheAttachment());
        this.empEduHisBo.delete(ehjId);

        return "success";
    }

    public String eduHisUpdate() throws Exception {
        this.empEduHisBo = ((IEmpEduHisBo) SpringBeanFactory.getBean("empEduHisBo"));
        List list = this.empEduHisBo.search(this.emphistoryedu);
        if (list.size() == 0) {
            return "error";
        }
        Emphistoryedu empOldHE = (Emphistoryedu) list.get(0);

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(empOldHE.getEmployee().getId()))) {
            addActionError("您没有修改权限执行本操作！");
            return "error";
        }

        if ((this.fileFileName != null) && (!"".equals(this.fileFileName))) {
            try {
                String pathConfig = "sys.profile.file.path";
                String typeConfig = "sys.profile.file.type";
                String lengthConfig = "sys.profile.file.length";

                String postfix = this.fileFileName.substring(this.fileFileName.lastIndexOf("."));
                this.fileFileName = (UUID.randomUUID() + postfix);
                String UploadResult = FileOperate.buildFile(this.file, pathConfig,
                                                            this.fileFileName, typeConfig,
                                                            lengthConfig);
                if ("property".equals(UploadResult)) {
                    addActionError("文件的上传错误！");
                    return "error";
                }
                if ("fileExtendNameError".equals(UploadResult)) {
                    addActionError("文件的后缀名不合法！");
                    return "error";
                }
                empOldHE.setEheAttachment(this.fileFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        empOldHE.setEheDateStart(this.emphistoryedu.getEheDateStart());
        empOldHE.setEheDateEnd(this.emphistoryedu.getEheDateEnd());
        empOldHE.setEheSchool(this.emphistoryedu.getEheSchool());
        empOldHE.setEheMajor(this.emphistoryedu.getEheMajor());
        empOldHE.setEheDegree(this.emphistoryedu.getEheDegree());
        empOldHE.setEheComments(this.emphistoryedu.getEheComments());
        this.emphistoryedu.setEheLastChangeBy(getCurrentEmpNo());
        this.emphistoryedu.setEheLastChangeTime(new Date());

        this.empEduHisBo.update(empOldHE);
        addSuccessInfo("修改员工教育背景成功。");
        return "success";
    }

    public String attachDelete() throws Exception {
        if ((this.eheId == null) || (this.eheId.equals("")) || (this.fileFileName == null)
                || ("".equals(this.fileFileName))) {
            addActionError("参数传递错误！");
            return "error";
        }

        this.empEduHisBo = ((IEmpEduHisBo) SpringBeanFactory.getBean("empEduHisBo"));
        String[] fetches = { Emphistoryedu.PROP_EMPLOYEE };
        Emphistoryedu empOldHE = this.empEduHisBo.load(this.eheId, fetches);

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(empOldHE.getEmployee().getId()))) {
            addActionError("您没有删除权限执行本操作！");
            return "error";
        }

        if (!this.empEduHisBo.deleteAttach(this.eheId, this.fileFileName)) {
            addActionError("附件删除失败！");
            return "error";
        }
        addActionMessage("附件删除成功。");
        return "success";
    }

    public Emphistoryedu loadEduHis(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        id = id.trim();
        this.empEduHisBo = ((IEmpEduHisBo) SpringBeanFactory.getBean("empEduHisBo"));
        Emphistoryedu result = this.empEduHisBo.load(id, new String[0]);
        return result;
    }

    public Emphistoryedu getEmphistoryedu() {
        return this.emphistoryedu;
    }

    public void setEmphistoryedu(Emphistoryedu emphistoryedu) {
        this.emphistoryedu = emphistoryedu;
    }

    public String getEheId() {
        return this.eheId;
    }

    public void setEheId(String eheId) {
        this.eheId = eheId;
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

    public String getEmpNo() {
        return this.empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
}