package com.hr.machine.zkem.m200;

public class Message {
    private long machineId;
    private long id;
    private long tag;
    private long validMinutes;
    private String startTime;
    private String content;

    public Message() {
        this.machineId = 1L;

        this.id = 0L;

        this.tag = 253L;

        this.validMinutes = 1L;
    }

    public long getMachineId() {
        return this.machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTag() {
        return this.tag;
    }

    public void setTag(long tag) {
        this.tag = tag;
    }

    public long getValidMinutes() {
        return this.validMinutes;
    }

    public void setValidMinutes(long validMinutes) {
        this.validMinutes = validMinutes;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.machine.zkem.m200.Message JD-Core Version: 0.5.4
 */