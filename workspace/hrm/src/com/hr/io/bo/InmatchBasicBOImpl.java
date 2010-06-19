package com.hr.io.bo;

import com.hr.io.dao.IInmatchBasicDAO;
import com.hr.io.domain.Inmatch;
import com.hr.io.domain.InmatchBasic;
import com.hr.io.domain.Iodef;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class InmatchBasicBOImpl implements IInmatchBasicBO {
    private IInmatchBasicDAO dao;

    public IInmatchBasicDAO getDao() {
        return this.dao;
    }

    public void setDao(IInmatchBasicDAO dao) {
        this.dao = dao;
    }

    public InmatchBasic loadObject(String id) {
        return (InmatchBasic) this.dao.loadObject(InmatchBasic.class, id, null, new boolean[0]);
    }

    public boolean saveOrUpdateObject(InmatchBasic imb) {
        this.dao.saveOrupdate(imb);
        if (imb.getImList() != null) {
            this.dao.saveOrupdate(imb.getImList());
        }
        return true;
    }

    public boolean deleteObject(InmatchBasic imb) {
        if (imb.getImList() != null) {
            this.dao.deleteCollection(imb.getImList());
        }
        this.dao.deleteObject(imb);
        return true;
    }

    public List<InmatchBasic> getListByIodef(Iodef iodef) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InmatchBasic.class);
        detachedCriteria.add(Restrictions.eq(InmatchBasic.PROP_IMB_IO, iodef));
        detachedCriteria.addOrder(Order.asc(InmatchBasic.PROP_IMB_SORT_ID));
        List result = this.dao.findByCriteria(detachedCriteria);
        return result;
    }

    public List<Inmatch> createInmatch(Iodef iodef) {
        List imbList = getListByIodef(iodef);
        List result = new ArrayList();
        for (int i = 0; i < imbList.size(); ++i) {
            Inmatch imTmp = new Inmatch();
            InmatchBasic imbTmp = (InmatchBasic) imbList.get(i);
            imTmp.initByInmatchBasic(imbTmp);
            imTmp.setAllMaps();
            result.add(imTmp);
        }
        return result;
    }

    public void initInmatchBasicMaps(List<InmatchBasic> imbList) {
        for (int i = 0; i < imbList.size(); ++i)
            ((InmatchBasic) imbList.get(i)).setAllMaps();
    }

    public boolean updateImbImList(List<InmatchBasic> imbList, List<Inmatch> imList) {
        if ((imbList != null) && (imbList.size() > 0)) {
            this.dao.saveOrupdate(imbList);
        }
        if ((imList != null) && (imList.size() > 0)) {
            this.dao.saveOrupdate(imList);
        }
        return true;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.bo.InmatchBasicBOImpl JD-Core Version: 0.5.4
 */