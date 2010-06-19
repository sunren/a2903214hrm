package com.hr.util;

import com.hr.compensation.domain.Empsalaryacctitems;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.compensation.domain.Empsalarypay;
import com.hr.examin.domain.Leavebalance;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class Interpreter {
    private Empsalaryconfig salaryConfig;
    private Empsalarypay salaryPay;
    private Leavebalance leaveBalance;
    private int fillDataMode;
    private SysConfigVarDef sysConfigVarDef;
    private DateVarDef dateVarDef;
    private BenefitVarDef benefitVarDef;
    private ExaminVarDef examinVarDef;
    private int validateFlag;

    public Interpreter() {
        this.sysConfigVarDef = new SysConfigVarDef(null);
        this.dateVarDef = new DateVarDef(null, null, null, null);
        this.benefitVarDef = new BenefitVarDef(null, null);
        this.examinVarDef = new ExaminVarDef(null);
    }

    public String formulaValidate(String formula) {
        formula = formula.replaceAll(" ", "");
        formula = formula.toUpperCase();
        if ((formula == null) || (formula.length() == 0))
            return "SUCC";

        setValidateFlag(1);
        try {
            calculateFormula(formula);
        } catch (InterpreterException ex) {
            return ex.getMessage();
        }
        return "SUCC";
    }

    public BigDecimal calculateLeave(String formula) throws InterpreterException {
        if (StringUtils.isEmpty(formula)) {
            return BigDecimal.ZERO;
        }

        setValidateFlag(0);
        try {
            double result = calculateFormula(formula);

            DecimalFormat df = new DecimalFormat("#.00");
            return new BigDecimal(df.format(result));
        } catch (InterpreterException ex) {
            throw new InterpreterException(ex.getMessage());
        }
    }

    public String formulaValidate(String formula, Empsalaryconfig config) {
        formula = formula.replaceAll(" ", "");
        formula = formula.toUpperCase();
        if ((formula == null) || (formula.length() == 0))
            return "SUCC";

        setSalaryConfig(config);
        setValidateFlag(1);
        try {
            calculateFormula(formula);
        } catch (InterpreterException ex) {
            return ex.getMessage();
        }
        return "SUCC";
    }

    public boolean calculateSalary(List<Empsalaryacctitems> acctItems, boolean noBenefit)
            throws InterpreterException {
        setValidateFlag(0);
        int size = acctItems.size();
        List errorList = new ArrayList();

        for (int j = 0; j < size; ++j) {
            Empsalaryacctitems item = (Empsalaryacctitems) acctItems.get(j);
            try {
                double result;
                if ((item.getEsaiDataIsCalc().intValue() == 0)
                        || (item.getEsaiDataIsCalc().intValue() == 1)) {
                    result = getFieldValue("A" + String.valueOf(j + 1)).doubleValue();
                } else {
                    if ((noBenefit) && (item.getEsaiEsdd().getEsddDataType().intValue() >= 10)
                            && (item.getEsaiEsdd().getEsddDataType().intValue() <= 16))
                        result = getFieldValue("A" + String.valueOf(j + 1)).doubleValue();
                    else
                        result = calculateFormula(item.getEsaiDataCalc());
                }
                switch (item.getEsaiDataRounding().intValue()) {
                case 1:
                    result = MyTools.ceil(result, 1);
                    break;
                case 2:
                    result = MyTools.ceil(result, 0);
                    break;
                case 3:
                    result = MyTools.round(result, 1);
                    break;
                case 4:
                    result = MyTools.round(result, 0);
                    break;
                case 5:
                    result = MyTools.floor(result, 1);
                    break;
                case 6:
                    result = MyTools.floor(result, 0);
                    break;
                default:
                    result = MyTools.round(result, 2);
                }
                DecimalFormat df = new DecimalFormat("#.00");
                BigDecimal value = new BigDecimal(df.format(result));

                if (this.fillDataMode == 0) {
                    Class ownerClass = this.salaryConfig.getClass();
                    Method method = ownerClass.getMethod("setEscColumn" + (j + 1),
                                                         new Class[] { BigDecimal.class });
                    method.invoke(this.salaryConfig, new Object[] { value });
                } else {
                    Class ownerClass = this.salaryPay.getClass();
                    Method method = ownerClass.getMethod("setEspColumn" + (j + 1),
                                                         new Class[] { BigDecimal.class });
                    method.invoke(this.salaryPay, new Object[] { value });
                }
            } catch (InterpreterException ex) {
                errorList.add(ex.getMessage());
            } catch (Exception e) {
                errorList.add("公式：" + item.getEsaiDataCalc() + "配置错误，所在行数：" + (j + 1));
            }
        }

        if (!errorList.isEmpty()) {
            throw new InterpreterException(errorList);
        }
        return true;
    }

    private double calculateFormula(String formula) throws InterpreterException {
        double result = 0.0D;
        formula = removeBrackets(formula);

        int i = seperateFormula(formula);
        if (i > 0) {
            result = calculateFormula(formula.substring(0, i));
            double resultRight = calculateFormula(formula.substring(i + 1, formula.length()));
            result = calculateBasic090414(result, resultRight, formula.charAt(i));
            return result;
        }

        if ((((formula.startsWith("A")) || (formula.startsWith("B")) || (formula.startsWith("C")) || (formula
                .startsWith("D"))))
                && (formula.length() > 1)
                && (formula.charAt(1) >= '0')
                && (formula.charAt(1) <= '9')) {
            result = getFieldValue(formula).doubleValue();
        } else if (((formula.charAt(0) >= '0') && (formula.charAt(0) <= '9'))
                || (formula.charAt(0) == '-') || (formula.charAt(0) == '.')) {
            try {
                result = Double.parseDouble(formula);
            } catch (Exception e) {
                throw new InterpreterException("数值" + formula + "解析错误！");
            }
        } else {
            if ((formula.startsWith("IF")) || (formula.startsWith("?"))) {
                if (formula.startsWith("?"))
                    formula = formula.substring(1);
                else
                    formula = formula.substring(2);
                if ((formula.startsWith("(")) && (formula.endsWith(")"))) {
                    String[] subformula = splitFormula(formula.substring(1, formula.length() - 1),
                                                       ',');
                    if ((subformula.length != 2) && (subformula.length != 3)) {
                        throw new InterpreterException("IF公式" + formula + "参数个数不正确！");
                    }
                    if (getValidateFlag() == 1) {
                        double resultLeft = calculateFormula(subformula[1]);
                        double resultRight;
                        if (subformula.length == 2)
                            resultRight = 0.0D;
                        else
                            resultRight = calculateFormula(subformula[2]);
                        if (calculateBool090409(subformula[0]).booleanValue())
                            return resultLeft;
                        return resultRight;
                    }

                    if (calculateBool090409(subformula[0]).booleanValue()) {
                        return calculateFormula(subformula[1]);
                    }
                    if (subformula.length == 2)
                        return 0.0D;
                    return calculateFormula(subformula[2]);
                }

                throw new InterpreterException("IF公式" + formula + "语法不正确！");
            }

            if (formula.startsWith("ROUND")) {
                formula = formula.substring(5);
                if ((formula.startsWith("(")) && (formula.endsWith(")"))) {
                    int indexsplit = formula.lastIndexOf(",");
                    if (indexsplit != -1) {
                        result = calculateFormula(formula.substring(1, indexsplit));
                        int roundupFactor = Integer.parseInt(formula
                                .substring(indexsplit + 1, formula.length() - 1));
                        return result = MyTools.round(result, roundupFactor);
                    }
                }

                throw new InterpreterException("ROUND公式" + formula + "语法不正确！");
            }

            if (formula.startsWith("ROUNDUP")) {
                formula = formula.substring(7);
                if ((formula.startsWith("(")) && (formula.endsWith(")"))) {
                    int indexsplit = formula.lastIndexOf(",");
                    if (indexsplit != -1) {
                        result = calculateFormula(formula.substring(1, indexsplit));
                        int roundupFactor = Integer.parseInt(formula
                                .substring(indexsplit + 1, formula.length() - 1));
                        return result = MyTools.ceil(result, roundupFactor);
                    }
                }

                throw new InterpreterException("ROUNDUP公式" + formula + "语法不正确！");
            }

            if (formula.startsWith("ROUNDDOWN")) {
                formula = formula.substring(9);
                if ((formula.startsWith("(")) && (formula.endsWith(")"))) {
                    int indexsplit = formula.lastIndexOf(",");
                    if (indexsplit != -1) {
                        result = calculateFormula(formula.substring(1, indexsplit));
                        int roundupFactor = Integer.parseInt(formula
                                .substring(indexsplit + 1, formula.length() - 1));
                        return result = MyTools.floor(result, roundupFactor);
                    }
                }

                throw new InterpreterException("ROUNDDOWN公式" + formula + "语法不正确！");
            }

            if (formula.startsWith("CEILING")) {
                formula = formula.substring(7);
                if ((formula.startsWith("(")) && (formula.endsWith(")"))) {
                    int indexsplit = formula.lastIndexOf(",");
                    result = calculateFormula(formula.substring(1, indexsplit));
                    if (indexsplit != -1) {
                        double ceilingFactor = Double.parseDouble(formula
                                .substring(indexsplit + 1, formula.length() - 1));
                        result = Math.ceil(result / ceilingFactor) * ceilingFactor;
                        return result;
                    }
                }

                throw new InterpreterException("CEILING公式" + formula + "语法不正确！");
            }

            if (formula.startsWith("MAX")) {
                formula = formula.substring(3);
                if ((formula.startsWith("(")) && (formula.endsWith(")"))) {
                    String[] subformula = splitFormula(formula.substring(1, formula.length() - 1),
                                                       ',');
                    if (subformula.length < 2) {
                        throw new InterpreterException("MAX公式" + formula + "至少有两个以上参数！");
                    }
                    double resultMax = calculateFormula(subformula[0]);
                    for (int j = 1; j < subformula.length; ++j) {
                        result = calculateFormula(subformula[j]);
                        if (result <= resultMax)
                            continue;
                        resultMax = result;
                    }
                    return resultMax;
                }

                throw new InterpreterException("MAX公式" + formula + "语法不正确！");
            }

            if (formula.startsWith("MIN")) {
                formula = formula.substring(3);
                if ((formula.startsWith("(")) && (formula.endsWith(")"))) {
                    String[] subformula = splitFormula(formula.substring(1, formula.length() - 1),
                                                       ',');
                    if (subformula.length < 2) {
                        throw new InterpreterException("MIN公式" + formula + "至少有两个以上参数！");
                    }
                    double resultMin = calculateFormula(subformula[0]);
                    for (int j = 1; j < subformula.length; ++j) {
                        result = calculateFormula(subformula[j]);
                        if (result >= resultMin)
                            continue;
                        resultMin = result;
                    }
                    return resultMin;
                }

                throw new InterpreterException("MIN公式" + formula + "语法不正确！");
            }

            if (formula.startsWith("$")) {
                formula = formula.substring(1);
                if ((formula.startsWith("(")) && (formula.endsWith(")"))) {
                    String[] subformula = splitFormula(formula.substring(1, formula.length() - 1),
                                                       ',');
                    if ((subformula.length != 1) && (subformula.length != 2)) {
                        throw new InterpreterException("基数自定义所得税$公式" + formula + "参数个数不正确！");
                    }
                    double incomeBfTax = calculateFormula(subformula[0]);
                    if (subformula.length == 2) {
                        this.sysConfigVarDef.setTaxBase(Double
                                .valueOf(calculateFormula(subformula[1])));
                    }
                    result = MyTools.getTaxbyIncome(incomeBfTax, this.sysConfigVarDef);
                    return result;
                }

                throw new InterpreterException("所得税$公式" + formula + "语法不正确！");
            }

            if (formula.startsWith("L$")) {
                formula = formula.substring(2);
                if ((formula.startsWith("(")) && (formula.endsWith(")"))) {
                    String[] subformula = splitFormula(formula.substring(1, formula.length() - 1),
                                                       ',');
                    if ((subformula.length != 1) && (subformula.length != 2)) {
                        throw new InterpreterException("劳务费所得税$公式" + formula + "参数个数不正确！");
                    }
                    double incomeBfTax = calculateFormula(subformula[0]);
                    if (subformula.length == 2) {
                        double base = calculateFormula(subformula[1]);
                        result = MyTools.getTaxbyLWIncome(incomeBfTax, Double.valueOf(base));
                    } else {
                        result = MyTools.getTaxbyLWIncome(incomeBfTax, null);
                    }
                    return result;
                }

                throw new InterpreterException("劳务费所得税$公式" + formula + "语法不正确！");
            }

            if ((formula.startsWith("N$")) || (formula.startsWith("~"))) {
                if (formula.startsWith("~"))
                    formula = formula.substring(1);
                else
                    formula = formula.substring(2);
                if ((formula.startsWith("(")) && (formula.endsWith(")"))) {
                    String[] subformula = splitFormula(formula.substring(1, formula.length() - 1),
                                                       ',');
                    if (subformula.length != 2) {
                        throw new InterpreterException("年终奖所得税$公式" + formula + "参数个数不正确！");
                    }
                    float salaryOfMonth = new Float(calculateFormula(subformula[0])).floatValue();
                    float yearEndReward = new Float(calculateFormula(subformula[1])).floatValue();
                    result = MyTools.getTaxbyYearEndReward(salaryOfMonth, yearEndReward,
                                                           this.sysConfigVarDef);
                    return result;
                }

                throw new InterpreterException("年终奖所得税$公式" + formula + "语法不正确！");
            }

            if (formula.startsWith("#")) {
                formula = formula.substring(1);
                if ((formula.startsWith("(")) && (formula.endsWith(")"))) {
                    String[] subformula = splitFormula(formula.substring(1, formula.length() - 1),
                                                       ',');
                    if (subformula.length != 3) {
                        throw new InterpreterException("上下限#公式" + formula + "参数个数不正确！");
                    }
                    result = calculateFormula(subformula[0]);
                    double limithigh = calculateFormula(subformula[1]);
                    double limitlow = calculateFormula(subformula[2]);
                    if (limithigh < limitlow) {
                        double swap = limithigh;
                        limithigh = limitlow;
                        limitlow = swap;
                    }
                    if (result > limithigh)
                        return limithigh;
                    if (result < limitlow)
                        return limitlow;
                    return result;
                }

                throw new InterpreterException("上下限#公式" + formula + "语法不正确！");
            }

            if (formula.startsWith("INT")) {
                formula = formula.substring(3);
                if ((formula.startsWith("(")) && (formula.endsWith(")"))) {
                    result = calculateFormula(formula.substring(1, formula.length() - 1));
                    return Math.ceil(result);
                }

                throw new InterpreterException("INT公式" + formula + "语法不正确！");
            }

            if (formula.startsWith("SIGN")) {
                formula = formula.substring(4);
                if ((formula.startsWith("(")) && (formula.endsWith(")"))) {
                    result = calculateFormula(formula.substring(1, formula.length() - 1));
                    return Math.signum(result);
                }

                throw new InterpreterException("SIGN公式" + formula + "语法不正确！");
            }

            if (formula.startsWith("ABS")) {
                formula = formula.substring(3);
                if ((formula.startsWith("(")) && (formula.endsWith(")"))) {
                    result = calculateFormula(formula.substring(1, formula.length() - 1));
                    return Math.abs(result);
                }

                throw new InterpreterException("ABS公式" + formula + "语法不正确！");
            }

            if (formula.startsWith("MOD")) {
                formula = formula.substring(3);
                if ((formula.startsWith("(")) && (formula.endsWith(")"))) {
                    int indexsplit = formula.lastIndexOf(",");
                    if (indexsplit != -1) {
                        result = calculateFormula(formula.substring(1, indexsplit));
                        double modFactor = Double.parseDouble(formula
                                .substring(indexsplit + 1, formula.length() - 1));
                        result -= Math.floor(result / modFactor) * modFactor;
                        return result;
                    }
                }

                throw new InterpreterException("MOD公式" + formula + "语法不正确！");
            }

            throw new InterpreterException("公式字符串" + formula + "解析错误！");
        }
        return result;
    }

    private double calculateBasic090414(double result, double result1, char oper)
            throws InterpreterException {
        switch (oper) {
        case '^':
            return pow(result, result1).doubleValue();
        case '*':
            return result * result1;
        case '+':
            return result + result1;
        case '-':
            return result - result1;
        case '/':
            if (result1 == 0.0D)
                return 0.0D;
            return result / result1;
        }
        throw new InterpreterException("算术运算符" + oper + "错误！");
    }

    private Boolean calculateBool090409(String formula) throws InterpreterException {
        formula = removeBrackets(formula);
        boolean BoolResult = false;

        if (formula.indexOf('*') != -1) {
            String[] subformula = splitFormula(formula, '*');
            BoolResult = calculateBool090409(subformula[0]).booleanValue();
            for (int i = 1; i < subformula.length; ++i)
                if ((subformula[i].startsWith("AND(")) && (subformula[i].endsWith(")"))) {
                    BoolResult = (BoolResult)
                            && (calculateBool090409(subformula[i].substring(4, subformula[i]
                                    .length() - 1)).booleanValue());
                } else if ((subformula[i].startsWith("OR(")) && (subformula[i].endsWith(")"))) {
                    BoolResult = (BoolResult)
                            || (calculateBool090409(subformula[i].substring(3, subformula[i]
                                    .length() - 1)).booleanValue());
                } else {
                    throw new InterpreterException("IF逻辑表达式" + subformula[i] + "语法不正确！");
                }
            return Boolean.valueOf(BoolResult);
        }

        if ((formula.startsWith("NOT(")) && (formula.endsWith(")"))) {
            return Boolean.valueOf(!calculateBool090409(formula.substring(4, formula.length() - 1))
                    .booleanValue());
        }

        int symbolLength = 1;
        int symbolIndex = 0;
        String symbol;
        if (formula.indexOf("<=") > 0) {
            symbol = "<=";
            symbolLength = 2;
            symbolIndex = formula.indexOf("<");
        } else if (formula.indexOf(">=") > 0) {
            symbol = ">=";
            symbolLength = 2;
            symbolIndex = formula.indexOf(">");
        } else if (formula.indexOf("==") > 0) {
            symbol = "==";
            symbolLength = 2;
            symbolIndex = formula.indexOf("=");
        } else if (formula.indexOf("<>") > 0) {
            symbol = "<>";
            symbolLength = 2;
            symbolIndex = formula.indexOf("<");
        } else if (formula.indexOf(">") > 0) {
            symbol = ">";
            symbolIndex = formula.indexOf(">");
        } else if (formula.indexOf("<") > 0) {
            symbol = "<";
            symbolIndex = formula.indexOf("<");
        } else {
            throw new InterpreterException("IF逻辑表达式" + formula + "语法不正确！");
        }
        double left = calculateFormula(formula.substring(0, symbolIndex));
        double right = calculateFormula(formula.substring(symbolIndex + symbolLength));

        if (((">".equals(symbol)) && (left > right)) || (("<".equals(symbol)) && (left < right))
                || ((">=".equals(symbol)) && (left >= right))
                || (("<=".equals(symbol)) && (left <= right))
                || (("==".equals(symbol)) && (left == right))
                || (("<>".equals(symbol)) && (left != right))) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    private String[] splitFormula(String formula, char delima) throws InterpreterException {
        int blankets = 0;
        int subindex = 0;

        StringBuffer subbuf = new StringBuffer();
        StringBuffer subString = new StringBuffer();

        for (int i = 0; i < formula.length(); ++i) {
            char charone = formula.charAt(i);

            if (charone == '(') {
                ++blankets;
            } else if (charone == ')') {
                --blankets;
            } else if ((charone == delima) && (blankets == 0)) {
                subbuf.append(subString);
                ++subindex;
                subbuf.append("\r");
                subString = new StringBuffer();
                if (subindex < 8)
                    continue;
                throw new InterpreterException("公式参数最多不能超过8个！");
            }

            subString = subString.append(charone);
        }
        if (subString != null) {
            subbuf.append(subString);
        }
        String[] subformula = subbuf.toString().split("\r");
        return subformula;
    }

    private int seperateFormula(String formula) throws InterpreterException {
        int blankets = 0;
        int subindex = 0;
        int subindexPow = 0;
        int subindexMD = 0;
        int subindexAS = 0;

        for (subindex = 0; subindex < formula.length(); ++subindex) {
            char charone = formula.charAt(subindex);
            if (charone == '(') {
                ++blankets;
            } else if (charone == ')') {
                --blankets;
            } else {
                if ((charone != '^') && (charone != '*') && (charone != '+') && (charone != '-')
                        && (charone != '/')) {
                    continue;
                }
                if (subindex == 0)
                    throw new InterpreterException("子公式" + formula + "缺少第一个参数！");
                if (subindex == formula.length() - 1)
                    throw new InterpreterException("子公式" + formula + "缺少第二个参数！");
                if (blankets != 0) {
                    continue;
                }
                if ((charone == '+') || (charone == '-')) {
                    subindexAS = subindex;
                } else if ((charone == '*') || (charone == '/')) {
                    subindexMD = subindex;
                } else {
                    if (charone != '^')
                        continue;
                    subindexPow = subindex;
                }
            }
        }
        if (blankets > 0)
            throw new InterpreterException("子公式" + formula + "缺少右括号')'！");
        if (blankets < 0)
            throw new InterpreterException("子公式" + formula + "缺少左括号'('！");

        if (subindexAS > 0)
            return subindexAS;

        if (subindexMD > 0)
            return subindexMD;

        if (subindexPow > 0)
            return subindexPow;

        return 0;
    }

    private String removeBrackets(String formula) throws InterpreterException {
        int blankets = 0;

        if ((formula.length() <= 2) || (formula.charAt(0) != '(')
                || (formula.charAt(formula.length() - 1) != ')')) {
            return formula;
        }

        for (int i = 1; i < formula.length() - 1; ++i) {
            char charone = formula.charAt(i);
            if (charone == '(')
                ++blankets;
            else if (charone == ')') {
                --blankets;
            }
            if (blankets < 0)
                return formula;
        }
        return removeBrackets(formula.substring(1, formula.length() - 1));
    }

    private Double getFieldValue(String field) throws InterpreterException {
        try {
            if (field.charAt(0) == 'A') {
                if (this.fillDataMode == 0) {
                    Class ownerClass = this.salaryConfig.getClass();
                    Method method = ownerClass.getMethod("getEscColumn" + field.substring(1),
                                                         new Class[0]);
                    Object object = method.invoke(this.salaryConfig, new Object[0]);
                    if (object == null)
                        return Double.valueOf(0.0D);
                    return Double.valueOf(((BigDecimal) object).doubleValue());
                }
                Class ownerClass = this.salaryPay.getClass();
                Method method = ownerClass.getMethod("getEspColumn" + field.substring(1),
                                                     new Class[0]);
                Object object = method.invoke(this.salaryPay, new Object[0]);
                if (object == null)
                    return Double.valueOf(0.0D);
                return Double.valueOf(((BigDecimal) object).doubleValue());
            }

            if (field.charAt(0) == 'B') {
                Class ownerClass = getDateVarDef().getClass();
                Method method = ownerClass.getMethod("get" + field, new Class[0]);
                return (Double) (Double) method.invoke(getDateVarDef(), new Object[0]);
            }
            if (field.charAt(0) == 'C') {
                Class ownerClass = getExaminVarDef().getClass();
                Method method = ownerClass.getMethod("get" + field, new Class[0]);
                return (Double) (Double) method.invoke(getExaminVarDef(), new Object[0]);
            }
            if (field.charAt(0) == 'D') {
                Class ownerClass = getBenefitVarDef().getClass();

                Method method = ownerClass.getMethod("get" + field, new Class[0]);

                return (Double) (Double) method.invoke(getBenefitVarDef(), new Object[0]);
            }

            Double result = new Double(field);
            return result;
        } catch (Exception e) {
            throw new InterpreterException("变量" + field + "不正确！");
        }
    }

    private static Double pow(double x, double n) throws InterpreterException {
        if (x == 0.0D) {
            if (n == 0.0D) {
                throw new InterpreterException("乘幂: 0^0 is undefined");
            }
            return Double.valueOf(0.0D);
        }
        if (n < 0.0D) {
            throw new InterpreterException("乘幂: Negative exponent");
        }
        if (n == 0.0D)
            return Double.valueOf(1.0D);
        if (n % 2.0D == 0.0D) {
            return pow(x * x, n / 2.0D);
        }
        return Double.valueOf(x * pow(x, n - 1.0D).doubleValue());
    }

    public Empsalaryconfig getSalaryConfig() {
        return this.salaryConfig;
    }

    public void setSalaryConfig(Empsalaryconfig salaryConfig) {
        this.salaryConfig = salaryConfig;
    }

    public DateVarDef getDateVarDef() {
        return this.dateVarDef;
    }

    public void setDateVarDef(DateVarDef dateVarDef) {
        this.dateVarDef = dateVarDef;
    }

    public ExaminVarDef getExaminVarDef() {
        return this.examinVarDef;
    }

    public void setExaminVarDef(ExaminVarDef examinVarDef) {
        this.examinVarDef = examinVarDef;
    }

    public BenefitVarDef getBenefitVarDef() {
        return this.benefitVarDef;
    }

    public void setBenefitVarDef(BenefitVarDef benefitVarDef) {
        this.benefitVarDef = benefitVarDef;
    }

    public SysConfigVarDef getSysConfigVarDef() {
        return this.sysConfigVarDef;
    }

    public void setSysConfigVarDef(SysConfigVarDef sysConfigVarDef) {
        this.sysConfigVarDef = sysConfigVarDef;
    }

    public int getValidateFlag() {
        return this.validateFlag;
    }

    public void setValidateFlag(int setValidateFlag) {
        this.validateFlag = setValidateFlag;
    }

    public int getFillDataMode() {
        return this.fillDataMode;
    }

    public void setFillDataMode(int fillDataMode) {
        this.fillDataMode = fillDataMode;
    }

    public Empsalarypay getSalaryPay() {
        return this.salaryPay;
    }

    public void setSalaryPay(Empsalarypay salaryPay) {
        this.salaryPay = salaryPay;
    }

    public Leavebalance getLeaveBalance() {
        return this.leaveBalance;
    }

    public void setLeavebalance(Leavebalance leaveBalance) {
        this.leaveBalance = leaveBalance;
    }
}