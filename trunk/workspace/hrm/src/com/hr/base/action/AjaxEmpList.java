package com.hr.base.action;

import com.hr.base.BaseAction;
import com.hr.configuration.domain.Department;
import com.hr.profile.bo.IEmployeeBo;
import com.hr.profile.domain.Employee;
import com.hr.util.BaseCrit;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.web.util.HtmlUtils;

public class AjaxEmpList extends BaseAction {
    private static final long serialVersionUID = 1L;

    public String execute() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String searchConditon = URLDecoder.decode(request.getParameter("searchConditon"), "UTF-8");
        String moduleNo = request.getParameter("moduleNo");
        Integer searchAll;
        try {
            searchAll = new Integer(request.getParameter("searchAll"));
        } catch (Exception e) {
            searchAll = null;
        }

        DetachedCriteria dc = DetachedCriteria.forClass(Employee.class);
        dc.createAlias(Employee.PROP_EMP_DEPT_NO, "empDeptNo", 1);

        String auth = null;
        if ("1".equals(moduleNo))
            auth = checkActionAuth("empProfile", "execute");
        else if ("2".equals(moduleNo))
            auth = checkActionAuth("mySalaryPaid", "execute");
        else if ("4".equals(moduleNo))
            auth = checkActionAuth("empListAction", "execute");
        else {
            return null;
        }

        if ("SUB".equals(auth))
            addSubDC(dc, "", Integer.parseInt(moduleNo));
        else if ("OWN".equals(auth)) {
            BaseCrit.addDC(dc, Employee.PROP_ID, "eq", new String[] { getCurrentEmp().getId() });
        }

        BaseCrit.addDeptEmpDC(dc, null, searchConditon);
        BaseCrit.addDC(dc, Employee.PROP_EMP_STATUS, "eq", new Integer[] { searchAll });

        dc.addOrder(Order.asc(Employee.PROP_EMP_DISTINCT_NO));

        IEmployeeBo empBo = (IEmployeeBo) getBean("empBo");
        List allSubEmps = empBo.findByCriteria(dc);

        StringBuffer xml = new StringBuffer();
        xml.append("<managers>\n");
        int len = allSubEmps.size();
        for (int i = 0; i < len; ++i) {
            xml.append("<manager>\n");
            Employee emp = (Employee) allSubEmps.get(i);
            String id = emp.getEmpDistinctNo();
            String name = emp.getEmpName();
            String department = emp.getEmpDeptNo().getDepartmentName();
            String managerId = emp.getId();

            xml.append("<id>");
            xml.append(id);
            xml.append("</id>\n");
            xml.append("<name>");
            xml.append(HtmlUtils.htmlEscape(name));
            xml.append("</name>\n");
            xml.append("<department>");
            xml.append(HtmlUtils.htmlEscape(department));
            xml.append("</department>\n");
            xml.append("<managerId>");
            xml.append(managerId);
            xml.append("</managerId>\n");
            xml.append("</manager>\n");
        }
        xml.append("</managers>");
        HttpServletResponse response = ServletActionContext.getResponse();

        response.setContentType("text/xml; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(xml.toString());
        return null;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.base.action.AjaxEmpList JD-Core Version: 0.5.4
 */