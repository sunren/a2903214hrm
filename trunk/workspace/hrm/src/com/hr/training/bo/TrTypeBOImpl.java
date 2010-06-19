package com.hr.training.bo;

import com.hr.training.dao.ITrTypeDAO;
import com.hr.training.domain.Trtype;
import java.util.List;

public class TrTypeBOImpl implements ITrTypeBO {
    private ITrTypeDAO trTypeDAO;

    public void save(Trtype trt) {
        this.trTypeDAO.saveObject(trt);
    }

    public void delete(Trtype trt) {
        this.trTypeDAO.deleteObject(trt);
    }

    public void delete(String trtNo) {
        this.trTypeDAO.deleteObject(Trtype.class, trtNo);
    }

    public Trtype load(String trtNo) {
        return (Trtype) this.trTypeDAO.loadObject(Trtype.class, trtNo, null, new boolean[0]);
    }

    public void update(Trtype trt) {
        this.trTypeDAO.saveOrupdate(trt);
    }

    public List loadAll() {
        return this.trTypeDAO.getObjects(Trtype.class, null);
    }

    public ITrTypeDAO getTrTypeDAO() {
        return this.trTypeDAO;
    }

    public void setTrTypeDAO(ITrTypeDAO trTypeDAO) {
        this.trTypeDAO = trTypeDAO;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.training.bo.TrTypeBOImpl JD-Core Version: 0.5.4
 */