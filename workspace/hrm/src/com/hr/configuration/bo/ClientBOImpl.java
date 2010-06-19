package com.hr.configuration.bo;

import com.hr.compensation.domain.Empsalarydatadef;
import com.hr.configuration.dao.IClientDAO;
import com.hr.configuration.domain.ContractType;
import com.hr.configuration.domain.Department;
import com.hr.configuration.domain.Ecptype;
import com.hr.configuration.domain.Emailtemplate;
import com.hr.configuration.domain.Emptype;
import com.hr.configuration.domain.Infoclass;
import com.hr.configuration.domain.JobTitle;
import com.hr.configuration.domain.Jobgrade;
import com.hr.configuration.domain.Location;
import com.hr.configuration.domain.Sysconfig;
import com.hr.examin.domain.Leavetype;
import com.hr.examin.domain.Overtimetype;
import com.hr.profile.domain.Empaddconf;
import com.hr.profile.domain.Employee;
import com.hr.security.domain.Client;
import com.hr.security.domain.Role;
import com.hr.security.domain.Userinfo;
import com.hr.security.web.action.ClientLimit;
import com.hr.util.DatabaseSysConfigManager;
import com.hr.util.MyTools;
import com.hr.util.Pager;
import com.hr.util.StringUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ClientBOImpl implements IClientBO {
    private IClientDAO clientDao;
    public final String USERADM = "USERADM";
    public final String USERMGR = "USERMGR";
    public final String USEREMP = "USEREMP";
    public final String USER = "USER";
    public final String EMP = "EMP";

    public ClientBOImpl() {
    }

    public Client loadOneClient(String clientNo) {
        Object obj = this.clientDao.loadObject(Client.class, clientNo, null, new boolean[0]);
        if (obj != null)
            return (Client) obj;
        return null;
    }

    public List<Client> loadClients() {
        return this.clientDao.getObjects(Client.class, null);
    }

    public void insertClient(Client newClient) {
        this.clientDao.saveObject(newClient);
    }

    public void deleteClient(String clientNo) {
        Client client = loadOneClient(clientNo);
        if (client == null)
            return;
        this.clientDao.deleteObject(client);
    }

    public void updateClient(Client client) {
        Client clientTmp = loadOneClient(client.getId());
        clientTmp.setClientName(client.getClientName());
        clientTmp.setClientShortName(client.getClientShortName());
        clientTmp.setClientAddress(client.getClientAddress());
        clientTmp.setClientZip(client.getClientZip());
        clientTmp.setClientPhone(client.getClientPhone());
        clientTmp.setClientFax(client.getClientFax());
        clientTmp.setClientEmail(client.getClientEmail());
        clientTmp.setClientContactName(client.getClientContactName());
        clientTmp.setClientRemarks(client.getClientRemarks());
        clientTmp.setClientStatus(client.getClientStatus());
        if (client.getClientLimit() != null) {
            clientTmp.setClientLimit(client.getClientLimit());
        }
        this.clientDao.updateObject(clientTmp);
    }

    public String checkLimit(String limitType, int addNum) {
        int limit = 0;
        HttpSession session = ServletActionContext.getRequest().getSession();

        super.getClass();
        if (limitType.equals("EMP")) {
            String limitStr = session.getAttribute("EMP").toString();
            limit = Integer.parseInt(limitStr);
            if (addNum >= limit)
                return "overLimit";
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
            detachedCriteria.add(Restrictions.eq("empStatus", Integer.valueOf(1)));
            int exitNumber = this.clientDao.findRowCountByCriteria(detachedCriteria) + addNum;
            return (exitNumber <= limit) ? "success" : "overLimit";
        }

        String limitStr = session.getAttribute("USER").toString();
        limit = Integer.parseInt(limitStr);
        if (addNum >= limit)
            return "overLimit";
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Userinfo.class);
        detachedCriteria.add(Restrictions.eq("uiStatus", Integer.valueOf(1)));
        int exitNumber = this.clientDao.findRowCountByCriteria(detachedCriteria) + addNum;
        return (exitNumber <= limit) ? "success" : "overLimit";
    }

    public String checkUserLimit(Integer[] roleNo, int addNum) {
        Integer[] users = getCurrentUsers(Integer.valueOf(1));
        String userType = getRoleAuths(roleNo);

        HttpSession session = ServletActionContext.getRequest().getSession();

        int limit = Integer.parseInt(session.getAttribute("USER").toString());
        if (users[3].intValue() + addNum > limit)
            return "overLimitU";

        super.getClass();
        if (userType.equals(USERADM)) {
            limit = Integer.parseInt(session.getAttribute(USERADM).toString());
            if (users[0].intValue() + addNum > limit)
                return "overLimitUADM";
        } else {
            super.getClass();
            if (userType.equals("USERMGR")) {
                if (session.getAttribute("USERMGR") == null)
                    return "success";
                limit = Integer.parseInt(session.getAttribute("USERMGR").toString());
                if (users[1].intValue() + addNum > limit)
                    return "overLimitUMGR";
            }
        }
        return "success";
    }

    public String checkUserLimit(String userType, int addNum) {
        Integer[] users = getCurrentUsers(Integer.valueOf(1));

        HttpSession session = ServletActionContext.getRequest().getSession();

        int limit = Integer.parseInt(session.getAttribute("USER").toString());
        if (users[3].intValue() + addNum > limit)
            return "overLimit";

        super.getClass();
        if (userType.equals(USERADM)) {
            limit = Integer.parseInt(session.getAttribute(USERADM).toString());
            if (users[0].intValue() + addNum > limit)
                return "overLimit";
        }

        super.getClass();
        if (userType.equals("USERMGR")) {
            limit = Integer.parseInt(session.getAttribute("USERMGR").toString());
            if (users[1].intValue() + addNum > limit)
                return "overLimit";
        }

        return "success";
    }

    public Integer[] getCurrentUsers(Integer uiStatus) {
        DetachedCriteria detachedCriteria = DetachedCriteria
                .forClass(com.hr.security.domain.Userinfo.class);
        detachedCriteria.add(Restrictions.eq("uiStatus", uiStatus));
        List uiList = clientDao.findByCriteria(detachedCriteria);
        Integer users[] = { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0),
                Integer.valueOf(0) };
        users[3] = Integer.valueOf(uiList.size());
        for (Iterator i$ = uiList.iterator(); i$.hasNext();) {
            Userinfo ui = (Userinfo) i$.next();
            String authType = StringUtil.authStringToType(ui.getUiAuthDecrypt());
            if (authType == "USERADM") {
                Integer ainteger[] = users;
                Integer integer = ainteger[0];
                Integer integer3 = ainteger[0] = Integer.valueOf(ainteger[0].intValue() + 1);
                Integer _tmp = integer;
            } else if (authType == "USERMGR") {
                Integer ainteger1[] = users;
                Integer integer1 = ainteger1[1];
                Integer integer4 = ainteger1[1] = Integer.valueOf(ainteger1[1].intValue() + 1);
                Integer _tmp1 = integer1;
            } else {
                Integer ainteger2[] = users;
                Integer integer2 = ainteger2[2];
                Integer integer5 = ainteger2[2] = Integer.valueOf(ainteger2[2].intValue() + 1);
                Integer _tmp2 = integer2;
            }
        }

        return users;
    }

    public String getRoleAuths(Integer[] roleNo) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
        detachedCriteria.add(Restrictions.in("roleNo", roleNo));
        List<Role> roleList = this.clientDao.findByCriteria(detachedCriteria);

        if ((roleList == null) || (roleList.size() == 0))
            return "USEREMP";

        Set authSet = new HashSet();
        for (Role role : roleList) {
            Set authSetTemp = StringUtil.authStringToSet(role.getRoleAuthority());
            authSet.addAll(authSetTemp);
        }
        return StringUtil.authSetToType(authSet);
    }

    public String checkEmpImport(int addNum) {
        HttpSession session = ServletActionContext.getRequest().getSession();
        String limitStr = session.getAttribute("EMP").toString();
        int limit = Integer.parseInt(limitStr);
        if (addNum >= limit)
            return "overLimit";

        String clientModel = session.getAttribute("loginModel").toString();
        String clientNo = session.getAttribute("clientNo").toString();
        Client client = loadOneClient(clientNo);

        if ((clientModel.equals("single")) && (client.getClientStatus().intValue() == 7)) {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Employee.class);
            detachedCriteria.add(Restrictions.eq("empStatus", Integer.valueOf(1)));
            int exitNumber = this.clientDao.findRowCountByCriteria(detachedCriteria) + addNum;
            return (exitNumber > limit) ? "overLimit" : "success";
        }

        return (addNum > limit) ? "overLimit" : "success";
    }

    public IClientDAO getClientDao() {
        return this.clientDao;
    }

    public void setClientDao(IClientDAO clientDao) {
        this.clientDao = clientDao;
    }

    public List searchAllClient(Client client, Pager pager) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Client.class);
        if (client == null) {
            detachedCriteria.addOrder(Order.asc(Client.PROP_ID));
            splitPage(detachedCriteria, pager);
            return decryptLimit(this.clientDao.findByCriteria(detachedCriteria,
                                                              pager.getPageSize(), pager
                                                                      .getCurrentPage()));
        }

        if ((client.getId() != null) && (client.getId().length() > 0)) {
            detachedCriteria.add(Restrictions.like(Client.PROP_ID, "%" + client.getId() + "%"));
        }
        if ((client.getClientAddress() != null) && (client.getClientAddress().length() > 0)) {
            detachedCriteria.add(Restrictions.like(Client.PROP_CLIENT_ADDRESS, "%"
                    + client.getClientAddress() + "%"));
        }
        if ((client.getClientEmail() != null) && (client.getClientEmail().length() > 0)) {
            detachedCriteria.add(Restrictions.like(Client.PROP_CLIENT_EMAIL, "%"
                    + client.getClientEmail() + "%"));
        }
        if ((client.getClientFax() != null) && (client.getClientFax().length() > 0)) {
            detachedCriteria.add(Restrictions.like(Client.PROP_CLIENT_FAX, "%"
                    + client.getClientFax() + "%"));
        }
        if ((client.getClientName() != null) && (client.getClientName().length() > 0)) {
            detachedCriteria.add(Restrictions.like(Client.PROP_CLIENT_NAME, "%"
                    + client.getClientName() + "%"));
        }

        if ((client.getClientPhone() != null) && (client.getClientPhone().length() > 0)) {
            detachedCriteria.add(Restrictions.like(Client.PROP_CLIENT_PHONE, "%"
                    + client.getClientPhone() + "%"));
        }
        detachedCriteria.addOrder(Order.asc(Client.PROP_ID));
        splitPage(detachedCriteria, pager);
        return decryptLimit(this.clientDao.findByCriteria(detachedCriteria, pager.getPageSize(),
                                                          pager.getCurrentPage()));
    }

    private List decryptLimit(List list) {
        List newList = new ArrayList();
        Iterator itor = list.iterator();
        while (itor.hasNext()) {
            Client client = (Client) itor.next();
            String clientString = MyTools.vigenere(client.getClientLimit(), MyTools.getUpKey(client
                    .getId(), MyTools.STRING), MyTools.DECRYPT_MODE);

            client.setClientLimit(clientString);
            newList.add(client);
        }
        list = null;
        return newList;
    }

    public String updateLoginLimit(Client client) {
        Hashtable clientTable = ClientLimit.getClientLimit(client);
        String dateLimitString = (String) clientTable.get("dateLimit");
        String countLimitString = (String) clientTable.get("countLimit");
        String currentDate = MyTools.getcurrdate();
        if (((countLimitString.length() > 0) && (Integer.valueOf(countLimitString).intValue() <= 0))
                || ((dateLimitString.length() > 0) && (dateLimitString.compareTo(currentDate) < 0))) {
            client.setClientStatus(Integer.valueOf(7));
            updateClient(client);
            return "overLimit";
        }

        if (countLimitString.length() > 0) {
            int currentCount = Integer.valueOf(countLimitString).intValue();
            --currentCount;
            String limit = "EMP:" + (String) clientTable.get("EMP") + "#USER:"
                    + (String) clientTable.get("USER") + "#AUTH:"
                    + (String) clientTable.get("authLimit") + "#DATE:"
                    + (String) clientTable.get("dateLimit") + "#COUNT:"
                    + String.valueOf(currentCount);

            String limitEncrypt = MyTools.vigenere(limit, MyTools.getUpKey(client.getClientId(),
                                                                           MyTools.STRING),
                                                   MyTools.ENCRYPT_MODE);

            client.setClientLimit(limitEncrypt);
            updateClient(client);
        }
        return "normalLimit";
    }

    private void splitPage(DetachedCriteria detachedCriteria, Pager page) {
        int size = this.clientDao.findRowCountByCriteria(detachedCriteria);

        int pageSize = Integer.valueOf(
                                       DatabaseSysConfigManager.getInstance()
                                               .getProperty("sys.split.pages")).intValue();
        detachedCriteria.setProjection(null);

        page.init(size, pageSize);

        if ((page.getOperate() != null) && ("previous".equalsIgnoreCase(page.getOperate()))) {
            page.previous();
        }
        if ((page.getOperate() != null) && ("next".equalsIgnoreCase(page.getOperate()))) {
            page.next();
        }
        if ((page.getOperate() != null) && ("first".equalsIgnoreCase(page.getOperate()))) {
            page.first();
        }
        if ((page.getOperate() != null) && ("last".equalsIgnoreCase(page.getOperate())))
            page.last();
    }

    public void insertRegistClient(Client client, String auths) {
        String clientLimit = MyTools.vigenere(auths, MyTools.getUpKey(client.getClientId(),
                                                                      MyTools.STRING),
                                              MyTools.ENCRYPT_MODE);

        client.setClientLimit(clientLimit);
        client.setClientActivateTime(new Date());
        client.setClientServiceMonths(Integer.valueOf(3));

        client.setClientStatus(Integer.valueOf(1));
        this.clientDao.saveObject(client);
    }

    public void insertFirstUser(Employee employee, Client client, String password, String auth,
            String role) {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("loginModel", "mutiple");
        session.setAttribute("clientNo", client.getId());

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(JobTitle.class);

        List objList = this.clientDao.findByCriteria(detachedCriteria);
        JobTitle jobTitle = null;
        if ((objList != null) && (!objList.isEmpty())) {
            jobTitle = (JobTitle) objList.get(0);
        } else {
            jobTitle = new JobTitle();

            jobTitle.setJobtitleNameDesc("娉ㄥ唽鏃惰嚜鍔ㄦ坊鍔犮��");
            jobTitle.setJobtitleSortId(Integer.valueOf(0));
            jobTitle.setClientNo(client.getId());
            jobTitle.setJobtitleNeedGmApprove(Integer.valueOf(0));
            this.clientDao.saveObject(jobTitle);
        }

        detachedCriteria = DetachedCriteria.forClass(Department.class);
        detachedCriteria.add(Restrictions.eq("departmentName", employee.getEmpDeptNo()
                .getDepartmentName().trim()));
        objList = this.clientDao.findByCriteria(detachedCriteria);
        Department department = null;
        if ((objList != null) && (!objList.isEmpty())) {
            department = (Department) objList.get(0);
        } else {
            department = new Department();
            department.setDepartmentName(employee.getEmpDeptNo().getDepartmentName().trim());
            department.setDepartmentDesc("娉ㄥ唽鏃惰嚜鍔ㄦ坊鍔犮��");
            department.setDepartmentSortId(Integer.valueOf(0));
            department.setClientNo(client.getId());
            this.clientDao.saveObject(department);
        }
        employee.setEmpDeptNo(department);

        detachedCriteria = DetachedCriteria.forClass(Emptype.class);
        detachedCriteria.add(Restrictions.eq("emptypeName", employee.getEmpType().getEmptypeName()
                .trim()));
        objList = this.clientDao.findByCriteria(detachedCriteria);
        Emptype emptype = null;
        if ((objList != null) && (!objList.isEmpty())) {
            emptype = (Emptype) objList.get(0);
        } else {
            emptype = new Emptype();
            emptype.setEmptypeName(employee.getEmpType().getEmptypeName().trim());
            emptype.setEmptypeDesc("娉ㄥ唽鏃惰嚜鍔ㄦ坊鍔犮��");
            emptype.setEmptypeSortId(Integer.valueOf(0));
            emptype.setClientNo(client.getId());
            this.clientDao.saveObject(emptype);
        }
        employee.setEmpType(emptype);

        detachedCriteria = DetachedCriteria.forClass(Location.class);
        detachedCriteria.add(Restrictions.eq("locationName", employee.getEmpLocationNo()
                .getLocationName().trim()));
        objList = this.clientDao.findByCriteria(detachedCriteria);
        Location location = null;
        if ((objList != null) && (!objList.isEmpty())) {
            location = (Location) objList.get(0);
        } else {
            location = new Location();
            location.setLocationName(employee.getEmpLocationNo().getLocationName().trim());
            location.setLocationSortId(Integer.valueOf(0));
            location.setClientNo(client.getId());
            location.setLocationDesc("娉ㄥ唽鏃惰嚜鍔ㄦ坊鍔犮��");
            this.clientDao.saveObject(location);
        }
        employee.setEmpLocationNo(location);
        employee.setId(UUID.randomUUID().toString());
        employee.setEmpStatus(Integer.valueOf(1));

        if (employee.getManager() == null)
            employee.setManager(employee.getId());
        employee.setEmpImportByInterface(Integer.valueOf(0));
        employee.setEmpCreateBy(employee);
        employee.setEmpCreateTime(Calendar.getInstance().getTime());
        employee.setEmpLastChangeTime(Calendar.getInstance().getTime());
        employee.setClientNo(client.getId());

        this.clientDao.saveObject(employee);

        Userinfo user = new Userinfo();
        user.setId(employee.getId());
        user.setUiUsername(employee.getEmpDistinctNo());
        user.setUiReferenceId(user.getUiUsername());
        user.setUiPasswordEncrypt(StringUtil.encodePassword(password));
        user.setUiAuthEncrypt(auth);
        user.setUiRoleEncrypt(role);
        user.setUiStatus(Integer.valueOf(1));
        user.setUiLevelRestrict(Integer.valueOf(0));
        user.setUiCreateBy(employee);
        user.setUiCreateTime(new Date());
        user.setUiLastChangeBy(employee);
        user.setUiLastChangeTime(new Date());
        user.setClientNo(client.getId());
        user.setEmployee(employee);
        this.clientDao.saveObject(user);
    }

    public String searchMaxClientId() {
        List oldClientList = this.clientDao.exeHqlList("select max(" + Client.PROP_ID
                + ") from Client");
        String oldClientId = oldClientList.get(0).toString();

        int maxClientId = Integer.parseInt(oldClientId) + 1;
        if (maxClientId < 101)
            maxClientId = 101;
        String clientId = String.valueOf(maxClientId);

        int length = oldClientId.length() - clientId.length();
        for (int idLength = 0; idLength < length; ++idLength) {
            clientId = "0" + clientId;
        }
        return clientId;
    }

    public void insertDefaultTables(String clientModel, String clientNo) {
        HttpSession session = ServletActionContext.getRequest().getSession();

        session.setAttribute("loginModel", "mutiple");

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Department.class);
        session.setAttribute("clientNo", clientModel);
        List objList = this.clientDao.findByCriteria(detachedCriteria);
        session.setAttribute("clientNo", clientNo);
        while (!objList.isEmpty()) {
            Department department = (Department) objList.get(0);
            Department newDepartment = new Department();
            newDepartment.setDepartmentDesc(department.getDepartmentDesc());
            newDepartment.setDepartmentName(department.getDepartmentName());
            newDepartment.setDepartmentSortId(department.getDepartmentSortId());
            this.clientDao.saveObject(newDepartment);
            objList.remove(0);
        }

        detachedCriteria = DetachedCriteria.forClass(Ecptype.class);
        session.setAttribute("clientNo", clientModel);
        objList = this.clientDao.findByCriteria(detachedCriteria);
        session.setAttribute("clientNo", clientNo);
        while (!objList.isEmpty()) {
            Ecptype ecptype = (Ecptype) objList.get(0);
            Ecptype newEcptype = new Ecptype();
            newEcptype.setEcptypeDesc(ecptype.getEcptypeDesc());
            newEcptype.setEcptypeName(ecptype.getEcptypeName());
            newEcptype.setEcptypeSortId(ecptype.getEcptypeSortId());
            this.clientDao.saveObject(newEcptype);
            objList.remove(0);
        }

        detachedCriteria = DetachedCriteria.forClass(ContractType.class);
        session.setAttribute("clientNo", clientModel);
        objList = this.clientDao.findByCriteria(detachedCriteria);
        session.setAttribute("clientNo", clientNo);
        while (!objList.isEmpty()) {
            ContractType contracttype = (ContractType) objList.get(0);
            ContractType newContracttype = new ContractType();
            newContracttype.setEctDescription(contracttype.getEctDescription());
            newContracttype.setEctName(contracttype.getEctName());
            newContracttype.setEctSortId(contracttype.getEctSortId());
            this.clientDao.saveObject(newContracttype);
            objList.remove(0);
        }

        detachedCriteria = DetachedCriteria.forClass(Emailtemplate.class);
        session.setAttribute("clientNo", clientModel);
        objList = this.clientDao.findByCriteria(detachedCriteria);
        session.setAttribute("clientNo", clientNo);
        while (!objList.isEmpty()) {
            Emailtemplate emailtemplate = (Emailtemplate) objList.get(0);
            Emailtemplate newEmailtemplate = new Emailtemplate();
            newEmailtemplate.setEtContent(emailtemplate.getEtContent());
            newEmailtemplate.setEtNotes(emailtemplate.getEtNotes());
            newEmailtemplate.setEtStatus(emailtemplate.getEtStatus());
            newEmailtemplate.setEtTitle(emailtemplate.getEtTitle());
            newEmailtemplate.setEtTitleNo(emailtemplate.getEtTitleNo());
            this.clientDao.saveObject(newEmailtemplate);
            objList.remove(0);
        }

        detachedCriteria = DetachedCriteria.forClass(Empaddconf.class);
        session.setAttribute("clientNo", clientModel);
        objList = this.clientDao.findByCriteria(detachedCriteria);
        session.setAttribute("clientNo", clientNo);
        while (!objList.isEmpty()) {
            Empaddconf empaddconf = (Empaddconf) objList.get(0);
            Empaddconf newEmpaddconf = new Empaddconf();
            newEmpaddconf.setEadcComments(empaddconf.getEadcComments());
            newEmpaddconf.setEadcFieldName(empaddconf.getEadcFieldName());
            newEmpaddconf.setEadcFieldType(empaddconf.getEadcFieldType());
            newEmpaddconf.setEadcFieldValue(empaddconf.getEadcFieldValue());
            newEmpaddconf.setEadcSeq(empaddconf.getEadcSeq());
            newEmpaddconf.setEadcTableName(empaddconf.getEadcTableName());
            this.clientDao.saveObject(newEmpaddconf);
            objList.remove(0);
        }

        detachedCriteria = DetachedCriteria.forClass(Emptype.class);
        session.setAttribute("clientNo", clientModel);
        objList = this.clientDao.findByCriteria(detachedCriteria);
        session.setAttribute("clientNo", clientNo);
        while (!objList.isEmpty()) {
            Emptype emptype = (Emptype) objList.get(0);
            Emptype newEmptype = new Emptype();
            newEmptype.setEmptypeDesc(emptype.getEmptypeDesc());
            newEmptype.setEmptypeName(emptype.getEmptypeName());
            newEmptype.setEmptypeSortId(emptype.getEmptypeSortId());
            this.clientDao.saveObject(newEmptype);
            objList.remove(0);
        }

        detachedCriteria = DetachedCriteria.forClass(Infoclass.class);
        session.setAttribute("clientNo", clientModel);
        objList = this.clientDao.findByCriteria(detachedCriteria);
        session.setAttribute("clientNo", clientNo);
        while (!objList.isEmpty()) {
            Infoclass infoclass = (Infoclass) objList.get(0);
            Infoclass newInfoclass = new Infoclass();
            newInfoclass.setInfoclassName(infoclass.getInfoclassName());
            newInfoclass.setInfoclassSortId(infoclass.getInfoclassSortId());
            newInfoclass.setInfoclassStatus(infoclass.getInfoclassStatus());
            this.clientDao.saveObject(newInfoclass);
            objList.remove(0);
        }

        detachedCriteria = DetachedCriteria.forClass(Jobgrade.class);
        session.setAttribute("clientNo", clientModel);
        objList = this.clientDao.findByCriteria(detachedCriteria);
        session.setAttribute("clientNo", clientNo);
        while (!objList.isEmpty()) {
            Jobgrade jobgrade = (Jobgrade) objList.get(0);
            Jobgrade newJobgrade = new Jobgrade(UUID.randomUUID().toString(), jobgrade
                    .getJobGradeNo(), jobgrade.getJobGradeLevel(), jobgrade.getJobGradeMrp(),
                    jobgrade.getJobGradeName());

            newJobgrade.setJobGradeSortId(Integer.valueOf(0));
            this.clientDao.saveObject(newJobgrade);
            objList.remove(0);
        }

        detachedCriteria = DetachedCriteria.forClass(JobTitle.class);
        session.setAttribute("clientNo", clientModel);
        objList = this.clientDao.findByCriteria(detachedCriteria);
        session.setAttribute("clientNo", clientNo);
        while (!objList.isEmpty()) {
            JobTitle jobtitle = (JobTitle) objList.get(0);
            JobTitle newJobTitle = new JobTitle();
            newJobTitle.setJobtitleNo(jobtitle.getJobtitleNo());
            newJobTitle.setJobtitleName(jobtitle.getJobtitleName());
            newJobTitle.setJobtitleNameDesc(jobtitle.getJobtitleNameDesc());
            newJobTitle.setJobtitleNeedGmApprove(jobtitle.getJobtitleNeedGmApprove());
            newJobTitle.setJobtitleSortId(jobtitle.getJobtitleSortId());
            this.clientDao.saveObject(newJobTitle);
            objList.remove(0);
        }

        detachedCriteria = DetachedCriteria.forClass(Leavetype.class);
        session.setAttribute("clientNo", clientModel);
        objList = this.clientDao.findByCriteria(detachedCriteria);
        session.setAttribute("clientNo", clientNo);
        while (!objList.isEmpty()) {
            Leavetype leavetype = (Leavetype) objList.get(0);
            Leavetype newLeavetype = new Leavetype();
            newLeavetype.setLtSpecialCat(leavetype.getLtSpecialCat());
            newLeavetype.setLtDesc(leavetype.getLtDesc());
            newLeavetype.setLtInclHoliday(leavetype.getLtInclHoliday());
            newLeavetype.setLtMaxApplyDays(leavetype.getLtMaxApplyDays());
            newLeavetype.setLtName(leavetype.getLtName());
            newLeavetype.setLtSortId(leavetype.getLtSortId());
            this.clientDao.saveObject(newLeavetype);
            objList.remove(0);
        }

        detachedCriteria = DetachedCriteria.forClass(Location.class);
        session.setAttribute("clientNo", clientModel);
        objList = this.clientDao.findByCriteria(detachedCriteria);
        session.setAttribute("clientNo", clientNo);
        while (!objList.isEmpty()) {
            Location location = (Location) objList.get(0);
            Location newLocation = new Location();
            newLocation.setLocationDesc(location.getLocationDesc());
            newLocation.setLocationName(location.getLocationName());
            newLocation.setLocationSortId(location.getLocationSortId());
            this.clientDao.saveObject(newLocation);
            objList.remove(0);
        }

        detachedCriteria = DetachedCriteria.forClass(Overtimetype.class);
        session.setAttribute("clientNo", clientModel);
        objList = this.clientDao.findByCriteria(detachedCriteria);
        session.setAttribute("clientNo", clientNo);
        while (!objList.isEmpty()) {
            Overtimetype overtimetype = (Overtimetype) objList.get(0);
            Overtimetype newOvertimetype = new Overtimetype();
            newOvertimetype.setOtDesc(overtimetype.getOtDesc());
            newOvertimetype.setOtHourRate(overtimetype.getOtHourRate());
            newOvertimetype.setOtName(overtimetype.getOtName());
            newOvertimetype.setOtOverLimit(overtimetype.getOtOverLimit());
            newOvertimetype.setOtSortId(overtimetype.getOtSortId());
            this.clientDao.saveObject(newOvertimetype);
            objList.remove(0);
        }

        detachedCriteria = DetachedCriteria.forClass(Role.class);
        session.setAttribute("clientNo", clientModel);
        objList = this.clientDao.findByCriteria(detachedCriteria);
        session.setAttribute("clientNo", clientNo);
        while (!objList.isEmpty()) {
            Role role = (Role) objList.get(0);
            Role newRole = new Role();
            newRole.setRoleAuthority(role.getRoleAuthority());
            newRole.setRoleDesc(role.getRoleDesc());
            newRole.setRoleNo(role.getRoleNo());
            newRole.setRoleName(role.getRoleName());
            newRole.setRoleSortId(role.getRoleSortId());
            this.clientDao.saveObject(newRole);
            objList.remove(0);
        }

        detachedCriteria = DetachedCriteria.forClass(Empsalarydatadef.class);
        session.setAttribute("clientNo", clientModel);
        objList = this.clientDao.findByCriteria(detachedCriteria);
        session.setAttribute("clientNo", clientNo);
        while (!objList.isEmpty()) {
            Empsalarydatadef empsalarydatadef = (Empsalarydatadef) objList.get(0);
            Empsalarydatadef newEmpsalarydatadef = new Empsalarydatadef();
            newEmpsalarydatadef.setEsddName(empsalarydatadef.getEsddName());
            newEmpsalarydatadef.setEsddDesc(empsalarydatadef.getEsddDesc());
            newEmpsalarydatadef.setEsddDataType(empsalarydatadef.getEsddDataType());
            newEmpsalarydatadef.setEsddDataIsCalc(empsalarydatadef.getEsddDataIsCalc());
            newEmpsalarydatadef.setEsddDataRounding(empsalarydatadef.getEsddDataRounding());
            newEmpsalarydatadef.setEsddDataLength(empsalarydatadef.getEsddDataLength());
            newEmpsalarydatadef.setEsddSortId(empsalarydatadef.getEsddSortId());
            newEmpsalarydatadef.setEsddDataOutput(empsalarydatadef.getEsddDataOutput());
            this.clientDao.saveObject(newEmpsalarydatadef);
            objList.remove(0);
        }

        detachedCriteria = DetachedCriteria.forClass(Sysconfig.class);
        session.setAttribute("clientNo", clientModel);
        objList = this.clientDao.findByCriteria(detachedCriteria);
        session.setAttribute("clientNo", clientNo);
        while (!objList.isEmpty()) {
            Sysconfig sysconfig = (Sysconfig) objList.get(0);
            Sysconfig newSysconfig = new Sysconfig();
            newSysconfig.setSysconfigId(UUID.randomUUID().toString());
            newSysconfig.setSysconfigKey(sysconfig.getSysconfigKey());
            newSysconfig.setSysconfigValue(sysconfig.getSysconfigValue());
            this.clientDao.saveObject(newSysconfig);
            objList.remove(0);
        }

        Sysconfig newSysconfig = new Sysconfig();
        newSysconfig.setSysconfigId(UUID.randomUUID().toString());
        newSysconfig.setSysconfigKey("sys.file.path");
        newSysconfig.setSysconfigValue(clientNo + "/file");
        this.clientDao.saveObject(newSysconfig);

        session.setAttribute("clientNo", null);
    }

    public Client loadOneClientByClientId(String clientId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Client.class);
        detachedCriteria.add(Restrictions.eq(Client.PROP_CLIENT_ID, clientId));
        List result = this.clientDao.findByCriteria(detachedCriteria);
        if ((result == null) || (result.isEmpty()))
            return null;
        return (Client) result.get(0);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.configuration.bo.ClientBOImpl JD-Core Version: 0.5.4
 */