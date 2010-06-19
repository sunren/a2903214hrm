package com.hr.help.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import com.hr.help.bo.IHelpBo;
import com.hr.help.domain.Helpclass;
import java.util.List;

public class SearchHelp extends BaseAction implements Constants {
    private List helpList;
    private List classList;
    private String classId;

    public SearchHelp() {
        this.classId = null;
    }

    public String execute() throws Exception {
        IHelpBo bo = (IHelpBo) getBean("helpBo");
        this.classList = bo.getClassList();
        if ((this.classId == null) && (this.classList.size() > 0)) {
            int length = this.classList.size();
            for (int i = 0; i < length; ++i) {
                Helpclass helpclass = ((Helpclass) this.classList.get(i)).getHcFather();
                if ((helpclass != null) && (helpclass.getId() != null)
                        && (helpclass.getId().length() > 0)) {
                    this.classId = ((Helpclass) this.classList.get(i)).getId();
                    break;
                }
            }
        }
        this.helpList = bo.getHelpByClass(this.classId);

        return "success";
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

    public List getHelpList() {
        return this.helpList;
    }

    public void setHelpList(List helpList) {
        this.helpList = helpList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.help.action.SearchHelp JD-Core Version: 0.5.4
 */