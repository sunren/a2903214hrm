package com.hr.profile.action;

import com.hr.base.BaseDownloadAction;
import com.hr.base.FileOperate;
import com.hr.configuration.domain.ContractType;
import com.hr.configuration.domain.Department;
import com.hr.profile.bo.IEmpContractBo;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Empcontract;
import com.hr.profile.domain.Employee;
import com.hr.util.BaseCrit;
import com.hr.util.MyTools;
import com.hr.util.Pager;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class ContractManagement extends BaseDownloadAction {
    private static final long serialVersionUID = 1L;
    private String ectNo;
    private Date batchStartDate;
    private Date batchEndDate;
    private Date ectStartDateTo;
    private Date ectEndDateTo;
    private Integer batchExpire;
    private String fileFileName;
    private File file;
    private String batchComments;
    private String batchStatus;
    private String batchempTypeId;
    private Integer toExpire;
    private String employeeId;
    private Employee employee;
    private Empcontract searchContract;
    private String updateEctId;
    private List<Employee> contractList;
    private List<ContractType> empTypeList;
    private Integer continueYear;
    private Integer radioSub;
    private String ectId;
    private String searchOrExport;
    private String ids;
    private String ectIds;
    private Pager page;
    private List<Department> deptList;
    private String outmatchModelId;
    private String outputIoName;

    public ContractManagement() {
        this.searchOrExport = null;

        this.page = new Pager();

        this.outputIoName = "OEmployeeContract";
    }

    public String execute() throws Exception {
        getDrillDownList();

        this.contractList = searchEmpContract();

        DecimalFormat df = new DecimalFormat("#.0");
        for (Employee employee : this.contractList) {
            employee.setJoinYear(MyTools.getWorkYearProfile(employee.getEmpJoinDate(), new Date()));
            if (employee.getEmpConfirmDate() != null) {
                employee.setPracticeMonth(Float.valueOf(df.format((float) (employee
                        .getEmpConfirmDate().getTime() - employee.getEmpJoinDate().getTime())
                        / 30.0F / 24.0F / 3600.0F / 1000.0F)));
            }

        }

        if ("export".equals(this.searchOrExport)) {
            return downloadToExcel(this.contractList, this.outputIoName, this.outmatchModelId, "");
        }

        return "success";
    }

    private List<Employee> searchEmpContract() {
        DetachedCriteria dcEmpCon = searchEmpWithCon_DC();

        addCriteria(dcEmpCon);

        IEmpContractBo empContractBo = (IEmpContractBo) getBean("empContractBo");
        return empContractBo.findContractByEmp(dcEmpCon, this.page, this.searchOrExport);
    }

    private DetachedCriteria searchEmpWithCon_DC() {
        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);

        dc.setFetchMode(Employee.PROP_EMP_DEPT_NO, FetchMode.JOIN);
        dc.createAlias(Employee.PROP_EMP_DEPT_NO, "empDeptNo", 1);
        dc.createAlias(Employee.PROP_EMP_CONTRACT, "con", 1);
        dc.createAlias("con." + Empcontract.PROP_CONTRACT_TYPE, "conType", 1);

        if ("export".equals(this.searchOrExport)) {
            dc.setFetchMode(Employee.PROP_EMP_PB_NO, FetchMode.JOIN);
            dc.setFetchMode(Employee.PROP_EMP_LOCATION_NO, FetchMode.JOIN);
            dc.setFetchMode(Employee.PROP_EMP_TYPE, FetchMode.JOIN);
            dc.setFetchMode(Employee.PROP_EMP_BENEFIT_TYPE, FetchMode.JOIN);
        }

        return dc;
    }

    public void addCriteria(DetachedCriteria dc) {
        if ("SUB".equals(this.authorityCondition)) {
            addSubDC(dc, "", 1);
        }

        addOrders(dc, this.page, new String[] {
                "con." + Empcontract.PROP_ECT_LAST_CHANGE_TIME + "-down",
                Employee.PROP_EMP_DISTINCT_NO + "-up" });

        if (this.employee != null) {
            BaseCrit.addEmpDC(dc, "", this.employee.getEmpName());
            BaseCrit.addDeptDC(dc, Employee.PROP_EMP_DEPT_NO, Employee.PROP_EMP_PB_NO, Integer
                    .valueOf(1), this.employee.getEmpDeptNo());
        }

        BaseCrit.addDC(dc, Employee.PROP_EMP_STATUS, "eq", new Integer[] { Integer.valueOf(1) });

        if (this.searchContract == null)
            return;

        if ("4".equals(this.searchContract.getEctStatus())) {
            dc.add(Restrictions.isNull(Employee.PROP_EMP_CONTRACT));
            return;
        }

        if ((this.toExpire != null) && (-1 == this.toExpire.intValue())) {
            BaseCrit.addDCDateExpire(dc, "con." + Empcontract.PROP_ECT_END_DATE, "le",
                                     this.toExpire, null);
        } else if (this.toExpire != null) {
            BaseCrit.addDCDateExpire(dc, "con." + Empcontract.PROP_ECT_END_DATE, null,
                                     this.toExpire, null);
        }

        BaseCrit.addDC(dc, "con." + Empcontract.PROP_ECT_NO, "like",
                       new String[] { this.searchContract.getEctNo() });
        BaseCrit.addDC(dc, "con." + Empcontract.PROP_ECT_COMMENTS, "like",
                       new String[] { this.searchContract.getEctComments() });
        BaseCrit.addDC(dc, "con." + Empcontract.PROP_ETC_EXPIRE, "eq",
                       new Integer[] { this.searchContract.getEtcExpire() });
        BaseCrit.addDCDateRange(dc, "con." + Empcontract.PROP_ECT_START_DATE, this.searchContract
                .getEctStartDate(), this.ectStartDateTo);
        BaseCrit.addDCDateRange(dc, "con." + Empcontract.PROP_ECT_END_DATE, this.searchContract
                .getEctEndDate(), this.ectEndDateTo);
        BaseCrit.addDC(dc, "con." + Empcontract.PROP_CONTRACT_TYPE, "id",
                       new Object[] { this.searchContract.getContractType() });

        if ("5".equals(this.searchContract.getEctStatus())) {
            dc.add(Restrictions.eq("con." + Empcontract.PROP_ECT_STATUS, "1"));
            dc.add(Restrictions.lt("con." + Empcontract.PROP_ECT_END_DATE, new Date()));
            return;
        }

        BaseCrit.addDC(dc, "con." + Empcontract.PROP_ECT_STATUS, "eq",
                       new String[] { this.searchContract.getEctStatus() });
    }

    private void getDrillDownList() {
        this.empTypeList = getDrillDown("ContractType", new String[0]);
        this.deptList = getDrillDown("Department", new String[0]);
    }

    public String batchCreate() {
        if (StringUtils.isEmpty(this.ids)) {
            addErrorInfo("错误的请求参数，请刷新页面后重试");
            return "success";
        }

        Empcontract contract = new Empcontract();
        contract.setEctStartDate(this.batchStartDate);
        contract.setEctEndDate(this.batchEndDate);
        contract.setContractType(new ContractType(this.batchempTypeId));
        contract.setEtcExpire(this.batchExpire);
        contract.setEctStatus(this.batchStatus);
        contract.setEctComments(this.batchComments);
        String[] idArr = this.ids.split(",");

        String errorStr = "";
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        List<Employee> empList = empBo.searchEmpContract(idArr);
        IEmpContractBo empContractBo = (IEmpContractBo) getBean("empContractBo");
        for (Employee emp : empList) {
            Empcontract c = emp.getContract();
            if ((c != null) && (StringUtils.isNotEmpty(c.getEctId()))) {
                errorStr = errorStr + emp.getEmpName() + ",";
            }

            contract.setEmployee(emp);
            contract.setEctCreateBy(getCurrentEmpNo());
            contract.setEctLastChangeBy(getCurrentEmpNo());
            contract.setEctCreateDate(new Date());
            contract.setEctLastChangeTime(new Date());

            empContractBo.insert(contract);
            emp.setContract(contract);
            empBo.updateEmp(emp, emp.getId());
        }
        if (errorStr.length() > 0) {
            errorStr = errorStr.substring(0, errorStr.length() - 1) + "已经存在合同，创建合同不成功";
            addErrorInfo(errorStr);
        } else {
            addSuccessInfo("批量创建合同成功");
        }
        return "success";
    }

    public String batchContinuous() {                                                                                                                             
        if (StringUtils.isEmpty(ids) || StringUtils.isEmpty(ectIds)) {                                                                                            
            return "success";                                                                                                                                     
        }                                                                                                                                                         
        String errMsg = "";                                                                                                                                       
        String error = "";                                                                                                                                        
        String dateError = "";                                                                                                                                    
        IEmployeeBo empBo = (IEmployeeBo)getBean("empBo");                                                                                                        
        String idArr[] = ids.split(",");                                                                                                                          
        List<Employee> empList = empBo.searchEmpContract(idArr);                                                                                                            
        String ectIdArr[] = ectIds.split(",");                                                                                                                    
        IEmpContractBo empContractBo = (IEmpContractBo)getBean("empContractBo");                                                                                  
        java.util.Iterator<Employee> i$ = empList.iterator();                                                                                                                         
        do {                                                                                                                                                      
            if (!i$.hasNext()) {                                                                                                                                  
                break;                                                                                                                                            
            }                                                                                                                                                     
            Employee emp = (Employee)i$.next();                                                                                                                   
            Empcontract old = emp.getContract();                                                                                                                  
            int flag = 0;                                                                                                                                         
            int i = 0;                                                                                                                                            
            do {                                                                                                                                                  
                if (i >= ectIdArr.length) {                                                                                                                       
                    break;                                                                                                                                        
                }                                                                                                                                                 
                if (old.getEctId().trim().equals(ectIdArr[i].trim())) {                                                                                           
                    flag = 1;                                                                                                                                     
                    break;                                                                                                                                        
                }                                                                                                                                                 
                i++;                                                                                                                                              
            } while (true);                                                                                                                                       
            if (flag == 0) {                                                                                                                                      
                continue;                                                                                                                                         
            }                                                                                                                                                     
            if (old == null || StringUtils.isEmpty(old.getEctId())) {                                                                                             
                errMsg = (new StringBuilder()).append(errMsg).append(emp.getEmpName()).append("\uFF0C").toString();                                               
                continue;                                                                                                                                         
            }                                                                                                                                                     
            if (old.getEtcExpire().intValue() == 1) {                                                                                                             
                error = (new StringBuilder()).append(error).append(emp.getEmpName()).append('\uFF0C').toString();                                                 
                continue;                                                                                                                                         
            }                                                                                                                                                     
            Empcontract contract = new Empcontract();                                                                                                             
            contract.setContractType(new ContractType(batchempTypeId));                                                                                           
            contract.setEtcExpire(batchExpire);                                                                                                                   
            contract.setEctStatus(batchStatus);                                                                                                                   
            contract.setEctComments(batchComments);                                                                                                               
            if (batchExpire.intValue() == 1) {                                                                                                                    
                if (batchStartDate.after(old.getEctStartDate())) {                                                                                                
                    contract.setEctStartDate(batchStartDate);                                                                                                     
                } else {                                                                                                                                          
                    dateError = (new StringBuilder()).append(dateError).append(emp.getEmpName()).append('\uFF0C').toString();                                     
                    continue;                                                                                                                                     
                }                                                                                                                                                 
                contract.setEctEndDate(null);                                                                                                                     
            } else                                                                                                                                                
            if (batchExpire.intValue() == 0 && radioSub.intValue() == 0) {                                                                                        
                if (batchStartDate.after(old.getEctStartDate())) {                                                                                                
                    contract.setEctStartDate(batchStartDate);                                                                                                     
                } else {                                                                                                                                          
                    dateError = (new StringBuilder()).append(dateError).append(emp.getEmpName()).append('\uFF0C').toString();                                     
                    continue;                                                                                                                                     
                }                                                                                                                                                 
                if (batchEndDate.after(old.getEctEndDate())) {                                                                                                    
                    contract.setEctEndDate(batchEndDate);                                                                                                         
                } else {                                                                                                                                          
                    dateError = (new StringBuilder()).append(dateError).append(emp.getEmpName()).append('\uFF0C').toString();                                     
                    continue;                                                                                                                                     
                }                                                                                                                                                 
            } else                                                                                                                                                
            if (batchExpire.intValue() == 0 && radioSub.intValue() == 1) {                                                                                        
                Calendar c = Calendar.getInstance();                                                                                                              
                c.setTime(old.getEctEndDate());                                                                                                                   
                c.add(5, 1);                                                                                                                                      
                contract.setEctStartDate(c.getTime());                                                                                                            
                c.setTime(old.getEctEndDate());                                                                                                                   
                c.add(1, continueYear.intValue());                                                                                                                
                contract.setEctEndDate(c.getTime());                                                                                                              
            }                                                                                                                                                     
            old.setEctStatus("2");                                                                                                                                
            old.setEctLastChangeTime(new Date());                                                                                                                 
            old.setEctLastChangeBy(getCurrentEmpNo());                                                                                                            
            if (contract.getEctAttatchment() != null) {                                                                                                           
                FileOperate.deleteFile("sys.profile.file.path", old.getEctAttatchment());                                                                         
                old.setEctAttatchment(contract.getEctAttatchment());                                                                                              
            } else {                                                                                                                                              
                contract.setEctAttatchment(old.getEctAttatchment());                                                                                              
            }                                                                                                                                                     
            empContractBo.updateObj(old);                                                                                                                         
            contract.setEmployee(emp);                                                                                                                            
            contract.setEctCreateBy(getCurrentEmpNo());                                                                                                           
            contract.setEctLastChangeBy(getCurrentEmpNo());                                                                                                       
            contract.setEctCreateDate(new Date());                                                                                                                
            contract.setEctLastChangeTime(new Date());                                                                                                            
            empContractBo.insert(contract);                                                                                                                       
            emp.setContract(contract);                                                                                                                            
            empBo.updateEmp(emp, getCurrentEmpNo());                                                                                                              
        } while (true);                                                                                                                                           
        if (errMsg.length() > 0) {                                                                                                                                
            errMsg = errMsg.substring(0, errMsg.length() - 1);                                                                                                    
            errMsg = (new StringBuilder()).append(errMsg).append("\u4E0D\u5B58\u5728\u5408\u540C\uFF0C").toString();                                              
        }                                                                                                                                                         
        if (error.length() > 0) {                                                                                                                                 
            error = error.substring(0, error.length() - 1);                                                                                                       
            error = (new StringBuilder()).append(error).append("\u5B58\u5728\u65E0\u9650\u671F\u5408\u540C\uFF0C").toString();                                    
        }                                                                                                                                                         
        if (dateError.length() > 0) {                                                                                                                             
            dateError = dateError.substring(0, dateError.length() - 1);                                                                                           
            dateError = (new StringBuilder()).append(dateError).append("\u5B58\u5728\u65B0\u8001\u5408\u540C\u65F6\u95F4\u51B2\u7A81\uFF0C").toString();          
        }                                                                                                                                                         
        String rs = (new StringBuilder()).append(errMsg).append(error).append(dateError).toString();                                                              
        if (rs.trim().length() > 0) {                                                                                                                             
            rs = (new StringBuilder()).append(rs).append("\u7EED\u7B7E\u5408\u540C\u4E0D\u6210\u529F\u3002").toString();                                          
            addErrorInfo(rs);                                                                                                                                     
            return "success";                                                                                                                                     
        }                                                                                                                                                         
        if (rs.trim().length() == 0) {                                                                                                                            
            addSuccessInfo("\u6279\u91CF\u7EED\u7B7E\u6210\u529F\u3002");                                                                                         
            return "success";                                                                                                                                     
        } else {                                                                                                                                                  
            return "success";                                                                                                                                     
        }                                                                                                                                                         
    }                                                                                                                                                          
