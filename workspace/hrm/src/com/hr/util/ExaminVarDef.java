package com.hr.util;

import com.hr.examin.domain.Attendmonthly;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;

public class ExaminVarDef {
    private Double C1 = Double.valueOf(0.0D);

    private Double C2 = Double.valueOf(0.0D);

    private Double C3 = Double.valueOf(0.0D);

    private Double C6 = Double.valueOf(0.0D);

    private Double C7 = Double.valueOf(0.0D);

    private Double C8 = Double.valueOf(0.0D);

    private Double C11 = Double.valueOf(0.0D);

    private Double C12 = Double.valueOf(0.0D);

    private Double C13 = Double.valueOf(0.0D);

    private Double C20 = Double.valueOf(0.0D);

    private Double C21 = Double.valueOf(0.0D);

    private Double C22 = Double.valueOf(0.0D);

    private Double C23 = Double.valueOf(0.0D);

    private Double C30 = Double.valueOf(0.0D);

    private Double C31 = Double.valueOf(0.0D);

    private Double C32 = Double.valueOf(0.0D);

    private Double C33 = Double.valueOf(0.0D);

    private Double C34 = Double.valueOf(0.0D);

    private Double C35 = Double.valueOf(0.0D);

    private Double C36 = Double.valueOf(0.0D);

    private Double C37 = Double.valueOf(0.0D);

    private Double C38 = Double.valueOf(0.0D);

    private Double C39 = Double.valueOf(0.0D);

    private Double C40 = Double.valueOf(0.0D);

    private Double C41 = Double.valueOf(0.0D);

    private Double C42 = Double.valueOf(0.0D);

    private Double C50 = Double.valueOf(0.0D);

    private Double C51 = Double.valueOf(0.0D);

    private Double C52 = Double.valueOf(0.0D);

    private Double C53 = Double.valueOf(0.0D);

    private Double C54 = Double.valueOf(0.0D);

    private Double C55 = Double.valueOf(0.0D);

    private Double C56 = Double.valueOf(0.0D);

    private Double C57 = Double.valueOf(0.0D);

    private Double C58 = Double.valueOf(0.0D);

    private Double C59 = Double.valueOf(0.0D);

    private Double C60 = Double.valueOf(0.0D);

    private Double C61 = Double.valueOf(0.0D);

    private Double C62 = Double.valueOf(0.0D);

    private Double C71 = Double.valueOf(0.0D);
    private Double C72 = Double.valueOf(0.0D);
    private Double C73 = Double.valueOf(0.0D);
    private Double C74 = Double.valueOf(0.0D);
    private Double C75 = Double.valueOf(0.0D);
    private Double C76 = Double.valueOf(0.0D);
    private Double C77 = Double.valueOf(0.0D);
    private Double C78 = Double.valueOf(0.0D);
    private Double C79 = Double.valueOf(0.0D);
    private Double C80 = Double.valueOf(0.0D);
    private Double C81 = Double.valueOf(0.0D);
    private Double C82 = Double.valueOf(0.0D);
    private Double C83 = Double.valueOf(0.0D);
    private Double C84 = Double.valueOf(0.0D);
    private Double C85 = Double.valueOf(0.0D);
    private Double C86 = Double.valueOf(0.0D);
    private Object C87 = Double.valueOf(0.0D);
    private Double C88 = Double.valueOf(0.0D);
    private Double C89 = Double.valueOf(0.0D);
    private Double C90 = Double.valueOf(0.0D);
    private Double C91 = Double.valueOf(0.0D);
    private Double C92 = Double.valueOf(0.0D);
    private Double C93 = Double.valueOf(0.0D);
    private Double C94 = Double.valueOf(0.0D);

