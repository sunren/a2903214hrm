package com.hr.io.domain;

import com.hr.base.ComonBeans;
import com.hr.io.domain.base.BaseOutmatchBasic;
import java.util.List;
import java.util.Map;

public class OutmatchBasic extends BaseOutmatchBasic {
    private static final long serialVersionUID = 1L;
    private String ombCanGroupMean;
    private String formatDesc;
    private Map<String, String> ombDataTypeMap;
    private Map<String, String> ombCanGroupMap;
    private List<Outmatch> omList;

    public OutmatchBasic() {
    }

    public OutmatchBasic(String ombId) {
        super(ombId);
    }

    public OutmatchBasic(String ombId, Iodef ombIo, String ombFieldName, Integer ombCanGroup,
            String ombDataType, String ombFieldDesc, Integer ombSortId, Integer ombColumnWidth) {
        super(ombId, ombIo, ombFieldName, ombCanGroup, ombDataType, ombFieldDesc, ombSortId,
                ombColumnWidth);
    }

    public void setAllMaps() {
        this.ombDataTypeMap = ComonBeans.getValuesToMap(PROP_OMB_DATA_TYPE, new boolean[0]);
        this.ombCanGroupMap = ComonBeans.getValuesToMap("yesOrNo", new boolean[0]);
        this.formatDesc = ComonBeans.getParameterValue(PROP_OMB_FORMAT, new String[] { super
                .getOmbDataType() });
    }

    public void setOmbCanGroup(Integer ombCanGroup) {
        super.setOmbCanGroup(ombCanGroup);
        if (ombCanGroup != null)
            this.ombCanGroupMean = ComonBeans.getParameterValue("yesOrNoImg",
                                                                new String[] { ombCanGroup
                                                                        .toString() });
        else
            this.ombCanGroupMean = null;
    }

    public Map<String, String> getOmbDataTypeMap() {
        return this.ombDataTypeMap;
    }

    public Map<String, String> getOmbCanGroupMap() {
        return this.ombCanGroupMap;
    }

    public String getOmbCanGroupMean() {
        return this.ombCanGroupMean;
    }

    public List<Outmatch> getOmList() {
        return this.omList;
    }

    public void setOmList(List<Outmatch> omList) {
        this.omList = omList;
    }

    public String getFormatDesc() {
        return this.formatDesc;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.domain.OutmatchBasic JD-Core Version: 0.5.4
 */