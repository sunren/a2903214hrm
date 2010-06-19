package com.hr.io.extend;

import com.hr.io.domain.Inmatch;
import com.hr.io.domain.InmatchBasic;
import com.hr.util.input.StringToObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

public class ReadExcelByImList extends IReadFileByImList {
    private Workbook workbook;

    public ReadExcelByImList(File inputFile, List<Inmatch> imList, int hasTitle,
            IOMessages ioMessages) {
        super(inputFile, imList, hasTitle, ioMessages);
    }

    public List<List> getContent() throws Exception {
        openFile();
        List result = readContent();
        closeFile();
        return result;
    }

    protected List<List> readContent() {
        Sheet sheet = this.workbook.getSheet(0);

        int colNum = sheet.getColumns();

        int rowNum = sheet.getRows();

        if ((colNum == 0) || (rowNum == 0)) {
            this.ioMessages.addMessage(new IOMessageSingle("文件内容为空", Integer.valueOf(-1),
                    new Integer[0]));
            return null;
        }

        if (this.hasTitle == 0) {
            if (colNum != this.imList.size()) {
                this.ioMessages.addMessage(new IOMessageSingle("导入文件必须和选择的模板列数一致", Integer
                        .valueOf(-1), new Integer[0]));
                return null;
            }

            int i = 0;
            for (Inmatch imTmp : this.imList)
                imTmp.setImSortId(Integer.valueOf(i++));
        } else {
            Map titleInmatchNumMap = new HashMap();
            for (Inmatch imTmp : this.imList) {
                imTmp.setImSortId(null);
                titleInmatchNumMap.put(imTmp.getImFieldDesc(), imTmp);
            }

            for (int i = colNum - 1; i >= 0; --i) {
                String title = sheet.getCell(i, 0).getContents().trim();
                if (titleInmatchNumMap.containsKey(title)) {
                    ((Inmatch) titleInmatchNumMap.get(title)).setImSortId(Integer.valueOf(i));
                    titleInmatchNumMap.remove(title);
                } else {
                    title = title.split("-")[0].trim();
                    if (titleInmatchNumMap.containsKey(title)) {
                        ((Inmatch) titleInmatchNumMap.get(title)).setImSortId(Integer.valueOf(i));
                        titleInmatchNumMap.remove(title);
                    }
                }
            }

            for (int i = this.imList.size() - 1; i >= 0; --i) {
                Inmatch inmatch = (Inmatch) this.imList.get(i);
                if (inmatch.getImSortId() == null) {
                    if (inmatch.getImRequired().intValue() == 1) {
                        this.ioMessages.addMessage(new IOMessageSingle("导入文件必须含有"
                                + inmatch.getImFieldDesc(), Integer.valueOf(-1), new Integer[0]));
                    }
                    this.imList.remove(i);
                }
            }
        }

        if (this.ioMessages.hasErrorMsg()) {
            return null;
        }

        StringToObject stringToObject = new StringToObject();
        List result = new ArrayList();

        for (Inmatch imTmp : this.imList) {
            List colList = new ArrayList();

            stringToObject.setFormat(imTmp.getImImb().getImbDataType(), imTmp.getImFormat(),
                                     new int[] { imTmp.getImDetectError().intValue() });

            for (int i = this.hasTitle; i < rowNum; ++i) {
                Cell cell = sheet.getCell(imTmp.getImSortId().intValue(), i);
                String strTmp = cell.getContents().trim();
                if (strTmp.length() < 1) {
                    if (imTmp.getImRequired().intValue() == 1)
                        this.ioMessages.addMessage(new IOMessageSingle(imTmp.getImFieldDesc()
                                + "必须填写", Integer.valueOf(-1), new Integer[] {
                                Integer.valueOf(i + 1), imTmp.getImSortId() }));
                    else {
                        colList.add(null);
                    }
                } else {
                    boolean judgeError = true;
                    Object objTmp = null;
                    if (stringToObject.getDataType().equalsIgnoreCase("date")) {
                        if (cell.getType() == CellType.DATE) {
                            objTmp = new Date(((DateCell) cell).getDate().getTime());
                            judgeError = false;
                        } else {
                            objTmp = (Date) stringToObject.toObject(strTmp);
                        }
                    } else {
                        objTmp = stringToObject.toObject(strTmp);
                    }
                    if ((objTmp == null) && (imTmp.getImRequired().intValue() == 1))
                        this.ioMessages.addMessage(new IOMessageSingle(imTmp.getImFieldDesc()
                                + "格式不正确", Integer.valueOf(-1), new Integer[] {
                                Integer.valueOf(i + 1), imTmp.getImSortId() }));
                    else {
                        colList.add(objTmp);
                    }

                    if ((judgeError) && (stringToObject.getResultType() < 0)) {
                        this.ioMessages.addMessage(new IOMessageSingle(imTmp.getImFieldDesc()
                                + "格式不正确", Integer.valueOf(-1), new Integer[] {
                                Integer.valueOf(i + 1), imTmp.getImSortId() }));
                    } else if ((judgeError) && (stringToObject.getResultType() > 0)) {
                        System.out.println("=====" + cell.getContents());
                        this.ioMessages.addMessage(new IOMessageSingle(imTmp.getImFieldDesc()
                                + "格式不正确，已经忽略或被强制转换", Integer.valueOf(1), new Integer[] {
                                Integer.valueOf(i + 1), imTmp.getImSortId() }));
                    }
                }
            }
            result.add(colList);
        }

        return result;
    }

    protected void openFile() throws Exception {
        this.inputStream = new FileInputStream(this.inputFile);
        this.workbook = Workbook.getWorkbook(this.inputStream);
    }

    protected void closeFile() throws Exception {
        this.workbook.close();
        this.workbook = null;
        this.inputStream.close();
        this.inputStream = null;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.extend.ReadExcelByImList JD-Core Version: 0.5.4
 */