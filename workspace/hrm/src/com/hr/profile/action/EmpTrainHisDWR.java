package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.base.FileOperate;
import com.hr.profile.bo.IEmpTrainHisBo;
import com.hr.profile.domain.Emphistorytrain;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;

public class EmpTrainHisDWR extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Emphistorytrain emphistorytrain;
    private String ehtId;
    private File file;
    private String fileFileName;
    private IEmpTrainHisBo empTrainHisBo;

    public String trainHisAdd() throws Exception {
        if ("self".equals(this.emphistorytrain.getEmployee().getId())) {
            this.emphistorytrain.setEmployee(getCurrentEmp());
        }

        if (("SUB".equals(this.authorityCondition))
                && (!checkAuth(this.emphistorytrain.getEmployee().getId()))) {
            addActionError("您没有增加权限执行本操作");
            return "error";
        }

        this.empTrainHisBo = ((IEmpTrainHisBo) SpringBeanFactory.getBean("empTrainHisBo"));
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
                    addActionError("文件的后缀名不合法");
                    return "error";
                }
                this.emphistorytrain.setEhtAttatchment(this.fileFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.emphistorytrain.setEmployee(this.emphistorytrain.getEmployee());
        this.emphistorytrain.setEhtCreateBy(getCurrentEmpNo());
        this.emphistorytrain.setEhtCreateDate(new Date());
        this.emphistorytrain.setEhtLastChangeBy(getCurrentEmpNo());
        this.emphistorytrain.setEhtLastChangeTime(new Date());

        this.empTrainHisBo.insert(this.emphistorytrain);
        addSuccessInfo("新增培训经历成功");
        return "success";
    }

    public String trainHisDel(String ehjId) {
        String auth = DWRUtil.checkAuth("emptrainhisdwr", "");
        if ("error".equalsIgnoreCase(auth)) {
            return "noauth";
        }
        this.empTrainHisBo = ((IEmpTrainHisBo) SpringBeanFactory.getBean("empTrainHisBo"));
        Emphistorytrain trainSearch = new Emphistorytrain();
        trainSearch.setEhtId(ehjId);
        List list = this.empTrainHisBo.search(trainSearch);
        Emphistorytrain EmpHT = (Emphistorytrain) list.get(0);

        if (("SUB".equals(auth)) && (!checkAuth(EmpHT.getEmployee().getId())))
            return "noauth";

        this.empTrainHisBo.deleteAttach(ehjId, EmpHT.getEhtAttatchment());
        this.empTrainHisBo.delete(ehjId);

        return "success";
    }

    public String trainHisUpdate() throws Exception {
        this.empTrainHisBo = ((IEmpTrainHisBo) SpringBeanFactory.getBean("empTrainHisBo"));
        List list = this.empTrainHisBo.search(this.emphistorytrain);
        if (list.size() == 0) {
            return "error";
        }
        Emphistorytrain empOldHT = (Emphistorytrain) list.get(0);

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(empOldHT.getEmployee().getId()))) {
            addActionError("您没有修改权限执行本操作");
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
                    addActionError("文件的后缀名不合法");
                    return "error";
                }
                empOldHT.setEhtAttatchment(this.fileFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        empOldHT.setEhtStartDate(this.emphistorytrain.getEhtStartDate());
        empOldHT.setEhtEndDate(this.emphistorytrain.getEhtEndDate());
        empOldHT.setEhtCourse(this.emphistorytrain.getEhtCourse());
        empOldHT.setEhtLocation(this.emphistorytrain.getEhtLocation());
        empOldHT.setEhtDepartment(this.emphistorytrain.getEhtDepartment());
        empOldHT.setEhtCertificate(this.emphistorytrain.getEhtCertificate());
        empOldHT.setEhtComments(this.emphistorytrain.getEhtComments());
        this.emphistorytrain.setEhtLastChangeBy(getCurrentEmpNo());
        this.emphistorytrain.setEhtLastChangeTime(new Date());

        this.empTrainHisBo.update(empOldHT);
        addSuccessInfo("更新培训经历成功");
        return "success";
    }

    public String attachDelete() throws Exception {
        this.empTrainHisBo = ((IEmpTrainHisBo) SpringBeanFactory.getBean("empTrainHisBo"));
        String[] fetches = { Emphistorytrain.PROP_EMPLOYEE };
        Emphistorytrain empOldHT = this.empTrainHisBo.load(this.ehtId, fetches);

        if (("SUB".equals(this.authorityCondition)) && (!checkAuth(empOldHT.getEmployee().getId()))) {
            addActionError("您没有删除权限执行本操作");
            return "error";
        }

        if ((this.ehtId == null) || (this.ehtId.equals("")) || (this.fileFileName == null)
                || ("".equals(this.fileFileName))) {
            addActionError("参数传递错误！");
            return "error";
        }
        this.empTrainHisBo = ((IEmpTrainHisBo) SpringBeanFactory.getBean("empTrainHisBo"));
        if (!this.empTrainHisBo.deleteAttach(this.ehtId, this.fileFileName)) {
            addActionError("附件删除失败");
            return "error";
        }
        addActionMessage("附件删除成功");
        return "success";
    }

    public Emphistorytrain loadTrainHis(String ehtId) {
        if (StringUtils.isEmpty(ehtId)) {
            return null;
        }
        this.empTrainHisBo = ((IEmpTrainHisBo) SpringBeanFactory.getBean("empTrainHisBo"));
        Emphistorytrain result = this.empTrainHisBo.load(ehtId, new String[0]);
        return result;
    }

    public Emphistorytrain getEmphistorytrain() {
        return this.emphistorytrain;
    }

    public void setEmphistorytrain(Emphistorytrain emphistorytrain) {
        this.emphistorytrain = emphistorytrain;
    }

    public String getEhtId() {
        return this.ehtId;
    }

    public void setEhtId(String ehtId) {
        this.ehtId = ehtId;
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
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.EmpTrainHisDWR JD-Core Version: 0.5.4
 */