package com.hr.exportfile;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class XlsBaseExport extends ExportFileAbstract {
    private WritableWorkbook writableWorkbook;

    public XlsBaseExport(String filePath) {
        super(filePath);
    }

    public void creatFile(String fileExtendsType, List<String>[] sheetList) throws Exception {
        super.creatFile("xls", new List[0]);

        if (this.writableWorkbook == null) {
            this.writableWorkbook = Workbook.createWorkbook(this.fileOutputStream);
        }
        for (int i = 0; i < sheetList[0].size(); ++i)
            this.writableWorkbook.createSheet((String) sheetList[0].get(i), i);
    }

    public void insertTitle(String sheetName, List<ExportContainer> titleList) throws Exception {
        WritableSheet writableSheet = this.writableWorkbook.getSheet(sheetName);
        if (writableSheet == null) {
            System.out.println("不存在的sheet名称" + sheetName);
            return;
        }
        for (int i = 0; i < titleList.size(); ++i)
            writableSheet.addCell(new Label(i, 0, ((ExportContainer) titleList.get(i))
                    .getContentValue(), ((ExportContainer) titleList.get(i)).getCellFormat()));
    }

    public void insertContent(String sheetName, List<ExportContainer> contentList) throws Exception {
        WritableSheet writableSheet = this.writableWorkbook.getSheet(sheetName);
        if (writableSheet == null) {
            System.out.println("不存在的sheet名称" + sheetName);
            return;
        }
        int insertPlace = writableSheet.getRows();
        for (int i = 0; i < contentList.size(); ++i)
            writableSheet.addCell(new Label(i, insertPlace, ((ExportContainer) contentList.get(i))
                    .getContentValue(), ((ExportContainer) contentList.get(i)).getCellFormat()));
    }

    public String closeExportFile() throws Exception {
        this.writableWorkbook.write();
        this.fileOutputStream.flush();
        this.writableWorkbook.close();
        this.fileOutputStream.close();
        return this.filePath;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.exportfile.XlsBaseExport JD-Core Version: 0.5.4
 */