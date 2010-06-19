package com.hr.examin.action;

import com.hr.base.DWRUtil;
import com.hr.configuration.bo.IAttdMachineBO;
import com.hr.configuration.domain.AttdMachine;
import com.hr.examin.bo.interfaces.IAttdSyncRecordBO;
import com.hr.examin.bo.interfaces.IAttdoriginaldataBO;
import com.hr.examin.domain.AttdSyncRecord;
import com.hr.examin.domain.Attdoriginaldata;
import com.hr.machine.IMachineBO;
import com.hr.machine.zkem.m200.Machine;
import com.hr.machine.zkem.m200.MachineBOImpl;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.birt.report.model.api.util.StringUtil;

public class DwrSyncAttdMachine {
    private IMachineBO machineBO;

    public String syncToMachine(String empIdStr, String machineIdStr) {
        if ("error".equals(DWRUtil.checkAuth("attdSyncRecordShow", "execute"))) {
            return "您无权执行此操作，请重新登陆！";
        }
        if ((empIdStr == null) || (empIdStr.equals(""))) {
            return "请你选择要同步的用户！";
        }
        if ((machineIdStr == null) || (machineIdStr.equals(""))) {
            return "请选择考勤机！";
        }
        String[] empNoArray = empIdStr.split(";");

        IEmployeeBo employeeBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        List employeeList = employeeBo.getEmpsNeedCard(empNoArray);

        String returnStr = "";

        String[] machineIdArray = machineIdStr.split(";");
        List resultList = new ArrayList();
        IAttdMachineBO attdMachineBO = (IAttdMachineBO) SpringBeanFactory.getBean("attdMachineBO");
        for (String machineId : machineIdArray) {
            if (!returnStr.equals(""))
                return returnStr += "  ";

            AttdMachine attdMachine = attdMachineBO.loadAttdMachine(machineId);

            Machine machine = attdMachineToMachine(attdMachine);

            this.machineBO = new MachineBOImpl(machine);
            if (this.machineBO.connectNet()) {
                SyncAttdMachineData syncAttdMachineData = new SyncAttdMachineData(this.machineBO);
                resultList = syncAttdMachineData.syncToMachine(employeeList);

                IAttdSyncRecordBO attdSyncRecordBO = (IAttdSyncRecordBO) SpringBeanFactory
                        .getBean("attdSyncRecordBO");

                attdSyncRecordBO.generateAttdSyncRecord(employeeList, attdMachine);

                returnStr = returnStr + dealResult(resultList, attdMachine.getMacNo());
            } else {
                returnStr = returnStr + attdMachine.getMacNo() + "连接失败";
            }
        }
        return returnStr;
    }

    public String syncToProject(String empIdStr, String machineIdStr) {
        if ("error".equals(DWRUtil.checkAuth("attdSyncRecordShow", "execute"))) {
            return "您无权执行此操作，请重新登陆！";
        }
        if ((machineIdStr == null) || (machineIdStr.equals(""))) {
            return "请选择考勤机！";
        }
        String[] empNoArray = null;
        if ((empIdStr != null) && (!empIdStr.equalsIgnoreCase(""))) {
            empNoArray = empIdStr.split(";");
        }

        IEmployeeBo employeeBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        List employeeList = employeeBo.getEmpsNeedCard(empNoArray);

        String[] machineIdArray = machineIdStr.split(";");
        IAttdMachineBO attdMachineBO = (IAttdMachineBO) SpringBeanFactory.getBean("attdMachineBO");
        String result = "";
        for (String machineId : machineIdArray) {
            if (!result.equals(""))
                return result += "  ";

            AttdMachine attdMachine = attdMachineBO.loadAttdMachine(machineId);

            Machine machine = attdMachineToMachine(attdMachine);

            this.machineBO = new MachineBOImpl(machine);
            if (this.machineBO.connectNet()) {
                compareTo(employeeList, attdMachine);
                result = result + "考勤机" + attdMachine.getMacNo() + "同步成功";
            } else {
                result = result + "考勤机" + attdMachine.getMacNo() + "连接失败";
            }
        }

        return result;
    }

