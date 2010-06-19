package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import com.hr.configuration.bo.IBenefitTypeBO;
import com.hr.configuration.bo.IClientBO;
import com.hr.configuration.bo.ISysLogBO;
import com.hr.configuration.domain.BenefitType;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Location;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Position;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.PropertiesFileConfigManager;
import com.hr.util.SysConfigManager;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

public class EditEmp extends BaseAction {
    private static final long serialVersionUID = -4234940898871087425L;
    SysConfigManager dbConfigManager;
    private Employee emp;
    private String connectionType;
    private String connectionNo;
    private String empMsn;
    private File file;
    private String fileContentType;
    private String fileFileName;
    private List<Location> locations;
    private List<Emptype> empTypes;
    private String createUser;
    private String empNo;
    private String oldEmpDistinctNo;
    private List<BenefitType> benefitTypes;
    String exShiftEnable;

    public EditEmp() {
        this.dbConfigManager = DatabaseSysConfigManager.getInstance();
    }

    public String createEmp() throws Exception {
        IClientBO clientLimit = (IClientBO) SpringBeanFactory.getBean("clientBo");
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");

        if (!validateId())
            return "input";
        if (!validateMQW())
            return "input";
        if ((this.emp.getPosition() == null)
                || (StringUtils.isEmpty(this.emp.getPosition().getId()))) {
            addErrorInfo("请�1�7�择该员工的职位＄1�7");
            return "input";
        }

        String limitInfo = clientLimit.checkLimit("EMP", 1);
        if (!"success".equals(limitInfo))
            return "overLimitE";

        String fileName = upload(this.emp.getEmpDistinctNo());
        if ((this.file != null) && (fileName.lastIndexOf(".") == -1))
            return "input";
        if ((fileName != null) && (fileName.length() > 0))
            this.emp.setEmpImage(fileName);

        this.emp.setEmpStatus(Integer.valueOf(1));
        if (this.connectionType.equals("0"))
            this.empMsn = this.connectionNo;
        else if (this.connectionType.equals("1"))
            this.empMsn = ("QQ:" + this.connectionNo);
        else if (this.connectionType.equals("2"))
            this.empMsn = ("WW:" + this.connectionNo);
        this.emp.setEmpMsn(this.empMsn);

        String info = empBo.saveCreateEmp(this.emp, getCurrentEmp(), this.createUser);

        if (!"SUCC".equals(info)) {
            addErrorInfo(info);
            return "input";
        }

        getSession().setAttribute("curr_oper_no", this.emp.getId());
        setEmpNo(this.emp.getId());
        this.emp.setEmpName(URLEncoder.encode(this.emp.getEmpName(), "GBK"));
        addSuccessInfo("添加员工基本资料成功〄1�7");
        return "success";
    }

    public String updateEmp() throws Exception {
        if (!validateId())
            return "input";
        if (!validateMQW())
            return "input";
        if ((this.emp.getPosition() == null)
                || (StringUtils.isEmpty(this.emp.getPosition().getId()))) {
            addErrorInfo("请�1�7�择该员工的职位＄1�7");
            return "input";
        }

        this.empTypes = getDrillDown("EmpType", new String[0]);
        this.locations = getDrillDown("Location", new String[0]);

        IBenefitTypeBO benefitTypeBo = (IBenefitTypeBO) SpringBeanFactory.getBean("benefitTypeBo");
        this.benefitTypes = benefitTypeBo.findAll();

        this.exShiftEnable = this.dbConfigManager.getProperty("sys.examin.shift.enable").trim();

        if ((this.authorityCondition != null) && (this.authorityCondition.equalsIgnoreCase("OWN"))
                && (!getCurrentEmpNo().equalsIgnoreCase(this.emp.getId()))) {
            addErrorInfo("您没权限对其他员工资料进行修改操佄1�7!");
            return "general";
        }

        String fileName = upload(this.emp.getEmpDistinctNo());
        if ((this.file != null) && (fileName.lastIndexOf(".") == -1)) {
            return "input";
        }
        String tempImageName = null;
        if ((fileName != null) && (fileName.length() > 0)) {
            this.emp.setEmpImage(fileName);
        } else if (StringUtils.isNotEmpty(this.emp.getEmpImage())) {
            tempImageName = this.emp.getEmpImage();
            this.emp.setEmpImage(this.emp.getEmpDistinctNo()
                    + this.emp.getEmpImage().substring(this.emp.getEmpImage().lastIndexOf("."),
                                                       this.emp.getEmpImage().length()));
        }

        if (this.connectionType.equals("0"))
            this.empMsn = this.connectionNo;
        else if (this.connectionType.equals("1"))
            this.empMsn = ("QQ:" + this.connectionNo);
        else if (this.connectionType.equals("2")) {
            this.empMsn = ("WW:" + this.connectionNo);
        }
        this.emp.setEmpMsn(this.empMsn);

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        List errors = empBo.updateEmpByempInfo(this.emp, getCurrentEmpNo());

        ISysLogBO logBO = (ISysLogBO) SpringBeanFactory.getBean("logBO");
        this.emp.setLogs(logBO.getLogs("employee", this.emp.getId()));
        if ((errors != null) && (errors.size() > 0)) {
            addErrorInfo(errors);
        } else {
            if ((StringUtils.isNotEmpty(this.emp.getEmpImage()))
                    && (!this.emp.getEmpDistinctNo().equalsIgnoreCase(this.oldEmpDistinctNo))) {
                renameFile((tempImageName == null) ? this.emp.getEmpImage() : tempImageName,
                           this.emp.getEmpDistinctNo());
            }
            addSuccessInfo("修改员工基本资料成功〄1�7");
        }

        if (this.authorityCondition.equalsIgnoreCase("ADM"))
            return "success";
        return "general";
    }

