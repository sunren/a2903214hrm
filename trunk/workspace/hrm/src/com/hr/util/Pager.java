package com.hr.util;

import com.hr.hibernate.IHibernateUtil;
import com.hr.spring.SpringBeanFactory;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

public class Pager {
    private String order = "";

    private String operate = "";

    private int totalRows = 0;

    private int totalPages = 0;

    private int pageSize = 20;

    private int currentPage = 1;

    private boolean isSplit = true;

    private boolean hasPrevious = false;

    private boolean hasNext = false;
    private int start;
    private int end;

    public int getPageSizeFromCookie() {
        HttpServletRequest request = ServletActionContext.getRequest();
        if (request == null) {
            return this.pageSize;
        }

        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; ++i) {
            Cookie c = cookies[i];
            if (c.getName().equalsIgnoreCase("pageSize")) {
                return Integer.parseInt(c.getValue());
            }

        }

        return this.pageSize;
    }

    public void init(int totalRows, int pageSize) {
        this.pageSize = pageSize;
        if (pageSize < 0) {
            pageSize = 99999999;
        }

        this.totalRows = totalRows;
        this.totalPages = ((totalRows + pageSize - 1) / pageSize);

        this.start = ((this.currentPage - 4 > 0) ? this.currentPage - 4 : 1);
        this.end = ((this.currentPage + 5 < this.totalPages) ? this.currentPage + 5
                : this.totalPages);
        if (this.end == this.totalPages)
            this.start = ((this.totalPages - 9 <= 0) ? 1 : this.totalPages - 9);
        else if ((this.start == 1) && (this.end < this.totalPages)) {
            this.end = ((this.totalPages > 10) ? 10 : this.totalPages);
        }

        if (this.currentPage > this.totalPages) {
            this.currentPage = this.totalPages;
        }
        if (this.currentPage <= 0)
            this.currentPage = 1;
    }

    public void splitPage(DetachedCriteria detachedCriteria) {
        IHibernateUtil dao = (IHibernateUtil) SpringBeanFactory.getBean("dao");
        int size = dao.findRowCountByCriteria(detachedCriteria);
        int pageSize = getPageSizeFromCookie();

        detachedCriteria.setProjection(null);

        init(size, pageSize);
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalRows() {
        return this.totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public void first() {
        this.currentPage = 1;
        setHasPrevious(false);
        refresh();
    }

    public void previous() {
        this.currentPage -= 1;
        this.operate = "";
        refresh();
    }

    public void next() {
        if (this.currentPage < this.totalPages) {
            this.currentPage += 1;
        }
        this.operate = "";
        refresh();
    }

    public void last() {
        this.currentPage = this.totalPages;
        setHasNext(false);
        refresh();
    }

    public boolean isHasNext() {
        return this.hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return this.hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public void refresh() {
        if (this.currentPage > this.totalPages) {
            this.currentPage = this.totalPages;
        }
        if (this.currentPage < 1) {
            this.currentPage = 1;
        }
        if (this.totalPages <= 1) {
            this.hasPrevious = false;
            this.hasNext = false;
        } else if (this.currentPage == 1) {
            this.hasPrevious = false;
            this.hasNext = true;
        } else if (this.currentPage == this.totalPages) {
            this.hasPrevious = true;
            this.hasNext = false;
        } else {
            this.hasPrevious = true;
            this.hasNext = true;
        }
        if (getTotalRows() <= getPageSize())
            setSplit(false);
        else
            setSplit(true);
    }

    public boolean isSplit() {
        return this.isSplit;
    }

    public void setSplit(boolean isSplit) {
        this.isSplit = isSplit;
    }

    public String getOperate() {
        return this.operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getEnd() {
        return this.end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name: com.hr.util.Pager
 * JD-Core Version: 0.5.4
 */