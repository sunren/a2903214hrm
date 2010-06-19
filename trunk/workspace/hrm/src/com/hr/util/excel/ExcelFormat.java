package com.hr.util.excel;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

public class ExcelFormat {
    private WritableCellFormat titleCellFormat;
    private WritableCellFormat bodyCellFormat;

    public ExcelFormat() throws WriteException {
        this.titleCellFormat = createTitleCellFormat();
        this.bodyCellFormat = createBodyCellFormat(new int[0]);
    }

    public static WritableCellFormat createTitleCellFormat() throws WriteException {
        WritableCellFormat result = createDefaultFormat(new int[0]);
        result.setBackground(Colour.GRAY_25);
        return result;
    }

    public static WritableCellFormat createBodyCellFormat(int[] floatNum) throws WriteException {
        WritableCellFormat result = createDefaultFormat(floatNum);
        return result;
    }

    public static WritableCellFormat createBodyCellFormat(Colour colour) throws WriteException {
        WritableCellFormat result = createDefaultFormat(new int[0]);
        result.setBackground(colour);
        return result;
    }

    public static WritableCellFormat createDefaultFormat(int[] floatNum) throws WriteException {
        WritableCellFormat result;
        if (floatNum.length < 1) {
            result = new WritableCellFormat();
        } else {
            String format = "";
            if (floatNum[0] > 0) {
                format = ".";
                for (int i = 0; i < floatNum[0]; ++i) {
                    format = format + "0";
                }
            }
            NumberFormat nf = new NumberFormat("#0" + format);
            result = new WritableCellFormat(nf);
        }
        setAlignmentAndWrap(result);
        result.setFont(defaultFontFormat());
        result.setBorder(Border.ALL, BorderLineStyle.THIN);
        return result;
    }

    public static void setAlignmentAndWrap(WritableCellFormat cellFormat) throws WriteException {
        cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

        cellFormat.setAlignment(Alignment.CENTRE);

        cellFormat.setWrap(true);
    }

    public static WritableFont defaultFontFormat() {
        return new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
    }

    public WritableCellFormat getTitleCellFormat() {
        return this.titleCellFormat;
    }

    public WritableCellFormat getBodyCellFormat() {
        return this.bodyCellFormat;
    }

    public void setTitleCellFormat(WritableCellFormat titleCellFormat) {
        this.titleCellFormat = titleCellFormat;
    }

    public void setBodyCellFormat(WritableCellFormat bodyCellFormat) {
        this.bodyCellFormat = bodyCellFormat;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.excel.ExcelFormat JD-Core Version: 0.5.4
 */