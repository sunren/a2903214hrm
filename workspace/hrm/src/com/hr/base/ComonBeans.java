package com.hr.base;

import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.SysConfigManager;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class ComonBeans {
    public static final String COMMON_BEAN_RESOURCE_FILE_NAME = "comombeans";
    private static final ResourceBundle COMMON_BEAN_RESOURCE_BUNDLE = PropertyResourceBundle
            .getBundle("comombeans");
    private static final String _SEPERATOR = "_";
    private static final Map<String, String> allProperties = getAllPropertiesFromResourceBundle();

    private static final Map<String, Map<String, String>> allPropertiesInGroup = new HashMap();
    public static final String _YESORNO = "yesOrNo";
    public static final String _NOORYES = "noOrYes";
    public static final String _YESORNOIMG = "yesOrNoImg";
    public static final String _ORDER_ASCORDESC = "orderAscOrDesc";
    public static final String _SELECT_NULL_OPTION = "selectNullOption";
    private static final String EmpPoliticsStarts = "EmpPolitic";
    private static final String EmpDiplomaStarts = "EmpDiploma";
    private static final String EmpSpecialityStarts = "EmpSpeciality";
    private static final String EmpCityNoStarts = "EmpCityNo";
    private static final String EmpNationStarts = "EmpNation";
    private static final String EmpSalaryRangeStarts = "EmpSalaryRange";
    private static final List<String> resultEmpPolitics = new ArrayList();

    private static final List<String> resultEmpDiploma = new ArrayList();

    private static final List<String> resultEmpSpeciality = new ArrayList();

    private static final List<String> resultEmpCityNo = new ArrayList();

    private static final List<String> resultEmpNation = new ArrayList();

    private static final List<String> empSalaryRange = new ArrayList();

    public static Map<String, String> getAllProperties() {
        return allProperties;
    }

    public static Map<String, String> getAllPropertiesFromResourceBundle() {
        Map allProperties = new HashMap();
        Enumeration keys = COMMON_BEAN_RESOURCE_BUNDLE.getKeys();
        while (keys.hasMoreElements()) {
            String keyTmp = (String) keys.nextElement();
            COMMON_BEAN_RESOURCE_BUNDLE.getString(keyTmp);
            allProperties.put(keyTmp, COMMON_BEAN_RESOURCE_BUNDLE.getString(keyTmp));
        }
        return allProperties;
    }

    public static Map<String, String> getValuesToMap(String key, boolean[] addNullOption) {
        Map<String, String> resultTmp;
        if (allPropertiesInGroup.containsKey(key)) {
            resultTmp = (Map) allPropertiesInGroup.get(key);
        } else {
            int len = key.length() + 1;
            resultTmp = new TreeMap();
            for (String keyTmp : allProperties.keySet()) {
                if (keyTmp.indexOf(key + "_") == 0) {
                    resultTmp.put(keyTmp.substring(len), allProperties.get(keyTmp));
                }
            }

            if (resultTmp.size() > 0) {
                allPropertiesInGroup.put(key, resultTmp);
            }
        }

        Map result = new TreeMap();
        for (String keyTmp : resultTmp.keySet()) {
            result.put(keyTmp, resultTmp.get(keyTmp));
        }

        if ((addNullOption.length > 0) && addNullOption[0]) {
            result.put("", allProperties.get("selectNullOption"));
        }
        return result;
    }

    public static Map<String, String> getValuesToMap(String key, String[] index,
            boolean[] addNullOption) {
        Map keyMap = getValuesToMap(key, new boolean[0]);
        Map result = new TreeMap();
        for (int i = 0; i < index.length; ++i) {
            result.put(index[i], keyMap.get(index[i]));
        }
        if ((addNullOption.length > 0) && addNullOption[0]) {
            result.put("", allProperties.get("selectNullOption"));
        }
        return result;
    }

    public static Map<String, String> getValuesToMap(String key, Integer[] index,
            boolean[] addNullOption) {
        String[] indexStr = new String[index.length];
        for (int i = 0; i < index.length; ++i) {
            indexStr[i] = (index[i] + "");
        }
        return getValuesToMap(key, indexStr, addNullOption);
    }

    public static Map<String, String> getValuesToMapInverse(String key) {
        Map<String, String> keyMap = getValuesToMap(key, new boolean[0]);
        Map result = new TreeMap();
        for (String keyTmp : keyMap.keySet()) {
            result.put(keyMap.get(keyTmp), keyTmp);
        }
        return result;
    }

    public static String getParameterValue(String key, String[] index) {
        String keyInMap = key;
        for (int i = 0; i < index.length; ++i) {
            keyInMap = keyInMap + "_" + index[i];
        }
        return (String) allProperties.get(keyInMap);
    }

    public static Map<String, Map<String, String>> getSecondLevelSelect(Map<String, String> topMap,
            String keySub, boolean[] addNullOption) {
        Map<String, Map<String, String>>  result = new TreeMap();

        Map<String, String> subMap = getValuesToMap(keySub, new boolean[0]);

        String defaultKey = "";

        for (String keyTmp : topMap.keySet()) {
            result.put(keyTmp, new TreeMap());
            if (keyTmp.compareTo(defaultKey) > 0) {
                defaultKey = keyTmp;
            }
        }

        int defaultKeyLength = defaultKey.length();
        for (String keyTmp : subMap.keySet()) {
            String keyTop = keyTmp.substring(0, defaultKeyLength);
            String valueTmp = (String) subMap.get(keyTmp);
            ((Map) result.get(keyTop)).put(keyTmp, valueTmp);
        }

        Map defaultMap = (Map) result.get(defaultKey);
        for (String keyTop : result.keySet()) {
            if ((keyTop.length() > 0) && (((Map) result.get(keyTop)).size() == 0)) {
                result.put(keyTop, defaultMap);
            }

        }

        if ((addNullOption.length > 0) && addNullOption[0]) {
            String nullValue = (String) allProperties.get("selectNullOption");
            for (Map mapTmp : result.values()) {
                if (defaultMap != mapTmp) {
                    mapTmp.put("", nullValue);
                }
            }
            defaultMap.put("", nullValue);
        }
        return result;
    }

    public static List<String> getEmpSalaryRange() {
        if (empSalaryRange.isEmpty()) {
            int index = 1;
            int temp = index;
            String currentContext = null;
            String currentContext2 = null;

            while ((currentContext = CommonBeanPropertyHelper.getParameterValue("EmpSalaryRange_"
                    + index)).length() > 0) {
                if (index == 1) {
                    empSalaryRange.add("<" + currentContext);
                } else {
                    temp = index - 1;
                    currentContext2 = CommonBeanPropertyHelper.getParameterValue("EmpSalaryRange_"
                            + temp);

                    empSalaryRange.add(currentContext2 + "-" + currentContext);
                }
                ++index;
            }

            empSalaryRange.add(">"
                    + CommonBeanPropertyHelper.getParameterValue(new StringBuilder()
                            .append("EmpSalaryRange_").append(index - 1).toString()));
        }

        return empSalaryRange;
    }

    public static List<String> getEmpPolitics() {
        if (resultEmpPolitics.isEmpty()) {
            int index = 1;
            String currentContext = null;

            while ((currentContext = CommonBeanPropertyHelper.getParameterValue("EmpPolitic_"
                    + index)).length() > 0) {
                resultEmpPolitics.add(currentContext);
                ++index;
            }
        }
        return resultEmpPolitics;
    }

    public static List<String> getEmpDiploma() {
        if (resultEmpDiploma.isEmpty()) {
            int index = 1;
            String currentContext = null;

            while ((currentContext = CommonBeanPropertyHelper.getParameterValue("EmpDiploma_"
                    + index)).length() > 0) {
                resultEmpDiploma.add(currentContext);
                ++index;
            }
        }
        return resultEmpDiploma;
    }

    public static List<String> getEmpSpeciality() {
        if (resultEmpSpeciality.isEmpty()) {
            int index = 1;
            String currentContext = null;

            while ((currentContext = CommonBeanPropertyHelper.getParameterValue("EmpSpeciality_"
                    + index)).length() > 0) {
                resultEmpSpeciality.add(currentContext);
                ++index;
            }
        }
        return resultEmpSpeciality;
    }

    public static List<String> getEmpCityNo() {
        if (resultEmpCityNo.isEmpty()) {
            int index = 1;
            String currentContext = null;

            while ((currentContext = CommonBeanPropertyHelper.getParameterValue("EmpCityNo_"
                    + index)).length() > 0) {
                resultEmpCityNo.add(currentContext);
                ++index;
            }
        }
        return resultEmpCityNo;
    }

    public static List<String> getEmpNation() {
        if (resultEmpNation.isEmpty()) {
            int index = 1;
            String currentContext = null;

            while ((currentContext = CommonBeanPropertyHelper.getParameterValue("EmpNation_"
                    + index)).length() > 0) {
                resultEmpNation.add(currentContext);
                ++index;
            }
        }
        return resultEmpNation;
    }

    public static String getShiftEnable() {
        SysConfigManager dbConfigManager = DatabaseSysConfigManager.getInstance();
        String mode = dbConfigManager.getProperty("sys.examin.shift.enable");

        return mode;
    }

    private static class CommonBeanPropertyHelper {
        public static final String COMMON_BEAN_RESOURCE_FILE_NAME = "comombeans";
        private static final ResourceBundle COMMON_BEAN_RESOURCE_BUNDLE = PropertyResourceBundle
                .getBundle("comombeans");

        public static String getParameterValue(String key) {
            String value = "";
            try {
                value = COMMON_BEAN_RESOURCE_BUNDLE.getString(key);
            } catch (MissingResourceException e) {
                value = "";
            } catch (NullPointerException e) {
                value = "";
            }

            return (value == null) ? "" : value.trim();
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.base.ComonBeans JD-Core Version: 0.5.4
 */