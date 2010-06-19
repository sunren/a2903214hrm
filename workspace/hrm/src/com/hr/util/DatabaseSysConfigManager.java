package com.hr.util;

import com.hr.configuration.dao.ISysconfigDAO;
import com.hr.configuration.domain.Sysconfig;
import com.hr.spring.SpringBeanFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public final class DatabaseSysConfigManager implements SysConfigManager {
    private ISysconfigDAO sysDAO = (ISysconfigDAO) SpringBeanFactory.getBean("sysDAO");

    private static DatabaseSysConfigManager instance = new DatabaseSysConfigManager();

    public static DatabaseSysConfigManager getInstance() {
        return instance;
    }

    public void addNewProperty(String key, String value) {
    }

    public String getProperty(String key) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Sysconfig.class);
        detachedCriteria.add(Restrictions.eq(Sysconfig.PROP_SYSCONFIG_KEY, key));
        List list = this.sysDAO.findByCriteria(detachedCriteria);
        if ((list == null) || (list.size() == 0)) {
            return "";
        }
        return ((Sysconfig) list.get(0)).getSysconfigValue();
    }

    public void removeProperty(String key) {
    }

    public void setProperty(String key, String value) {
        String tempValue = getProperty(key);
        if ((tempValue == null) || ("".equals(tempValue))) {
            Sysconfig config = new Sysconfig(getUUID(), key, value);
            this.sysDAO.saveObject(config);
        } else {
            String hql = "update Sysconfig set sysconfigValue='" + value + "' where sysconfigKey='"
                    + key + "'";
            this.sysDAO.exeHql(hql);
        }
    }

    public void saveChange() {
    }

    public Map<String, String> getProperties() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Sysconfig.class);
        List<Sysconfig> list = this.sysDAO.findByCriteria(detachedCriteria);
        Map map = new HashMap();
        for (Sysconfig config : list) {
            map.put(config.getSysconfigKey(), config.getSysconfigValue());
        }
        return map;
    }

    private String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.DatabaseSysConfigManager JD-Core Version: 0.5.4
 */