    public String batchRead(String empIdStr, String machineIdStr, String dateFrom, String dateTo,
            String readAll, String onlyRead) {
        if ("error".equals(DWRUtil.checkAuth("attdSyncRecordShow", "execute"))) {
            return "您无权执行此操作，请重新登陆！";
        }
        if ((machineIdStr == null) || (machineIdStr.equals(""))) {
            return "请选择考勤机！";
        }

        IEmployeeBo employeeBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        List employeeList = employeeBo.getEmpsNeedCard(new String[0]);

        String[] machineIdArray = machineIdStr.split(";");
        IAttdMachineBO attdMachineBO = (IAttdMachineBO) SpringBeanFactory.getBean("attdMachineBO");
        String result = "";
        for (String machineId : machineIdArray) {
            if (!result.equals(""))
                return result += "  ";

            AttdMachine attdMachine = attdMachineBO.loadAttdMachine(machineId);

            Machine machine = attdMachineToMachine(attdMachine);

            this.machineBO = new MachineBOImpl(machine);
            if (this.machineBO.connectNet()) {
                SyncAttdMachineData syncAttdMachineData = new SyncAttdMachineData(this.machineBO);
                List resultList = syncAttdMachineData.batchRead(employeeList, null, null, null,
                                                                null);

                IAttdoriginaldataBO attdorigianldataBO = (IAttdoriginaldataBO) SpringBeanFactory
                        .getBean("attdoriginaldataBO");

                insertAttdoriginalData(employeeList, resultList, attdorigianldataBO, attdMachine);
                result = result + "考勤机" + attdMachine.getMacNo() + "成功读取数据";
            } else {
                result = result + "考勤机" + attdMachine.getMacNo() + "连接失败";
            }
        }
        return result;
    }

    public String batchDelete(String empIdStr, String machineIdStr) {
        if ("error".equals(DWRUtil.checkAuth("attdSyncRecordShow", "execute"))) {
            return "您无权执行此操作，请重新登陆！";
        }
        if ((empIdStr == null) || (empIdStr.equalsIgnoreCase(""))) {
            return "请你选择要在考勤机中删除的用户！";
        }
        if ((machineIdStr == null) || (machineIdStr.equals(""))) {
            return "请选择考勤机！";
        }
        String[] empNoArray = null;
        empNoArray = empIdStr.split(";");

        IEmployeeBo employeeBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        List employeeList = employeeBo.getEmpsNeedCard(empNoArray);

        IAttdMachineBO attdMachineBO = (IAttdMachineBO) SpringBeanFactory.getBean("attdMachineBO");

        String[] machineIdArray = machineIdStr.split(";");
        String returnStr = "";
        for (String machineId : machineIdArray) {
            if (!returnStr.equals(""))
                return returnStr += "  ";

            AttdMachine attdMachine = attdMachineBO.loadAttdMachine(machineId);

            Machine machine = attdMachineToMachine(attdMachine);

            this.machineBO = new MachineBOImpl(machine);
            if (this.machineBO.connectNet()) {
                SyncAttdMachineData syncAttdMachineData = new SyncAttdMachineData(this.machineBO);
                employeeList = syncAttdMachineData.batchDelete(employeeList);

                IAttdSyncRecordBO attdSyncRecordBO = (IAttdSyncRecordBO) SpringBeanFactory
                        .getBean("attdSyncRecordBO");

                attdSyncRecordBO.deleteAttdSyncRecord(employeeList, attdMachine);
                returnStr = returnStr + dealResult(employeeList, attdMachine.getMacNo());
            } else {
                returnStr = returnStr + "考勤机" + attdMachine.getMacNo() + "连接失败";
            }
        }

        return returnStr;
    }

