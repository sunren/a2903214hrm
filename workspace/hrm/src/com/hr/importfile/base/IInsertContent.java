package com.hr.importfile.base;

import com.hr.importfile.bo.ErrorMessage;
import com.hr.io.domain.Iodef;
import com.hr.profile.domain.Employee;
import java.util.List;
import java.util.Map;

public abstract interface IInsertContent {
    public abstract List<ErrorMessage> insertTransmit(String[][] paramArrayOfString,
            List paramList, int[] paramArrayOfInt, Iodef paramIodef, Employee paramEmployee,
            Map paramMap);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.importfile.base.IInsertContent JD-Core Version: 0.5.4
 */