    private boolean validateId() {
        if (this.emp.getEmpIdentificationType().intValue() == 0) {
            String pattern15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
            String pattern18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}(\\d{1}|[xX])$";
            if ((!Pattern.matches(pattern15, this.emp.getEmpIdentificationNo()))
                    && (!Pattern.matches(pattern18, this.emp.getEmpIdentificationNo()))) {
                addFieldError("emp.empIdentificationNo", "格式错误＄1�7");
                return false;
            }
        }
        return true;
    }

    private boolean validateMQW() {
        if (this.connectionNo.length() == 0) {
            return true;
        }
        if (this.connectionType.equals("0")) {
            String pattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*.\\w+([-.]\\w+)*";
            if (!Pattern.matches(pattern, this.connectionNo)) {
                addFieldError("connectionNo", "格式错误＄1�7");
                return false;
            }
        } else if ((this.connectionType.equals("1")) && (!isNumeric(this.connectionNo))) {
            addFieldError("connectionNo", "数字项！");
            return false;
        }

        return true;
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);

        return isNum.matches();
    }

    public String upload(String empNo) throws Exception {
        String fileName = "";
        if ((null != this.file) && (null != empNo) && (empNo.trim().length() > 0)) {
            String type = this.fileFileName.substring(this.fileFileName.lastIndexOf("."));

            fileName = empNo + type;
            String message = FileOperate.buildFile(this.file, "sys.user.image.path", fileName,
                                                   "sys.user.image.type", "sys.user.image.length");

            if (!"success".equals(message)) {
                if ("error".equals(message))
                    addFieldError("file", "文件操作错误＄1�7");
                else if ("fileExtendNameError".equals(message))
                    addFieldError("file", "图片类型不匹配！");
                else if ("fileTooLength".equals(message))
                    addFieldError("file", "图片过大＄1�7");
                else if ("property".equals(message)) {
                    addFieldError("file", "属�1�7�文件配置错误！");
                }
                return message;
            }
        }

        return fileName;
    }

    private void renameFile(String oldFileName, String newFileName) {
        String filePath = FileOperate.getFileHomePath()
                + PropertiesFileConfigManager.getInstance().getProperty("sys.user.image.path");

        File oldFile = new File(filePath + oldFileName);
        if (oldFile.exists())
            oldFile.renameTo(new File(filePath + newFileName
                    + oldFileName.substring(oldFileName.lastIndexOf("."), oldFileName.length())));
    }

    public Employee getEmp() {
        return this.emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public List<Emptype> getEmpTypes() {
        return this.empTypes;
    }

    public void setEmpTypes(List<Emptype> empTypes) {
        this.empTypes = empTypes;
    }

    public List<Location> getLocations() {
        return this.locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileContentType() {
        return this.fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFileFileName() {
        return this.fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getEmpNo() {
        return this.empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getOldEmpDistinctNo() {
        return this.oldEmpDistinctNo;
    }

    public void setOldEmpDistinctNo(String oldEmpDistinctNo) {
        this.oldEmpDistinctNo = oldEmpDistinctNo;
    }

    public String getConnectionType() {
        return this.connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getConnectionNo() {
        return this.connectionNo;
    }

    public void setConnectionNo(String connectionNo) {
        this.connectionNo = connectionNo;
    }

    public List<BenefitType> getBenefitTypes() {
        return this.benefitTypes;
    }

    public void setBenefitTypes(List<BenefitType> benefitTypes) {
        this.benefitTypes = benefitTypes;
    }

    public String getExShiftEnable() {
        return this.exShiftEnable;
    }

    public void setExShiftEnable(String exShiftEnable) {
        this.exShiftEnable = exShiftEnable;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.EditEmp JD-Core Version: 0.5.4
 */