package com.hr.examin.action;

import com.hr.configuration.bo.IClientBO;
import com.hr.examin.action.beans.EmailConfirmParamBean;
import com.hr.examin.bo.interfaces.ILeaverequestBO;
import com.hr.examin.bo.interfaces.IOvertimerequestBo;
import com.hr.examin.domain.Leaverequest;
import com.hr.examin.domain.Overtimerequest;
import com.hr.security.bo.UserBo;
import com.hr.security.domain.Client;
import com.hr.security.domain.Userinfo;
import com.hr.security.web.action.GetUserSessionAction;
import com.hr.spring.SpringBeanFactory;
import com.opensymphony.xwork2.ActionContext;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.SessionMap;

public class EmailConfirmAction extends GetUserSessionAction {
    private EmailConfirmParamBean param;

    public String execute() throws Exception {
        HttpServletRequest req = getRequest();
        this.param = fetchParametersFromRequest(req);

        if (!isLegal(this.param)) {
            addActionError("请求参数错误＄1�7");
            return "input";
        }

        if ((StringUtils.equals("reject", this.param.getOperation()))
                && (StringUtils.isEmpty(this.param.getComments()))) {
            addActionError("拒绝时必须填写备注！");
            return "input";
        }

        if ((StringUtils.isNotEmpty(this.param.getComments()))
                && (this.param.getComments().trim().length() > 255)) {
            addActionError("备注长度必须小于255个字符！");
            return "input";
        }

        UserBo userBo = (UserBo) SpringBeanFactory.getBean("userService");
        Userinfo userinfo = userBo.getUserById(this.param.getOperatorId());
        if ((userinfo == null) || (userinfo.getUiStatus().intValue() != 1)) {
            addActionError("用户不存在或已经被停用！");
            return "input";
        }

        HttpSession session = req.getSession();
        IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");
        Client client = null;
        if (StringUtils.isNotEmpty(userinfo.getClientNo())) {
            client = clientBo.loadOneClient(userinfo.getClientNo());
            session.setAttribute("clientNo", userinfo.getClientNo());
            session.setAttribute("loginModel", "multiple");
            session.setAttribute("userId", userinfo.getUiUsername());
        } else {
            client = (Client) clientBo.loadClients().get(0);
            session.setAttribute("clientNo", client.getId());
            session.setAttribute("loginModel", "single");
        }
        ActionContext.getContext().setSession(new SessionMap(req));
        setUser(userinfo);
        super.execute();

        if (isLeaveRequest(this.param)) {
            ILeaverequestBO bo = (ILeaverequestBO) SpringBeanFactory.getBean("leaverequestBO");
            Leaverequest lr = bo.loadLeaverequest(this.param.getRecordId());
            if (lr == null) {
                addActionError("您要审批的请假记录不存在或已经被删除＄1�7");
                return "input";
            }

            if (StringUtils.equals(this.param.getSecurityNo(), lr.getLrSecurityNo())) {
                if (StringUtils.isEmpty(this.param.getOperation())) {
                    return "input";
                }
                return this.param.getActionName();
            }
            addActionError("您要审批的编号为" + lr.getLrNo() + "的请假记录已经被审批过！");
            return "input";
        }

        if (isOvertimeRequest(this.param)) {
            IOvertimerequestBo or_Bo = (IOvertimerequestBo) SpringBeanFactory
                    .getBean("overtimerequestBO");

            Overtimerequest or = or_Bo.loadOvertimerequest(this.param.getRecordId());
            if (or == null) {
                addActionError("您要审批的加班记录不存在或已经被删除＄1�7");
                return "input";
            }

            if (StringUtils.equals(this.param.getSecurityNo(), or.getOrSecurityNo())) {
                if (StringUtils.isEmpty(this.param.getOperation())) {
                    return "input";
                }
                return this.param.getActionName();
            }
            addActionError("您要审批的编号为" + or.getOrNo() + "的加班记录已经被审批过！");
            return "input";
        }

        addActionError("请求参数错误，未知的url地址");
        return "input";
    }

    public EmailConfirmParamBean getParam() {
        return this.param;
    }

    public void setParam(EmailConfirmParamBean param) {
        this.param = param;
    }

    private boolean isLegal(EmailConfirmParamBean params) {
        return (!StringUtils.isEmpty(params.getActionName()))
                && (!StringUtils.equals("null", params.getActionName()))
                && (!StringUtils.isEmpty(params.getOperatorId()))
                && (!StringUtils.equals("null", params.getOperatorId()))
                && (!StringUtils.isEmpty(params.getCommand()))
                && (!StringUtils.equals("null", params.getCommand()))
                && (!StringUtils.isEmpty(params.getRecordId()))
                && (!StringUtils.equals("null", params.getRecordId()))
                && (!StringUtils.isEmpty(params.getSecurityNo()))
                && (!StringUtils.equals("null", params.getSecurityNo()));
    }

    private EmailConfirmParamBean fetchParametersFromRequest(HttpServletRequest req) {
        EmailConfirmParamBean params = new EmailConfirmParamBean();
        params.setActionName(req.getParameter("actionName"));
        params.setOperatorId(req.getParameter("operatorId"));
        params.setRecordId(req.getParameter("recordId"));
        params.setSecurityNo(req.getParameter("securityNo"));
        params.setCommand(req.getParameter("command"));
        params.setOperation(req.getParameter("operation"));
        String comments = req.getParameter("comments");
        if (StringUtils.isNotEmpty(comments)) {
            try {
                params.setComments(new String(comments.getBytes("ISO8859_1"), "GBK"));
            } catch (UnsupportedEncodingException e) {
                params.setComments(comments);
            }
        }
        return params;
    }

    private boolean isLeaveRequest(EmailConfirmParamBean params) {
        return StringUtils.equals(params.getCommand(), "leaveRequest");
    }

    private boolean isOvertimeRequest(EmailConfirmParamBean params) {
        return StringUtils.equals(params.getCommand(), "overtimeRequest");
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.action.EmailConfirmAction JD-Core Version: 0.5.4
 */