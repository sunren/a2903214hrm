package com.hr.util;

public class Message {
    private Integer msgType;
    private String msgContent;

    public Message() {
    }

    public Message(Integer msgType, String msgContent) {
        this.msgContent = msgContent;
        this.msgType = msgType;
    }

    public Integer getMsgType() {
        return this.msgType;
    }

    public String getMsgContent() {
        return this.msgContent;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name: com.hr.util.Message
 * JD-Core Version: 0.5.4
 */