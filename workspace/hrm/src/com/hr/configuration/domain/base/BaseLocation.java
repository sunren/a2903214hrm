package com.hr.configuration.domain.base;

import com.hr.base.BaseDomain;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.configuration.domain.Location;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseLocation extends BaseDomain implements Serializable {
    public static String REF = "Location";
    public static String PROP_LOCATION_DESC = "locationDesc";
    public static String PROP_LOCATION_SORT_ID = "locationSortId";
    public static String PROP_LOCATION_NAME = "locationName";
    public static String PROP_ID = "id";
    public static String PROP_LOCATION_STATUS = "locationStatus";

    private int hashCode = -2147483648;
    private String id;
    private String locationName;
    private String locationDesc;
    private Integer locationSortId;
    private Integer locationStatus;
    public String headNo;
    private Set<Empsalarypay> empsalarypaids;
    private Set<Employee> employees;

    public BaseLocation() {
        initialize();
    }

    public BaseLocation(String id) {
        setId(id);
        initialize();
    }

    public BaseLocation(String id, String locationName, Integer locationSortId) {
        setId(id);
        setLocationName(locationName);
        setLocationSortId(locationSortId);
        initialize();
    }

    protected void initialize() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDesc() {
        return this.locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public Integer getLocationSortId() {
        return this.locationSortId;
    }

    public void setLocationSortId(Integer locationSortId) {
        this.locationSortId = locationSortId;
    }

    public Set<Empsalarypay> getEmpsalarypaids() {
        return this.empsalarypaids;
    }

    public void setEmpsalarypaids(Set<Empsalarypay> empsalarypaids) {
        this.empsalarypaids = empsalarypaids;
    }

    public void addToempsalarypaids(Empsalarypay empsalarypaid) {
        if (null == getEmpsalarypaids())
            setEmpsalarypaids(new TreeSet());
        getEmpsalarypaids().add(empsalarypaid);
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void addToemployees(Employee employee) {
        if (null == getEmployees())
            setEmployees(new TreeSet());
        getEmployees().add(employee);
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Location))
            return false;

        Location location = (Location) obj;
        if ((null == getId()) || (null == location.getId()))
            return false;
        return getId().equals(location.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }

    public String getHeadNo() {
        if (this.headNo != null) {
            return this.headNo;
        }
        if (getId() == null) {
            return null;
        }
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        String hql = "SELECT orgheadsEmpNo from Orgheads where orgheadsOrgNo ='" + getId()
                + "' and orgheadsResponsibility='locheader'";

        List list = empBo.exeHqlList(hql, new int[0]);
        if ((list == null) || (list.size() == 0)) {
            return null;
        }
        return (String) list.get(0);
    }

    public void setHeadNo(String headNo) {
        this.headNo = headNo;
    }

    public Integer getLocationStatus() {
        return this.locationStatus;
    }

    public void setLocationStatus(Integer locationStatus) {
        this.locationStatus = locationStatus;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.domain.base.BaseLocation JD-Core Version: 0.5.4
 */