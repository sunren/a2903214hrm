package com.hr.util.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ReadNormalExcel extends IReadExcel {
    public ReadNormalExcel(File inputFile) {
        setInputFile(inputFile);
    }

    public List<String[]> readContent() throws Exception {
        Sheet sheet = this.workbook.getSheet(0);
        int colNum = sheet.getColumns();
        int rowNum = sheet.getRows();
        if ((colNum == 0) || (rowNum == 0)) {
            return null;
        }
        List result = new ArrayList();
        for (int i = 0; i < colNum; ++i) {
            String[] col = new String[rowNum];
            for (int j = 0; j < rowNum; ++j) {
                col[j] = sheet.getCell(i, j).getContents();
            }
            result.add(col);
        }
        return result;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.excel.ReadNormalExcel JD-Core Version: 0.5.4
 */