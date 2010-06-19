package com.hr.examin.bo;

import com.hr.examin.action.beans.OrgBean;
import java.util.Comparator;

public class OrgBeanComparators implements Comparator {
    public int compare(Object orgBean1, Object orgBean2) {
        int value1 = ((OrgBean) orgBean1).getLevel();
        int value2 = ((OrgBean) orgBean2).getLevel();

        return value2 - value1;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.bo.OrgBeanComparators JD-Core Version: 0.5.4
 */