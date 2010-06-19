package com.hr.io.bo;

import com.hr.io.dao.IOutmatchDAO;
import com.hr.io.domain.Iodef;
import com.hr.io.domain.Outmatch;
import com.hr.io.domain.OutmatchBasic;
import com.hr.io.domain.OutmatchModel;
import com.hr.util.output.FieldOperate;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class OutmatchBOImpl implements IOutmatchBO {
    private IOutmatchDAO dao;

    public IOutmatchDAO getDao() {
        return this.dao;
    }

    public void setDao(IOutmatchDAO dao) {
        this.dao = dao;
    }

    public Outmatch loadObject(String id) {
        return loadObject(id, null);
    }

    public Outmatch loadObject(String id, String[] fetch) {
        return (Outmatch) this.dao.loadObject(Outmatch.class, id, fetch, new boolean[0]);
    }

    public boolean saveObject(Outmatch obj) {
        this.dao.saveObject(obj);
        return true;
    }

    public boolean updateObject(Outmatch obj) {
        this.dao.updateObject(obj);
        return true;
    }

    public boolean saveOrUpdateList(List<Outmatch> list) {
        this.dao.saveOrupdate(list);
        return true;
    }

    public boolean deleteObject(String id) {
        this.dao.deleteObject(Outmatch.class, id);
        return true;
    }

    public Iodef loadIodefByName(String name) {
        return (Iodef) this.dao.loadObjectByName(Iodef.class, Iodef.PROP_IO_NAME, name, null,
                                                 new boolean[0]);
    }

    public List<Outmatch> getOutmatchList(OutmatchModel outmatchModel) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Outmatch.class);
        detachedCriteria.setFetchMode(Outmatch.PROP_OM_OMB, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Outmatch.PROP_OM_OMM, outmatchModel));
        detachedCriteria.addOrder(Order.asc(Outmatch.PROP_OM_SORT_ID));
        List result = this.dao.findByCriteria(detachedCriteria);
        return result;
    }

    public List<Outmatch> getFullOutmatchList(OutmatchModel outmatchModel) {
        List<Outmatch> result = getOutmatchList(outmatchModel);

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OutmatchBasic.class);
        detachedCriteria.add(Restrictions.eq(OutmatchBasic.PROP_OMB_IO, outmatchModel.getOmmIo()));
        detachedCriteria.addOrder(Order.asc(OutmatchBasic.PROP_OMB_SORT_ID));
        List<OutmatchBasic> ombList = this.dao.findByCriteria(detachedCriteria);

        for (Outmatch omTmp : result) {
            ombList.remove(omTmp.getOmOmb());
            omTmp.setOmIsOutput(Integer.valueOf(1));
        }

        int sortId = ((Outmatch) result.get(result.size() - 1)).getOmSortId().intValue() + 1;
        for (OutmatchBasic ombTmp : ombList) {
            Outmatch omTmp = new Outmatch();
            omTmp.initByOutmatchBasic(ombTmp);
            omTmp.setOmSortId(Integer.valueOf(sortId++));
            omTmp.setOmIsOutput(Integer.valueOf(0));
            result.add(omTmp);
        }
        return result;
    }

    public List<Outmatch> getOutmatchListOrderById(OutmatchModel outmatchModel) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Outmatch.class);
        detachedCriteria.setFetchMode(Outmatch.PROP_OM_OMB, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Outmatch.PROP_OM_OMM, outmatchModel));
        detachedCriteria.addOrder(Order.asc(Outmatch.PROP_OM_ID));
        List result = this.dao.findByCriteria(detachedCriteria);
        return result;
    }

    public void initOutmatchMaps(List<Outmatch> omList) {
        for (int i = 0; i < omList.size(); ++i)
            ((Outmatch) omList.get(i)).setAllMaps();
    }

    public List<FieldOperate> getOutputList(OutmatchModel outmatchModel) {
        List result = new ArrayList();
        List omList = getOutmatchList(outmatchModel);
        if ((omList == null) || (omList.size() < 1))
            return result;
        for (int i = 0; i < omList.size(); ++i) {
            Outmatch omTmp = (Outmatch) omList.get(i);
            int groupOrder = omTmp.getOmIsGroup().intValue();
            if (groupOrder != 0) {
                groupOrder = (2 - (groupOrder - 1) / 2) * (groupOrder % 2 * 2 - 1);
            }
            result.add(new FieldOperate(omTmp.getOmOmb().getOmbFieldName(), omTmp.getOmFieldDesc(),
                    omTmp.getOmOmb().getOmbDataType(), omTmp.getOmFormat(), omTmp.getOmStatistic(),
                    omTmp.getOmColumnWidth().intValue(), groupOrder));
        }

        return result;
    }

    public List<Outmatch> getOutmatchListByOmb(String ombId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Outmatch.class);
        detachedCriteria.add(Restrictions.eq(Outmatch.PROP_OM_OMB, new OutmatchBasic(ombId)));
        List result = this.dao.findByCriteria(detachedCriteria);
        return result;
    }

    public List<Outmatch> getOmListByOmbFieldNameArr(String[] ombFieldNameArr) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Outmatch.class);
        detachedCriteria.createAlias(Outmatch.PROP_OM_OMB, "omOmb", 1);
        detachedCriteria.add(Restrictions.in("omOmb.ombFieldName", ombFieldNameArr));
        List result = this.dao.findByCriteria(detachedCriteria);

        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.bo.OutmatchBOImpl JD-Core Version: 0.5.4
 */