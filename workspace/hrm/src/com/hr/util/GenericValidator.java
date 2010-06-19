package com.hr.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenericValidator implements Serializable {
    private static final long serialVersionUID = 8536524636265626889L;

    public static boolean isPhone(String value) {
        return value.matches("^([0|1](\\d){1,3}[ ]?)?([-]?((\\d)|[ ]){1,12})(-(\\d){1,5})?$");
    }

    public static boolean isMobile(String value) {
        return value.matches("^[1-9]{1,3}(\\d){8,9}$");
    }

    public static boolean isNO(String value) {
        return value.matches("^([a-zA-Z0-9]|[-_]){0,64}$");
    }

    public static boolean isNumber(String value, int l) {
        return (value.matches("^\\d*$")) && (value.length() == l);
    }

    public static boolean isNumber(String value) {
        return value.matches("^\\d*$");
    }

    public static boolean isEmail(String value) {
        return org.apache.commons.validator.GenericValidator.isEmail(value);
    }

    public static boolean isBlankOrNull(String value) {
        return (value == null) || (value.trim().length() == 0);
    }

    public static boolean isByte(String value) {
        if (value == null) {
            return false;
        }
        try {
            new Byte(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isShort(String value) {
        if (value == null) {
            return false;
        }
        try {
            new Short(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isInt(String value) {
        if (value == null) {
            return false;
        }
        try {
            new Integer(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isLong(String value) {
        if (value == null) {
            return false;
        }
        try {
            new Long(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isFloat(String value) {
        if (value == null) {
            return false;
        }
        try {
            new Float(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String value) {
        if (value == null) {
            return false;
        }
        try {
            new Double(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isInRange(byte value, byte min, byte max) {
        return (value >= min) && (value <= max);
    }

    public static boolean isInRange(int value, int min, int max) {
        return (value >= min) && (value <= max);
    }

    public static boolean isInRange(float value, float min, float max) {
        return (value >= min) && (value <= max);
    }

    public static boolean isInRange(short value, short min, short max) {
        return (value >= min) && (value <= max);
    }

    public static boolean isInRange(long value, long min, long max) {
        return (value >= min) && (value <= max);
    }

    public static boolean isInRange(double value, double min, double max) {
        return (value >= min) && (value <= max);
    }

    public static boolean maxLength(String value, int max) {
        return value.length() <= max;
    }

    public static boolean minLength(String value, int min) {
        return value.length() >= min;
    }

    public static boolean isDate(String var, String datePattern) {
        SimpleDateFormat DF = new SimpleDateFormat(datePattern);
        try {
            DF.parse(var);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isDate(String value) {
        if ((value == null) || (value.trim().length() < 2)) {
            return false;
        }
        return value.matches("^(\\d{4})(-|\\/)(\\d{2})\\2(\\d{2})$");
    }

    public static boolean isValidPhone(String pno) {
        boolean b = true;
        if (null == pno)
            b = false;
        if ((b) && (pno.startsWith("86")))
            pno = pno.substring(2);
        if ((b) && (((!pno.startsWith("13")) || (pno.length() != 11))))
            b = false;
        return b;
    }

    public static boolean isCharNum(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean value = true;
        try {
            p = Pattern.compile("[^0-9a-zA-z]");
            m = p.matcher(str);
            if (m.find())
                value = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static boolean isCharacter(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean value = true;
        try {
            p = Pattern.compile("[^a-zA-z]");
            m = p.matcher(str);
            if (m.find())
                value = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.GenericValidator JD-Core Version: 0.5.4
 */