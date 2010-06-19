package com.hr.util.reflect;

import java.io.PrintStream;
import java.lang.reflect.Method;

public class SetObjectProperty {
    public static final String fieldSeperator = "\\.";
    public static final String classPrefix = "com.hr";
    private String fieldName;
    private Method[] smethods;
    private Method[] gmethods;
    private Class[] paraClass;
    private int arrayLength = 0;

    public SetObjectProperty() {
    }

    public SetObjectProperty(Class objClass, String fieldName) throws Exception {
        initial(objClass, fieldName);
    }

    public void initial(String className, String fieldName) throws Exception {
        initial(Class.forName(className), fieldName);
    }

    public void initial(Class objClass, String fieldName) throws Exception {
        if ((fieldName == null) || (fieldName.length() < 1)) {
            System.out.println("==========error: fieldName is null: (" + objClass.getName() + " , "
                    + fieldName + ")");
            throw new Exception();
        }
        this.fieldName = fieldName;

        String[] fieldNameArray = fieldName.split("\\.");

        this.arrayLength = fieldNameArray.length;
        this.smethods = new Method[this.arrayLength];
        this.gmethods = new Method[this.arrayLength];
        this.paraClass = new Class[this.arrayLength];
        Class objectClass = objClass;

        for (int i = 0; i < this.arrayLength; ++i) {
            if (objectClass.getName().indexOf("com.hr") != 0) {
                System.out
                        .println("==========error: the declared class of fieldName should begin with com.hr: ("
                                + objClass.getName() + " , " + fieldName + ")");
                throw new Exception();
            }
            String methodNameTmp = fieldNameArray[i].substring(0, 1).toUpperCase()
                    + fieldNameArray[i].substring(1);

            this.gmethods[i] = objectClass.getMethod("get" + methodNameTmp, new Class[0]);

            this.paraClass[i] = this.gmethods[i].getReturnType();

            this.smethods[i] = objectClass.getMethod("set" + methodNameTmp,
                                                     new Class[] { this.paraClass[i] });
            objectClass = this.paraClass[i];
        }
    }

    public void setProperty(Object obj, Object property) throws Exception {
        if (property == null)
            return;
        Object property1;
        if ((property.getClass().getName() == "java.lang.String")
                && (this.paraClass[(this.arrayLength - 1)].getName() == "java.lang.Integer")) {
            property1 = Integer.valueOf(Integer.parseInt((String) property));
        } else {
            if (property.getClass().getName().compareTo(
                                                        this.paraClass[(this.arrayLength - 1)]
                                                                .getName()) != 0) {
                System.out.println("==========error: parameter class is not correct: need "
                        + this.paraClass[(this.arrayLength - 1)].getName() + " , input "
                        + property.getClass().getName());
                throw new Exception();
            }
            property1 = property;
        }

        Object objTmp = obj;
        for (int i = 0; i < this.arrayLength - 1; ++i) {
            Object objTmp1 = this.gmethods[i].invoke(objTmp, new Object[0]);
            if (objTmp1 == null) {
                this.smethods[i].invoke(objTmp, new Object[] { this.paraClass[i].newInstance() });
                objTmp = this.gmethods[i].invoke(objTmp, new Object[0]);
            } else {
                objTmp = objTmp1;
            }
        }

        this.smethods[(this.arrayLength - 1)].invoke(objTmp, new Object[] { property1 });
    }

    public static void setObjectProperty(Object obj, String fieldName, Object property)
            throws Exception {
        if (obj == null) {
            return;
        }
        if ((fieldName == null) || (fieldName.length() < 1)) {
            obj = property;
            return;
        }

        String[] fieldNameArray = fieldName.split("\\.");
        int arrayLength = fieldNameArray.length;
        Class objectClass = obj.getClass();

        Object objTmp = obj;
        for (int i = 0; i < arrayLength - 1; ++i) {
            String methodNameTmp = fieldNameArray[i].substring(0, 1).toUpperCase()
                    + fieldNameArray[i].substring(1);

            Method getMethod = objectClass.getMethod("get" + methodNameTmp, new Class[0]);

            Object objTmp1 = getMethod.invoke(objTmp, new Object[0]);
            if (objTmp1 == null) {
                Class paraClass = getMethod.getReturnType();
                Method setMethod = objectClass.getMethod("set" + methodNameTmp,
                                                         new Class[] { paraClass });
                Object objTmp2 = paraClass.newInstance();
                setMethod.invoke(objTmp, new Object[] { objTmp2 });
                objTmp = objTmp2;
            } else {
                objTmp = objTmp1;
            }
        }

        String methodNameTmp = fieldNameArray[(arrayLength - 1)].substring(0, 1).toUpperCase()
                + fieldNameArray[(arrayLength - 1)].substring(1);
        Method getMethod = objectClass.getMethod("get" + methodNameTmp, new Class[0]);
        Method setMethod = objectClass.getMethod("set" + methodNameTmp, new Class[] { getMethod
                .getReturnType() });
        setMethod.invoke(objTmp, new Object[] { property });
    }

    public String getFieldName() {
        return this.fieldName;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.reflect.SetObjectProperty JD-Core Version: 0.5.4
 */