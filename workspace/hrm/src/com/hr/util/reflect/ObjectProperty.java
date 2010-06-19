package com.hr.util.reflect;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectProperty {
    public static final String fieldSeperator = "\\.";
    private String fieldName;
    private Class returnType;
    private Method[] methods;
    private Object[] keys;

    public ObjectProperty() {
    }

    public ObjectProperty(Class objClass, String fieldName) throws Exception {
        initial(objClass, fieldName);
    }

    public void initial(Class objClass, String fieldName) throws Exception {
        if ((fieldName == null) || (fieldName.length() < 1)) {
            this.methods = null;
            return;
        }
        this.fieldName = fieldName;
        List methodKeyList = getInterParaForObjectProperty(objClass, fieldName);
        List<Method> methodList = (List) methodKeyList.get(0);
        this.methods = new Method[methodList.size()];
        int i = 0;
        for (Method method : methodList) {
            this.methods[(i++)] = method;
        }
        this.keys = ((List) methodKeyList.get(1)).toArray();
        this.returnType = this.methods[(this.methods.length - 1)].getReturnType();
    }

    public Object getProperty(Object owner) {
        if (this.methods == null)
            return owner;
        try {
            Object property = owner;

            for (int i = 0; i < this.methods.length; ++i) {
                if (property == null) {
                    return null;
                }
                if (this.keys[i] != null)
                    property = this.methods[i].invoke(property, new Object[] { this.keys[i] });
                else {
                    property = this.methods[i].invoke(property, new Object[0]);
                }
            }
            return property;
        } catch (Exception e) {
            System.out.println("==========error:" + owner + "-->" + this.fieldName);
            e.printStackTrace();
        }
        return null;
    }

    public Double getDouble(Object owner) throws Exception {
        Double result = new Double(0.0D);
        Object obj = getProperty(owner);
        if (obj == null) {
            return result;
        }
        String sd = obj.toString();
        if (sd.length() < 1) {
            return result;
        }
        result = Double.valueOf(sd);
        return result;
    }

    public static List<List> getInterParaForObjectProperty(Class objClass, String fieldName)
            throws Exception {
        List result = new ArrayList();
        if ((fieldName == null) || (fieldName.length() < 1)) {
            return result;
        }
        List methodList = new ArrayList();
        List keyList = new ArrayList();

        String[] fieldNameArray = fieldName.split("\\.");
        Class objectClass = objClass;

        for (int i = 0; i < fieldNameArray.length; ++i) {
            Object keyTmp;
            Method methodTmp;
            if (objectClass.getName().compareTo("java.util.List") == 0) {
                methodTmp = objectClass.getMethod("get", new Class[] { Integer.TYPE });
                keyTmp = new Integer(fieldNameArray[i]);
            } else {
                if (objectClass.getName().compareTo("java.util.Map") == 0) {
                    methodTmp = objectClass.getMethod("get", new Class[] { Object.class });
                    keyTmp = fieldNameArray[i];
                } else {
                    methodTmp = objectClass.getMethod("get"
                            + fieldNameArray[i].substring(0, 1).toUpperCase()
                            + fieldNameArray[i].substring(1), new Class[0]);

                    keyTmp = null;
                }
            }
            methodList.add(methodTmp);
            keyList.add(keyTmp);
            objectClass = methodTmp.getReturnType();
        }
        result.add(methodList);
        result.add(keyList);
        return result;
    }

    private static Object getObjectProperty(Object owner, String fieldName) throws Exception {
        if ((fieldName == null) || (fieldName.length() < 1))
            return owner;
        if (owner == null) {
            return null;
        }

        Class ownerClass = owner.getClass();
        if (ownerClass.getName().compareTo("java.util.List") == 0) {
            Integer i = new Integer(fieldName);
            List list = (List) owner;
            return list.get(i.intValue());
        }
        Method method = ownerClass.getMethod("get" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1), new Class[0]);

        Object property = method.invoke(owner, new Object[0]);
        return property;
    }

    public static Object getObjectFinalProperty(Object owner, String fieldName) throws Exception {
        if ((fieldName == null) || (fieldName.length() < 1))
            return owner;
        String[] fieldNameArray = fieldName.split("\\.");
        if (owner == null)
            return null;
        Object property = getObjectProperty(owner, fieldNameArray[0]);
        for (int i = 1; i < fieldNameArray.length; ++i) {
            property = getObjectProperty(property, fieldNameArray[i]);
        }
        return property;
    }

    public static Double getObjectDoublelValue(Object owner) throws Exception {
        Double result = new Double(0.0D);
        if (owner == null)
            return result;
        String sd = owner.toString();
        if (sd.length() < 1) {
            return result;
        }
        result = Double.valueOf(sd);
        return result;
    }

    public static double getObjectDoublelValue(Object owner, Method doubleValueMethode)
            throws Exception {
        if (owner == null) {
            return 0.0D;
        }
        return ((Double) doubleValueMethode.invoke(owner, new Object[0])).doubleValue();
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public Class getReturnType() {
        return this.returnType;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.reflect.ObjectProperty JD-Core Version: 0.5.4
 */