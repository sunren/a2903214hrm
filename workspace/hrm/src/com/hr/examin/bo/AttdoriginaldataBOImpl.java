package com.hr.examin.bo;

import com.hr.base.BaseAction;
import com.hr.configuration.domain.Department;
import com.hr.examin.bo.interfaces.IAttdoriginaldataBO;
import com.hr.examin.dao.interfaces.IAttdoriginaldataDAO;
import com.hr.examin.dao.interfaces.IEmpshiftDAO;
import com.hr.examin.domain.Attdoriginaldata;
import com.hr.examin.domain.Attendshift;
import com.hr.examin.domain.Empshift;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.BaseCrit;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class AttdoriginaldataBOImpl implements IAttdoriginaldataBO {
    private IAttdoriginaldataDAO attdoriginaldataDAO;

    public boolean addAttdoriginaldata(Attdoriginaldata data) {
        try {
            this.attdoriginaldataDAO.saveObject(data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean deleteAttdoriginaldata(String aodId) {
        try {
            Attdoriginaldata data = (Attdoriginaldata) this.attdoriginaldataDAO
                    .loadObject(Attdoriginaldata.class, aodId, null, new boolean[0]);
            if (data == null) {
                return false;
            }
            this.attdoriginaldataDAO.deleteObject(data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Attdoriginaldata getAttdoriginaldata(String aodId) {
        return (Attdoriginaldata) this.attdoriginaldataDAO.loadObject(Attdoriginaldata.class,
                                                                      aodId, null, new boolean[0]);
    }

    public boolean saveAttdoriginaldata(Attdoriginaldata data) {
        this.attdoriginaldataDAO.saveOrupdate(data);

        return true;
    }

    public boolean insertDataList(List<Attdoriginaldata> dataList, Date cardStartDate,
            Date cardEndDate) {
        if (dataList.size() == 0) {
            return true;
        }

        String[] empIds = getEmpIds(dataList);

        getExactDateOfCardtime(dataList, cardStartDate, cardEndDate, empIds);

        this.attdoriginaldataDAO.saveOrupdate(dataList);
        return true;
    }

    private void getExactDateOfCardtime(List<Attdoriginaldata> dataList, Date cardStartDate,
            Date cardEndDate, String[] empIds) {
        Map sysMap = DatabaseSysConfigManager.getInstance().getProperties();
        String startMinute = (String) sysMap.get("sys.shift.start.minute");
        String endMinute = (String) sysMap.get("sys.shift.end.minute");
        Map existShiftMap = provideEmpShiftData(empIds, cardStartDate, cardEndDate);

        Attendshift shift = null;
        Date beforeDate = null;
        for (Attdoriginaldata data : dataList) {
            beforeDate = DateUtil.dateAdd(data.getAodAttdDate(), -1);
            shift = (Attendshift) existShiftMap.get(data.getAttdEmp().getId()
                    + DateUtil.formatDate(beforeDate));
            if (shift == null)
                continue;
            if (shift.getAttsNightShift().intValue() == 0) {
                continue;
            }

            String[] cut = shift.getAttsSession().split(",");
            Date onTime = DateUtil.parseDateByFormat(DateUtil.formatDateToS(beforeDate,
                                                                            "yyyy-MM-dd")
                    + " " + cut[0].split("-")[0], "yyyy-MM-dd HH:mm");

            Date offTime = DateUtil.parseDateByFormat(DateUtil.formatDateToS(beforeDate,
                                                                             "yyyy-MM-dd")
                    + " " + cut[(cut.length - 1)].split("-")[1], "yyyy-MM-dd HH:mm");

            Date onTime_b = DateUtil.dateTimeAdd(onTime, 0 - Integer.parseInt(startMinute), 12);
            Date offTime_a = DateUtil.dateTimeAdd(offTime, Integer.parseInt(endMinute), 12);

            if ((onTime_b.getTime() <= data.getAodCardTime().getTime())
                    && (data.getAodCardTime().getTime() <= offTime_a.getTime())) {
                data.setAodAttdDate(beforeDate);
            }
        }
    }

    private String[] getEmpIds(List<Attdoriginaldata> dataList) {
        String[] empIds = new String[dataList.size()];
        int count = 0;
        for (Attdoriginaldata data : dataList) {
            empIds[count] = data.getAttdEmp().getId();
            ++count;
        }

        return empIds;
    }

    private Map<String, Attendshift> provideEmpShiftData(String[] empIds, Date start, Date end) {
        if ((empIds == null) || (empIds.length == 0)) {
            return new HashMap(0);
        }

        Date beforeDate = DateUtil.dateAdd(start, -1);

        DetachedCriteria dc = DetachedCriteria.forClass(Empshift.class);
        dc.setFetchMode(Empshift.PROP_EMPSHIFT_SHIFT_NO, FetchMode.JOIN);
        BaseCrit.addDCDateRange(dc, Empshift.PROP_EMPSHIFT_DATE, beforeDate, end);

        if (empIds.length < 500) {
            dc.add(Restrictions.in("empshiftEmpNo.id", empIds));
        }
        IEmpshiftDAO dao = (IEmpshiftDAO) SpringBeanFactory.getBean("empshiftDAO");
        List<Empshift> empShiftList = dao.findByCriteria(dc);

        Map resultMap = new HashMap();
        for (Empshift empShift : empShiftList) {
            String key = empShift.getEmpshiftEmpNo().getId()
                    + DateUtil.formatDate(empShift.getEmpshiftDate());
            resultMap.put(key, empShift.getEmpshiftShiftNo());
        }
        return resultMap;
    }

    public List<Attdoriginaldata> getOriginalDataList(DetachedCriteria dc, Pager page, String emp,
            String dept, Attdoriginaldata paramData, Date dateFrom, Date dateTo) {
        dc.createAlias(Attdoriginaldata.PROP_AOD_EMP_NO, "emp", 1);
        dc.createAlias("emp." + Employee.PROP_EMP_DEPT_NO, "empDeptNo", 1);
        BaseCrit.addEmpDC(dc, "emp", emp);
        BaseCrit.addDeptDC(dc, "emp." + Employee.PROP_EMP_DEPT_NO,
                           "emp." + Employee.PROP_EMP_PB_NO, Integer.valueOf(1), new Department(
                                   dept));

        BaseCrit.addDCDateRange(dc, Attdoriginaldata.PROP_AOD_ATTD_DATE, dateFrom, dateTo);
        BaseCrit.addDC(dc, Attdoriginaldata.PROP_AOD_TTD_MACHINE_NO, "like",
                       new String[] { paramData.getAodTtdMachineNo() });

        if ((paramData.getAodStatus() != null) && (paramData.getAodStatus().intValue() != 2)) {
            dc.add(Restrictions.eq(Attdoriginaldata.PROP_AOD_STATUS, paramData.getAodStatus()));
        }

        BaseAction.addOrders(dc, page, new String[] {
                Attdoriginaldata.PROP_AOD_EMP_NO + "." + Employee.PROP_EMP_DISTINCT_NO,
                Attdoriginaldata.PROP_AOD_ATTD_DATE, Attdoriginaldata.PROP_AOD_CARD_TIME });

        page.splitPage(dc);
        return this.attdoriginaldataDAO.findByCriteria(dc, page.getPageSize(), page
                .getCurrentPage());
    }

    public boolean deleteDataInDateDomain(Date dateFrom, Date dateTo) {
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom1 = format.format(dateFrom);
        String dateTo1 = format.format(dateTo);
        String hql = "delete Attdoriginaldata where aodAttdDate >= '" + dateFrom1
                + "' and aodAttdDate <= '" + dateTo1 + "'";
        this.attdoriginaldataDAO.exeHql(hql);
        return true;
    }

    public boolean saveOriginalData(String[] empNo, Date attdDate, Date attdCardTime,
            String aodTtdMachineNo, String memo, String currentEmpName) {
        List dataList = new ArrayList();
        IEmployeeBo esaBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        List<Employee> empList = esaBo.searchEmpArray(empNo);
        for (Employee emp : empList) {
            Attdoriginaldata data = new Attdoriginaldata();
            data.setAodId(emp.getId() + DateUtil.formatDateToS(attdCardTime, "yyyyMMddHHmmss"));
            data.setAttdEmp(emp);
            data.setAodCardTime(attdCardTime);
            data.setAodEmpDistinctNo(emp.getEmpDistinctNo());
            data.setAodAttdDate(attdDate);
            data.setAodTtdMachineNo(aodTtdMachineNo);
            data.setAodStatus(Integer.valueOf(0));
            data.setAodLastModifyUser(currentEmpName);
            data.setAodLastModifyTime(new Date());
            data.setAodStatus(Integer.valueOf(1));
            data.setAodMemo(memo);

            dataList.add(data);
        }

        this.attdoriginaldataDAO.saveOrupdate(dataList);
        return true;
    }

    public IAttdoriginaldataDAO getAttdoriginaldataDAO() {
        return this.attdoriginaldataDAO;
    }

    public void setAttdoriginaldataDAO(IAttdoriginaldataDAO attdoriginaldataDAO) {
        this.attdoriginaldataDAO = attdoriginaldataDAO;
    }

    private void checkOrderMethod(DetachedCriteria detachedCriteria, String order) {
        if ((order != null) && (order.trim().length() > 1) && (order.indexOf('-') != -1)) {
            String[] pram = order.split("-");
            if ((pram[0] == null) || (pram[0].length() < 1)) {
                return;
            }
            String orde = pram[0];
            if ((pram.length == 2) && (pram[1].equalsIgnoreCase("up")))
                detachedCriteria.addOrder(Order.asc(orde));
            else
                detachedCriteria.addOrder(Order.desc(orde));
        } else {
            detachedCriteria.addOrder(Order.asc("attdEmp.empName"));
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.bo.AttdoriginaldataBOImpl JD-Core Version: 0.5.4
 */