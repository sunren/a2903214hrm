package com.hr.report.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

public class XMLCreator {
    public boolean xmlWriter(String fileName, String className, String[] fieldName, Object[] listSet) {
        Document writeDoc = getCreatDocument(className, fieldName, listSet);
        if (writeDoc == null) {
            return false;
        }
        try {
            XMLWriter writer = new XMLWriter(new FileWriter(fileName));
            writer.write(writeDoc);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Document getCreatDocument(String className, String[] filedName, Object[] listSet) {
        if ((listSet == null) || (listSet.length == 0)) {
            return null;
        }
        for (int i = 0; i < listSet.length; ++i) {
            if ((!(listSet[i] instanceof List[])) && (!(listSet[i] instanceof List))) {
                return null;
            }
        }

        Document returnDocument = DocumentHelper.createDocument();
        try {
            Element dataSource = returnDocument.addElement("dataSource");
            for (int i = 0; i < listSet.length; ++i) {
                Element dataSet = dataSource.addElement("dataSet-" + i);
                if (listSet[i] instanceof List[]) {
                    List[] twoDimList = (List[]) (List[]) listSet[i];
                    getElementByTwoDim(className, filedName, twoDimList, dataSet);
                } else if (listSet[i] instanceof List) {
                    List oneDimList = (List) listSet[i];
                    getElmentByOneDim(className, filedName, oneDimList, dataSet);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return returnDocument;
    }

    private Element getElementByTwoDim(String className, String[] filedName, List<Object>[] list,
            Element rootElement) throws Exception {
        for (int i = 0; i < list.length; ++i) {
            Element group = rootElement.addElement("group");
            List tempList = list[i];
            getElmentByOneDim(className, filedName, tempList, group);
        }
        return rootElement;
    }

    private Element getElmentByOneDim(String className, String[] fieldName, List<Object> list,
            Element rootElement) throws Exception {
        for (int i = 0; i < list.size(); ++i) {
            Element row = rootElement.addElement("row");
            Object node = list.get(i);
            for (int j = 0; j < fieldName.length; ++j) {
                Field f = Class.forName(className).getDeclaredField(fieldName[j]);
                f.setAccessible(true);
                Element column = row.addElement("column-" + j);

                column.addAttribute("value", f.get(node).toString());
            }
        }
        return rootElement;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.report.utils.XMLCreator JD-Core Version: 0.5.4
 */