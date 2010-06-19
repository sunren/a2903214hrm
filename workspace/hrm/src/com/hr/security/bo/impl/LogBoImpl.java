package com.hr.security.bo.impl;

import com.hr.base.FileOperate;
import com.hr.security.bo.LogBo;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.Pager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class LogBoImpl implements LogBo {
    public synchronized ArrayList getLogRecorders(String filePath, Pager pager) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File fileD = new File(filePath.substring(0, filePath.lastIndexOf("/")));
                fileD.mkdirs();
                clearLogRecorders(filePath);
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));
            Node node = document.getFirstChild();
            node = node.getFirstChild();
            int totalLength = Integer.parseInt(node.getTextContent());
            splitPagers(totalLength, pager);
            ArrayList list = new ArrayList();
            int pageCount = 0;
            int begain = totalLength - pager.getCurrentPage() * pager.getPageSize();
            begain = (begain > 0) ? begain : 0;
            int end = begain + pager.getPageSize();
            end = (end > totalLength) ? totalLength : end;
            while ((node = node.getNextSibling()) != null) {
                if (pageCount < begain) {
                    ++pageCount;
                }

                if (pageCount >= end)
                    break;
                if (node.getNodeName().equals("C"))
                    ;
                ++pageCount;
                list.add(node.getTextContent());
            }

            int listSize = list.size();
            if (listSize > 0) {
                ArrayList list2 = new ArrayList();
                for (int i = 0; i < listSize; ++i) {
                    list2.add(list.get(listSize - i - 1));
                }
                return list2;
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public synchronized boolean clearLogRecorders(String fileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElement("R");
            document.appendChild(root);
            Element title = document.createElement("T");
            title.appendChild(document.createTextNode("0"));
            root.appendChild(title);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);

            transformer.setOutputProperty("encoding", "Shift_JIS");
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized boolean updateXML(String fileName, String content) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                File fileD = new File(fileName.substring(0, fileName.lastIndexOf("/")));
                fileD.mkdirs();
                clearLogRecorders(fileName);
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(fileName));
            Node node = document.getFirstChild();
            Node titleNode = node.getFirstChild();
            int nodeLength = Integer.valueOf(titleNode.getTextContent()).intValue();
            titleNode.setTextContent(String.valueOf(nodeLength + 1));
            Element contentN = document.createElement("C");
            contentN.appendChild(document.createTextNode(content));
            node.appendChild(contentN);
            DOMSource doms = new DOMSource(document);
            File f = new File(fileName);
            StreamResult sr = new StreamResult(f);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.transform(doms, sr);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void splitPagers(int size, Pager pager) {
        int pageSize = Integer.valueOf(
                                       DatabaseSysConfigManager.getInstance()
                                               .getProperty("sys.split.pages")).intValue();

        pager.init(size, pageSize);

        if ((pager.getOperate() != null) && ("previous".equalsIgnoreCase(pager.getOperate()))) {
            pager.previous();
        }
        if ((pager.getOperate() != null) && ("next".equalsIgnoreCase(pager.getOperate()))) {
            pager.next();
        }
        if ((pager.getOperate() != null) && ("first".equalsIgnoreCase(pager.getOperate()))) {
            pager.first();
        }
        if ((pager.getOperate() != null) && ("last".equalsIgnoreCase(pager.getOperate())))
            pager.last();
    }

    public static void main(String[] args) {
        LogBoImpl lb = new LogBoImpl();
        lb.getLogRecorders(FileOperate.getFileHomePath() + "login_log/login.xml", new Pager());
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.bo.impl.LogBoImpl JD-Core Version: 0.5.4
 */