package com.hr.util.output;

import com.hr.util.comparator.ListComparator;
import com.hr.util.reflect.ObjectProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ObjectListToListList {
    private List inputList;
    private List<FieldOperate> fieldList;
    private List<List<String>> statisticResult;
    private List<List> listList;
    private List<List<List>> statisticList;
    private List<List<Integer>> groupPlaceList;

    public ObjectListToListList() {
    }

    public ObjectListToListList(List inputList, List<FieldOperate> fieldList) {
        setInputList(inputList);
        setFieldList(fieldList);
    }

    public void transferGroupOrderStatistic() throws Exception {
        transferGroupOrder();
        statistic();
    }

    public List<List> transferGroupOrder() throws Exception {
        this.groupPlaceList = null;
        this.listList = null;
        this.statisticList = null;

        int inputNum = this.inputList.size();
        int fieldNum = this.fieldList.size();

        List interList = new ArrayList();
        for (int i = 0; i < this.inputList.size(); ++i) {
            interList.add(new ArrayList());
        }
        ObjectProperty objectProperty = new ObjectProperty();
        Class objClass = this.inputList.get(0).getClass();
        List groupField = new ArrayList();
        List orderField = new ArrayList();
        FormatToString format = new FormatToString();
        for (int i = 0; i < fieldNum; ++i) {
            objectProperty.initial(objClass, ((FieldOperate) this.fieldList.get(i)).getFieldName());
            format.setFormat(((FieldOperate) this.fieldList.get(i)).getDataType(),
                             ((FieldOperate) this.fieldList.get(i)).getFormat());
            for (int j = 0; j < this.inputList.size(); ++j) {
                ((List) interList.get(j)).add(format.format(objectProperty
                        .getProperty(this.inputList.get(j))));
            }
            if (Math.abs(((FieldOperate) this.fieldList.get(i)).getGroupOrder()) == 1)
                orderField.add(Integer.valueOf(i));
            else if (Math.abs(((FieldOperate) this.fieldList.get(i)).getGroupOrder()) == 2) {
                groupField.add(Integer.valueOf(i));
            }

        }

        int groupFieldNum = groupField.size();
        int orderFieldNum = orderField.size();
        List resultList = null;
        ListComparator listComparator = new ListComparator();

        int[][] groupArr = (int[][]) null;
        if (groupFieldNum == 0) {
            resultList = new ArrayList();
            resultList.add(interList);
        } else {
            groupArr = new int[groupFieldNum][2];
            for (int i = 0; i < groupFieldNum; ++i) {
                groupArr[i][0] = ((Integer) groupField.get(i)).intValue();
                groupArr[i][1] = ((FieldOperate) this.fieldList.get(groupArr[i][0]))
                        .getGroupOrder();
            }
            listComparator.setOrderArr(groupArr);

            resultList = new ArrayList();
            List signList = new ArrayList();
            signList.add(interList.get(0));
            List tmpList = new ArrayList();
            tmpList.add(interList.get(0));
            resultList.add(tmpList);
            
            for (int i = 1; i < inputNum; ++i) {
                List list1 = (List) interList.get(i);
                int groupNum = signList.size();
                int j = 0;
                for (; j < groupNum; ++j) {
                    int compareResult = listComparator.compare(list1, (List) signList.get(j));
                    if (compareResult == 0) {
                        ((List) resultList.get(j)).add(list1);
                        break;
                    }
                    if (compareResult < 0) {
                        signList.add(j, list1);
                        List tmpList1 = new ArrayList();
                        tmpList1.add(list1);
                        resultList.add(j, tmpList1);
                        break;
                    }
                }
                if (j == groupNum) {
                    signList.add(list1);
                    List tmpList1 = new ArrayList();
                    tmpList1.add(list1);
                    resultList.add(tmpList1);
                }
            }
        }

        int groupNum = resultList.size();

        if (orderFieldNum > 0) {
            int[][] orderArr = new int[orderFieldNum][2];
            for (int i = 0; i < orderFieldNum; ++i) {
                orderArr[i][0] = ((Integer) orderField.get(i)).intValue();
                orderArr[i][1] = ((FieldOperate) this.fieldList.get(orderArr[i][0]))
                        .getGroupOrder();
            }
            listComparator.setOrderArr(orderArr);

            for (int i = 0; i < groupNum; ++i) {
                List allListTmp = (List) resultList.get(i);
                Collections.sort((List) resultList.get(i), listComparator);
            }
        }

        this.listList = new ArrayList();
        this.groupPlaceList = new ArrayList();
        this.groupPlaceList.add(new ArrayList());
        for (int i = 0; i < groupNum; ++i) {
            ((List) this.groupPlaceList.get(0)).add(Integer.valueOf(this.listList.size()));
            this.listList.addAll((Collection) resultList.get(i));
        }

        if (groupFieldNum > 0) {
            for (int i = 1; i < groupFieldNum; ++i) {
                this.groupPlaceList.add(new ArrayList());
                int[][] groupArrNew = new int[groupFieldNum - i][2];
                for (int j = 0; j < groupArrNew.length; ++j) {
                    groupArrNew[j][0] = groupArr[j][0];
                    groupArrNew[j][1] = groupArr[j][1];
                }
                listComparator.setOrderArr(groupArrNew);
                int sizeI = ((List) this.groupPlaceList.get(i - 1)).size();
                ((List) this.groupPlaceList.get(i)).add(Integer.valueOf(0));
                for (int j = 1; j < sizeI; ++j) {
                    int indexBgn = ((Integer) ((List) this.groupPlaceList.get(i - 1)).get(j - 1))
                            .intValue();
                    int indexEnd = ((Integer) ((List) this.groupPlaceList.get(i - 1)).get(j))
                            .intValue();
                    if (listComparator.compare((List) this.listList.get(indexBgn),
                                               (List) this.listList.get(indexEnd)) != 0) {
                        ((List) this.groupPlaceList.get(i)).add(Integer.valueOf(indexEnd));
                    }
                }
            }
            this.groupPlaceList.add(new ArrayList());
            ((List) this.groupPlaceList.get(groupFieldNum)).add(Integer.valueOf(0));
        }
        for (int i = 0; i < groupFieldNum + 1; ++i) {
            ((List) this.groupPlaceList.get(i)).add(Integer.valueOf(inputNum));
        }

        return this.listList;
    }

    public List<List<List>> statistic() throws Exception {
        this.statisticList = new ArrayList();
        Statistic statistic = new Statistic(this.listList, this.groupPlaceList);
        for (int i = 0; i < this.fieldList.size(); ++i) {
            FieldOperate tmpField = (FieldOperate) this.fieldList.get(i);
            List resultTmp = statistic.statistic(tmpField.getStatisticMethod(), i);
            this.statisticList.add(resultTmp);
        }
        return this.statisticList;
    }

    public String[][] getListListToString() throws Exception {
        int fieldNum = this.fieldList.size();
        int listNum = this.listList.size();
        String[][] result = new String[listNum][fieldNum];
        FormatToString format = new FormatToString();
        for (int i = 0; i < fieldNum; ++i) {
            format.setFormat(((FieldOperate) this.fieldList.get(i)).getDataType(),
                             ((FieldOperate) this.fieldList.get(i)).getFormat());
            for (int j = 0; j < listNum; ++j) {
                result[j][i] = format.toStringAfterFormat(((List) this.listList.get(j)).get(i));
            }
        }
        return result;
    }

    public List<List<List<String>>> getStatisticListToString() throws Exception {
        int fieldNum = this.fieldList.size();
        List result = new ArrayList();
        FormatToString format = new FormatToString();
        List nullStr = new ArrayList();
        for (int i = 0; i < this.groupPlaceList.size(); ++i) {
            nullStr.add(new ArrayList());
            for (int j = 1; j < ((List) this.groupPlaceList.get(i)).size(); ++j) {
                ((List) nullStr.get(i)).add("");
            }
        }
        for (int i = 0; i < fieldNum; ++i) {
            List fieldListTmp = (List) this.statisticList.get(i);
            if (fieldListTmp == null) {
                result.add(nullStr);
            } else {
                List resultField = new ArrayList();
                format.setFormat(((FieldOperate) this.fieldList.get(i)).getStatisticDataType(),
                                 ((FieldOperate) this.fieldList.get(i)).getStatisticFormat());
                for (int j = 0; j < fieldListTmp.size(); ++j) {
                    List resultElement = new ArrayList();
                    for (int k = 0; k < ((List) fieldListTmp.get(j)).size(); ++k) {
                        resultElement.add(format.toStringAfterFormat(((List) fieldListTmp.get(j))
                                .get(k)));
                    }
                    resultField.add(resultElement);
                }
                result.add(resultField);
            }
        }
        return result;
    }

    public void setFieldList(List<FieldOperate> fieldList) {
        this.fieldList = fieldList;
    }

    public void setInputList(List inputList) {
        this.inputList = inputList;
    }

    public List getInputList() {
        return this.inputList;
    }

    public List<FieldOperate> getFieldList() {
        return this.fieldList;
    }

    public List<List<String>> getStatisticResult() {
        return this.statisticResult;
    }

    public List<List> getListList() {
        return this.listList;
    }

    public List<List<List>> getStatisticList() {
        return this.statisticList;
    }

    public List<List<Integer>> getGroupPlaceList() {
        return this.groupPlaceList;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.output.ObjectListToListList JD-Core Version: 0.5.4
 */