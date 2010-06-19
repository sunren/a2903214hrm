package com.hr.util.comparator;

import com.hr.util.reflect.ObjectProperty;
import java.io.PrintStream;
import java.util.Comparator;

public class ObjectFieldComparator extends IComparator implements Comparator<Object> {
    private String[] fieldList;
    private int[] orderList;
    private ObjectProperty[] objectProperty;
    private ObjectComparator[] objComparator;

    public ObjectFieldComparator() {
    }

    public ObjectFieldComparator(Class objClass, String[] fieldList, int[] compareMethod)
            throws Exception {
        initial(objClass, fieldList, compareMethod);
    }

    public ObjectFieldComparator(Class objClass, String[] fieldList, int[] orderList,
            int[] compareMethod) throws Exception {
        initial(objClass, fieldList, orderList);
    }

    public void initial(Class objClass, String[] fieldList, int[] compareMethod) throws Exception {
        int[] orderList = new int[fieldList.length];
        for (int i = 0; i < fieldList.length; ++i) {
            orderList[i] = 1;
        }
        initial(objClass, fieldList, orderList, compareMethod);
    }

    public void initial(Class objClass, String[] fieldList, int[] orderList, int[] compareMethod)
            throws Exception {
        if ((fieldList == null) || (orderList == null) || (fieldList.length < 1)
                || (fieldList.length != orderList.length)) {
            System.out
                    .println("==========error: the format of fieldList and orderList is not valid");
            throw new Exception();
        }

        this.objectProperty = new ObjectProperty[fieldList.length];
        this.objComparator = new ObjectComparator[fieldList.length];
        for (int i = 0; i < fieldList.length; ++i) {
            this.objectProperty[i] = new ObjectProperty(objClass, fieldList[i]);
            this.objComparator[i] = new ObjectComparator(orderList[i], new int[0]);
        }

        this.fieldList = fieldList;
        this.orderList = orderList;
        if (compareMethod.length > 0)
            setCompareMethod(compareMethod[0]);
    }

    public int compare(Object obj1, Object obj2) {
        for (int i = 0; i < this.fieldList.length; ++i) {
            int resultTmp = this.objComparator[i].compare(this.objectProperty[i].getProperty(obj1),
                                                          this.objectProperty[i].getProperty(obj2));
            if (resultTmp != 0) {
                return resultTmp;
            }
        }
        return 0;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.comparator.ObjectFieldComparator JD-Core Version: 0.5.4
 */