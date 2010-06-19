package com.hr.util;

public class MessageForField {
    private String[] fields;
    private String messageModel;
    private Integer msgType;

    public MessageForField(String messageModel, Integer msgType, String[] fields) {
        this.messageModel = messageModel;
        this.msgType = msgType;
        this.fields = fields;
    }

    public String[] getFields() {
        return this.fields;
    }

    public String getMessageModel() {
        return this.messageModel;
    }

    public Integer getMsgType() {
        return this.msgType;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.MessageForField JD-Core Version: 0.5.4
 */