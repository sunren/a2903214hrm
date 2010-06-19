package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.base.FileOperate;
import com.hr.security.bo.LogBo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.Pager;
import java.util.List;

public class LogsManager extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Pager pager;
    private List recorderList;

    public LogsManager() {
        this.pager = new Pager();

        this.recorderList = null;
    }

    public Pager getPager() {
        return this.pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List getRecorderList() {
        return this.recorderList;
    }

    public void setRecorderList(List recorderList) {
        this.recorderList = recorderList;
    }

    public String execute() throws Exception {
        LogBo logBo = (LogBo) SpringBeanFactory.getBean("logmanager");
        this.recorderList = logBo.getLogRecorders(FileOperate.getFileHomePath()
                + "login_log/login.xml", this.pager);
        return "success";
    }

    public String deleteAllLogs() throws Exception {
        LogBo logBo = (LogBo) SpringBeanFactory.getBean("logmanager");
        logBo.clearLogRecorders(FileOperate.getFileHomePath() + "login_log/login.xml");
        return "success";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.action.LogsManager JD-Core Version: 0.5.4
 */