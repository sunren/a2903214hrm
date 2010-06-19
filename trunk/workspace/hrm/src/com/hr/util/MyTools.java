package com.hr.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyTools
{
  public static int ENCRYPT_MODE = 0;

  public static int DECRYPT_MODE = 1;

  public static String comKeyStr = "TYHR";

  public static String comKeyBd = "123456789";

  public static String keyStr = "TYHR";

  public static String keyBd = "123456789";

  public static int STRING = 0;

  public static int BIGDECIMAL = 1;

  public static int CHINESE = 2;
  public static final int BUFFER_SIZE = 1024;

  public static String nullToStr(String inString, String newString)
  {
    if ((inString == null) && (newString != null)) {
      return newString;
    }
    return inString;
  }

  public static String dateToFormat(Date inputeDate)
  {
    DateFormat df1 = DateFormat.getDateInstance(1);
    DateFormat df2 = DateFormat.getTimeInstance(2);
    return df1.format(inputeDate) + df2.format(inputeDate);
  }

  public static String isotogb(String ori)
  {
    String newstr = "";
    try {
      if (ori == null) {
        ori = "";
      }
      byte[] byte1 = null;
      byte1 = ori.getBytes("ISO-8859-1");
      newstr = new String(byte1, "EUC_CN");
      return newstr;
    } catch (Exception e) {
      e.printStackTrace();
    }return newstr;
  }

  public static String isotoutf8(String ori)
  {
    String newstr = "";
    try {
      if (ori == null) {
        ori = "";
      }
      byte[] byte1 = null;
      byte1 = ori.getBytes("ISO-8859-1");
      newstr = new String(byte1, "utf-8");
      return newstr;
    } catch (Exception e) {
      e.printStackTrace();
    }return newstr;
  }

  public static String gbtoiso(String ori)
  {
    String newstr = "";
    try {
      if (ori == null) {
        ori = "";
      }
      byte[] byte1 = null;
      byte1 = ori.getBytes("EUC_CN");
      newstr = new String(byte1, "ISO-8859-1");

      return newstr;
    } catch (Exception e) {
      e.printStackTrace();
    }return newstr;
  }

  public static String filtertag(String src)
  {
    if (src == null) {
      return "";
    }
    String result = "";
    result = insteadstr(src, "&", "&amp;");
    result = insteadstr(result, "<", "&lt;");
    result = insteadstr(result, ">", "&gt;");
    result = insteadstr(result, "\n", "<BR>");
    result = insteadstr(result, "\"", "&quot;");
    return result;
  }

  public static String getcurrdate()
  {
    Date currDate = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    String strdate = sdf.format(currDate);
    return strdate;
  }

  public static String getcurrdate(String style)
  {
    Date currDate = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat(style);

    String strdate = sdf.format(currDate);
    return strdate;
  }

  public static Timestamp getTimestamp(String st)
  {
    String[] dataArr = st.split("-");
    int year = Integer.parseInt(dataArr[0]);
    int month = Integer.parseInt(dataArr[1]) - 1;
    int day = Integer.parseInt(dataArr[2]);
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month, day);

    long millis = calendar.getTime().getTime();

    Timestamp ts = new Timestamp(millis);

    return ts;
  }

  public static Timestamp getTimestamp()
  {
    Calendar calendar = Calendar.getInstance();
    int YEAR = calendar.get(1);
    int MONTH = calendar.get(2);
    int DAY = calendar.get(5);
    calendar.set(YEAR, MONTH, DAY);

    long millis = calendar.getTime().getTime();

    Timestamp ts = new Timestamp(millis);

    return ts;
  }

  public static Timestamp getTimestamp(int year, int month, int day)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month, day);

    long millis = calendar.getTime().getTime();

    Timestamp ts = new Timestamp(millis);

    return ts;
  }

  public static String getLongDate()
  {
    String str = new Date().getTime() + "";
    return str;
  }

  public static String insteadstr(String inString, String oldPattern, String newPattern)
  {
    if (inString == null) {
      return null;
    }
    if ((oldPattern == null) || (newPattern == null)) {
      return inString;
    }

    StringBuffer sbuf = new StringBuffer();
    int pos = 0;
    int index = inString.indexOf(oldPattern);
    int patLen = oldPattern.length();
    while (index >= 0) {
      sbuf.append(inString.substring(pos, index));
      sbuf.append(newPattern);
      pos = index + patLen;
      index = inString.indexOf(oldPattern, pos);
    }
    sbuf.append(inString.substring(pos));
    return sbuf.toString();
  }

  public static String insteadstrRecursion(String src, String sub, String subvalue)
  {
    String tmpsrc = src;
    if (tmpsrc.indexOf(sub) == -1) {
      return tmpsrc;
    }
    StringBuffer sb1 = new StringBuffer(src.substring(0, tmpsrc.indexOf(sub)));
    sb1.append(subvalue);
    String tmpsrc2 = src.substring(tmpsrc.indexOf(sub) + sub.length(), tmpsrc.length());
    return insteadstr(tmpsrc2, sub, subvalue);
  }

  public static String computepass(String oripass)
  {
    String key = "01234567890123456789";
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < oripass.length(); ++i) {
      char tt = (char)(oripass.charAt(i) ^ key.charAt(i));
      sb.append(tt);
    }
    return sb.toString();
  }

  public static Number parseNumber(String text, Class targetClass)
  {
    String trimmed = text.trim();

    if (targetClass.equals(Byte.class))
      return Byte.decode(trimmed);
    if (targetClass.equals(Short.class))
      return Short.decode(trimmed);
    if (targetClass.equals(Integer.class))
      return Integer.decode(trimmed);
    if (targetClass.equals(Long.class))
      return Long.decode(trimmed);
    if (targetClass.equals(BigInteger.class))
      return decodeBigInteger(trimmed);
    if (targetClass.equals(Float.class))
      return Float.valueOf(trimmed);
    if (targetClass.equals(Double.class))
      return Double.valueOf(trimmed);
    if ((targetClass.equals(BigDecimal.class)) || (targetClass.equals(Number.class))) {
      return new BigDecimal(trimmed);
    }
    throw new IllegalArgumentException("无法转换字符串  [" + text + "] 到目标类型 [" + targetClass.getName() + "]");
  }

  private static BigInteger decodeBigInteger(String value)
  {
    int radix = 10;
    int index = 0;
    boolean negative = false;

    if (value.startsWith("-")) {
      negative = true;
      ++index;
    }

    if ((value.startsWith("0x", index)) || (value.startsWith("0X", index))) {
      index += 2;
      radix = 16;
    } else if (value.startsWith("#", index)) {
      ++index;
      radix = 16;
    } else if ((value.startsWith("0", index)) && (value.length() > 1 + index)) {
      ++index;
      radix = 8;
    }

    BigInteger result = new BigInteger(value.substring(index), radix);
    return (negative) ? result.negate() : result;
  }

  public static String moneyLowToUpper(double param)
  {
    String[] Dw = { "分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾", "佰", "仟" };

    String[] Num = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
    String str = (int)(param * 100.0D) + "";
    int length = str.length();

    char[] c = new char[length];
    int[] character_value = new int[length];
    String depart = "";

    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < length; ++i) {
      c[i] = str.charAt(i);
      character_value[i] = Integer.parseInt(c[i] + "");
    }

    for (int i = 0; i < length; ++i)
    {
      if (character_value[i] == 0) {
        if (i == length - 1)
          depart = "";
        else if (i == length - 2)
        {
          if (Integer.parseInt(c[(length - 1)] + "") == 0)
            depart = "整";
          else
            depart = Num[character_value[i]];
        }
        else if (i == length - 3)
        {
          depart = Dw[(length - 1 - i)];
        }
        else if ((i == length - 7) || (i == length - 11)) {
          depart = Dw[(length - 1 - i)] + Num[character_value[i]];
        }
        else if (Integer.parseInt(c[(i - 1)] + "") == 0) {
          depart = "";
        }
        else if (i == length - 4) {
          if (Integer.parseInt(c[(length - 3)] + "") == 0)
            depart = "";
          else
            depart = Num[character_value[i]];
        }
        else {
          depart = Num[character_value[i]];
        }

      }
      else
      {
        depart = Num[character_value[i]] + Dw[(length - 1 - i)];
      }

      sb.append(depart);
    }
    return sb.toString();
  }

  public static String trim(String inString, String pattern)
  {
    return insteadstr(inString, pattern, "");
  }

  public static String num2show(double n)
  {
    String s = "";
    if (n == 0.0D) {
      return "&nbsp;";
    }
    s = DecimalFormat.getInstance().format(n);

    return strFill(s);
  }

  public static String num2show(float n)
  {
    String s = "";
    if (n == 0.0F) {
      return "&nbsp;";
    }
    s = DecimalFormat.getInstance().format(n);

    return strFill(s);
  }

  public static String num2show(long n)
  {
    String s = "";
    if (n == 0L)
      return "&nbsp;";
    s = DecimalFormat.getInstance().format(n);
    return s;
  }

  public static String num2show(int n)
  {
    String s = "";
    if (n == 0)
      return "&nbsp;";
    s = DecimalFormat.getInstance().format(n);
    return s;
  }

  public static double round(double f, int n)
  {
    int r = 1;
    for (int i = 1; i <= Math.abs(n); ++i)
      r *= 10;
    if (n >= 0) {
      return Math.round(f * r) / r;
    }
    return Math.round(f / r) * r;
  }

  public static double floor(double f, int n)
  {
    int r = 1;
    for (int i = 1; i <= Math.abs(n); ++i)
      r *= 10;
    if (n >= 0) {
      return Math.floor(f * r) / r;
    }
    return Math.floor(f / r) * r;
  }

  public static double ceil(double f, int n)
  {
    int r = 1;
    for (int i = 1; i <= Math.abs(n); ++i)
      r *= 10;
    if (n >= 0) {
      return Math.ceil(f * r) / r;
    }
    return Math.ceil(f / r) * r;
  }

  public static float round(float f, int n)
  {
    int r = 1;
    for (int i = 1; i <= Math.abs(n); ++i)
      r *= 10;
    if (n >= 0) {
      return Math.round(f * r) / r;
    }
    return Math.round(f / r) * r;
  }

  public static String strFill(String s)
  {
    if ((s == null) || (s.length() == 0))
      return s;
    int index = s.indexOf(".");
    String s1 = "";
    if (index > 0) {
      s1 = s.substring(index);
      if (s1.length() == 1)
        s = s + "00";
      else if (s1.length() == 2)
        s = s + "0";
    }
    else {
      s = s + ".00";
    }
    return s;
  }

  public static String getStrFromProperties(String propertiesfileName, String id)
  {
    ResourceBundle errBundle = PropertyResourceBundle.getBundle(propertiesfileName, new Locale("ch", "CH"));

    String _str = "";
    try {
      _str = errBundle.getString(id);
    } catch (NullPointerException e) {
      _str = "";
    }
    if (_str == null)
      _str = "";
    try
    {
      _str = new String(_str.getBytes("ISO8859_1"), "GB2312");
    } catch (Exception e) {
      _str = "";
    }
    return _str;
  }

  public static void writeFile(String fileName, String str)
  {
    PrintStream out = null;
    try {
      File outFile = new File(fileName);
      out = new PrintStream(new BufferedOutputStream(new FileOutputStream(outFile)));
      out.println(str);
    } catch (Exception e) {
      e.printStackTrace();
    }
    out.close();
  }

  public static String readFile(String fileName)
  {
    String s = "";
    StringBuffer sb = new StringBuffer();
    File inFile = new File(fileName);
    BufferedReader in = null;
    try {
      in = new BufferedReader(new InputStreamReader(new FileInputStream(inFile), "EUC_CN"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      while ((s = in.readLine()) != null) {
        sb.append(s + "\n");
      }
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  public static String getFileTxt(String filename, String charset)
  {
    String str = "";
    File phTxt = new File(filename);
    if (phTxt.isFile()) {
      try {
        FileInputStream phFile = new FileInputStream(phTxt);
        if (phFile != null) {
          InputStreamReader phSr = new InputStreamReader(phFile, charset);
          if (phSr != null) {
            BufferedReader phReader = new BufferedReader(phSr);
            if (phReader != null) {
              String sbuf = "";
              while ((sbuf = phReader.readLine()) != null) {
                if (str.length() > 0)
                  str = str + "\r\n";
                str = str + sbuf;
              }
              phReader.close();
            }
            phSr.close();
          }
          phFile.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return str;
  }

  public static String[] vectorToArray(Vector vt)
  {
    String[] _arr = null;
    if ((vt != null) && (vt.size() != 0)) {
      _arr = new String[vt.size()];
      for (int i = 0; i < vt.size(); ++i) {
        _arr[i] = ((String)vt.elementAt(i));
      }
    }
    return _arr;
  }

  public static String madeNbsp(int count)
  {
    String str = "";
    for (int i = 0; i < count; ++i) {
      str = str + "&nbsp;";
    }
    return str;
  }

  public static String[] strSplit2(String str, char ch)
  {
    if ((str == null) || (str == ""))
      return null;
    Vector v = new Vector();
    String new_str = str.replace(ch, ' ');
    StringTokenizer st1 = new StringTokenizer(new_str);
    while (st1.hasMoreTokens()) {
      v.add(st1.nextToken());
    }
    String[] result = new String[v.size()];
    for (int i = 0; i < v.size(); ++i) {
      result[i] = ((String)v.elementAt(i));
    }

    return result;
  }

  public static String arrayToDelimitedString(Object[] arr, String delim)
  {
    if ((arr == null) || (arr.length == 0)) {
      return "";
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < arr.length; ++i) {
      if (i > 0) {
        sb.append(delim);
      }
      sb.append('\'');
      sb.append(arr[i]);
      sb.append('\'');
    }
    return sb.toString();
  }

  public static String arrayToStr(String[] array1, char split, char ch)
  {
    if ((array1 == null) || (array1.length < 0))
      return null;
    String string1 = "";
    int len = array1.length - 1;
    if (!"".equals(Character.valueOf(ch)))
      for (int i = 0; i < array1.length; ++i) {
        string1 = string1 + '\'';
        string1 = string1 + array1[i].toString();
        string1 = string1 + '\'';
        if (i != len)
          string1 = string1 + split;
      }
    else {
      for (int i = 0; i < array1.length; ++i) {
        string1 = string1 + array1[i].toString();
        if (i != len)
          string1 = string1 + split;
      }
    }
    return string1;
  }

  public static String arrayToStr(Object[] arr, char split)
  {
    if ((arr == null) || (arr.length == 0)) {
      return null;
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < arr.length; ++i) {
      if (i > 0) {
        sb.append(split);
      }
      sb.append(arr[i]);
    }

    return sb.toString();
  }

  public static String[] strSplit(String fieldsru, char tag)
  {
    char dot = tag;
    if ((fieldsru == null) || (fieldsru.equals("")))
      return null;
    String field;
    if (fieldsru.charAt(fieldsru.length() - 1) != dot)
      field = fieldsru + dot;
    else {
      field = fieldsru;
    }
    int num = 0;
    int field_len = field.length();
    for (int i = 0; i < field_len; ++i) {
      if (field.charAt(i) == dot) {
        ++num;
      }
    }

    int begin = 0;

    String[] returnStr = new String[num];
    for (int j = 0; j < num; ++j) {
      int end = field.indexOf(dot, begin);

      returnStr[j] = field.substring(begin, end);

      begin = end + 1;
    }
    return returnStr;
  }

  public static Hashtable StrSpilt(String fieldsru, String tag)
  {
    Hashtable returnarray = new Hashtable();
    char dot = tag.charAt(0);

    String field = fieldsru + dot;
    int num = 0;
    int field_len = field.length();
    for (int i = 0; i < field_len; ++i) {
      if (field.charAt(i) == dot) {
        ++num;
      }
    }
    int begin = 0;

    for (int j = 0; j < num; ++j)
    {
      int end = field.indexOf(dot, begin);
      returnarray.put(new Integer(j), field.substring(begin, end));
      begin = end + 1;
    }
    return returnarray;
  }

  public static String toFormat(String s, String i_format)
  {
    if (i_format == null) {
      return s;
    }
    if (i_format.equals("$NUM_CN")) {
      return toChineseNumber(s);
    }
    if (i_format.equals("$DEC_2")) {
      return toDecmalFormat(s, 2.0D);
    }
    if (i_format.equals("$DEC_4")) {
      return toDecmalFormat(s, 4.0D);
    }
    if (i_format.equals("$DEC_6")) {
      return toDecmalFormat(s, 6.0D);
    }
    if (i_format.equals("$HTML")) {
      return filtertag(s);
    }
    return s;
  }

  public static String PositiveIntegerToHanStr(String NumStr) {
    String[] HanDigiStr = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
    String[] HanDiviStr = { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };

    String RMBStr = "";
    boolean lastzero = false;
    boolean hasvalue = false;

    int len = NumStr.length();
    if (len > 15)
      return "数值过大!";
    for (int i = len - 1; i >= 0; --i) {
      if (NumStr.charAt(len - i - 1) == ' ')
        continue;
      int n = NumStr.charAt(len - i - 1) - '0';
      if ((n < 0) || (n > 9))
        return "输入含非数字字符!";
      if (n != 0) {
        if (lastzero) {
          RMBStr = RMBStr + HanDigiStr[0];
        }

        if ((n != 1) || (i % 4 != 1) || (i != len - 1))
          RMBStr = RMBStr + HanDigiStr[n];
        RMBStr = RMBStr + HanDiviStr[i];
        hasvalue = true;
      }
      else if ((i % 8 == 0) || ((i % 8 == 4) && (hasvalue))) {
        RMBStr = RMBStr + HanDiviStr[i];
      }
      if (i % 8 == 0)
        hasvalue = false;
      lastzero = (n == 0) && (i % 4 != 0);
    }

    if (RMBStr.length() == 0)
      return HanDigiStr[0];
    return RMBStr;
  }

  public static String toChineseNumber(String NumStr) {
    if (NumStr == null)
      return "";
    return toChineseNumber(Double.parseDouble(NumStr));
  }

  public static String toChineseNumber(double val) {
    String[] HanDigiStr = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

    String[] HanDiviStr = { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };

    String SignStr = "";
    String TailStr = "";

    if (val < 0.0D) {
      val = -val;
      SignStr = "负";
    }
    if ((val > 100000000000000.0D) || (val < -100000000000000.0D)) {
      return "数值位数过大!";
    }
    long temp = Math.round(val * 100.0D);
    long integer = temp / 100L;
    long fraction = temp % 100L;
    int jiao = (int)fraction / 10;
    int fen = (int)fraction % 10;
    if ((jiao == 0) && (fen == 0)) {
      TailStr = "整";
    } else {
      TailStr = HanDigiStr[jiao];
      if (jiao != 0)
        TailStr = TailStr + "角";
      if ((integer == 0L) && (jiao == 0))
        TailStr = "";
      if (fen != 0) {
        TailStr = TailStr + HanDigiStr[fen] + "分";
      }
    }
    if (integer < 1L)
      return SignStr + TailStr;
    return SignStr + PositiveIntegerToHanStr(String.valueOf(integer)) + "元" + TailStr;
  }

  public static String toDecmalFormat(String s, double fraction) {
    if (fraction < 0.0D)
      fraction = 2.0D;
    s = Double.toString(Math.round(Double.parseDouble(s) * Math.pow(10.0D, fraction)) / Math.pow(10.0D, fraction) + Math.pow(10.0D, -1.0D - fraction));

    return s.substring(0, s.length() - 1);
  }

  public static String ucs(String s)
  {
    try
    {
      return new String(s.getBytes(), "GB2312");
    } catch (Exception e) {
      e.printStackTrace();
    }return s;
  }

  public static void SendRedirect(String myurl, HttpServletRequest request, HttpServletResponse response)
  {
    RequestDispatcher rd = null;
    if ((myurl != null) && (myurl.trim().length() != 0))
      rd = request.getRequestDispatcher(myurl);
    try
    {
      rd.forward(request, response);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static Date StringToDate(String str)
  {
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    Date dt = null;
    try {
      dt = f.parse(str);
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    return dt;
  }

  public static String formartDate(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String strdate = sdf.format(date);
    return strdate;
  }

  public static String cry(String content, String key)
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < content.length(); ++i) {
      char tt = (char)(content.charAt(i) ^ key.charAt(i));
      sb.append(tt);
    }
    return sb.toString();
  }

  public static String getFromURL(HttpServletRequest request)
  {
    StringBuffer fromURL = request.getRequestURL();
    Map params = request.getParameterMap();
    Iterator iter;
    if ((params != null) && (params.size() > 0)) {
      fromURL.append("?");
      for (iter = params.keySet().iterator(); iter.hasNext(); ) {
        String key = (String)iter.next();
        String[] values = (String[])(String[])params.get(key);
        for (int i = 0; i < values.length; ++i) {
          fromURL.append(key).append("=").append(values[i]).append("&");
        }
      }
    }
    return fromURL.toString();
  }

  public static String getContenByURL(String url)
  {
    String content = null;
    if ((url != null) && (url.trim().length() > 0)) {
      try {
        URL myurl = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(myurl.openStream()));

        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null)
        {
          sb.append(line);
        }
        in.close();
        content = sb.toString();
      } catch (Exception ex1) {
        ex1.printStackTrace();
      }
    }
    return content;
  }

  public static String getValidPhone(String mobile)
  {
    String validPhone = "";
    if ((mobile != null) && (mobile.trim().length() > 0))
    {
      if (mobile.indexOf("+") != -1) {
        validPhone = mobile.substring(1);
      }
      if (mobile.substring(0, 2).indexOf("86") != -1) {
        validPhone = mobile.substring(2);
      }
      if ((mobile.substring(0, 2).indexOf("13") != -1) && (mobile.length() == 11)) {
        validPhone = mobile;
      }
    }
    return validPhone;
  }

  public static boolean copyFile(String inputFilepathName, String onputFilepathName)
    throws FileNotFoundException, IOException
  {
    boolean isSuc = false;
    File file_in = new File(inputFilepathName);
    File file_out = new File(onputFilepathName);
    if (copy(new BufferedInputStream(new FileInputStream(file_in)), new BufferedOutputStream(new FileOutputStream(file_out))) > 0)
    {
      isSuc = true;
    }
    return isSuc;
  }

  public static int copy(InputStream in, OutputStream out)
    throws IOException
  {
    try
    {
      int byteCount = 0;
      byte[] buffer = new byte[1024];
      int bytesRead = -1;
      while ((bytesRead = in.read(buffer)) != -1) {
        out.write(buffer, 0, bytesRead);
        byteCount += bytesRead;
      }
      out.flush();
      int i = byteCount;

      return i;
    }
    finally
    {
      try
      {
        in.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      try {
        out.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  public static String getSpId(String mobile) {
    String spid = "";
    if ((mobile == null) || (mobile.length() < 11))
      spid = "invalid mobile";
    else
      try {
        int third = Integer.parseInt(mobile.substring(2, 3));
        if (third < 5)
          spid = "unicom";
        else
          spid = "cmcc";
      }
      catch (Exception e) {
      }
    return spid;
  }

  public static void runCmd(String cmd)
  {
    try
    {
      Runtime.getRuntime().exec(cmd);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String encode(String str, String charset, String toCharset)
  {
    if (str != null) {
      try {
        if (charset != null)
          str = new String(str.getBytes(charset), toCharset);
        else
          str = new String(str.getBytes(), toCharset);
      } catch (UnsupportedEncodingException uee) {
        uee.printStackTrace();
      }
    }
    return str;
  }

  public static String deleteAny(String inString, String charsToDelete)
  {
    if ((inString == null) || (charsToDelete == null)) {
      return inString;
    }
    StringBuffer out = new StringBuffer();
    for (int i = 0; i < inString.length(); ++i) {
      char c = inString.charAt(i);
      if (charsToDelete.indexOf(c) == -1) {
        out.append(c);
      }
    }
    return out.toString();
  }

  public static String decimalFormat(BigDecimal decimal, int len1, int len2)
  {
    DecimalFormat decimalFormat = new DecimalFormat("##########0.00");
    if (len1 > 0) {
      decimalFormat.setMaximumIntegerDigits(len1);
    }
    if (len2 > 0) {
      decimalFormat.setMaximumFractionDigits(len2);
    }
    return decimalFormat.format(decimal);
  }

  public static String vigenere(String str, String key, int mode)
  {
    if ((str == null) || (key == null))
      return null;
    byte[] strBytes = str.getBytes();
    byte[] keyBytes = key.getBytes();
    int keyLength = keyBytes.length;
    byte[] result = new byte[strBytes.length];
    if (mode == ENCRYPT_MODE)
    {
      for (int i = 0; i < strBytes.length; ++i)
        result[i] = (byte)((strBytes[i] + keyBytes[(i % keyLength)]) % 128);
      return new String(result);
    }if (mode == DECRYPT_MODE) {
      for (int i = 0; i < strBytes.length; ++i) {
        int temp = strBytes[i] - keyBytes[(i % keyLength)];
        if (temp < 0)
          temp += 128;
        result[i] = (byte)(temp % 128);
      }
      return new String(result);
    }
    return null;
  }

  public static String vigenereChinese(String str, String key, int mode)
  {
    if ((str == null) || (key == null))
      return null;
    if (mode == ENCRYPT_MODE)
      return vigenere(toEncrypt16(str), key, mode);
    if (mode == DECRYPT_MODE) {
      return toStringHex(vigenere(str, key, mode));
    }
    return null;
  }

  public static BigDecimal vigenere(BigDecimal bd, String key, int mode)
  {
    if ((bd == null) || (key == null))
      return null;
    if (mode == ENCRYPT_MODE)
    {
      String str = bd.toPlainString();
      byte[] strBytes = str.getBytes();
      byte[] keyBytes = key.getBytes();
      int keyLength = keyBytes.length;
      byte[] result = new byte[strBytes.length];
      for (int i = 0; i < strBytes.length; ++i) {
        if ((strBytes[i] == 43) || (strBytes[i] == 45) || (strBytes[i] == 46))
        {
          result[i] = strBytes[i];
        }
        else
          result[i] = (byte)((strBytes[i] - 48 + keyBytes[(i % keyLength)] - 48) % 10 + 48);
      }
      String inDateBase = new String(result);
      if (!str.substring(0, 1).equals("-"))
        inDateBase = "1" + inDateBase;
      else if (str.substring(0, 1).equals("-"))
        inDateBase = "2" + inDateBase.substring(1);
      return new BigDecimal(inDateBase);
    }if (mode == DECRYPT_MODE) {
      String str = bd.toPlainString();
      if (str.substring(0, 1).equals("1"))
        str = str.substring(1);
      else {
        str = "-" + str.substring(1);
      }
      byte[] strBytes = str.getBytes();
      byte[] keyBytes = key.getBytes();
      int keyLength = keyBytes.length;
      byte[] result = new byte[strBytes.length];
      for (int i = 0; i < strBytes.length; ++i)
        if ((strBytes[i] == 43) || (strBytes[i] == 45) || (strBytes[i] == 46))
        {
          result[i] = strBytes[i];
        }
        else {
          int temp = strBytes[i] - keyBytes[(i % keyLength)];
          if (temp < 0)
            temp += 10;
          result[i] = (byte)(temp % 10 + 48);
        }
      return new BigDecimal(new String(result));
    }
    return null;
  }

  public Object getDecry(Object content, String empNo, int flag)
  {
    String key = comKeyStr;
    String bd = comKeyBd;
    if (flag == 1) {
      key = vigenere(empNo, comKeyStr, ENCRYPT_MODE);
      byte[] last = empNo.substring(empNo.length() - 1).getBytes();
      BigDecimal tempBd = new BigDecimal(last[0] + 0);
      bd = vigenere(tempBd, comKeyBd, ENCRYPT_MODE).toPlainString();
    }
    try {
      if ((content != null) && (content.getClass().equals(String.class))) {
        content = vigenere((String)content, key, DECRYPT_MODE);
      }
      if ((content != null) && (content.getClass().equals(BigDecimal.class))) {
        content = vigenere((BigDecimal)content, bd, DECRYPT_MODE);
      }
      return content;
    } catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public static String toStringHex(String s)
  {
    byte[] baKeyword = new byte[s.length() / 2];
    for (int i = 0; i < baKeyword.length; ++i)
      try {
        baKeyword[i] = (byte)(0xFF & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
      }
      catch (Exception e)
      {
      }
    s = new String(baKeyword);
    return s;
  }

  public static String toEncrypt16(String str)
  {
    String encrypt16 = "";
    byte[] bytes = str.getBytes();
    for (int i = 0; i < bytes.length; ++i) {
      String a = Integer.toHexString(bytes[i] & 0xFF);
      encrypt16 = encrypt16 + a;
    }
    return encrypt16;
  }

  public static String getUpKey(String empNo, int flag)
  {
    if ((flag == STRING) || (flag == CHINESE)) {
      if ((empNo == null) || (empNo.equals(""))) {
        return comKeyStr;
      }
      return vigenere(empNo, comKeyStr, ENCRYPT_MODE);
    }
    if ((empNo == null) || (empNo.equals(""))) {
      return comKeyBd;
    }
    byte[] last = empNo.substring(empNo.length() - 1).getBytes();
    BigDecimal tempBd = new BigDecimal(last[0] + 0);
    String str = tempBd.toPlainString();
    byte[] strBytes = str.getBytes();
    byte[] keyBytes = comKeyBd.getBytes();
    int keyLength = keyBytes.length;
    byte[] result = new byte[strBytes.length];
    for (int i = 0; i < strBytes.length; ++i) {
      result[i] = (byte)((strBytes[i] - 48 + keyBytes[(i % keyLength)] - 48) % 10 + 48);
    }
    return new String(result);
  }

  public static Float getWorkYearProfile(Date joinDate, Date endDate)
  {
    if ((joinDate == null) || (joinDate.after(endDate))) {
      return new Float(0.0D);
    }

    int year = DateUtil.getYear(endDate) - DateUtil.getYear(joinDate);
    int month = DateUtil.getMonth(endDate) - DateUtil.getMonth(joinDate);
    int day = DateUtil.getDay(endDate) - DateUtil.getDay(joinDate);
    if (day < 0) --month;
    if (month < 0) {
      month += 12;
      --year;
    }

    Float result = new Float(year);
    result = Float.valueOf(result.floatValue() + new Float(month / 100.0D).floatValue());
    return result;
  }

  public static Double getWorkYearSalary(Date joinDate, Date endDate, Integer joinYearCalc)
  {
    if ((joinDate.after(endDate)) || (joinYearCalc == null)) {
      return new Double(0.0D);
    }

    int year = DateUtil.getYear(endDate) - DateUtil.getYear(joinDate);
    int month = DateUtil.getMonth(endDate) - DateUtil.getMonth(joinDate);
    if (month < 0) {
      month += 12;
      --year;
    }

    if (DateUtil.getDay(joinDate) <= joinYearCalc.intValue()) {
      ++month;
      if (month == 12) {
        month = 0;
        ++year;
      }

    }

    Double result = new Double(year);
    result = Double.valueOf(result.doubleValue() + new Double(month / 100.0D).doubleValue());
    return result;
  }

  public static double getTaxbyIncome(double income, SysConfigVarDef sysConfigVarDef)
  {
    String taxRange = sysConfigVarDef.getTaxRange();
    String taxRate = sysConfigVarDef.getTaxRate();
    String[] taxRangeArray = taxRange.split(",");
    String[] taxRateArray = taxRate.split(",");

    double base = sysConfigVarDef.getTaxBase().doubleValue();
    double tax_income = round(income - base, 2);
    double[] tax_line = new double[taxRangeArray.length];
    double[] tax_rate = new double[taxRateArray.length];
    for (int i = 0; i < taxRangeArray.length; ++i) {
      tax_line[i] = Double.parseDouble(taxRangeArray[i]);
    }
    for (int i = 0; i < taxRateArray.length; ++i) {
      tax_rate[i] = Double.parseDouble(taxRateArray[i]);
    }

    int i_tax_level = 0;
    while ((i_tax_level < tax_line.length) && (tax_income > tax_line[i_tax_level])) {
      ++i_tax_level;
    }

    if (i_tax_level > 0) {
      double result = new Double(0.0D).doubleValue();
      for (int i = 1; i < i_tax_level; ++i) {
        result += (tax_line[i] - tax_line[(i - 1)]) * tax_rate[(i - 1)];
      }

      result += (tax_income - tax_line[(i_tax_level - 1)]) * tax_rate[(i_tax_level - 1)];

      return round(result, 2);
    }
    return new Double(0.0D).doubleValue();
  }

  public static double getTaxbyLWIncome(double income, Double base)
  {
    double tax_income = 0.0D;
    double tax_amount = 0.0D;
    if (base == null) {
      base = new Double(800.0D);
    }
    if (income <= 800.0D) {
      return 0.0D;
    }

    if (income <= 4000.0D)
      tax_income = income - base.doubleValue();
    else
      tax_income = income * 0.8D;
    tax_income = round(tax_income, 2);

    if (tax_income > 40000.0D)
      tax_amount = tax_income * 0.4D - 7000.0D;
    else if (tax_income > 20000.0D)
      tax_amount = tax_income * 0.3D - 2000.0D;
    else {
      tax_amount = tax_income * 0.2D;
    }

    return round(tax_amount, 2);
  }

  public static double getTaxbyYearEndReward(float monthSalary, float yearEndReward, SysConfigVarDef sysConfigVarDef)
  {
    Double base = sysConfigVarDef.getTaxBase();
    float mintax_fl = new Float(base.doubleValue()).floatValue();

    String taxRange = sysConfigVarDef.getTaxRange();
    String taxRate = sysConfigVarDef.getTaxRate();

    String[] taxRangeArray = taxRange.split(",");
    String[] taxRateArray = taxRate.split(",");
    float f_income = yearEndReward / 12.0F;
    float[] tax_line = new float[taxRangeArray.length];
    float[] tax_rate = new float[taxRateArray.length];
    for (int i = 0; i < taxRangeArray.length; ++i) {
      tax_line[i] = Float.parseFloat(taxRangeArray[i]);
    }
    for (int i = 0; i < taxRateArray.length; ++i) {
      tax_rate[i] = Float.parseFloat(taxRateArray[i]);
    }

    int i_tax_level = 0;
    while ((i_tax_level < tax_line.length) && (f_income >= tax_line[i_tax_level])) {
      ++i_tax_level;
    }

    if (i_tax_level > 0) {
      float result = 0.0F;
      for (int i = 1; i < i_tax_level; ++i) {
        result += (tax_line[i] - tax_line[(i - 1)]) * tax_rate[(i - 1)];
      }

      result = -result + (yearEndReward - ((monthSalary > mintax_fl) ? 0.0F : mintax_fl - monthSalary)) * tax_rate[(i_tax_level - 1)];

      return round(result, 2);
    }
    return 0.0D;
  }

  public static BigDecimal encryDecimal(String empNo, BigDecimal value)
  {
    if (value == null) value = new BigDecimal(0.0D);

    DecimalFormat df = new DecimalFormat("#.00");
    BigDecimal enctrypUse = new BigDecimal(df.format(value));

    String key = getUpKey(empNo, BIGDECIMAL);
    BigDecimal encrypto = vigenere(enctrypUse, key, ENCRYPT_MODE);
    return encrypto;
  }

  public static BigDecimal decryDecimal(String empNo, BigDecimal value)
  {
    if (value == null) return new BigDecimal(0.0D);

    String key = getUpKey(empNo, BIGDECIMAL);
    BigDecimal decrypto = vigenere(value, key, DECRYPT_MODE);
    return decrypto;
  }

  public static String getUUID()
  {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }
}