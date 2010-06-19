package com.hr.io.domain;

import com.hr.base.ComonBeans;
import com.hr.io.domain.base.BaseOutmatch;
import java.util.Map;

public class Outmatch extends BaseOutmatch {
    private static final long serialVersionUID = 1L;
    private Integer omIsOutput;
    private Map<String, String> omIsGroupMap;
    private Map<String, String> omStatisticMap;
    private String formatDesc;

    public Outmatch() {
    }

    public Outmatch(String omId) {
        super(omId);
    }

    public Outmatch(String omId, OutmatchBasic omOmb, OutmatchModel omOmm, String omFieldDesc,
            Integer omSortId, Integer omColumnWidth, Integer omIsGroup) {
        super(omId, omOmb, omOmm, omFieldDesc, omSortId, omColumnWidth, omIsGroup);
    }

    public void initByOutmatchBasic(OutmatchBasic outmatchBasic) {
        super.setOmOmb(outmatchBasic);
        super.setOmFieldDesc(outmatchBasic.getOmbFieldDesc());
        super.setOmFormat(outmatchBasic.getOmbFormat());
        super.setOmColumnWidth(outmatchBasic.getOmbColumnWidth());
        super.setOmSortId(outmatchBasic.getOmbSortId());
        super.setOmIsGroup(Integer.valueOf(0));
        setOmIsOutput(Integer.valueOf(0));
    }

    public void setAllMaps() {
        this.formatDesc = null;
        this.omStatisticMap = null;
        this.omIsGroupMap = null;

        if ((getOmOmb() == null) || (getOmOmb().getOmbDataType() == null)
                || (getOmOmb().getOmbCanGroup() == null)) {
            return;
        }
        this.omIsGroupMap = ComonBeans.getValuesToMap(PROP_OM_IS_GROUP, new boolean[0]);
        if (getOmOmb().getOmbCanGroup().intValue() != 1) {
            this.omIsGroupMap.remove("1");
            this.omIsGroupMap.remove("2");
        }
        String ombDataType = getOmOmb().getOmbDataType();
        String[] methods = null;
        if (ombDataType.compareTo("date") == 0) {
            methods = new String[] { "count", "max", "min", "mode" };
        } else if (ombDataType.compareTo("integer") == 0) {
            methods = new String[] { "avg", "sum", "count", "max", "min", "mode" };
        } else if (ombDataType.compareTo("decimal") == 0) {
            methods = new String[] { "max", "min", "avg", "sum", "mode", "count" };
        } else if (ombDataType.compareTo("string") == 0) {
            methods = new String[] { "count", "max", "min", "mode" };
        } else {
            methods = new String[] { "count", "max", "min", "mode" };
        }

        if (methods != null) {
            this.omStatisticMap = ComonBeans.getValuesToMap(PROP_OM_STATISTIC, methods,
                                                            new boolean[] { true });
        }
        this.formatDesc = ComonBeans.getParameterValue(OutmatchBasic.PROP_OMB_FORMAT,
                                                       new String[] { ombDataType });
    }

    public String getFormatDesc() {
        return this.formatDesc;
    }

    public Map<String, String> getOmIsGroupMap() {
        return this.omIsGroupMap;
    }

    public Map<String, String> getOmStatisticMap() {
        return this.omStatisticMap;
    }

    public Integer getOmIsOutput() {
        return this.omIsOutput;
    }

    public void setOmIsOutput(Integer omIsOutput) {
        this.omIsOutput = omIsOutput;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.domain.Outmatch JD-Core Version: 0.5.4
 */