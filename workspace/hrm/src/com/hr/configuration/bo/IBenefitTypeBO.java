package com.hr.configuration.bo;

import com.hr.configuration.domain.BenefitType;
import java.util.List;

public abstract interface IBenefitTypeBO {
    public abstract List<BenefitType> findAll();

    public abstract BenefitType searchById(String paramString);

    public abstract List<BenefitType> findNameByobj(BenefitType paramBenefitType);

    public abstract List<BenefitType> findSameNameByobj(BenefitType paramBenefitType);

    public abstract boolean addBenefitType(BenefitType paramBenefitType);

    public abstract boolean updateBenefitType(BenefitType paramBenefitType);

    public abstract boolean delBenefitType(Class<BenefitType> paramClass, String paramString);

    public abstract boolean saveBenefitTypeSortIdByBatch(String[] paramArrayOfString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.IBenefitTypeBO JD-Core Version: 0.5.4
 */