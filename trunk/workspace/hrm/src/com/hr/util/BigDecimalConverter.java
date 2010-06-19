package com.hr.util;

import com.opensymphony.xwork2.XWorkException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import ognl.DefaultTypeConverter;

public class BigDecimalConverter extends DefaultTypeConverter {
    private String doConvertToString(Object value) {
        DecimalFormat decimalFormat = new DecimalFormat("##########0.00");
        String result = null;
        if (value instanceof BigDecimal) {
            return decimalFormat.format(value);
        }
        return result;
    }

    public Object convertValue(Map ognlContext, Object value, Class toType) {
        Object result = null;
        if (toType == BigDecimal.class)
            result = doConvertToBigDecimal(value);
        else if (toType == String.class) {
            result = doConvertToString(value);
        }
        return result;
    }

    private BigDecimal doConvertToBigDecimal(Object value) {
        BigDecimal result = null;

        if (value instanceof String) {
            try {
                result = new BigDecimal((String) value);
            } catch (Exception e) {
                throw new XWorkException("Could not parse date", e);
            }
        } else if (value instanceof Object[]) {
            Object[] array = (Object[]) (Object[]) value;
            if ((array != null) && (array.length >= 1)) {
                value = array[0];
                result = doConvertToBigDecimal(value);
            }
        } else if (BigDecimal.class.isAssignableFrom(value.getClass())) {
            result = (BigDecimal) value;
        }
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.BigDecimalConverter JD-Core Version: 0.5.4
 */