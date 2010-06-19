package com.hr.security.web.action;

import com.hr.security.domain.Client;
import com.hr.util.MyTools;
import java.util.Hashtable;

public class ClientLimit {
    public static Hashtable<String, String> getClientLimit(Client client) {
        if ((client.getClientStatus().intValue() == 7)
                || (client.getClientStatus().intValue() == 0))
            return domeHashtable();

        String limit = MyTools.vigenere(client.getClientLimit(), MyTools.getUpKey(client
                .getClientId(), MyTools.STRING), MyTools.DECRYPT_MODE);

        String[] message = limit.split("#");

        String empS = message[0].substring(4);
        if (!isDigit(empS))
            return domeHashtable();

        String[] userS = message[1].substring(5).split(",");
        if (userS.length > 3)
            return domeHashtable();
        String totalUsers = "0";
        for (String userNo : userS) {
            if (!isDigit(userNo))
                return domeHashtable();
            totalUsers = String.valueOf(Integer.parseInt(totalUsers) + Integer.parseInt(userNo));
        }

        String auth = message[2].substring(5);
        if (!isRightAuths(auth))
            return domeHashtable();

        Hashtable table = new Hashtable();

        if (message.length == 3) {
            table.put("dateLimit", "");
            table.put("countLimit", "");
        }

        if (message.length == 4) {
            table.put("dateLimit", message[3].substring(5));
            table.put("countLimit", "");
        }

        if (message.length == 5) {
            table.put("dateLimit", message[3].substring(5));
            table.put("countLimit", message[4].substring(6));
        }

        table.put("authLimit", auth);
        table.put("EMP", empS);

        table.put("USER", totalUsers);
        table.put("USERADM", userS[0]);
        if (userS.length > 1)
            table.put("USERMGR", userS[1]);
        if (userS.length > 2)
            table.put("USEREMP", userS[2]);
        return table;
    }

    public static Hashtable<String, String> domeHashtable() {
        Hashtable table = new Hashtable();
        table.put("dateLimit", "");
        table.put("countLimit", "");
        table
                .put("authLimit",
                     "1,2,3,4,5,11,12,13,14,15,31,32,33,34,35,61,71,72,73,81,82,83,84,85,86,87,88,89,91");
        table.put("EMP", "100");
        table.put("USER", "5");
        table.put("USERADM", "2");
        table.put("USERMGR", "2");
        table.put("USEREMP", "1");
        return table;
    }

    public static boolean isDigit(String str) {
        if (str == null)
            return false;
        int length = str.length();
        for (int i = 0; i < length; ++i) {
            char tempChar = str.charAt(i);
            if ((tempChar > '9') || (tempChar < '0'))
                return false;
        }
        return true;
    }

    public static boolean isRightAuths(String auth) {
        if (auth == null)
            return false;
        String[] auths = auth.split(",");
        for (int authsIndex = 0; authsIndex < auths.length; ++authsIndex) {
            String splitAuth = auths[authsIndex];
            if (!isDigit(splitAuth))
                return false;
        }
        return true;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.web.action.ClientLimit JD-Core Version: 0.5.4
 */