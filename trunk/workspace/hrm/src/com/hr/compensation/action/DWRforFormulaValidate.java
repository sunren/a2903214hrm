package com.hr.compensation.action;

import com.hr.base.DWRUtil;
import com.hr.base.Status;
import com.hr.compensation.domain.Empsalaryconfig;
import com.hr.util.Interpreter;
import com.hr.util.StringUtil;

public class DWRforFormulaValidate extends CompAction implements Status {
    private static final long serialVersionUID = 1L;

    public String validateFormula(String formula) {
        if ("error".equalsIgnoreCase(DWRUtil.checkAuth("acctItemDef", "execute"))) {
            return StringUtil.message(this.msgNoAuth, new Object[] { "noauth" });
        }
        Interpreter interpreter = new Interpreter();
        String info = interpreter.formulaValidate(formula, new Empsalaryconfig());
        if (!"SUCC".equals(info))
            return StringUtil.message(this.msgFormulaIllegal, new Object[] { formula, info });

        return StringUtil.message(this.msgFormulaLegal, new Object[] { "SUCC", formula });
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.compensation.action.DWRforFormulaValidate JD-Core Version: 0.5.4
 */