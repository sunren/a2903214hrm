package com.hr.util;

import java.io.IOException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class TextTag extends BodyTagSupport {
    private static final long serialVersionUID = 1L;
    int counts;

    public void setCounts(int c) {
        this.counts = c;
    }

    public int doStartTag() throws JspTagException {
        if (this.counts > 0) {
            return 2;
        }
        return 1;
    }

    public int doEndTag() throws JspTagException {
        try {
            if (this.bodyContent != null) {
                String body = this.bodyContent.getString().trim();
                byte[] bodyBytes = body.getBytes();

                if (this.counts > bodyBytes.length) {
                    this.bodyContent.getEnclosingWriter().write(body);
                } else {
                    byte[] subBytes = new byte[this.counts];
                    for (int i = 0; i < this.counts; ++i) {
                        subBytes[i] = bodyBytes[i];
                    }
                    int subIndex = new String(subBytes).length();
                    this.bodyContent.getEnclosingWriter().write(
                                                                "<span title='"
                                                                        + body
                                                                        + "'>"
                                                                        + body.substring(0,
                                                                                         subIndex)
                                                                        + "...</span>");
                }
            }
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return 6;
    }

    public void doInitBody() throws JspTagException {
    }

    public void setBodyContent(BodyContent bodyContent) {
        this.bodyContent = bodyContent;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name: com.hr.util.TextTag
 * JD-Core Version: 0.5.4
 */