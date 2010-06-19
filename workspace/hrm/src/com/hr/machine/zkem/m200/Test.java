package com.hr.machine.zkem.m200;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        MachineBO machineBO = new MachineBO();
        int machineId = 1;
        int userId = 1;
        ArrayList<User> arrayUser = new ArrayList<User>();

        User user = new User();
        user.setUserId(userId);
        arrayUser.add(user);

        machineBO.downRecord(machineId);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.machine.zkem.m200.Test JD-Core Version: 0.5.4
 */