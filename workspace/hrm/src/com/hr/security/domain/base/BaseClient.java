package com.hr.security.domain.base;

import com.hr.base.BaseDomain;
import com.hr.security.domain.Client;
import java.io.Serializable;
import java.util.Date;

public abstract class BaseClient extends BaseDomain implements Serializable {
    public static String REF = "Client";
    public static String PROP_CLIENT_ACTIVATE_TIME = "clientActivateTime";
    public static String PROP_CLIENT_REMARKS = "clientRemarks";
    public static String PROP_CLIENT_EMAIL = "clientEmail";
    public static String PROP_CLIENT_NAME = "clientName";
    public static String PROP_CLIENT_ADDRESS = "clientAddress";
    public static String PROP_CLIENT_PHONE = "clientPhone";
    public static String PROP_CLIENT_SERVICE_TIMES = "clientServiceTimes";
    public static String PROP_CLIENT_STATUS = "clientStatus";
    public static String PROP_CLIENT_SERVICE_MONTHS = "clientServiceMonths";
    public static String PROP_CLIENT_ID = "clientId";
    public static String PROP_CLIENT_SHORT_NAME = "clientShortName";
    public static String PROP_CLIENT_FAX = "clientFax";
    public static String PROP_CLIENT_CONTACT_NAME = "clientContactName";
    public static String PROP_CLIENT_LIMIT = "clientLimit";
    public static String PROP_CLIENT_ZIP = "clientZip";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private String clientId;
    private String clientName;
    private String clientShortName;
    private String clientAddress;
    private String clientZip;
    private String clientPhone;
    private String clientFax;
    private String clientEmail;
    private String clientContactName;
    private Date clientActivateTime;
    private Integer clientServiceMonths = Integer.valueOf(0);
    private Integer clientServiceTimes = Integer.valueOf(0);
    private Integer clientStatus;
    private String clientLimit;
    private String clientRemarks;

    public BaseClient() {
        initialize();
    }

    public BaseClient(String id) {
        setId(id);
        initialize();
    }

    public BaseClient(String id, String clientLimit, String clientRemarks) {
        setId(id);
        setClientLimit(clientLimit);
        setClientRemarks(clientRemarks);
        initialize();
    }

    protected void initialize() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return this.clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientShortName() {
        return this.clientShortName;
    }

    public void setClientShortName(String clientShortName) {
        this.clientShortName = clientShortName;
    }

    public String getClientAddress() {
        return this.clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientZip() {
        return this.clientZip;
    }

    public void setClientZip(String clientZip) {
        this.clientZip = clientZip;
    }

    public String getClientPhone() {
        return this.clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientFax() {
        return this.clientFax;
    }

    public void setClientFax(String clientFax) {
        this.clientFax = clientFax;
    }

    public String getClientEmail() {
        return this.clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientContactName() {
        return this.clientContactName;
    }

    public void setClientContactName(String clientContactName) {
        this.clientContactName = clientContactName;
    }

    public Date getClientActivateTime() {
        return this.clientActivateTime;
    }

    public void setClientActivateTime(Date clientActivateTime) {
        this.clientActivateTime = clientActivateTime;
    }

    public Integer getClientServiceMonths() {
        return this.clientServiceMonths;
    }

    public void setClientServiceMonths(Integer clientServiceMonths) {
        this.clientServiceMonths = clientServiceMonths;
    }

    public Integer getClientServiceTimes() {
        return this.clientServiceTimes;
    }

    public void setClientServiceTimes(Integer clientServiceTimes) {
        this.clientServiceTimes = clientServiceTimes;
    }

    public Integer getClientStatus() {
        return this.clientStatus;
    }

    public void setClientStatus(Integer clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getClientLimit() {
        return this.clientLimit;
    }

    public void setClientLimit(String clientLimit) {
        this.clientLimit = clientLimit;
    }

    public String getClientRemarks() {
        return this.clientRemarks;
    }

    public void setClientRemarks(String clientRemarks) {
        this.clientRemarks = clientRemarks;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Client))
            return false;

        Client client = (Client) obj;
        if ((null == getId()) || (null == client.getId()))
            return false;
        return getId().equals(client.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (null == getId())
                return super.hashCode();

            String hashStr = super.getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.domain.base.BaseClient JD-Core Version: 0.5.4
 */