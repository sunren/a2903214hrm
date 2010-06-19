package com.hr.examin.bo.interfaces;

import com.hr.examin.domain.Attdoriginaldata;
import com.hr.util.Pager;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface IAttdoriginaldataBO {
    public abstract boolean addAttdoriginaldata(Attdoriginaldata paramAttdoriginaldata);

    public abstract boolean saveAttdoriginaldata(Attdoriginaldata paramAttdoriginaldata);

    public abstract boolean deleteAttdoriginaldata(String paramString);

    public abstract Attdoriginaldata getAttdoriginaldata(String paramString);

    public abstract boolean insertDataList(List<Attdoriginaldata> paramList, Date paramDate1,
            Date paramDate2);

    public abstract List<Attdoriginaldata> getOriginalDataList(
            DetachedCriteria paramDetachedCriteria, Pager paramPager, String paramString1,
            String paramString2, Attdoriginaldata paramAttdoriginaldata, Date paramDate1,
            Date paramDate2);

    public abstract boolean deleteDataInDateDomain(Date paramDate1, Date paramDate2);

    public abstract boolean saveOriginalData(String[] paramArrayOfString, Date paramDate1,
            Date paramDate2, String paramString1, String paramString2, String paramString3);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.interfaces.IAttdoriginaldataBO JD-Core Version: 0.5.4
 */