package com.hr.configuration.bo;

import com.hr.configuration.domain.JobTitle;
import java.util.List;

public abstract interface IJobTitleBo {
    public abstract List FindAllJobTitle();

    public abstract List<JobTitle> FindEnabledJobTitle();

    public abstract boolean addJobTitle(JobTitle paramJobTitle);

    public abstract boolean delJobTitle(Class<JobTitle> paramClass, String paramString);

    public abstract String updateJobTitle(JobTitle paramJobTitle);

    public abstract String getJobTitleName(String paramString);

    public abstract List getJobTitlesByNos(String[] paramArrayOfString);

    public abstract JobTitle loadJobTitleByNo(String paramString);

    public abstract void saveJobTitleSortIdByBatch(String[] paramArrayOfString);

    public abstract <T> List loadObjects(Class<T> paramClass, String[] paramArrayOfString);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.IJobTitleBo JD-Core Version: 0.5.4
 */