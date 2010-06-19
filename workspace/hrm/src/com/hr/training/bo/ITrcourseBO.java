package com.hr.training.bo;

import com.hr.training.domain.Trcourse;
import com.hr.util.Pager;
import java.util.List;

public abstract interface ITrcourseBO {
    public abstract void addTrc(Trcourse paramTrcourse);

    public abstract void updateTrc(Trcourse paramTrcourse);

    public abstract void deleteTrc(String paramString);

    public abstract Trcourse loadTrc(String paramString);

    public abstract List loadAll();

    public abstract List<Trcourse> search(Trcourse paramTrcourse, Pager paramPager);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.training.bo.ITrcourseBO JD-Core Version: 0.5.4
 */