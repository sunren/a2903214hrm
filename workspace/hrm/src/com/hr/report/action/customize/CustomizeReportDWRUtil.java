package com.hr.report.action.customize;

import com.hr.base.BaseAction;
import com.hr.report.bo.CustomizeReportJoinDefBo;
import com.hr.report.bo.CustomizeReportModuleDefBo;
import com.hr.report.bo.CustomizeReportSetsDefBo;
import com.hr.report.domain.ReportJoinDef;
import com.hr.report.domain.ReportModuleDef;
import com.hr.report.domain.ReportSetsDef;
import com.hr.security.bo.AuthBo;
import com.hr.security.domain.Authority;
import com.hr.spring.SpringBeanFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomizeReportDWRUtil extends BaseAction {
    public List findReportsDefByModuleAndTable(Integer moduleId, String tableName, Integer flag) {
        if ((flag.intValue() == 0) || (flag.intValue() == 1)) {
            return getList(moduleId, tableName, flag);
        }
        List result = new ArrayList();
        List<ReportJoinDef> list = findAssistanceTable(tableName);

        for (ReportJoinDef r : list) {
            String assistanceTable = r.getReportJoinDefAssistantTable();
            List tempList = getList(moduleId, assistanceTable, Integer.valueOf(0));
            result.addAll(tempList);
        }

        result.addAll(getList(moduleId, tableName, Integer.valueOf(1)));
        return result;
    }

    private List getList(Integer moduleId, String tableName, Integer flag) {
        List list = null;
        CustomizeReportSetsDefBo bo = (CustomizeReportSetsDefBo) SpringBeanFactory
                .getBean("customizeReportSetsDefBo");
        list = bo.getReportSetsDefByModuleAndTableName(moduleId, tableName, flag);
        return list;
    }

    private List findAssistanceTable(String tableName) {
        List list = null;
        CustomizeReportJoinDefBo joinBo = (CustomizeReportJoinDefBo) SpringBeanFactory
                .getBean("customizeReportJoinDefBo");
        list = joinBo.findJoinDefListByMainTableName(tableName);
        return list;
    }

    public ReportJoinDef findByMainTableAndAssistanceTable(String mainTable, String assistanceTable) {
        ReportJoinDef result = null;
        CustomizeReportJoinDefBo joinBo = (CustomizeReportJoinDefBo) SpringBeanFactory
                .getBean("customizeReportJoinDefBo");
        result = joinBo.findByMainTableAndAssistanceTable(mainTable, assistanceTable);
        return result;
    }

    public List<ReportJoinDef> loadAll() {
        CustomizeReportJoinDefBo joinBo = (CustomizeReportJoinDefBo) SpringBeanFactory
                .getBean("customizeReportJoinDefBo");
        return joinBo.loadAll();
    }

    public List<ReportModuleDef> loadReportModuleDefs(String moduleId, String tableName, String flag) {
        CustomizeReportModuleDefBo moduleDefBo = (CustomizeReportModuleDefBo) SpringBeanFactory
                .getBean("customizeReportModuleDefBo");
        List moduleList = moduleDefBo.list(moduleId, flag);
        List<ReportJoinDef> joinList = findAssistanceTable(tableName);

        List result = new ArrayList();
        for (Iterator i$ = moduleList.iterator(); i$.hasNext();) {
            ReportModuleDef def = (ReportModuleDef) i$.next();
            if ((tableName.equalsIgnoreCase(def.getReportModuleDefTable()))
                    && (def.getReportModuleDefFlag().equals(Integer.valueOf(1)))) {
                result.add(def);
            }

            for (ReportJoinDef join : joinList)
                if (def.getReportModuleDefTable()
                        .equalsIgnoreCase(join.getReportJoinDefAssistantTable()))
                    result.add(def);
        }

        ReportModuleDef def;
        List tmpResult = new ArrayList();
        for (Iterator i$ = result.iterator(); i$.hasNext();) {
            ReportModuleDef mo = (ReportModuleDef) i$.next();
            if (!tableName.equalsIgnoreCase(mo.getReportModuleDefTable())) {
                List<ReportJoinDef> joList = findAssistanceTable(mo.getReportModuleDefTable());
                for (ReportJoinDef tmp : joList) {
                    ReportModuleDef de = moduleDefBo.getReportModuleDef(tmp
                            .getReportJoinDefAssistantTable());
                    if (!mo.getReportModuleDefTable()
                            .equalsIgnoreCase(de.getReportModuleDefTable())) {
                        de.setReportModuleDefTable(de.getReportModuleDefTable() + "&"
                                + mo.getReportModuleDefTable());
                        tmpResult.add(de);
                    }
                }
            }
        }

        ReportModuleDef mo;
        result.addAll(tmpResult);

        return result;
    }

    public List<ReportSetsDef> loadReportSetsDefByTableName(String tableName) {
        CustomizeReportSetsDefBo bo = (CustomizeReportSetsDefBo) SpringBeanFactory
                .getBean("customizeReportSetsDefBo");
        return bo.getReportSetsDefByTableName(tableName);
    }

    public List<Authority> getAuthList(String prfix) {
        AuthBo authBo = (AuthBo) SpringBeanFactory.getBean("authService");
        return authBo.getAuthList(prfix);
    }

    public ReportModuleDef getReportModuleDef(String tableName) {
        CustomizeReportModuleDefBo moduleDefBo = (CustomizeReportModuleDefBo) SpringBeanFactory
                .getBean("customizeReportModuleDefBo");
        return moduleDefBo.getReportModuleDef(tableName);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.action.customize.CustomizeReportDWRUtil JD-Core Version: 0.5.4
 */