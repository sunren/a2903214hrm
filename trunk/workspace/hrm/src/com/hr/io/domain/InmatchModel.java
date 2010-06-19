package com.hr.io.domain;

import com.hr.base.ComonBeans;
import com.hr.io.domain.base.BaseInmatchModel;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class InmatchModel extends BaseInmatchModel {
    private static final long serialVersionUID = 1L;
    private String immNoTitleMean;
    private String immInputTypeMean;
    private String immImportModeMean;
    private Map<String, String> immImportModeMap;
    private Map<String, String> immInputTypeMap;
    private Map<String, String> immNoTitleMap;
    private List<Inmatch> imList;

    public InmatchModel() {
    }

    public InmatchModel(String immId) {
        super(immId);
    }

    public InmatchModel(String immId, Iodef immIo, String immName, String immInputType,
            Integer immNoTitle, Integer immImportMode, Integer immDefault, Date immCreateTime,
            Date immLastChangeTime, String immLastChangeBy, String immCreateBy) {
        super(immId, immIo, immName, immInputType, immNoTitle, immImportMode, immDefault,
                immCreateTime, immLastChangeTime, immLastChangeBy, immCreateBy);
    }

    public void setImmNoTitle(Integer immNoTitle) {
        super.setImmNoTitle(immNoTitle);
        if (immNoTitle != null) {
            this.immNoTitleMean = ComonBeans.getParameterValue("noOrYes", new String[] { immNoTitle
                    .toString() });
        } else
            this.immNoTitleMean = null;
    }

    public void setImmInputType(String immInputType) {
        super.setImmInputType(immInputType);
        if (immInputType != null) {
            this.immInputTypeMean = ComonBeans.getParameterValue(PROP_IMM_INPUT_TYPE,
                                                                 new String[] { immInputType });
        } else
            this.immInputTypeMean = null;
    }

    public void setImmImportMode(Integer immImportMode) {
        super.setImmImportMode(immImportMode);
        if (immImportMode != null) {
            this.immImportModeMean = ComonBeans.getParameterValue(PROP_IMM_IMPORT_MODE,
                                                                  new String[] { immImportMode
                                                                          .toString() });
        } else
            this.immImportModeMean = null;
    }

    public void setAllMaps() {
        this.immInputTypeMap = ComonBeans.getValuesToMap(PROP_IMM_INPUT_TYPE, new boolean[0]);
        this.immImportModeMap = ComonBeans.getValuesToMap(PROP_IMM_IMPORT_MODE, new boolean[0]);
        this.immNoTitleMap = ComonBeans.getValuesToMap("noOrYes", new boolean[0]);
    }

    public String getImmNoTitleMean() {
        return this.immNoTitleMean;
    }

    public String getImmInputTypeMean() {
        return this.immInputTypeMean;
    }

    public String getImmImportModeMean() {
        return this.immImportModeMean;
    }

    public Map<String, String> getImmImportModeMap() {
        return this.immImportModeMap;
    }

    public Map<String, String> getImmInputTypeMap() {
        return this.immInputTypeMap;
    }

    public Map<String, String> getImmNoTitleMap() {
        return this.immNoTitleMap;
    }

    public List<Inmatch> getImList() {
        return this.imList;
    }

    public void setImList(List<Inmatch> imList) {
        this.imList = imList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.domain.InmatchModel JD-Core Version: 0.5.4
 */