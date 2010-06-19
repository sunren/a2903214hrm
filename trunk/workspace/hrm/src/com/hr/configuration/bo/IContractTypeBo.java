package com.hr.configuration.bo;

import com.hr.configuration.domain.ContractType;
import java.util.List;

public abstract interface IContractTypeBo {
    public abstract List FindAllEcptype();

    public abstract void addContractType(ContractType paramContractType);

    public abstract String delContractType(String paramString);

    public abstract void updateContractType(ContractType paramContractType);

    public abstract List findByName(String paramString);

    public abstract void saveContractTypeIdByBatch(String[] paramArrayOfString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.IContractTypeBo JD-Core Version: 0.5.4
 */