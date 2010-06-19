package com.hr.examin.bo.interfaces;

import com.hr.examin.domain.Empshift;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract interface IEmpshiftBo {
    public abstract boolean addEmpshift(Empshift paramEmpshift);

    public abstract boolean updateEmpshift(Empshift paramEmpshift);

    public abstract boolean deleteEmpshift(Empshift paramEmpshift);

    public abstract Empshift getEmpshift(String paramString);

    public abstract boolean batchSaveEmpshift(List<Empshift> paramList1, List<Empshift> paramList2);

    public abstract List<Empshift> getEmpShiftList(Date paramDate1, Date paramDate2,
            String paramString);

    public abstract HashMap<String, Empshift> getEmpshiftList(String[] paramArrayOfString,
            Date[] paramArrayOfDate);

    public abstract HashMap<String, Empshift> getEmpshiftList(String[] paramArrayOfString,
            Date paramDate1, Date paramDate2);

    public abstract Integer isInWorkShift(Date paramDate, List<Empshift> paramList);

    public abstract int computeTotalLeaveMinutes(Date paramDate1, Date paramDate2,
            List<Empshift> paramList);

    public abstract List<String> validateShift(List<Empshift> paramList,
            Map<String, Empshift> paramMap);

    public abstract void checkShiftSchedule(List<String> paramList, String[] paramArrayOfString,
            List<Empshift> paramList1);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.interfaces.IEmpshiftBo JD-Core Version: 0.5.4
 */