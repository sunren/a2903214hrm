package com.hr.base.io;

import com.hr.base.Constants;
import com.hr.compensation.action.SalaryConfigAssist;
import com.hr.compensation.bo.IEmpSalaryAcctitemsBo;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryadj;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.configuration.domain.Department;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.MyTools;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Exportinfo implements Constants {
    public List[] getSalarySheetAndTitle(List<Empsalarypay> decryResult,
            IEmpSalaryAcctitemsBo esaitemsBo) {
        Set nameSet = new HashSet();
        Set versionSet = new HashSet();

        Empsalaryacct acct = null;
        Empsalaryacctversion version = null;
        for (int i = 0; i < decryResult.size(); ++i) {
            acct = ((Empsalarypay) decryResult.get(i)).getEspEsavId().getEsavEsac();
            if (nameSet.add(acct.getEsacName())) {
                version = ((Empsalarypay) decryResult.get(i)).getEspEsavId();
                versionSet.add(version);
            }
        }

        List[] titlesList = new List[versionSet.size()];
        Iterator versionIterator = versionSet.iterator();
        int index = 0;
        while (versionIterator.hasNext()) {
            List stringList = new ArrayList();
            version = (Empsalaryacctversion) versionIterator.next();
            stringList.add(version.getEsavEsac().getEsacName());
            stringList = getTitle(version.getId(), esaitemsBo, stringList);
            titlesList[index] = stringList;
            ++index;
        }

        return titlesList;
    }

    public List[] getPayslipTitle(List<Empsalarypay> decryResult, IEmpSalaryAcctitemsBo esaitemsBo) {
        int assistListSize = decryResult.size();

        Set bigTitleNameSet = new HashSet();

        List payrollTitleList = new ArrayList();

        String deptName = null;
        Empsalaryacct acct = null;
        Empsalaryacctversion version = null;
        for (int i = 0; i < assistListSize; ++i) {
            deptName = ((Empsalarypay) decryResult.get(i)).getEspDept().getDepartmentName();
            acct = ((Empsalarypay) decryResult.get(i)).getEspEsavId().getEsavEsac();
            version = ((Empsalarypay) decryResult.get(i)).getEspEsavId();
            String bigTitleName = deptName + "," + acct.getEsacName() + "," + version.getId();
            if (bigTitleNameSet.add(bigTitleName)) {
                payrollTitleList.add(bigTitleName);
            }
        }
        Collections.sort(payrollTitleList);
        List[] titlesList = new List[payrollTitleList.size()];
        for (int i = 0; i < payrollTitleList.size(); ++i) {
            List stringList = new ArrayList();
            String[] array = ((String) payrollTitleList.get(i)).split(",");

            stringList.add(array[0]);

            stringList.add(array[1]);

            stringList = getPayslipTitle(array[2], esaitemsBo, stringList);
            titlesList[i] = stringList;
        }
        return titlesList;
    }

    public List[] getSalaryadjSheetAndTitle(List<Empsalaryadj> result,
            IEmpSalaryAcctitemsBo esaitemsBo) {
        int adjListSize = result.size();
        Set stringSet = new HashSet();
        Set empsalaryadjSet = new HashSet();
        for (int i = 0; i < adjListSize; ++i) {
            Empsalaryadj empsalaryAdj = (Empsalaryadj) result.get(i);
            String sheetName = empsalaryAdj.getEsaEsavIdCur().getEsavEsac().getEsacName() + "-->"
                    + empsalaryAdj.getEsaEsavIdPro().getEsavEsac().getEsacName();
            if (!stringSet.add(sheetName))
                continue;
            empsalaryadjSet.add(empsalaryAdj);
        }

        int totalSize = empsalaryadjSet.size();
        List[] titlesList = new List[totalSize];

        Iterator adjIterator = empsalaryadjSet.iterator();

        int index = 0;
        while (adjIterator.hasNext()) {
            List stringList = new ArrayList();
            Empsalaryadj empsalaryAdj = (Empsalaryadj) adjIterator.next();

            String sheetName = empsalaryAdj.getEsaEsavIdCur().getEsavEsac().getEsacName() + "-->"
                    + empsalaryAdj.getEsaEsavIdPro().getEsavEsac().getEsacName();

            stringList.add(sheetName);

            stringList = getEmpsalaryadjTitle(empsalaryAdj, stringList);
            titlesList[index] = stringList;
            ++index;
        }

        return titlesList;
    }

    public List[] getSheetAndTitle(List<SalaryConfigAssist> salaryConfigAssistList,
            IEmpSalaryAcctitemsBo esaitemsBo) {
        int assistListSize = salaryConfigAssistList.size();

        Set nameSet = new HashSet();
        Set assistSet = new HashSet();

        for (int i = 0; i < assistListSize; ++i) {
            SalaryConfigAssist assist = (SalaryConfigAssist) salaryConfigAssistList.get(i);
            if (nameSet.add(assist.getAcceptName())) {
                assistSet.add(assist);
            }
        }
        int totalSize = assistSet.size();
        List[] titlesList = new List[totalSize];
        Iterator acctIterator = assistSet.iterator();
        int index = 0;
        while (acctIterator.hasNext()) {
            List stringList = new ArrayList();
            SalaryConfigAssist assist = (SalaryConfigAssist) acctIterator.next();
            stringList.add(assist.getAcceptName());
            stringList = getTitle(assist.getSalaryAccept(), esaitemsBo, stringList);
            titlesList[index] = stringList;
            ++index;
        }

        return titlesList;
    }

    public List[] getSalaryResult(List<Empsalarypay> decryResult, List[] titles) {
        int size = titles.length;

        String[] acceptNameArray = new String[size];
        List[] result = new ArrayList[size];

        for (int i = 0; i < size; ++i) {
            acceptNameArray[i] = ((String) titles[i].get(0));
            result[i] = new ArrayList();
        }

        for (int i = 0; i < decryResult.size(); ++i) {
            Empsalarypay empsalarypay = (Empsalarypay) decryResult.get(i);
            for (int j = 0; j < size; ++j) {
                if (empsalarypay.getEspEsavId().getEsavEsac().getEsacName() == acceptNameArray[j]) {
                    result[j].add(empsalarypay);
                    break;
                }
            }
        }
        return result;
    }

    public List[] getPayslipResult(List<Empsalarypay> decryResult, List[] titles) {
        int size = titles.length;

        String[] deptNameArray = new String[size];

        String[] acceptNameArray = new String[size];
        List[] result = new ArrayList[size];

        for (int i = 0; i < size; ++i) {
            deptNameArray[i] = ((String) titles[i].get(0));
            acceptNameArray[i] = ((String) titles[i].get(1));
            result[i] = new ArrayList();
        }

        for (int i = 0; i < decryResult.size(); ++i) {
            Empsalarypay empsalarypay = (Empsalarypay) decryResult.get(i);
            for (int j = 0; j < size; ++j) {
                if ((!empsalarypay.getEspDept().getDepartmentName().equals(deptNameArray[j]))
                        || (!empsalarypay.getEspEsavId().getEsavEsac().getEsacName()
                                .equals(acceptNameArray[j])))
                    continue;
                result[j].add(empsalarypay);
                break;
            }
        }

        return result;
    }

    public List[] getSalaryadjResult(List<Empsalaryadj> exportResult, List[] titles) {
        int size = titles.length;

        String[] salaryadjName = new String[size];
        List[] result = new ArrayList[size];

        for (int i = 0; i < size; ++i) {
            salaryadjName[i] = ((String) titles[i].get(0));
            result[i] = new ArrayList();
        }

        for (int i = 0; i < exportResult.size(); ++i) {
            Empsalaryadj empsalaryAdj = (Empsalaryadj) exportResult.get(i);
            String sheetName = empsalaryAdj.getEsaEsavIdCur().getEsavEsac().getEsacName() + "-->"
                    + empsalaryAdj.getEsaEsavIdPro().getEsavEsac().getEsacName();
            for (int j = 0; j < size; ++j) {
                if (sheetName.equals(salaryadjName[j])) {
                    result[j].add(empsalaryAdj);
                    break;
                }
            }
        }
        return result;
    }

    public List[] getConfigResult(List<SalaryConfigAssist> salaryConfigAssistList, List[] titles) {
        int size = titles.length;

        String[] acceptNameArray = new String[size];
        List[] result = new ArrayList[size];

        for (int i = 0; i < size; ++i) {
            acceptNameArray[i] = ((String) titles[i].get(0));
            result[i] = new ArrayList();
        }

        for (int i = 0; i < salaryConfigAssistList.size(); ++i) {
            SalaryConfigAssist salaryConfigAssist = (SalaryConfigAssist) salaryConfigAssistList
                    .get(i);
            for (int j = 0; j < size; ++j) {
                if (salaryConfigAssist.getAcceptName() == acceptNameArray[j]) {
                    result[j].add(salaryConfigAssist);
                    break;
                }
            }
        }
        return result;
    }

    private List<BigDecimal> getSalaryConfigList(Empsalaryconfig config, String key)
            throws Exception {
        List list = new ArrayList();
        Class ownerClass = config.getClass();

        for (int i = 1; i <= 48; ++i) {
            Method confMethod = ownerClass.getMethod("getEscColumn" + i, new Class[0]);
            list.add(MyTools.vigenere((BigDecimal) confMethod.invoke(config, new Object[0]), key,
                                      MyTools.DECRYPT_MODE));
        }
        return list;
    }

    public String getEmpStaus(Integer i) {
        switch (i.intValue()) {
        case 0:
            return "离职";
        case 1:
            return "在职";
        }
        return null;
    }

    private List<String> getTitle(String versionId, IEmpSalaryAcctitemsBo esaitemsBo,
            List<String> stringList) {
        stringList.add("工号");
        stringList.add("姓名");
        stringList.add("部门");
        stringList.add("职位");
        stringList.add("用工形式");
        stringList.add("工作地区");
        stringList.add("员工状态");
        stringList.add("银行帐号");
        stringList.add("银行开户行");
        stringList.add("成本中心");
        stringList.add("薪资级别");
        stringList.add("薪资帐套");
        List itemsList = esaitemsBo.getItemsByAcctversion(versionId);
        int size = itemsList.size();
        for (int i = 0; i < size; ++i) {
            String string = "A" + (i + 1) + "-"
                    + ((Empsalaryacctitems) itemsList.get(i)).getEsaiEsdd().getEsddName();

            stringList.add(string);
        }
        return stringList;
    }

    private List<String> getPayslipTitle(String versionId, IEmpSalaryAcctitemsBo esaitemsBo,
            List<String> stringList) {
        stringList.add("工号");
        stringList.add("姓名");
        stringList.add("用工形式");
        stringList.add("经理姓名");
        stringList.add("状态");
        stringList.add("银行帐号");
        stringList.add("薪资级别");

        List itemsList = esaitemsBo.getItemsByAcctversion(versionId);
        int size = itemsList.size();
        for (int i = 0; i < size; ++i) {
            String string = ((Empsalaryacctitems) itemsList.get(i)).getEsaiEsdd().getEsddName();
            stringList.add(string);
        }
        return stringList;
    }

    private List<String> getEmpsalaryadjTitle(Empsalaryadj empSalaryadj, List<String> stringList) {
        stringList.add("工号");
        stringList.add("姓名");
        stringList.add("部门");
        stringList.add("用工形式");
        stringList.add("工作地区");
        stringList.add("加薪生效日期");
        stringList.add("本次加薪幅度");
        stringList.add("加薪备注");
        stringList.add("加薪状况");
        stringList.add("调薪前薪资级别");
        stringList.add("调薪后薪资级别");
        stringList.add("调薪前薪资帐户");
        stringList.add("调薪后薪资帐户");

        IEmpSalaryAcctitemsBo esaitemsBo = (IEmpSalaryAcctitemsBo) SpringBeanFactory
                .getBean("empsalaryacctitemsBo");

        List itemsCur = esaitemsBo.getItemsByAcctversion(empSalaryadj.getEsaEsavIdCur().getId());
        int sizeCur = itemsCur.size();
        for (int i = 0; i < sizeCur; ++i) {
            int index = i + 1;
            String titleName = "A" + index + "-"
                    + ((Empsalaryacctitems) itemsCur.get(i)).getEsaiEsdd().getEsddName();
            stringList.add(titleName);
        }

        for (int max = sizeCur; max < 48; ++max) {
            int index = max + 1;
            String titleName = "A" + index;
            stringList.add(titleName);
        }

        List itemsPro = esaitemsBo.getItemsByAcctversion(empSalaryadj.getEsaEsavIdPro().getId());
        int sizePro = itemsPro.size();
        for (int i = 0; i < sizePro; ++i) {
            int index = i + 1;
            String titleName = "B" + index + "-"
                    + ((Empsalaryacctitems) itemsPro.get(i)).getEsaiEsdd().getEsddName();
            stringList.add(titleName);
        }

        for (int max = sizePro; max < 48; ++max) {
            int index = max + 1;
            String titleName = "B" + index;
            stringList.add(titleName);
        }

        return stringList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.base.io.Exportinfo JD-Core Version: 0.5.4
 */