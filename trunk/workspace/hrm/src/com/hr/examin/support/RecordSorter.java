package com.hr.examin.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.StringUtils;

public class RecordSorter {
    private List records;
    private static final String DEFAULT_SORT_PROPERTY = "examinDate";
    private static final String DEFAULT_SORT_ORDER = "up";

    public RecordSorter(List records) {
        this.records = records;
    }

    public void doSort(String sortProperty, String order) {
        if (this.records == null) {
            this.records = new ArrayList(0);
        }
        if (StringUtils.isEmpty(sortProperty)) {
            sortProperty = "examinDate";
        }
        if (StringUtils.isEmpty(order)) {
            order = "up";
        }

        Comparator mycmp = ComparatorUtils.nullLowComparator(ComparableComparator.getInstance());
        if ("down".equalsIgnoreCase(order)) {
            mycmp = ComparatorUtils.reversedComparator(mycmp);
        }
        ArrayList sortFields = new ArrayList();
        sortFields.add(new BeanComparator(sortProperty, mycmp));

        Comparator empNameSort = ComparatorUtils.nullLowComparator(ComparableComparator
                .getInstance());
        sortFields.add(new BeanComparator("empName", empNameSort));
        Comparator examinDateSort = ComparatorUtils.nullLowComparator(ComparableComparator
                .getInstance());
        sortFields.add(new BeanComparator("examinDate", examinDateSort));

        ComparatorChain multiSort = new ComparatorChain(sortFields);
        Collections.sort(this.records, multiSort);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.examin.support.RecordSorter JD-Core Version: 0.5.4
 */