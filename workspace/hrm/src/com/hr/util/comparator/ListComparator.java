package com.hr.util.comparator;

import java.util.Comparator;
import java.util.List;

public class ListComparator extends IComparator implements Comparator<List> {
    private int[][] orderArr;
    private ObjectComparator[] objComparator;

    public ListComparator() {
    }

    public ListComparator(int[] orderArr, int[] compareMethod) {
        setOrderArr(orderArr);
        if (compareMethod.length > 0)
            setCompareMethod(compareMethod[0]);
    }

    public ListComparator(int[][] orderArr, int[] compareMethod) {
        setOrderArr(orderArr);
        if (compareMethod.length > 0)
            setCompareMethod(compareMethod[0]);
    }

    public int compare(List list1, List list2) {
        for (int i = 0; i < this.orderArr.length; ++i) {
            if (this.objComparator[i] == null) {
                this.objComparator[i] = new ObjectComparator(this.orderArr[i][1], new int[0]);
            }

            int resultTmp = this.objComparator[i].compare(list1.get(this.orderArr[i][0]), list2
                    .get(this.orderArr[i][0]));
            if (resultTmp != 0) {
                return resultTmp;
            }
        }
        return 0;
    }

    public int[][] getOrderArr() {
        return this.orderArr;
    }

    public void setOrderArr(int[] orderArr) {
        this.orderArr = new int[orderArr.length][2];
        for (int i = 0; i < orderArr.length; ++i) {
            this.orderArr[i][0] = orderArr[i];
            this.orderArr[i][1] = 1;
        }
        this.objComparator = new ObjectComparator[this.orderArr.length];
    }

    public void setOrderArr(int[][] orderArr) {
        this.orderArr = orderArr;
        this.objComparator = new ObjectComparator[this.orderArr.length];
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.comparator.ListComparator JD-Core Version: 0.5.4
 */