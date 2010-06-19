package com.hr.information.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IInfoBO;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class NewsInfo extends BaseAction {
    private static final String ERROR = "ERROR";
    private static final String EXISTED = "EXISTED";
    private static final String SUCC = "SUCC";
    private static final String FAIL = "FAIL";
    private List allInfo;

    public String execute() throws Exception {
        IInfoBO infobo = (IInfoBO) SpringBeanFactory.getBean("infoBO");
        this.allInfo = infobo.FindAllInfo();
        return "success";
    }

    public List getAllInfo() {
        return this.allInfo;
    }

    public void setAllInfo(List allInfo) {
        this.allInfo = allInfo;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.information.action.NewsInfo JD-Core Version: 0.5.4
 */