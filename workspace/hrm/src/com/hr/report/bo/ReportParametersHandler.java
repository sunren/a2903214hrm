package com.hr.report.bo;

import com.hr.report.domain.ReportDef;
import com.hr.report.domain.ReportJoinDef;
import com.hr.report.domain.ReportParams;
import com.hr.report.domain.ReportSets;
import com.hr.report.domain.ReportSetsDef;
import com.hr.spring.SpringBeanFactory;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.eclipse.birt.report.model.api.ElementFactory;
import org.eclipse.birt.report.model.api.OdaDataSetHandle;
import org.eclipse.birt.report.model.api.ScalarParameterHandle;
import org.eclipse.birt.report.model.api.activity.SemanticException;

public class ReportParametersHandler {
    private static String[] columns;

    public static String execute(ReportDef reportDef, List<ReportParams> params,
            List<ReportSets> reportSets, String mainTable) {
        String sql = "";
        String reportSetsSQL = "";
        String reportFromSQL = " from " + mainTable + " ";
        String reportParamsSQL = "";
        String reportJoinSQL = "";
        String reportGroupSQL = "";

        Set<String> reportSourceSet = new LinkedHashSet<String>();

        if ((reportSets == null) || (reportSets.isEmpty())) {
            return "";
        }

        CustomizeReportParamsBo paramsBo = (CustomizeReportParamsBo) SpringBeanFactory
                .getBean("customizeReportParamsBo");
        CustomizeReportSetsBo reportSetsBo = (CustomizeReportSetsBo) SpringBeanFactory
                .getBean("customizeReportSetsBo");
        CustomizeReportSetsDefBo reportSetsDefBo = (CustomizeReportSetsDefBo) SpringBeanFactory
                .getBean("customizeReportSetsDefBo");
        CustomizeReportJoinDefBo reportJoinDefBo = (CustomizeReportJoinDefBo) SpringBeanFactory
                .getBean("customizeReportJoinDefBo");

        List<ReportJoinDef> joinList = reportJoinDefBo.findJoinDefListByMainTableName(mainTable);

        List tempJoin = new ArrayList();
        for (ReportJoinDef def : joinList) {
            List temp = reportJoinDefBo.findJoinDefListByMainTableName(def
                    .getReportJoinDefMainTable());
            tempJoin.addAll(temp);
        }
        joinList.addAll(tempJoin);
        removeDuplicate(joinList);

        int columnsLen = reportSets.size();
        columns = new String[columnsLen];
        ReportSets set = null;
        ReportSetsDef setDef = null;
        ReportJoinDef joinDef = null;
        for (int i = 0; i < columnsLen; ++i) {
            set = (ReportSets) reportSets.get(i);
            if (StringUtils.isEmpty(set.getFieldName())) {
                continue;
            }
            columns[i] = set.getReportSetsDesc();

            if (StringUtils.isNotEmpty(set.getReportSetsFunction()))
                reportSetsSQL = reportSetsSQL + set.getReportSetsFunction() + "("
                        + set.getFieldName() + "),";
            else {
                reportSetsSQL = reportSetsSQL + set.getFieldName() + ",";
            }

            String group = set.getReportSetsGroupFunction();
            if ("group".equalsIgnoreCase(group))
                reportGroupSQL = reportGroupSQL + set.getFieldName() + ",";
            else if (!"nogroup".equalsIgnoreCase(group)) {
                reportGroupSQL = reportGroupSQL + group + "(" + set.getFieldName() + "),";
            }

            if (set.getSource().indexOf("&") != -1) {
                String[] splits = set.getSource().split("&");
                joinDef = reportJoinDefBo.findByMainTableAndAssistanceTable(mainTable, splits[1]);
                String tmp = " left join " + joinDef.getReportJoinDefAssistantTable() + " on "
                        + joinDef.getReportJoinDefMainJoin() + "="
                        + joinDef.getReportJoinDefAssistantJoin() + " ";
                reportSourceSet.add(tmp);

                joinDef = reportJoinDefBo.findByMainTableAndAssistanceTable(splits[1], splits[0]);
                String tmp2 = " left join " + joinDef.getReportJoinDefAssistantTable() + " on "
                        + joinDef.getReportJoinDefMainJoin() + "="
                        + joinDef.getReportJoinDefAssistantJoin() + " ";
                reportSourceSet.add(tmp2);
            } else if (!mainTable.equalsIgnoreCase(set.getSource())) {
                joinDef = reportJoinDefBo.findByMainTableAndAssistanceTable(mainTable, set
                        .getSource());
                String tmp = " left join " + joinDef.getReportJoinDefAssistantTable() + " on "
                        + joinDef.getReportJoinDefMainJoin() + "="
                        + joinDef.getReportJoinDefAssistantJoin() + " ";
                reportSourceSet.add(tmp);
            }

            setDef = reportSetsDefBo.getReportSetsDef(set.getFieldName());
            set.setReportSetsReportDef(reportDef);
            set.setReportSetsReportSetsDef(setDef);
            reportSetsBo.addReportSets(set);
        }

        int len = params.size();
        ReportParams param = null;
        for (int i = 0; i < len; ++i) {
            param = (ReportParams) params.get(i);
            if (StringUtils.isEmpty(param.getFieldName())) {
                continue;
            }

            String tempValue = param.getDefaultValue();
            if (StringUtils.isNotEmpty(tempValue)) {
                if (tempValue.indexOf(",") != -1) {
                    String[] splits = tempValue.split("\\,");
                    param.setReportParamsRangeLow(splits[0]);
                    param.setReportParamsRangeHigh(splits[1]);
                } else {
                    param.setReportParamsRangeLow(tempValue);
                }

            }

            String type = param.getReportParamsCondition();
            String and = (i == len - 1) ? " " : " and ";
            reportParamsSQL = reportParamsSQL + param.getFieldName() + getCondition(type) + and;

            if (param.getSource().indexOf("&") != -1) {
                String[] splits = param.getSource().split("&");
                joinDef = reportJoinDefBo.findByMainTableAndAssistanceTable(mainTable, splits[1]);
                String tmp = " left join " + joinDef.getReportJoinDefAssistantTable() + " on "
                        + joinDef.getReportJoinDefMainJoin() + "="
                        + joinDef.getReportJoinDefAssistantJoin() + " ";
                reportSourceSet.add(tmp);

                joinDef = reportJoinDefBo.findByMainTableAndAssistanceTable(splits[1], splits[0]);
                String tmp2 = " left join " + joinDef.getReportJoinDefAssistantTable() + " on "
                        + joinDef.getReportJoinDefMainJoin() + "="
                        + joinDef.getReportJoinDefAssistantJoin() + " ";
                reportSourceSet.add(tmp2);
            } else if (!mainTable.equalsIgnoreCase(param.getSource())) {
                joinDef = reportJoinDefBo.findByMainTableAndAssistanceTable(mainTable, param
                        .getSource());
                String tmp = " left join " + joinDef.getReportJoinDefAssistantTable() + " on "
                        + joinDef.getReportJoinDefMainJoin() + "="
                        + joinDef.getReportJoinDefAssistantJoin() + " ";
                reportSourceSet.add(tmp);
            }

            setDef = reportSetsDefBo.getReportSetsDef(param.getFieldName());
            param.setReportSetsDef(setDef);
            param.setReportDef(reportDef);
            paramsBo.addReportParams(param);
        }

        Iterator it = reportSourceSet.iterator();
        while (it.hasNext()) {
            reportJoinSQL = reportJoinSQL + (String) it.next();
        }

        String where = "";
        if (StringUtils.isNotEmpty(reportParamsSQL)) {
            where = where + " where " + formatSQL(reportParamsSQL);
        }

        String group = "";
        if (StringUtils.isNotEmpty(reportGroupSQL)) {
            group = group + " group by " + formatSQL(reportGroupSQL);
        }
        sql = "select " + formatSQL(reportSetsSQL) + " " + formatSQL(reportFromSQL) + reportJoinSQL
                + where + group;
        System.out.println("=========" + sql + "=========");
        return sql;
    }

