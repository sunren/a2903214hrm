package com.hr.io.extend;

import com.hr.compensation.bo.IEmpBenefitBo;
import com.hr.compensation.dao.IEmpBenefitDao;
import com.hr.compensation.domain.Empbenefit;
import com.hr.configuration.bo.IStatBO;
import com.hr.configuration.domain.BenefitType;
import com.hr.configuration.domain.Statusconf;
import com.hr.io.domain.InmatchModel;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;

public class IEmpSalaryBenefitUpd extends ICheckAndInsert {
    String msgDbInsertError;
    String msgNotExist;
    String msgNotInited;
    String msgCardIDNotExist;
    String msgCardIDNotInited;
    String msgPensionNoNotInited;
    String msgHousingNoNotInited;
    String msgMedicalNoNotInited;
    String msgNoneOf5;
    String msgNoAmount;
    String msgNotExistBeneType;
    String msgDateValidFormat;
    String msgStartDateLessDB;

    public IEmpSalaryBenefitUpd() {
        this.msgDbInsertError = "数据库插入失贄1�7";
        this.msgNotExist = "编号为{0}的员工不存在";
        this.msgNotInited = "编号为{0}的员工社保尚未初始化";
        this.msgCardIDNotExist = "身份证号为{0}的员工不存在";
        this.msgCardIDNotInited = "身份证号为{0}的员工社保尚未初始化";
        this.msgPensionNoNotInited = "养�1�7�保险号为{0}的员工社保数据尚未初始化";
        this.msgHousingNoNotInited = "公积金号为{0}的员工社保数据尚未初始化";
        this.msgMedicalNoNotInited = "医疗保险号为{0}的员工社保数据尚未初始化";
        this.msgNoneOf5 = "员工编号|身份证号码|养�1�7�保险号|公积金号|医疗保险号请至少填写丄1�7顄1�7";
        this.msgNoAmount = "至少填写丄1�7项基敄1�7";
        this.msgNotExistBeneType = "名称为{0}的社保种类不存在";
        this.msgDateValidFormat = "生效年月为空或格式不正确";
        this.msgStartDateLessDB = "基数调整生效日期不能早于{0}";
    }

