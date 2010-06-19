package com.hr.security.bo.impl;

import com.hr.compensation.domain.Empsalaryadj;
import com.hr.configuration.domain.Ecptype;
import com.hr.configuration.domain.Emptype;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Leavetype;
import com.hr.examin.domain.Overtimerequest;
import com.hr.examin.domain.Overtimetype;
import com.hr.profile.domain.Employee;
import com.hr.recruitment.domain.Recruitplan;
import com.hr.security.bo.DataCleanBo;
import com.hr.security.dao.DataCleanDao;
import com.hr.security.domain.base.BaseDataClean;
import com.hr.training.domain.Trcourse;
import com.hr.training.domain.Trcourseplan;
import com.hr.training.domain.Tremployeeplan;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanWrapperImpl;

public class DataCleanImpl implements DataCleanBo {
    private DataCleanDao dataCleanDao;

    public DataCleanImpl() {
        this.dataCleanDao = null;
    }

    public String deleteData(String deleteChoose) {
        if ((deleteChoose == null) || (deleteChoose.length() == 0))
            return "input";
        deleteChoose = deleteChoose.substring(0, deleteChoose.length() - 1);
        String[] split = deleteChoose.split("/");
        int index = -1;
        String temp = null;
        int splitLength = split.length;
        List empList = new ArrayList();
        List recList = new ArrayList();
        List leaList = new ArrayList();
        List oveList = new ArrayList();
        List traList = new ArrayList();
        for (int i = 0; i < splitLength; ++i) {
            temp = split[i];
            index = temp.indexOf("+");
            if (index == -1)
                continue;
            if (index + 1 >= temp.length()) {
                continue;
            }
            String id = temp.substring(0, index);
            String tag = temp.substring(index + 1);
            if ("Empsalaryadj".equals(tag)) {
                empList.add(id);
            } else if ("Recruitplan".equals(tag)) {
                recList.add(id);
            } else if ("Leaverequest".equals(tag)) {
                leaList.add(id);
            } else if ("Overtimerequest".equals(tag)) {
                oveList.add(id);
            } else if ("Tremployeeplan".equals(tag)) {
                traList.add(id);
            } else {
                System.out.println("find strange tag!++++++++++");
            }
        }
        if (empList.size() != 0) {
            deleteCleanObject(Employee.class, empList);
        }
        if (recList.size() != 0) {
            deleteCleanObject(Recruitplan.class, recList);
        }
        if (leaList.size() != 0) {
            deleteCleanObject(Leaverequest.class, leaList);
        }
        if (oveList.size() != 0) {
            deleteCleanObject(Overtimerequest.class, oveList);
        }
        if (traList.size() != 0) {
            deleteCleanObject(Tremployeeplan.class, traList);
        }
        return "success";
    }

