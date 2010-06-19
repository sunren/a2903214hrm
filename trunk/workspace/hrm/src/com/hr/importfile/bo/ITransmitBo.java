package com.hr.importfile.bo;

import com.hr.io.domain.Iodef;
import com.hr.profile.domain.Employee;
import java.io.File;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public abstract interface ITransmitBo {
    public abstract String[][] getContent(int paramInt1, File paramFile, int paramInt2);

    public abstract Hashtable<String, List<ErrorMessage>> checkTransmit(Iodef paramIodef,
            String[][] paramArrayOfString, File paramFile, int[] paramArrayOfInt, List paramList);

    public abstract Hashtable<String, List<ErrorMessage>> insertTransmit(Iodef paramIodef,
            String[][] paramArrayOfString, File paramFile, int[] paramArrayOfInt, List paramList,
            Employee paramEmployee, Map paramMap);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.importfile.bo.ITransmitBo JD-Core Version: 0.5.4
 */