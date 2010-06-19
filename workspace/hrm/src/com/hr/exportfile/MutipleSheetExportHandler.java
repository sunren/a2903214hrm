package com.hr.exportfile;

import java.util.List;
import jxl.write.WritableSheet;

public abstract class MutipleSheetExportHandler extends ExportHandler {
    protected List[] results;
    protected List[] titles;
    protected WritableSheet[] writableSheets;

    public MutipleSheetExportHandler(List[] results, List[] titles) {
        this.results = results;
        this.titles = titles;
    }

    protected WritableSheet[] getWritableSheet() {
        return this.writableSheets;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.exportfile.MutipleSheetExportHandler JD-Core Version: 0.5.4
 */