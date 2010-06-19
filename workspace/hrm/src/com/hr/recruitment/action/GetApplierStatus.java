package com.hr.recruitment.action;

import com.hr.base.Status;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class GetApplierStatus extends BodyTagSupport implements Status {
    private static final long serialVersionUID = 1L;

    public int doEndTag() throws JspException {
        try {
            BodyContent l_tagbody = getBodyContent();
            String ls_output = "";
            if (l_tagbody != null)
                switch (Integer.valueOf(l_tagbody.getString().trim()).intValue()) {
                case 1:
                    ls_output = "初选通过";
                    break;
                case 21:
                    ls_output = "黑名单";
                    break;
                case 11:
                    ls_output = "待定候选人";
                    break;
                case 13:
                    ls_output = "接受录取通知";
                    break;
                case 19:
                    ls_output = "拒绝录取通知";
                    break;
                case 12:
                    ls_output = "已发录取通知";
                    break;
                case 9:
                    ls_output = "不合格";
                    break;
                case 2:
                    ls_output = "笔试";
                    break;
                case 3:
                    ls_output = "面试";
                    break;
                case 4:
                    ls_output = "电话面试";
                case 5:
                case 6:
                case 7:
                case 8:
                case 10:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 20:
                }
            this.pageContext.getOut().write(ls_output.trim());
        } catch (IOException e) {
            throw new JspTagException("Tag Error:" + e.toString());
        }
        return 6;
    }
}