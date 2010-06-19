package com.hr.profile.domain;

import com.hr.profile.domain.base.BaseEmphistposition;
import java.util.Date;

public class Emphistposition extends BaseEmphistposition {
    private static final long serialVersionUID = 1L;

    public Emphistposition() {
    }

    public Emphistposition(String id) {
        super(id);
    }

    public Emphistposition(String id, Employee ehposEmpNo, Position ehposPositionNo,
            Date ehposValidFrom, Integer ehposIsLatest) {
        super(id, ehposEmpNo, ehposPositionNo, ehposValidFrom, ehposIsLatest);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.domain.Emphistposition JD-Core Version: 0.5.4
 */