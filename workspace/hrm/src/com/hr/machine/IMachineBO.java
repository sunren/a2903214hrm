package com.hr.machine;

import com.hr.machine.zkem.m200.Record;
import com.hr.machine.zkem.m200.User;
import java.util.Date;
import java.util.List;

public abstract interface IMachineBO {
    public abstract boolean connectNet();

    public abstract List<User> getAllUserInfo();

    public abstract User getAllUserInfo(int paramInt);

    public abstract List<User> getAllUserInfo(int[] paramArrayOfInt);

    public abstract List<Record> getAllRecord(Date paramDate1, Date paramDate2,
            boolean paramBoolean1, boolean paramBoolean2);

    public abstract List<Record> getRecord(int paramInt, Date paramDate1, Date paramDate2,
            boolean paramBoolean1, boolean paramBoolean2);

    public abstract List<Record> getAllRecord(int[] paramArrayOfInt, Date paramDate1,
            Date paramDate2, boolean paramBoolean1, boolean paramBoolean2);

    public abstract boolean setUserInfo(User paramUser);

    public abstract boolean setUserInfo(List<User> paramList);

    public abstract boolean deleteAllUser();

    public abstract boolean deleteAllUser(int[] paramArrayOfInt);

    public abstract boolean deleteUser(User paramUser);

    public abstract boolean clearRecord();

    public abstract void disconnect();
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.machine.IMachineBO JD-Core Version: 0.5.4
 */