    public ExaminVarDef(Attendmonthly attendmonthly) {
        if (attendmonthly == null) {
            return;
        }

        this.C1 = getDoubleValue(attendmonthly.getAttmDutyDays());
        this.C2 = getDoubleValue(attendmonthly.getAttmOnDutyDays());
        this.C3 = getDoubleValue(attendmonthly.getAttmOffDutyDays());

        this.C6 = getDoubleValue(attendmonthly.getAttmDutyHours());
        this.C7 = getDoubleValue(attendmonthly.getAttmOnDutyHours());
        this.C8 = getDoubleValue(attendmonthly.getAttmOffDutyHours());

        this.C11 = getDoubleValue(attendmonthly.getAttmLateTimes());
        this.C12 = getDoubleValue(attendmonthly.getAttmEarlyLeave());
        this.C13 = getDoubleValue(attendmonthly.getAttmAbsentDays());

        this.C20 = getDoubleValue(attendmonthly.getAttmOvertimeHours());
        this.C21 = getDoubleValue(attendmonthly.getAttmOtNormalHours());
        this.C22 = getDoubleValue(attendmonthly.getAttmOtWeekendHours());
        this.C23 = getDoubleValue(attendmonthly.getAttmOtHolidayHours());

        this.C30 = getDoubleValue(attendmonthly.getAttmLeaveDays());
        this.C31 = getDoubleValue(attendmonthly.getAttmLeaveAnnualDays());
        this.C32 = getDoubleValue(attendmonthly.getAttmLeaveCasualDays());
        this.C33 = getDoubleValue(attendmonthly.getAttmLeaveSickDays());
        this.C34 = getDoubleValue(attendmonthly.getAttmLeaveSick01Days());
        this.C35 = getDoubleValue(attendmonthly.getAttmLeaveWeddingDays());
        this.C36 = getDoubleValue(attendmonthly.getAttmLeaveMaternityDays());
        this.C37 = getDoubleValue(attendmonthly.getAttmLeaveTravelDays());
        this.C38 = getDoubleValue(attendmonthly.getAttmLeaveOuterDays());
        this.C39 = getDoubleValue(attendmonthly.getAttmLeaveTiaoxiuDays());
        this.C40 = getDoubleValue(attendmonthly.getAttmLeaveOtherDays());
        this.C41 = getDoubleValue(attendmonthly.getAttmLeaveTiaoxiu01Days());
        this.C42 = getDoubleValue(attendmonthly.getAttmLeaveSick02Days());

        this.C50 = getDoubleValue(attendmonthly.getAttmLeaveHours());
        this.C51 = getDoubleValue(attendmonthly.getAttmLeaveAnnualHours());
        this.C52 = getDoubleValue(attendmonthly.getAttmLeaveCasualHours());
        this.C53 = getDoubleValue(attendmonthly.getAttmLeaveSickHours());
        this.C54 = getDoubleValue(attendmonthly.getAttmLeaveSick01Hours());
        this.C55 = getDoubleValue(attendmonthly.getAttmLeaveWeddingHours());
        this.C56 = getDoubleValue(attendmonthly.getAttmLeaveMaternityHours());
        this.C57 = getDoubleValue(attendmonthly.getAttmLeaveTravelHours());
        this.C58 = getDoubleValue(attendmonthly.getAttmLeaveOuterHours());
        this.C59 = getDoubleValue(attendmonthly.getAttmLeaveTiaoxiuHours());
        this.C60 = getDoubleValue(attendmonthly.getAttmLeaveOtherHours());
        this.C61 = getDoubleValue(attendmonthly.getAttmLeaveTiaoxiu01Hours());
        this.C62 = getDoubleValue(attendmonthly.getAttmLeaveSick02Hours());

        this.C71 = getDoubleValue(attendmonthly.getAttmField01());
        this.C72 = getDoubleValue(attendmonthly.getAttmField02());
        this.C73 = getDoubleValue(attendmonthly.getAttmField03());
        this.C74 = getDoubleValue(attendmonthly.getAttmField04());
        this.C75 = getDoubleValue(attendmonthly.getAttmField05());
        this.C76 = getDoubleValue(attendmonthly.getAttmField06());
        this.C77 = getDoubleValue(attendmonthly.getAttmField07());
        this.C78 = getDoubleValue(attendmonthly.getAttmField08());
        this.C79 = getDoubleValue(attendmonthly.getAttmField09());
        this.C80 = getDoubleValue(attendmonthly.getAttmField10());
        this.C81 = getDoubleValue(attendmonthly.getAttmField11());
        this.C82 = getDoubleValue(attendmonthly.getAttmField12());
        this.C83 = getDoubleValue(attendmonthly.getAttmField13());
        this.C84 = getDoubleValue(attendmonthly.getAttmField14());
        this.C85 = getDoubleValue(attendmonthly.getAttmField15());
        this.C86 = getDoubleValue(attendmonthly.getAttmField16());
        this.C87 = getDoubleValue(attendmonthly.getAttmField17());
        this.C88 = getDoubleValue(attendmonthly.getAttmField18());
        this.C89 = getDoubleValue(attendmonthly.getAttmField19());
        this.C90 = getDoubleValue(attendmonthly.getAttmField20());
        this.C91 = getDoubleValue(attendmonthly.getAttmField21());
        this.C92 = getDoubleValue(attendmonthly.getAttmField22());
        this.C93 = getDoubleValue(attendmonthly.getAttmField23());
        this.C94 = getDoubleValue(attendmonthly.getAttmField24());
    }

