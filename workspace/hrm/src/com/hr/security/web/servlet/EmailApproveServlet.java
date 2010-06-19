package com.hr.security.web.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmailApproveServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String employeeId = request.getParameter("employeeId");
        String objId = request.getParameter("objId");
        String securityNo = request.getParameter("securityNo");
        String flowType = request.getParameter("flowType");

        request.setAttribute("employeeId", employeeId);
        request.setAttribute("objId", objId);
        request.setAttribute("securityNo", securityNo);
        request.setAttribute("flowType", flowType);

        String approvePage = "examin/emailconfirmresult.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(approvePage);
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.web.servlet.EmailApproveServlet JD-Core Version: 0.5.4
 */