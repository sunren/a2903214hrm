package com.hr.io.extend;

import com.hr.examin.bo.interfaces.IAttdoriginaldataBO;
import com.hr.examin.domain.Attdoriginaldata;
import com.hr.io.domain.ExaminCardBean;
import com.hr.io.domain.InmatchModel;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.DateUtil;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class IExaminCardData extends ICheckAndInsert {
    String msgNotExist;
    String msgCardNoNotExist;
    String msgNoBothNo;
    String msgNoCardTime;
    String msgCardTimeValid;

    public IExaminCardData()
    {
      this.msgNotExist = "编号为{0}的员工不存在";
      this.msgCardNoNotExist = "考勤卡号为{0}的员工不存在";
      this.msgNoBothNo = "未填写考勤卡号或员工工号";
      this.msgNoCardTime = "必须至少填写一个刷卡数据";
      this.msgCardTimeValid = "刷卡时间{0}格式不正确";
    }
    
    public int[] insertTransmit(List insertList, CommonParameters commonParas) {
        Employee user = commonParas.getCurrEmp();
        HashMap employeeMap = getEmployeeMap();
        List dataList = new ArrayList();
        int[] result = { 0, 0 };

        Date cardStartDate = null;
        Date cardEndDate = null;

        List<ExaminCardBean> excelDataList = insertList;
        int hasTitle = 1 - commonParas.getInmatchModel().getImmNoTitle().intValue();
        int rowNum = hasTitle + 1;
        String empIdStr = null;

        Attdoriginaldata data = null;
        Map aodIdMap = new HashMap();
        for (ExaminCardBean bean : excelDataList) {
            boolean hasDistinctNo = false;
            boolean hasShiftNo = false;
            boolean hasACardTime = false;

            String empDistinctNo = bean.getEmp().getEmpDistinctNo();
            String empShiftNo = bean.getEmp().getEmpShiftNo();
            String aodMachineNo = bean.getAodMachineNo();
            Date aodAttdDate = bean.getAodAttdDate();
            Employee rowEmp = null;

            if (empDistinctNo != null) {
                hasDistinctNo = true;
                if (!employeeMap.containsKey(empDistinctNo)) {
                    commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                                new String[] { empDistinctNo });
                }

                rowEmp = (Employee) employeeMap.get(empDistinctNo);
            }

            if (empShiftNo != null) {
                hasShiftNo = true;
                if (!employeeMap.containsKey(empShiftNo)) {
                    commonParas.addErrorMessage(this.msgCardNoNotExist, Integer.valueOf(rowNum),
                                                new String[] { empShiftNo });
                }

                if (rowEmp == null)
                    rowEmp = (Employee) employeeMap.get(empShiftNo);

            }

            if ((!hasDistinctNo) && (!hasShiftNo)) {
                commonParas.addErrorMessage(this.msgNoBothNo, Integer.valueOf(rowNum),
                                            new String[0]);
            }

            Class objClass = bean.getClass();
            Method getMethod = null;
            String cardTime = null;
            for (int i = 1; i <= bean.getTimeCount(); ++i) {
                try {
                    getMethod = objClass.getMethod("getAodCardTime" + i, new Class[0]);
                    cardTime = (String) getMethod.invoke(bean, new Object[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (cardTime == null)
                    continue;
                hasACardTime = true;

                if (cardTime.indexOf("/") > -1) {
                    cardTime = cardTime.replaceAll("/", "-");
                }
                if (!matcherDateString(cardTime)) {
                    commonParas.addErrorMessage(this.msgCardTimeValid, Integer.valueOf(rowNum),
                                                new String[] { cardTime });
                } else {
                    data = new Attdoriginaldata();
                    data.setAttdEmp(rowEmp);
                    data.setAodAttdDate(aodAttdDate);
                    Date aodCardTime = getCardTime(data, cardTime);
                    String aodId = rowEmp.getId()
                            + DateUtil.formatDateToS(aodCardTime, "yyyyMMddHHmmss");
                    if ((aodIdMap.isEmpty()) || (aodIdMap.get(aodId) == null)) {
                        aodIdMap.put(aodId, aodId);
                        data.setAodId(aodId);
                        data.setAodCardTime(aodCardTime);
                        data.setAodTtdMachineNo(aodMachineNo);
                        data.setAodMemo(bean.getAodMemo());

                        data.setAodStatus(Integer.valueOf(0));
                        data.setAodLastModifyUser(user.getId());
                        data.setAodLastModifyTime(new Date());

                        if ((cardStartDate == null) || (cardEndDate == null)) {
                            cardStartDate = cardEndDate = data.getAodAttdDate();
                        }
                        if ((cardStartDate != null)
                                && (data.getAodAttdDate().getTime() < cardStartDate.getTime())) {
                            cardStartDate = data.getAodAttdDate();
                        }
                        if ((cardEndDate != null)
                                && (data.getAodAttdDate().getTime() > cardEndDate.getTime())) {
                            cardEndDate = data.getAodAttdDate();
                        }

                        dataList.add(data);
                    }
                }
            }
            if (!hasACardTime) {
                commonParas.addErrorMessage(this.msgNoCardTime, Integer.valueOf(rowNum),
                                            new String[0]);
            }
            if ((empIdStr != null) && (!empIdStr.equals(""))) {
                empIdStr = empIdStr + "&";
            }
            empIdStr = rowEmp.getId();
            ++rowNum;
        }

        if (("SUB".equals(commonParas.getAuthCondition())) && (StringUtils.isEmpty(empIdStr))) {
            chechDeptAuth(user, empIdStr.split("&"), commonParas);
        }

        IAttdoriginaldataBO originaldataBo = (IAttdoriginaldataBO) SpringBeanFactory
                .getBean("attdoriginaldataBO");
        if (!commonParas.getIoMessages().hasErrorMsg()) {
            boolean temp = originaldataBo.insertDataList(dataList, cardStartDate, cardEndDate);
            if (temp) {
                result[0] = excelDataList.size();
            }
        }

        return result;
    }

    private HashMap<String, Employee> getEmployeeMap() {
        HashMap map = new HashMap();
        IEmployeeBo esaBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        List empList = esaBo.exeHqlList("select id, empDistinctNo, empShiftNo from Employee",
                                        new int[0]);

        Object[] info = null;
        Employee emp = null;
        String shiftNo = null;
        for (int i = 0; (empList != null) && (i < empList.size()); ++i) {
            info = (Object[]) (Object[]) empList.get(i);
            emp = new Employee((String) info[0]);
            map.put((String) info[1], emp);
            shiftNo = (String) info[2];
            if ((shiftNo != null) && (!"".equals(shiftNo))) {
                map.put(shiftNo, emp);
            }
        }
        return map;
    }

    private boolean matcherDateString(String content) {
        String style1 = "\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}";

        String style2 = "\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}";

        String style3 = "\\d{1,2}:\\d{1,2}:\\d{1,2}";

        String style4 = "\\d{1,2}:\\d{1,2}";
        boolean flag = (content.matches(style1)) || (content.matches(style2))
                || (content.matches(style3)) || (content.matches(style4));
        return flag;
    }

    private Date getCardTime(Attdoriginaldata data, String checkContent) {
        Date date = null;
        if (checkContent.indexOf("/") > -1) {
            checkContent = checkContent.replaceAll("/", "-");
        }

        if ((checkContent.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}"))
                || (checkContent.matches("\\d{1,2}:\\d{1,2}")))
            checkContent = new SimpleDateFormat("yyyy-MM-dd").format(data.getAodAttdDate()) + " "
                    + checkContent;
        try {
            if (checkContent.matches("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}"))
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(checkContent);
            else if (checkContent.matches("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}"))
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(checkContent);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    private class ObjAndRow {
        public Integer rowNum;
        public ExaminCardBean bean;

        ObjAndRow(Integer rowNum, ExaminCardBean bean) {
            this.rowNum = rowNum;
            this.bean = bean;
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.extend.IExaminCardData JD-Core Version: 0.5.4
 */