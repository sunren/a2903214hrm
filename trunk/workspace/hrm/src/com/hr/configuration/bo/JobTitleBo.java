package com.hr.configuration.bo;

import com.hr.compensation.domain.Empsalaryacct;
import com.hr.configuration.dao.IJobTitleDao;
import com.hr.configuration.domain.JobTitle;
import com.hr.configuration.domain.Jobgrade;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class JobTitleBo implements IJobTitleBo {
    private IJobTitleDao jobTitleDao;

    public List FindAllJobTitle() {
        DetachedCriteria dc = DetachedCriteria.forClass(JobTitle.class);
        dc.setFetchMode("jobtitleDefaultAcct", FetchMode.JOIN);
        dc.setFetchMode("jobtitleDefaultJg", FetchMode.JOIN);
        dc.addOrder(Order.asc("jobtitleSortId"));
        return this.jobTitleDao.findByCriteria(dc);
    }

    public List<JobTitle> FindEnabledJobTitle() {
        DetachedCriteria dc = DetachedCriteria.forClass(JobTitle.class);
        dc.setFetchMode("jobtitleDefaultAcct", FetchMode.JOIN);
        dc.setFetchMode("jobtitleDefaultJg", FetchMode.JOIN);
        dc.add(Restrictions.eq(JobTitle.PROP_JOBTITLE_STATUS, new Integer(1)));
        dc.addOrder(Order.asc("jobtitleSortId"));
        return this.jobTitleDao.findByCriteria(dc);
    }

    public boolean addJobTitle(JobTitle jobTitle) {
        try {
            String trimmedName = jobTitle.getJobtitleName().trim();
            List old = this.jobTitleDao.exeHqlList(" from JobTitle where jobtitleName ='"
                    + trimmedName + "'");

            if ((old == null) || (old.size() == 0)) {
                jobTitle.setJobtitleName(trimmedName);
                if (StringUtils.isNotEmpty(jobTitle.getDefaultAccount()))
                    jobTitle
                            .setJobtitleDefaultAcct(new Empsalaryacct(jobTitle.getDefaultAccount()));
                else {
                    jobTitle.setJobtitleDefaultAcct(null);
                }
                if (StringUtils.isNotEmpty(jobTitle.getDefaultJg()))
                    jobTitle.setJobtitleDefaultJg(new Jobgrade(jobTitle.getDefaultJg()));
                else {
                    jobTitle.setJobtitleDefaultJg(null);
                }
                this.jobTitleDao.saveObject(jobTitle);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delJobTitle(Class<JobTitle> name, String id) {
        List old = this.jobTitleDao
                .exeHqlList("select count(*) from Employee emp left join emp.empPbNo empPb where  empPb.pbJobTitle.id = '"
                        + id + "'");
        Integer nums = Integer.valueOf(old.get(0).toString());
        if (nums.intValue() > 0) {
            return false;
        }

        List trainPlan = this.jobTitleDao
                .exeHqlList("select count(*) from Recruitplan where  recpJobTitle = '" + id + "'");
        Integer trainNums = Integer.valueOf(trainPlan.get(0).toString());
        if (trainNums.intValue() > 0) {
            return false;
        }

        List recruitment = this.jobTitleDao
                .exeHqlList("select count(*) from Tremployeeplan where  trpTraineeJobTitle = '"
                        + id + "'");
        Integer recNums = Integer.valueOf(recruitment.get(0).toString());
        if (recNums.intValue() > 0) {
            return false;
        }

        List reward = this.jobTitleDao
                .exeHqlList("select count(*) from Empreward where erJobTitle = '" + id + "'");
        Integer rewardNums = Integer.valueOf(reward.get(0).toString());
        if (rewardNums.intValue() > 0) {
            return false;
        }

        List eval = this.jobTitleDao.exeHqlList("select count(*) from Empeval where eeJobTitle = '"
                + id + "'");
        Integer evalNums = Integer.valueOf(eval.get(0).toString());
        if (evalNums.intValue() > 0) {
            return false;
        }

        List transfer = this.jobTitleDao
                .exeHqlList("select count(*) from Emptransfer where eftOldJobTitle = '" + id
                        + "' or eftNewJobTitle='" + id + "'");
        Integer transferNums = Integer.valueOf(transfer.get(0).toString());
        if (transferNums.intValue() > 0)
            return false;
        try {
            this.jobTitleDao.exeHql("delete from JobTitle where jobtitleNo='" + id + "'");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public String getJobTitleName(String jobTitleNo) {
        JobTitle jobTitle = (JobTitle) this.jobTitleDao.loadObject(JobTitle.class, jobTitleNo,
                                                                   null, new boolean[0]);
        return (jobTitle == null) ? "" : jobTitle.getJobtitleName();
    }

    public List getJobTitlesByNos(String[] jobTitleNos) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(JobTitle.class);
        detachedCriteria.add(Restrictions.in("jobtitleNo", jobTitleNos));
        return this.jobTitleDao.findByCriteria(detachedCriteria);
    }

    public JobTitle loadJobTitleByNo(String jobTitleNo) {
        return (JobTitle) this.jobTitleDao.loadObject(JobTitle.class, jobTitleNo, null,
                                                      new boolean[0]);
    }

    public void saveJobTitleSortIdByBatch(String[] ids) {
        int len = ids.length;
        int sortId = 1;
        for (int i = 0; i < len; ++i) {
            this.jobTitleDao.exeHql("update JobTitle set jobtitleSortId=" + sortId
                    + " where jobtitleNo='" + ids[i] + "'");
            ++sortId;
        }
    }

    public String updateJobTitle(JobTitle jobTitle) {
        String jobTitleName = jobTitle.getJobtitleName().trim();

        if (jobTitle.getJobtitleStatus().intValue() == 0) {
            List check = new ArrayList();
            String hql = "select count(*) from Employee emp left join emp.empPbNo empPB where  empPB.pbJobTitle.id = '"
                    + jobTitle.getJobtitleNo() + "'";
            check = this.jobTitleDao.exeHqlList(hql);
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                return "1";
            }
        }
        String hql = " from JobTitle where jobtitleNo !='" + jobTitle.getJobtitleNo()
                + "' and jobtitleName ='" + jobTitleName + "'";
        List old = this.jobTitleDao.exeHqlList(hql);

        if ((old == null) || (old.size() == 0)) {
            JobTitle oldJobTitle = (JobTitle) this.jobTitleDao.loadObject(JobTitle.class, jobTitle
                    .getJobtitleNo(), null, new boolean[0]);
            if (StringUtils.isNotEmpty(jobTitle.getDefaultAccount()))
                oldJobTitle.setJobtitleDefaultAcct(new Empsalaryacct(jobTitle.getDefaultAccount()));
            else {
                oldJobTitle.setJobtitleDefaultAcct(null);
            }
            if (StringUtils.isNotEmpty(jobTitle.getDefaultJg()))
                oldJobTitle.setJobtitleDefaultJg(new Jobgrade(jobTitle.getDefaultJg()));
            else {
                oldJobTitle.setJobtitleDefaultJg(null);
            }
            oldJobTitle.setJobtitleName(jobTitle.getJobtitleName());
            oldJobTitle.setJobtitleNameDesc(jobTitle.getJobtitleNameDesc());
            oldJobTitle.setJobtitleNeedGmApprove(jobTitle.getJobtitleNeedGmApprove());
            oldJobTitle.setJobtitleSortId(jobTitle.getJobtitleSortId());
            oldJobTitle.setJobtitleStatus(jobTitle.getJobtitleStatus());
            this.jobTitleDao.updateObject(oldJobTitle);
            return "0";
        }
        return "2";
    }

    public <T> List loadObjects(Class<T> clazz, String[] fetch) {
        return this.jobTitleDao.getObjects(clazz, fetch);
    }

    public IJobTitleDao getJobTitleDao() {
        return this.jobTitleDao;
    }

    public void setJobTitleDao(IJobTitleDao jobTitleDao) {
        this.jobTitleDao = jobTitleDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.bo.JobTitleBo JD-Core Version: 0.5.4
 */