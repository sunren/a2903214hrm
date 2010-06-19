package com.hr.configuration.bo;

import com.hr.configuration.dao.IAttdMachineDAO;
import com.hr.configuration.domain.AttdMachine;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class AttdMachineBOImpl implements IAttdMachineBO {
    private IAttdMachineDAO attdMachineDAO;

    public List FindAllAttdMachine() {
        DetachedCriteria dc = DetachedCriteria.forClass(AttdMachine.class);
        dc.addOrder(Order.asc("macSortId"));
        return this.attdMachineDAO.findByCriteria(dc);
    }

    public List<AttdMachine> FindEnabledAttdMachine() {
        DetachedCriteria dc = DetachedCriteria.forClass(AttdMachine.class);
        dc.add(Restrictions.eq(AttdMachine.PROP_MAC_STATUS, new Integer(1)));
        dc.addOrder(Order.asc("macSortId"));
        return this.attdMachineDAO.findByCriteria(dc);
    }

    public boolean addAttdMachine(AttdMachine attdMachine) {
        try {
            String trimmedAttdMachineNo = attdMachine.getMacNo().trim();
            List old = this.attdMachineDAO.exeHqlList(" from AttdMachine where macNo ='"
                    + trimmedAttdMachineNo + "'");

            if ((old == null) || (old.size() == 0)) {
                attdMachine.setMacNo(trimmedAttdMachineNo);
                this.attdMachineDAO.saveObject(attdMachine);

                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String delAttdMachine(Class<AttdMachine> name, String id) {
        StringBuffer msg = new StringBuffer();
        try {
            this.attdMachineDAO.deleteObject(AttdMachine.class, id);
        } catch (Exception e) {
            msg.append("系统异常＄1�7" + e.getMessage());
        }
        return msg.toString();
    }

    public AttdMachine loadAttdMachine(String macId) {
        Object ob = this.attdMachineDAO.loadObject(AttdMachine.class, macId, null, new boolean[0]);
        if (ob == null) {
            return null;
        }
        return (AttdMachine) ob;
    }

    public void saveAttdMachineSortIdByAttdMachine(String[] ids) {
        int length = ids.length;
        int sortId = 1;
        for (int i = 0; i < length; ++i) {
            this.attdMachineDAO.exeHql("update AttdMachine set macSortId=" + sortId
                    + " where id ='" + ids[i] + "'");

            ++sortId;
        }
    }

    public String updateAttdMachine(AttdMachine attdMachine) {
        String trimmedAttdMachineMacNo = attdMachine.getMacNo().trim();
        List old = this.attdMachineDAO.exeHqlList(" from AttdMachine where  macId != '"
                + attdMachine.getMacId() + "'  and macNo  ='" + trimmedAttdMachineMacNo + "'");

        if ((old == null) || (old.size() == 0)) {
            AttdMachine oldAttdMachine = (AttdMachine) this.attdMachineDAO
                    .loadObject(AttdMachine.class, attdMachine.getMacId().trim(), null,
                                new boolean[0]);
            oldAttdMachine.setMacNo(attdMachine.getMacNo());
            oldAttdMachine.setMacDesc(attdMachine.getMacDesc());
            oldAttdMachine.setMacIP(attdMachine.getMacIP());
            oldAttdMachine.setMacPort(attdMachine.getMacPort());
            oldAttdMachine.setMacPassword(attdMachine.getMacPassword());
            oldAttdMachine.setMacType(attdMachine.getMacType());
            oldAttdMachine.setMacStatus(attdMachine.getMacStatus());

            this.attdMachineDAO.updateObject(oldAttdMachine);

            return "0";
        }
        return "2";
    }

    public IAttdMachineDAO getAttdMachineDAO() {
        return this.attdMachineDAO;
    }

    public void setAttdMachineDAO(IAttdMachineDAO attdMachineDAO) {
        this.attdMachineDAO = attdMachineDAO;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.bo.AttdMachineBOImpl JD-Core Version: 0.5.4
 */