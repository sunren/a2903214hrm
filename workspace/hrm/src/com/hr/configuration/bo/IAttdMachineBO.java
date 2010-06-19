package com.hr.configuration.bo;

import com.hr.configuration.domain.AttdMachine;
import java.util.List;

public abstract interface IAttdMachineBO {
    public abstract List FindAllAttdMachine();

    public abstract List<AttdMachine> FindEnabledAttdMachine();

    public abstract boolean addAttdMachine(AttdMachine paramAttdMachine);

    public abstract String delAttdMachine(Class<AttdMachine> paramClass, String paramString);

    public abstract String updateAttdMachine(AttdMachine paramAttdMachine);

    public abstract AttdMachine loadAttdMachine(String paramString);

    public abstract void saveAttdMachineSortIdByAttdMachine(String[] paramArrayOfString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.bo.IAttdMachineBO JD-Core Version: 0.5.4
 */