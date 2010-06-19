package com.hr.base;

import com.hr.util.MyTools;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class DaoBean extends BaseDomain implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void setEncry(String fieldName, Object contentValue, String id, int flag) {
        if (contentValue == null)
            return;
        try {
            Class[] cls = { contentValue.getClass() };
            Method method = super.getClass().getMethod(
                                                       "set"
                                                               + fieldName.substring(0, 1)
                                                                       .toUpperCase()
                                                               + fieldName.substring(1, fieldName
                                                                       .length()), cls);
            String key = setUpKey(id, flag);
            if (flag == MyTools.STRING) {
                String encrypto = MyTools.vigenere(contentValue.toString(), key,
                                                   MyTools.ENCRYPT_MODE);
                Object[] objs = { encrypto };
                method.invoke(this, objs);
            } else if (flag == MyTools.CHINESE) {
                String encrypto = MyTools.vigenereChinese(contentValue.toString(), key,
                                                          MyTools.ENCRYPT_MODE);
                Object[] objs = { encrypto };
                method.invoke(this, objs);
            } else if (flag == MyTools.BIGDECIMAL) {
                String encryptString = ((BigDecimal) contentValue).toString();
                if (encryptString.indexOf('.') == -1)
                    encryptString = encryptString + ".00";
                BigDecimal enctrypUse = new BigDecimal(encryptString);
                BigDecimal encrypto = MyTools.vigenere(enctrypUse, key, MyTools.ENCRYPT_MODE);
                Object[] objs = { encrypto };
                method.invoke(this, objs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getDecry(String content, String id, int flag) {
        String key = setUpKey(id, flag);
        try {
            Method method = super.getClass().getMethod(
                                                       "get"
                                                               + content.substring(0, 1)
                                                                       .toUpperCase()
                                                               + content.substring(1, content
                                                                       .length()), new Class[0]);
            Object decrypto = method.invoke(this, new Object[0]);
            if ((decrypto != null) && (decrypto.getClass().equals(String.class))) {
                if (flag == MyTools.STRING)
                    decrypto = MyTools.vigenere((String) decrypto, key, MyTools.DECRYPT_MODE);
                else if (flag == MyTools.CHINESE)
                    decrypto = MyTools
                            .vigenereChinese((String) decrypto, key, MyTools.DECRYPT_MODE);
            }
            if ((decrypto != null) && (decrypto.getClass().equals(BigDecimal.class))) {
                decrypto = MyTools.vigenere((BigDecimal) decrypto, key, MyTools.DECRYPT_MODE);
            }
            return decrypto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String setUpKey(String id, int flag) {
        return MyTools.getUpKey(id, flag);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name: com.hr.base.DaoBean
 * JD-Core Version: 0.5.4
 */