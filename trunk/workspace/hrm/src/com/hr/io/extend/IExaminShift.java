package com.hr.io.extend;

import com.hr.examin.bo.ExaminDateUtil;
import com.hr.examin.bo.interfaces.IEmpshiftBo;
import com.hr.examin.domain.Attendshift;
import com.hr.examin.domain.Empshift;
import com.hr.hibernate.IHibernateUtil;
import com.hr.io.domain.ExaminShiftBean;
import com.hr.io.domain.InmatchModel;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.DateUtil;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class IExaminShift extends ICheckAndInsert {
    String msgNotExist;
    String msgShiftNotExist;
    String msgEmpNoShift;

    public IExaminShift() {
        this.msgNotExist = "编号为{0}的员工不存在";
        this.msgShiftNotExist = "代码为{0}的班次未启用或不存在";
        this.msgEmpNoShift = "编号为{0}的员工为非排班人员，不能排班";
    }

    public int[] insertTransmit(List insertList, CommonParameters commonParas) {
        Employee currentEmp = commonParas.getCurrEmp();
        Map empInfoMap = getEmpInfo();
        Map shiftMap = getAllShift();
        int[] result = { 0, 0 };

        String params = commonParas.getImportParameter();
        String year = params.split(",")[0];
        String month = params.split(",")[1];
        Map sysMap = DatabaseSysConfigManager.getInstance().getProperties();
        String config = (String) sysMap.get("sys.examin.month.sum");
        Date[] dateArr = ExaminDateUtil.getDateArray(Integer.parseInt(year), Integer
                .parseInt(month), config);
        Date startDate = dateArr[0];
        Date endDate = dateArr[1];

        List<ExaminShiftBean> excelDataList = insertList;
        int hasTitle = 1 - commonParas.getInmatchModel().getImmNoTitle().intValue();
        Map<String, ObjAndRow> objInsertMap = new HashMap<String, ObjAndRow>();
        int rowNum = hasTitle + 1;
        for (ExaminShiftBean bean : excelDataList) {
            objInsertMap.put(bean.getEmp().getEmpDistinctNo(), new ObjAndRow(Integer
                    .valueOf(rowNum++), bean));
        }

        String[] empIdArr = new String[excelDataList.size()];
        List<Empshift> empshiftList = new ArrayList<Empshift>();
        List<Empshift> deleteList = new ArrayList<Empshift>();
        List<Empshift> toCheckList = new ArrayList<Empshift>();
        List<String> blankList = new ArrayList<String>();

        Iterator<Entry<String, ObjAndRow>> iter = objInsertMap.entrySet().iterator();
        String empDistinctNo = null;
        ObjAndRow rowObj = null;
        ExaminShiftBean shiftBean = null;
        Employee currEmp = null;
        while (iter.hasNext()) {
            Map.Entry<String, ObjAndRow> entry = iter.next();
            empDistinctNo = (String) entry.getKey();
            rowObj = (ObjAndRow) entry.getValue();
            rowNum = rowObj.rowNum.intValue();
            shiftBean = rowObj.bean;
            currEmp = (Employee) empInfoMap.get(empDistinctNo);

            if (currEmp == null) {
                commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                            new String[] { empDistinctNo });
            }

            if (currEmp.getEmpShiftType().intValue() == 0) {
                commonParas.addErrorMessage(this.msgEmpNoShift, Integer.valueOf(rowNum),
                                            new String[] { empDistinctNo });
            }

            empIdArr[(rowNum - hasTitle - 1)] = currEmp.getId();

            Class<? extends ExaminShiftBean> beanClass = shiftBean.getClass();
            Method getMethod = null;
            Date tempDate = (Date) startDate.clone();
            String shiftStr = null;
            while (tempDate.compareTo(endDate) <= 0) {
                String dayStr = DateUtil.formatDate(tempDate).substring(8, 10);
                dayStr = (Integer.parseInt(dayStr) < 10) ? dayStr.substring(1) : dayStr;
                try {
                    getMethod = beanClass.getMethod("get_" + dayStr, new Class[0]);
                    shiftStr = (String) getMethod.invoke(shiftBean, new Object[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (shiftStr == null) {
                    blankList.add(currEmp.getId() + "" + DateUtil.formatDate(tempDate));
                    tempDate = DateUtil.dateAdd(tempDate, 1);
                }

                if (shiftMap.get(shiftStr) == null) {
                    commonParas.addErrorMessage(this.msgShiftNotExist, Integer.valueOf(rowNum),
                                                new String[] { shiftStr });
                    tempDate = DateUtil.dateAdd(tempDate, 1);
                }

                Empshift empshift = new Empshift();
                empshift.setEmpshiftEmpNo((Employee) empInfoMap.get(empDistinctNo));
                empshift.setEmpshiftShiftNo((Attendshift) shiftMap.get(shiftStr));
                empshift.setEmpshiftDate(DateUtil.parseDateByFormat(DateUtil.formatDate(tempDate),
                                                                    "yyyy-MM-dd"));
                empshift.setEmpshiftCreateTime(new Date());
                empshift.setEmpshiftCreateBy(currentEmp.getId());
                empshift.setEmpshiftLastChangeTime(new Date());
                empshift.setEmpshiftLastChangeBy(currentEmp.getId());
                empshiftList.add(empshift);
                toCheckList.add(empshift);

                tempDate = DateUtil.dateAdd(tempDate, 1);
            }

        }

        if ("SUB".equals(commonParas.getAuthCondition())) {
            chechDeptAuth(currentEmp, empIdArr, commonParas);
        }

        String overrideConfig = (String) sysMap.get("sys.examin.shiftimport.override");

        IEmpshiftBo empshiftBo = (IEmpshiftBo) SpringBeanFactory.getBean("empshiftBO");
        Map<String, Empshift> empshiftMap = empshiftBo.getEmpshiftList(empIdArr, startDate, endDate);

        Iterator<Empshift> iter1 = empshiftList.iterator();
        Iterator<Empshift> checkIter = toCheckList.iterator();
        Empshift temp = null;
        Empshift existTemp = null;
        Empshift checkTemp = null;
        while ((iter1.hasNext()) && (checkIter.hasNext())) {
            temp = (Empshift) iter1.next();
            checkTemp = (Empshift) checkIter.next();
            existTemp = (Empshift) empshiftMap.get(temp.getEmpshiftEmpNo().getId()
                    + DateUtil.formatDateToS(temp.getEmpshiftDate(), "yyyy-MM-dd"));
            if (existTemp == null)
                continue;
            if ("0".equals(overrideConfig)) {
                iter1.remove();
                checkIter.remove();
            }

            temp.setId(existTemp.getId());
            temp.setEmpshiftCreateBy(existTemp.getEmpshiftCreateBy());
            temp.setEmpshiftCreateTime(existTemp.getEmpshiftCreateTime());
        }

        if ("1".equals(overrideConfig)) {
            for (String key : blankList) {
                temp = (Empshift) empshiftMap.get(key);
                if (temp == null)
                    continue;
                deleteList.add(temp);
                checkTemp = temp.clone();
                checkTemp.setEmpshiftShiftNo(null);
                toCheckList.add(checkTemp);
            }

        }

        List<String> errorList = empshiftBo.validateShift(toCheckList, empshiftMap);

        empshiftBo.checkShiftSchedule(errorList, empIdArr, empshiftList);
        for (String error : errorList) {
            commonParas.addErrorMessage(error, null, new String[0]);
        }

        if (!commonParas.getIoMessages().hasErrorMsg()) {
            empshiftBo.batchSaveEmpshift(empshiftList, deleteList);
            result[0] = excelDataList.size();
        }

        return result;
    }

    private Map<String, Employee> getEmpInfo() {
        IHibernateUtil dao = (IHibernateUtil) SpringBeanFactory.getBean("dao");
        String hql = "select empDistinctNo, id, empShiftType from Employee";
        List empList = dao.exeHqlList(hql);
        Object[] info = null;
        Map empMap = new HashMap();
        for (int i = 0; i < empList.size(); ++i) {
            info = (Object[]) (Object[]) empList.get(i);
            Employee emp = new Employee((String) info[1]);
            emp.setEmpShiftType((Integer) info[2]);
            empMap.put((String) info[0], emp);
        }

        return empMap;
    }

    private Map<String, Attendshift> getAllShift() {
        IHibernateUtil dao = (IHibernateUtil) SpringBeanFactory.getBean("dao");
        String hql = "from Attendshift";
        List shiftList = dao.exeHqlList(hql);
        Attendshift shift = null;
        Map shiftMap = new HashMap();
        for (int i = 0; i < shiftList.size(); ++i) {
            shift = (Attendshift) shiftList.get(i);
            shiftMap.put(shift.getAttsShortName(), shift);
        }

        return shiftMap;
    }

    private class ObjAndRow {
        public Integer rowNum;
        public ExaminShiftBean bean;

        ObjAndRow(Integer rowNum, ExaminShiftBean bean) {
            this.rowNum = rowNum;
            this.bean = bean;
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.extend.IExaminShift JD-Core Version: 0.5.4
 */