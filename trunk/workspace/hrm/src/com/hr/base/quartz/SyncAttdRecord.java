package com.hr.base.quartz;

import com.hr.configuration.bo.IAttdMachineBO;
import com.hr.configuration.domain.AttdMachine;
import com.hr.examin.action.SyncAttdMachineData;
import com.hr.examin.bo.interfaces.IAttdoriginaldataBO;
import com.hr.examin.domain.Attdoriginaldata;
import com.hr.machine.zkem.m200.Machine;
import com.hr.machine.zkem.m200.MachineBOImpl;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.security.web.action.LoginAction;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SyncAttdRecord implements Job {
    static Logger logger = Logger.getLogger(LoginAction.class.getName());

    public void execute() throws JobExecutionException {
        logger.info("开始同步考勤记录");

        syncOperate();

        logger.info("同步考勤记录结束");
    }

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
    }

    private void syncOperate() {
        IAttdMachineBO attdMachineBO = (IAttdMachineBO) SpringBeanFactory.getBean("attdMachineBO");
        List<AttdMachine> attdMachineList = attdMachineBO.FindEnabledAttdMachine();
        if ((attdMachineList == null) || (attdMachineList.isEmpty())) {
            return;
        }

        IEmployeeBo employeeBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        List employeeList = employeeBo.getEmpsNeedCard(new String[0]);
        if ((employeeList == null) || (employeeList.isEmpty())) {
            return;
        }
        for (AttdMachine attdMachine : attdMachineList) {
            Machine machine = attdMachineToMachine(attdMachine);
            MachineBOImpl machineBO = new MachineBOImpl(machine);
            if (machineBO.connectNet()) {
                SyncAttdMachineData syncAttdMachineData = new SyncAttdMachineData(machineBO);
                List resultList = syncAttdMachineData.batchRead(employeeList, null, null, null,
                                                                null);

                IAttdoriginaldataBO attdorigianldataBO = (IAttdoriginaldataBO) SpringBeanFactory
                        .getBean("attdoriginaldataBO");
                insertAttdoriginalData(employeeList, resultList, attdorigianldataBO, attdMachine);
            }
        }
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

    public String insertAttdoriginalData(List<Employee> empList, List<Attdoriginaldata> dataList,
            IAttdoriginaldataBO dataBo, AttdMachine machine) {
        boolean flag = false;
        Date startDate = new Date();
        Date endDate = new Date();
        List resultList = new ArrayList();
        for (Attdoriginaldata data : dataList) {
            if (data.getAodEmpDistinctNo() == null) {
                continue;
            }
            flag = false;
            for (Employee emp : empList) {
                if (data.getAodEmpDistinctNo().equals(emp.getEmpDistinctNo())) {
                    flag = true;
                }
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
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.base.quartz.SyncAttdRecord JD-Core Version: 0.5.4
 */