//
//    public String batchContinuous()
//    {
//    if ((StringUtils.isEmpty(this.ids)) || (StringUtils.isEmpty(this.ectIds))) {
//      return "success";
//    }
//
//    String errMsg = "";
//    String error = "";
//    String dateError = "";
//
//    IEmployeeBo empBo = (IEmployeeBo)getBean("empBo");
//    String[] idArr = this.ids.split(",");
//    List<Employee> empList = empBo.searchEmpContract(idArr);
//
//    String[] ectIdArr = this.ectIds.split(",");
//    IEmpContractBo empContractBo = (IEmpContractBo)getBean("empContractBo");
//    for (Employee emp : empList) {
//      Empcontract old = emp.getContract();
//
//      int flag = 0;
//      for (int i = 0; i < ectIdArr.length; ++i) {
//        if (old.getEctId().trim().equals(ectIdArr[i].trim())) {
//          flag = 1;
//          break;
//        }
//      }
//      if (flag == 0) {
//        continue;
//      }
//
//      if ((old == null) || (StringUtils.isEmpty(old.getEctId()))) {
//        errMsg = errMsg + emp.getEmpName() + "、";
//      }
//
//      if (old.getEtcExpire().intValue() == 1) {
//        error = error + emp.getEmpName() + 65292;
//      }
//
//      Empcontract contract = new Empcontract();
//      contract.setContractType(new ContractType(this.batchempTypeId));
//      contract.setEtcExpire(this.batchExpire);
//      contract.setEctStatus(this.batchStatus);
//      contract.setEctComments(this.batchComments);
//
//      if (this.batchExpire.intValue() == 1)
//      {
//        if (this.batchStartDate.after(old.getEctStartDate())) {
//          contract.setEctStartDate(this.batchStartDate);
//        } else {
//          dateError = dateError + emp.getEmpName() + 65292;
//          continue;
//        }
//        contract.setEctEndDate(null); } else {
//        if ((this.batchExpire.intValue() == 0) && (this.radioSub.intValue() == 0))
//        {
//          if (this.batchStartDate.after(old.getEctStartDate())) {
//            contract.setEctStartDate(this.batchStartDate);
//          } else {
//            dateError = dateError + emp.getEmpName() + 65292;
//            continue;
//          }
//
//          if (this.batchEndDate.after(old.getEctEndDate())) {
//            contract.setEctEndDate(this.batchEndDate); break label621:
//          }
//          dateError = dateError + emp.getEmpName() + 65292;
//        }
//
//        if ((this.batchExpire.intValue() == 0) && (this.radioSub.intValue() == 1)) {
//          Calendar c = Calendar.getInstance();
//          c.setTime(old.getEctEndDate());
//          c.add(5, 1);
//          contract.setEctStartDate(c.getTime());
//          c.setTime(old.getEctEndDate());
//          c.add(1, this.continueYear.intValue());
//          contract.setEctEndDate(c.getTime());
//        }
//      }
//      old.setEctStatus("2");
//      old.setEctLastChangeTime(new Date());
//      old.setEctLastChangeBy(getCurrentEmpNo());
//      if (contract.getEctAttatchment() != null) {
//        FileOperate.deleteFile("sys.profile.file.path", old.getEctAttatchment());
//        old.setEctAttatchment(contract.getEctAttatchment());
//      } else {
//        contract.setEctAttatchment(old.getEctAttatchment());
//      }
//      empContractBo.updateObj(old);
//
//      contract.setEmployee(emp);
//      contract.setEctCreateBy(getCurrentEmpNo());
//      contract.setEctLastChangeBy(getCurrentEmpNo());
//      contract.setEctCreateDate(new Date());
//      contract.setEctLastChangeTime(new Date());
//
//      empContractBo.insert(contract);
//      emp.setContract(contract);
//      empBo.updateEmp(emp, getCurrentEmpNo());
//    }
//    if (errMsg.length() > 0) {
//      label621: errMsg = errMsg.substring(0, errMsg.length() - 1);
//      errMsg = errMsg + "不存在合同，";
//    }
//    if (error.length() > 0) {
//      error = error.substring(0, error.length() - 1);
//      error = error + "存在无限期合同，";
//    }
//    if (dateError.length() > 0) {
//      dateError = dateError.substring(0, dateError.length() - 1);
//      dateError = dateError + "存在新合同时间冲突，";
//    }
//    String rs = errMsg + error + dateError;
//    if (rs.trim().length() > 0) {
//      rs = rs + "续签合同不成功";
//      addErrorInfo(rs);
//      return "success";
//    }if (rs.trim().length() == 0) {
//      addSuccessInfo("批量续签成功");
//      return "success";
//    }
//    return "success";
//  }

    public String batchRepeal() {
        if ((StringUtils.isEmpty(this.ids)) || (StringUtils.isEmpty(this.ectIds))) {
            addErrorInfo("错误的请求参数，请刷新页面后重试");
            return "success";
        }

        String error = "";

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        String[] arr = this.ids.split(",");
        List<Employee> empList = empBo.searchEmpContract(arr);
        String[] ectIdArr = this.ectIds.split(",");
        IEmpContractBo empContractBo = (IEmpContractBo) getBean("empContractBo");

        for (Employee emp : empList) {
            Empcontract old = emp.getContract();
            if (old == null)
                continue;
            if (old.getEctId() == null) {
                continue;
            }

            int flag = 0;
            for (int i = 0; i < ectIdArr.length; ++i) {
                if (old.getEctId().trim().equals(ectIdArr[i].trim())) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                continue;
            }

            old.setEctStatus("3");
            error = error + empContractBo.update(old, old.getEctId());
        }
        if (error.length() > 0)
            addErrorInfo(error);
        else {
            addSuccessInfo("批量解除合同成功");
        }
        return "success";
    }

    public String checkOldEmployee(String empId) {
        if (empId == null) {
            return "success";
        }
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee emp = empBo.loadEmp(empId, null);
        if (emp == null) {
            return "success";
        }
        IEmpContractBo empContractBo = (IEmpContractBo) getBean("empContractBo");
        List list = empContractBo.searchByEmpNo(empId);
        Empcontract empct = (Empcontract) list.get(0);
        if ((empct.getEctStatus().equals("1")) && (empct.getEtcExpire().intValue() == 1)) {
            return "fail";
        }
        emp.setJoinYear(Float.valueOf((float) (new Date().getTime() - emp.getEmpJoinDate()
                .getTime())
                / 1000.0F / 3600.0F / 24.0F / 365.0F));
        if (emp.getJoinYear().floatValue() >= 10.0F) {
            return "员工入职年限已超过十年，应签订无限期合同";
        }
        if ((list != null) && (list.size() > 1)) {
            return "员工已签订两份（以上）合同，应签订无限期合同";
        }
        return "success";
    }

    public String deleteContract() {
        if (StringUtils.isEmpty(this.ectId)) {
            addErrorInfo("非法的请求参数，请选择要删除的记录");
            return "success";
        }
        IEmpContractBo empContractBo = (IEmpContractBo) getBean("empContractBo");
        Empcontract ect = empContractBo.getById(this.ectId);
        if (ect == null) {
            addErrorInfo("您要删除的记录已经被删除");
            return "success";
        }
        String fileName = ect.getEctAttatchment();
        if ((fileName != null) && (!"".equals(fileName))) {
            FileOperate.deleteFile("sys.profile.file.path", fileName);
        }
        empContractBo.delete(this.ectId);
        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee emp = empBo.loadEmp(ect.getEmployee().getId(), null);
        List oldList = empContractBo.searchByEmpNo(emp.getId());
        if ((oldList != null) && (oldList.size() > 0))
            emp.setContract((Empcontract) oldList.get(0));
        else {
            emp.setContract(null);
        }
        empBo.updateEmp(emp, emp.getId());
        addSuccessInfo("员工" + emp.getEmpName() + "的合同删除成功！");
        return "success";
    }

    public String updateContract() throws Exception {
        String currentEmpNo = getCurrentEmpNo();

        if ((this.employeeId == null) || ("".equals(this.employeeId))) {
            this.employeeId = currentEmpNo;
        }

        IEmpContractBo empContractBo = (IEmpContractBo) getBean("empContractBo");

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
                this.searchContract.setEctAttatchment(this.fileFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Employee emp = empBo.loadEmp(this.updateEctId, new String[] { "contract" });
        this.searchContract.setEmployee(emp);
        this.searchContract.setEctCreateBy(getCurrentEmpNo());
        this.searchContract.setEctLastChangeBy(getCurrentEmpNo());
        this.searchContract.setEctCreateDate(new Date());
        this.searchContract.setEctLastChangeTime(new Date());
        empContractBo.update(this.searchContract, emp.getContract().getEctId());
        clear();
        addActionMessage("更新员工合同成功");

        return "success";
    }

    public String attachDelete() throws Exception {
        if ((this.updateEctId == null) || (this.updateEctId.equals(""))
                || (this.fileFileName == null) || ("".equals(this.fileFileName))) {
            addActionError("参数传递错误！");
            return "error";
        }
        IEmpContractBo empContractBo = (IEmpContractBo) getBean("empContractBo");
        if (!empContractBo.deleteAttach(this.updateEctId, this.fileFileName)) {
            addActionError("附件删除失败");
            return "error";
        }
        addActionMessage("附件删除成功");
        return "success";
    }

    public String getYearOrMonth(Float joinYear) {
        int value = 0;
        String result = "";
        if (joinYear.floatValue() >= 1.0F) {
            value = Integer.parseInt(joinYear.intValue() + "");
            result = result + value + "个月";
        } else {
            joinYear = Float.valueOf(joinYear.floatValue() * 100.0F);
            value = Integer.parseInt(joinYear.intValue() + "");
            result = result + value + "个月";
        }

        return result;
    }

    public String getEctNo() {
        return this.ectNo;
    }

    public void setEctNo(String ectNo) {
        this.ectNo = ectNo;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFileFileName() {
        return this.fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getUpdateEctId() {
        return this.updateEctId;
    }

    public void setUpdateEctId(String updateEctId) {
        this.updateEctId = updateEctId;
    }

    public List<Employee> getContractList() {
        return this.contractList;
    }

    public void setContractList(List<Employee> contractList) {
        this.contractList = contractList;
    }

    public List<ContractType> getEmpTypeList() {
        return this.empTypeList;
    }

    public void setEmpTypeList(List<ContractType> empTypeList) {
        this.empTypeList = empTypeList;
    }

    public Date getEctStartDateTo() {
        return this.ectStartDateTo;
    }

    public void setEctStartDateTo(Date ectStartDateTo) {
        this.ectStartDateTo = ectStartDateTo;
    }

    public Date getEctEndDateTo() {
        return this.ectEndDateTo;
    }

    public void setEctEndDateTo(Date ectEndDateTo) {
        this.ectEndDateTo = ectEndDateTo;
    }

    private void clear() {
        this.ectNo = null;
        this.batchStartDate = null;
        this.batchEndDate = null;
        this.ectStartDateTo = null;
        this.ectEndDateTo = null;
        this.batchExpire = null;
        this.fileFileName = null;
        this.file = null;
        this.batchComments = null;
        this.batchStatus = null;
        this.batchempTypeId = null;
        this.employeeId = null;

        this.searchContract = null;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Pager getPage() {
        return this.page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }

    public List<Department> getDeptList() {
        return this.deptList;
    }

    public void setDeptList(List<Department> deptList) {
        this.deptList = deptList;
    }

    public Integer getToExpire() {
        return this.toExpire;
    }

    public void setToExpire(Integer toExpire) {
        this.toExpire = toExpire;
    }

    public Empcontract getSearchContract() {
        return this.searchContract;
    }

    public void setSearchContract(Empcontract searchContract) {
        this.searchContract = searchContract;
    }

    public String getEctId() {
        return this.ectId;
    }

    public void setEctId(String ectId) {
        this.ectId = ectId;
    }

    public String getIds() {
        return this.ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Date getBatchStartDate() {
        return this.batchStartDate;
    }

    public void setBatchStartDate(Date batchStartDate) {
        this.batchStartDate = batchStartDate;
    }

    public Date getBatchEndDate() {
        return this.batchEndDate;
    }

    public void setBatchEndDate(Date batchEndDate) {
        this.batchEndDate = batchEndDate;
    }

    public Integer getBatchExpire() {
        return this.batchExpire;
    }

    public void setBatchExpire(Integer batchExpire) {
        this.batchExpire = batchExpire;
    }

    public String getBatchComments() {
        return this.batchComments;
    }

    public void setBatchComments(String batchComments) {
        this.batchComments = batchComments;
    }

    public String getBatchStatus() {
        return this.batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public String getBatchempTypeId() {
        return this.batchempTypeId;
    }

    public void setBatchempTypeId(String batchempTypeId) {
        this.batchempTypeId = batchempTypeId;
    }

    public String getEctIds() {
        return this.ectIds;
    }

    public void setEctIds(String ectIds) {
        this.ectIds = ectIds;
    }

    public Integer getContinueYear() {
        return this.continueYear;
    }

    public void setContinueYear(Integer continueYear) {
        this.continueYear = continueYear;
    }

    public Integer getRadioSub() {
        return this.radioSub;
    }

    public void setRadioSub(Integer radioSub) {
        this.radioSub = radioSub;
    }

    public String getSearchOrExport() {
        return this.searchOrExport;
    }

    public void setSearchOrExport(String searchOrExport) {
        this.searchOrExport = searchOrExport;
    }

    public String getOutmatchModelId() {
        return this.outmatchModelId;
    }

    public void setOutmatchModelId(String outmatchModelId) {
        this.outmatchModelId = outmatchModelId;
    }

    public String getOutputIoName() {
        return this.outputIoName;
    }

    public void setOutputIoName(String outputIoName) {
        this.outputIoName = outputIoName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.ContractManagement JD-Core Version: 0.5.4
 */