package com.hr.configuration.bo;

import com.hr.configuration.domain.Jobgrade;
import java.util.List;

public abstract interface IJobgradeBO {
    public abstract Jobgrade loadJobgrade(String paramString);

    public abstract List<Jobgrade> FindAllJobgrade();

    public abstract boolean addJobgrade(Jobgrade paramJobgrade);

    public abstract boolean delJobgrade(Class<Jobgrade> paramClass, String paramString);

    public abstract boolean updateJobgrade(Jobgrade paramJobgrade);

    public abstract void saveJobGradeIdByBatch(String[] paramArrayOfString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.IJobgradeBO JD-Core Version: 0.5.4
 */