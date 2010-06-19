package com.hr.examin.bo.interfaces;

import com.hr.configuration.domain.AttdMachine;
import com.hr.examin.domain.AttdSyncRecord;
import com.hr.profile.domain.Employee;
import com.hr.util.Pager;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface IAttdSyncRecordBO {
    public abstract boolean generateAttdSyncRecord(List<Employee> paramList,
            AttdMachine paramAttdMachine);

    public abstract boolean deleteAttdSyncRecord(List<Employee> paramList,
            AttdMachine paramAttdMachine);

    public abstract boolean addAttdSyncRecord(AttdSyncRecord paramAttdSyncRecord);

    public abstract boolean saveAttdSyncRecord(AttdSyncRecord paramAttdSyncRecord);

    public abstract boolean deleteAttdSyncRecord(String paramString);

    public abstract AttdSyncRecord getAttdSyncRecord(String paramString);

    public abstract AttdSyncRecord getAttdSyncRecord(String paramString1, String paramString2);

    public abstract boolean insertDataList(List<AttdSyncRecord> paramList);

    public abstract List<AttdSyncRecord> getAttdSyncRecordList(
            DetachedCriteria paramDetachedCriteria, Pager paramPager);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.interfaces.IAttdSyncRecordBO JD-Core Version: 0.5.4
 */