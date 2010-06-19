package com.hr.tool;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hr.configuration.bo.IClientBO;
import com.hr.security.domain.Client;
import com.hr.spring.SpringBeanFactory;
import com.hr.util.MyTools;

public class LicenseTool {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext( new String[] {"spring/applicationContext-compensation.xml",
                "spring/applicationContext-configuration.xml",
                "spring/applicationContext-examin.xml",
                "spring/applicationContext-help.xml",
                "spring/applicationContext-information.xml",
                "spring/applicationContext-io.xml",
                "spring/applicationContext-profile.xml",
                "spring/applicationContext-quartz.xml",
                "spring/applicationContext-recruitment.xml",
                "spring/applicationContext-report.xml",
                "spring/applicationContext-training.xml",
                "spring/applicationContext.xml"});
        IClientBO clientBo = (IClientBO) context.getBean("clientBo");        
        Client client = clientBo.loadOneClient("DEMO");
        String auths = "EMP:200#USER:50#AUTH:1,2,3,4,5,6,11,12,13,14,15,16,17,21,22,23,24,25,31,32,33,34,35,36,41,42,43,44,45,51,52,53,54,55,61,62,63,71,72,73,81,82,83,84,85,86,87,88,89,91,92";
        System.out.println(client.getClientId());
        String clientLimit = MyTools.vigenere(auths, MyTools
                .getUpKey(client.getClientId(), MyTools.STRING),
                                              MyTools.ENCRYPT_MODE);
        client.setClientLimit(clientLimit);
        clientBo.updateClient(client);
        
        Client clientSelect = clientBo.loadOneClient("DEMO");
        System.out.println(client.getClientId());
        System.out.println(clientSelect.getClientLimit());
        String decodeclientLimit = MyTools.vigenere(clientSelect.getClientLimit(), MyTools
                                              .getUpKey(client.getClientId(), MyTools.STRING),
                                                                            MyTools.DECRYPT_MODE);
        System.out.println(decodeclientLimit);
        System.exit(0);
    }
}
