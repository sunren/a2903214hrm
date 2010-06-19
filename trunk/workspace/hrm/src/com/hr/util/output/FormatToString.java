package com.hr.util.output;

import com.hr.base.ComonBeans;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class FormatToString {
    public static final String _STRING = "string";
    public static final String _DECIMAL = "decimal";
    public static final String _DATE = "date";
    public static final String _INTEGER = "integer";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private String dataType;
    private String format;
    private SimpleDateFormat simpleDateFormat;
    private Integer num;
    private Map<String, String> explains;

    public FormatToString() {
    }

    public FormatToString(String dataType, String format) throws Exception {
        setFormat(dataType, format);
    }

    public String formatToString(Object obj) throws Exception {
        if (obj == null) {
            return "";
        }
        if (this.dataType.compareToIgnoreCase("date") == 0)
            return dateFormatToString(obj);
        if ((this.dataType.compareToIgnoreCase("string") == 0) && (this.num != null))
            return strMaxLen(obj);
        if ((this.dataType.compareToIgnoreCase("decimal") == 0) && (this.num != null))
            return numScaleToString(obj);
        if (this.explains != null) {
            return (String) this.explains.get(obj.toString());
        }
        return obj.toString();
    }

    public Object format(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        if (this.dataType.compareToIgnoreCase("date") == 0) {
            return dateFormat(obj);
        }
        if ((this.dataType.compareToIgnoreCase("string") == 0) && (this.num != null)) {
            return strMaxLen(obj);
        }
        if ((this.dataType.compareToIgnoreCase("decimal") == 0)
                || (this.dataType.compareToIgnoreCase("integer") == 0)) {
            return numScale(obj);
        }
        if (this.explains != null) {
            return this.explains.get(obj.toString());
        }
        return obj.toString();
    }

    public String toStringAfterFormat(Object obj) throws Exception {
        if (obj == null) {
            return "";
        }
        if (this.dataType.compareToIgnoreCase("date") == 0) {
            return dateFormatToString(obj);
        }
        return obj.toString();
    }

    public String strMaxLen(Object obj) {
        String str = obj.toString();
        return (str.length() > this.num.intValue()) ? str.substring(0, this.num.intValue()) : str;
    }

    public String numScaleToString(Object obj) throws Exception {
        String objClass = obj.getClass().getName();
        BigDecimal bdNum;
        if (objClass.compareTo("java.math.BigDecimal") == 0) {
            bdNum = (BigDecimal) obj;
        } else {
            if (objClass.compareTo("java.lang.Double") == 0) {
                Double obj1 = (Double) obj;
                bdNum = BigDecimal.valueOf(obj1.doubleValue());
            } else {
                String result = obj.toString();
                bdNum = new BigDecimal(result);
            }
        }
        bdNum = bdNum.setScale(this.num.intValue(), 6);
        return bdNum.toString();
    }

    public BigDecimal numScale(Object obj) throws Exception {
        String objClass = obj.getClass().getName();
        BigDecimal bdNum;
        if (objClass.compareTo("java.math.BigDecimal") == 0) {
            bdNum = (BigDecimal) obj;
        } else {
            if (objClass.compareTo("java.lang.Double") == 0) {
                Double obj1 = (Double) obj;
                bdNum = BigDecimal.valueOf(obj1.doubleValue());
            } else {
                String result = obj.toString();
                bdNum = new BigDecimal(result);
            }
        }
        if (this.num != null) {
            bdNum = bdNum.setScale(this.num.intValue(), 6);
        }
        return bdNum;
    }

    public String dateFormatToString(Object obj) throws Exception {
        return this.simpleDateFormat.format((Date) obj);
    }

    public Date dateFormat(Object obj) throws Exception {
        return this.simpleDateFormat.parse(this.simpleDateFormat.format((Date) obj));
    }

    public String getDataType() {
        return this.dataType;
    }

    public boolean setFormat(String dataType, String format) {
        this.dataType = null;
        this.format = null;
        this.num = null;
        this.explains = null;
        if ((dataType == null) || (dataType.length() < 1)) {
            return false;
        }
        if ((format == null) || (format.length() < 1))
            format = null;
        try {
            if ((dataType.compareToIgnoreCase("string") == 0)
                    || (dataType.compareToIgnoreCase("decimal") == 0)) {
                if (format != null) {
                    this.num = new Integer(format);
                }
                this.format = format;
            } else if (dataType.compareToIgnoreCase("date") == 0) {
                this.simpleDateFormat = new SimpleDateFormat(format);
                this.format = format;
            } else if (dataType.compareToIgnoreCase("integer") == 0) {
                this.format = null;
            } else {
                this.format = null;
                this.explains = ComonBeans.getValuesToMap(dataType, new boolean[0]);
                if (this.explains.size() < 1) {
                    this.explains = null;
                    return false;
                }
            }
            this.dataType = dataType;
            return true;
        } catch (Exception e) {
            if (dataType.compareToIgnoreCase("date") == 0)
                this.format = "yyyy-MM-dd";
            else
                this.format = null;
        }
        return false;
    }

    public String getFormat() {
        return this.format;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.output.FormatToString JD-Core Version: 0.5.4
 */