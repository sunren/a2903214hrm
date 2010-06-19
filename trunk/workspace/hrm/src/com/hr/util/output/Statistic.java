package com.hr.util.output;

import com.hr.util.comparator.ObjectComparator;
import com.hr.util.reflect.ObjectProperty;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Statistic {
    public static final String _SUM = "sum";
    public static final String _AVG = "avg";
    public static final String _MIN = "min";
    public static final String _MAX = "max";
    public static final String _COUNT = "count";
    public static final String _MODE = "mode";
    private List<List> listList;
    private List<List<Integer>> groupPlaceList;

    public Statistic() {
    }

    public Statistic(List<List> listList, List<List<Integer>> groupPlaceList) {
        initList(listList, groupPlaceList);
    }

    public void initList(List<List> listList, List<List<Integer>> groupPlaceList) {
        this.listList = listList;
        this.groupPlaceList = groupPlaceList;
    }

    public List<List> statistic(String statisticMethod, int index) throws Exception {
        if ((statisticMethod == null) || (statisticMethod.length() < 1)) {
            return null;
        }
        Class[] parameterClass = { List.class, Integer.TYPE, List.class };
        Method method = Statistic.class.getMethod(statisticMethod, parameterClass);
        Object[] parameter = { this.listList, Integer.valueOf(index), this.groupPlaceList };
        return (List) method.invoke(this, parameter);
    }

    public static List statistic(String statisticMethod, List<List> list, int index,
            List<List<Integer>> groupPlaceList) throws Exception {
        if ((statisticMethod == null) || (statisticMethod.length() < 1)) {
            return null;
        }
        if (statisticMethod.compareTo("avg") == 0)
            return avg(list, index, groupPlaceList);
        if (statisticMethod.compareTo("sum") == 0)
            return sum(list, index, groupPlaceList);
        if (statisticMethod.compareTo("min") == 0)
            return min(list, index, groupPlaceList);
        if (statisticMethod.compareTo("max") == 0)
            return max(list, index, groupPlaceList);
        if (statisticMethod.compareTo("count") == 0)
            return count(list, index, groupPlaceList);
        if (statisticMethod.compareTo("mode") == 0) {
            return mode(list, index, groupPlaceList);
        }
        return null;
    }

    public static List<List<Double>> sum(List<List> list, int index,
            List<List<Integer>> groupPlaceList) throws Exception {
        Method doubleMethod = null;
        for (int i = 0; i < list.size(); ++i) {
            Object obj = ((List) list.get(i)).get(index);
            if (obj != null) {
                doubleMethod = obj.getClass().getMethod("doubleValue", new Class[0]);
                break;
            }
        }
        if (doubleMethod == null) {
            return null;
        }
        int allNum = list.size();
        List result = new ArrayList();
        result.add(new ArrayList());
        for (int i = 1; i < ((List) groupPlaceList.get(0)).size(); ++i) {
            double sumTmp = 0.0D;
            for (int j = ((Integer) ((List) groupPlaceList.get(0)).get(i - 1)).intValue(); j < ((Integer) ((List) groupPlaceList
                    .get(0)).get(i)).intValue(); ++j) {
                sumTmp += ObjectProperty.getObjectDoublelValue(((List) list.get(j)).get(index),
                                                               doubleMethod);
            }
            ((List) result.get(0)).add(Double.valueOf(sumTmp));
        }
        for (int i = 1; i < groupPlaceList.size(); ++i) {
            result.add(new ArrayList());
            int k = 0;
            for (int j = 1; j < ((List) groupPlaceList.get(i)).size(); ++j) {
                double sumTmp = 0.0D;
                int place0 = ((Integer) ((List) groupPlaceList.get(i - 1)).get(k)).intValue();
                int place1 = ((Integer) ((List) groupPlaceList.get(i)).get(j)).intValue();
                while (place0 < place1) {
                    sumTmp += ((Double) ((List) result.get(i - 1)).get(k)).doubleValue();
                    place0 = ((Integer) ((List) groupPlaceList.get(i - 1)).get(++k)).intValue();
                }
                ((List) result.get(i)).add(Double.valueOf(sumTmp));
            }
        }
        return result;
    }

    public static List<List<Double>> avg(List<List> list, int index,
            List<List<Integer>> groupPlaceList) throws Exception {
        List result = sum(list, index, groupPlaceList);
        if (result == null) {
            return null;
        }
        for (int i = 0; i < groupPlaceList.size(); ++i) {
            for (int j = 1; j < ((List) groupPlaceList.get(i)).size(); ++j) {
                double avgTmp = ((Double) ((List) result.get(i)).get(j - 1)).doubleValue();
                avgTmp /= (((Integer) ((List) groupPlaceList.get(i)).get(j)).intValue() - ((Integer) ((List) groupPlaceList
                        .get(i)).get(j - 1)).intValue());
                ((List) result.get(i)).set(j - 1, Double.valueOf(avgTmp));
            }
        }
        return result;
    }

    public static List<List<Object>> minOrMax(List<List> list, int index,
            List<List<Integer>> groupPlaceList, boolean[] isMin) throws Exception {
        int order = 1;
        if ((isMin.length > 0) && isMin[0]) {
            order = -1;
        }
        ObjectComparator objComparator = new ObjectComparator(order, new int[0]);
        List result = new ArrayList();
        int allNum = list.size();
        result.add(new ArrayList());
        for (int i = 0; i < ((List) groupPlaceList.get(0)).size() - 1; ++i) {
            Object minOrMaxTmp = ((List) list.get(((Integer) ((List) groupPlaceList.get(0)).get(i))
                    .intValue())).get(index);
            for (int j = ((Integer) ((List) groupPlaceList.get(0)).get(i)).intValue() + 1; j < ((Integer) ((List) groupPlaceList
                    .get(0)).get(i + 1)).intValue(); ++j) {
                Object objTmp = ((List) list.get(j)).get(index);
                if (objComparator.compare(minOrMaxTmp, objTmp) > 0) {
                    minOrMaxTmp = objTmp;
                }
            }
            ((List) result.get(0)).add(minOrMaxTmp);
        }
        for (int i = 1; i < groupPlaceList.size(); ++i) {
            result.add(new ArrayList());
            int k = 0;
            for (int j = 1; j < ((List) groupPlaceList.get(i)).size(); ++j) {
                Object minOrMaxTmp = ((List) result.get(i - 1)).get(k);
                int place0 = ((Integer) ((List) groupPlaceList.get(i - 1)).get(k)).intValue();
                int place1 = ((Integer) ((List) groupPlaceList.get(i)).get(j)).intValue();
                while (place0 < place1) {
                    if (objComparator.compare(minOrMaxTmp, ((List) result.get(i - 1)).get(k)) > 0) {
                        minOrMaxTmp = ((List) result.get(i - 1)).get(k);
                    }
                    place0 = ((Integer) ((List) groupPlaceList.get(i - 1)).get(++k)).intValue();
                }
                ((List) result.get(i)).add(minOrMaxTmp);
            }
        }
        return result;
    }

    public static List<List<Object>> min(List<List> list, int index,
            List<List<Integer>> groupPlaceList) throws Exception {
        return minOrMax(list, index, groupPlaceList, new boolean[] { true });
    }

    public static List<List<Object>> max(List<List> list, int index,
            List<List<Integer>> groupPlaceList) throws Exception {
        return minOrMax(list, index, groupPlaceList, new boolean[0]);
    }

    public static List<List<Integer>> count(List<List> list, int index,
            List<List<Integer>> groupPlaceList) throws Exception {
        List statisticList = new ArrayList();
        statisticList.add(new ArrayList());
        for (int i = 0; i < ((List) groupPlaceList.get(0)).size() - 1; ++i) {
            Set setTmp = new HashSet();
            for (int j = ((Integer) ((List) groupPlaceList.get(0)).get(i)).intValue(); j < ((Integer) ((List) groupPlaceList
                    .get(0)).get(i + 1)).intValue(); ++j) {
                setTmp.add(((List) list.get(j)).get(index));
            }
            setTmp.remove(null);
            ((List) statisticList.get(0)).add(setTmp);
        }
        for (int i = 1; i < groupPlaceList.size(); ++i) {
            statisticList.add(new ArrayList());
            int k = 0;
            for (int j = 1; j < ((List) groupPlaceList.get(i)).size(); ++j) {
                Set setTmp = new HashSet();
                int place0 = ((Integer) ((List) groupPlaceList.get(i - 1)).get(k)).intValue();
                int place1 = ((Integer) ((List) groupPlaceList.get(i)).get(j)).intValue();
                while (place0 < place1) {
                    setTmp.addAll((Collection) ((List) statisticList.get(i - 1)).get(k));
                    place0 = ((Integer) ((List) groupPlaceList.get(i - 1)).get(++k)).intValue();
                }
                setTmp.remove(null);
                ((List) statisticList.get(i)).add(setTmp);
            }
        }
        List result = new ArrayList();
        for (int i = 0; i < statisticList.size(); ++i) {
            result.add(new ArrayList());
            for (int j = 0; j < ((List) statisticList.get(i)).size(); ++j) {
                ((List) result.get(i)).add(Integer.valueOf(((Set) ((List) statisticList.get(i))
                        .get(j)).size()));
            }
        }
        return result;
    }

    public static List<List<Object>> mode(List<List> list, int index,
            List<List<Integer>> groupPlaceList) throws Exception {
        List result = new ArrayList();
        result.add(new ArrayList());
        List statisticList = new ArrayList();
        statisticList.add(new ArrayList());
        for (int i = 0; i < ((List) groupPlaceList.get(0)).size() - 1; ++i) {
            Map mapTmp = new TreeMap();
            ((List) statisticList.get(0)).add(mapTmp);
            int countMaxTmp = 0;
            Object objMaxTmp = null;
            for (int j = ((Integer) ((List) groupPlaceList.get(0)).get(i)).intValue(); j < ((Integer) ((List) groupPlaceList
                    .get(0)).get(i + 1)).intValue(); ++j) {
                Object objTmp = ((List) list.get(j)).get(index);
                if (objTmp == null) {
                    continue;
                }
                int countTmp = 1;
                if (mapTmp.containsKey(objTmp)) {
                    countTmp += ((Integer) mapTmp.get(objTmp)).intValue();
                }
                mapTmp.put(objTmp, Integer.valueOf(countTmp));
                if (countTmp > countMaxTmp) {
                    countMaxTmp = countTmp;
                    objMaxTmp = objTmp;
                }
            }
            ((List) result.get(0)).add(objMaxTmp);
        }

        for (int i = 1; i < groupPlaceList.size(); ++i) {
            result.add(new ArrayList());
            statisticList.add(new ArrayList());
            int k = 0;
            for (int j = 1; j < ((List) groupPlaceList.get(i)).size(); ++j) {
                Map mapTmp = new TreeMap();
                ((List) statisticList.get(i)).add(mapTmp);
                Object objMaxTmp = null;
                int countMaxTmp = 0;
                int place0 = ((Integer) ((List) groupPlaceList.get(i - 1)).get(k)).intValue();
                int place1 = ((Integer) ((List) groupPlaceList.get(i)).get(j)).intValue();
                while (place0 < place1) {
                    for (Iterator i$ = ((Map) ((List) statisticList.get(i - 1)).get(k)).keySet()
                            .iterator(); i$.hasNext();) {
                        Object objTmp = i$.next();
                        int countTmp = ((Integer) ((Map) ((List) statisticList.get(i - 1)).get(k))
                                .get(objTmp)).intValue();
                        if (mapTmp.containsKey(objTmp)) {
                            countTmp += ((Integer) mapTmp.get(objTmp)).intValue();
                        }
                        mapTmp.put(objTmp, Integer.valueOf(countTmp));
                        if (countTmp > countMaxTmp) {
                            countMaxTmp = countTmp;
                            objMaxTmp = objTmp;
                        }
                    }

                    place0 = ((Integer) ((List) groupPlaceList.get(i - 1)).get(++k)).intValue();
                }
                ((List) result.get(i)).add(objMaxTmp);
            }
        }
        return result;
    }

    public List<List> getListList() {
        return this.listList;
    }

    public List<List<Integer>> getGroupPlaceList() {
        return this.groupPlaceList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.output.Statistic JD-Core Version: 0.5.4
 */