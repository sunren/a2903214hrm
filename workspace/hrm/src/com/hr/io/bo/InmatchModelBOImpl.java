package com.hr.io.bo;

import com.hr.io.dao.IInmatchModelDAO;
import com.hr.io.domain.InmatchModel;
import com.hr.io.domain.Iodef;
import java.util.Collection;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class InmatchModelBOImpl implements IInmatchModelBO {
    private IInmatchModelDAO dao;

    public IInmatchModelDAO getDao() {
        return this.dao;
    }

    public void setDao(IInmatchModelDAO dao) {
        this.dao = dao;
    }

    public InmatchModel loadObject(String id, String[] fetch) {
        return (InmatchModel) this.dao.loadObject(InmatchModel.class, id, fetch, new boolean[0]);
    }

    public boolean saveOrUpdate(InmatchModel imm) {
        this.dao.saveOrupdate(imm);
        if ((imm.getImList() != null) && (imm.getImList().size() > 0)) {
            this.dao.saveOrupdate(imm.getImList());
        }
        return true;
    }

    public boolean saveOrUpdate(Collection<InmatchModel> imms) {
        this.dao.saveOrupdate(imms);
        return true;
    }

    public boolean deleteInmatchModel(InmatchModel inmatchModel) {
        if ((inmatchModel.getImList() != null) && (inmatchModel.getImList().size() > 0)) {
            this.dao.deleteCollection(inmatchModel.getImList());
        }
        this.dao.deleteObject(inmatchModel);
        return true;
    }

    public InmatchModel loadDefaultObject(Iodef iodef) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InmatchModel.class);
        detachedCriteria.add(Restrictions.eq(InmatchModel.PROP_IMM_IO, iodef));
        detachedCriteria.add(Restrictions.eq(InmatchModel.PROP_IMM_DEFAULT, Integer.valueOf(1)));
        return (InmatchModel) this.dao.findByCriteria(detachedCriteria, 1, 1).get(0);
    }

    public InmatchModel getObject(Iodef iodef, String ommId) {
        InmatchModel result = loadObject(ommId, null);
        if ((result != null) && (result.getImmIo().getIoId().compareTo(iodef.getIoId()) == 0)) {
            return result;
        }
        return null;
    }

    public List<InmatchModel> getListByIodef(Iodef iodef) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InmatchModel.class);
        detachedCriteria.add(Restrictions.eq(InmatchModel.PROP_IMM_IO, iodef));
        detachedCriteria.addOrder(Order.asc(InmatchModel.PROP_IMM_NAME));
        List result = this.dao.findByCriteria(detachedCriteria);
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.bo.InmatchModelBOImpl JD-Core Version: 0.5.4
 */