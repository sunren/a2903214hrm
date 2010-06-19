package com.hr.report.engine;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IParameterGroupDefn;
import org.eclipse.birt.report.engine.api.IParameterSelectionChoice;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.eclipse.birt.report.model.api.CascadingParameterGroupHandle;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.ReportDesignHandle;
import org.eclipse.birt.report.model.api.ScalarParameterHandle;
import org.eclipse.birt.report.model.api.SlotHandle;

public class ReportHandler extends HttpServlet {
    public static final String FORMAT_PDF = "PDF";
    public static final String FORMAT_XML = "XML";
    public static final String FORMAT_HTML = "HTML";
    public static final String FORMAT_XLS = "XLS";
    public static final String FORMAT_CSV = "CSV";
    public static final String FORMAT_RTF = "RTF";
    public static final String REQUEST_INPUT_FORMAT = "_format";
    public static final String REQUEST_INPUT_REPORT = "_report";
    public static final String REQUEST_INPUT_PARAMS = "_params";
    public static final String REQUEST_CONTENT_TYPE = "_contentType";
    public static final String REQUEST_DOC_NAME = "_fileName";
    protected Map<String, Object> params = new HashMap();
    protected String format = "HTML";
    protected String contentDisposition;
    protected String documentName;
    private static final long serialVersionUID = 1L;
    private static IReportEngine birtReportEngine = null;

    protected static final Logger logger = Logger.getLogger("org.eclipse.birt");

    public void destroy() {
        synchronized (this) {
            String imageFileFolder = getServletContext().getRealPath("/report/images");
            File file = new File(imageFileFolder);
            if (file.exists()) {
                File[] images = file.listFiles();
                int len = images.length;
                for (int i = 0; i < len; ++i) {
                    images[i].delete();
                }
            }
        }
        super.destroy();
        BirtEngineFactory.destroyBirtReportEngine();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        executeReport(req, resp);
    }

    private void executeReport(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map pas = (Map) req.getAttribute("_params");
        if ((pas != null) && (pas.size() > 0)) {
            this.params.putAll(pas);
        }

        String fo = (String) req.getAttribute("_format");
        if ((fo != null) && (fo.trim().length() > 0)) {
            this.format = fo.trim();
        }

        String ct = (String) req.getAttribute("_contentType");
        if ((ct != null) && (ct.trim().length() > 0)) {
            this.contentDisposition = ct.trim();
        }

        String fn = (String) req.getAttribute("_fileName");
        if ((fn != null) && (fn.trim().length() > 0)) {
            this.documentName = fn;
        }

        HashMap parmDetails = new HashMap();

        ServletContext sc = req.getSession().getServletContext();
        birtReportEngine = BirtEngineFactory.getBirtReportEngine(sc);
        String filePath = (String) req.getAttribute("_report");

        String systemId = sc.getRealPath(filePath);

        HTMLRenderContext renderContext = new HTMLRenderContext();
        renderContext.setBaseImageURL(req.getContextPath() + "/report/images");
        renderContext.setImageDirectory(sc.getRealPath("/report/images"));
        logger.log(Level.FINE, "image directory " + sc.getRealPath("/report/images"));

        HashMap contextMap = new HashMap();
        contextMap.put("HTML_RENDER_CONTEXT", renderContext);
        try {
            IReportRunnable design = birtReportEngine.openReportDesign(systemId);

            IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask(design);
            task.setAppContext(contextMap);

            IGetParameterDefinitionTask taskParam = birtReportEngine
                    .createGetParameterDefinitionTask(design);

            Collection params = taskParam.getParameterDefns(true);

            Iterator iter = params.iterator();
            while (iter.hasNext()) {
                IParameterDefnBase param = (IParameterDefnBase) iter.next();
                if (param instanceof IParameterGroupDefn) {
                    IParameterGroupDefn group = (IParameterGroupDefn) param;
                    System.out.println("Parameter Group: " + group.getName());

                    Iterator i2 = group.getContents().iterator();
                    while (i2.hasNext()) {
                        IScalarParameterDefn scalar = (IScalarParameterDefn) i2.next();

                        System.out.println("\t" + scalar.getName());

                        parmDetails.put(scalar.getName(), loadParameterDetails(taskParam, scalar,
                                                                               design, group));
                    }
                } else {
                    IScalarParameterDefn scalar = (IScalarParameterDefn) param;
                    System.out.println(param.getName());

                    parmDetails.put(scalar.getName(), loadParameterDetails(taskParam, scalar,
                                                                           design, null));
                }

            }

            HTMLRenderOption options = new HTMLRenderOption();
            if ("PDF".equals(this.format)) {
                resp.setContentType("application/pdf");
                options.setOutputFormat("pdf");
            } else {
                resp.setContentType("text/html");
                options.setOutputFormat("html");
            }

            if ((this.contentDisposition != null) || (this.documentName != null)) {
                StringBuffer tmp = new StringBuffer();
                tmp.append((this.contentDisposition == null) ? "inline" : this.contentDisposition);
                if (this.documentName != null) {
                    tmp.append("; filename=");
                    tmp.append(this.documentName);
                    tmp.append(".");
                    tmp.append(this.format.toLowerCase());
                }
                resp.setHeader("Content-disposition", tmp.toString());
            } else {
                resp.setHeader("Content-Disposition", "inline");
            }

            options.setOutputStream(resp.getOutputStream());
            task.setRenderOption(options);

            task.run();
            task.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
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

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        executeReport(request, response);
    }

    public void init() throws ServletException {
        super.init();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.engine.ReportHandler JD-Core Version: 0.5.4
 */