    public int[] insertTransmit(List insertList, CommonParameters commonParas) {
        Map empMap = getEmployeeMap();
        List empBenefitList = getEmpbenefitHashtable();
        int[] result = { 0, 0 };

        List<Empbenefit> insertEbList = insertList;
        List<Empbenefit> toUpdateList = new ArrayList();
        int hasTitle = 1 - commonParas.getInmatchModel().getImmNoTitle().intValue();
        Map objInsertMap = new HashMap();
        int rowNum = hasTitle + 1;
        for (Empbenefit bene : insertEbList) {
            objInsertMap.put(rowNum + "", new ObjAndRow(Integer.valueOf(rowNum), bene));
            ++rowNum;
        }

        Iterator iter = objInsertMap.entrySet().iterator();
        String empDistinctNo = null;
        Empbenefit bene = null;
        while (iter.hasNext()) {
            Empbenefit beneInDB = null;
            boolean chooseIdFlag = false;
            boolean choosedNoOrID = false;
            boolean valueFlag = false;

            Map.Entry entry = (Map.Entry) iter.next();
            ObjAndRow objAndRowTmp = (ObjAndRow) entry.getValue();
            rowNum = objAndRowTmp.rowNum.intValue();
            bene = objAndRowTmp.bene;
            empDistinctNo = (bene.getEmployee() != null) ? bene.getEmployee().getEmpDistinctNo()
                    : null;

            if (empDistinctNo != null) {
                chooseIdFlag = true;
                if (!empMap.containsKey(empDistinctNo)) {
                    commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                                new String[] { empDistinctNo });
                } else if (!empBenefitList.contains(empDistinctNo)) {
                    commonParas.addErrorMessage(this.msgNotInited, Integer.valueOf(rowNum),
                                                new String[] { empDistinctNo });
                } else {
                    beneInDB = ((Employee) empMap.get(empDistinctNo)).getBenefit();
                    choosedNoOrID = true;
                }

            }

            String empCardID = (bene.getEmployee() != null) ? bene.getEmployee()
                    .getEmpIdentificationNo() : null;
            if ((empCardID != null) && (!chooseIdFlag)) {
                chooseIdFlag = true;
                if (!empMap.containsKey(empCardID)) {
                    commonParas.addErrorMessage(this.msgCardIDNotExist, Integer.valueOf(rowNum),
                                                new String[] { empCardID });
                } else if (!empBenefitList.contains(empCardID)) {
                    commonParas.addErrorMessage(this.msgCardIDNotInited, Integer.valueOf(rowNum),
                                                new String[] { empCardID });
                } else {
                    beneInDB = ((Employee) empMap.get(empCardID)).getBenefit();
                    choosedNoOrID = true;
                }

            }

            if ((bene.getEbfPensionNo() != null) && (!choosedNoOrID) && (!chooseIdFlag)) {
                chooseIdFlag = true;
                beneInDB = ((Employee) empMap.get(bene.getEbfPensionNo())).getBenefit();
                if (beneInDB == null) {
                    commonParas.addErrorMessage(this.msgPensionNoNotInited,
                                                Integer.valueOf(rowNum), new String[] { bene
                                                        .getEbfPensionNo() });
                }
            }

            if ((bene.getEbfPensionNo() != null) && (choosedNoOrID)) {
                beneInDB.setEbfPensionNo(bene.getEbfPensionNo());
            }

            if ((bene.getEbfHousingNo() != null) && (!choosedNoOrID) && (!chooseIdFlag)) {
                chooseIdFlag = true;
                beneInDB = ((Employee) empMap.get(bene.getEbfHousingNo())).getBenefit();
                if (beneInDB == null) {
                    commonParas.addErrorMessage(this.msgHousingNoNotInited,
                                                Integer.valueOf(rowNum), new String[] { bene
                                                        .getEbfHousingNo() });
                }
            }

            if ((bene.getEbfHousingNo() != null) && (choosedNoOrID)) {
                beneInDB.setEbfHousingNo(bene.getEbfHousingNo());
            }

            if ((bene.getEbfMedicalNo() != null) && (!choosedNoOrID) && (!chooseIdFlag)) {
                chooseIdFlag = true;
                beneInDB = ((Employee) empMap.get(bene.getEbfMedicalNo())).getBenefit();
                if (beneInDB == null) {
                    commonParas.addErrorMessage(this.msgMedicalNoNotInited,
                                                Integer.valueOf(rowNum), new String[] { bene
                                                        .getEbfMedicalNo() });
                }
            }

            if ((bene.getEbfMedicalNo() != null) && (choosedNoOrID)) {
                beneInDB.setEbfMedicalNo(bene.getEbfMedicalNo());
            }

            String ebfStartMonth = bene.getEbfStartMonth();
            if (ebfStartMonth != null) {
                ebfStartMonth = ebfStartMonth.replaceAll("/", "-");
                ebfStartMonth = convertDateFormatToString(ebfStartMonth);
                if (ebfStartMonth == null) {
                    commonParas.addErrorMessage(this.msgDateValidFormat, Integer.valueOf(rowNum),
                                                new String[0]);
                }
            }

            if ((bene != null) && (beneInDB != null) && (bene.getEbfStartMonth() != null)) {
                if (Integer.parseInt(bene.getEbfStartMonth()) < Integer.parseInt(beneInDB
                        .getEbfStartMonth())) {
                    commonParas.addErrorMessage(this.msgStartDateLessDB, Integer.valueOf(rowNum),
                                                new String[] { beneInDB.getEbfStartMonth() });
                } else {
                    beneInDB.setEbfStartMonth(bene.getEbfStartMonth());
                    beneInDB.setEbfEndMonth(null);
                }

            }

            if (bene.getEbfPensionAmount() != null) {
                valueFlag = true;
                if (beneInDB != null) {
                    beneInDB.setEbfPensionAmount(bene.getEbfPensionAmount());
                }

            }

            if (bene.getEbfHousingAmount() != null) {
                valueFlag = true;
                if (beneInDB != null) {
                    beneInDB.setEbfHousingAmount(bene.getEbfHousingAmount());
                }

            }

            if (bene.getEbfInsuranceAmount() != null) {
                valueFlag = true;
                if (beneInDB != null) {
                    beneInDB.setEbfInsuranceAmount(bene.getEbfInsuranceAmount());
                }

            }

            if ((bene.getEbfComments() != null) && (beneInDB != null)) {
                beneInDB.setEbfComments(bene.getEbfComments());
            }

            if (!chooseIdFlag) {
                commonParas
                        .addErrorMessage(this.msgNoneOf5, Integer.valueOf(rowNum), new String[0]);
            }

            if (!valueFlag) {
                commonParas.addErrorMessage(this.msgNoAmount, Integer.valueOf(rowNum),
                                            new String[0]);
            }

            toUpdateList.add(beneInDB);
        }

