package com.hr.help.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.help.bo.IHelpBo;
import com.hr.help.domain.Help;
import com.hr.help.domain.Helpclass;
import java.util.List;

public class UpdateHelp extends BaseAction implements Constants {
    private String[] helpTitle;
    private String[] helpDesc;
    private String[] id;
    private String classId;
    private String classBrief;
    private String operation;
    private List helpList;
    private List classList;

    public UpdateHelp() {
        this.classId = null;

        this.classBrief = null;

        this.operation = null;
    }

    public String execute() throws Exception {
        IHelpBo bo = (IHelpBo) getBean("helpBo");
        if (this.classId == null) {
            return "success";
        }
        if ((this.operation == null) || (!this.operation.equalsIgnoreCase("update"))) {
            this.classList = bo.getClassList();
            this.helpList = bo.getHelpByClass(this.classId);
            return "input";
        }
        if ((this.classBrief != null) && (this.classBrief.trim().length() > 0)) {
            Helpclass tmpclass = (Helpclass) bo.loadObject(Helpclass.class, this.classId, null);
            tmpclass.setHcBrief(this.classBrief.trim());
            bo.updateObject(tmpclass);
        }
        if (this.helpTitle.length > 0) {
            for (int i = 0; i < this.helpTitle.length; ++i) {
                if ((this.id[i] != null) && (this.id[i].length() > 0)) {
                    Help tmpHelp = (Help) bo.loadObject(Help.class, this.id[i], null);
                    if ((this.helpTitle[i].trim().length() == 0)
                            && (this.helpDesc[i].trim().length() == 0)) {
                        if (tmpHelp != null)
                            bo.deleteObject(tmpHelp);
                    } else {
                        tmpHelp.setHelpTitle(this.helpTitle[i].trim());
                        tmpHelp.setHelpDesc(this.helpDesc[i].trim());
                        bo.updateObject(tmpHelp);
                    }
                } else {
                    if ((this.helpTitle[i].trim().length() <= 0)
                            || (this.helpDesc[i].trim().length() <= 0))
                        continue;
                    Help tmpHelp = new Help();
                    tmpHelp.setHelpTitle(this.helpTitle[i].trim());
                    tmpHelp.setHelpDesc(this.helpDesc[i].trim());
                    tmpHelp.setHelpClass(new Helpclass(this.classId));
                    bo.saveObject(tmpHelp);
                }
            }
        }
        return "success";
    }

    public String getClassBrief() {
        return this.classBrief;
    }

    public void setClassBrief(String classBrief) {
        this.classBrief = classBrief;
    }

    public String getClassId() {
        return this.classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public List getClassList() {
        return this.classList;
    }

    public void setClassList(List classList) {
        this.classList = classList;
    }

    public String[] getHelpDesc() {
        return this.helpDesc;
    }

    public void setHelpDesc(String[] helpDesc) {
        this.helpDesc = helpDesc;
    }

    public List getHelpList() {
        return this.helpList;
    }

    public void setHelpList(List helpList) {
        this.helpList = helpList;
    }

    public String[] getHelpTitle() {
        return this.helpTitle;
    }

    public void setHelpTitle(String[] helpTitle) {
        this.helpTitle = helpTitle;
    }

    public String[] getId() {
        return this.id;
    }

    public void setId(String[] id) {
        this.id = id;
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.help.action.UpdateHelp JD-Core Version: 0.5.4
 */