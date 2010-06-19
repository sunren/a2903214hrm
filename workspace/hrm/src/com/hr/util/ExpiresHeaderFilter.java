package com.hr.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExpiresHeaderFilter implements Filter {
    private long time;
    private String[] patterns;
    private static final String KEY_CONTENT_PATTERNS = "ContentPatterns";
    private static final String KEY_MAX_AGE = "max-age";
    private static final String SEPERATOR = "\\|";

    public ExpiresHeaderFilter() {
        this.time = 0L;

        this.patterns = new String[0];
    }

    public void init(FilterConfig config) throws ServletException {
        String patternStr = config.getInitParameter("ContentPatterns");
        String[] values = patternStr.split("\\|");
        List list = new ArrayList();
        for (String value : values) {
            if ((value != null) && (!"".equals(value))) {
                list.add(value);
            }
        }

        this.patterns = ((String[]) list.toArray(new String[list.size()]));

        String timeStr = config.getInitParameter("max-age");
        try {
            this.time = Long.parseLong(timeStr);
        } catch (NumberFormatException e) {
            this.time = 0L;
        }
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        if ((resp instanceof HttpServletResponse) && (req instanceof HttpServletRequest)) {
            HttpServletRequest request = (HttpServletRequest) req;
            String url = request.getRequestURI();
            for (String pattern : this.patterns) {
                if (url.endsWith(pattern)) {
                    HttpServletResponse response = (HttpServletResponse) resp;
                    response.addHeader("Cache-Control", "max-age=" + this.time
                            + ", must-revalidate");
                    response.addDateHeader("Expires", Calendar.getInstance().getTimeInMillis()
                            + this.time * 1000L);
                    break;
                }
            }
        }
        chain.doFilter(req, resp);
    }

    public void destroy() {
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.ExpiresHeaderFilter JD-Core Version: 0.5.4
 */