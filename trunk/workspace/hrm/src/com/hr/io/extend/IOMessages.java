package com.hr.io.extend;

import com.hr.util.Message;
import com.hr.util.comparator.ObjectFieldComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IOMessages {
    private List<IOMessageSingle> msgList = new ArrayList();

    private String msgFormat = "";
    public static final String FORMAT_EXCEL = "excel";
    public static final int ROW = 0;
    public static final int COL = 1;
    public static final String[] ROWCOLNAME = { "rowNum", "colNum" };

    public IOMessages() {
    }

    public IOMessages(String msgFormat) {
        setMsgFormat(msgFormat);
    }

    public String getSingleMessage(IOMessageSingle ioMessageSingle) {
        String result = "";
        if (this.msgFormat.equalsIgnoreCase("excel")) {
            if (ioMessageSingle.getRowNum() != null) {
                result = result + "第" + ioMessageSingle.getRowNum() + "行";
            }
            if (ioMessageSingle.getColNum() != null) {
                if (result.length() > 0) {
                    result = result + "，";
                }
                result = result + "第" + numToExcelColNum(ioMessageSingle.getColNum().intValue())
                        + "列";
            }
        }

        result = result + " ";
        result = result + ioMessageSingle.getMessage();
        return result;
    }

    public void sortMessages(int[] sortIndex) throws Exception {
        if (sortIndex.length == 0) {
            return;
        }
        int sortNum = (sortIndex.length > 2) ? 2 : sortIndex.length;
        String[] fieldNameArr = new String[sortNum];
        for (int i = 0; i < sortNum; ++i) {
            fieldNameArr[i] = ROWCOLNAME[sortIndex[i]];
        }
        ObjectFieldComparator objComparator = new ObjectFieldComparator(IOMessageSingle.class,
                fieldNameArr, new int[0]);
        Collections.sort(this.msgList, objComparator);
    }

    public List<List<Message>> getShowMessageByTopDetail() {
        List result = new ArrayList();
        List topList = new ArrayList();
        List detailList = new ArrayList();
        for (IOMessageSingle ioMessageSingle : this.msgList) {
            if ((ioMessageSingle.getRowNum() == null) && (ioMessageSingle.getColNum() == null))
                topList.add(new Message(ioMessageSingle.getErrorType(),
                        getSingleMessage(ioMessageSingle)));
            else {
                detailList.add(new Message(ioMessageSingle.getErrorType(),
                        getSingleMessage(ioMessageSingle)));
            }
        }
        result.add(topList);
        result.add(detailList);
        return result;
    }

    public List<Message> getShowMessage() {
        List result = new ArrayList();
        for (IOMessageSingle ioMessageSingle : this.msgList) {
            result.add(new Message(ioMessageSingle.getErrorType(),
                    getSingleMessage(ioMessageSingle)));
        }
        return result;
    }

    public boolean hasErrorMsg() {
        List result = new ArrayList();
        for (IOMessageSingle ioMessageSingle : this.msgList) {
            if (ioMessageSingle.getErrorType().intValue() < 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasWarningMsg() {
        List result = new ArrayList();
        for (IOMessageSingle ioMessageSingle : this.msgList) {
            if (ioMessageSingle.getErrorType().intValue() > 0) {
                return true;
            }
        }
        return false;
    }

    public String numToExcelColNum(int num) {
        if ((num < 0) || (num > 701)) {
            return "";
        }
        int bgnChar = 65;
        String result = "";
        int first = num / 26 - 1;
        int second = num % 26;
        if (first >= 0) {
            result = result + (char) (first + bgnChar);
        }
        result = result + (char) (second + bgnChar);
        return result;
    }

    public void addMessageToFirst(IOMessageSingle ioMessageSingle) {
        this.msgList.add(0, ioMessageSingle);
    }

    public void addMessage(IOMessageSingle ioMessageSingle) {
        this.msgList.add(ioMessageSingle);
    }

    public void addMessage(List<IOMessageSingle> msgList) {
        for (IOMessageSingle ioMessageSingle : msgList)
            addMessage(ioMessageSingle);
    }

    public List<IOMessageSingle> getMsgList() {
        return this.msgList;
    }

    public String getMsgFormat() {
        return this.msgFormat;
    }

    public void setMsgFormat(String msgFormat) {
        if (msgFormat == null)
            this.msgFormat = "";
        else
            this.msgFormat = msgFormat;
    }
}