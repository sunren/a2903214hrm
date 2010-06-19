package com.hr.util;

import com.hr.base.Constants;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

public class StringUtil {
    private static final String algorithm = "MD5";

    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());

            byte[] b = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < b.length; ++i) {
                int v = b[i];
                v = (v < 0) ? 256 + v : v;
                String cc = Integer.toHexString(v);
                if (cc.length() == 1)
                    sb.append('0');
                sb.append(cc);
            }

            return sb.toString();
        } catch (Exception e) {
        }
        return "";
    }

    public static String encodePassword(String password) {
        byte[] unencodedPassword = password.getBytes();

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }

        md.reset();

        md.update(unencodedPassword);

        byte[] encodedPassword = md.digest();

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < encodedPassword.length; ++i) {
            if ((encodedPassword[i] & 0xFF) < 16) {
                buf.append("0");
            }
            buf.append(Long.toString(encodedPassword[i] & 0xFF, 16));
        }
        return buf.toString();
    }

    public static String[][] merge2D(String[][] twoDArray1, String[][] twoDArray2) {
        int size1 = 0;
        int size2 = 0;

        if (twoDArray1 != null)
            size1 = twoDArray1.length;
        if (twoDArray2 != null)
            size2 = twoDArray2.length;

        if (size1 + size2 == 0)
            return (String[][]) null;

        String[][] merge2DArray = new String[size1 + size2][2];
        for (int i = 0; i < size1; ++i) {
            merge2DArray[i][0] = twoDArray1[i][0];
            merge2DArray[i][1] = twoDArray1[i][1];
        }
        for (int i = 0; i < size2; ++i) {
            merge2DArray[(i + size1)][0] = twoDArray2[i][0];
            merge2DArray[(i + size1)][1] = twoDArray2[i][1];
        }
        return merge2DArray;
    }

    public static String[][] merge(String[][] sourceArray) {
        return sourceArray;
    }

    public static String[] convert2DTo1D(String[][] twoDArray) {
        if (twoDArray == null) {
            return null;
        }
        String[] result = new String[twoDArray.length];
        for (int i = 0; i < twoDArray.length; ++i) {
            result[i] = twoDArray[i][0];
        }
        return result;
    }

    public static String[] merge1D(String[] oneDArray1, String[] oneDArray2) {
        int size1 = 0;
        int size2 = 0;

        if (oneDArray1 != null)
            size1 = oneDArray1.length;
        if (oneDArray2 != null)
            size2 = oneDArray2.length;

        String[] merge1DArray = new String[size1 + size2];
        for (int i = 0; i < size1; ++i) {
            merge1DArray[i] = oneDArray1[i];
        }
        for (int i = 0; i < size2; ++i) {
            merge1DArray[(i + size1)] = oneDArray2[i];
        }
        return merge1DArray;
    }

    public static String[] merge1D(String[] oneDArray1, String stringToMerge) {
        if (oneDArray1 == null) {
            String[] result = new String[1];
            result[0] = new String(stringToMerge);
            return result;
        }
        String[] result = new String[oneDArray1.length + 1];
        for (int i = 0; i < oneDArray1.length; ++i) {
            result[i] = oneDArray1[i];
        }
        result[oneDArray1.length] = stringToMerge;
        return result;
    }

    public static String splitForIn(String[] oneDArray) {
        if ((oneDArray == null) || (oneDArray.length == 0))
            return null;

        StringBuffer idStr = new StringBuffer("");
        for (String str : oneDArray) {
            idStr.append("'").append(str).append("',");
        }
        return idStr.substring(0, idStr.length() - 1);
    }

    public static Set<Integer> authStringToSet(String auth) {
        Set authSet = new HashSet();
        if (auth == null)
            return authSet;

        String[] auths = auth.split(",");
        for (int i = 0; i < auths.length; ++i) {
            authSet.add(Integer.valueOf(Integer.parseInt(auths[i])));
        }
        return authSet;
    }

    public static String authSetToString(Set<Integer> auth) {
        if (auth == null)
            return "";
        String authString = "";
        Iterator iter = auth.iterator();
        while (iter.hasNext()) {
            authString = authString + iter.next() + ",";
        }
        if (authString.length() == 0)
            return "";
        authString = authString.substring(0, authString.length() - 1);

        return authString;
    }

    public static String authSetToType(Set<Integer> authSet) {
        int suthSetSize = authSet.size();
        if ((authSet.removeAll(Constants.ADM_AUTHS)) || (authSet.size() < suthSetSize))
            return "USERADM";
        if ((authSet.removeAll(Constants.MGR_AUTHS)) || (authSet.size() < suthSetSize))
            return "USERMGR";
        return "USEREMP";
    }

    public static String authStringToType(String auth) {
        Set authSet = authStringToSet(auth);
        return authSetToType(authSet);
    }

    public static String message(String message, Object[] variables) {
        String output = MessageFormat.format(message, variables);
        return output;
    }

    public static String formatBDToS(BigDecimal value, String[] format) {
        if (value == null) {
            return null;
        }
        String DCformat = "##########0.00";
        if ((format.length > 0) && (!StringUtils.isEmpty(format[0]))) {
            DCformat = format[0];
        }
        DecimalFormat decimalFormat = new DecimalFormat(DCformat);
        return decimalFormat.format(value);
    }

    public static String setToString(Set<String> setMsg) {
        if (setMsg == null)
            return " ";
        String msgString = "";
        Iterator iter = setMsg.iterator();
        while (iter.hasNext()) {
            msgString = msgString + iter.next() + ", ";
        }
        if (msgString.length() == 0)
            return " ";
        msgString = msgString.substring(0, msgString.length() - 2);

        return msgString;
    }

    public static String getClassName(Object obj) {
        if (obj == null)
            return null;
        String className = obj.getClass().getName();
        int index = className.lastIndexOf(".");
        return className.substring(index + 1);
    }

    public static String getClassNameLowCase(Object obj) {
        return getClassName(obj).toLowerCase();
    }

    public static String messageNoErrCode(String message, Object[] variables) {
        String output = MessageFormat.format(message, variables);
        int index = output.indexOf(":");
        if ((index >= 0) && (index <= 16)) {
            return output.substring(index + 1);
        }
        return output;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.StringUtil JD-Core Version: 0.5.4
 */