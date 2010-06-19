package com.hr.configuration.domain;

import com.hr.configuration.domain.base.BaseContractType;

public class ContractType extends BaseContractType {
    public ContractType() {
    }

    public ContractType(String id) {
        setId(id);
    }

    public ContractType(String id, String ectName, String ectDescription, int sortId) {
        super(id, ectName, ectDescription, sortId);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.domain.ContractType JD-Core Version: 0.5.4
 */