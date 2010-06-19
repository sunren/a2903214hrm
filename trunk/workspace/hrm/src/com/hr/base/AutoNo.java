package com.hr.base;

import com.hr.base.bo.IBaseBo;
import com.hr.spring.SpringBeanFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AutoNo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String textfieldName = request.getParameter("textfieldName");
        String model = request.getParameter("model");
        String domainName = request.getParameter("domainName");
        if ((model == null) || (domainName == null) || (textfieldName == null)
                || (domainName.trim().length() == 0) || (textfieldName.trim().length() == 0)) {
            return;
        }

        if (textfieldName.indexOf(".") > 0) {
            textfieldName = textfieldName.substring(textfieldName.indexOf(".") + 1);
        }
        IBaseBo bo = (IBaseBo) SpringBeanFactory.getBean("baseService");
        String valu = bo.getNoByPrefix(domainName, model.trim(), textfieldName);

        response.getWriter().write(valu);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name: com.hr.base.AutoNo
 * JD-Core Version: 0.5.4
 */