package com.hr.io.domain;

import com.hr.base.ComonBeans;
import com.hr.io.domain.base.BaseOutmatchModel;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OutmatchModel extends BaseOutmatchModel {
    private static final long serialVersionUID = 1L;
    private String ommDefaultMean;
    private String ommStatisticPlaceMean;
    private String ommStatisticDisplayTypeMean;
    private String ommNoTitleMean;
    private String ommOutputTypeMean;
    private Map<String, String> ommStatisticPlaceMap;
    private Map<String, String> ommStatisticDisplayTypeMap;
    private Map<String, String> ommOutputTypeMap;
    private Map<String, String> ommNoTitleMap;
    private List<Outmatch> omList;

    public OutmatchModel() {
    }

    public OutmatchModel(String ommId) {
        super(ommId);
    }

    public OutmatchModel(String ommId, Iodef ommIo, String ommName, Date ommCreateTime,
            Date ommLastChangeTime, Integer ommStatisticPlace, String ommOutputType,
            Integer ommNoTitle, Integer ommDefault, String ommLastChangeBy, String ommCreateBy,
            Integer ommStatisticDisplayType) {
        super(ommId, ommIo, ommName, ommCreateTime, ommLastChangeTime, ommStatisticPlace,
                ommOutputType, ommNoTitle, ommDefault, ommLastChangeBy, ommCreateBy,
                ommStatisticDisplayType);
    }

    public void setOmmStatisticPlace(Integer ommStatisticPlace) {
        super.setOmmStatisticPlace(ommStatisticPlace);
        if (ommStatisticPlace != null) {
            this.ommStatisticPlaceMean = ComonBeans
                    .getParameterValue(PROP_OMM_STATISTIC_PLACE, new String[] { ommStatisticPlace
                            .toString() });
        } else
            this.ommStatisticPlaceMean = null;
    }

    public void setOmmDefault(Integer ommDefault) {
        super.setOmmDefault(ommDefault);
        if (ommDefault != null) {
            this.ommDefaultMean = ComonBeans
                    .getParameterValue("yesOrNoImg", new String[] { ommDefault.toString() });
        } else
            this.ommDefaultMean = null;
    }

    public void setOmmStatisticDisplayType(Integer ommStatisticDisplayType) {
        super.setOmmStatisticDisplayType(ommStatisticDisplayType);
        if (ommStatisticDisplayType != null) {
            this.ommStatisticDisplayTypeMean = ComonBeans
                    .getParameterValue(PROP_OMM_STATISTIC_DISPLAY_TYPE,
                                       new String[] { ommStatisticDisplayType.toString() });
        } else
            this.ommStatisticDisplayTypeMean = null;
    }

    public void setOmmNoTitle(Integer ommNoTitle) {
        super.setOmmNoTitle(ommNoTitle);
        if (ommNoTitle != null) {
            this.ommNoTitleMean = ComonBeans.getParameterValue("noOrYes", new String[] { ommNoTitle
                    .toString() });
        } else
            this.ommNoTitleMean = null;
    }

    public void setOmmOutputType(String ommOutputType) {
        super.setOmmOutputType(ommOutputType);
        if (ommOutputType != null) {
            this.ommOutputTypeMean = ComonBeans.getParameterValue(PROP_OMM_OUTPUT_TYPE,
                                                                  new String[] { ommOutputType
                                                                          .toString() });
        } else
            this.ommOutputTypeMean = null;
    }

    public void setAllMaps() {
        this.ommStatisticPlaceMap = ComonBeans.getValuesToMap(PROP_OMM_STATISTIC_PLACE,
                                                              new boolean[0]);
        this.ommStatisticDisplayTypeMap = ComonBeans
                .getValuesToMap(PROP_OMM_STATISTIC_DISPLAY_TYPE, new boolean[0]);
        this.ommOutputTypeMap = ComonBeans.getValuesToMap(PROP_OMM_OUTPUT_TYPE, new boolean[0]);
        this.ommNoTitleMap = ComonBeans.getValuesToMap("noOrYes", new boolean[0]);
    }

    public String getOmmStatisticPlaceMean() {
        return this.ommStatisticPlaceMean;
    }

    public void setOmmStatisticPlaceMean(String ommStatisticPlaceMean) {
        this.ommStatisticPlaceMean = ommStatisticPlaceMean;
    }

    public String getOmmNoTitleMean() {
        return this.ommNoTitleMean;
    }

    public Map<String, String> getOmmStatisticPlaceMap() {
        return this.ommStatisticPlaceMap;
    }

    public Map<String, String> getOmmOutputTypeMap() {
        return this.ommOutputTypeMap;
    }

    public Map<String, String> getOmmNoTitleMap() {
        return this.ommNoTitleMap;
    }

    public List<Outmatch> getOmList() {
        return this.omList;
    }

    public void setOmList(List<Outmatch> omList) {
        this.omList = omList;
    }

    public String getOmmOutputTypeMean() {
        return this.ommOutputTypeMean;
    }

    public String getOmmStatisticDisplayTypeMean() {
        return this.ommStatisticDisplayTypeMean;
    }

    public Map<String, String> getOmmStatisticDisplayTypeMap() {
        return this.ommStatisticDisplayTypeMap;
    }

    public String getOmmDefaultMean() {
        return this.ommDefaultMean;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.domain.OutmatchModel JD-Core Version: 0.5.4
 */