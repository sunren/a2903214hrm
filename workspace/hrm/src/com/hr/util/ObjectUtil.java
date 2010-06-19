package com.hr.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;

public class ObjectUtil {
    public List<String> convertObjListToIdList(List<Object> objList) {
        List idList = new ArrayList();
        String methodName = "getId";
        for (Iterator i$ = objList.iterator(); i$.hasNext();) {
            Object obj = i$.next();
            Class objClass = obj.getClass();
            try {
                Method method = objClass.getMethod(methodName, (Class[]) null);
                String id = (String) method.invoke(obj, (Object[]) null);
                idList.add(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            continue;
        }

        return idList;
    }

    public static String[] convObjToIdArr(Object[] objArr, String[] fieldName) {
        String[] idArr = new String[objArr.length];
        String name = "id";
        if ((fieldName != null) && (fieldName.length > 0)) {
            name = fieldName[0];
        }
        for (int i = 0; i < objArr.length; ++i) {
            try {
                Object id = PropertyUtils.getNestedProperty(objArr[i], name);
                idArr[i] = ((String) id);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return idArr;
    }

    public static boolean contains(Object[] array, Object toComp) {
        if ((array == null) || (array.length == 0))
            return false;

        for (int i = 0; i < array.length; ++i) {
            if (toComp.equals(array[i]))
                return true;
        }
        return false;
    }

    public static boolean contains(List list, Object toComp) {
        if ((list == null) || (list.size() == 0))
            return false;

        for (Iterator i$ = list.iterator(); i$.hasNext();) {
            Object obj = i$.next();
            if (toComp.equals(obj))
                return true;
        }

        return false;
    }

    public static Object[] copy(Object[] sourceArray, int newLength) {
        if ((sourceArray == null) || (sourceArray.length == 0)) {
            return sourceArray;
        }
        Object[] result = new Object[newLength];

        int size = sourceArray.length;
        if (newLength < size)
            size = newLength;

        for (int i = 0; i < size; ++i) {
            result[i] = sourceArray[i];
        }
        return result;
    }

    public static Object[] reduceByIndex(Object[] array, Integer[] indexToRemove) {
        if ((indexToRemove == null) || (indexToRemove.length == 0) || (array == null)
                || (array.length == 0)) {
            return array;
        }

        int resultNos = 0;
        Integer[] arr$ = indexToRemove;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; ++i$) {
            int index = arr$[i$].intValue();
            if (index < 0)
                continue;
            if (index < array.length)
                ++resultNos;
        }

        if (resultNos == 0)
            return array;
        Object[] result = new Object[array.length - resultNos];

        int resultIndex = 0;
        for (int i = 0; i < array.length; ++i)
            if (!contains(indexToRemove, Integer.valueOf(i))) {
                result[resultIndex] = array[i];
                ++resultIndex;
            }
        return result;
    }

    public static List<Object> reduceByIndex(List<Object> sourceList, Integer[] indexToRemove) {
        if ((indexToRemove == null) || (indexToRemove.length == 0) || (sourceList == null)
                || (sourceList.size() == 0)) {
            return sourceList;
        }

        List targetList = new ArrayList();
        for (int i = 0; i < sourceList.size(); ++i) {
            if (!contains(indexToRemove, Integer.valueOf(i)))
                targetList.add(sourceList.get(i));
        }
        return targetList;
    }

    public static Object[] reduce(Object[] array, Object[] itemsToRemove) {
        if ((itemsToRemove == null) || (itemsToRemove.length == 0) || (array == null)
                || (array.length == 0)) {
            return array;
        }

        Object[] tempArray = new Object[array.length];
        int tempArrayIndex = 0;
        for (int i = 0; i < array.length; ++i) {
            if (!contains(itemsToRemove, array[i])) {
                tempArray[tempArrayIndex] = array[i];
                ++tempArrayIndex;
            }
        }
        if (tempArrayIndex == 0)
            return null;
        if (tempArrayIndex == array.length)
            return tempArray;

        return copy(tempArray, tempArrayIndex);
    }

    public static List<Object> reduce(List<Object> sourceList, Object[] itemsToRemove) {
        if ((itemsToRemove == null) || (itemsToRemove.length == 0) || (sourceList == null)
                || (sourceList.size() == 0)) {
            return sourceList;
        }

        List targetList = new ArrayList();
        for (Iterator i$ = sourceList.iterator(); i$.hasNext();) {
            Object source = i$.next();
            if (!contains(itemsToRemove, source))
                ;
            targetList.add(source);
        }

        return targetList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.ObjectUtil JD-Core Version: 0.5.4
 */