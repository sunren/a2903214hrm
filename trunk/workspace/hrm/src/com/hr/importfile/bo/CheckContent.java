package com.hr.importfile.bo;

import com.hr.io.domain.Iomatch;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.validator.GenericValidator;

public class CheckContent {
    public List<ErrorMessage> checkTransmit(String[][] content, List transmitList, int[] match,
            int titleOffset) {
        List matchList = new ArrayList();

        titleOffset += 1;

        for (int row = 0; row < content.length; ++row)
            for (int column = 0; column < match.length; ++column)
                if (match[column] != 0) {
                    Iomatch iomatch = (Iomatch) transmitList.get(match[column]);
                    String checkContent = content[row][column];

                    if (checkContent != null)
                        checkContent = checkContent.trim();

                    if ((iomatch.getIomatchRequired().intValue() == 1)
                            && (((checkContent == null) || (checkContent.length() < 1)))) {
                        ErrorMessage message = new ErrorMessage("第 " + (row + titleOffset) + " 行",
                                checkContent, "必填项缺少数据！", "第 " + (row + titleOffset) + " 行，第 "
                                        + (column + 1) + " 列数据为必填内容，文件数据为空！");

                        matchList.add(message);
                    } else {
                        if (iomatch.getIomatchRequired().intValue() == 0) {
                            if (checkContent == null)
                                continue;
                            if (checkContent.length() < 1)
                                continue;
                        }

                        if (!checkLength(iomatch.getIomatchLength().intValue(), checkContent,
                                         iomatch.getIomatchValidType().toLowerCase())) {
                            ErrorMessage message = new ErrorMessage("第 " + (row + titleOffset)
                                    + " 行", checkContent, "数据长度超过限制！", "第 " + (row + titleOffset)
                                    + " 行，第 " + (column + 1) + " 列数据长度超过限制，要求长度最大为"
                                    + iomatch.getIomatchLength() + "！");

                            matchList.add(message);
                        } else if (!checkDetail(iomatch.getIomatchValidType().toLowerCase(),
                                                checkContent)) {
                            ErrorMessage message = new ErrorMessage("第 " + (row + titleOffset)
                                    + " 行", checkContent, "数据类型匹配出错", "第 " + (row + titleOffset)
                                    + " 行，第 " + (column + 1) + " 列数据要求类型为"
                                    + iomatch.getIomatchValidType() + "，文件内容无法匹配该类型！");

                            matchList.add(message);
                        }
                    }
                }
        return matchList;
    }

    private boolean checkLength(int length, String content, String type) {
        if ((content == null) || (content.length() < 1))
            return true;

        if (type.equals("bigdecimal")) {
            if ((content.charAt(0) == '-') || (content.charAt(0) == '+'))
                content = content.substring(1);
            int index = content.lastIndexOf('.');
            if (index > 0)
                content = content.substring(0, index);
            if ((content == null) || (content.length() < 1))
                return true;
        }
        return content.length() <= length;
    }

    private boolean checkDetail(String type, String content) {
        if (type.equals("string"))
            return true;

        if (type.equals("date"))
            return isDate(content);

        if (type.equals("integer"))
            return isNumber(content);

        if (type.equals("bigdecimal"))
            return isBigdecimal(content);

        if (type.equals("zip"))
            return isZip(content);

        if (type.equals("phone"))
            return isPhone(content);

        if (type.equals("fax"))
            return isFax(content);

        if (type.equals("email"))
            return isEmail(content);

        if (type.equals("mobile"))
            return isMobile(content);

        if (type.equals("marry"))
            return isMarry(content);

        if (type.equals("gender"))
            return isGender(content);

        if (type.equals("boold"))
            return isBoold(content);

        if (type.equals("datetime"))
            return isDatetime(content);

        return true;
    }

    private boolean isPhone(String value) {
        return value.matches("^([0|1](\\d){1,3}[ ]?)?([-]?((\\d)|[ ]){1,12})(-(\\d){3,4})?$");
    }

    private boolean isZip(String value) {
        if (value.length() < 6)
            return false;
        return isNumber(value);
    }

    private boolean isMobile(String value) {
        return value.matches("^[1-9]{1,3}(\\d){8,9}$");
    }

    private boolean isEmail(String value) {
        return GenericValidator.isEmail(value);
    }

    private boolean isDate(String value) {
        return value.matches("^(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2})$");
    }

    private boolean isFax(String value) {
        return true;
    }

    private boolean isNumber(String value) {
        for (int i = 0; i < value.length(); ++i) {
            if ((value.charAt(i) > '9') || (value.charAt(i) < '0'))
                return false;
        }
        return true;
    }

    private boolean isMarry(String value) {
        return ("未婚".equals(value)) || ("已婚".equals(value));
    }

    private boolean isGender(String value) {
        return ("男".equals(value)) || ("女".equals(value));
    }

    private boolean isBoold(String value) {
        value = value.toUpperCase();
        return ("A".equals(value)) || ("B".equals(value)) || ("O".equals(value))
                || ("AB".equals(value));
    }

    private boolean isBigdecimal(String value) {
        if ((value.charAt(0) == '-') || (value.charAt(0) == '+')) {
            value = value.substring(1);

            if ((value == null) || (value.length() < 1))
                return false;
        }
        int index = value.lastIndexOf('.');
        if (index < 0)
            return isNumber(value);

        if ((value.length() - index > 3) || (value.length() - index == 1))
            return false;

        return (isNumber(value.substring(0, index))) && (isNumber(value.substring(index + 1)));
    }

    private boolean isDatetime(String value) {
        char tempChar = '0';
        for (int i = 0; i < value.length(); ++i) {
            tempChar = value.charAt(i);
            if ((((tempChar > '9') || (tempChar < '0'))) && (tempChar != '-') && (tempChar != '/')
                    && (tempChar != ':') && (tempChar != ' '))
                return false;
        }
        return true;
    }
}