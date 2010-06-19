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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;

public class IEmpSalaryBenefit extends ICheckAndInsert {
    String msgDbInsertError;
    String msgNotExist;
    String msgInited;
    String msgNotExistBeneType;
    String msgDateValidFormat;
    String msgNoAmount;

    public IEmpSalaryBenefit() {
        this.msgDbInsertError = "数据库插入失败！";
        this.msgNotExist = "编号为{0}的员工不存在！";
        this.msgInited = "重复记录：编号为{0}的员工社保已经初始化！";
        this.msgNotExistBeneType = "名称为{0}的社保种类不存在！";
        this.msgDateValidFormat = "生效年月为空或格式不正确！";
        this.msgNoAmount = "至少填写一项基数！";
    }

    public int[] insertTransmit(List insertList, CommonParameters commonParas) {
        Map empMap = getEmployeeMap();
        List empBenefitList = getEmpbenefitHashtable();
        List beneTypeList = getBenefittypeList();
        int[] result = { 0, 0 };

        List<Empbenefit> insertEbList = insertList;
        int hasTitle = 1 - commonParas.getInmatchModel().getImmNoTitle().intValue();
        Map objInsertMap = new HashMap();
        int rowNum = hasTitle + 1;
        for (Empbenefit bene : insertEbList) {
            objInsertMap.put(bene.getEmployee().getEmpDistinctNo(), new ObjAndRow(Integer
                    .valueOf(rowNum++), bene));
        }

        Iterator iter = insertEbList.iterator();
        String empDistinctNo = null;
        Empbenefit bene = null;
        BenefitType beneType = null;
        rowNum = hasTitle + 1;
        while (iter.hasNext()) {
            bene = (Empbenefit) iter.next();
            empDistinctNo = bene.getEmployee().getEmpDistinctNo();

            if (empDistinctNo != null) {
                if (!empMap.containsKey(empDistinctNo)) {
                    commonParas.addErrorMessage(this.msgNotExist, Integer.valueOf(rowNum),
                                                new String[] { empDistinctNo });
                } else {
                    if (empBenefitList.contains(empDistinctNo)) {
                        commonParas.addMessage(this.msgInited, Integer.valueOf(1), Integer
                                .valueOf(rowNum), new String[] { empDistinctNo });
                        iter.remove();
                        ++rowNum;
                        result[1] += 1;
                    }

                    bene.getEmployee().setId(((Employee) empMap.get(empDistinctNo)).getId());
                }

            }

            String beneTypeName = bene.getEmployee().getEmpBenefitType().getBenefitTypeName();
            if (beneTypeName != null) {
                beneType = getBeneType(beneTypeList, beneTypeName);
                if (beneType == null) {
                    commonParas.addErrorMessage(this.msgNotExistBeneType, Integer.valueOf(rowNum),
                                                new String[] { beneTypeName });
                }

                bene.setBeneType(beneType.getId());
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

            if ((bene.getEbfPensionAmount() == null) && (bene.getEbfInsuranceAmount() == null)
                    && (bene.getEbfHousingAmount() == null)) {
                commonParas.addErrorMessage(this.msgNoAmount, Integer.valueOf(rowNum),
                                            new String[0]);
            }

            ++rowNum;
        }

        IEmpBenefitBo beneBo = (IEmpBenefitBo) SpringBeanFactory.getBean("empbenefitBo");
        if (!commonParas.getIoMessages().hasErrorMsg()) {
            for (Empbenefit tempBene : insertEbList) {
                beneBo.insertBenefit(tempBene, commonParas.getCurrEmp().getId());
                result[0] += 1;
            }
        }

        return result;
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
        List empList = esaBo.findByCriteria(detachedCriteria);
        Map empMap = new HashMap();
        Employee emp = null;
        for (int i = 0; i < empList.size(); ++i) {
            emp = (Employee) empList.get(i);
            empMap.put(emp.getEmpDistinctNo(), emp);
        }
        return empMap;
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
        }

        return beneHashtable;
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
 * com.hr.io.extend.IEmpSalaryBenefit JD-Core Version: 0.5.4
 */