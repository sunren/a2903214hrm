package com.hr.exportfile;

import java.util.List;
import jxl.write.WritableSheet;

public abstract class SingleSheetExportHandler extends ExportHandler {
    protected List result;
    protected String sheetTitle;
    protected WritableSheet writableSheet;

    public SingleSheetExportHandler(List result, String sheetTitle) {
        this.result = result;
        this.sheetTitle = sheetTitle;
    }

    protected WritableSheet getWritableSheet() {
        return this.writableSheet;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.exportfile.SingleSheetExportHandler JD-Core Version: 0.5.4
 */