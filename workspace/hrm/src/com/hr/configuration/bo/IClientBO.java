package com.hr.configuration.bo;

import com.hr.profile.domain.Employee;
import com.hr.security.domain.Client;
import com.hr.util.Pager;
import java.util.List;

public abstract interface IClientBO {
    public abstract Client loadOneClient(String paramString);

    public abstract List<Client> loadClients();

    public abstract List searchAllClient(Client paramClient, Pager paramPager);

    public abstract void insertClient(Client paramClient);

    public abstract void deleteClient(String paramString);

    public abstract void updateClient(Client paramClient);

    public abstract String checkLimit(String paramString, int paramInt);

    public abstract String checkUserLimit(Integer[] paramArrayOfInteger, int paramInt);

    public abstract String checkUserLimit(String paramString, int paramInt);

    public abstract String checkEmpImport(int paramInt);

    public abstract String updateLoginLimit(Client paramClient);

    public abstract void insertRegistClient(Client paramClient, String paramString);

    public abstract String searchMaxClientId();

    public abstract void insertFirstUser(Employee paramEmployee, Client paramClient,
            String paramString1, String paramString2, String paramString3);

    public abstract Client loadOneClientByClientId(String paramString);

    public abstract void insertDefaultTables(String paramString1, String paramString2);
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.configuration.bo.IClientBO JD-Core Version: 0.5.4
 */