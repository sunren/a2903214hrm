package com.hr.report.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;

public class WebReport extends HttpServlet {
    public static final String FORMAT_PDF = "PDF";
    public static final String FORMAT_XML = "XML";
    public static final String FORMAT_HTML = "HTML";
    public static final String FORMAT_XLS = "XLS";
    public static final String FORMAT_DOC = "DOC";
    public static final String FORMAT_PPT = "PPT";
    public static final String FORMAT_CSV = "CSV";
    public static final String FORMAT_RTF = "RTF";
    public static final String REQUEST_INPUT_FORMAT = "_format";
    public static final String REQUEST_INPUT_REPORT = "_report";
    public static final String REQUEST_INPUT_PARAMS = "_params";
    public static final String REQUEST_CONTENT_TYPE = "_contentType";
    public static final String REQUEST_DOC_NAME = "_fileName";
    private static final long serialVersionUID = 1L;
    private static IReportEngine birtReportEngine = null;

    protected static Logger logger = Logger.getLogger("org.eclipse.birt");

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
        BirtEngine.destroyBirtEngine();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        Map params = new HashMap();
        String format = "HTML";
        String contentDisposition = null;
        String documentName = null;

        Map pas = (Map) req.getAttribute("_params");
        if ((pas != null) && (pas.size() > 0)) {
            params.putAll(pas);
        }

        String clientNo = (String) req.getSession().getAttribute("clientNo");
        params.put("clientNo", clientNo);

        String fo = (String) req.getAttribute("_format");
        if ((fo != null) && (fo.trim().length() > 0)) {
            format = fo.trim();
        }

        String ct = (String) req.getAttribute("_contentType");
        if ((ct != null) && (ct.trim().length() > 0)) {
            contentDisposition = ct.trim();
        }

        String fn = (String) req.getAttribute("_fileName");
        if ((fn != null) && (fn.trim().length() > 0)) {
            documentName = fn;
        }

        ServletContext sc = req.getSession().getServletContext();
        birtReportEngine = BirtEngine.getBirtEngine(sc);
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

            task.setParameterValues(params);

            task.validateParameters();

            HTMLRenderOption options = new HTMLRenderOption();
            if ("PDF".equals(format)) {
                resp.setContentType("application/pdf");
                options.setOutputFormat("pdf");
            } else if ("XLS".equals(format)) {
                resp.setContentType("application/vnd.ms-excel");
                resp.setHeader("Content-Disposition", "inline; filename=WebReport.xls");
                options.setOutputFormat("XLS");
            } else {
                resp.setContentType("text/html");
                options.setOutputFormat("html");
            }

            if ((contentDisposition != null) || (documentName != null)) {
                StringBuffer tmp = new StringBuffer();
                tmp.append((contentDisposition == null) ? "inline" : contentDisposition);
                if (documentName != null) {
                    tmp.append("; filename=");
                    tmp.append(documentName);
                    tmp.append(".");
                    tmp.append(format.toLowerCase());
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

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void init() throws ServletException {
        super.init();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.servlet.WebReport JD-Core Version: 0.5.4
 */