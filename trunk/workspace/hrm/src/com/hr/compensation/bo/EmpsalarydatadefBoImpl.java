package com.hr.compensation.bo;

import com.hr.compensation.dao.IEmpsalarydatadefDao;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.io.bo.IIodefBo;
import com.hr.io.bo.IOutmatchBO;
import com.hr.io.bo.IOutmatchBasicBO;
import com.hr.io.bo.IOutmatchModelBO;
import com.hr.io.domain.Iodef;
import com.hr.io.domain.Outmatch;
import com.hr.io.domain.OutmatchBasic;
import com.hr.io.domain.OutmatchModel;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class EmpsalarydatadefBoImpl implements IEmpsalarydatadefBo {
    static final String OSALARYPAY = "OEmpSalaryPay";
    static final String OBENEFITHIS = "OEmpbenefitHistory";
    static final String[] SALARYIODEFS = { "OEmpSalaryPay", "OEmpbenefitHistory" };
    IEmpsalarydatadefDao empsalarydatadefDao;

    public List<Empsalarydatadef> searchByType(Integer type) {
        if (type == null) {
            return null;
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarydatadef.class);
        detachedCriteria.add(Restrictions.eq("esddDataType", type));
        return this.empsalarydatadefDao.findByCriteria(detachedCriteria);
    }

    public Empsalarydatadef searchById(String esddId) {
        if (esddId == null) {
            return null;
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarydatadef.class);
        detachedCriteria.add(Restrictions.eq("esddId", esddId));
        List list = this.empsalarydatadefDao.findByCriteria(detachedCriteria);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return (Empsalarydatadef) list.get(0);
    }

    public boolean delete(String salaryDataDefId) {
        try {
            String hql = "delete from Empsalarydatadef where esddId='" + salaryDataDefId + "'";
            this.empsalarydatadefDao.exeHql(hql);

            IOutmatchBasicBO outmatchBasicBO = (IOutmatchBasicBO) SpringBeanFactory
                    .getBean("outmatchBasicBO");
            String ombIds = outmatchBasicBO.getOmbListByFieldName(salaryDataDefId);
            if (ombIds != null) {
                String deleteOmHql = "delete from Outmatch where omOmb.ombId in (" + ombIds + ")";
                String deleteOmbHql = "delete from OutmatchBasic where ombFieldName='outPutList."
                        + salaryDataDefId + "'";
                this.empsalarydatadefDao.exeHql(deleteOmHql);
                this.empsalarydatadefDao.exeHql(deleteOmbHql);
            }
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean insert(Empsalarydatadef salaryDataDef) {
        salaryDataDef.setEsddSortId(Integer.valueOf(getMaxSortIdEsdd().intValue() + 1));
        this.empsalarydatadefDao.saveObject(salaryDataDef);

        if (salaryDataDef.getEsddDataOutput().intValue() == 0) {
            return true;
        }
        if ((10 <= salaryDataDef.getEsddDataType().intValue())
                && (salaryDataDef.getEsddDataType().intValue() <= 16)) {
            Boolean result = Boolean
                    .valueOf(insertOmbAndOm("OEmpSalaryPay",
                                            new Empsalarydatadef[] { salaryDataDef }));
            if (result.booleanValue())
                ;
            return insertOmbAndOm("OEmpbenefitHistory", new Empsalarydatadef[] { salaryDataDef });
        }
        return insertOmbAndOm("OEmpSalaryPay", new Empsalarydatadef[] { salaryDataDef });
    }

    public boolean update(Empsalarydatadef salaryDataDef) {
        if ((salaryDataDef.getEsddId() == null) || (salaryDataDef.getEsddId().equals(""))) {
            return false;
        }

        try {
            this.empsalarydatadefDao.updateObject(salaryDataDef);

            IOutmatchBasicBO outmatchBasicBO = (IOutmatchBasicBO) SpringBeanFactory
                    .getBean("outmatchBasicBO");
            String ombIds = outmatchBasicBO.getOmbListByFieldName(salaryDataDef.getEsddId());

            if (salaryDataDef.getEsddDataOutput().intValue() == 0) {
                if (ombIds == null)
                    return true;

                String deleteOmHql = "delete from Outmatch where omOmb.ombId in (" + ombIds + ")";
                String deleteOmbHql = "delete from OutmatchBasic where ombFieldName='outPutList."
                        + salaryDataDef.getEsddId() + "'";

                this.empsalarydatadefDao.exeHql(deleteOmHql);
                this.empsalarydatadefDao.exeHql(deleteOmbHql);
                return true;
            }

            if (ombIds == null) {
                if ((10 <= salaryDataDef.getEsddDataType().intValue())
                        && (salaryDataDef.getEsddDataType().intValue() <= 16)) {
                    Boolean result = Boolean
                            .valueOf(insertOmbAndOm("OEmpSalaryPay",
                                                    new Empsalarydatadef[] { salaryDataDef }));
                    if (result.booleanValue())
                        ;
                    return insertOmbAndOm("OEmpbenefitHistory",
                                          new Empsalarydatadef[] { salaryDataDef });
                }
                return insertOmbAndOm("OEmpSalaryPay", new Empsalarydatadef[] { salaryDataDef });
            }

            String updateOmHql = "update Outmatch set omFieldDesc='" + salaryDataDef.getEsddName()
                    + "' where omOmb.ombId in (" + ombIds + ")";

            String updateOmbHql = "update OutmatchBasic set ombFieldDesc='"
                    + salaryDataDef.getEsddName() + "' where ombFieldName='outPutList."
                    + salaryDataDef.getEsddId() + "'";

            this.empsalarydatadefDao.exeHql(updateOmHql);
            this.empsalarydatadefDao.exeHql(updateOmbHql);

            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean saveSort(String[] ids) {
        if (ids == null) {
            return false;
        }

        List<Empsalarydatadef> esddList = searchEsddByIdArr(ids);

        Map sortIdMap = new HashMap();
        for (int i = 0; i < ids.length; ++i) {
            Integer sortId = new Integer(i + 1);
            sortIdMap.put(ids[i], sortId);
        }

        Map fieldSortIdMap = new HashMap();
        for (Empsalarydatadef esdd : esddList) {
            Integer sortId = (Integer) sortIdMap.get(esdd.getEsddId());
            esdd.setEsddSortId(sortId);
            if (esdd.getEsddDataOutput().intValue() == 1) {
                fieldSortIdMap.put("outPutList." + esdd.getEsddId(), sortId);
            }
        }

        this.empsalarydatadefDao.saveOrupdate(esddList);

        Boolean result = Boolean.valueOf(savesortOmbAndOm("OEmpSalaryPay", fieldSortIdMap));
        return (result.booleanValue()) && (savesortOmbAndOm("OEmpbenefitHistory", fieldSortIdMap));
    }

    private boolean savesortOmbAndOm(String iodefName, Map<String, Integer> fieldSortIdMap) {
        IIodefBo iodefBo = (IIodefBo) SpringBeanFactory.getBean("iodefbo");
        Iodef iodef = iodefBo.searchIodefByName(iodefName);
        if (iodef == null)
            return true;

        String[] ombFieldNameArr = new String[fieldSortIdMap.size()];
        int index = 0;
        for (String dataKey : fieldSortIdMap.keySet()) {
            ombFieldNameArr[index] = dataKey;
            ++index;
        }

        Integer offset = getMaxSortIdOmb(iodef.getIoId(), "outPutList");

        IOutmatchBasicBO outmatchBasicBO = (IOutmatchBasicBO) SpringBeanFactory
                .getBean("outmatchBasicBO");
        List<OutmatchBasic> ombList = outmatchBasicBO.getOmbListByFieldNameArr(iodefName,
                                                                               ombFieldNameArr);
        for (OutmatchBasic omb : ombList) {
            omb.setOmbSortId(Integer.valueOf(((Integer) fieldSortIdMap.get(omb.getOmbFieldName()))
                    .intValue()
                    + offset.intValue()));
        }

        IOutmatchBO outmatchBO = (IOutmatchBO) SpringBeanFactory.getBean("outmatchBO");
        List<Outmatch> omList = outmatchBO.getOmListByOmbFieldNameArr(ombFieldNameArr);
        for (Outmatch om : omList)
            om.setOmSortId(Integer.valueOf(((Integer) fieldSortIdMap.get(om.getOmOmb()
                    .getOmbFieldName())).intValue()
                    + offset.intValue()));
        try {
            this.empsalarydatadefDao.saveOrupdate(ombList);
            this.empsalarydatadefDao.saveOrupdate(omList);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean batchRefreshOMConfig() {
        IOutmatchBasicBO outmatchBasicBO = (IOutmatchBasicBO) SpringBeanFactory
                .getBean("outmatchBasicBO");
        String ombIds = outmatchBasicBO.getOmbListByFieldName(null);
        if (ombIds != null) {
            String deleteOmHql = "delete from Outmatch where omOmb.ombId in (" + ombIds + ")";
            String deleteOmbHql = "delete from OutmatchBasic where ombFieldName like 'outPutList.%'";
            this.empsalarydatadefDao.exeHql(deleteOmHql);
            this.empsalarydatadefDao.exeHql(deleteOmbHql);
        }

        List<Empsalarydatadef> esddList = searchAll(Integer.valueOf(1));
        if (esddList.size() == 0)
            return true;
        Boolean result = Boolean
                .valueOf(insertOmbAndOm("OEmpSalaryPay", (Empsalarydatadef[]) esddList
                        .toArray(new Empsalarydatadef[esddList.size()])));

        List esddBenefitList = new ArrayList();
        for (Empsalarydatadef esdd : esddList) {
            if ((esdd.getEsddDataType().intValue() >= 10)
                    && (esdd.getEsddDataType().intValue() <= 16))
                esddBenefitList.add(esdd);
        }
        if (esddBenefitList.size() == 0)
            return true;

        return (result.booleanValue())
                && (insertOmbAndOm("OEmpbenefitHistory", (Empsalarydatadef[]) esddBenefitList
                        .toArray(new Empsalarydatadef[esddBenefitList.size()])));
    }

    private boolean insertOmbAndOm(String iodefName, Empsalarydatadef[] esddArray) {
        if ((esddArray == null) || (esddArray.length == 0))
            return false;

        IIodefBo iodefBo = (IIodefBo) SpringBeanFactory.getBean("iodefbo");
        Iodef iodef = iodefBo.searchIodefByName(iodefName);
        if (iodef == null) {
            return true;
        }

        IOutmatchModelBO outmatchModelBO = (IOutmatchModelBO) SpringBeanFactory
                .getBean("outmatchModelBO");
        List<OutmatchModel> ommList = outmatchModelBO.getListByIodef(iodef);

        Integer offset = getMaxSortIdOmb(iodef.getIoId(), "outPutList");

        List ombList = new ArrayList();
        List omList = new ArrayList();
        OutmatchBasic omb;
        for (Empsalarydatadef esdd : esddArray) {
            omb = new OutmatchBasic(null, iodef, "outPutList." + esdd.getEsddId(), new Integer(0),
                    "decimal", esdd.getEsddName(), Integer.valueOf(esdd.getEsddSortId().intValue()
                            + offset.intValue()), new Integer(15));

            omb.setOmbFormat("2");
            ombList.add(omb);

            for (OutmatchModel omm : ommList) {
                Outmatch om = new Outmatch();
                om.initByOutmatchBasic(omb);
                om.setOmOmm(omm);
                omList.add(om);
            }
        }

        try {
            this.empsalarydatadefDao.saveOrupdate(ombList);
            if ((omList != null) && (omList.size() != 0))
                this.empsalarydatadefDao.saveOrupdate(omList);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private List<Empsalarydatadef> searchAll(Integer esddDataOutput) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarydatadef.class);
        detachedCriteria.add(Restrictions.eq("esddDataOutput", esddDataOutput));
        detachedCriteria.addOrder(Order.asc("esddSortId"));
        List list = this.empsalarydatadefDao.findByCriteria(detachedCriteria);
        return list;
    }

    private List<Empsalarydatadef> searchAll(Integer esddDataOutput, Integer typeLow,
            Integer typeHigh) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarydatadef.class);
        detachedCriteria.add(Restrictions.eq("esddDataOutput", esddDataOutput));
        detachedCriteria.add(Restrictions.ge("esddDataType", typeLow));
        detachedCriteria.add(Restrictions.le("esddDataType", typeHigh));
        detachedCriteria.addOrder(Order.asc("esddSortId"));
        List list = this.empsalarydatadefDao.findByCriteria(detachedCriteria);
        return list;
    }

    public List<Empsalarydatadef> searchAll() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarydatadef.class);
        detachedCriteria.addOrder(Order.asc("esddSortId"));
        List list = this.empsalarydatadefDao.findByCriteria(detachedCriteria);
        return list;
    }

    public List<Empsalarydatadef> findByName(String name) {
        DetachedCriteria dc = DetachedCriteria.forClass(Empsalarydatadef.class);
        dc.add(Restrictions.eq("esddName", name.trim()));
        List list = this.empsalarydatadefDao.findByCriteria(dc);
        return list;
    }

    public List<Empsalarydatadef> findSameName(Empsalarydatadef def) {
        DetachedCriteria dc = DetachedCriteria.forClass(Empsalarydatadef.class);
        dc.add(Restrictions.eq("esddName", def.getEsddName().trim()));
        dc.add(Restrictions.ne("esddId", def.getEsddId().trim()));
        List list = this.empsalarydatadefDao.findByCriteria(dc);
        return list;
    }

    public List<Empsalarydatadef> findOutput(Integer status) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarydatadef.class);
        detachedCriteria.add(Restrictions.eq("esddDataOutput", status));
        detachedCriteria.addOrder(Order.asc("esddSortId"));
        List list = this.empsalarydatadefDao.findByCriteria(detachedCriteria);
        return list;
    }

    public List<Empsalarydatadef> getBenedatadefs() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarydatadef.class);
        detachedCriteria.add(Restrictions.eq("esddDataOutput", new Integer(1)));

        detachedCriteria.add(Restrictions.ge("esddDataType", new Integer(10)));
        detachedCriteria.add(Restrictions.le("esddDataType", new Integer(16)));
        detachedCriteria.addOrder(Order.asc("esddSortId"));
        List list = this.empsalarydatadefDao.findByCriteria(detachedCriteria);
        return list;
    }

    public Integer getMaxSortIdEsdd() {
        String hql = "select max(esddSortId) from Empsalarydatadef";
        List list = this.empsalarydatadefDao.exeHqlList(hql);
        Integer maxID = (Integer) list.get(0);

        return Integer.valueOf((maxID == null) ? 1 : maxID.intValue());
    }

    public Integer getMaxSortIdOmb(String ioDefId, String outPutList) {
        String hql = "select max(ombSortId) from OutmatchBasic where ombIo.ioId = '" + ioDefId
                + "' and ombFieldName not like '" + outPutList + ".%'";

        List list = this.empsalarydatadefDao.exeHqlList(hql);
        Integer intMax = (Integer) list.get(0);
        return Integer.valueOf((intMax == null) ? 0 : intMax.intValue());
    }

    public List<Empsalarydatadef> searchEsddByIdArr(String[] idArr) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empsalarydatadef.class);
        detachedCriteria.add(Restrictions.in("esddId", idArr));
        List list = this.empsalarydatadefDao.findByCriteria(detachedCriteria);
        return list;
    }

    private String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public IEmpsalarydatadefDao getEmpsalarydatadefDao() {
        return this.empsalarydatadefDao;
    }

    public void setEmpsalarydatadefDao(IEmpsalarydatadefDao empsalarydatadefDao) {
        this.empsalarydatadefDao = empsalarydatadefDao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.bo.EmpsalarydatadefBoImpl JD-Core Version: 0.5.4
 */