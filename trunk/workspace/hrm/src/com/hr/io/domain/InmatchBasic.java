package com.hr.io.domain;

import com.hr.base.ComonBeans;
import com.hr.io.domain.base.BaseInmatchBasic;
import java.util.List;
import java.util.Map;

public class InmatchBasic extends BaseInmatchBasic {
    private static final long serialVersionUID = 1L;
    private String formatDesc;
    private String imbDataTypeMean;
    private Map<String, String> imbDataTypeMap;
    private String imbRequiredMean;
    private Map<String, String> imbRequiredMap;
    private String imbDetectErrorMean;
    private Map<String, String> imbDetectErrorMap;
    private List<Inmatch> imList;

    public InmatchBasic() {
    }

    public InmatchBasic(String imbId) {
        super(imbId);
    }

    public InmatchBasic(String imbId, Iodef imbIo, String imbFieldName, String imbFieldDesc,
            String imbDataType, Integer imbDetectError, Integer imbRequired, Integer imbSortId,
            String imbSample) {
        super(imbId, imbIo, imbFieldName, imbFieldDesc, imbDataType, imbDetectError, imbRequired,
                imbSortId, imbSample);
    }

    public void setAllMaps() {
        this.imbDataTypeMap = ComonBeans.getValuesToMap(PROP_IMB_DATA_TYPE, new boolean[0]);
        this.imbRequiredMap = ComonBeans.getValuesToMap("yesOrNo", new boolean[0]);
        this.imbDetectErrorMap = ComonBeans.getValuesToMap("yesOrNo", new boolean[0]);
        this.formatDesc = ComonBeans.getParameterValue(PROP_IMB_FORMAT, new String[] { super
                .getImbDataType() });
    }

    public void setImbDataType(String imbDataType) {
        super.setImbDataType(imbDataType);
        if ((imbDataType != null) && (imbDataType.length() > 0))
            this.imbDataTypeMean = ComonBeans.getParameterValue(PROP_IMB_DATA_TYPE,
                                                                new String[] { imbDataType });
        else
            this.imbDataTypeMean = null;
    }

    public void setImbRequired(Integer imbRequired) {
        super.setImbRequired(imbRequired);
        if (imbRequired != null)
            this.imbRequiredMean = ComonBeans.getParameterValue("yesOrNoImg",
                                                                new String[] { imbRequired
                                                                        .toString() });
        else
            this.imbRequiredMean = null;
    }

    public void setImbDetectError(Integer imbDetectError) {
        super.setImbDetectError(imbDetectError);
        if (imbDetectError != null)
            this.imbDetectErrorMean = ComonBeans.getParameterValue("yesOrNoImg",
                                                                   new String[] { imbDetectError
                                                                           .toString() });
        else
            this.imbDetectErrorMean = null;
    }

    public void setImList(List<Inmatch> imList) {
        this.imList = imList;
    }

    public List<Inmatch> getImList() {
        return this.imList;
    }

    public String getFormatDesc() {
        return this.formatDesc;
    }

    public Map<String, String> getImbDataTypeMap() {
        return this.imbDataTypeMap;
    }

    public String getImbRequiredMean() {
        return this.imbRequiredMean;
    }

    public Map<String, String> getImbRequiredMap() {
        return this.imbRequiredMap;
    }

    public String getImbDetectErrorMean() {
        return this.imbDetectErrorMean;
    }

    public Map<String, String> getImbDetectErrorMap() {
        return this.imbDetectErrorMap;
    }

    public String getImbDataTypeMean() {
        return this.imbDataTypeMean;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.domain.InmatchBasic JD-Core Version: 0.5.4
 */