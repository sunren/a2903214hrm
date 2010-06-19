package com.hr.compensation.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.configuration.bo.IBenefitTypeBO;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.BenefitType;
import com.hr.configuration.domain.Location;
import com.hr.spring.SpringBeanFactory;
import java.util.List;

public class BenefitTypeList extends BaseAction {
    private static final long serialVersionUID = 1L;
    private List<BenefitType> bfTypeList;
    private List locList;

    public String execute() throws Exception {
        this.bfTypeList = getDrillDown("BenefitType", new String[0]);
        this.locList = getDrillDown("Location", new String[] { "1" });

        return "success";
    }

    public String addBenefitType(String locationId, BenefitType benefitType) {
        String auth = DWRUtil.checkAuth("benefitTypeList", "execute");
        if ("error".equals(auth))
            return "noauth";
        IBenefitTypeBO bfTypeBo = (IBenefitTypeBO) SpringBeanFactory.getBean("benefitTypeBo");
        ILocationBO localbo = (ILocationBO) SpringBeanFactory.getBean("locationBO");
        Location location = localbo.loadLocation(locationId);
        benefitType.setBenefitTypeLocNo(location);
        List list = bfTypeBo.findNameByobj(benefitType);
        if (list.size() > 0) {
            return "EXISTED";
        }
        if (bfTypeBo.addBenefitType(benefitType)) {
            return benefitType.getId();
        }
        return "FAIL";
    }

    public String delBenefitType(String id) {
        String auth = DWRUtil.checkAuth("benefitTypeList", "execute");
        if ("error".equals(auth))
            return "noauth";
        try {
            IBenefitTypeBO bfTypeBo = (IBenefitTypeBO) SpringBeanFactory.getBean("benefitTypeBo");
            if (bfTypeBo.delBenefitType(BenefitType.class, id)) {
                return "SUCC";
            }
            return "FAIL";
        } catch (Exception e) {
        }
        return "error";
    }

    public String saveBenefitTypeSortIdByBatch(String[] ids) {
        String auth = DWRUtil.checkAuth("benefitTypeList", "execute");
        if ("error".equals(auth))
            return "noauth";
        IBenefitTypeBO bfTypeBo = (IBenefitTypeBO) SpringBeanFactory.getBean("benefitTypeBo");
        if (bfTypeBo.saveBenefitTypeSortIdByBatch(ids)) {
            return "SUCC";
        }
        return "FAIL";
    }

    public String updateBenefitType(String locationId, BenefitType benefitType) {
        String auth = DWRUtil.checkAuth("benefitTypeList", "execute");
        if ("error".equals(auth))
            return "noauth";
        IBenefitTypeBO bfTypeBo = (IBenefitTypeBO) SpringBeanFactory.getBean("benefitTypeBo");
        BenefitType dbBenefitType = bfTypeBo.searchById(benefitType.getId());
        if (dbBenefitType == null) {
            return "FAIL";
        }
        List list = bfTypeBo.findSameNameByobj(benefitType);
        if (list.size() > 0) {
            return "EXISTED";
        }
        dbBenefitType.setBenefitTypeLocNo(new Location(locationId));
        dbBenefitType.setBenefitTypeDescription(benefitType.getBenefitTypeDescription());
        dbBenefitType.setBenefitTypeName(benefitType.getBenefitTypeName());
        dbBenefitType.setBenefitTypePayType(benefitType.getBenefitTypePayType());
        dbBenefitType.setBenefitTypeSortId(benefitType.getBenefitTypeSortId());
        dbBenefitType.setEbfTypeChildbirthLowlimit(benefitType.getEbfTypeChildbirthLowlimit());
        dbBenefitType.setEbfTypeChildbirthUplimit(benefitType.getEbfTypeChildbirthUplimit());
        dbBenefitType.setEbfTypeHousingLowlimit(benefitType.getEbfTypeHousingLowlimit());
        dbBenefitType.setEbfTypeHousingUplimit(benefitType.getEbfTypeHousingUplimit());
        dbBenefitType.setEbfTypeInjuryLowlimit(benefitType.getEbfTypeInjuryLowlimit());
        dbBenefitType.setEbfTypeInjuryUplimit(benefitType.getEbfTypeInjuryUplimit());
        dbBenefitType.setEbfTypeMedicareLowlimit(benefitType.getEbfTypeMedicareLowlimit());
        dbBenefitType.setEbfTypeMedicareUplimit(benefitType.getEbfTypeMedicareUplimit());
        dbBenefitType.setEbfTypeOtherLowlimit(benefitType.getEbfTypeOtherLowlimit());
        dbBenefitType.setEbfTypeOtherUplimit(benefitType.getEbfTypeOtherUplimit());
        dbBenefitType.setEbfTypePensionLowlimit(benefitType.getEbfTypePensionLowlimit());
        dbBenefitType.setEbfTypePensionUplimit(benefitType.getEbfTypePensionUplimit());
        dbBenefitType.setEbfTypeUnemploymentLowlimit(benefitType.getEbfTypeUnemploymentLowlimit());
        dbBenefitType.setEbfTypeUnemploymentUplimit(benefitType.getEbfTypeUnemploymentUplimit());
        if (bfTypeBo.updateBenefitType(dbBenefitType)) {
            return "SUCC";
        }
        return "FAIL";
    }

    public List<BenefitType> getBfTypeList() {
        return this.bfTypeList;
    }

    public void setBfTypeList(List<BenefitType> bfTypeList) {
        this.bfTypeList = bfTypeList;
    }

    public List getLocList() {
        return this.locList;
    }

    public void setLocList(List locList) {
        this.locList = locList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.compensation.action.BenefitTypeList JD-Core Version: 0.5.4
 */