package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Location;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.bo.IOrgheadsBo;
import com.hr.profile.domain.Orgheads;
import com.hr.spring.SpringBeanFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class LocationManagementAction extends BaseAction {
    private static final long serialVersionUID = -694225214148958897L;
    private List<Location> allLocation;

    public String execute() throws Exception {
        ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        this.allLocation = localbo.FindAllLocation();

        Map locationHeadMap = new HashMap();
        IOrgheadsBo headsBo = (IOrgheadsBo) SpringBeanFactory.getBean("headsBo");
        List<Orgheads> orgheadlist = headsBo.listOrgheadNo("locationheader");
        for (Orgheads head : orgheadlist) {
            locationHeadMap.put(head.getOrgheadsOrgNo(), head.getOrgheadsEmpNo());
        }

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        Map empMap = empBo.getEmployeeMap(locationHeadMap.values());

        for (Location location : this.allLocation) {
            String headerId = (String) locationHeadMap.get(location.getId());
            if (StringUtils.isEmpty(headerId)) {
                continue;
            }

            location.setHeadNo(headerId);
            location.setHeadName((String) empMap.get(headerId));
        }
        return "success";
    }

    public List<Location> getAllLocation() {
        return this.allLocation;
    }

    public void setAllLocation(List<Location> allLocation) {
        this.allLocation = allLocation;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.LocationManagementAction JD-Core Version: 0.5.4
 */