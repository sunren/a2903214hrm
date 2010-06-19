package com.hr.information.action;

import com.hr.base.BaseAction;
import com.hr.base.Constants;
import org.apache.log4j.Logger;

public class IndexInfo extends BaseAction implements Constants {
    private static final long serialVersionUID = -4234940898871087425L;
    private static final Logger logger = Logger.getLogger(IndexInfo.class);

    public String execute() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("测试：execute() - CreateInfo.action");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("execute() -CreateInfo- end");
        }
        return "success";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.information.action.IndexInfo JD-Core Version: 0.5.4
 */