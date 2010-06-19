package com.hr.spring;

import com.hr.report.engine.BirtEngineFactory;
import com.hr.report.servlet.BirtEngine;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringBeanFactory implements ServletContextListener {
    private static ApplicationContext context = null;
    private ContextLoader contextLoader;
    private static Map<String, Object> beanCache = new HashMap();

    public static Object getBean(String name) {
        if (!beanCache.containsKey(name)) {
            beanCache.put(name, context.getBean(name));
        }
        return beanCache.get(name);
    }

    public static ApplicationContext getSpringContext() {
        return context;
    }

    public void contextInitialized(ServletContextEvent event) {
        ServletContext application = event.getServletContext();
        this.contextLoader = new ContextLoader();
        this.contextLoader.initWebApplicationContext(application);
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(application);
        BirtEngine.getBirtEngine(application);
        BirtEngineFactory.getBirtDesignEngine(application);
    }

    public void contextDestroyed(ServletContextEvent event) {
        BirtEngine.destroyBirtEngine();
        System.gc();
        this.contextLoader.closeWebApplicationContext(event.getServletContext());
    }

    public static void setApplicationContext(ApplicationContext cxt) {
        context = cxt;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.spring.SpringBeanFactory JD-Core Version: 0.5.4
 */