    public static List<ScalarParameterHandle> createReportParams(ElementFactory elementFactory,
            OdaDataSetHandle handle, String reportId) throws SemanticException {
        List result = new ArrayList();
        CustomizeReportParamsBo paramBo = (CustomizeReportParamsBo) SpringBeanFactory
                .getBean("customizeReportParamsBo");
        List<ReportParams> params = paramBo.findReportParams(reportId);
        String condition = null;
        ScalarParameterHandle temp = null;
        String code = handle.getQueryText();
        String codeGroup = "";
        if (code.indexOf("group") != -1) {
            codeGroup = code.split("group")[1];
            code = code.split("group")[0];
        }
        String[] codeArray = code.split("\\?");
        int paramPosition = 0;
        for (ReportParams param : params) {
            condition = param.getReportParamsCondition().trim();
            temp = elementFactory.newScalarParameter(param.getReportSetsDef()
                    .getReportSetsDefField());
            temp.setIsRequired(true);
            temp.setValueType("static");
            temp.setDataSetName("Data Set");

            if (("contains".equalsIgnoreCase(param.getReportParamsCondition()))
                    || ("notContains".equalsIgnoreCase(param.getReportParamsCondition()))) {
                codeArray[paramPosition] = (((paramPosition == 0) ? "\"" : " + \"")
                        + codeArray[paramPosition] + " '%\"" + " + " + "params[\""
                        + param.getReportSetsDef().getReportSetsDefField() + "\"]" + " + \"%'\"");
            } else if ("between".equalsIgnoreCase(param.getReportParamsCondition())) {
                if ((param.getReportSetsDef().getReportSetsDefType().equalsIgnoreCase("date"))
                        || (param.getReportSetsDef().getReportSetsDefType()
                                .equalsIgnoreCase("text")))
                    codeArray[paramPosition] = (((paramPosition == 0) ? "\"" : " + \"'")
                            + codeArray[paramPosition] + "'\" + " + "params[\""
                            + param.getReportSetsDef().getReportSetsDefField() + "\"]+\"'\"");
                else {
                    codeArray[paramPosition] = (((paramPosition == 0) ? "\"" : " + \"")
                            + codeArray[paramPosition] + "\" + " + "params[\""
                            + param.getReportSetsDef().getReportSetsDefField() + "\"]");
                }

                ScalarParameterHandle temp2 = elementFactory.newScalarParameter(param
                        .getReportSetsDef().getReportSetsDefField()
                        + "2");
                temp2.setAllowBlank(true);
                temp2.setAllowNull(true);
                temp2.setValueType("static");
                temp2.setDataSetName("Data Set");
                temp2.setDefaultValue(param.getReportParamsRangeHigh());
                temp2.setName(param.getReportSetsDef().getReportSetsDefField() + "2");

                ++paramPosition;
                if ((param.getReportSetsDef().getReportSetsDefType().equalsIgnoreCase("date"))
                        || (param.getReportSetsDef().getReportSetsDefType()
                                .equalsIgnoreCase("text")))
                    codeArray[paramPosition] = (" + \"" + codeArray[paramPosition] + "'\""
                            + " + params[\"" + param.getReportSetsDef().getReportSetsDefField() + "2\"]+\"'\"");
                else {
                    codeArray[paramPosition] = (" + \"" + codeArray[paramPosition] + "\""
                            + " + params[\"" + param.getReportSetsDef().getReportSetsDefField() + "2\"]");
                }

                if (StringUtils.isNotEmpty(param.getReportParamsRangeHigh())) {
                    temp2.setAllowBlank(false);
                    temp2.setAllowNull(false);
                } else {
                    temp2.setAllowBlank(true);
                    temp2.setAllowNull(true);
                    temp2.setDefaultValue(null);
                }

                result.add(temp2);
            } else if ((param.getReportSetsDef().getReportSetsDefType().equalsIgnoreCase("date"))
                    || (param.getReportSetsDef().getReportSetsDefType().equalsIgnoreCase("text"))) {
                codeArray[paramPosition] = (((paramPosition == 0) ? "\"" : " + \"")
                        + codeArray[paramPosition] + "'\" + " + "params[\""
                        + param.getReportSetsDef().getReportSetsDefField() + "\"]+\"'\"");
            } else {
                codeArray[paramPosition] = (((paramPosition == 0) ? "\"" : " + \"")
                        + codeArray[paramPosition] + "\" + " + "params[\""
                        + param.getReportSetsDef().getReportSetsDefField() + "\"]");
            }

            if (StringUtils.isNotEmpty(param.getReportParamsRangeLow())) {
                temp.setAllowBlank(false);
                temp.setAllowNull(false);
                temp.setDefaultValue(param.getReportParamsRangeLow());
            } else {
                temp.setAllowBlank(true);
                temp.setAllowNull(true);
                temp.setDefaultValue(null);

                temp.setName(param.getReportSetsDef().getReportSetsDefField());
            }

            ++paramPosition;

            result.add(temp);
        }

        String tmp = "";
        for (int i = 0; i < codeArray.length; ++i) {
            tmp = tmp + codeArray[i];
        }
        if (!codeGroup.equals("")) {
            tmp = tmp + " +\" group " + codeGroup + "\";\n";
        }
        tmp = tmp + "Packages.java.lang.System.out.println(this.queryText);";
        System.out.println("this.queryText = " + tmp);
        if (!params.isEmpty()) {
            handle.setBeforeOpen("this.queryText = " + tmp);
        }
        return result;
    }

