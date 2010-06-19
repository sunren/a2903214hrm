package com.hr.security.bo;

import com.hr.security.domain.base.BaseDataClean;
import java.util.Date;
import java.util.List;

public abstract interface DataCleanBo {
    public abstract List<BaseDataClean> searchData(String paramString1, String paramString2,
            Date paramDate1, Date paramDate2, Date paramDate3, Date paramDate4);

    public abstract String deleteData(String paramString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.bo.DataCleanBo JD-Core Version: 0.5.4
 */