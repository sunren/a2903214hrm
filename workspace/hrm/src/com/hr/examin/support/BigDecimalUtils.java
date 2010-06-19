package com.hr.examin.support;

import java.math.BigDecimal;

public class BigDecimalUtils {
    public static boolean isNotNull(BigDecimal number) {
        return !isNull(number);
    }

    public static boolean isNull(BigDecimal number) {
        return number == null;
    }

    public static boolean isZero(BigDecimal number) {
        return (isNotNull(number)) && (number.compareTo(BigDecimal.ZERO) == 0);
    }

    public static boolean isBiggerThanZero(BigDecimal number) {
        return (isNotNull(number)) && (number.compareTo(BigDecimal.ZERO) > 0);
    }

    public static boolean isLessThanZero(BigDecimal number) {
        return (isNotNull(number)) && (number.compareTo(BigDecimal.ZERO) < 0);
    }

    public static boolean isEqual(BigDecimal number1, BigDecimal number2) {
        return number1.compareTo(number2) == 0;
    }

    public static BigDecimal convertToZeroIfNull(BigDecimal bigDecimal) {
        return (isNull(bigDecimal)) ? new BigDecimal(0) : bigDecimal;
    }

    public static BigDecimal add(BigDecimal num1, BigDecimal num2) {
        return convertToZeroIfNull(num1).add(convertToZeroIfNull(num2));
    }

    public static BigDecimal substract(BigDecimal num1, BigDecimal num2) {
        return convertToZeroIfNull(num1).subtract(num2);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.examin.support.BigDecimalUtils JD-Core Version: 0.5.4
 */