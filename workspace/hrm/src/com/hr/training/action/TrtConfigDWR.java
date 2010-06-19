package com.hr.training.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.training.bo.ITrTypeBO;
import com.hr.training.domain.Trtype;

public class TrtConfigDWR extends BaseAction {
    protected static final String SUCC = "SUCC";
    protected static final String FAIL = "FAIL";
    ITrTypeBO trTypeBO;

    public TrtConfigDWR() {
        this.trTypeBO = ((ITrTypeBO) BaseAction.getBean("trTypeBO"));
    }

    private boolean checkAuthForTrt() {
        String flt = DWRUtil.checkAuth("trtConfig", "execute");

        return !"error".equalsIgnoreCase(flt);
    }

    public String addTrType(Trtype trtype) {
        if (checkAuthForTrt()) {
            try {
                this.trTypeBO.save(trtype);
                return String.valueOf(trtype.getTrtNo());
            } catch (Exception e) {
                e.printStackTrace();
                return "FAIL";
            }
        }
        return "noauth";
    }

    public String delTrType(String trtNo) {
        if (checkAuthForTrt()) {
            try {
                this.trTypeBO.delete(trtNo);
                return "SUCC";
            } catch (Exception e) {
                e.printStackTrace();
                return "related";
            }
        }
        return "noauth";
    }

    public String updateTrType(Trtype trtype) {
        if (checkAuthForTrt()) {
            try {
                this.trTypeBO.update(trtype);
                return "SUCC";
            } catch (Exception e) {
                e.printStackTrace();
                return "FAIL";
            }
        }
        return "noauth";
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.training.action.TrtConfigDWR JD-Core Version: 0.5.4
 */