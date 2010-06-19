package com.hr.util;

import com.hr.examin.bo.interfaces.ILeavecalendarBO;
import com.hr.examin.domain.Leavecalendar;
import com.hr.profile.domain.Employee;
import com.hr.spring.SpringBeanFactory;
import java.util.Date;
import java.util.List;

public class DateVarDef {
    private Double B0Y = Double.valueOf(0.0D);
    private Double B0M = Double.valueOf(0.0D);

    private Double B1Y = Double.valueOf(0.0D);
    private Double B1M = Double.valueOf(0.0D);

    private Double B1 = Double.valueOf(0.0D);
    private Double B2 = Double.valueOf(0.0D);
    private Double B3 = Double.valueOf(0.0D);
    private Double B4 = Double.valueOf(0.0D);
    private Double B5 = Double.valueOf(0.0D);
    private Double B6 = Double.valueOf(0.0D);
    private Double B7 = Double.valueOf(0.0D);

    private Double B11 = Double.valueOf(0.0D);
    private Double B12 = Double.valueOf(0.0D);
    private Double B13 = Double.valueOf(0.0D);
    private Double B14 = Double.valueOf(0.0D);
    private Double B15 = Double.valueOf(0.0D);
    private Double B16 = Double.valueOf(0.0D);
    private Double B17 = Double.valueOf(0.0D);
    private ILeavecalendarBO lc_BO;

    public DateVarDef(Employee emp, Date[] dateArr, Integer joinYearCalc, List<Leavecalendar> lcList) {
        if ((emp == null) || (dateArr == null))
            return;
        this.lc_BO = ((ILeavecalendarBO) SpringBeanFactory.getBean("leavecalendarBO"));

        if (emp.getEmpWorkDate() != null) {
            Double workYear = MyTools.getWorkYearSalary(emp.getEmpWorkDate(), dateArr[1],
                                                        joinYearCalc);
            setB0Y(Double.valueOf(workYear.intValue()));
            setB0M(Double.valueOf(workYear.doubleValue() * 100.0D - workYear.intValue() * 100));
        }

        Double workYear = MyTools.getWorkYearSalary(emp.getEmpJoinDate(), dateArr[1], joinYearCalc);
        setB1Y(Double.valueOf(workYear.intValue()));
        setB1M(Double.valueOf(workYear.doubleValue() * 100.0D - workYear.intValue() * 100));

        this.lc_BO = ((ILeavecalendarBO) SpringBeanFactory.getBean("leavecalendarBO"));
        setB1(new Double(this.lc_BO.getWorkDays(dateArr[0], dateArr[1], lcList, emp
                .getEmpLocationNo())));
        setB11(new Double(DateUtil.dateDiff(dateArr[0], dateArr[1], 5) + 1));

        if ((emp.getEmpConfirmDate() == null)
                || (emp.getEmpConfirmDate().compareTo(dateArr[1]) > 0)) {
            setB3(this.B1);
            setB13(this.B11);
        } else if ((emp.getEmpConfirmDate().compareTo(dateArr[0]) > 0)
                && (emp.getEmpConfirmDate().compareTo(dateArr[1]) <= 0)) {
            setB3(new Double(this.lc_BO.getWorkDays(dateArr[0], emp.getEmpConfirmDate(), lcList,
                                                    emp.getEmpLocationNo()) - 1));
            if (this.B3.doubleValue() < 0.0D)
                setB3(new Double(0.0D));
            setB4(new Double(this.lc_BO.getWorkDays(emp.getEmpConfirmDate(), dateArr[1], lcList,
                                                    emp.getEmpLocationNo())));
            setB13(new Double(DateUtil.dateDiff(dateArr[0], emp.getEmpConfirmDate(), 5)));
            setB14(new Double(DateUtil.dateDiff(emp.getEmpConfirmDate(), dateArr[1], 5) + 1));
        } else {
            setB4(this.B1);
            setB14(this.B11);
        }

        if ((emp.getEmpJoinDate().compareTo(dateArr[0]) > 0)
                && (emp.getEmpJoinDate().compareTo(dateArr[1]) <= 0)) {
            setB6(new Double(this.lc_BO.getWorkDays(dateArr[0], emp.getEmpJoinDate(), lcList, emp
                    .getEmpLocationNo()) - 1));
            if (this.B6.doubleValue() < 0.0D)
                setB6(new Double(0.0D));
            setB16(new Double(DateUtil.dateDiff(dateArr[0], emp.getEmpJoinDate(), 5)));
            setB3(Double.valueOf(this.B3.doubleValue() - this.B6.doubleValue()));
            setB13(Double.valueOf(this.B13.doubleValue() - this.B16.doubleValue()));
        }

        if ((emp.getEmpTerminateDate() != null)
                && (emp.getEmpTerminateDate().compareTo(dateArr[0]) >= 0)
                && (emp.getEmpTerminateDate().compareTo(dateArr[1]) < 0)) {
            setB7(new Double(this.lc_BO.getWorkDays(emp.getEmpTerminateDate(), dateArr[1], lcList,
                                                    emp.getEmpLocationNo()) - 1));
            if (this.B7.doubleValue() < 0.0D)
                setB7(new Double(0.0D));
            setB17(new Double(DateUtil.dateDiff(emp.getEmpTerminateDate(), dateArr[1], 5)));
            setB3(Double.valueOf((this.B4.doubleValue() - this.B7.doubleValue() < 0.0D) ? this.B3
                    .doubleValue()
                    + this.B4.doubleValue() - this.B7.doubleValue() : this.B3.doubleValue()));
            setB13(Double
                    .valueOf((this.B14.doubleValue() - this.B17.doubleValue() < 0.0D) ? this.B13
                            .doubleValue()
                            + this.B14.doubleValue() - this.B17.doubleValue() : this.B13
                            .doubleValue()));
            setB4(Double.valueOf((this.B4.doubleValue() - this.B7.doubleValue() < 0.0D) ? 0.0D
                    : this.B4.doubleValue() - this.B7.doubleValue()));
            setB14(Double.valueOf((this.B14.doubleValue() - this.B17.doubleValue() < 0.0D) ? 0.0D
                    : this.B14.doubleValue() - this.B17.doubleValue()));
        }

        setB2(Double.valueOf(this.B3.doubleValue() + this.B4.doubleValue()));
        setB5(Double.valueOf(this.B6.doubleValue() + this.B7.doubleValue()));
    }

