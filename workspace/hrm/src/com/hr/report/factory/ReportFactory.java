package com.hr.report.factory;

import com.hr.report.domain.ReportDef;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.lang.StringUtils;
import org.eclipse.birt.chart.model.Chart;

public class ReportFactory {
    public static final String METHOD_NAME = "createChart";

    public static Chart getChartModel(String className, String methodName, ReportDef model) {
        Chart chart = null;

        if (StringUtils.isEmpty(methodName))
            methodName = "createChart";
        try {
            Class modelClass = Class.forName(className);
            try {
                Method method = modelClass.getMethod(methodName, new Class[] { ReportDef.class });
                try {
                    chart = (Chart) method.invoke(modelClass.newInstance(), new Object[] { model });
                } catch (InvocationTargetException iex) {
                    iex.printStackTrace();
                } catch (IllegalAccessException iex) {
                    iex.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException mex) {
                mex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return chart;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.factory.ReportFactory JD-Core Version: 0.5.4
 */