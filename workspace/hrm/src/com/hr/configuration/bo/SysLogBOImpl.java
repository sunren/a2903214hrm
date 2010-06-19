package com.hr.configuration.bo;

import com.hr.base.ComonBeans;
import com.hr.base.wf.Workflow;
import com.hr.configuration.dao.ISysLogDAO;
import com.hr.configuration.domain.Syslog;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Overtimerequest;
import com.hr.profile.domain.Employee;
import com.hr.util.MyTools;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class SysLogBOImpl implements ISysLogBO {
    private ISysLogDAO logDAO;

    public SysLogBOImpl() {
        this.logDAO = null;
    }

    public void deleteLogs(String tableName, String key) {
        List results = getLogs(tableName, key);
        if ((results != null) && (results.size() > 0))
            this.logDAO.deleteCollection(results);
    }

    public List<Syslog> getLogs(String tableName, String key) {
        DetachedCriteria dc = DetachedCriteria.forClass(Syslog.class);
        dc.setFetchMode("slChangeEmpno", FetchMode.JOIN);
        dc.setFetchMode("slChangeEmpno.empBuNo", FetchMode.JOIN);
        dc.setFetchMode("slChangeEmpno.empLocationNo", FetchMode.JOIN);
        dc.setFetchMode("slChangeEmpno.empDeptNo", FetchMode.JOIN);
        dc.addOrder(Order.desc("slChangeTime"));
        dc.add(Restrictions.eq("slTableName", tableName.trim()));
        dc.add(Restrictions.eq("slRecordId", key.trim()));

        List<Syslog> logs = this.logDAO.findByCriteria(dc);

        for (Syslog log : logs) {
            if (log.getSlType().intValue() == 1) {
                log.setSlComments((String) log.getDecry("SlComments", log.getSlRecordId(),
                                                        MyTools.CHINESE));
            }
        }
        return logs;
    }

    public List insertLog(Syslog log, String empNo) {
        log.setSlChangeTime(new Date());
        log.setSlChangeEmpno(new Employee(empNo));
        this.logDAO.saveObject(log);

        return null;
    }

    public List insertLog(Syslog log) {
        log.setSlChangeTime(new Date());
        this.logDAO.saveObject(log);
        return null;
    }

    public void addToSyslog(String tableName, String changeEmpno, String empNo, String recordId,
            int type, String action, String comments) {
        Syslog sl = new Syslog();
        sl.setSlTableName(tableName);
        if (changeEmpno == null)
            sl.setSlChangeEmpno(null);
        else {
            sl.setSlChangeEmpno(new Employee(changeEmpno));
        }
        sl.setSlEmpno(empNo);
        sl.setSlRecordId(recordId);
        sl.setSlType(Integer.valueOf(type));
        sl.setSlAction(action);
        sl.setSlComments(comments);

        if (sl.getSlType().intValue() == 1) {
            sl.setEncry("SlComments", sl.getSlComments(), sl.getSlRecordId(), MyTools.CHINESE);
        }
        insertLog(sl);
    }

    public void addLog(List<Object> objs) {
        List logList = new ArrayList(objs.size());
        Workflow wf = null;
        for (Iterator i$ = objs.iterator(); i$.hasNext();) {
            Object obj = i$.next();

            Syslog log = new Syslog();
            log.setSlType(Integer.valueOf(0));

            if (obj instanceof Leaverequest) {
                wf = ((Leaverequest) obj).getWorkflow();
                log.setSlRecordId(new String(((Leaverequest) obj).getLrId()));
            } else if (obj instanceof Overtimerequest) {
                wf = ((Overtimerequest) obj).getWorkflow();
                log.setSlRecordId(new String(((Overtimerequest) obj).getOrId()));
            }
            log.setSlTableName(wf.getFlowType());
            log.setSlComments(wf.getComment());
            log.setSlChangeTime(wf.getOpTime());
            log.setSlChangeEmpno(new Employee(new String(wf.getOperator().getId())));
            log.setSlEmpno(new String(wf.getApplier().getId()));
            log.setSlAction(ComonBeans.getParameterValue("lrStatus", new String[] { wf.getStatus()
                    .toString() }));

            logList.add(log);
        }

        this.logDAO.saveOrupdate(logList);
    }

    public ISysLogDAO getLogDAO() {
        return this.logDAO;
    }

    public void setLogDAO(ISysLogDAO logDAO) {
        this.logDAO = logDAO;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.SysLogBOImpl JD-Core Version: 0.5.4
 */