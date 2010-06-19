package com.hr.profile.bo;

import com.hr.profile.dao.IEmpAddConfDAO;
import com.hr.profile.domain.Empaddconf;
import java.util.LinkedList;
import java.util.List;

public class EmpAddConfBoImpl implements IEmpAddConfBo {
    private static final int NUMBER = 16;
    IEmpAddConfDAO empAddConfDAO;

    public List<Empaddconf> listAll() {
        String hql = "from Empaddconf order by eadcSeq";
        List results = this.empAddConfDAO.exeHqlList(hql);
        return results;
    }

    public List<Empaddconf> listByTable(String tableName) {
        String hql = "from Empaddconf where eadcTableName = '" + tableName + "' order by eadcSeq";
        List results = this.empAddConfDAO.exeHqlList(hql);
        return results;
    }

    public void deleteEmpaddconf(String id) {
        this.empAddConfDAO.deleteObject(Empaddconf.class, id);
    }

    public void insertEmpaddconf(Empaddconf conf) {
        this.empAddConfDAO.saveObject(conf);
    }

    public Empaddconf loadEmpaddconf(String id) {
        Empaddconf conf = (Empaddconf) this.empAddConfDAO.loadObject(Empaddconf.class, id, null,
                                                                     new boolean[0]);
        return conf;
    }

    public List<Integer> listAllActiveFields() {
        List fields = new LinkedList();
        for (int i = 1; i <= 16; ++i) {
            fields.add(Integer.valueOf(i));
        }

        List fieldList = null;

        String hql = "select eadcSeq from Empaddconf";
        fieldList = this.empAddConfDAO.exeHqlList("select eadcSeq from Empaddconf");

        if (fieldList != null) {
            int size = fieldList.size();
            for (int i = 0; i < size; ++i) {
                fields.remove(fieldList.get(i));
            }
        }
        return fields;
    }

    public List<Integer> listAllActiveFieldsByTable(String tableName, Integer number) {
        List fields = new LinkedList();
        for (int i = 1; i <= number.intValue(); ++i) {
            fields.add(Integer.valueOf(i));
        }

        List fieldList = null;

        String hql = "select eadcSeq from Empaddconf where eadcTableName = '" + tableName + "'";
        fieldList = this.empAddConfDAO.exeHqlList(hql);

        if (fieldList != null) {
            int size = fieldList.size();
            for (int i = 0; i < size; ++i) {
                fields.remove(fieldList.get(i));
            }
        }
        return fields;
    }

    public List findByName(String fieldName) {
        String hql = "from Empaddconf as conf where conf.eadcTableName='empadditional' and conf.eadcFieldName='"
                + fieldName + "'";
        return this.empAddConfDAO.exeHqlList(hql);
    }

    public List findByConf(Empaddconf conf) {
        String hql = "from Empaddconf as conf where conf.eadcTableName='" + conf.getEadcTableName()
                + "' and conf.eadcFieldName='" + conf.getEadcFieldName() + "'";

        return this.empAddConfDAO.exeHqlList(hql);
    }

    public List findByTableName(String tableName) {
        String hql = "from Empaddconf as conf where conf.eadcTableName ='" + tableName + "'";
        return this.empAddConfDAO.exeHqlList(hql);
    }

    public void updateEmpaddconf(Empaddconf conf) {
        this.empAddConfDAO.updateObject(conf);
    }

    public IEmpAddConfDAO getEmpAddConfDAO() {
        return this.empAddConfDAO;
    }

    public void setEmpAddConfDAO(IEmpAddConfDAO empAddConfDAO) {
        this.empAddConfDAO = empAddConfDAO;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.EmpAddConfBoImpl JD-Core Version: 0.5.4
 */