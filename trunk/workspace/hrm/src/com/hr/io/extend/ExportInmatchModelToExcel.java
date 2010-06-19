package com.hr.io.extend;

import com.hr.io.domain.Inmatch;
import com.hr.io.domain.InmatchModel;
import com.hr.util.excel.ExcelFormat;
import com.hr.util.excel.IExportToExcel;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportInmatchModelToExcel extends IExportToExcel {
    private InmatchModel inmatchModel;
    private WritableCellFormat cf = ExcelFormat.createBodyCellFormat(Colour.YELLOW);

    public ExportInmatchModelToExcel(String filePathName, InmatchModel inmatchModel)
            throws Exception {
        super.setFilePathName(filePathName);
        setInmatchModel(inmatchModel);
    }

    public void buildContent() throws Exception {
        int needTitle = 1 - this.inmatchModel.getImmNoTitle().intValue();
        WritableSheet ws = createSheet(this.sheetDefaultName, 0, this.writableWorkbook);
        int colNum = 0;
        for (Inmatch inmatch : this.inmatchModel.getImList()) {
            if ((inmatch.getImSampleWidth() != null) && (inmatch.getImSampleWidth().intValue() > 0)) {
                ws.setColumnView(colNum, inmatch.getImSampleWidth().intValue());
            }
            if (needTitle == 1) {
                if (inmatch.getImRequired().intValue() == 1)
                    ws.addCell(new Label(colNum, 0, inmatch.getImFieldDesc(), this.cf));
                else {
                    ws.addCell(new Label(colNum, 0, inmatch.getImFieldDesc(), this.format
                            .getTitleCellFormat()));
                }
                ws.addCell(new Label(colNum++, needTitle, inmatch.getImSample(), this.format
                        .getBodyCellFormat()));
            } else if (inmatch.getImRequired().intValue() == 1) {
                ws.addCell(new Label(colNum++, needTitle, inmatch.getImSample(), this.cf));
            } else {
                ws.addCell(new Label(colNum++, needTitle, inmatch.getImSample(), this.format
                        .getBodyCellFormat()));
            }

        }

        this.writableWorkbook.write();
    }

    public InmatchModel getInmatchModel() {
        return this.inmatchModel;
    }

    public void setInmatchModel(InmatchModel inmatchModel) {
        this.inmatchModel = inmatchModel;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.extend.ExportInmatchModelToExcel JD-Core Version: 0.5.4
 */