package com.hr.examin.bo;

import com.hr.base.BaseAction;
import com.hr.configuration.domain.AttdMachine;
import com.hr.examin.bo.interfaces.IAttdSyncRecordBO;
import com.hr.examin.dao.interfaces.IAttdSyncRecordDAO;
import com.hr.examin.domain.AttdSyncRecord;
import com.hr.profile.domain.Employee;
import com.hr.util.BaseCrit;
import com.hr.util.Pager;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public class AttdSyncRecordBOImpl implements IAttdSyncRecordBO {
    private IAttdSyncRecordDAO attdSyncRecordDAO;

    public IAttdSyncRecordDAO getAttdSyncRecordDAO() {
        return this.attdSyncRecordDAO;
    }

    public void setAttdSyncRecordDAO(IAttdSyncRecordDAO attdSyncRecordDAO) {
        this.attdSyncRecordDAO = attdSyncRecordDAO;
    }

    public boolean addAttdSyncRecord(AttdSyncRecord data) {
        try {
            this.attdSyncRecordDAO.saveObject(data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean deleteAttdSyncRecord(String srcId) {
        try {
            AttdSyncRecord data = (AttdSyncRecord) this.attdSyncRecordDAO
                    .loadObject(AttdSyncRecord.class, srcId, null, new boolean[0]);
            if (data == null) {
                return false;
            }
            this.attdSyncRecordDAO.deleteObject(data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public AttdSyncRecord getAttdSyncRecord(String srcId) {
        return (AttdSyncRecord) this.attdSyncRecordDAO.loadObject(AttdSyncRecord.class, srcId,
                                                                  null, new boolean[0]);
    }

    public List<AttdSyncRecord> getAttdSyncRecordList(DetachedCriteria dc, Pager page) {
        if (page != null) {
            BaseAction.addOrders(dc, page, new String[] { "emp." + Employee.PROP_EMP_DISTINCT_NO
                    + "-up" });
            page.splitPage(dc);
            return this.attdSyncRecordDAO.findByCriteria(dc, page.getPageSize(), page
                    .getCurrentPage());
        }

        BaseAction.addOrders(dc, null, new String[] { "emp." + Employee.PROP_EMP_DISTINCT_NO
                + "-up" });
        return this.attdSyncRecordDAO.findByCriteria(dc);
    }

    public boolean insertDataList(List<AttdSyncRecord> dataList) {
        this.attdSyncRecordDAO.saveOrupdate(dataList);
        return true;
    }

    public boolean saveAttdSyncRecord(AttdSyncRecord data) {
        this.attdSyncRecordDAO.saveOrupdate(data);

        return true;
    }

    private String[] getEmpIds(List<AttdSyncRecord> dataList) {
        String[] empIds = new String[dataList.size()];
        int count = 0;
        for (AttdSyncRecord data : dataList) {
            empIds[count] = data.getAsrEmp().getId();
            ++count;
        }

        return empIds;
    }

    public boolean generateAttdSyncRecord(List<Employee> employeeList, AttdMachine machine) {
        List attdSyncRecordList = new ArrayList();
        for (Employee emp : employeeList) {
            AttdSyncRecord record = getAttdSyncRecord(emp.getId(), machine.getMacId());
            if (record != null) {
                if (emp.getSyncResult().intValue() == 1) {
                    record.setAsrSync(Integer.valueOf(1));
                    if ((emp.getEmpShiftNo() != null) && (!emp.getEmpShiftNo().equals("")))
                        record.setAsrEmpCardNo(emp.getEmpShiftNo());
                    else
                        record.setAsrEmpCardNo("暂无卡号");
                } else {
                    record.setAsrSync(Integer.valueOf(0));
                }
            } else {
                record = new AttdSyncRecord();
                record.setAsrAttdMachine(machine);
                record.setAsrEmp(emp);
                if ((emp.getEmpShiftNo() != null) && (!emp.getEmpShiftNo().equals("")))
                    record.setAsrEmpCardNo(emp.getEmpShiftNo());
                else
                    record.setAsrEmpCardNo("暂无卡号");
                record.setAsrEmpMachineNo(emp.getEmpMachineNo());
                if (emp.getSyncResult().intValue() == 1)
                    record.setAsrSync(Integer.valueOf(1));
                else
                    record.setAsrSync(Integer.valueOf(0));
            }
            attdSyncRecordList.add(record);
        }
        this.attdSyncRecordDAO.saveOrupdate(attdSyncRecordList);

        return true;
    }

    public boolean deleteAttdSyncRecord(List<Employee> employeeList, AttdMachine machine) {
        for (Employee emp : employeeList) {
            AttdSyncRecord record = getAttdSyncRecord(emp.getId(), machine.getMacId());
            if (record != null)
                ;
            if (emp.getSyncResult().intValue() == 1) {
                this.attdSyncRecordDAO.deleteObject(record);
            } else {
                record.setAsrSync(Integer.valueOf(0));
                this.attdSyncRecordDAO.saveOrupdate(record);
            }
        }

        return true;
    }

    public AttdSyncRecord getAttdSyncRecord(String empId, String machineId) {
        DetachedCriteria dc = DetachedCriteria.forClass(AttdSyncRecord.class);
        BaseCrit.addDC(dc, AttdSyncRecord.PROP_ASR_EMP_NO, Employee.PROP_ID,
                       new Object[] { new Employee(empId) });
        BaseCrit.addDC(dc, AttdSyncRecord.PROP_ASR_ATTD_MACHINE_NO, AttdMachine.PROP_MAC_ID,
                       new Object[] { new AttdMachine(machineId) });
        List recordList = this.attdSyncRecordDAO.findByCriteria(dc);
        if ((recordList == null) || (recordList.isEmpty())) {
            return null;
        }
        return (AttdSyncRecord) recordList.get(0);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.bo.AttdSyncRecordBOImpl JD-Core Version: 0.5.4
 */