package com.hr.compensation.action;

import com.hr.base.BaseAction;
import com.hr.base.Status;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.bo.IEmpsalarydatadefBo;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.axis.utils.StringUtils;

public class DWRforAcctItemDef extends BaseAction implements Status {
    private static final long serialVersionUID = 1L;

    public List<String> getItemsByType(Integer type) {
        IEmpsalarydatadefBo defBo = (IEmpsalarydatadefBo) SpringBeanFactory
                .getBean("empsalarydatadefBo");
        List<Empsalarydatadef> list = defBo.searchByType(type);
        if (list == null) {
            return null;
        }
        List result = new ArrayList();
        for (Empsalarydatadef def : list) {
            result.add(def.getEsddId().toString());
            result.add(def.getEsddName());
        }
        return result;
    }

    public Empsalarydatadef getItemById(String esddId) {
        if (StringUtils.isEmpty(esddId)) {
            return null;
        }
        IEmpsalarydatadefBo defBo = (IEmpsalarydatadefBo) SpringBeanFactory
                .getBean("empsalarydatadefBo");

        return defBo.searchById(esddId);
    }

    public List<Empsalaryacctitems> getAcctItemsById(String acctversionId) {
        IEmpSalaryAcctitemsBo acctItemBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");
        return acctItemBo.getItemsByAcctversion(acctversionId);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.action.DWRforAcctItemDef JD-Core Version: 0.5.4
 */