    public List<BaseDataClean> searchData(String choose, String select, Date cbegain, Date cend,
            Date chbegain, Date chend) {
        List list = new ArrayList();

        List stateList = new ArrayList();
        stateList.add(Integer.valueOf(1));
        stateList.add(Integer.valueOf(21));
        stateList.add(Integer.valueOf(22));

        if ("Empsalaryadj".equals(choose)) {
            if ("0".equals(select)) {
                list.addAll(getRequestList(Empsalaryadj.class, "esaCreateBy", "esaLastChangeBy",
                                           "esaStatus", "id", "esaCreateTime",
                                           "esaLastChangeTimee", "esaEcptypeId", stateList,
                                           cbegain, cend, chbegain, chend));
            } else {
                List tempList = new ArrayList();
                tempList.add(Integer.valueOf(Integer.parseInt(select)));
                list.addAll(getRequestList(Empsalaryadj.class, "esaCreateBy", "esaLastChangeBy",
                                           "esaStatus", "id", "esaCreateTime",
                                           "esaLastChangeTimee", "esaEcptypeId", tempList, cbegain,
                                           cend, chbegain, chend));
            }

        } else if ("Recruitplan".equals(choose)) {
            if ("0".equals(select)) {
                list.addAll(getRequestList(Recruitplan.class, "recpCreateBy", "recpLastChangeBy",
                                           "recpStatus", "id", "recpCreateTime",
                                           "recpLastChangeTime", "recpType", stateList, cbegain,
                                           cend, chbegain, chend));
            } else {
                List tempList = new ArrayList();
                tempList.add(Integer.valueOf(Integer.parseInt(select)));
                list.addAll(getRequestList(Recruitplan.class, "recpCreateBy", "recpLastChangeBy",
                                           "recpStatus", "id", "recpCreateTime",
                                           "recpLastChangeTime", "recpType", tempList, cbegain,
                                           cend, chbegain, chend));
            }

        } else if ("LeaveOvertime".equals(choose)) {
            if ("0".equals(select)) {
                list.addAll(getRequestList(Leaverequest.class, "lrCreateBy", "lrLastChangeBy",
                                           "lrStatus", "lrId", "lrCreateTime", "lrLastChangeTime",
                                           "lrLtNo", stateList, cbegain, cend, chbegain, chend));

                list.addAll(getRequestList(Overtimerequest.class, "orCreateBy", "orLastChangeBy",
                                           "orStatus", "orId", "orCreateTime", "orLastChangeTime",
                                           "orOtNo", stateList, cbegain, cend, chbegain, chend));
            } else {
                List tempList = new ArrayList();
                tempList.add(Integer.valueOf(Integer.parseInt(select)));
                list.addAll(getRequestList(Leaverequest.class, "lrCreateBy", "lrLastChangeBy",
                                           "lrStatus", "lrId", "lrCreateTime", "lrLastChangeTime",
                                           "lrLtNo", tempList, cbegain, cend, chbegain, chend));

                list.addAll(getRequestList(Overtimerequest.class, "orCreateBy", "orLastChangeBy",
                                           "orStatus", "orId", "orCreateTime", "orLastChangeTime",
                                           "orOtNo", tempList, cbegain, cend, chbegain, chend));
            }

        } else if ("Tremployeeplan".equals(choose)) {
            if ("0".equals(select)) {
                list.addAll(getRequestList(Tremployeeplan.class, "trpCreateBy", "trpLastChangeBy",
                                           "trpStatus", "trpId", "trpCreateTime",
                                           "trpLastChangeTime", "trpTrcp", stateList, cbegain,
                                           cend, chbegain, chend));
            } else {
                List tempList = new ArrayList();
                tempList.add(Integer.valueOf(Integer.parseInt(select)));
                list.addAll(getRequestList(Tremployeeplan.class, "trpCreateBy", "trpLastChangeBy",
                                           "trpStatus", "trpId", "trpCreateTime",
                                           "trpLastChangeTime", "trpTrcp", tempList, cbegain, cend,
                                           chbegain, chend));
            }

        } else if ("All".equals(choose)) {
            if ("0".equals(select)) {
                list.addAll(getRequestList(Recruitplan.class, "recpCreateBy", "recpLastChangeBy",
                                           "recpStatus", "id", "recpCreateTime",
                                           "recpLastChangeTime", "recpType", stateList, cbegain,
                                           cend, chbegain, chend));

                list.addAll(getRequestList(Leaverequest.class, "lrCreateBy", "lrLastChangeBy",
                                           "lrStatus", "lrId", "lrCreateTime", "lrLastChangeTime",
                                           "lrLtNo", stateList, cbegain, cend, chbegain, chend));

                list.addAll(getRequestList(Overtimerequest.class, "orCreateBy", "orLastChangeBy",
                                           "orStatus", "orId", "orCreateTime", "orLastChangeTime",
                                           "orOtNo", stateList, cbegain, cend, chbegain, chend));

                list.addAll(getRequestList(Tremployeeplan.class, "trpCreateBy", "trpLastChangeBy",
                                           "trpStatus", "trpId", "trpCreateTime",
                                           "trpLastChangeTime", "trpTrcp", stateList, cbegain,
                                           cend, chbegain, chend));
            } else {
                List tempList = new ArrayList();
                tempList.add(Integer.valueOf(Integer.parseInt(select)));
                list.addAll(getRequestList(Recruitplan.class, "recpCreateBy", "recpLastChangeBy",
                                           "recpStatus", "id", "recpCreateTime",
                                           "recpLastChangeTime", "recpType", tempList, cbegain,
                                           cend, chbegain, chend));

                list.addAll(getRequestList(Leaverequest.class, "lrCreateBy", "lrLastChangeBy",
                                           "lrStatus", "lrId", "lrCreateTime", "lrLastChangeTime",
                                           "lrLtNo", tempList, cbegain, cend, chbegain, chend));

                list.addAll(getRequestList(Overtimerequest.class, "orCreateBy", "orLastChangeBy",
                                           "orStatus", "orId", "orCreateTime", "orLastChangeTime",
                                           "orOtNo", tempList, cbegain, cend, chbegain, chend));

                list.addAll(getRequestList(Tremployeeplan.class, "trpCreateBy", "trpLastChangeBy",
                                           "trpStatus", "trpId", "trpCreateTime",
                                           "trpLastChangeTime", "trpTrcp", tempList, cbegain, cend,
                                           chbegain, chend));
            }

        }

        return list;
    }

