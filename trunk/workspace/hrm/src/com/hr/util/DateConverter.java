package com.hr.util;

import com.opensymphony.xwork2.XWorkException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import ognl.DefaultTypeConverter;

public class DateConverter extends DefaultTypeConverter {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Object convertValue(Map ognlContext, Object value, Class toType) {
        Object result = null;
        if (toType == Date.class)
            result = doConvertToDate(value);
        else if (toType == String.class) {
            result = doConvertToString(value);
        }
        return result;
    }

    private Date doConvertToDate(Object value) {
        Date result = null;
        if (value instanceof String) {
            Locale locale = Locale.getDefault();
            SimpleDateFormat simpDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat datchDate = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat d1 = (SimpleDateFormat)DateFormat.getDateTimeInstance(3, 1, locale);
            SimpleDateFormat d2 = (SimpleDateFormat)DateFormat.getDateTimeInstance(3, 2, locale);
            SimpleDateFormat d3 = (SimpleDateFormat)DateFormat.getDateTimeInstance(3, 3, locale);
            SimpleDateFormat dfs[] = {
                simpDate, sdf, datchDate, d1, d2, d3
            };
            for (int i = 0; i < dfs.length; i++) {
                try {
                    result = dfs[i].parse((String)value);
                    if (result != null) {
                        break;
                    }
                }
                catch (ParseException ignore) { }
            }

            if (result == null) {
                throw new XWorkException("Could not parse date- com.hr.util.DateConverter.java 48 line...");
            }
        } else
        if (value instanceof Object[]) {
            Object array[] = (Object[])(Object[])value;
            if (array != null && array.length >= 1) {
                value = array[0];
                result = doConvertToDate(value);
            }
        } else
        if (Date.class.isAssignableFrom(value.getClass())) {
            result = (Date)value;
        }
        return result;
    }

//    private Date doConvertToDate(Object value) {
//    Date result = null;
//    if (value instanceof String) {
//      Locale locale = Locale.getDefault();
//      SimpleDateFormat simpDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//      SimpleDateFormat datchDate = new SimpleDateFormat("yyyy/MM/dd");
//      SimpleDateFormat d1 = (SimpleDateFormat)DateFormat.getDateTimeInstance(3, 1, locale);
//      SimpleDateFormat d2 = (SimpleDateFormat)DateFormat.getDateTimeInstance(3, 2, locale);
//      SimpleDateFormat d3 = (SimpleDateFormat)DateFormat.getDateTimeInstance(3, 3, locale);
//      SimpleDateFormat[] dfs = { simpDate, sdf, datchDate, d1, d2, d3 };
//      for (int i = 0; i < dfs.length; ++i) {
//        try {
//          result = dfs[i].parse((String)value);
//          if (result != null)
//            break label148:
//        }
//        catch (ParseException ignore)
//        {
//        }
//      }
//      if (result == null)
//        label148: throw new XWorkException("Could not parse date- com.hr.util.DateConverter.java 48 line...");
//    }
//    else if (value instanceof Object[])
//    {
//      Object[] array = (Object[])(Object[])value;
//      if ((array != null) && (array.length >= 1)) {
//        value = array[0];
//        result = doConvertToDate(value);
//      }
//    } else if (Date.class.isAssignableFrom(value.getClass())) {
//      result = (Date)value;
//    }
//    return result;
//  }

    private String doConvertToString(Object value) {
        String result = null;
        if (value instanceof Date) {
            result = sdf.format(value);
        }
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.DateConverter JD-Core Version: 0.5.4
 */