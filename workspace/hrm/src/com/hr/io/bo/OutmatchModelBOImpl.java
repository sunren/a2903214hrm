package com.hr.io.bo;

import com.hr.io.dao.IOutmatchModelDAO;
import com.hr.io.domain.Iodef;
import com.hr.io.domain.Outmatch;
import com.hr.io.domain.OutmatchModel;
import java.util.Collection;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class OutmatchModelBOImpl implements IOutmatchModelBO {
    private IOutmatchModelDAO dao;

    public IOutmatchModelDAO getDao() {
        return this.dao;
    }

    public void setDao(IOutmatchModelDAO dao) {
        this.dao = dao;
    }

    public OutmatchModel loadObject(String id) {
        return loadObject(id, null);
    }

    public OutmatchModel loadObject(String id, String[] fetch) {
        return (OutmatchModel) this.dao.loadObject(OutmatchModel.class, id, fetch, new boolean[0]);
    }

    public OutmatchModel loadDefaultObject(Iodef iodef) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OutmatchModel.class);
        detachedCriteria.add(Restrictions.eq(OutmatchModel.PROP_OMM_IO, iodef));
        detachedCriteria.add(Restrictions.eq(OutmatchModel.PROP_OMM_DEFAULT, Integer.valueOf(1)));
        return (OutmatchModel) this.dao.findByCriteria(detachedCriteria, 1, 1).get(0);
    }

    public OutmatchModel getObject(Iodef iodef, String ommId) {
        OutmatchModel result = loadObject(ommId);
        if ((result != null) && (result.getOmmIo().getIoId().compareTo(iodef.getIoId()) == 0)) {
            return result;
        }
        return null;
    }

    public boolean saveObject(OutmatchModel obj) {
        this.dao.saveObject(obj);
        return true;
    }

    public boolean updateObject(OutmatchModel obj) {
        this.dao.updateObject(obj);
        return true;
    }

    public boolean saveOrUpdateOutmatchModel(OutmatchModel omm, List<Outmatch> omDelList) {
        this.dao.saveOrupdate(omm);
        if ((omDelList != null) && (omDelList.size() > 0)) {
            this.dao.deleteCollection(omDelList);
        }
        if ((omm.getOmList() != null) && (omm.getOmList().size() > 0)) {
            this.dao.saveOrupdate(omm.getOmList());
        }

        return true;
    }

    public boolean saveOrUpdate(Collection<OutmatchModel> outmatchModels) {
        this.dao.saveOrupdate(outmatchModels);
        return true;
    }

    public boolean deleteObject(String id) {
        this.dao.deleteObject(OutmatchModel.class, id);
        return true;
    }

    public boolean deleteOutmatchModel(OutmatchModel outmatchModel) {
        this.dao.deleteCollection(outmatchModel.getOmList());
        this.dao.deleteObject(outmatchModel);
        return true;
    }

    public List<OutmatchModel> getListByIodef(Iodef iodef) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OutmatchModel.class);
        detachedCriteria.add(Restrictions.eq(OutmatchModel.PROP_OMM_IO, iodef));
        detachedCriteria.addOrder(Order.asc(OutmatchModel.PROP_OMM_NAME));
        List result = this.dao.findByCriteria(detachedCriteria);
        return result;
    }

    public List<OutmatchModel> getListByIodef(String ioName) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OutmatchModel.class);
        detachedCriteria.setFetchMode(OutmatchModel.PROP_OMM_IO, FetchMode.JOIN);
        detachedCriteria.createAlias(OutmatchModel.PROP_OMM_IO, "ommIo");
        detachedCriteria.add(Restrictions.eq("ommIo.ioName", ioName));
        List result = this.dao.findByCriteria(detachedCriteria);
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.bo.OutmatchModelBOImpl JD-Core Version: 0.5.4
 */