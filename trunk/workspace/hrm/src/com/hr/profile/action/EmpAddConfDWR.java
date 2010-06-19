package com.hr.profile.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.profile.bo.IEmpAddConfBo;
import com.hr.profile.domain.Empaddconf;
import java.util.List;

public class EmpAddConfDWR extends BaseAction {
    public String addEmpAddConf(Empaddconf config) {
        if (!checkAuthForSystem()) {
            return "noauth";
        }
        IEmpAddConfBo empAddConfBo = (IEmpAddConfBo) BaseAction.getBean("empAddConfBo");
        if ((config.getEadcTableName() == null) || ("".equals(config.getEadcTableName()))) {
            config.setEadcTableName("empadditional");
        }

        List temp = empAddConfBo.findByConf(config);
        if ((temp != null) && (temp.size() > 0)) {
            return "EXISTED";
        }

        empAddConfBo.insertEmpaddconf(config);
        return config.getEadcId();
    }

    public String deleteEmpAddConf(String id) {
        if (!checkAuthForSystem()) {
            return "noauth";
        }
        IEmpAddConfBo empAddConfBo = (IEmpAddConfBo) BaseAction.getBean("empAddConfBo");
        empAddConfBo.deleteEmpaddconf(id);
        return "SUCC";
    }

    public String updateEmpAddConf(Empaddconf config) {
        if (!checkAuthForSystem()) {
            return "noauth";
        }
        IEmpAddConfBo empAddConfBo = (IEmpAddConfBo) BaseAction.getBean("empAddConfBo");
        if ((config.getEadcTableName() == null) || ("".equals(config.getEadcTableName()))) {
            config.setEadcTableName("empadditional");
        }

        List temp = empAddConfBo.findByConf(config);
        if (temp != null) {
            if (temp.size() > 1)
                return "EXISTED";
            if ((temp.size() == 1)
                    && (!config.getEadcId()
                            .equalsIgnoreCase(((Empaddconf) temp.get(0)).getEadcId()))) {
                return "EXISTED";
            }
        }
        empAddConfBo.updateEmpaddconf(config);
        return "SUCC";
    }

    private boolean checkAuthForSystem() {
        String flt = DWRUtil.checkAuth("config", "execute");

        return !"error".equalsIgnoreCase(flt);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.EmpAddConfDWR JD-Core Version: 0.5.4
 */