package com.hr.exportfile;

import com.hr.compensation.domain.Empsalarypay;
import com.hr.util.comparator.ObjectFieldComparator;
import java.util.Collections;
import java.util.List;

public class SalaryRecordSorter {
    private List<Empsalarypay> records;

    public SalaryRecordSorter(List<Empsalarypay> records) {
        this.records = records;
    }

    public void doSort(String[] sortProperties) {
        ObjectFieldComparator com = new ObjectFieldComparator();
        try {
            com.initial(Empsalarypay.class, sortProperties, new int[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(this.records, com);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.exportfile.SalaryRecordSorter JD-Core Version: 0.5.4
 */