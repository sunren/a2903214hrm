package com.hr.util.comparator;

import java.lang.reflect.Method;
import net.sourceforge.pinyin4j.PinyinHelper;

public class IComparator {
    public static final int _NORMAL = 0;
    public static final int _IGNORECASE = 1;
    protected int compareMethod = 1;

    public int getCompareMethod() {
        return this.compareMethod;
    }

    public void setCompareMethod(int compareMethod) {
        this.compareMethod = compareMethod;
    }

    public static Method getMethodCompareTo(Object obj, int[] compareMethod) throws Exception {
        return getMethodCompareTo(obj.getClass(), compareMethod);
    }

    public static Method getMethodCompareTo(Class objClass, int[] compareMethod) throws Exception {
        String methodName = "compareTo";
        if ((objClass.getName().compareTo("java.lang.String") == 0) && (compareMethod.length > 0)
                && (compareMethod[0] == 1)) {
            methodName = "compareToIgnoreCase";
        }
        Method result = objClass.getMethod(methodName, new Class[] { objClass });
        return result;
    }

    public static int compareObject(Object obj1, Object obj2) throws Exception {
        if ((obj1 == null) && (obj2 == null))
            return 0;
        if (obj1 == null)
            return -1;
        if (obj2 == null) {
            return 1;
        }
        Method methodCompareTo = getMethodCompareTo(obj1, new int[] { 0 });
        Integer resultTmp = (Integer) methodCompareTo.invoke(obj1, new Object[] { obj2 });
        return resultTmp.intValue();
    }

    public static int comparePinYin(String o1, String o2) {
        for (int i = 0; (i < o1.length()) && (i < o2.length()); ++i) {
            int codePoint1 = o1.charAt(i);
            int codePoint2 = o2.charAt(i);

            if (codePoint1 == codePoint2) {
                continue;
            }

            if ((codePoint1 >= 65) && (codePoint1 <= 90)) {
                codePoint1 += 32;
            }
            if ((codePoint2 >= 65) && (codePoint2 <= 90)) {
                codePoint2 += 32;
            }
            if ((codePoint1 < 256) || (codePoint2 < 256)) {
                return codePoint1 - codePoint2;
            }
            String pinyin1 = pinyin((char) codePoint1);
            String pinyin2 = pinyin((char) codePoint2);
            if ((pinyin1 != null) && (pinyin2 != null) && (!pinyin1.equals(pinyin2))) {
                return pinyin1.compareTo(pinyin2);
            }

            return codePoint1 - codePoint2;
        }

        return o1.length() - o2.length();
    }

    private static String pinyin(char c) {
        String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
        if (pinyins == null) {
            return null;
        }
        return pinyins[0];
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.comparator.IComparator JD-Core Version: 0.5.4
 */