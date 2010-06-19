package com.hr.exportfile;

import com.hr.base.Constants;
import com.hr.compensation.action.SalaryConfigAssist;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExportSalaryConfig extends MutipleSheetExportHandler implements Constants {
    private Map<String, List[]> resultMap;
    private List<String> sheetTitle = new ArrayList();

    public ExportSalaryConfig(List[] results, List[] titles) {
        super(results, titles);
    }

    public ExportSalaryConfig(Map<String, List[]> resultMap) {
        this(null, null);
        this.resultMap = resultMap;
        for (String key : resultMap.keySet())
            this.sheetTitle.add(key);
    }

    protected void buildTitle() throws RowsExceededException, WriteException {
        int size = this.resultMap.keySet().size();
        this.writableSheets = new WritableSheet[size];
        for (int i = 0; i < size; ++i) {
            this.writableSheets[i] = this.writableWorkbook.createSheet((String) this.sheetTitle
                    .get(i), i);
            this.writableSheets[i].setRowView(0, 500);
            this.writableSheets[i].setColumnView(2, 15);
            this.writableSheets[i].setColumnView(6, 15);
            this.writableSheets[i].setColumnView(8, 15);
            int sheetTitleLength = ((List[]) this.resultMap.get(this.sheetTitle.get(i)))[0].size();
            for (int j = 0; j < sheetTitleLength; ++j) {
                if (j > 10)
                    this.writableSheets[i].setColumnView(j, 20);
                this.writableSheets[i].addCell(new Label(j, 0, (String) ((List[]) this.resultMap
                        .get(this.sheetTitle.get(i)))[0].get(j), this.titleCellFormat));
            }
        }
    }

    protected void buildBody() throws RowsExceededException, WriteException, IOException {
        int i = 0;
        for (String key : this.resultMap.keySet()) {
            List<SalaryConfigAssist> contents = ((List[]) this.resultMap.get(key))[1];
            int j = 0;
            for (SalaryConfigAssist salaryConfigAssist : contents) {
                this.writableSheets[i].setRowView(j + 1, 400);
                this.writableSheets[i].addCell(new Label(0, j + 1, salaryConfigAssist.getEmpNo(),
                        this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(1, j + 1, salaryConfigAssist.getEmpName(),
                        this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(2, j + 1, salaryConfigAssist.getEmpDept(),
                        this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(3, j + 1, salaryConfigAssist.getEmpType(),
                        this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(4, j + 1, salaryConfigAssist
                        .getEmpLocation(), this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(5, j + 1, salaryConfigAssist
                        .getEmpStatus().toString(), this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(6, j + 1,
                        salaryConfigAssist.getEmpCount(), this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(7, j + 1, salaryConfigAssist
                        .getEmpBankName(), this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(8, j + 1, salaryConfigAssist
                        .getCostCenter(), this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(9, j + 1, salaryConfigAssist
                        .getSalaryLevel(), this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(10, j + 1, salaryConfigAssist
                        .getAcceptName(), this.defaultCellFormat));

                for (int acssist = 0; acssist < salaryConfigAssist.getList().size(); ++acssist) {
                    Number nb = new Number(11 + acssist, j + 1,
                            getFloatValue((BigDecimal) salaryConfigAssist.getList().get(acssist)),
                            this.numberCellFormat);

                    this.writableSheets[i].addCell(nb);
                }
                ++j;
            }
            ++i;
        }
        this.writableWorkbook.write();
    }

    private float getFloatValue(BigDecimal bd) {
        float fl = 0.0F;
        if (bd != null)
            fl = bd.floatValue();
        return fl;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.exportfile.ExportSalaryConfig JD-Core Version: 0.5.4
 */