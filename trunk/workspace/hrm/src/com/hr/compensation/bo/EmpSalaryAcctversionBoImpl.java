package com.hr.compensation.bo;

import com.hr.compensation.dao.IEmpSalaryAcctversionDao;
import com.hr.compensation.domain.Empbenefitplan;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DateUtil;
import com.hr.util.Pager;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EmpSalaryAcctversionBoImpl implements IEmpSalaryAcctversionBo {
    private IEmpSalaryAcctversionDao empsalaryacctversionDao;

    public Boolean deleteEmpsalaryacctversion(String esavId) {
        try {
            String hql = "delete Empsalaryacctversion as esav where id='" + esavId + "'";
            this.empsalaryacctversionDao.exeHql(hql);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.valueOf(false);
    }

    public Boolean useEsaversion(String esaId, int version) {
        try {
            String hql = "update Empsalaryacctversion set esavValidTo= null where esavEsac.id='"
                    + esaId + "' and esavVersion=" + version;
            this.empsalaryacctversionDao.updateHqlQuery(hql);
            return Boolean.valueOf(true);
        } catch (Exception e) {
        }
        return Boolean.valueOf(false);
    }

    public List<Empsalaryacctversion> searchEsav(Empsalaryacct empsalaryacct, Pager page,
            String versionFlag) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalaryacctversion.class);

        detachedCriteria.setFetchMode("esavEsac", FetchMode.JOIN);
        detachedCriteria.createAlias("esavEsac", "esac");

        if ((empsalaryacct != null) && (empsalaryacct.getEsacName() != null)) {
            detachedCriteria.add(Restrictions.like("esac.esacName", empsalaryacct.getEsacName()
                    .trim(), MatchMode.ANYWHERE));
        }
        if (versionFlag == null) {
            detachedCriteria.add(Restrictions.isNull("esavValidTo"));
        }
        checkOrderMethod(detachedCriteria, page.getOrder());

        detachedCriteria.addOrder(Order.asc("esac.esacName"));
        detachedCriteria.addOrder(Order.desc(Empsalaryacctversion.PROP_ESAV_VALID_FROM));
        return this.empsalaryacctversionDao.findByCriteria(detachedCriteria);
    }

    public Boolean addAcctVersion(Empsalaryacctversion empsalaryacctversion,
            List<Empsalaryacctitems> newItems) {
        try {
            IEmpSalaryAcctBo esaBo = (IEmpSalaryAcctBo) SpringBeanFactory
                    .getBean("empsalaryacctBo");
            esaBo.insertEmpsalaryacct(empsalaryacctversion.getEsavEsac());

            insertEmpsalaryacctversion(empsalaryacctversion);

            IEmpSalaryAcctitemsBo esaiBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                    .getBean("empsalaryacctitemsBo");
            esaiBo.saveOrupdate(newItems);

            return Boolean.valueOf(true);
        } catch (Exception e) {
        }
        return Boolean.valueOf(false);
    }

    public Boolean deleteAcctVersion(String acctversionId, String acctId) {
        try {
            IEmpSalaryAcctBo esaBo = (IEmpSalaryAcctBo) SpringBeanFactory
                    .getBean("empsalaryacctBo");
            Empsalaryacctversion esavPrev = esaBo.getMaxValidToEsav(acctId);
            IEmpSalaryAcctitemsBo esaiBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                    .getBean("empsalaryacctitemsBo");

            if (!esavPrev.getId().equals(acctversionId)) {
                ISalaryconfBo salaryconfBo = (ISalaryconfBo) SpringBeanFactory
                        .getBean("salaryconfBo");
                if (salaryconfBo.hasSalaryConfigByAcctVersion(acctversionId) > 0) {
                    Map newMap = getOldAcctItems(acctversionId);

                    List oldItems = esaiBo.getItemsByAcctversion(esavPrev.getId());

                    List salaryconfigList = salaryconfBo.changeSalaryConfig(acctversionId, newMap,
                                                                            oldItems);
                    salaryconfBo.saveOrupdate(salaryconfigList);
                }
                esavPrev.setEsavValidTo(null);
                updateEmpsalaryacctversion(esavPrev);
                esaiBo.deleteEmpsalaryacctitems(acctversionId);
                deleteEmpsalaryacctversion(acctversionId);
                return Boolean.valueOf(true);
            }

            ISalaryconfBo escBo = (ISalaryconfBo) SpringBeanFactory.getBean("salaryconfBo");
            escBo.deleteSalaryConfig(acctversionId);
            esaiBo.deleteEmpsalaryacctitems(acctversionId);
            deleteEmpsalaryacctversion(acctversionId);
            esaBo.deleteEmpsalaryacct(acctId);
            return Boolean.valueOf(true);
        } catch (Exception e) {
        }
        return Boolean.valueOf(false);
    }

    public Boolean updateAcctVersion(Empsalaryacctversion empsalaryacctversion,
            List<Empsalaryacctitems> newItems, List<Empsalaryconfig> salaryconfigList,
            List<Empsalarypay> salarypayList, List<Empbenefitplan> planList) {
        try {
            IEmpSalaryAcctBo esaBo = (IEmpSalaryAcctBo) SpringBeanFactory
                    .getBean("empsalaryacctBo");
            esaBo.updateEmpsalaryacct(empsalaryacctversion.getEsavEsac());

            empsalaryacctversion.setEsavLastChangeTime(new Date());
            IEmpSalaryAcctversionBo esavBo = (IEmpSalaryAcctversionBo) SpringBeanFactory
                    .getBean("empsalaryacctversionBo");
            esavBo.updateEmpsalaryacctversion(empsalaryacctversion);

            ISalaryconfBo salaryconfBo = (ISalaryconfBo) SpringBeanFactory.getBean("salaryconfBo");
            salaryconfBo.saveOrupdate(salaryconfigList);

            ISalaryPaidBo espBo = (ISalaryPaidBo) SpringBeanFactory.getBean("salaryPaidBo");
            espBo.saveOrupdate(salarypayList);

            IEmpBenefitBo empbenefitBo = (IEmpBenefitBo) SpringBeanFactory.getBean("empbenefitBo");
            empbenefitBo.saveOrupdate(planList);

            IEmpSalaryAcctitemsBo esaiBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                    .getBean("empsalaryacctitemsBo");
            esaiBo.deleteEmpsalaryacctitems(empsalaryacctversion.getId());
            esaiBo.saveOrupdate(newItems);

            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.valueOf(false);
    }

    public Boolean updateAcctVersion(Empsalaryacctversion empsalaryacctversion,
            List<Empsalaryacctitems> newItems, List<Empsalaryconfig> salaryconfigList) {
        try {
            IEmpSalaryAcctBo esaBo = (IEmpSalaryAcctBo) SpringBeanFactory
                    .getBean("empsalaryacctBo");
            esaBo.updateEmpsalaryacct(empsalaryacctversion.getEsavEsac());

            empsalaryacctversion.setEsavLastChangeTime(new Date());
            Date today = DateUtil.convDateTimeToDate(new Date());
            empsalaryacctversion.setEsavValidTo(DateUtil.dateAdd(today, -1));
            IEmpSalaryAcctversionBo esavBo = (IEmpSalaryAcctversionBo) SpringBeanFactory
                    .getBean("empsalaryacctversionBo");
            esavBo.updateEmpsalaryacctversion(empsalaryacctversion);

            String curempId = empsalaryacctversion.getEsavLastChangeBy();
            Empsalaryacct empsalaryacct = empsalaryacctversion.getEsavEsac();

            empsalaryacctversion = new Empsalaryacctversion();
            empsalaryacctversion.setEsavEsac(empsalaryacct);
            empsalaryacctversion.setEsavCreateBy(curempId);
            empsalaryacctversion.setEsavCreateTime(new Date());
            empsalaryacctversion.setEsavLastChangeBy(curempId);
            empsalaryacctversion.setEsavLastChangeTime(new Date());
            empsalaryacctversion.setEsavValidFrom(today);
            empsalaryacctversion.setEsavValidTo(null);
            esavBo.insertEmpsalaryacctversion(empsalaryacctversion);

            for (Empsalaryconfig cfg : salaryconfigList) {
                cfg.setEscEsavId(empsalaryacctversion);
            }

            ISalaryconfBo salaryconfBo = (ISalaryconfBo) SpringBeanFactory.getBean("salaryconfBo");
            salaryconfBo.saveOrupdate(salaryconfigList);

            for (Empsalaryacctitems nitms : newItems) {
                nitms.setEsaiEsav(empsalaryacctversion);
            }

            IEmpSalaryAcctitemsBo esaiBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                    .getBean("empsalaryacctitemsBo");
            esaiBo.saveOrupdate(newItems);

            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.valueOf(false);
    }

    public Map<String, Integer> getOldAcctItems(String esavId) {
        IEmpSalaryAcctitemsBo esaiBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        List oldItems = esaiBo.getItemsByAcctversion(esavId);

        Map oldMap = new HashMap();
        String dataDefId = new String();
        for (int i = 0; i < oldItems.size(); ++i) {
            dataDefId = ((Empsalaryacctitems) oldItems.get(i)).getEsaiEsdd().getEsddId();
            oldMap.put(dataDefId, Integer.valueOf(i));
        }
        return oldMap;
    }

    private void checkOrderMethod(DetachedCriteria detachedCriteria, String order) {
        if ((order != null) && (order.trim().length() > 1) && (order.indexOf('-') != -1)) {
            String[] pram = order.split("-");
            if ((pram[0] == null) || (pram[0].length() < 1)) {
                return;
            }
            String[] fetch = pram[0].split("\\.");
            String orde = pram[0];
            if (fetch.length > 1) {
                String str = "";
                String fetc = "";
                for (int len = 0; len < fetch.length - 1; ++len) {
                    if ("esavEsac".equals(fetc + fetch[len])) {
                        fetc = fetc + fetch[len] + ".";
                        str = "esac.";
                    } else {
                        detachedCriteria.createAlias(fetc + fetch[len], "ord" + len);
                        fetc = fetc + fetch[len] + ".";
                        str = "ord" + len + ".";
                    }
                }

                orde = str + pram[0].substring(pram[0].lastIndexOf(".") + 1);
            }
            if ((pram.length == 2) && (pram[1].equalsIgnoreCase("up")))
                detachedCriteria.addOrder(Order.asc(orde));
            else
                detachedCriteria.addOrder(Order.desc(orde));
        }
    }

    public List<Empsalaryacctversion> getObjects(Class<Empsalaryacctversion> clas, String[] fetch) {
        return this.empsalaryacctversionDao.getObjects(Empsalaryacctversion.class, fetch);
    }

    public List<Empsalaryacctversion> exeHqlList(String hql) {
        return this.empsalaryacctversionDao.exeHqlList(hql);
    }

    public Boolean insertEmpsalaryacctversion(Empsalaryacctversion empsalaryacctversion) {
        this.empsalaryacctversionDao.saveObject(empsalaryacctversion);
        return Boolean.valueOf(true);
    }

    public Empsalaryacctversion loadObject(String esavId, String[] fetch) {
        return (Empsalaryacctversion) this.empsalaryacctversionDao
                .loadObject(Empsalaryacctversion.class, esavId, fetch, null);
    }

    public Boolean updateEmpsalaryacctversion(Empsalaryacctversion empsalaryacctversion) {
        this.empsalaryacctversionDao.updateObject(empsalaryacctversion);
        return Boolean.valueOf(true);
    }

    public IEmpSalaryAcctversionDao getEmpsalaryacctversionDao() {
        return this.empsalaryacctversionDao;
    }

    public void setEmpsalaryacctversionDao(IEmpSalaryAcctversionDao empsalaryacctversionDao) {
        this.empsalaryacctversionDao = empsalaryacctversionDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.bo.EmpSalaryAcctversionBoImpl JD-Core Version: 0.5.4
 */