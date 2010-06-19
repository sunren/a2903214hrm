package com.hr.report.factory.test;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IParameterGroupDefn;
import org.eclipse.birt.report.engine.api.IParameterSelectionChoice;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.eclipse.birt.report.model.api.CascadingParameterGroupHandle;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.ScalarParameterHandle;
import org.eclipse.birt.report.model.api.SlotHandle;

public class ParametersTask {
    static void executeReport() throws EngineException {
        HashMap parmDetails = new HashMap();
        IReportEngine engine = null;
        EngineConfig config = null;
        try {
            config = new EngineConfig();
            config
                    .setEngineHome("D:/开源项目/birt 安装/birt-runtime-2_2_1/birt-runtime-2_2_1/ReportEngine");

            config.setLogConfig(null, Level.FINE);
            Platform.startup(config);
            IReportEngineFactory factory = (IReportEngineFactory) Platform
                    .createFactoryObject("org.eclipse.birt.report.engine.ReportEngineFactory");

            engine = factory.createReportEngine(config);
            engine.changeLogLevel(Level.WARNING);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        IReportRunnable design = null;

        design = engine
                .openReportDesign("F:/myeclipse/365hrm/src/com/hr/report/factory/test/test_param.rptdesign");

        IGetParameterDefinitionTask task = engine.createGetParameterDefinitionTask(design);

        Collection params = task.getParameterDefns(true);

        Iterator iter = params.iterator();
        while (iter.hasNext()) {
            IParameterDefnBase param = (IParameterDefnBase) iter.next();
            if (param instanceof IParameterGroupDefn) {
                IParameterGroupDefn group = (IParameterGroupDefn) param;

                Iterator i2 = group.getContents().iterator();
                while (i2.hasNext()) {
                    IScalarParameterDefn scalar = (IScalarParameterDefn) i2.next();

                    parmDetails.put(scalar.getName(), loadParameterDetails(task, scalar, design,
                                                                           group));
                }
            } else {
                IScalarParameterDefn scalar = (IScalarParameterDefn) param;

                parmDetails.put(scalar.getName(), loadParameterDetails(task, scalar, design, null));
            }

        }

        engine.shutdown();
        Platform.shutdown();
        System.out.println(" Finished ");
    }

    private static HashMap<String, Serializable> loadParameterDetails(
            IGetParameterDefinitionTask task, IScalarParameterDefn scalar, IReportRunnable report,
            IParameterGroupDefn group) {
        HashMap parameter = new HashMap();
        if (group == null)
            parameter.put(" Parameter Group ", " Default ");
        else {
            parameter.put(" Parameter Group ", group.getName());
        }
        parameter.put(" Name ", scalar.getName());
        parameter.put(" Help Text ", scalar.getHelpText());
        parameter.put(" Display Name ", scalar.getDisplayName());

        parameter.put(" Display Format ", scalar.getDisplayFormat());
        if (scalar.isHidden())
            parameter.put(" Hidden ", " Yes ");
        else {
            parameter.put(" Hidden ", " No ");
        }
        if (scalar.allowBlank())
            parameter.put(" Allow Blank ", " Yes ");
        else {
            parameter.put(" Allow Blank ", " No ");
        }
        if (scalar.allowNull())
            parameter.put(" Allow Null ", " Yes ");
        else {
            parameter.put(" Allow Null ", " No ");
        }
        if (scalar.isValueConcealed())
            parameter.put(" Conceal Entry ", " Yes ");
        else {
            parameter.put(" Conceal Entry ", " No ");
        }
        switch (scalar.getControlType()) {
        case 0:
            parameter.put(" Type ", " Text Box ");
            break;
        case 1:
            parameter.put(" Type ", " List Box ");
            break;
        case 2:
            parameter.put(" Type ", " List Box ");
            break;
        case 3:
            parameter.put(" Type ", " List Box ");
            break;
        default:
            parameter.put(" Type ", " Text Box ");
        }

        switch (scalar.getDataType()) {
        case 1:
            parameter.put(" Data Type ", " String ");
            break;
        case 2:
            parameter.put(" Data Type ", " Float ");
            break;
        case 3:
            parameter.put(" Data Type ", " Decimal ");
            break;
        case 4:
            parameter.put(" Data Type ", " Date Time ");
            break;
        case 5:
            parameter.put(" Data Type ", " Boolean ");
            break;
        default:
            parameter.put(" Data Type ", " Any ");
        }

        ReportDesignHandle reportHandle = (ReportDesignHandle) report.getDesignHandle();

        ScalarParameterHandle parameterHandle = (ScalarParameterHandle) reportHandle
                .findParameter(scalar.getName());

        parameter.put(" Default Value ", parameterHandle.getDefaultValue());
        parameter.put(" Prompt Text ", parameterHandle.getPromptText());
        parameter.put(" Data Set Expression ", parameterHandle.getValueExpr());
        if (scalar.getControlType() != 0) {
            if (parameterHandle.getContainer() instanceof CascadingParameterGroupHandle) {
                Collection sList = Collections.EMPTY_LIST;
                if (parameterHandle.getContainer() instanceof CascadingParameterGroupHandle) {
                    int index = parameterHandle.getContainerSlotHandle().findPosn(parameterHandle);

                    Object[] keyValue = new Object[index];
                    for (int i = 0; i < index; ++i) {
                        ScalarParameterHandle handle = (ScalarParameterHandle) ((CascadingParameterGroupHandle) parameterHandle
                                .getContainer()).getParameters().get(i);

                        keyValue[i] = handle.getDefaultValue();
                    }
                    String groupName = parameterHandle.getContainer().getName();
                    task.evaluateQuery(groupName);
                    sList = task.getSelectionListForCascadingGroup(groupName, keyValue);

                    HashMap dynamicList = new HashMap();
                    for (Iterator sl = sList.iterator(); sl.hasNext();) {
                        IParameterSelectionChoice sI = (IParameterSelectionChoice) sl.next();

                        Object value = sI.getValue();
                        Object label = sI.getLabel();
                        System.out.println(label + " -- " + value);
                        dynamicList.put(value, (String) label);
                    }
                    parameter.put(" Selection List ", dynamicList);
                }
            } else {
                Collection selectionList = task.getSelectionList(scalar.getName());

                if (selectionList != null) {
                    HashMap dynamicList = new HashMap();
                    Iterator sliter = selectionList.iterator();
                    while (sliter.hasNext()) {
                        IParameterSelectionChoice selectionItem = (IParameterSelectionChoice) sliter
                                .next();

                        Object value = selectionItem.getValue();
                        String label = selectionItem.getLabel();

                        dynamicList.put(value, label);
                    }
                    parameter.put(" Selection List ", dynamicList);
                }
            }
        }

        Iterator iter = parameter.keySet().iterator();
        System.out.println(" ======================Parameter = " + scalar.getName());

        while (iter.hasNext()) {
            String name = (String) iter.next();
            if (name.equals(" Selection List ")) {
                HashMap selList = (HashMap) parameter.get(name);
                Iterator selIter = selList.keySet().iterator();
                while (selIter.hasNext()) {
                    Object lbl = selIter.next();
                    System.out.println(" Selection List Entry ===== Key = " + lbl + " Value = "
                            + selList.get(lbl));
                }
            } else {
                System.out.println(name + " = " + parameter.get(name));
            }
        }
        return parameter;
    }

    public static void main(String[] args) {
        try {
            executeReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}