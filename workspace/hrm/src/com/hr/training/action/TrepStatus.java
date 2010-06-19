package com.hr.training.action;

import com.hr.base.BaseAction;
import com.hr.configuration.domain.Statusconf;
import com.hr.training.bo.ITremployeeplanBO;
import java.util.List;

public class TrepStatus extends BaseAction {
    private static List<Statusconf> status;

    public static String getTreStatus(int statusNo) {
        ITremployeeplanBO tremployeeplanBO = (ITremployeeplanBO) getBean("tremployeeplanBO");
        status = tremployeeplanBO.getCompeplanStatusInRecruitplan();

        String resultStatus = Statusconf.getEcpStatus(statusNo, status);
        if (resultStatus != null) {
            return resultStatus;
        }
        return "";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.training.action.TrepStatus JD-Core Version: 0.5.4
 */