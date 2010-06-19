package com.hr.machine.zkem.m200;

import com.hr.machine.MachineACOI;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import java.util.ArrayList;
import java.util.Date;

public class MachineACO extends MachineCOM implements MachineACOI {
    private Machine machine;

    public MachineACO(Machine machineVO) {
        setMachine(machineVO);
        initCOM();
    }

    public Machine getMachine() {
        return this.machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public boolean connectNet() {
        String ipAdd = getMachine().getIpAdd();
        int port = getMachine().getPort();
        Variant rs = Dispatch.call(getComObj(), "connectNet", ipAdd, Integer.valueOf(port));
        return rs.getBoolean();
    }

    public boolean enableUser(int userId, boolean flag) {
        Object[] args = { Integer.valueOf(getMachine().getMachineId()), Integer.valueOf(userId),
                Boolean.valueOf(flag) };

        Variant rs = Dispatch.callN(getComObj(), "enableUser", args);
        return rs.getBoolean();
    }

    public ArrayList<User> getAllUserInfo() {
        ArrayList<User> arrayList = new ArrayList<User>();
        int machineId = getMachine().getMachineId();

        Variant rs = Dispatch.call(getComObj(), "getAllUserInfo", Integer.valueOf(machineId));
        if (rs.getString().trim().length() > 0) {
            String[] userInfo = rs.getString().split("\\|");
            for (int i = 0; i < userInfo.length; ++i) {
                String[] record = userInfo[i].split(",");

                User user = new User();
                user.setMachineId(machineId);
                user.setUserId(Integer.parseInt(record[0]));
                user.setCardNumber(record[1]);
                user.setName(record[2]);
                user.setPassword(record[3]);
                user.setPrivilege(Integer.valueOf(record[4]).intValue());
                user.setEnabled(Boolean.valueOf(record[5]).booleanValue());

                arrayList.add(user);
            }
        }
        return arrayList;
    }

    public int getLastError() {
        Variant rs = Dispatch.call(getComObj(), "getLastError");
        return rs.getInt();
    }

    public ArrayList<Record> getAllRecord() {
        ArrayList<Record> arrayList = new ArrayList<Record>();
        int machineId = getMachine().getMachineId();

        Variant rs = Dispatch.call(getComObj(), "getAllRecord", Integer.valueOf(machineId));
        if (rs.getString().trim().length() > 0) {
            String[] recordInfo = rs.getString().split("\\|");
            for (int i = 0; i < recordInfo.length; ++i) {
                String[] info = recordInfo[i].split(",");

                Record record = new Record();
                record.setMachineId(machineId);
                record.setUserId(Integer.valueOf(info[0]).intValue());
                record.setVerifyModel(Integer.valueOf(info[1]).intValue());
                record.setInOutModel(Integer.valueOf(info[2]).intValue());
                record.setYear(Integer.valueOf(info[3]).intValue());
                record.setMonth(Integer.valueOf(info[4]).intValue());
                record.setDay(Integer.valueOf(info[5]).intValue());
                record.setHour(Integer.valueOf(info[6]).intValue());
                record.setMinute(Integer.valueOf(info[7]).intValue());
                record.setSecond(Integer.valueOf(info[8]).intValue());
                arrayList.add(record);
            }
        }
        return arrayList;
    }

    public User getUserInfo(int userId) {
        int machineId = getMachine().getMachineId();
        Object[] args = { Integer.valueOf(machineId), Integer.valueOf(userId) };

        Variant rs = Dispatch.callN(getComObj(), "getUserInfo", args);
        if (rs.getString().trim().length() > 0) {
            String[] info = rs.getString().split(",");

            User user = new User();
            user.setMachineId(machineId);
            user.setUserId(Integer.parseInt(info[0]));
            user.setCardNumber(info[1]);
            user.setName(info[2]);
            user.setPassword(info[3]);
            user.setPrivilege(Integer.valueOf(info[4]).intValue());
            user.setEnabled(Boolean.valueOf(info[5]).booleanValue());

            return user;
        }
        return null;
    }

    public boolean readAllRecord() {
        int machineId = getMachine().getMachineId();
        Variant rs = Dispatch.call(getComObj(), "readAllRecord", Integer.valueOf(machineId));
        return rs.getBoolean();
    }

    public boolean readAllUserInfo() {
        Variant rs = Dispatch.call(getComObj(), "readAllUserInfo");
        return rs.getBoolean();
    }

    public boolean setConnectPassword() {
        int password = getMachine().getPassword();
        if (password != 0) {
            Variant rs = Dispatch
                    .call(getComObj(), "setConnectPassword", Integer.valueOf(password));
            return rs.getBoolean();
        }
        return true;
    }

    public boolean setUserInfo(User user) {
        Object[] args = { Integer.valueOf(getMachine().getMachineId()),
                Integer.valueOf(user.getUserId()), user.getCardNumber(), user.getName(),
                user.getPassword(), Integer.valueOf(user.getPrivilege()),
                Boolean.valueOf(user.getEnabled()) };

        Variant rs = Dispatch.callN(getComObj(), "setUserInfo", args);
        return rs.getBoolean();
    }

    public boolean clearRecord() {
        Variant rs = Dispatch.call(getComObj(), "clearRecord", Integer.valueOf(getMachine()
                .getMachineId()));
        return rs.getBoolean();
    }

    public void disconnect() {
        Dispatch.call(getComObj(), "disconnect");
    }

    public ArrayList<User> getUserInfo(int[] userIds) {
        return null;
    }

    public boolean enableAllUser(boolean flag) {
        return false;
    }

    public boolean enableUser(int[] userIds, boolean flag) {
        int[] arr$ = userIds;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; ++i$) {
            Integer userId = Integer.valueOf(arr$[i$]);
            enableUser(userId.intValue(), flag);
        }

        return true;
    }

    public ArrayList<Record> getAllRecord(int[] userIds) {
        return null;
    }

    public ArrayList<Record> getRecord(int userId) {
        return null;
    }

    public boolean setUserInfo(ArrayList<User> userList) {
        return false;
    }

    public ArrayList<Record> getAllRecord(Date dateFrom, Date dateTo, boolean readAll,
            boolean onlyRead) {
        return null;
    }

    public ArrayList<Record> getAllRecord(int[] userIds, Date dateFrom, Date dateTo,
            boolean readAll, boolean onlyRead) {
        return null;
    }

    public ArrayList<Record> getRecord(int userId, Date dateFrom, Date dateTo, boolean readAll,
            boolean onlyRead) {
        return null;
    }

    public boolean readAllRecord(Date dateFrom, Date dateTo, boolean readAll, boolean onlyRead) {
        return false;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.machine.zkem.m200.MachineACO JD-Core Version: 0.5.4
 */