    private Double getDoubleValue(String str) {
        if (StringUtils.isEmpty(str))
            return Double.valueOf(0.0D);
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException e) {
        }
        return Double.valueOf(0.0D);
    }

    private Double getDoubleValue(BigDecimal decimalNum) {
        return Double.valueOf((decimalNum == null) ? 0.0D : decimalNum.doubleValue());
    }

    public Double getC1() {
        return this.C1;
    }

    public void setC1(Double c1) {
        this.C1 = c1;
    }

    public Double getC2() {
        return this.C2;
    }

    public void setC2(Double c2) {
        this.C2 = c2;
    }

    public Double getC3() {
        return this.C3;
    }

    public void setC3(Double c3) {
        this.C3 = c3;
    }

    public Double getC6() {
        return this.C6;
    }

    public void setC6(Double c6) {
        this.C6 = c6;
    }

    public Double getC7() {
        return this.C7;
    }

    public void setC7(Double c7) {
        this.C7 = c7;
    }

    public Double getC8() {
        return this.C8;
    }

    public void setC8(Double c8) {
        this.C8 = c8;
    }

    public Double getC11() {
        return this.C11;
    }

    public void setC11(Double c11) {
        this.C11 = c11;
    }

    public Double getC12() {
        return this.C12;
    }

    public void setC12(Double c12) {
        this.C12 = c12;
    }

    public Double getC13() {
        return this.C13;
    }

    public void setC13(Double c13) {
        this.C13 = c13;
    }

    public Double getC20() {
        return this.C20;
    }

    public void setC20(Double c20) {
        this.C20 = c20;
    }

    public Double getC21() {
        return this.C21;
    }

    public void setC21(Double c21) {
        this.C21 = c21;
    }

    public Double getC22() {
        return this.C22;
    }

    public void setC22(Double c22) {
        this.C22 = c22;
    }

    public Double getC23() {
        return this.C23;
    }

    public void setC23(Double c23) {
        this.C23 = c23;
    }

    public Double getC30() {
        return this.C30;
    }

    public void setC30(Double c30) {
        this.C30 = c30;
    }

    public Double getC31() {
        return this.C31;
    }

    public void setC31(Double c31) {
        this.C31 = c31;
    }

    public Double getC32() {
        return this.C32;
    }

    public void setC32(Double c32) {
        this.C32 = c32;
    }

    public Double getC33() {
        return this.C33;
    }

    public void setC33(Double c33) {
        this.C33 = c33;
    }

    public Double getC34() {
        return this.C34;
    }

    public void setC34(Double c34) {
        this.C34 = c34;
    }

    public Double getC35() {
        return this.C35;
    }

    public void setC35(Double c35) {
        this.C35 = c35;
    }

    public Double getC36() {
        return this.C36;
    }

    public void setC36(Double c36) {
        this.C36 = c36;
    }

    public Double getC37() {
        return this.C37;
    }

    public void setC37(Double c37) {
        this.C37 = c37;
    }

    public Double getC38() {
        return this.C38;
    }

    public void setC38(Double c38) {
        this.C38 = c38;
    }

    public Double getC39() {
        return this.C39;
    }

    public void setC39(Double c39) {
        this.C39 = c39;
    }

    public Double getC40() {
        return this.C40;
    }

    public void setC40(Double c40) {
        this.C40 = c40;
    }

    public Double getC41() {
        return this.C41;
    }

    public void setC41(Double c41) {
        this.C41 = c41;
    }

    public Double getC42() {
        return this.C42;
    }

    public void setC42(Double c42) {
        this.C42 = c42;
    }

    public Double getC50() {
        return this.C50;
    }

    public void setC50(Double c50) {
        this.C50 = c50;
    }

    public Double getC51() {
        return this.C51;
    }

    public void setC51(Double c51) {
        this.C51 = c51;
    }

    public Double getC52() {
        return this.C52;
    }

    public void setC52(Double c52) {
        this.C52 = c52;
    }

    public Double getC53() {
        return this.C53;
    }

    public void setC53(Double c53) {
        this.C53 = c53;
    }

    public Double getC54() {
        return this.C54;
    }

    public void setC54(Double c54) {
        this.C54 = c54;
    }

    public Double getC55() {
        return this.C55;
    }

    public void setC55(Double c55) {
        this.C55 = c55;
    }

    public Double getC56() {
        return this.C56;
    }

    public void setC56(Double c56) {
        this.C56 = c56;
    }

    public Double getC57() {
        return this.C57;
    }

    public void setC57(Double c57) {
        this.C57 = c57;
    }

    public Double getC58() {
        return this.C58;
    }

    public void setC58(Double c58) {
        this.C58 = c58;
    }

    public Double getC59() {
        return this.C59;
    }

    public void setC59(Double c59) {
        this.C59 = c59;
    }

    public Double getC60() {
        return this.C60;
    }

    public void setC60(Double c60) {
        this.C60 = c60;
    }

    public Double getC61() {
        return this.C61;
    }

    public void setC61(Double c61) {
        this.C61 = c61;
    }

    public Double getC62() {
        return this.C62;
    }

    public void setC62(Double c62) {
        this.C62 = c62;
    }

    public Double getC71() {
        return this.C71;
    }

    public void setC71(Double c71) {
        this.C71 = c71;
    }

    public Double getC72() {
        return this.C72;
    }

    public void setC72(Double c72) {
        this.C72 = c72;
    }

    public Double getC73() {
        return this.C73;
    }

    public void setC73(Double c73) {
        this.C73 = c73;
    }

    public Double getC74() {
        return this.C74;
    }

    public void setC74(Double c74) {
        this.C74 = c74;
    }

    public Double getC75() {
        return this.C75;
    }

    public void setC75(Double c75) {
        this.C75 = c75;
    }

    public Double getC76() {
        return this.C76;
    }

    public void setC76(Double c76) {
        this.C76 = c76;
    }

    public Double getC77() {
        return this.C77;
    }

    public void setC77(Double c77) {
        this.C77 = c77;
    }

    public Double getC78() {
        return this.C78;
    }

    public void setC78(Double c78) {
        this.C78 = c78;
    }

    public Double getC79() {
        return this.C79;
    }

    public void setC79(Double c79) {
        this.C79 = c79;
    }

    public Double getC80() {
        return this.C80;
    }

    public void setC80(Double c80) {
        this.C80 = c80;
    }

    public Double getC81() {
        return this.C81;
    }

    public void setC81(Double c81) {
        this.C81 = c81;
    }

    public Double getC82() {
        return this.C82;
    }

    public void setC82(Double c82) {
        this.C82 = c82;
    }

    public Double getC83() {
        return this.C83;
    }

    public void setC83(Double c83) {
        this.C83 = c83;
    }

    public Double getC84() {
        return this.C84;
    }

    public void setC84(Double c84) {
        this.C84 = c84;
    }

    public Double getC85() {
        return this.C85;
    }

    public void setC85(Double c85) {
        this.C85 = c85;
    }

    public Double getC86() {
        return this.C86;
    }

    public void setC86(Double c86) {
        this.C86 = c86;
    }

    public Object getC87() {
        return this.C87;
    }

    public void setC87(Object c87) {
        this.C87 = c87;
    }

    public Double getC88() {
        return this.C88;
    }

    public void setC88(Double c88) {
        this.C88 = c88;
    }

    public Double getC89() {
        return this.C89;
    }

    public void setC89(Double c89) {
        this.C89 = c89;
    }

    public Double getC90() {
        return this.C90;
    }

    public void setC90(Double c90) {
        this.C90 = c90;
    }

    public Double getC91() {
        return this.C91;
    }

    public void setC91(Double c91) {
        this.C91 = c91;
    }

    public Double getC92() {
        return this.C92;
    }

    public void setC92(Double c92) {
        this.C92 = c92;
    }

    public Double getC93() {
        return this.C93;
    }

    public void setC93(Double c93) {
        this.C93 = c93;
    }

    public Double getC94() {
        return this.C94;
    }

    public void setC94(Double c94) {
        this.C94 = c94;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.ExaminVarDef JD-Core Version: 0.5.4
 */