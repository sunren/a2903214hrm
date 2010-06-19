package com.hr.examin.action;

import com.hr.base.BaseAction;
import com.hr.examin.domain.Attdoriginaldata;
import com.hr.machine.IMachineBO;
import com.hr.machine.zkem.m200.Record;
import com.hr.machine.zkem.m200.User;
import com.hr.profile.domain.Employee;
import com.hr.util.DateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class SyncAttdMachineData extends BaseAction {
    static Logger logger = Logger.getLogger(DwrSyncAttdMachine.class.getName());
    private IMachineBO machineBO;

    public SyncAttdMachineData(IMachineBO machineBO) {
        this.machineBO = machineBO;
    }

    public List<Employee> syncToMachine(List<Employee> employeeList) {
        logger.info(getCurrentEmp().getEmpName() + "|" + "向考勤机中同步员工信息");

        List<User> userList = empToUser(employeeList);
        try {
            for (User user : userList) {
                if (this.machineBO.setUserInfo(user)) {
                    for (Employee emp : employeeList) {
                        emp.setSyncResult(Integer.valueOf(1));
                    }
                } else
                    for (Employee emp : employeeList)
                        emp.setSyncResult(Integer.valueOf(2));
            }
        } catch (Exception e) {
            return employeeList;
        }

        return employeeList;
    }

    public List<Employee> syncToProject(List<Employee> employeeList) {
        logger.info(getCurrentEmp().getEmpName() + "|" + "同步考勤机中的员工信息");

        List returnList = new ArrayList();

        List userList = this.machineBO.getAllUserInfo();

        returnList = userToEmp(employeeList, userList);

        return returnList;
    }

    public List<Attdoriginaldata> batchRead(List<Employee> employeeList, Date dateFrom,
            Date dateTo, String readAll, String onlyRead) {
        logger.info(getCurrentEmp().getEmpName() + "|" + "批量读取考勤信息");

        int[] userIds = new int[employeeList.size()];

        for (int i = 0; i < employeeList.size(); ++i) {
            userIds[i] = ((Employee) employeeList.get(i)).getEmpMachineNo().intValue();
        }

        List list = this.machineBO.getAllRecord(userIds, dateFrom, dateTo, true, true);
        List recordList = new ArrayList();
        if (list != null) {
            recordList = recordToAttdSyncRecord(employeeList, list);
        }

        return recordList;
    }

    public List<Employee> batchDelete(List<Employee> employeeList) {
        logger.info(getCurrentEmp().getEmpName() + "|" + "批量删除考勤机中员工信息");
        try {
            for (Employee emp : employeeList) {
                User user = empToUser(emp);
                if (this.machineBO.deleteUser(user)) {
                    emp.setSyncResult(Integer.valueOf(1));
                    emp.setEmpShiftNo(null);
                } else {
                    emp.setSyncResult(Integer.valueOf(2));
                }
            }
        } catch (Exception e) {
            return employeeList;
        }

        return employeeList;
    }

    public List<User> empToUser(List<Employee> empList) {
        List userList = new ArrayList();
        for (Employee emp : empList) {
            User user = new User();
            user.setName(emp.getEmpName());
            user.setUserId(emp.getEmpMachineNo().intValue());
            user.setCardNumber(emp.getEmpShiftNo());
            user.setEnabled(true);
            userList.add(user);
        }

        return userList;
    }

    public User empToUser(Employee emp) {
        User user = new User();
        user.setName(emp.getEmpName());
        user.setUserId(emp.getEmpMachineNo().intValue());
        user.setCardNumber(emp.getEmpShiftNo());
        user.setEnabled(true);

        return user;
    }

    public List<Employee> userToEmp(List<Employee> empList, List<User> userList) {
        Map map = userListToMap(userList);
        for (int i = 0; i < empList.size(); ++i) {
            User user = (User) map.get(Long.valueOf(Long.parseLong(((Employee) empList.get(i))
                    .getEmpMachineNo().toString())));
            if (user != null) {
                ((Employee) empList.get(i)).setEmpShiftNo(user.getCardNumber());
                ((Employee) empList.get(i)).setSyncResult(Integer.valueOf(1));
            }
        }
        return empList;
    }

    public List<Attdoriginaldata> recordToAttdSyncRecord(List<Employee> empList,
            List<Record> recordList) {
        List list = new ArrayList();

        Map map = employeeListToMap(empList);
        for (Record record : recordList) {
            Attdoriginaldata data = new Attdoriginaldata();

            String dateStr = record.getYear() + "-";
            if (record.getMonth() < 10L) {
                dateStr = dateStr + "0";
            }
            dateStr = dateStr + record.getMonth();
            dateStr = dateStr + "-";
            if (record.getDay() < 10L) {
                dateStr = dateStr + "0";
            }
            dateStr = dateStr + record.getDay();

            String timeStr = dateStr + " ";
            if (record.getHour() < 10L) {
                timeStr = timeStr + "0";
            }
            timeStr = timeStr + record.getHour();
            timeStr = timeStr + ":";
            if (record.getMinute() < 10L) {
                timeStr = timeStr + "0";
            }
            timeStr = timeStr + record.getMinute();
            timeStr = timeStr + ":";
            if (record.getSecond() < 10L) {
                timeStr = timeStr + "0";
            }
            timeStr = timeStr + record.getSecond();

            Date aodCardTime = DateUtil.parseDateByFormat(timeStr, "yyyy-MM-dd hh:mm:ss");

            Employee emp = (Employee) map.get(Long.valueOf(record.getUserId()));
            if (emp == null) {
                continue;
            }

            data.setAodId(emp.getId() + DateUtil.formatDateToS(aodCardTime, "yyyyMMddHHmmss"));

            data.setAodCardTime(aodCardTime);

            data.setAodAttdDate(DateUtil.parseDateByFormat(dateStr, "yyyy-MM-dd"));
            data.setAttdEmp(emp);
            data.setAodEmpDistinctNo(emp.getEmpDistinctNo());

            data.setAodStatus(Integer.valueOf(0));
            list.add(data);
        }

        return list;
    }

    public Map<Long, Employee> employeeListToMap(List<Employee> list) {
        Map map = new HashMap();
        for (Employee emp : list) {
            map.put(Long.valueOf(Long.parseLong(emp.getEmpMachineNo().toString())), emp);
        }
        return map;
    }

    public Map<Long, User> userListToMap(List<User> list) {
        Map map = new HashMap();
        for (User user : list) {
            map.put(Long.valueOf(Long.parseLong(user.getUserId() + "")), user);
        }
        return map;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.SyncAttdMachineData JD-Core Version: 0.5.4
 */