    public Double getB1Y() {
        return this.B1Y;
    }

    public void setB1Y(Double b1y) {
        this.B1Y = b1y;
    }

    public Double getB1M() {
        return this.B1M;
    }

    public void setB1M(Double b1m) {
        this.B1M = b1m;
    }

    public Double getB0M() {
        return this.B0M;
    }

    public void setB0M(Double b0m) {
        this.B0M = b0m;
    }

    public Double getB0Y() {
        return this.B0Y;
    }

    public void setB0Y(Double b0y) {
        this.B0Y = b0y;
    }

    public Double getB1() {
        return this.B1;
    }

    public void setB1(Double b1) {
        this.B1 = b1;
    }

    public Double getB11() {
        return this.B11;
    }

    public void setB11(Double b11) {
        this.B11 = b11;
    }

    public Double getB12() {
        return this.B12;
    }

    public void setB12(Double b12) {
        this.B12 = b12;
    }

    public Double getB13() {
        return this.B13;
    }

    public void setB13(Double b13) {
        this.B13 = b13;
    }

    public Double getB14() {
        return this.B14;
    }

    public void setB14(Double b14) {
        this.B14 = b14;
    }

    public Double getB15() {
        return this.B15;
    }

    public void setB15(Double b15) {
        this.B15 = b15;
    }

    public Double getB16() {
        return this.B16;
    }

    public void setB16(Double b16) {
        this.B16 = b16;
    }

    public Double getB17() {
        return this.B17;
    }

    public void setB17(Double b17) {
        this.B17 = b17;
    }

    public Double getB2() {
        return this.B2;
    }

    public void setB2(Double b2) {
        this.B2 = b2;
    }

    public Double getB3() {
        return this.B3;
    }

    public void setB3(Double b3) {
        this.B3 = b3;
    }

    public Double getB4() {
        return this.B4;
    }

    public void setB4(Double b4) {
        this.B4 = b4;
    }

    public Double getB5() {
        return this.B5;
    }

    public void setB5(Double b5) {
        this.B5 = b5;
    }

    public Double getB6() {
        return this.B6;
    }

    public void setB6(Double b6) {
        this.B6 = b6;
    }

    public Double getB7() {
        return this.B7;
    }

    public void setB7(Double b7) {
        this.B7 = b7;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.DateVarDef JD-Core Version: 0.5.4
 */