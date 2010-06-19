package com.hr.io.domain;

import com.hr.base.ComonBeans;
import com.hr.io.domain.base.BaseIodef;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Iodef extends BaseIodef {
    private static final long serialVersionUID = 1L;
    private List transmitList;
    private Set<Iomatch> iomatchs = new HashSet(0);

    private Map<String, Object> paramMap = new HashMap();
    private String ioModuleMean;
    private String ioIsExtendMean;
    private String ioTypeMean;
    private Map<String, String> ioModuleMap;
    private Map<String, String> ioIsExtendMap;
    private Map<String, String> ioTypeMap;
    private List<OutmatchBasic> ombList;
    private List<InmatchBasic> imbList;

    public Iodef() {
    }

    public Iodef(String ioId) {
        super(ioId);
    }

    public Iodef(String ioId, String ioName, Integer ioType, Integer ioFilePath,
            Integer ioFileHasTitle, Integer ioMatchType, Integer ioModule, String ioAuthority,
            String ioClassName, String ioFetchNames, Date ioCreateTime, Date ioLastChangeTime) {
        super(ioId, ioName, ioType, ioFilePath, ioFileHasTitle, ioMatchType, ioModule, ioAuthority,
                ioClassName, ioFetchNames, ioCreateTime, ioLastChangeTime);
    }

    public Set getIomatchs() {
        return this.iomatchs;
    }

    public void setIomatchs(Set iomatchs) {
        this.iomatchs = iomatchs;
    }

    public List getTransmitList() {
        return this.transmitList;
    }

    public void setTransmitList(List transmitList) {
        this.transmitList = transmitList;
    }

    public Map<String, Object> getParamMap() {
        return this.paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public void setIoModule(Integer ioModule) {
        super.setIoModule(ioModule);
        if (ioModule != null)
            this.ioModuleMean = ComonBeans.getParameterValue(PROP_IO_MODULE,
                                                             new String[] { ioModule.toString() });
        else
            this.ioModuleMean = null;
    }

    public void setIoIsExtend(Integer ioIsExtend) {
        super.setIoIsExtend(ioIsExtend);
        if (ioIsExtend != null) {
            this.ioIsExtendMean = ComonBeans
                    .getParameterValue(PROP_IO_IS_EXTEND, new String[] { ioIsExtend.toString() });
        } else
            this.ioIsExtendMean = null;
    }

    public void setIoType(Integer ioType) {
        super.setIoType(ioType);
        if (ioType != null)
            this.ioTypeMean = ComonBeans.getParameterValue(PROP_IO_TYPE, new String[] { ioType
                    .toString() });
        else
            this.ioTypeMean = null;
    }

    public void setAllMaps() {
        this.ioIsExtendMap = ComonBeans.getValuesToMap(PROP_IO_IS_EXTEND, new boolean[0]);
        this.ioModuleMap = ComonBeans.getValuesToMap(PROP_IO_MODULE, new boolean[0]);
        this.ioTypeMap = ComonBeans.getValuesToMap(PROP_IO_TYPE, new boolean[0]);
    }

    public String getIoModuleMean() {
        return this.ioModuleMean;
    }

    public String getIoIsExtendMean() {
        return this.ioIsExtendMean;
    }

    public String getIoTypeMean() {
        return this.ioTypeMean;
    }

    public Map<String, String> getIoModuleMap() {
        return this.ioModuleMap;
    }

    public Map<String, String> getIoIsExtendMap() {
        return this.ioIsExtendMap;
    }

    public Map<String, String> getIoTypeMap() {
        return this.ioTypeMap;
    }

    public List<OutmatchBasic> getOmbList() {
        return this.ombList;
    }

    public List<InmatchBasic> getImbList() {
        return this.imbList;
    }

    public void setOmbList(List<OutmatchBasic> ombList) {
        this.ombList = ombList;
    }

    public void setImbList(List<InmatchBasic> imbList) {
        this.imbList = imbList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.domain.Iodef JD-Core Version: 0.5.4
 */