        IEmpBenefitBo beneBo = (IEmpBenefitBo) SpringBeanFactory.getBean("empbenefitBo");
        if (!commonParas.getIoMessages().hasErrorMsg()) {
            for (Empbenefit tempBene : toUpdateList) {
                beneBo.insertBenefit(tempBene, commonParas.getCurrEmp().getId());
                result[0] += 1;
            }
        }

        return result;
    }

    public List<String> getEmpbenefitHashtable() {
        List beneHashtable = new ArrayList();
        IEmpBenefitDao beneDao = (IEmpBenefitDao) SpringBeanFactory.getBean("empbenefitDao");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empbenefit.class);
        detachedCriteria.setFetchMode("employee", FetchMode.JOIN);
        List beneList = beneDao.findByCriteria(detachedCriteria);
        Empbenefit bene = null;
        for (int i = 0; i < beneList.size(); ++i) {
            bene = (Empbenefit) beneList.get(i);
            beneHashtable.add(bene.getEmployee().getEmpDistinctNo());
            beneHashtable.add(bene.getEmployee().getEmpIdentificationNo());
            beneHashtable.add(bene.getEbfPensionNo());
            beneHashtable.add(bene.getEbfHousingNo());
            beneHashtable.add(bene.getEbfMedicalNo());
        }

        return beneHashtable;
    }

    public List<String> getEmpStatusHashtable() {
        List empStatusHashtable = new ArrayList();
        IStatBO statBO = (IStatBO) SpringBeanFactory.getBean("statBO");
        List statBOList = statBO.getAllTableStatus("employee");
        int statBOListSize = statBOList.size();
        Statusconf statusconf = null;
        for (int i = 0; i < statBOListSize; ++i) {
            statusconf = (Statusconf) statBOList.get(i);
            empStatusHashtable.add(statusconf.getStatusconfDesc());
        }
        return empStatusHashtable;
    }

    public Map<String, Employee> getEmployeeMap() {
        IEmployeeBo esaBo = (IEmployeeBo) SpringBeanFactory.getBean("empBo");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
        detachedCriteria.setFetchMode("benefit", FetchMode.JOIN);
        List empList = esaBo.findByCriteria(detachedCriteria);
        Map empMap = new HashMap();
        Employee emp = null;
        for (int i = 0; i < empList.size(); ++i) {
            emp = (Employee) empList.get(i);
            empMap.put(emp.getId(), emp);
            empMap.put(emp.getEmpDistinctNo(), emp);
            if (emp.getEmpIdentificationNo() != null) {
                empMap.put(emp.getEmpIdentificationNo(), emp);
            }
            if ((emp.getBenefit() != null) && (emp.getBenefit().getEbfPensionNo() != null)) {
                empMap.put(emp.getBenefit().getEbfPensionNo(), emp);
            }
            if ((emp.getBenefit() != null) && (emp.getBenefit().getEbfHousingNo() != null)) {
                empMap.put(emp.getBenefit().getEbfHousingNo(), emp);
            }
            if ((emp.getBenefit() != null) && (emp.getBenefit().getEbfMedicalNo() != null)) {
                empMap.put(emp.getBenefit().getEbfMedicalNo(), emp);
            }
        }

        return empMap;
    }

    public Map<String, Empbenefit> getEmpbenefitMap() {
        Map beneMap = new HashMap();
        IEmpBenefitDao beneDao = (IEmpBenefitDao) SpringBeanFactory.getBean("empbenefitDao");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Empbenefit.class);
        detachedCriteria.setFetchMode("employee", FetchMode.JOIN);
        List beneList = beneDao.findByCriteria(detachedCriteria);
        Empbenefit bene = null;
        for (int i = 0; i < beneList.size(); ++i) {
            bene = (Empbenefit) beneList.get(i);
            beneMap.put(bene.getEbfId(), bene);
        }

        return beneMap;
    }

    public List<BenefitType> getBenefittypeList() {
        IEmpBenefitDao beneDao = (IEmpBenefitDao) SpringBeanFactory.getBean("empbenefitDao");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BenefitType.class);
        List typeList = beneDao.findByCriteria(detachedCriteria);

        return typeList;
    }

    public BenefitType getBeneType(List<BenefitType> beneTypeList, String temp) {
        BenefitType tempType = null;
        for (BenefitType type : beneTypeList) {
            if ((type.getBenefitTypeName().indexOf(temp) > -1)
                    || (type.getBenefitTypeDescription().indexOf(temp) > -1)) {
                tempType = type;
                break;
            }
        }

        return tempType;
    }

    public String convertDateFormatToString(String content) {
        if ((content == null) || ("".equals(content))) {
            return null;
        }
        String result = null;

        String style1 = "\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}";

        String style2 = "\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}";

        String style3 = "\\d{4}-\\d{1,2}-\\d{1,2}";

        String style4 = "\\d{4}-\\d{1,2}";
        SimpleDateFormat form = null;
        if ((matchStyle(content, style1)) || (matchStyle(content, style2))
                || (matchStyle(content, style3)) || (matchStyle(content, style4))) {
            String[] tempArray = content.split("-");
            result = tempArray[0] + "" + tempArray[1];
        } else {
            String style5 = "[0-9]*";
            if ((matchStyle(content, style5)) && (content.length() == 6))
                result = content;
            else if ((matchStyle(content, style5)) && (content.length() == 5)) {
                result = content.substring(0, 4) + "0" + content.substring(4);
            }
        }

        return result;
    }

    private boolean matchStyle(String content, String style) {
        Pattern pattern = Pattern.compile(style);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    private boolean matcherDateString(String content) {
        String style1 = "\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}";

        String style2 = "\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}";

        String style3 = "\\d{1,2}:\\d{1,2}:\\d{1,2}";

        String style4 = "\\d{1,2}:\\d{1,2}";
        boolean flag = (matchStyle(content, style1)) || (matchStyle(content, style2))
                || (matchStyle(content, style3)) || (matchStyle(content, style4));
        return flag;
    }

    private String null2zero(String content) {
        String temp = "0";
        if ((content == null) || ("".equals(content.trim()))) {
            return temp;
        }

        return content;
    }

    private class ObjAndRow {
        public Integer rowNum;
        public Empbenefit bene;

        ObjAndRow(Integer rowNum, Empbenefit bene) {
            this.rowNum = rowNum;
            this.bene = bene;
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.extend.IEmpSalaryBenefitUpd JD-Core Version: 0.5.4
 */