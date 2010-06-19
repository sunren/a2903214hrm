package com.hr.configuration.bo;

import com.hr.configuration.dao.IJobgradeDAO;
import com.hr.configuration.domain.Jobgrade;
import com.hr.util.MyTools;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

public class JobgradeBOImpl implements IJobgradeBO {
    private IJobgradeDAO dao;

    public JobgradeBOImpl() {
        this.dao = null;
    }

    public Jobgrade loadJobgrade(String id) {
        return (Jobgrade) this.dao.loadObject(Jobgrade.class, id, null, new boolean[0]);
    }

    public List<Jobgrade> FindAllJobgrade() {
        DetachedCriteria dc = DetachedCriteria.forClass(Jobgrade.class);
        dc.addOrder(Order.asc(Jobgrade.PROP_JOB_GRADE_SORT_ID));
        return this.dao.findByCriteria(dc);
    }

    public boolean addJobgrade(Jobgrade jobgrade) {
        try {
            String trimmedJobGradeNo = jobgrade.getJobGradeNo().trim();
            List old = this.dao.exeHqlList(" from Jobgrade where jobGradeNo ='" + trimmedJobGradeNo
                    + "'");

            if ((old == null) || (old.size() == 0)) {
                String trimmedJobGradeName = jobgrade.getJobGradeName().trim();
                jobgrade.setJobGradeLevel(jobgrade.getJobGradeLevel());
                jobgrade.setJobGradeName(trimmedJobGradeName);
                jobgrade.setJobGradeNo(trimmedJobGradeNo);
                jobgrade.setJobGradeSortId(Integer.valueOf(0));
                UUID uuid = UUID.randomUUID();
                jobgrade.setId(uuid.toString());
                jobgrade.setEncry(Jobgrade.PROP_JOB_GRADE_MRP, jobgrade.getJobGradeMrp(), jobgrade
                        .getId(), MyTools.BIGDECIMAL);
                this.dao.saveObject(jobgrade);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateJobgrade(Jobgrade jobgrade) {
        String trimmedJobGradeNo = jobgrade.getJobGradeNo().trim();
        List old = this.dao.exeHqlList(" from Jobgrade where jobGradeNo ='" + trimmedJobGradeNo
                + "' and id <> '" + jobgrade.getId().trim() + "'");
        if ((old == null) || (old.size() == 0)) {
            Jobgrade oldJobgrade = (Jobgrade) this.dao.loadObject(Jobgrade.class, jobgrade.getId(),
                                                                  null, new boolean[0]);
            String trimmedJobGradeName = jobgrade.getJobGradeName().trim();
            oldJobgrade.setEncry("jobGradeMrp", jobgrade.getJobGradeMrp(), jobgrade.getId(),
                                 MyTools.BIGDECIMAL);
            oldJobgrade.setJobGradeNo(trimmedJobGradeNo);
            oldJobgrade.setJobGradeLevel(jobgrade.getJobGradeLevel());
            oldJobgrade.setJobGradeName(trimmedJobGradeName);
            this.dao.updateObject(oldJobgrade);
            return true;
        }
        return false;
    }

    public boolean delJobgrade(Class<Jobgrade> name, String id) {
        try {
            Jobgrade jobgrade = (Jobgrade) this.dao.loadObject(name, id, null, new boolean[0]);
            if (jobgrade == null) {
                return false;
            }
            List check = new ArrayList();

            check = this.dao
                    .exeHqlList("select count(*) from Empsalaryadj where esaJobgradeCur = '" + id
                            + "'or esaJobgradePro = '" + id + "'");
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                return false;
            }
            check = this.dao.exeHqlList("select count(*) from Empsalarypay where espJobgrade = '"
                    + id + "'");
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                return false;
            }
            check = this.dao
                    .exeHqlList("select count(*) from Empsalaryconfig where escJobgrade = '" + id
                            + "'");
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                return false;
            }
            check = this.dao
                    .exeHqlList("select count(*) from JobTitle where jobtitleDefaultJg.id = '" + id
                            + "'");
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                return false;
            }
            this.dao.deleteObject(jobgrade);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void saveJobGradeIdByBatch(String[] ids) {
        if (ids == null)
            return;
        int sort = 1;
        int len = ids.length;
        for (int i = 0; i < len; ++i) {
            this.dao.exeHql("update Jobgrade set jobGradeSortId=" + sort + " where id ='" + ids[i]
                    + "'");

            ++sort;
        }
    }

    public IJobgradeDAO getDao() {
        return this.dao;
    }

    public void setDao(IJobgradeDAO dao) {
        this.dao = dao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.bo.JobgradeBOImpl JD-Core Version: 0.5.4
 */