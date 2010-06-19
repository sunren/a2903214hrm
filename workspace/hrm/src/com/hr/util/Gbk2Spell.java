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
            if (compare(current, "啊") < 0) {
                result = result + current;
            } else if ((compare(current, "啊") >= 0) && (compare(current, "座") <= 0))
                if (compare(current, "匝") >= 0) {
                    result = result + "z";
                } else if (compare(current, "压") >= 0) {
                    result = result + "y";
                } else if (compare(current, "昔") >= 0) {
                    result = result + "x";
                } else if (compare(current, "挖") >= 0) {
                    result = result + "w";
                } else if (compare(current, "塌") >= 0) {
                    result = result + "t";
                } else if (compare(current, "撒") >= 0) {
                    result = result + "s";
                } else if (compare(current, "然") >= 0) {
                    result = result + "r";
                } else if (compare(current, "期") >= 0) {
                    result = result + "q";
                } else if (compare(current, "啪") >= 0) {
                    result = result + "p";
                } else if (compare(current, "哦") >= 0) {
                    result = result + "o";
                } else if (compare(current, "拿") >= 0) {
                    result = result + "n";
                } else if (compare(current, "妈") >= 0) {
                    result = result + "m";
                } else if (compare(current, "垃") >= 0) {
                    result = result + "l";
                } else if (compare(current, "喀") >= 0) {
                    result = result + "k";
                } else if (compare(current, "击") > 0) {
                    result = result + "j";
                } else if (compare(current, "哈") >= 0) {
                    result = result + "h";
                } else if (compare(current, "噶") >= 0) {
                    result = result + "g";
                } else if (compare(current, "发") >= 0) {
                    result = result + "f";
                } else if (compare(current, "蛾") >= 0) {
                    result = result + "e";
                } else if (compare(current, "搭") >= 0) {
                    result = result + "d";
                } else if (compare(current, "擦") >= 0) {
                    result = result + "c";
                } else if (compare(current, "芭") >= 0) {
                    result = result + "b";
                } else if (compare(current, "啊") >= 0)
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