package com.hr.io.bo;

import com.hr.io.dao.IOutmatchBasicDAO;
import com.hr.io.domain.Iodef;
import com.hr.io.domain.Outmatch;
import com.hr.io.domain.OutmatchBasic;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class OutmatchBasicBOImpl implements IOutmatchBasicBO {
    private IOutmatchBasicDAO dao;

    public IOutmatchBasicDAO getDao() {
        return this.dao;
    }

    public void setDao(IOutmatchBasicDAO dao) {
        this.dao = dao;
    }

    public OutmatchBasic loadObject(String id) {
        return loadObject(id, null);
    }

    public OutmatchBasic loadObject(String id, String[] fetch) {
        return (OutmatchBasic) this.dao.loadObject(OutmatchBasic.class, id, fetch, new boolean[0]);
    }

    public OutmatchBasic loadObject(Iodef iodef, String name) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OutmatchBasic.class);
        detachedCriteria.add(Restrictions.eq(OutmatchBasic.PROP_OMB_IO, iodef));
        detachedCriteria.add(Restrictions.eq(OutmatchBasic.PROP_OMB_FIELD_NAME, name));
        List result = this.dao.findByCriteria(detachedCriteria);
        if ((result != null) && (result.size() != 1))
            return null;
        return (OutmatchBasic) result.get(0);
    }

    public boolean saveObject(OutmatchBasic obj) {
        this.dao.saveObject(obj);
        return true;
    }

    public boolean saveOrUpdateObject(OutmatchBasic omb) {
        this.dao.saveOrupdate(omb);
        if (omb.getOmList() != null) {
            this.dao.saveOrupdate(omb.getOmList());
        }
        return true;
    }

    public boolean updateObject(OutmatchBasic obj) {
        this.dao.updateObject(obj);
        return true;
    }

    public boolean deleteObject(OutmatchBasic omb) {
        if (omb.getOmList() != null) {
            this.dao.deleteCollection(omb.getOmList());
        }
        this.dao.deleteObject(omb);
        return true;
    }

    public List<OutmatchBasic> getListByIodef(Iodef iodef) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OutmatchBasic.class);
        detachedCriteria.add(Restrictions.eq(OutmatchBasic.PROP_OMB_IO, iodef));
        detachedCriteria.addOrder(Order.asc(OutmatchBasic.PROP_OMB_SORT_ID));
        List result = this.dao.findByCriteria(detachedCriteria);
        return result;
    }

    public List<Outmatch> createOutmatch(Iodef iodef) {
        List ombList = getListByIodef(iodef);
        List result = new ArrayList();
        for (int i = 0; i < ombList.size(); ++i) {
            Outmatch omTmp = new Outmatch();
            OutmatchBasic ombTmp = (OutmatchBasic) ombList.get(i);
            omTmp.initByOutmatchBasic(ombTmp);
            omTmp.setAllMaps();
            result.add(omTmp);
        }
        return result;
    }

    public void initOutmatchBasicMaps(List<OutmatchBasic> ombList) {
        for (int i = 0; i < ombList.size(); ++i)
            ((OutmatchBasic) ombList.get(i)).setAllMaps();
    }

    public boolean updateOmbOmList(List<OutmatchBasic> ombList, List<Outmatch> omList) {
        if ((ombList != null) && (ombList.size() > 0)) {
            this.dao.saveOrupdate(ombList);
        }
        if ((omList != null) && (omList.size() > 0)) {
            this.dao.saveOrupdate(omList);
        }
        return true;
    }

    public List<OutmatchBasic> getOmbListByFieldNameArr(String ioName, String[] fieldNameArr) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OutmatchBasic.class);

        if (fieldNameArr != null)
            detachedCriteria.add(Restrictions.in(OutmatchBasic.PROP_OMB_FIELD_NAME, fieldNameArr));
        else {
            detachedCriteria.add(Restrictions.like(OutmatchBasic.PROP_OMB_FIELD_NAME,
                                                   "outPutList.%"));
        }
        detachedCriteria.setFetchMode(OutmatchBasic.PROP_OMB_IO, FetchMode.JOIN);
        detachedCriteria.createAlias(OutmatchBasic.PROP_OMB_IO, "ombIo");
        detachedCriteria.add(Restrictions.eq("ombIo.ioName", ioName));
        List result = this.dao.findByCriteria(detachedCriteria);
        return result;
    }

    public String getOmbListByFieldName(String fieldName) {
        String updateOmbHql;
        if (fieldName != null)
            updateOmbHql = "select ombId from OutmatchBasic where ombFieldName='outPutList."
                    + fieldName + "'";
        else {
            updateOmbHql = "select ombId from OutmatchBasic where ombFieldName like 'outPutList.%'";
        }
        List<String> ombIdList = this.dao.exeHqlList(updateOmbHql);

        if ((ombIdList == null) || (ombIdList.size() == 0)) {
            return null;
        }
        StringBuffer OmbIds_b = new StringBuffer("");
        for (String ombId : ombIdList) {
            OmbIds_b.append("'").append(ombId).append("',");
        }
        String omBIds = OmbIds_b.toString().substring(0, OmbIds_b.length() - 1);
        return omBIds;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.bo.OutmatchBasicBOImpl JD-Core Version: 0.5.4
 */