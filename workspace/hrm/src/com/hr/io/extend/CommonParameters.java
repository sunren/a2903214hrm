package com.hr.io.extend;

import com.hr.io.domain.Inmatch;
import com.hr.io.domain.InmatchBasic;
import com.hr.io.domain.InmatchModel;
import com.hr.profile.domain.Employee;
import com.hr.util.MessageForField;
import com.hr.util.reflect.ObjectProperty;
import com.hr.util.reflect.SetObjectProperty;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class CommonParameters {
    private InmatchModel inmatchModel;
    private Map<String, Inmatch> matchMap;
    private IOMessages ioMessages;
    private Employee currEmp;
    private String authCondition;
    private String importParameter;

    public CommonParameters(InmatchModel inmatchModel, Map<String, Inmatch> matchMap,
            IOMessages ioMessages, Employee currEmp, String authorityCondition,
            String[] importParameter) {
        setMatchMap(matchMap);
        setInmatchModel(inmatchModel);
        setIoMessages(ioMessages);
        setCurrEmp(currEmp);
        setAuthCondition(authorityCondition);
        if (importParameter.length > 0)
            setImportParameter(importParameter[0]);
    }

    public Integer getColNum(String fieldName) {
        if (this.matchMap.containsKey(fieldName)) {
            return ((Inmatch) this.matchMap.get(fieldName)).getImSortId();
        }
        return null;
    }

    public String getDesc(String fieldName) {
        if (this.matchMap.containsKey(fieldName)) {
            return ((Inmatch) this.matchMap.get(fieldName)).getImFieldDesc();
        }
        return fieldName;
    }

    public void addMessage(String contentModle, Integer msgType, Integer rowNum, String[] fieldName) {
        String[] paras;
        Integer colNum;
        if (fieldName.length == 0) {
            colNum = null;
            paras = new String[0];
        } else {
            colNum = getColNum(fieldName[0]);
            paras = new String[fieldName.length];
            for (int i = 0; i < fieldName.length; ++i) {
                paras[i] = getDesc(fieldName[i]);
            }
        }
        this.ioMessages.addMessage(new IOMessageSingle(MessageFormat.format(contentModle,
                                                                            (Object[]) paras),
                msgType, new Integer[] { rowNum, colNum }));
    }

    public void addErrorMessage(String contentModle, Integer rowNum, String[] fieldName) {
        addMessage(contentModle, Integer.valueOf(-1), rowNum, fieldName);
    }

    public void addMessages(List<MessageForField> msgList, Integer rowNum) {
        for (MessageForField mff : msgList)
            addMessage(mff.getMessageModel(), mff.getMsgType(), rowNum, mff.getFields());
    }

    public void copy1To2(Object obj1, Object obj2) throws Exception {
        for (int i = 0; i < this.inmatchModel.getImList().size(); ++i) {
            String fieldName = ((Inmatch) this.inmatchModel.getImList().get(i)).getImImb()
                    .getImbFieldName();
            if (fieldName.indexOf(".") != -1)
                fieldName = fieldName.substring(0, fieldName.indexOf("."));
            SetObjectProperty.setObjectProperty(obj2, fieldName, ObjectProperty
                    .getObjectFinalProperty(obj1, fieldName));
        }
    }

    public Map<String, Inmatch> getMatchMap() {
        return this.matchMap;
    }

    public void setMatchMap(Map<String, Inmatch> matchMap) {
        this.matchMap = matchMap;
    }

    public InmatchModel getInmatchModel() {
        return this.inmatchModel;
    }

    public void setInmatchModel(InmatchModel inmatchModel) {
        this.inmatchModel = inmatchModel;
    }

    public IOMessages getIoMessages() {
        return this.ioMessages;
    }

    public void setIoMessages(IOMessages ioMessages) {
        this.ioMessages = ioMessages;
    }

    public Employee getCurrEmp() {
        return this.currEmp;
    }

    public void setCurrEmp(Employee currEmp) {
        this.currEmp = currEmp;
    }

    public String getImportParameter() {
        return this.importParameter;
    }

    public void setImportParameter(String importParameter) {
        this.importParameter = importParameter;
    }

    public String getAuthCondition() {
        return this.authCondition;
    }

    public void setAuthCondition(String authorityCondition) {
        this.authCondition = authorityCondition;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.extend.CommonParameters JD-Core Version: 0.5.4
 */