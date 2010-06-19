package com.hr.io.domain;

import com.hr.base.ComonBeans;
import com.hr.io.domain.base.BaseInmatch;
import java.util.Map;

public class Inmatch extends BaseInmatch {
    private static final long serialVersionUID = 1L;
    private Map<String, String> imRequiredMap;
    private Map<String, String> imDetectErrorMap;
    private String formatDesc;

    public Inmatch() {
    }

    public Inmatch(String imId) {
        super(imId);
    }

    public Inmatch(String imId, InmatchModel imImm, InmatchBasic imImb, String imFieldDesc,
            Integer imDetectError, Integer imRequired, Integer imIsInput, Integer imSortId,
            String imSample) {
        super(imId, imImm, imImb, imFieldDesc, imDetectError, imRequired, imIsInput, imSortId,
                imSample);
    }

    public void initByInmatchBasic(InmatchBasic inmatchBasic) {
        super.setImImb(inmatchBasic);
        super.setImFieldDesc(inmatchBasic.getImbFieldDesc());
        super.setImFormat(inmatchBasic.getImbFormat());
        super.setImSortId(inmatchBasic.getImbSortId());
        super.setImDetectError(inmatchBasic.getImbDetectError());
        super.setImRequired(inmatchBasic.getImbRequired());
        super.setImSample(inmatchBasic.getImbSample());
    }

    public void setAllMaps() {
        this.formatDesc = null;
        this.imDetectErrorMap = null;
        this.imRequiredMap = null;
        this.imDetectErrorMap = ComonBeans.getValuesToMap("yesOrNo", new boolean[0]);

        this.imRequiredMap = ComonBeans.getValuesToMap("yesOrNo", new boolean[0]);
        if ((getImImb() == null) || (getImImb().getImbDataType() == null)) {
            return;
        }
        this.formatDesc = ComonBeans
                .getParameterValue(InmatchBasic.PROP_IMB_FORMAT, new String[] { getImImb()
                        .getImbDataType() });
    }

    public Map<String, String> getImRequiredMap() {
        return this.imRequiredMap;
    }

    public Map<String, String> getImDetectErrorMap() {
        return this.imDetectErrorMap;
    }

    public String getFormatDesc() {
        return this.formatDesc;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.domain.Inmatch JD-Core Version: 0.5.4
 */