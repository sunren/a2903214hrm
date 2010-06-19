package com.hr.report.bo;

import java.util.Comparator;

public class ExaminCellCompare implements Comparator<String> {
    public int compare(String o1, String o2) {
        String o1Str = o1.replace('|', ':').replace('|', ':').replace('|', ':');
        String o2Str = o2.replace('|', ':').replace('|', ':').replace('|', ':');
        try {
            int o1Index = Integer.parseInt(o1Str.split(":")[1].trim());
            int o2Index = Integer.parseInt(o2Str.split(":")[1].trim());
            if ((o1Index - o2Index != 0) || (o1Str.split(":").length <= 2)
                    || (o2Str.split(":").length <= 2)) {
                return o1Index - o2Index;
            }
            o1Index = Integer.parseInt(o1Str.split(":")[2].trim());
            o2Index = Integer.parseInt(o2Str.split(":")[2].trim());
            return o1Index - o2Index;
        } catch (NumberFormatException e) {
        }
        return o1Str.split(":")[2].trim().compareTo(o2Str.split(":")[2].trim());
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.bo.ExaminCellCompare JD-Core Version: 0.5.4
 */