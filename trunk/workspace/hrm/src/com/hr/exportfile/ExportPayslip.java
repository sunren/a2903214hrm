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

public class ExportPayslip extends MutipleSheetExportHandler implements Constants {
    private WritableSheet writableSheet;

    public ExportPayslip(List[] results, List[] titles) {
        super(results, titles);
        doSort(results);
    }

    protected void buildTitle() throws RowsExceededException, WriteException {
    }

    protected void buildBody() throws RowsExceededException, WriteException, IOException {
        int size = this.results.length;
        int[] itemsSize = new int[size];

        int count = -2;
        String currentDept = null;
        int workSheetIndex = 0;
        for (int i = 0; i < size; ++i) {
            List titleList = this.titles[i];

            List list = this.results[i];
            itemsSize[i] = (this.titles[i].size() - 9);

            for (int j = 0; j < list.size(); ++j) {
                Empsalarypay emp = (Empsalarypay) list.get(j);
                if (!emp.getEspDept().getId().equals(currentDept)) {
                    this.writableSheet = this.writableWorkbook.createSheet("工资条-"
                            + emp.getEspDept().getDepartmentName(), workSheetIndex++);

                    this.writableSheet.setRowView(0, 800);
                    currentDept = emp.getEspDept().getId();
                    count = 0;
                }

                int sheetTitleLength = titleList.size() - 2;
                this.writableSheet.addCell(new Label(0, count, "部门", this.titleCellFormat));
                this.writableSheet.addCell(new Label(1, count, "薪资帐套", this.titleCellFormat));
                for (int k = 0; k < sheetTitleLength; ++k) {
                    this.writableSheet.addCell(new Label(k + 2, count, titleList.get(k + 2)
                            .toString(), this.titleCellFormat));
                }

                ++count;
                this.writableSheet.addCell(new Label(0, count,
                        emp.getEspDept().getDepartmentName(), this.defaultCellFormat));

                this.writableSheet.addCell(new Label(1, count, emp.getEspEsavId().getEsavEsac()
                        .getEsacName(), this.defaultCellFormat));

                this.writableSheet.addCell(new Label(2, count,
                        emp.getEspEmpno().getEmpDistinctNo(), this.defaultCellFormat));

                this.writableSheet.addCell(new Label(3, count, emp.getEspEmpno().getEmpName(),
                        this.defaultCellFormat));

                this.writableSheet.addCell(new Label(4, count,
                        emp.getEspEmptype().getEmptypeName(), this.defaultCellFormat));

                this.writableSheet.addCell(new Label(5, count, emp.getEspLocation()
                        .getLocationName(), this.defaultCellFormat));

                this.writableSheet.addCell(new Label(6, count, getEmpStaus(
                                                                           emp.getEspEmpno()
                                                                                   .getEmpStatus())
                        .toString(), this.defaultCellFormat));

                this.writableSheet.addCell(new Label(7, count, MyTools.vigenere(emp
                        .getEspEmpconfig().getEscBankAccountNo(), MyTools.getUpKey(emp
                        .getEspEmpconfig().getId(), MyTools.STRING), MyTools.DECRYPT_MODE),
                        this.defaultCellFormat));

                this.writableSheet.addCell(new Label(8, count, emp.getEspEmpconfig()
                        .getEscJobgrade().getJobGradeName(), this.defaultCellFormat));

                int itemSize = (itemsSize[i] < 48) ? itemsSize[i] : 48;

                for (int acssist = 0; acssist < itemSize; ++acssist) {
                    try {
                        Class ownerClass = emp.getClass();
                        int number = acssist + 1;
                        Method getMethod = ownerClass.getMethod("getEspColumn" + number,
                                                                new Class[0]);

                        BigDecimal bd = (BigDecimal) getMethod.invoke(emp, new Object[0]);
                        Number nb = new Number(9 + acssist, count, bd.floatValue(),
                                this.numberCellFormat);

                        this.writableSheet.addCell(nb);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                count += 2;
            }
        }

        this.writableWorkbook.write();
    }

    private void doSort(List[] results) {
        for (int i = 0; i < results.length; ++i) {
            SalaryRecordSorter sorter = new SalaryRecordSorter(results[i]);
            String[] props = { "espEmpno.empDistinctNo" };
            sorter.doSort(props);
        }
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
        }
        return null;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.exportfile.ExportPayslip JD-Core Version: 0.5.4
 */