    private String dealResult(List<Employee> resultList, String machineNo) {
        StringBuffer result = new StringBuffer();
        String errorMessage = "";

        for (Employee emp : resultList) {
            if (emp.getSyncResult().intValue() == 1) {
                if (errorMessage.equals("")) {
                    errorMessage = "在同步时操作成功的员工为：";
                }
                errorMessage = errorMessage + "(" + emp.getEmpDistinctNo() + "|" + emp.getEmpName()
                        + "|考勤机:" + machineNo + ") ";
            }
        }
        result.append(errorMessage);
        errorMessage = "";

        for (Employee emp : resultList) {
            if (emp.getSyncResult().intValue() == 2) {
                if (errorMessage.equals("")) {
                    errorMessage = "在同步时出错的员工为：";
                }
                errorMessage = errorMessage + "(" + emp.getEmpDistinctNo() + "|" + emp.getEmpName()
                        + "|考勤机:" + machineNo + ") ";
            }
        }
        if (!result.toString().equals("")) {
            result.append("  ");
        }
        result.append(errorMessage);
        errorMessage = "";

        for (Employee emp : resultList) {
            if (emp.getSyncResult().intValue() == 0) {
                if (errorMessage.equals("")) {
                    errorMessage = "在同步时未操作员工为：";
                }
                errorMessage = errorMessage + "(" + emp.getEmpDistinctNo() + "|" + emp.getEmpName()
                        + "|考勤机:" + machineNo + ") ";
            }
        }
        if (!result.toString().equals("")) {
            result.append("  ");
        }
        result.append(errorMessage);
        return result.toString();
    }

    private void compareTo(List<Employee> employeeList, AttdMachine machine) {
        SyncAttdMachineData syncAttdMachineData = new SyncAttdMachineData(this.machineBO);

        List resultList = syncAttdMachineData.syncToProject(employeeList);

        Map map = empListToMap(resultList);
        IAttdSyncRecordBO attdSyncRecordBO = (IAttdSyncRecordBO) SpringBeanFactory
                .getBean("attdSyncRecordBO");

        List recordList = new ArrayList();

        for (Employee emp1 : employeeList) {
            if (emp1.getEmpShiftNo() == null)
                continue;
            Employee emp2 = (Employee) map.get(emp1.getEmpDistinctNo());
            if (emp2 == null)
                continue;
            if (emp2.getEmpShiftNo() == null) {
                continue;
            }
            AttdSyncRecord record = attdSyncRecordBO.getAttdSyncRecord(emp1.getId(), machine
                    .getMacId());
            if (record != null) {
                if (!StringUtil.isEmpty(emp2.getEmpShiftNo())) {
                    if (!emp2.getEmpShiftNo().equals(emp1.getEmpShiftNo()))
                        record.setAsrSync(Integer.valueOf(0));
                    else
                        record.setAsrSync(Integer.valueOf(1));
                    record.setAsrEmpCardNo(emp2.getEmpShiftNo());
                } else {
                    record.setAsrEmpCardNo("暂无卡号");
                }
                recordList.add(record);
            }
        }
        attdSyncRecordBO.insertDataList(recordList);
    }

    private Machine attdMachineToMachine(AttdMachine attdMachine) {
        Machine machine = new Machine();

        machine.setIpAdd(attdMachine.getMacIP());

        machine.setPassword(attdMachine.getMacPassword().intValue());

        if (attdMachine.getMacPort() != null) {
            machine.setPort(Integer.parseInt(attdMachine.getMacPort()));
        }
        return machine;
    }

    private String insertAttdoriginalData(List<Employee> empList, List<Attdoriginaldata> dataList,
            IAttdoriginaldataBO dataBo, AttdMachine machine) {
        boolean flag = false;
        Date startDate = new Date();
        Date endDate = new Date();
        List resultList = new ArrayList();
        Map map = empListToMap(empList);

        for (Attdoriginaldata data : dataList) {
            if (data.getAodEmpDistinctNo() == null) {
                continue;
            }
            flag = false;

            Employee emp = (Employee) map.get(data.getAodEmpDistinctNo());
            if (emp != null) {
                flag = true;
            }

            if (flag) {
                resultList.add(data);
                data.setAodTtdMachineNo(machine.getMacNo());
            }

            if (data.getAodAttdDate().compareTo(startDate) < 0) {
                startDate = data.getAodAttdDate();
            }

            if (data.getAodAttdDate().compareTo(endDate) > 0) {
                startDate = data.getAodAttdDate();
            }
        }
        dataBo.insertDataList(resultList, startDate, endDate);
        return null;
    }

    private Map<String, Employee> empListToMap(List<Employee> list) {
        Map map = new HashMap();
        for (Employee emp : list) {
            map.put(emp.getEmpDistinctNo(), emp);
        }
        return map;
    }
}