package com.hr.profile.bo;

import com.hr.configuration.domain.Statusconf;
import com.hr.profile.domain.Empcontract;
import com.hr.profile.domain.Employee;
import com.hr.profile.domain.Empquit;
import com.hr.profile.domain.Emptransfer;
import com.hr.profile.domain.Position;
import com.hr.profile.domain.SimpleEmployee;
import com.hr.report.action.profile.support.EmpSumReport;
import com.hr.util.Pager;
import com.jenkov.prizetags.tree.itf.ITree;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;

public abstract interface IEmployeeBo {
    public abstract Object exeHql(String paramString);

    public abstract Employee loadEmpByDistinctNo(String paramString);

    public abstract List exeHqlList(String paramString, int[] paramArrayOfInt);

    public abstract List exeHqlList(String paramString, String[] paramArrayOfString1,
            String[] paramArrayOfString2);

    public abstract List findByCriteria(DetachedCriteria paramDetachedCriteria);

    public abstract <T> void saveOrupdate(Collection<T> paramCollection);

    public abstract boolean saveOrUpdateEmpInfo(List<Employee> paramList, List<Empquit> paramList1,
            List<Empcontract> paramList2, List<Position> paramList3);

    public abstract <T> List<T> getObjects(Class<T> paramClass, String[] paramArrayOfString);

    public abstract List getEmployees();

    public abstract Employee loadEmp(Object paramObject, String[] paramArrayOfString);

    public abstract List<String> updateEmpByempInfo(Employee paramEmployee, String paramString);

    public abstract List<String> updateEmp(Employee paramEmployee, String paramString);

    public abstract List<String> deleteEmp(Employee paramEmployee);

    public abstract List<Employee> searchAndExportEmp(DetachedCriteria paramDetachedCriteria,
            Pager paramPager, String paramString);

    public abstract List<Statusconf> getEmpStatus();

    public abstract String saveCreateEmp(Employee paramEmployee1, Employee paramEmployee2,
            String paramString);

    public abstract List<String> insertEmp(Employee paramEmployee1, Employee paramEmployee2);

    public abstract List<Employee> getOrderEmps(String paramString1, String paramString2);

    public abstract List findEmpWithActiveStatus();

    public abstract List<Employee> findDirectEmps(String paramString);

    public abstract int countDirectEmpNumbers(String paramString, Integer paramInteger);

    public abstract int countAllEmpNumbers(String paramString);

    public abstract List findNouserEmps();

    public abstract List<Employee> searchEmpContract(String[] paramArrayOfString);

    public abstract List<Employee> searchEmpArray(String[] paramArrayOfString);

    public abstract List<String> batchUpdateOrDel(Employee paramEmployee, Pager paramPager,
            String paramString);

    public abstract List allSubnos(String paramString);

    public abstract boolean updatePassword(String paramString1, String paramString2);

    public abstract List getAllRootNodes();

    public abstract ITree generateAllTree(List paramList);

    public abstract List getPbActiveEmpNos(String paramString);

    public abstract List loadAll();

    public abstract List<String> allSubnosHighPerformance(String paramString);

    public abstract boolean checkEmpInCharge(Employee paramEmployee1, Employee paramEmployee2);

    public abstract List<Employee> loadEmpDetails(String[] paramArrayOfString);

    public abstract List<String> batchTransfer(Emptransfer paramEmptransfer,
            String[] paramArrayOfString, String paramString, Position paramPosition);

    public abstract Map<String, String> getEmployeeMap(Collection<String> paramCollection);

    public abstract List<Employee> getEmpsNeedCard(String[] paramArrayOfString);

    public abstract List<Employee> findAllActiveEmployees(String paramString);

    public abstract List<EmpSumReport> findDimissionEmpInStartAndEnd(String paramString1,
            String paramString2, String paramString3);

    public abstract List<Employee> findAllDimissionEmployees();

    public abstract List<SimpleEmployee> getSubManagers();

    public abstract List<SimpleEmployee> getSubManagers(String paramString);

    public abstract Integer findEmployeeMaxMachineNo();

    public abstract List<EmpSumReport> getEmpSumReport(String paramString1, String paramString2,
            String paramString3);

    public abstract List<EmpSumReport> getEmpNewReport(String paramString1, String paramString2,
            String paramString3);

    public abstract List<EmpSumReport> getEmpNewRateReport(String paramString1,
            String paramString2, String paramString3);

    public abstract List<EmpSumReport> getEmpNetRateReport(String paramString1,
            String paramString2, String paramString3);

    public abstract List<EmpSumReport> getEmpDimissionRateReport(String paramString1,
            String paramString2, String paramString3);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.profile.bo.IEmployeeBo JD-Core Version: 0.5.4
 */