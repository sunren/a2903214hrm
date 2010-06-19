package com.hr.importfile.base;

import com.hr.importfile.bo.ErrorMessage;
import com.hr.io.domain.Iodef;
import java.util.List;

public abstract interface ICheckLogic {
    public abstract List<ErrorMessage> checkLogicValid(String[][] paramArrayOfString,
            List paramList, int[] paramArrayOfInt, Iodef paramIodef);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.importfile.base.ICheckLogic JD-Core Version: 0.5.4
 */