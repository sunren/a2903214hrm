package com.hr.configuration.bo;

import com.hr.configuration.dao.ILocationDAO;
import com.hr.configuration.domain.Location;
import com.hr.profile.domain.Orgheads;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class LocationBOImpl implements ILocationBO {
    private ILocationDAO dao;

    public LocationBOImpl() {
        this.dao = null;
    }

    public Location loadLocation(String locationNo) {
        Object ob = this.dao.loadObject(Location.class, locationNo, null, new boolean[0]);
        if (ob == null) {
            return null;
        }
        return (Location) ob;
    }

    public List FindAllLocation() {
        DetachedCriteria dc = DetachedCriteria.forClass(Location.class);
        dc.addOrder(Order.asc("locationSortId"));
        return this.dao.findByCriteria(dc);
    }

    public List<Location> FindEnabledLocation() {
        DetachedCriteria dc = DetachedCriteria.forClass(Location.class);
        dc.add(Restrictions.eq(Location.PROP_LOCATION_STATUS, new Integer(1)));
        dc.addOrder(Order.asc("locationSortId"));
        return this.dao.findByCriteria(dc);
    }

    public boolean addLocation(Location location) {
        try {
            String trimmedLocationName = location.getLocationName().trim();

            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Location.class);
            detachedCriteria.add(Restrictions.eq("locationName", trimmedLocationName));
            List old = this.dao.findByCriteria(detachedCriteria);
            if (!old.isEmpty()) {
                return false;
            }

            location.setLocationName(trimmedLocationName);
            this.dao.saveObject(location);

            if (StringUtils.isNotEmpty(location.getHeadNo())) {
                Orgheads head = new Orgheads();
                head.setOrgheadsEmpNo(location.getHeadNo());
                head.setOrgheadsOrgNo(location.getId());
                head.setOrgheadsResponsibility("locationheader");
                this.dao.saveObject(head);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String delLocation(Class<Location> name, String id) {
        StringBuffer buffer = new StringBuffer();
        try {
            String hql = "select count(*) from Employee where empLocationNo='" + id + "'";
            List check = this.dao.exeHqlList(hql);
            if (hasReleasation(check)) {
                buffer.append("有员工关联到该地区！");
            }
            hql = "select count(*) from BenefitType where benefitTypeLocNo='" + id + "'";
            check = this.dao.exeHqlList(hql);
            if (hasReleasation(check)) {
                buffer.append("有社保种类关联到该地区！");
            }

            hql = "select count(*) from Empsalarypay where espDept='" + id + "'";
            check = this.dao.exeHqlList(hql);
            if (hasReleasation(check)) {
                buffer.append("有薪资数据发放记录关联到该地区！");
            }

            hql = "select count(*) from Leavecalendar where lcLocationNo='" + id + "'";
            check = this.dao.exeHqlList(hql);
            if (hasReleasation(check)) {
                buffer.append("有日历关联到该地区！");
            }

            hql = "select count(*) from Leaverequest where lrEmpLocationNo='" + id + "'";
            check = this.dao.exeHqlList(hql);
            if (hasReleasation(check)) {
                buffer.append("有请假记录关联到该地区！");
            }

            hql = "select count(*) from Overtimerequest where orEmpLocationNo='" + id + "'";
            check = this.dao.exeHqlList(hql);
            if (hasReleasation(check)) {
                buffer.append("有加班记录关联到该地区！");
            }

            hql = "select count(*) from Recruitplan where recpWorkLocation='" + id + "'";
            check = this.dao.exeHqlList(hql);
            if (hasReleasation(check)) {
                buffer.append("有招聘计划关联到该地区！");
            }
            if (StringUtils.isEmpty(buffer.toString())) {
                this.dao.deleteObject(Location.class, id);
            }

            hql = "delete from Orgheads where orgheadsOrgNo='" + id + "'";
            this.dao.exeHql(hql);
            return buffer.toString();
        } catch (Exception e) {
            buffer.append("系统异常" + e.getMessage());
        }
        return buffer.toString();
    }

    private boolean hasReleasation(List list) {
        return Integer.valueOf(list.get(0).toString()).intValue() > 0;
    }

    public String updateLocation(Location location) {
        String trimmedLocationName = location.getLocationName().trim();

        if (location.getLocationStatus().intValue() == 0) {
            String hql = "select count(*) from Employee where empLocationNo='" + location.getId()
                    + "'";
            List check = this.dao.exeHqlList(hql);
            if (Integer.valueOf(check.get(0).toString()).intValue() > 0) {
                return "1";
            }
        }

        List old = this.dao.exeHqlList(" from Location where  id != '" + location.getId()
                + "'  and locationName  ='" + trimmedLocationName + "'");

        if (!old.isEmpty()) {
            return "2";
        }

        Location oldLocation = (Location) this.dao.loadObject(Location.class, location.getId(),
                                                              null, new boolean[0]);

        oldLocation.setLocationDesc(location.getLocationDesc());
        oldLocation.setLocationName(trimmedLocationName);
        oldLocation.setLocationStatus(location.getLocationStatus());
        oldLocation.setLocationSortId(location.getLocationSortId());
        this.dao.updateObject(oldLocation);

        if (StringUtils.isNotEmpty(location.getHeadNo())) {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Orgheads.class);
            detachedCriteria.add(Restrictions.eq("orgheadsOrgNo", oldLocation.getId()));
            detachedCriteria.add(Restrictions.eq("orgheadsResponsibility", "locationheader"));
            List oldLocationHead = this.dao.findByCriteria(detachedCriteria);
            if (oldLocationHead.isEmpty()) {
                Orgheads head = new Orgheads();
                head.setOrgheadsEmpNo(location.getHeadNo());
                head.setOrgheadsOrgNo(oldLocation.getId());
                head.setOrgheadsResponsibility("locationheader");
                this.dao.saveObject(head);
            } else {
                Orgheads head = (Orgheads) oldLocationHead.get(0);
                head.setOrgheadsEmpNo(location.getHeadNo());
                this.dao.updateObject(head);
            }
        }
        return "0";
    }

    public void updateLocationSwapSortedId(String tr1Id, String tr2Id) {
        Location swapLocationOne = (Location) this.dao.loadObject(Location.class, tr1Id, null,
                                                                  new boolean[0]);

        Location swapLocationTwo = (Location) this.dao.loadObject(Location.class, tr2Id, null,
                                                                  new boolean[0]);

        int tempSortId = swapLocationOne.getLocationSortId().intValue();
        swapLocationOne.setLocationSortId(swapLocationTwo.getLocationSortId());
        swapLocationTwo.setLocationSortId(Integer.valueOf(tempSortId));
        this.dao.updateObject(swapLocationOne);
        this.dao.updateObject(swapLocationTwo);
    }

    public void saveLocationSortIdByBatch(String[] ids) {
        int length = ids.length;
        int sortId = 1;
        for (int i = 0; i < length; ++i) {
            this.dao.exeHql("update Location set locationSortId=" + sortId + " where id ='"
                    + ids[i] + "'");

            ++sortId;
        }
    }

    public ILocationDAO getDao() {
        return this.dao;
    }

    public void setDao(ILocationDAO dao) {
        this.dao = dao;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.LocationBOImpl JD-Core Version: 0.5.4
 */