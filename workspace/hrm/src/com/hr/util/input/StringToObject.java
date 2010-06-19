package com.hr.util.input;

import com.hr.base.ComonBeans;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Map;

public class StringToObject {
    public static final String _STRING = "string";
    public static final String _DECIMAL = "decimal";
    public static final String _DATE = "date";
    public static final String _INTEGER = "integer";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String SPECIAL_DATATYPE_PREFIX = "_";
    private String dataType;
    private String format;
    private int detectError;
    private int resultType;
    private SimpleDateFormat simpleDateFormat;
    private Integer num;
    private Map<String, String> explains;
    private Method checkMethod;

    public StringToObject() {
    }

    public StringToObject(String dataType, String format, int[] detectError) throws Exception {
        setFormat(dataType, format, detectError);
    }

    public Object toObject(String str) {
        this.resultType = 0;

        if ((str == null) || (str.length() < 1))
            return null;
        try {
            if (this.dataType.equalsIgnoreCase("string")) {
                if ((this.num != null) && (str.length() > this.num.intValue())) {
                    if (this.detectError == 1) {
                        this.resultType = -1;
                        return null;
                    }
                    this.resultType = 1;
                    return str.substring(0, this.num.intValue());
                }

                return str;
            }
            if (this.dataType.equalsIgnoreCase("integer"))
                return new Integer(str);
            if (this.dataType.equalsIgnoreCase("decimal")) {
                BigDecimal result = new BigDecimal(str);
                if ((this.num != null) && (result.scale() > this.num.intValue())) {
                    if (this.detectError == 1) {
                        this.resultType = -1;
                        return null;
                    }
                    this.resultType = 1;
                    return result.setScale(this.num.intValue(), 6);
                }

                return result;
            }
            if (this.dataType.equalsIgnoreCase("date"))
                return this.simpleDateFormat.parse(str);
            if (this.dataType.indexOf("_") == 0) {
                if ((this.num != null) && (str.length() > this.num.intValue())) {
                    if (this.detectError == 1) {
                        this.resultType = -1;
                        return null;
                    }
                    this.resultType = 1;
                    return str.substring(0, this.num.intValue());
                }

                if (this.detectError == 1) {
                    Boolean checkResult = (Boolean) this.checkMethod.invoke(this,
                                                                            new Object[] { str });
                    if (!checkResult.booleanValue()) {
                        this.resultType = -1;
                        return null;
                    }
                }
                return str;
            }
            if ((this.detectError == 1) && (!this.explains.containsKey(str))) {
                this.resultType = -1;
                return null;
            }
            return this.explains.get(str);
        } catch (Exception e) {
            this.resultType = -1;
        }
        return null;
    }

    public boolean checkStr(String str) {
        toObject(str);

        return this.resultType == 0;
    }

    public static String getObjectType(String dataType) {
        String result = null;
        if (dataType.equalsIgnoreCase("date"))
            result = "java.util.Date";
        else if (dataType.equalsIgnoreCase("integer"))
            result = "java.lang.Integer";
        else if (dataType.equalsIgnoreCase("decimal"))
            result = "java.math.BigDecimal";
        else {
            result = "java.lang.String";
        }
        return result;
    }

    public static boolean isEmail(String str) {
        return true;
    }

    public static boolean isZip(String str) {
        return true;
    }

    public static boolean isPhone(String str) {
        return true;
    }

    public static boolean isMobile(String str) {
        return true;
    }

    public static boolean isMsn(String str) {
        return true;
    }

    public boolean setFormat(String dataType, String format, int[] detectError) {
        this.dataType = null;
        this.format = null;
        this.detectError = 0;

        this.num = null;
        this.explains = null;
        if ((dataType == null) || (dataType.length() == 0)) {
            return false;
        }
        if ((format == null) || (format.length() == 0))
            format = null;
        try {
            if ((dataType.equalsIgnoreCase("string")) || (dataType.equalsIgnoreCase("decimal"))) {
                if (format != null) {
                    this.num = new Integer(format);
                }
                this.format = format;
            } else if (dataType.equalsIgnoreCase("date")) {
                this.simpleDateFormat = new SimpleDateFormat(format);
                this.format = format;
            } else if (dataType.equalsIgnoreCase("integer")) {
                this.format = null;
            } else if (dataType.indexOf("_") == 0) {
                if (format != null) {
                    this.num = new Integer(format);
                }
                String methodName = dataType.substring("_".length());
                methodName = "is" + methodName.substring(0, 1).toUpperCase()
                        + methodName.substring(1);
                this.checkMethod = StringToObject.class.getMethod(methodName,
                                                                  new Class[] { String.class });
                this.format = format;
            } else {
                this.format = null;
                this.explains = ComonBeans.getValuesToMapInverse(dataType);
                if (this.explains.size() < 1) {
                    this.explains = null;
                    return false;
                }
            }
            this.dataType = dataType;
            if (detectError.length > 0) {
                this.detectError = detectError[0];
            }
            return true;
        } catch (Exception e) {
            if (dataType.equalsIgnoreCase("date"))
                this.format = "yyyy-MM-dd";
            else if (dataType.indexOf("_") == 0)
                this.dataType = "string";
        }
        return false;
    }

    public String getFormat() {
        return this.format;
    }

    public String getDataType() {
        return this.dataType;
    }

    public int getDetectError() {
        return this.detectError;
    }

    public int getResultType() {
        return this.resultType;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.input.StringToObject JD-Core Version: 0.5.4
 */