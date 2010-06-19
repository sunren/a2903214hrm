package com.hr.exportfile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public abstract class ExportHandler {
    protected WritableWorkbook writableWorkbook;
    protected WritableCellFormat titleCellFormat;
    protected WritableCellFormat defaultCellFormat;
    protected WritableCellFormat numberCellFormat;
    protected WritableCellFormat numberCellFormat1;
    protected static SimpleDateFormat SIMPLE_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public void export(String filename) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        this.writableWorkbook = Workbook.createWorkbook(fileOutputStream);
        WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false);
        this.titleCellFormat = new WritableCellFormat(titleFont);

        this.titleCellFormat.setBackground(Colour.GRAY_25);
        setAlignmentAndWrap(this.titleCellFormat);

        this.titleCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        WritableFont writableFont = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD,
                false);

        this.defaultCellFormat = new WritableCellFormat(writableFont);
        setAlignmentAndWrap(this.defaultCellFormat);

        NumberFormat nf = new NumberFormat("#0.00");
        this.numberCellFormat = new WritableCellFormat(nf);
        setAlignmentAndWrap(this.numberCellFormat);

        NumberFormat nf1 = new NumberFormat("#0.0");
        this.numberCellFormat1 = new WritableCellFormat(nf1);
        setAlignmentAndWrap(this.numberCellFormat1);
        buildTitle();
        buildBody();

        releaseResource();
        if (null != fileOutputStream) {
            fileOutputStream.close();
            fileOutputStream = null;
        }
    }

    protected abstract void buildTitle() throws RowsExceededException, WriteException;

    protected abstract void buildBody() throws RowsExceededException, WriteException, IOException;

    private void releaseResource() throws WriteException, IOException {
        if (this.writableWorkbook != null) {
            this.writableWorkbook.close();
            this.writableWorkbook = null;
        }
    }

    protected void setAlignmentAndWrap(WritableCellFormat cellFormat) throws Exception {
        cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        cellFormat.setAlignment(Alignment.CENTRE);

        cellFormat.setWrap(true);
    }

    protected String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        String eqDate = SIMPLE_DATEFORMAT.format(date);
        return eqDate;
    }

    protected WritableWorkbook getWritableWorkbook() {
        return this.writableWorkbook;
    }

    protected void setWritableWorkbook(WritableWorkbook writableWorkbook) {
        this.writableWorkbook = writableWorkbook;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.exportfile.ExportHandler JD-Core Version: 0.5.4
 */