package com.hr.security.web.action;

import com.hr.base.BaseAction;
import com.hr.configuration.bo.IClientBO;
import com.hr.security.domain.Client;
import com.hr.spring.SpringBeanFactory;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import org.apache.commons.validator.GenericValidator;

public class RegistClientAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private Client client;

    public String execute() throws Exception {
        IClientBO clientBo = (IClientBO) SpringBeanFactory.getBean("clientBo");

        this.client.setId(clientBo.searchMaxClientId());
        UUID uuid = UUID.randomUUID();
        this.client.setClientId(uuid.toString());

        clientBo.insertRegistClient(this.client,
                                    "EMP:100#USER:25#AUTH:1,2,3,4,5,11,12,13,14,15,16,71,72,73,81,82,85,86#DATE:"
                                            + getDateString());
        return "success";
    }

    private String getDateString() {
        Calendar cal = Calendar.getInstance();
        cal.add(2, 3);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    }

    public void validate() {
        super.validate();
        if ((this.client.getClientName() == null) || (this.client.getClientName().length() < 1)) {
            addFieldError("client.clientName", "必填项！");
        }

        if ((this.client.getClientPhone() == null) || (this.client.getClientPhone().length() < 1)) {
            addFieldError("client.clientPhone", "必填项！");
        } else if (!isPhone(this.client.getClientPhone())) {
            addFieldError("client.clientPhone", "格式不符合要求！");
        }
        if ((this.client.getClientContactName() == null)
                || (this.client.getClientContactName().length() < 1)) {
            addFieldError("client.clientContactName", "必填项！");
        }

        if ((this.client.getClientEmail() == null) || (this.client.getClientEmail().length() < 1)) {
            addFieldError("client.clientEmail", "必填项！");
        } else if (!isEmail(this.client.getClientEmail())) {
            addFieldError("client.clientEmail", "格式不符合要求！");
        }
        if ((this.client.getClientZip() != null) && (this.client.getClientZip().length() > 1)
                && (!isZip(this.client.getClientZip())))
            addFieldError("client.clientZip", "格式不符合要求！");
    }

    private boolean isPhone(String value) {
        return value.matches("^([0|1](\\d){1,3}[ ]?)?([-]?((\\d)|[ ]){1,12})(-(\\d){3,4})?$");
    }

    private boolean isZip(String value) {
        if (value.length() < 6)
            return false;
        return isNumber(value);
    }

    private boolean isNumber(String value) {
        for (int i = 0; i < value.length(); ++i) {
            if ((value.charAt(i) > '9') || (value.charAt(i) < '0'))
                return false;
        }
        return true;
    }

    private boolean isEmail(String value) {
        return GenericValidator.isEmail(value);
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.web.action.RegistClientAction JD-Core Version: 0.5.4
 */