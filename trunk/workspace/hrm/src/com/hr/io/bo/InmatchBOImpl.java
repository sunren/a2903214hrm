package com.hr.io.bo;

import com.hr.io.dao.IInmatchDAO;
import com.hr.io.domain.Inmatch;
import com.hr.io.domain.InmatchBasic;
import com.hr.io.domain.InmatchModel;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class InmatchBOImpl implements IInmatchBO {
    private IInmatchDAO dao;

    public IInmatchDAO getDao() {
        return this.dao;
    }

    public void setDao(IInmatchDAO dao) {
        this.dao = dao;
    }

    public void initInmatchMaps(List<Inmatch> imList) {
        for (int i = 0; i < imList.size(); ++i)
            ((Inmatch) imList.get(i)).setAllMaps();
    }

    public List<Inmatch> getInmatchList(InmatchModel inmatchModel) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inmatch.class);
        detachedCriteria.setFetchMode(Inmatch.PROP_IM_IMB, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Inmatch.PROP_IM_IMM, inmatchModel));
        detachedCriteria.addOrder(Order.asc(Inmatch.PROP_IM_SORT_ID));
        List result = this.dao.findByCriteria(detachedCriteria);
        if ((result != null) && (result.size() > 0))
            return result;
        return null;
    }

    public List<Inmatch> getInmatchListByImb(String imbId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inmatch.class);
        detachedCriteria.add(Restrictions.eq(Inmatch.PROP_IM_IMB, new InmatchBasic(imbId)));
        List result = this.dao.findByCriteria(detachedCriteria);
        return result;
    }

    public List<Inmatch> getInputList(InmatchModel inmatchModel) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Inmatch.class);
        detachedCriteria.setFetchMode(Inmatch.PROP_IM_IMB, FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq(Inmatch.PROP_IM_IMM, inmatchModel));
        detachedCriteria.add(Restrictions.eq(Inmatch.PROP_IM_IS_INPUT, Integer.valueOf(1)));
        detachedCriteria.addOrder(Order.asc(Inmatch.PROP_IM_SORT_ID));
        List result = this.dao.findByCriteria(detachedCriteria);
        if ((result != null) && (result.size() > 0))
            return result;
        return null;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.bo.InmatchBOImpl JD-Core Version: 0.5.4
 */