package com.hr.importfile.bo;

import com.hr.importfile.base.ICheckLogic;
import com.hr.importfile.base.IInsertContent;
import com.hr.io.domain.Iodef;
import com.hr.profile.domain.Employee;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class TransmitBoImpl implements ITransmitBo {
    private ReadContent readContent;
    private CheckContent checkContent;

    public Hashtable<String, List<ErrorMessage>> checkTransmit(Iodef iodef, String[][] content,
            File file, int[] userChoose, List chooseList) {
        if (content == null)
            return null;

        Hashtable errorTable = new Hashtable();

        errorTable.put("matchList", this.checkContent.checkTransmit(content, chooseList,
                                                                    userChoose, iodef
                                                                            .getIoFileHasTitle()
                                                                            .intValue()));

        ICheckLogic checkLogic = null;
        try {
            String classPath = ((iodef.getIoIsExtend().intValue() == 0) ? "extend." : "ioextend.")
                    + iodef.getIoName() + "Check";
            checkLogic = (ICheckLogic) Class.forName("com.hr.importfile." + classPath)
                    .newInstance();
        } catch (Exception e) {
            return errorTable;
        }
        errorTable.put("logicList", checkLogic.checkLogicValid(content, chooseList, userChoose,
                                                               iodef));
        return errorTable;
    }

    public String[][] getContent(int hasTitle, File file, int showRows) {
        return this.readContent.readRows(hasTitle, file, showRows);
    }

    public Hashtable<String, List<ErrorMessage>> insertTransmit(Iodef iodef, String[][] content,
            File readFile, int[] userChoose, List transmitList, Employee user, Map paramMap) {
        ICheckLogic checkLogic = null;
        try {
            String classPath = ((iodef.getIoIsExtend().intValue() == 0) ? "extend." : "ioextend.")
                    + iodef.getIoName() + "Check";
            checkLogic = (ICheckLogic) Class.forName("com.hr.importfile." + classPath)
                    .newInstance();
        } catch (Exception e) {
        }
        Hashtable messageList = new Hashtable();
        List logicList = new ArrayList();

        List matchList = this.checkContent.checkTransmit(content, transmitList, userChoose, iodef
                .getIoFileHasTitle().intValue());
        messageList.put("matchList", matchList);

        if (checkLogic != null) {
            logicList = checkLogic.checkLogicValid(content, transmitList, userChoose, iodef);
            messageList.put("logicList", logicList);
        }

        if (((logicList != null) && (logicList.size() > 0))
                || ((matchList != null) && (matchList.size() > 0)))
            return messageList;

        IInsertContent insertContent = null;
        try {
            String classPath = ((iodef.getIoIsExtend().intValue() == 0) ? "extend." : "ioextend.")
                    + iodef.getIoName() + "Insert";
            insertContent = (IInsertContent) Class.forName("com.hr.importfile." + classPath)
                    .newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List insertList = insertContent.insertTransmit(content, transmitList, userChoose, iodef,
                                                       user, paramMap);
        messageList.put("insertList", insertList);
        return messageList;
    }

    public ReadContent getReadContent() {
        return this.readContent;
    }

    public void setReadContent(ReadContent readContent) {
        this.readContent = readContent;
    }

    public CheckContent getCheckContent() {
        return this.checkContent;
    }

    public void setCheckContent(CheckContent checkContent) {
        this.checkContent = checkContent;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.importfile.bo.TransmitBoImpl JD-Core Version: 0.5.4
 */