package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Location;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IOrgheadsBo;
import com.hr.profile.domain.Orgheads;
import com.hr.spring.SpringBeanFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LocationManagerAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    List<Location> locationList;
    List<Orgheads> locationHeads;

    public String execute() throws Exception {
        ILocationBO locationBo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        this.locationList = locationBo.FindAllLocation();

        Map locationNameMap = new HashMap();
        for (Location location : this.locationList) {
            locationNameMap.put(location.getId(), location.getLocationName());
        }

        IOrgheadsBo headsBo = (IOrgheadsBo) SpringBeanFactory.getBean("headsBo");
        this.locationHeads = headsBo.listOrgheadNo("location");
        Set empIds = new HashSet();
        for (Orgheads head : this.locationHeads) {
            empIds.add(head.getOrgheadsEmpNo());
        }
        IEmployeeBo empBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");

        Map empMap = empBo.getEmployeeMap(empIds);

        for (Orgheads head : this.locationHeads) {
            head.setOrgName((String) locationNameMap.get(head.getOrgheadsOrgNo()));
            head.setEmpName((String) empMap.get(head.getOrgheadsEmpNo()));
        }
        return "success";
    }

    public List<Location> getLocationList() {
        return this.locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<Orgheads> getLocationHeads() {
        return this.locationHeads;
    }

    public void setLocationHeads(List<Orgheads> locationHeads) {
        this.locationHeads = locationHeads;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.action.LocationManagerAction JD-Core Version: 0.5.4
 */