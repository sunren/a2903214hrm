package com.hr.exportfile;

import com.hr.base.Constants;
import com.hr.compensation.domain.Empsalaryacct;
import com.hr.compensation.domain.Empsalaryacctversion;
import com.hr.compensation.domain.Empsalaryadj;
import com.hr.configuration.bo.IStatBO;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Jobgrade;
import com.hr.configuration.domain.Location;
import com.hr.configuration.domain.Statusconf;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExportSalaryAdj extends MutipleSheetExportHandler implements Constants {
    public ExportSalaryAdj(List[] results, List[] titles) {
        super(results, titles);
    }

    protected void buildTitle() throws RowsExceededException, WriteException {
        WritableFont titleFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, true,
                UnderlineStyle.NO_UNDERLINE, Colour.RED);

        WritableCellFormat titleCellFormat = new WritableCellFormat(titleFont);
        this.writableSheets = new WritableSheet[this.titles.length];

        for (int i = 0; i < this.titles.length; ++i) {
            this.writableSheets[i] = this.writableWorkbook.createSheet(this.titles[i].get(0)
                    .toString(), i);
            int sheetTitleLength = this.titles[i].size() - 1;
            for (int j = 0; j < sheetTitleLength; ++j)
                this.writableSheets[i].addCell(new Label(j, 0,
                        this.titles[i].get(j + 1).toString(), titleCellFormat));
        }
    }

    protected void buildBody() throws RowsExceededException, WriteException, IOException {
        WritableFont writableFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD,
                false);
        WritableCellFormat defaultCellFormat = new WritableCellFormat(writableFont);
        int size = this.results.length;

        int[] itemsSize = new int[size];
        for (int i = 0; i < size; ++i) {
            List list = this.results[i];
            itemsSize[i] = (this.titles[i].size() - 10);

            for (int j = 0; j < list.size(); ++j) {
                Empsalaryadj adj = (Empsalaryadj) list.get(j);
                adj.decryEmpSalaryAdj(adj);

                this.writableSheets[i].addCell(new Label(0, j + 1, adj.getEsaEmpno()
                        .getEmpDistinctNo(), defaultCellFormat));

                this.writableSheets[i].addCell(new Label(1, j + 1, adj.getEsaEmpno().getEmpName(),
                        defaultCellFormat));

                this.writableSheets[i].addCell(new Label(2, j + 1, adj.getEsaEmpno().getEmpDeptNo()
                        .getDepartmentName(), defaultCellFormat));

                this.writableSheets[i].addCell(new Label(3, j + 1, adj.getEsaEmpno().getEmpType()
                        .getEmptypeName(), defaultCellFormat));

                this.writableSheets[i].addCell(new Label(4, j + 1, adj.getEsaEmpno()
                        .getEmpLocationNo().getLocationName(), defaultCellFormat));

                this.writableSheets[i].addCell(new Label(5, j + 1, formatDate(adj
                        .getEsaCurEffDate()), defaultCellFormat));
                this.writableSheets[i].addCell(new Label(6, j + 1, adj.getEsaCurIncrRate()
                        .toPlainString()
                        + "%", defaultCellFormat));

                this.writableSheets[i].addCell(new Label(7, j + 1, adj.getEsaComments(),
                        defaultCellFormat));

                this.writableSheets[i].addCell(new Label(8, j + 1, getStatus(adj.getEsaStatus()),
                        defaultCellFormat));

                this.writableSheets[i].addCell(new Label(9, j + 1, adj.getEsaJobgradeCur()
                        .getJobGradeName(), defaultCellFormat));

                this.writableSheets[i].addCell(new Label(10, j + 1, adj.getEsaJobgradePro()
                        .getJobGradeName(), defaultCellFormat));

                this.writableSheets[i].addCell(new Label(11, j + 1, adj.getEsaEsavIdCur()
                        .getEsavEsac().getEsacName(), defaultCellFormat));

                this.writableSheets[i].addCell(new Label(12, j + 1, adj.getEsaEsavIdPro()
                        .getEsavEsac().getEsacName(), defaultCellFormat));

                for (int acssist = 0; acssist < 48; ++acssist) {
                    try {
                        Class ownerClass = adj.getClass();
                        int number = acssist + 1;
                        Method getMethod = ownerClass.getMethod("getEsaColumn" + number + "Cur",
                                                                new Class[0]);
                        BigDecimal bd = (BigDecimal) getMethod.invoke(adj, new Object[0]);
                        if (bd == null)
                            this.writableSheets[i].addCell(new Label(13 + acssist, j + 1, "",
                                    defaultCellFormat));
                        else
                            this.writableSheets[i].addCell(new Label(13 + acssist, j + 1, bd
                                    .toPlainString(), defaultCellFormat));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                for (int acssist = 0; acssist < 48; ++acssist) {
                    try {
                        Class ownerClass = adj.getClass();
                        int number = acssist + 1;
                        Method getMethod = ownerClass.getMethod("getEsaColumn" + number + "Pro",
                                                                new Class[0]);
                        BigDecimal bd = (BigDecimal) getMethod.invoke(adj, new Object[0]);

                        if (bd == null)
                            this.writableSheets[i].addCell(new Label(61 + acssist, j + 1, "",
                                    defaultCellFormat));
                        else
                            this.writableSheets[i].addCell(new Label(61 + acssist, j + 1, bd
                                    .toPlainString(), defaultCellFormat));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        this.writableWorkbook.write();
    }

    private String getStatus(Integer intergerStatus) {
        IStatBO statBO = (IStatBO) SpringBeanFactory.getBean("statBO");
        Statusconf state = statBO.getStatusByConfigAndTable("empsalaryadj", intergerStatus
                .intValue());
        String status = "";
        if (state != null) {
            status = state.getStatusconfDesc();
        }
        return status;
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
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.exportfile.ExportSalaryAdj JD-Core Version: 0.5.4
 */