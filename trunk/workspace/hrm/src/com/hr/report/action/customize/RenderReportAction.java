package com.hr.report.action.customize;

import com.hr.base.BaseAction;
import com.hr.report.engine.BirtEngineFactory;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;

public class RenderReportAction extends BaseAction {
    private static IReportEngine birtReportEngine = null;
    protected static Logger logger = Logger.getLogger("org.eclipse.birt");

    public String execute() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpServletRequest request = getRequest();
        try {
            out = renderReportPage(request);
        } catch (ServletException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        request.setAttribute("reportHTML", out);

        return "none";
    }

    public String pdfReportAction() throws ServletException {
        HttpServletResponse response = getResponse();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=WebReport.pdf");
        HttpServletRequest request = getRequest();
        String reportName = request.getParameter("ReportName");
        ServletContext sc = request.getSession().getServletContext();
        birtReportEngine = BirtEngineFactory.getBirtReportEngine(sc);

        HTMLRenderContext renderContext = new HTMLRenderContext();
        renderContext.setBaseImageURL(request.getContextPath() + "/images");
        renderContext.setImageDirectory(sc.getRealPath("/images"));

        logger.log(Level.FINE, "image directory " + sc.getRealPath("/images"));

        HashMap contextMap = new HashMap();
        contextMap.put("HTML_RENDER_CONTEXT", renderContext);
        try {
            IReportRunnable design = birtReportEngine.openReportDesign(sc.getRealPath("/reports")
                    + "/" + reportName);

            IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask(design);
            task.setAppContext(contextMap);

            HTMLRenderOption options = new HTMLRenderOption();
            options.setOutputFormat("pdf");
            options.setOutputStream(response.getOutputStream());
            task.setRenderOption(options);

            task.run();
            task.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServletException(e);
        }
        return "none";
    }

    public String xlsReportAction() throws ServletException {
        HttpServletResponse response = getResponse();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=WebReport.xls");
        HttpServletRequest request = getRequest();
        String reportName = request.getParameter("ReportName");
        ServletContext sc = request.getSession().getServletContext();
        birtReportEngine = BirtEngineFactory.getBirtReportEngine(sc);

        HTMLRenderContext renderContext = new HTMLRenderContext();
        renderContext.setBaseImageURL(request.getContextPath() + "/images");
        renderContext.setImageDirectory(sc.getRealPath("/images"));

        logger.log(Level.FINE, "image directory " + sc.getRealPath("/images"));

        HashMap contextMap = new HashMap();
        contextMap.put("HTML_RENDER_CONTEXT", renderContext);
        try {
            IReportRunnable design = birtReportEngine.openReportDesign(sc.getRealPath("/reports")
                    + "/" + reportName);

            IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask(design);
            task.setAppContext(contextMap);

            HTMLRenderOption options = new HTMLRenderOption();
            options.setOutputFormat("xls");
            options.setOutputStream(response.getOutputStream());
            task.setRenderOption(options);

            task.run();
            task.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServletException(e);
        }
        return "none";
    }

    private ByteArrayOutputStream renderReportPage(HttpServletRequest request)
            throws ServletException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String reportName = request.getParameter("ReportName");
        ServletContext sc = request.getSession().getServletContext();
        birtReportEngine = BirtEngineFactory.getBirtReportEngine(sc);

        HTMLRenderContext renderContext = new HTMLRenderContext();
        renderContext.setBaseImageURL(request.getContextPath() + "/images");
        renderContext.setImageDirectory(sc.getRealPath("/images"));

        logger.log(Level.FINE, "image directory " + sc.getRealPath("/images"));

        HashMap contextMap = new HashMap();
        contextMap.put("HTML_RENDER_CONTEXT", renderContext);
        try {
            IReportRunnable design = birtReportEngine.openReportDesign(sc.getRealPath("/reports")
                    + "/" + reportName);

            IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask(design);
            task.setAppContext(contextMap);

            HTMLRenderOption options = new HTMLRenderOption();
            options.setOutputFormat("html");
            options.setEmbeddable(true);
            options.setOutputStream(out);
            task.setRenderOption(options);

            task.run();
            task.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServletException(e);
        }
        return out;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.action.customize.RenderReportAction JD-Core Version: 0.5.4
 */