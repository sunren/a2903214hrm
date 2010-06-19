package com.hr.machine.zkem.m200;

public class Machine {
    public String ipAdd;
    public int port;
    public int password;
    public boolean flag;
    private int machineId;

    public Machine() {
        this.port = 4370;

        this.password = 0;

        this.flag = false;

        this.machineId = 1;
    }

    public String getIpAdd() {
        return this.ipAdd;
    }

    public void setIpAdd(String ipAdd) {
        this.ipAdd = ipAdd;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPassword() {
        return this.password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public boolean isFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getMachineId() {
        return this.machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.machine.zkem.m200.Machine JD-Core Version: 0.5.4
 */