    private List<BaseDataClean> getRequestList(Class myClass, String creatBy, String changeBy,
            String searchState, String id, String creatTime, String changeTime, String descript,
            List<Integer> stateList, Date cbegain, Date cend, Date chbegain, Date chend) {
        List list = new ArrayList();
        BeanWrapperImpl wrap = null;
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(myClass);
        if (myClass == Recruitplan.class) {
            detachedCriteria.setFetchMode(Recruitplan.PROP_RECP_TYPE, FetchMode.JOIN);
        } else if (myClass == Empsalaryadj.class) {
            detachedCriteria.setFetchMode(Empsalaryadj.PROP_ESA_ECPTYPE_ID, FetchMode.JOIN);
            detachedCriteria.setFetchMode(Empsalaryadj.PROP_ESA_EMPNO, FetchMode.JOIN);
        } else if (myClass == Tremployeeplan.class) {
            detachedCriteria.setFetchMode(Tremployeeplan.PROP_TRP_TRCP + "."
                    + Trcourseplan.PROP_TRCP_COURSE_NO, FetchMode.JOIN);

            detachedCriteria.setFetchMode(Tremployeeplan.PROP_TRP_TRAINEE_NO, FetchMode.JOIN);
        } else if (myClass == Leaverequest.class) {
            detachedCriteria.setFetchMode(Leaverequest.PROP_LR_LT_NO, FetchMode.JOIN);
        } else if (myClass == Overtimerequest.class) {
            detachedCriteria.setFetchMode(Overtimerequest.PROP_OR_OT_NO, FetchMode.JOIN);
        }
        detachedCriteria.setFetchMode(creatBy, FetchMode.JOIN);
        detachedCriteria.setFetchMode(changeBy, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.in(searchState, stateList));
        if ((cbegain != null) && (cend != null)) {
            detachedCriteria.add(Restrictions.and(Restrictions.gt(creatTime, cbegain), Restrictions
                    .lt(creatTime, cend)));
        } else if (cbegain != null) {
            detachedCriteria.add(Restrictions.gt(creatTime, cbegain));
        } else if (cend != null) {
            detachedCriteria.add(Restrictions.lt(creatTime, cend));
        }

        if ((chbegain != null) && (chend != null)) {
            detachedCriteria.add(Restrictions.and(Restrictions.gt(changeTime, chbegain),
                                                  Restrictions.lt(changeTime, chend)));
        } else if (chbegain != null) {
            detachedCriteria.add(Restrictions.gt(changeTime, chbegain));
        } else if (chend != null) {
            detachedCriteria.add(Restrictions.lt(changeTime, chend));
        }
        detachedCriteria.addOrder(Order.asc(creatTime));

        Iterator tempIterator = this.dataCleanDao.findByCriteria(detachedCriteria).iterator();

        while (tempIterator.hasNext()) {
            BaseDataClean baseClean = new BaseDataClean();

            if (myClass == Empsalaryadj.class) {
                wrap = new BeanWrapperImpl((Empsalaryadj) tempIterator.next());
                baseClean.setTag("Empsalaryadj");
                baseClean.setModel("薪资");
            } else if (myClass == Recruitplan.class) {
                wrap = new BeanWrapperImpl((Recruitplan) tempIterator.next());
                baseClean.setTag("Recruitplan");
                baseClean.setModel("招聘");
            } else if (myClass == Leaverequest.class) {
                wrap = new BeanWrapperImpl((Leaverequest) tempIterator.next());
                baseClean.setTag("Leaverequest");
                baseClean.setModel("考勤");
            } else if (myClass == Overtimerequest.class) {
                wrap = new BeanWrapperImpl((Overtimerequest) tempIterator.next());
                baseClean.setTag("Overtimerequest");
                baseClean.setModel("考勤");
            } else if (myClass == Tremployeeplan.class) {
                wrap = new BeanWrapperImpl((Tremployeeplan) tempIterator.next());
                baseClean.setTag("Tremployeeplan");
                baseClean.setModel("培训");
            } else {
                System.out.println("wrong in class invok!");
                continue;
            }

            if (wrap == null) {
                System.out.println("wrong in wrap init");
            }

            baseClean.setId(wrap.getPropertyValue(id).toString());

            if ((wrap.getPropertyValue(descript) != null) && (myClass == Recruitplan.class)) {
                baseClean.setDescript(wrap.getPropertyValue(Recruitplan.PROP_RECP_NO) + " "
                        + ((Emptype) wrap.getPropertyValue(descript)).getEmptypeName());
            } else if ((wrap.getPropertyValue(descript) != null) && (myClass == Empsalaryadj.class)) {
                String date = wrap.getPropertyValue(Empsalaryadj.PROP_ESA_EFFDATE_CUR).toString();
                if ((date != null) && (date.length() > 11)) {
                    date = date.substring(0, date.length() - 10);
                }
                baseClean.setDescript(((Employee) wrap
                        .getPropertyValue(Empsalaryadj.PROP_ESA_EMPNO)).getEmpName()
                        + " "
                        + ((Ecptype) wrap.getPropertyValue(descript)).getEcptypeName()
                        + " "
                        + date);
            } else if ((wrap.getPropertyValue(descript) != null)
                    && (myClass == Tremployeeplan.class)) {
                Trcourseplan temp = (Trcourseplan) wrap.getPropertyValue(descript);
                String date = temp.getTrcpStartDate().toString();
                if ((date != null) && (date.length() > 11))
                    date = date.substring(date.length() - 10);
                baseClean.setDescript(((Employee) wrap
                        .getPropertyValue(Tremployeeplan.PROP_TRP_TRAINEE_NO)).getEmpName()
                        + " " + temp.getTrcpCourseNo().getTrcName() + " " + date);
            } else if ((wrap.getPropertyValue(descript) != null) && (myClass == Leaverequest.class)) {
                baseClean.setDescript(wrap.getPropertyValue(Leaverequest.PROP_LR_NO) + " "
                        + ((Leavetype) wrap.getPropertyValue(descript)).getLtName() + " "
                        + wrap.getPropertyValue(Leaverequest.PROP_LR_REASON));
            } else if ((wrap.getPropertyValue(descript) != null)
                    && (myClass == Overtimerequest.class)) {
                baseClean.setDescript(wrap.getPropertyValue(Overtimerequest.PROP_OR_NO) + " "
                        + ((Overtimetype) wrap.getPropertyValue(descript)).getOtName() + " "
                        + wrap.getPropertyValue(Overtimerequest.PROP_OR_REASON));
            }

            Employee employeeCreate = (Employee) wrap.getPropertyValue(changeBy);
            if (employeeCreate != null) {
                baseClean.setCreatBy(employeeCreate.getEmpDistinctNo());
            }

            Employee employeeChange = (Employee) wrap.getPropertyValue(creatBy);
            if (employeeChange != null) {
                baseClean.setChangeBy(employeeChange.getEmpDistinctNo());
            }

            baseClean.setChangeTime(getFormatDate((Date) wrap.getPropertyValue(changeTime)));
            baseClean.setCreatTime(getFormatDate((Date) wrap.getPropertyValue(creatTime)));
            if ("1".equals(wrap.getPropertyValue(searchState).toString())) {
                baseClean.setState("草稿");
            } else if ("21".equals(wrap.getPropertyValue(searchState).toString())) {
                baseClean.setState("已拒组1�7");
            } else if ("22".equals(wrap.getPropertyValue(searchState).toString())) {
                baseClean.setState("已作庄1�7");
            }
            list.add(baseClean);
        }
        tempIterator = null;
        return list;
    }

    private void deleteCleanObject(Class myClass, List list) {
        Iterator itr = list.iterator();
        while (itr.hasNext()) {
            this.dataCleanDao.deleteObject(myClass, itr.next());
        }
        itr = null;
    }

    private String getFormatDate(Date date) {
        if (date == null)
            return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateString = simpleDateFormat.format(date);
        return currentDateString;
    }

    public DataCleanDao getDataCleanDao() {
        return this.dataCleanDao;
    }

    public void setDataCleanDao(DataCleanDao dataCleanDao) {
        this.dataCleanDao = dataCleanDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.bo.impl.DataCleanImpl JD-Core Version: 0.5.4
 */