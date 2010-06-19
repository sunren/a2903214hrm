package com.hr.compensation.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.compensation.bo.IEmpsalarydatadefBo;
import com.hr.compensation.domain.Empsalarydatadef;
import java.util.List;

public class AcctItemDef extends BaseAction {
    private static final long serialVersionUID = 1L;
    private List<Empsalarydatadef> datadefList;
    private String changeIodef;

    public AcctItemDef() {
        this.changeIodef = "";
    }

    public String execute() throws Exception {
        IEmpsalarydatadefBo defBo = (IEmpsalarydatadefBo) getBean("empsalarydatadefBo");
        this.datadefList = defBo.searchAll();
        return "success";
    }

    public String deleteDef(String defId) {
        String auth = DWRUtil.checkAuth("acctItemDef", "execute");
        if ("error".equals(auth))
            return "noauth";
        IEmpsalarydatadefBo defBo = (IEmpsalarydatadefBo) getBean("empsalarydatadefBo");
        if (defBo.delete(defId)) {
            return "success";
        }
        return "FAIL";
    }

    public String update(Empsalarydatadef datadef) {
        String auth = DWRUtil.checkAuth("acctItemDef", "execute");
        if ("error".equals(auth)) {
            return "noauth";
        }
        IEmpsalarydatadefBo defBo = (IEmpsalarydatadefBo) getBean("empsalarydatadefBo");
        datadef.setEsddSortId(defBo.searchById(datadef.getEsddId()).getEsddSortId());
        List list = defBo.findSameName(datadef);
        if (list.size() > 0) {
            return "EXISTED";
        }
        if (defBo.update(datadef)) {
            return "success";
        }
        return "FAIL";
    }

    public String insert(Empsalarydatadef datadef) {
        String auth = DWRUtil.checkAuth("acctItemDef", "execute");
        if ("error".equals(auth)) {
            return "noauth";
        }
        IEmpsalarydatadefBo defBo = (IEmpsalarydatadefBo) getBean("empsalarydatadefBo");
        List list = defBo.findByName(datadef.getEsddName().trim());
        if (list.size() > 0) {
            return "EXISTED";
        }

        if (defBo.insert(datadef)) {
            return datadef.getEsddId();
        }

        return "FAIL";
    }

    public String saveSort(String[] ids) {
        String auth = DWRUtil.checkAuth("acctItemDef", "execute");
        if ("error".equals(auth)) {
            return "noauth";
        }
        IEmpsalarydatadefBo defBo = (IEmpsalarydatadefBo) getBean("empsalarydatadefBo");
        if (defBo.saveSort(ids)) {
            return "success";
        }
        return "FAIL";
    }

    public String refreshOMConfig(String changeIodef) {
        if ((changeIodef == null) || (changeIodef.length() < 1)
                || (!changeIodef.equals("TengSourceAdmin"))) {
            return "noauth";
        }
        String auth = DWRUtil.checkAuth("acctItemDef", "execute");
        if ("error".equals(auth)) {
            return "noauth";
        }
        IEmpsalarydatadefBo defBo = (IEmpsalarydatadefBo) getBean("empsalarydatadefBo");
        if (defBo.batchRefreshOMConfig()) {
            return "success";
        }
        return "FAIL";
    }

    public List<Empsalarydatadef> getDatadefList() {
        return this.datadefList;
    }

    public void setDatadefList(List<Empsalarydatadef> datadefList) {
        this.datadefList = datadefList;
    }

    public String getChangeIodef() {
        return this.changeIodef;
    }

    public void setChangeIodef(String changeIodef) {
        if ((changeIodef != null) && (changeIodef.equals("TengSourceAdmin")))
            this.changeIodef = changeIodef;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.action.AcctItemDef JD-Core Version: 0.5.4
 */