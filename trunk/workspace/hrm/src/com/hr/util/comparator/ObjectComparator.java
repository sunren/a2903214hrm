package com.hr.util.comparator;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Comparator;

public class ObjectComparator extends IComparator implements Comparator<Object> {
    private int order = 1;
    private String className;
    private Method method;

    public ObjectComparator() {
    }

    public ObjectComparator(int order, int[] compareMethod) {
        setOrder(order);
        if (compareMethod.length > 0)
            setCompareMethod(compareMethod[0]);
    }

    public int compare(Object obj1, Object obj2) {
        if ((obj1 == null) && (obj2 == null))
            return 0;
        if (obj1 == null)
            return -this.order;
        if (obj2 == null)
            return this.order;
        try {
            if ((this.className == null)
                    || (this.className.compareTo(obj1.getClass().getName()) != 0)) {
                this.className = obj1.getClass().getName();
                this.method = getMethodCompareTo(obj1, new int[] { this.compareMethod });
            }
            if (this.className == "java.lang.String") {
                return comparePinYin((String) obj1, (String) obj2) * this.order;
            }
            Integer resultTmp = (Integer) this.method.invoke(obj1, new Object[] { obj2 });
            if (resultTmp.intValue() != 0)
                return resultTmp.intValue() * this.order;
        } catch (Exception e) {
            System.out.print("==========error in compare: obj1,2=" + obj1.getClass().getName()
                    + ", " + obj2.getClass().getName());
            e.printStackTrace();
        }
        return 0;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.className = null;
        this.method = null;
        if (order != 0)
            this.order = order;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.comparator.ObjectComparator JD-Core Version: 0.5.4
 */