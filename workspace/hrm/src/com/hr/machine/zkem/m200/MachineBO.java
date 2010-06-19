package com.hr.machine.zkem.m200;

import java.util.ArrayList;

public class MachineBO {
    private ArrayList<MachineACO> arrayMachineACO;

    public MachineBO() {
        this.arrayMachineACO = new ArrayList<MachineACO>();
    }

    public MachineACO getMachineACO(int machineId) {
        for (int i = 0; i < this.arrayMachineACO.size(); ++i) {
            MachineACO machineACO = (MachineACO) this.arrayMachineACO.get(i);
            if (machineACO.getMachine().getMachineId() == machineId) {
                return machineACO;
            }

        }

        MachineACO machineACO = newMachineACO(machineId);
        this.arrayMachineACO.add(machineACO);
        return machineACO;
    }

    public MachineACO newMachineACO(int machineId) {
        Machine machine = new Machine();
        machine.setMachineId(machineId);
        machine.setIpAdd("192.168.0.201");
        machine.setPassword(123);

        MachineACO machineACO = new MachineACO(machine);
        machineACO.setConnectPassword();
        if (machineACO.connectNet()) {
            System.out.println("连接成功");
        } else {
            System.out.println("连接失败");
            System.out.println("错误代码为：" + machineACO.getLastError());
        }
        return machineACO;
    }

    public void closeMachineACO(int machineId) {
        for (int i = 0; i < this.arrayMachineACO.size(); ++i) {
            MachineACO machineACO = (MachineACO) this.arrayMachineACO.get(i);
            if (machineACO.getMachine().getMachineId() == machineId) {
                machineACO.disconnect();
                this.arrayMachineACO.remove(i);
            }
        }
    }

    public boolean uploadUser(ArrayList<User> arrayUser, int machineId) {
        MachineACO machineACO = getMachineACO(machineId);
        for (int i = 0; i < arrayUser.size(); ++i) {
            User user = (User) arrayUser.get(i);
            if (machineACO.setUserInfo(user)) {
                System.out.print("上传人员成功,id号为：" + user.getUserId());
            }
        }
        return true;
    }

    public boolean downloadUser(ArrayList<User> arrayUser, int machineId) {
        MachineACO machineACO = getMachineACO(machineId);
        for (int i = 0; i < arrayUser.size(); ++i) {
            User user = (User) arrayUser.get(i);
            if (machineACO.getUserInfo(user.getUserId()) instanceof User) {
                System.out.print("下载人员成功,id号为：" + user.getUserId() + " 卡号为："
                        + user.getCardNumber());
            }
        }
        return true;
    }

    public boolean downloadUser(int machineId) {
        MachineACO machineACO = getMachineACO(machineId);
        ArrayList<?> arrayUserDownload = machineACO.getAllUserInfo();
        if (arrayUserDownload.size() > 0) {
            System.out.print("下载人员成功");
            for (int i = 0; i < arrayUserDownload.size(); ++i) {
                User user = (User) arrayUserDownload.get(i);
                System.out.println("用户编号为：" + user.getUserId() + " 用户名称：" + user.getName());
            }
        }
        return true;
    }

    public boolean deleteUser(ArrayList<User> arrayUser, int machineId) {
        MachineACO machineACO = getMachineACO(machineId);
        for (int i = 0; i < arrayUser.size(); ++i) {
            User user = (User) arrayUser.get(i);
            if (machineACO.enableUser(user.getUserId(), false)) {
                System.out.print("删除人员成功,id号为：" + user.getUserId());
            }
        }
        return true;
    }

    public boolean restoreUser(int userId, int machineId) {
        MachineACO machineACO = getMachineACO(machineId);
        if (machineACO.enableUser(userId, true)) {
            System.out.print("恢复人员成功,id号为：" + userId);
            return true;
        }
        return true;
    }

    public boolean downRecord(int machineId) {
        MachineACO machineACO = getMachineACO(machineId);
        machineACO.readAllRecord();
        ArrayList<?> arrayList = machineACO.getAllRecord();

        for (int i = 0; i < arrayList.size(); ++i) {
            Record record = (Record) arrayList.get(i);
            System.out.println("用户编号：" + record.getUserId() + " 考勤时间：" + record.getYear()
                    + "-" + record.getMonth() + "-" + record.getDay() + " " + record.getHour()
                    + ":" + record.getMinute());
        }

        return true;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.machine.zkem.m200.MachineBO JD-Core Version: 0.5.4
 */