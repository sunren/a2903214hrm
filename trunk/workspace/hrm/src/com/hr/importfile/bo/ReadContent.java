package com.hr.importfile.bo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

public class ReadContent {
    public String[][] readRows(int hasTitle, File readFile, int rowNumber) {
        int index = readFile.getName().indexOf(".");
        String fileExtends = null;
        if (index != -1) {
            fileExtends = readFile.getName().substring(index + 1);
        }

        if ((fileExtends == null) || (fileExtends.equalsIgnoreCase("xls"))) {
            return readExcelFile(hasTitle, readFile, rowNumber);
        }

        if ((fileExtends != null) && (fileExtends.equalsIgnoreCase("csv"))) {
            return readCsvFile(hasTitle, readFile, rowNumber);
        }

        if ((fileExtends != null) && (fileExtends.equalsIgnoreCase("txt"))) {
            return readTextFile(hasTitle, readFile, rowNumber);
        }

        return (String[][]) null;
    }

    private String[][] readExcelFile(int readTitle, File readFile, int rowNumber) {
        try {
            InputStream inputStream = new FileInputStream(readFile);
            Workbook workbook = Workbook.getWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(0);

            int columnCount = sheet.getColumns();
            int rowCount = sheet.getRows();
            rowNumber = (rowNumber > rowCount) ? rowCount : rowNumber;

            ArrayList readList = new ArrayList();
            for (int i = readTitle; i < rowNumber; ++i) {
                String[] readRow = new String[columnCount];
                for (int j = 0; j < columnCount; ++j) {
                    Cell cell = sheet.getCell(j, i);

                    if ((sheet.getCell(j, i).getType() == CellType.DATE)
                            || (sheet.getCell(j, i).getType() == CellType.DATE_FORMULA)) {
                        String temp = cell.getContents();
                        SimpleDateFormat dateformat;
                        if ((cell.getContents().trim().length() <= 10)
                                && (cell.getContents().indexOf(":") < 0)) {
                            dateformat = new SimpleDateFormat("yyyy-MM-dd");
                        } else {
                            if ((cell.getContents().trim().length() <= 8)
                                    && (cell.getContents().indexOf(":") > -1))
                                dateformat = new SimpleDateFormat("HH:mm:ss");
                            else {
                                dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            }
                        }
                        dateformat.setTimeZone(TimeZone.getTimeZone("GMT"));

                        DateCell datecell = (DateCell) cell;
                        Date startdate = datecell.getDate();
                        readRow[j] = dateformat.format(startdate);
                    } else {
                        readRow[j] = cell.getContents().trim();
                    }
                }
                readList.add(readRow);
            }
            workbook.close();
            inputStream.close();

            List noBlankRowList = new ArrayList();
            Iterator iterator = readList.iterator();
            while (iterator.hasNext()) {
                boolean isBlankRows = true;
                String[] originalRow = (String[]) (String[]) iterator.next();
                for (int i = 0; i < originalRow.length; ++i) {
                    if ((originalRow[i] != null) && (originalRow[i].length() > 0)) {
                        isBlankRows = false;
                        break;
                    }
                }
                if (!isBlankRows) {
                    noBlankRowList.add(originalRow);
                }
            }

            int noBlankRowSize = noBlankRowList.size();
            if (noBlankRowSize == 0) {
                return (String[][]) null;
            }
            String[][] resultArr = new String[noBlankRowSize][columnCount];
            for (int i = 0; i < noBlankRowSize; ++i)
                resultArr[i] = ((String[]) noBlankRowList.get(i));
            return resultArr;
        } catch (Exception e) {
        }
        return (String[][]) null;
    }

    private String[][] readTextFile(int readTitle, File readFile, int rowNumber) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(
                    readFile)));

            String line = null;
            List readList = new ArrayList();
            while ((line = in.readLine()) != null) {
                if (line.trim().length() > 0)
                    ;
                readList.add(line);
            }

            int readListSize = readList.size();
            if (readListSize == 0) {
                return (String[][]) null;
            }
            int length = ((String) readList.get(0)).split("\t").length;
            int size = (rowNumber > readListSize) ? readListSize : rowNumber;
            String[][] resultArr = new String[size - readTitle][length];
            for (int i = readTitle; i < size; ++i) {
                resultArr[(i - readTitle)] = ((String) readList.get(i)).split("\t");
            }
            return resultArr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String[][]) null;
    }

    private String[][] readCsvFile(int readTitle, File readFile, int rowNumber) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(
                    readFile)));

            String line = null;
            List readList = new ArrayList();
            while ((line = in.readLine()) != null) {
                if (line.trim().length() > 0)
                    ;
                readList.add(line);
            }

            int readListSize = readList.size();
            if (readListSize == 0) {
                return (String[][]) null;
            }
            int length = ((String) readList.get(0)).split(",").length;
            int size = (rowNumber > readListSize) ? readListSize : rowNumber;
            String[][] resultArr = new String[size - readTitle][length];
            for (int i = readTitle; i < size; ++i) {
                resultArr[(i - readTitle)] = ((String) readList.get(i)).split(",");
            }
            return resultArr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String[][]) null;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.importfile.bo.ReadContent JD-Core Version: 0.5.4
 */