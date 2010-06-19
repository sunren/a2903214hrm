package com.hr.machine.zkem.m200;

import com.hr.machine.IMachineBO;
import com.hr.machine.MachineACOI;
import com.hr.util.DateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineBOImpl extends MachineCOM implements IMachineBO {
    private MachineACOI machineACO;

    public MachineBOImpl(Machine machine) {
        this.machineACO = new MachineACO(machine);
    }

    public boolean connectNet() {
        this.machineACO.setConnectPassword();

        return this.machineACO.connectNet();
    }

    public boolean deleteAllUser() {
        return this.machineACO.enableAllUser(false);
    }

    public boolean deleteAllUser(int[] userIds) {
        if (userIds == null) {
            return true;
        }

        return this.machineACO.enableUser(userIds, false);
    }

    public boolean deleteUser(User user) {
        if (this.machineACO.enableUser(user.getUserId(), false)) {
            user.setCardNumber(null);
            this.machineACO.setUserInfo(user);
            return true;
        }
        return false;
    }

    public void disconnect() {
        this.machineACO.disconnect();
    }

    public List<Record> getAllRecord(Date dateFrom, Date dateTo, boolean readAll, boolean onlyRead) {
        List<Record> recordList = this.machineACO.getAllRecord();
        List<Record> list = new ArrayList<Record>(recordList);
        for (Record record : recordList) {
            Date date = getRecordDate(record);
            if ((date.compareTo(dateFrom) == -1) || (date.compareTo(dateTo) == 1)) {
                list.remove(record);
            }
        }
        return list;
    }

    public List<Record> getAllRecord(int[] userIds, Date dateFrom, Date dateTo, boolean readAll,
            boolean onlyRead) {
        if (userIds == null) {
            return getAllRecord(dateFrom, dateTo, readAll, onlyRead);
        }
        Map<Long, Long> map = new HashMap<Long, Long>();
        int[] arr$ = userIds;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; ++i$) {
            Integer userId = Integer.valueOf(arr$[i$]);
            map.put(Long.valueOf(Long.parseLong(userId.toString())), Long.valueOf(Long
                    .parseLong(userId.toString())));
        }

        List<Record> recordList = this.machineACO.getAllRecord();
        List<Record> list = new ArrayList<Record>(recordList);

        for (Record record : recordList) {
            if (map.get(Long.valueOf(record.getUserId())) == null) {
                list.remove(record);
            }
        }
        return list;
    }

    public List<User> getAllUserInfo() {
        return this.machineACO.getAllUserInfo();
    }

    public User getAllUserInfo(int userId) {
        return this.machineACO.getUserInfo(userId);
    }

    public List<User> getAllUserInfo(int[] userIds) {
        return this.machineACO.getUserInfo(userIds);
    }

    public List<Record> getRecord(int userId, Date dateFrom, Date dateTo, boolean readAll,
            boolean onlyRead) {
        List<Record> recordList = this.machineACO.getAllRecord();
        List<Record> list = new ArrayList<Record>(recordList);

        for (Record record : recordList) {
            Date date = getRecordDate(record);
            if ((record.getUserId() != userId) || (date.compareTo(dateFrom) == -1)
                    || (date.compareTo(dateTo) == 1)) {
                list.remove(record);
            }
        }
        return list;
    }

    public boolean setUserInfo(User user) {
        return this.machineACO.setUserInfo(user);
    }

    public boolean setUserInfo(List<User> userList) {
        return this.machineACO.setUserInfo((ArrayList<User>) userList);
    }

    public boolean clearRecord() {
        return this.machineACO.clearRecord();
    }

    private Date getRecordDate(Record record) {
        String timeStr = record.getYear() + "-" + record.getMonth() + "-" + record.getDay() + " "
                + record.getHour() + ":" + record.getMinute() + ":" + "00";
        Date date = DateUtil.parseDateByFormat(timeStr, "yyyy-MM-dd hh:mm:ss");

        return date;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.machine.zkem.m200.MachineBOImpl JD-Core Version: 0.5.4
 */