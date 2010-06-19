package com.hr.machine;

import com.hr.machine.zkem.m200.Record;
import com.hr.machine.zkem.m200.User;
import java.util.ArrayList;
import java.util.Date;

public abstract interface MachineACOI {
    public abstract boolean connectNet();

    public abstract boolean setConnectPassword();

    public abstract int getLastError();

    public abstract boolean readAllRecord();

    public abstract ArrayList<Record> getAllRecord();

    public abstract ArrayList<Record> getRecord(int paramInt);

    public abstract ArrayList<Record> getAllRecord(int[] paramArrayOfInt);

    public abstract boolean readAllRecord(Date paramDate1, Date paramDate2, boolean paramBoolean1,
            boolean paramBoolean2);

    public abstract ArrayList<Record> getAllRecord(Date paramDate1, Date paramDate2,
            boolean paramBoolean1, boolean paramBoolean2);

    public abstract ArrayList<Record> getRecord(int paramInt, Date paramDate1, Date paramDate2,
            boolean paramBoolean1, boolean paramBoolean2);

    public abstract ArrayList<Record> getAllRecord(int[] paramArrayOfInt, Date paramDate1,
            Date paramDate2, boolean paramBoolean1, boolean paramBoolean2);

    public abstract boolean readAllUserInfo();

    public abstract ArrayList<User> getAllUserInfo();

    public abstract User getUserInfo(int paramInt);

    public abstract ArrayList<User> getUserInfo(int[] paramArrayOfInt);

    public abstract boolean setUserInfo(User paramUser);

    public abstract boolean setUserInfo(ArrayList<User> paramArrayList);

    public abstract boolean enableUser(int paramInt, boolean paramBoolean);

    public abstract boolean enableUser(int[] paramArrayOfInt, boolean paramBoolean);

    public abstract boolean enableAllUser(boolean paramBoolean);

    public abstract boolean clearRecord();

    public abstract void disconnect();
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.machine.MachineACOI JD-Core Version: 0.5.4
 */