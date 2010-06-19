package com.hr.profile.action;

import com.hr.configuration.domain.Department;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AssembleDeptList {
    protected List<Department> deptList;
    protected List<Department> allResultList;
    protected String queryDeptId;
    protected Integer queryDepth;
    protected Date queryDate;
    private int treeDepth;
    private Department queryDept;
    private int index;

    public AssembleDeptList() {
        this.deptList = new ArrayList();

        this.allResultList = new ArrayList();

        this.queryDept = null;

        this.index = 0;
    }

    public List<Department> getList() {
        getDeptList();

        this.index = 0;
        getMaxDepth(this.queryDeptId);

        setActualNum();

        setOrderNum();

        setDeptHead();

        plusSubDeptNum(this.queryDept);

        return this.deptList;
    }

    public Integer getTreeDepth() {
        return Integer.valueOf(this.treeDepth);
    }

    protected abstract void getDeptList();

    protected abstract void setActualNum();

    protected abstract void setOrderNum();

    protected abstract void setDeptHead();

    protected void prepareInitialDeptList() {
        if (this.queryDeptId == null)
            for (int i = 0; i < this.allResultList.size(); ++i) {
                Department dept = (Department) this.allResultList.get(i);
                if (dept.getDepartmentParentId() == null) {
                    this.queryDeptId = dept.getId();
                    this.queryDept = dept;
                    this.deptList.add(dept);
                    break;
                }
            }
        else {
            for (int i = 0; i < this.allResultList.size(); ++i) {
                Department dept = (Department) this.allResultList.get(i);
                if (dept.getId().equals(this.queryDeptId.trim())) {
                    this.queryDept = dept;
                    this.deptList.add(dept);
                    break;
                }
            }
        }

        if (this.queryDepth == null) {
            this.queryDepth = Integer.valueOf(1000);
        }
        getDeptListByQueryId(this.queryDept);
    }

    protected void getDeptListByQueryId(Department queryDept) {
        this.index += 1;
        if (this.index > this.queryDepth.intValue() - 1) {
            return;
        }
        for (int i = 0; i < this.allResultList.size(); ++i) {
            Department dept = (Department) this.allResultList.get(i);
            if ((dept.getDepartmentParentId() == null)
                    || (!dept.getDepartmentParentId().getId().equals(queryDept.getId())))
                continue;
            List subDepts = queryDept.getSubDeptList();
            if (subDepts == null) {
                subDepts = new ArrayList();
            }
            subDepts.add(dept);
            queryDept.setSubDeptList(subDepts);
            this.deptList.add(dept);
            getDeptListByQueryId(dept);
            this.index -= 1;
        }
    }

    private void getMaxDepth(String deptId) {
        this.index += 1;
        if (this.index >= this.treeDepth) {
            this.treeDepth = this.index;
        }
        for (int i = 0; i < this.allResultList.size(); ++i) {
            Department dept = (Department) this.allResultList.get(i);
            if ((dept.getDepartmentParentId() == null)
                    || (!dept.getDepartmentParentId().getId().equals(deptId)))
                continue;
            getMaxDepth(dept.getId());
            this.index -= 1;
        }
    }

    private void plusSubDeptNum(Department queryDept) {
        List<Department> subDepts = queryDept.getSubDeptList();
        if ((subDepts != null) && (subDepts.size() > 0)) {
            for (Department deptIn : subDepts) {
                plusSubDeptNum(deptIn);
            }
        }
        if (queryDept.getDepartmentParentId() != null) {
            Department parentDept = queryDept.getDepartmentParentId();
            parentDept.setActualNum(Integer.valueOf(parentDept.getActualNum().intValue()
                    + queryDept.getActualNum().intValue()));
            parentDept.setOrderNum(Integer.valueOf(parentDept.getOrderNum().intValue()
                    + queryDept.getOrderNum().intValue()));
        }
    }

    public String getQueryDeptId() {
        return this.queryDeptId;
    }

    public void setQueryDeptId(String queryDeptId) {
        this.queryDeptId = queryDeptId;
    }

    public Integer getQueryDepth() {
        return this.queryDepth;
    }

    public void setQueryDepth(Integer queryDepth) {
        this.queryDepth = queryDepth;
    }

    public Date getQueryDate() {
        return this.queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.profile.action.AssembleDeptList JD-Core Version: 0.5.4
 */