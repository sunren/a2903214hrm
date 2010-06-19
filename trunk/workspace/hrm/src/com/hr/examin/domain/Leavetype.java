package com.hr.examin.domain;

import com.hr.examin.domain.base.BaseLeavetype;

public class Leavetype extends BaseLeavetype {
    private static final long serialVersionUID = 1L;
    public static int LT_INCLUDE_HOLIDAYs_FLAG_YES = 1;

    public static int LT_INCLUDE_HOLIDAYs_FLAG_NO = 0;
    private String balForward;

    public Leavetype() {
    }

    public Leavetype(String ltNo) {
        super(ltNo);
    }

    public Leavetype(String ltNo, String ltName, String ltDesc, Integer ltInclHoliday,
            Integer ltAnnual, Integer ltSortId) {
        super(ltNo, ltName, ltDesc, ltInclHoliday, ltAnnual, ltSortId);
    }

    public String getBalForward() {
        return this.balForward;
    }

    public void setBalForward(String balForward) {
        this.balForward = balForward;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.domain.Leavetype JD-Core Version: 0.5.4
 */