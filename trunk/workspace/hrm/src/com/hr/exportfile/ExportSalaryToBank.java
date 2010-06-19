package com.hr.exportfile;

import com.hr.compensation.domain.Empsalarypay;
import com.hr.profile.domain.Employee;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.apache.commons.lang.StringUtils;

public class ExportSalaryToBank extends SingleSheetExportHandler {
    public ExportSalaryToBank(List result, String sheetTitle) {
        super(result, sheetTitle);
        doSort();
    }

    protected void buildTitle() throws RowsExceededException, WriteException {
    }

    private void doSort() {
        SalaryRecordSorter sorter = new SalaryRecordSorter(this.result);
        String[] props = { "espBankName", "espDept.departmentName", "espEmpno.empDistinctNo" };

        sorter.doSort(props);
    }

    private WritableSheet buildSheet(String sheetTitle, int sheetIndex)
            throws RowsExceededException, WriteException {
        WritableSheet writableSheet = this.writableWorkbook.createSheet(sheetTitle, sheetIndex);
        writableSheet.setRowView(0, 500);
        writableSheet.setColumnView(2, 15);
        writableSheet.setColumnView(3, 15);
        writableSheet.addCell(new Label(0, 0, "工号", this.titleCellFormat));
        writableSheet.addCell(new Label(1, 0, "姓名", this.titleCellFormat));
        writableSheet.addCell(new Label(2, 0, "银行帐号", this.titleCellFormat));
        writableSheet.addCell(new Label(3, 0, "银行弄1�7户行", this.titleCellFormat));
        writableSheet.addCell(new Label(4, 0, "税后收入", this.titleCellFormat));
        return writableSheet;
    }

    protected void buildBody() throws RowsExceededException, WriteException, IOException {
        String bankName = null;
        int size = this.result.size();
        int sheetIndex = 0;
        int countIndex = 0;
        for (int i = 0; i < size; ++i) {
            Empsalarypay tmpEmpSP = (Empsalarypay) this.result.get(i);
            if ((i == 0) && (StringUtils.isEmpty(tmpEmpSP.getEspBankName()))) {
                this.writableSheet = buildSheet(this.sheetTitle, 0);
                sheetIndex = 1;
            } else if ((StringUtils.isNotEmpty(tmpEmpSP.getEspBankName()))
                    && (!StringUtils.equals(tmpEmpSP.getEspBankName(), bankName))) {
                this.writableSheet = buildSheet(this.sheetTitle + tmpEmpSP.getEspBankName(),
                                                sheetIndex++);
                countIndex = 0;
            }

            bankName = tmpEmpSP.getEspBankName();
            this.writableSheet.setRowView(countIndex + 1, 400);
            this.writableSheet.addCell(new Label(0, countIndex + 1, tmpEmpSP.getEspEmpno()
                    .getEmpDistinctNo(), this.defaultCellFormat));

            this.writableSheet.addCell(new Label(1, countIndex + 1, tmpEmpSP.getEspEmpno()
                    .getEmpName(), this.defaultCellFormat));

            this.writableSheet.addCell(new Label(2, countIndex + 1, tmpEmpSP.getEspBankAccountNo(),
                    this.defaultCellFormat));
            this.writableSheet.addCell(new Label(3, countIndex + 1, tmpEmpSP.getEspBankName()));

            BigDecimal salary = tmpEmpSP.getShowColumn19();
            Number nb = new Number(4, countIndex + 1, salary.floatValue(), this.numberCellFormat);

            this.writableSheet.addCell(nb);
            ++countIndex;
        }
        this.writableWorkbook.write();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.exportfile.ExportSalaryToBank JD-Core Version: 0.5.4
 */