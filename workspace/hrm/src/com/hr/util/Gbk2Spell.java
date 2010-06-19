package com.hr.util;

public class Gbk2Spell {
    private static String _FromEncode_ = "GBK";
    private static String _ToEncode_ = "GBK";

    public static int compare(String str1, String str2) {
        int result = 0;
        String m_s1 = null;
        String m_s2 = null;
        try {
            m_s1 = new String(str1.getBytes(_FromEncode_), _ToEncode_);
            m_s2 = new String(str2.getBytes(_FromEncode_), _ToEncode_);
        } catch (Exception e) {
            return str1.compareTo(str2);
        }
        result = chineseCompareTo(m_s1, m_s2);
        return result;
    }

    public static int getCharCode(String s) {
        if ((s == null) && (s.equals("")))
            return -1;
        byte[] b = s.getBytes();
        int value = 0;
        for (int i = 0; (i < b.length) && (i <= 2); ++i) {
            value = value * 100 + b[i];
        }
        return value;
    }

    public static int chineseCompareTo(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int n = Math.min(len1, len2);
        for (int i = 0; i < n; ++i) {
            int s1_code = getCharCode(s1.charAt(i) + "");
            int s2_code = getCharCode(s2.charAt(i) + "");
            if (s1_code * s2_code < 0)
                return Math.min(s1_code, s2_code);
            if (s1_code != s2_code) {
                return s1_code - s2_code;
            }
        }
        return len1 - len2;
    }

    public static void main(String[] args) {
    }

    public static String getBeginCharacter(String res) {
        String a = res;
        String result = "";
        for (int i = 0; i < a.length(); ++i) {
            String current = a.substring(i, i + 1);
            if (compare(current, "啄1�7") < 0) {
                result = result + current;
            } else if ((compare(current, "啄1�7") >= 0) && (compare(current, "庄1�7") <= 0))
                if (compare(current, "匄1�7") >= 0) {
                    result = result + "z";
                } else if (compare(current, "厄1�7") >= 0) {
                    result = result + "y";
                } else if (compare(current, "昄1�7") >= 0) {
                    result = result + "x";
                } else if (compare(current, "挄1�7") >= 0) {
                    result = result + "w";
                } else if (compare(current, "塄1�7") >= 0) {
                    result = result + "t";
                } else if (compare(current, "撄1�7") >= 0) {
                    result = result + "s";
                } else if (compare(current, "焄1�7") >= 0) {
                    result = result + "r";
                } else if (compare(current, "朄1�7") >= 0) {
                    result = result + "q";
                } else if (compare(current, "啄1�7") >= 0) {
                    result = result + "p";
                } else if (compare(current, "哄1�7") >= 0) {
                    result = result + "o";
                } else if (compare(current, "拄1�7") >= 0) {
                    result = result + "n";
                } else if (compare(current, "妄1�7") >= 0) {
                    result = result + "m";
                } else if (compare(current, "垄1�7") >= 0) {
                    result = result + "l";
                } else if (compare(current, "善1�7") >= 0) {
                    result = result + "k";
                } else if (compare(current, "凄1�7") > 0) {
                    result = result + "j";
                } else if (compare(current, "哄1�7") >= 0) {
                    result = result + "h";
                } else if (compare(current, "噄1�7") >= 0) {
                    result = result + "g";
                } else if (compare(current, "叄1�7") >= 0) {
                    result = result + "f";
                } else if (compare(current, "蛄1�7") >= 0) {
                    result = result + "e";
                } else if (compare(current, "搄1�7") >= 0) {
                    result = result + "d";
                } else if (compare(current, "擄1�7") >= 0) {
                    result = result + "c";
                } else if (compare(current, "芄1�7") >= 0) {
                    result = result + "b";
                } else if (compare(current, "啄1�7") >= 0)
                    result = result + "a";
        }
        try {
            return result.toUpperCase();
        } catch (Exception e) {
        }
        return "";
    }

    public static String getFirstStr(String str) {
        char a = str.charAt(0);
        char[] aa = { a };

        String sss = new String(aa);
        if (Character.isDigit(aa[0])) {
            sss = "data";
        } else if (((a >= 'a') && (a <= 'z')) || ((a >= 'A') && (a <= 'Z')))
            sss = "character";
        else
            sss = getBeginCharacter(sss);
        return sss;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.Gbk2Spell JD-Core Version: 0.5.4
 */