package com.hr.io.bo;

import com.hr.io.dao.IodefDAOImpl;
import com.hr.io.domain.Iodef;
import com.hr.io.domain.Iomatch;
import com.hr.io.domain.Outmatch;
import com.hr.io.domain.OutmatchBasic;
import com.hr.util.Pager;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class IodefBoImpl implements IIodefBo {
    private IodefDAOImpl iodefdao;

    public IodefBoImpl() {
        this.iodefdao = null;
    }

    public Iodef loadObject(String id) {
        return loadObject(id, null);
    }

    public Iodef loadObjectByName(String name) {
        return (Iodef) this.iodefdao.loadObjectByName(Iodef.class, Iodef.PROP_IO_NAME, name, null,
                                                      new boolean[0]);
    }

    public Iodef loadObject(String id, String[] fetch) {
        return (Iodef) this.iodefdao.loadObject(Iodef.class, id, fetch, new boolean[0]);
    }

    public boolean saveObject(Iodef obj) {
        this.iodefdao.saveObject(obj);
        return true;
    }

    public boolean updateObject(Iodef obj) {
        this.iodefdao.updateObject(obj);
        return true;
    }

    public boolean deleteObject(String id) {
        this.iodefdao.deleteObject(Iodef.class, id);
        return true;
    }

    public List<Iodef> searchIodef(Iodef iodef, Pager pager) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Iodef.class);
        checkOrderMethod(detachedCriteria, pager.getOrder());
        if (iodef == null) {
            return this.iodefdao.findByCriteria(detachedCriteria);
        }
        if (iodef.getIoName() != null) {
            detachedCriteria.add(Restrictions.like("ioName", "%" + iodef.getIoName() + "%"));
        }
        if (iodef.getIoType() != null) {
            detachedCriteria.add(Restrictions.eq("ioType", iodef.getIoType()));
        }
        if (iodef.getIoMatchType() != null) {
            detachedCriteria.add(Restrictions.eq("ioMatchType", iodef.getIoMatchType()));
        }
        if (iodef.getIoModule() != null) {
            detachedCriteria.add(Restrictions.eq("ioModule", iodef.getIoModule()));
        }
        return this.iodefdao.findByCriteria(detachedCriteria);
    }

    public String insertIodef(Iodef iodef) {
        if (iodef != null) {
            iodef.setIoId(UUID.randomUUID().toString());
            this.iodefdao.saveObject(iodef);
        }
        return iodef.getIoId();
    }

    public String deletIodef(String iodefId) {
        String hql = "delete from Iomatch where iodef.ioId ='" + iodefId + "'";
        this.iodefdao.exeHql(hql);
        hql = "delete from Iodef where ioId='" + iodefId + "'";
        this.iodefdao.exeHql(hql);
        return iodefId;
    }

    public Iodef searchOneIodef(String iodefId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Iodef.class);
        detachedCriteria.setFetchMode("iomatchs", FetchMode.JOIN);
        detachedCriteria.add(Restrictions.eq("ioId", iodefId));
        List result = this.iodefdao.findByCriteria(detachedCriteria);
        if ((result != null) && (result.size() != 0))
            return (Iodef) result.get(0);
        return null;
    }

    public Iodef searchIodefByName(String iodefName) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Iodef.class);
        detachedCriteria.add(Restrictions.eq("ioName", iodefName));
        List result = this.iodefdao.findByCriteria(detachedCriteria);
        if ((result != null) && (result.size() != 0))
            return (Iodef) result.get(0);
        return null;
    }

    public String updateIodef(Iodef iodef) {
        Iodef iodefOld = searchOneIodef(iodef.getIoId());
        iodef.setIoCreateBy(iodefOld.getIoCreateBy());
        iodef.setIoCreateTime(iodefOld.getIoCreateTime());
        iodef.setIoIsExtend(iodefOld.getIoIsExtend());

        Set<Iomatch> matchs = iodef.getIomatchs();
        String matchIds = "";
        for (Iomatch iomatch : matchs) {
            matchIds = (("".equals(matchIds)) ? matchIds : new StringBuilder().append(matchIds)
                    .append("',").toString())
                    + "'" + iomatch.getIomatchId();
        }
        matchIds = matchIds + "'";
        String hql = "delete from Iomatch where iodef.ioId='" + iodef.getIoId()
                + "' and iomatchId not in (" + matchIds + ")";
        this.iodefdao.exeHql(hql);

        this.iodefdao.updateObject(iodef);
        return iodef.getIoId();
    }

    public IodefDAOImpl getIodefdao() {
        return this.iodefdao;
    }

    public void setIodefdao(IodefDAOImpl iodefdao) {
        this.iodefdao = iodefdao;
    }

    private void checkOrderMethod(DetachedCriteria detachedCriteria, String order) {
        if ((order != null) && (order.trim().length() > 1) && (order.indexOf('-') != -1)) {
            String[] pram = order.split("-");
            if ((pram[0] == null) || (pram[0].length() < 1)) {
                return;
            }
            String[] fetch = pram[0].split("\\.");
            String orde = pram[0];
            if ((pram.length == 2) && (pram[1].equalsIgnoreCase("up")))
                detachedCriteria.addOrder(Order.asc(orde));
            else
                detachedCriteria.addOrder(Order.desc(orde));
        }
    }

    public boolean updateIodef(Iodef iodef, List<OutmatchBasic> ombList, List<Outmatch> omList) {
        this.iodefdao.updateObject(iodef);
        this.iodefdao.saveOrupdate(ombList);
        this.iodefdao.saveOrupdate(omList);
        return true;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.bo.IodefBoImpl JD-Core Version: 0.5.4
 */