    private static String getType(String type) {
        if ("datetime".equalsIgnoreCase(type))
            return "dateTime";
        if ("text".equalsIgnoreCase(type))
            return "string";
        if ("number".equalsIgnoreCase(type))
            return "decimal";
        if ("date".equalsIgnoreCase("date")) {
            return "date";
        }
        return "string";
    }

    private static String getCondition(String condition) {
        String result = "";
        if ("equal".equalsIgnoreCase(condition))
            result = " =? ";
        else if ("notEqual".equalsIgnoreCase(condition))
            result = " <>? ";
        else if ("lt".equalsIgnoreCase(condition))
            result = " <? ";
        else if ("le".equalsIgnoreCase(condition))
            result = " <=? ";
        else if ("gt".equalsIgnoreCase(condition))
            result = " >? ";
        else if ("ge".equalsIgnoreCase(condition))
            result = " >=? ";
        else if ("between".equalsIgnoreCase(condition))
            result = " between ? and ? ";
        else if ("contains".equalsIgnoreCase(condition))
            result = " like ? ";
        else if ("notContains".equalsIgnoreCase(condition))
            result = " not like ? ";
        else {
            System.out.println("找不到匹配的过滤噄1�7");
        }
        return result;
    }

    public static synchronized String[] getOutputColumns() {
        return columns;
    }

    private static String formatSQL(String sql) {
        String rs = sql.trim();
        int len = rs.length();
        String tmp = rs.substring(len - 1, len);
        if (",".equals(tmp))
            rs = rs.substring(0, len - 1);
        else {
            rs = sql;
        }
        return rs;
    }

    public static void removeDuplicate(List arlList) {
        HashSet h = new HashSet(arlList);
        arlList.clear();
        arlList.addAll(h);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.bo.ReportParametersHandler JD-Core Version: 0.5.4
 */