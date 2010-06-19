package com.hr.examin.action;

import com.hr.base.BaseAction;
import com.hr.base.DWRUtil;
import com.hr.configuration.bo.ILocationBO;
import com.hr.configuration.domain.Location;
import com.hr.examin.bo.interfaces.ILeavecalendarBO;
import com.hr.examin.domain.Leavecalendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LeavecalendarManageAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private int yearSelect;
    private List<Location> locationList;
    private List<LCType> typeList;
    private List<Leavecalendar> result;
    private String infoMeg;
    private String errorMsg;
    private ILeavecalendarBO lc_BO;

    public String execute() throws Exception {
        ILocationBO l_BO = (ILocationBO) getBean("locationBO");
        this.locationList = l_BO.FindAllLocation();
        Location templ = new Location();
        templ.setId("all");
        templ.setLocationName("全部地区");
        this.locationList.add(0, templ);
        this.typeList = new ArrayList();
        this.typeList.add(new LCType(0, getTypeDescription(0)));
        this.typeList.add(new LCType(1, getTypeDescription(1)));
        this.typeList.add(new LCType(2, getTypeDescription(2)));

        if (this.yearSelect == 0) {
            this.yearSelect = Calendar.getInstance().get(1);
        }
        this.lc_BO = ((ILeavecalendarBO) getBean("leavecalendarBO"));
        this.result = this.lc_BO.getCalendarByYear(this.yearSelect);
        if ((getInfoMeg() != null) && (getInfoMeg().length() > 0)) {
            addSuccessInfo(getInfoMeg());
            setInfoMeg(null);
        }
        if ((getErrorMsg() != null) && (getErrorMsg().length() > 0)) {
            addErrorInfo(getErrorMsg());
            setErrorMsg(null);
        }
        return "success";
    }

    public String getTypeDescription(int lc_sign) {
        if (lc_sign == 0)
            return "公休日";
        if (lc_sign == 1)
            return "工作日";
        if (lc_sign == 2) {
            return "节假日";
        }
        return "Error";
    }

    public String getLocationName(Location lc_location) {
        if (lc_location == null) {
            return "全部地区";
        }
        return lc_location.getLocationName();
    }

    public String checkLeavecalendar(Leavecalendar lc) {
        if (lc == null) {
            return "No input";
        }
        if (lc.getLcDate() == null) {
            return "没有时间输入";
        }
        if ((lc.getLcDateDesc() != null) && (lc.getLcDateDesc().length() > 64)) {
            return "备注超过64字节长度";
        }
        if (lc.getLcLocationNo().getId().equals("all")) {
            lc.setLcLocationNo(null);
        }
        return null;
    }

    public String insertLeavecalendar(Leavecalendar lc) {
        if (!DWRUtil.checkAuth("leavecalendarManage", "execute").equals("HR")) {
            return "您无权执行此操作，请重新登陆";
        }
        String check = checkLeavecalendar(lc);
        if (check != null) {
            return check;
        }
        this.lc_BO = ((ILeavecalendarBO) getBean("leavecalendarBO"));
        List result = this.lc_BO.insertLeavecalendar(lc);
        if ((result == null) || (result.size() == 0))
            return "suc";
        return (String) result.get(0);
    }

    public String delLeavecalendar(String lcId) {
        if (!DWRUtil.checkAuth("leavecalendarManage", "execute").equals("HR")) {
            return "您无权执行此操作，请重新登陆";
        }
        this.lc_BO = ((ILeavecalendarBO) getBean("leavecalendarBO"));
        List result = this.lc_BO.deleteLeavecalendar(lcId);
        if ((result == null) || (result.size() == 0))
            return "suc";
        return (String) result.get(0);
    }

    public String updateLeavecalendar(Leavecalendar lc) {
        if (!DWRUtil.checkAuth("leavecalendarManage", "execute").equals("HR")) {
            return "您无权执行此操作，请重新登陆";
        }
        String check = checkLeavecalendar(lc);
        if (check != null) {
            return check;
        }
        this.lc_BO = ((ILeavecalendarBO) getBean("leavecalendarBO"));
        List result = this.lc_BO.updateLeavecalendar(lc);
        if ((result == null) || (result.size() == 0))
            return "suc";
        return (String) result.get(0);
    }

    public Leavecalendar loadLeavecalendar(String lcId) {
        if (!DWRUtil.checkAuth("leavecalendarManage", "execute").equals("HR")) {
            return null;
        }
        this.lc_BO = ((ILeavecalendarBO) getBean("leavecalendarBO"));
        return this.lc_BO.loadLeavecalendar(lcId);
    }

    public int getYearSelect() {
        return this.yearSelect;
    }

    public void setYearSelect(int yearSelect) {
        this.yearSelect = yearSelect;
    }

    public ILeavecalendarBO getLc_BO() {
        return this.lc_BO;
    }

    public void setLc_BO(ILeavecalendarBO lc_BO) {
        this.lc_BO = lc_BO;
    }

    public List<Location> getLocationList() {
        return this.locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<Leavecalendar> getResult() {
        return this.result;
    }

    public void setResult(List<Leavecalendar> result) {
        this.result = result;
    }

    public List<LCType> getTypeList() {
        return this.typeList;
    }

    public void setTypeList(List<LCType> typeList) {
        this.typeList = typeList;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getInfoMeg() {
        return this.infoMeg;
    }

    public void setInfoMeg(String infoMeg) {
        this.infoMeg = infoMeg;
    }

    class LCType {
        int lcSign;
        String signName;

        public LCType(int lc, String sN) {
            this.lcSign = lc;
            this.signName = sN;
        }

        public int getLcSign() {
            return this.lcSign;
        }

        public void setLcSign(int lcSign) {
            this.lcSign = lcSign;
        }

        public String getSignName() {
            return this.signName;
        }

        public void setSignName(String signName) {
            this.signName = signName;
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.LeavecalendarManageAction JD-Core Version: 0.5.4
 */