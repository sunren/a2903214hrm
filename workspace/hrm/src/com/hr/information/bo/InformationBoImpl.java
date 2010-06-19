package com.hr.information.bo;

import com.hr.base.FileOperate;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Infoclass;
import com.hr.information.dao.IInformationDAO;
import com.hr.information.domain.Information;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.security.domain.Userinfo;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.Pager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class InformationBoImpl implements IInformationBo {
    private IInformationDAO informationDAO;

    public IInformationDAO getInformationDAO() {
        return this.informationDAO;
    }

    public void setInformationDAO(IInformationDAO informationDAO) {
        this.informationDAO = informationDAO;
    }

    public boolean deleteInfo(Information info) {
        if (info == null) {
            return true;
        }
        String infoName = info.getInfoFileName();
        if ((infoName != null) && (infoName.trim().length() > 0)) {
            FileOperate.deleteFile("sys.information.file.path", infoName.trim());
        }
        String imgName = info.getInfoPicName();
        if ((imgName != null) && (imgName.trim().length() > 0)) {
            FileOperate.deleteFile("sys.information.image.path", imgName);
            imgName = imgName.replaceAll("_s", "");
            FileOperate.deleteFile("sys.information.image.path", imgName);
        }
        this.informationDAO.deleteObject(info);
        return true;
    }

    public Infoclass getInfoclass(String classId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Infoclass.class);
        detachedCriteria.add(Restrictions.eq(Infoclass.PROP_ID, classId));
        detachedCriteria.addOrder(Order.asc(Infoclass.PROP_INFOCLASS_SORT_ID));
        List list = this.informationDAO.findByCriteria(detachedCriteria);
        if ((list == null) || (list.size() != 1)) {
            return null;
        }
        return (Infoclass) list.get(0);
    }

    public List getListbyClass(String infoid, Userinfo user) {
        if (infoid == null) {
            return new ArrayList();
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Information.class);
        detachedCriteria.setFetchMode("infoClass", FetchMode.JOIN);
        if (user != null) {
            String[] fetch = { "empDeptNo" };
            IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
            Employee employee = empBo.loadEmp(user.getId(), fetch);
            detachedCriteria.add(Restrictions.or(Restrictions.like("infoTag", "%,"
                    + employee.getEmpDeptNo().getDepartmentName() + ",%"), Restrictions
                    .like("infoTag", "%扄1�7有部闄1�7%")));
        }

        detachedCriteria.createCriteria("infoClass").add(Restrictions.eq("id", infoid));
        detachedCriteria.add(Restrictions.eq("infoStatus", Integer.valueOf(1)));
        detachedCriteria.addOrder(Order.desc("infoLastChangeTime"));
        List result = this.informationDAO.findByCriteria(detachedCriteria);
        return result;
    }

    public List getListbyClassId(String classId, Pager page, String searchKey, Userinfo user,
            Integer infoStatus) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Information.class);
        detachedCriteria.setFetchMode("infoClass", FetchMode.JOIN);
        detachedCriteria.setFetchMode("infoCreateBy", FetchMode.JOIN);
        if ((searchKey != null) && (searchKey.trim().length() > 0)) {
            detachedCriteria.add(Restrictions.or(Restrictions.like("infoTitle", "%" + searchKey
                    + "%"), Restrictions.or(Restrictions.like("infoBreif", "%" + searchKey + "%"),
                                            Restrictions.or(Restrictions.like("infoContent", "%"
                                                    + searchKey + "%"), Restrictions
                                                    .like("infoTag", "%" + searchKey + "%")))));
        }

        if ((classId != null) && (classId.length() > 0)) {
            detachedCriteria.createCriteria("infoClass").add(Restrictions.eq("id", classId));
        }
        if ((infoStatus != null) && (infoStatus.intValue() != 0)) {
            detachedCriteria.add(Restrictions.eq("infoStatus", infoStatus));
        }
        if (user != null) {
            String[] fetch = { "empDeptNo" };
            IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
            Employee employee = empBo.loadEmp(user.getId(), fetch);
            detachedCriteria.add(Restrictions.or(Restrictions.like("infoTag", "%,"
                    + employee.getEmpDeptNo().getDepartmentName() + ",%"), Restrictions
                    .like("infoTag", "%扄1�7有部闄1�7%")));
        }

        detachedCriteria.addOrder(Order.desc("infoLastChangeTime"));
        List result = this.informationDAO.findByCriteria(detachedCriteria);

        int size = result.size();
        int pageSize = Integer.valueOf(
                                       DatabaseSysConfigManager.getInstance()
                                               .getProperty("sys.split.pages")).intValue();

        page.init(size, pageSize);

        if ((page.getOperate() != null) && ("previous".equalsIgnoreCase(page.getOperate()))) {
            page.previous();
        }
        if ((page.getOperate() != null) && ("next".equalsIgnoreCase(page.getOperate()))) {
            page.next();
        }
        if ((page.getOperate() != null) && ("first".equalsIgnoreCase(page.getOperate()))) {
            page.first();
        }
        if ((page.getOperate() != null) && ("last".equalsIgnoreCase(page.getOperate()))) {
            page.last();
        }
        List returnList = new ArrayList();
        if (size == 0)
            return returnList;

        int endPage = (size > page.getCurrentPage() * pageSize) ? page.getCurrentPage() * pageSize
                : size;
        for (int i = (page.getCurrentPage() - 1) * pageSize; i < endPage; ++i)
            returnList.add(result.get(i));
        return returnList;
    }

    public List getObjects(Class clas, String[] fetch) {
        List result = this.informationDAO.getObjects(clas, fetch);
        return result;
    }

    public List getOrderInfos(String fieldName, String methods) {
        return this.informationDAO.exeHqlList("from Information order by " + fieldName + " "
                + methods);
    }

    public List insertInfo(Information info) {
        this.informationDAO.saveObject(info);
        return null;
    }

    public Information loadInfo(Object id, String[] fetch) {
        return (Information) this.informationDAO.loadObject(Information.class, id, fetch,
                                                            new boolean[0]);
    }

    public Information loadOne(String oneId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Information.class);
        detachedCriteria.add(Restrictions.eq(Information.PROP_ID, oneId));
        List list = this.informationDAO.findByCriteria(detachedCriteria);
        if ((list == null) || (list.size() != 1)) {
            return null;
        }
        return (Information) list.get(0);
    }

    public boolean updateInfo(Information info) {
        this.informationDAO.updateObject(info);
        return true;
    }

    public boolean saveObject(Information info) {
        try {
            List errors = new ArrayList();
            if (errors.size() < 1) {
                this.informationDAO.saveObject(info);
                return true;
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }

    public Date getLastMonthDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(2, 0);
        cal.set(5, 0);
        return cal.getTime();
    }

    public Date getNextMonthDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(2, 1);
        cal.set(5, 1);
        return cal.getTime();
    }

    public String getElementId(String tag) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Infoclass.class);
        if ((tag == null) || (tag.trim().length() <= 0)) {
            return null;
        }
        detachedCriteria.addOrder(Order.asc(Infoclass.PROP_INFOCLASS_SORT_ID));
        List list = this.informationDAO.findByCriteria(detachedCriteria);
        if ((list == null) || (list.size() <= 0)) {
            return null;
        }
        if ("first".equals(tag))
            return ((Infoclass) list.get(0)).getId();
        if (list.size() <= 1) {
            return null;
        }
        return ((Infoclass) list.get(1)).getId();
    }

    public List getInfoClassBySortId() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Infoclass.class);
        detachedCriteria.addOrder(Order.asc(Infoclass.PROP_INFOCLASS_SORT_ID));
        List list = this.informationDAO.findByCriteria(detachedCriteria);
        return list;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.information.bo.InformationBoImpl JD-Core Version: 0.5.4
 */