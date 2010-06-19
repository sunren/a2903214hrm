package com.hr.exportfile;

import com.hr.base.Constants;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Jobgrade;
import com.hr.configuration.domain.Location;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.PositionBase;
import com.hr.util.MyTools;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExportSalary extends MutipleSheetExportHandler implements Constants {
    private boolean mySalary;

    public ExportSalary(List[] results, List[] titles, boolean mySalary) {
        super(results, titles);
        this.mySalary = mySalary;
    }

    protected void buildTitle() throws RowsExceededException, WriteException {
        this.writableSheets = new WritableSheet[this.titles.length];
        for (int i = 0; i < this.titles.length; ++i) {
            this.writableSheets[i] = this.writableWorkbook.createSheet(this.titles[i].get(0)
                    .toString(), i);
            int sheetTitleLength = this.titles[i].size() - 1;

            int count = 0;
            if (this.mySalary) {
                this.writableSheets[i].addCell(new Label(0, 0, "年-月", this.titleCellFormat));
                count = 1;
            }

            this.writableSheets[i].setRowView(0, 500);

            this.writableSheets[i].setColumnView(2 + count, 15);
            this.writableSheets[i].setColumnView(6 + count, 15);
            this.writableSheets[i].setColumnView(8 + count, 15);
            for (int j = 0; j < sheetTitleLength; ++j) {
                if (j > 11)
                    this.writableSheets[i].setColumnView(j + count, 20);
                this.writableSheets[i].addCell(new Label(j + count, 0, this.titles[i].get(j + 1)
                        .toString(), this.titleCellFormat));
            }
        }
    }

    protected void buildBody() throws RowsExceededException, WriteException, IOException {
        int size = this.results.length;
        String[] acceptNameArray = new String[size];
        int[] itemsSize = new int[size];
        for (int i = 0; i < size; ++i) {
            List list = this.results[i];
            acceptNameArray[i] = this.titles[i].get(0).toString();
            itemsSize[i] = (this.titles[i].size() - 13);
            for (int j = 0; j < list.size(); ++j) {
                this.writableSheets[i].setRowView(j + 1, 400);
                Empsalarypay pay = (Empsalarypay) list.get(j);
                int count = 0;
                if (this.mySalary) {
                    this.writableSheets[i].addCell(new Label(0, j + 1, pay.getYearAndMonth(),
                            this.defaultCellFormat));

                    count = 1;
                }
                this.writableSheets[i].addCell(new Label(count, j + 1, pay.getEspEmpno()
                        .getEmpDistinctNo(), this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(count + 1, j + 1, pay.getEspEmpno()
                        .getEmpName(), this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(count + 2, j + 1, pay.getEspEmpno()
                        .getEmpDeptNo().getDepartmentName(), this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(count + 3, j + 1, pay.getEspEmpno()
                        .getEmpPbNo().getPbName(), this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(count + 4, j + 1, pay.getEspEmpno()
                        .getEmpType().getEmptypeName(), this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(count + 5, j + 1, pay.getEspLocation()
                        .getLocationName(), this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(count + 6, j + 1,
                        getEmpStaus(pay.getEspEmpno().getEmpStatus()).toString(),
                        this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(count + 7, j + 1, MyTools.vigenere(pay
                        .getEspEmpconfig().getEscBankAccountNo(), MyTools.getUpKey(pay
                        .getEspEmpconfig().getId(), MyTools.STRING), MyTools.DECRYPT_MODE),
                        this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(count + 8, j + 1, pay.getEspEmpconfig()
                        .getEscBankName(), this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(count + 9, j + 1, pay.getEspEmpconfig()
                        .getEscCostCenter(), this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(count + 10, j + 1, pay.getEspEmpconfig()
                        .getEscJobgrade().getJobGradeName(), this.defaultCellFormat));

                this.writableSheets[i].addCell(new Label(count + 11, j + 1, pay.getEspEsavId()
                        .getEsavEsac().getEsacName(), this.defaultCellFormat));

                int itemSize = (itemsSize[i] < 48) ? itemsSize[i] : 48;

                for (int acssist = 0; acssist < itemSize; ++acssist) {
                    try {
                        Class ownerClass = pay.getClass();
                        int number = acssist + 1;
                        Method getMethod = ownerClass.getMethod("getEspColumn" + number,
                                                                new Class[0]);

                        BigDecimal bd = (BigDecimal) getMethod.invoke(pay, new Object[0]);
                        if (bd == null)
                            bd = new BigDecimal(0);

                        Number nb = new Number(count + 12 + acssist, j + 1, bd.floatValue(),
                                this.numberCellFormat);

                        this.writableSheets[i].addCell(nb);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        this.writableWorkbook.write();
    }

    public String getEmpStaus(Integer i) {
        switch (i.intValue()) {
        case 0:
            return "辞职";
        case 1:
            return "在职";
        case 2:
            return "停薪留职";
        case 3:
            return "退休";
        case 4:
            return "辞退";
        case 5:
            return "合同到期";
        case 6:
            return "其他";
        }
        return null;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.exportfile.ExportSalary JD-Core Version: 0.5.4
 */