package com.hr.security.bo.impl;

import com.alisoft.sip.sdk.isv.SignatureUtil;
import com.hr.security.bo.IValidateUser;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ValidateUserImpl implements IValidateUser {
    public String validateUser(String session_id, String user_id, String app_instance_id,
            String app_id, String token) {
        String code = null;
        String result = null;
        SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sip_timestamp = timeStamp.format(new Date());
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("appId", app_id);
        map.put("userId", user_id);
        map.put("appInstanceId", app_instance_id);
        map.put("sip_sessionid", session_id);
        map.put("sip_appkey", "12215");
        map.put("sip_appsecret", "fbc1e03056fb11dda1dfd0ef302ff22c");
        map.put("sip_apiname", "alisoft.validateUser");
        map.put("sip_timestamp", sip_timestamp);
        String sign = SignatureUtil.Signature(map, (String) map.get("sip_appsecret"));
        map.put("sip_sign", sign);

        StringBuffer buffer = new StringBuffer();
        boolean notFirst = false;
        for (Map.Entry entry : map.entrySet()) {
            if (notFirst) {
                buffer.append("&");
            } else {
                notFirst = true;
            }
            Object value = entry.getValue();
            buffer.append((String) entry.getKey()).append("=").append(encodeURL(value));
        }
        String queryString = buffer.toString();
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(
                    "http://sipdev.alisoft.com/sip/rest").openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.connect();
            conn.getOutputStream().write(queryString.getBytes());
            String charset = getChareset(conn.getContentType());
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                    charset));
            StringBuffer outbuffer = new StringBuffer();

            String line = null;
            while ((line = reader.readLine()) != null) {
                outbuffer.append(line);
            }
            reader.close();
            code = conn.getHeaderField("sip_status");
            conn.disconnect();
            result = outbuffer.toString();
            if ((result != null) && (code.equals("9999"))) {
                Map returnMap = getCode(result);
                return (String) returnMap.get("String");
            }

            return "-3";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-3";
    }

    private String getChareset(String contentType) {
        int i = (contentType == null) ? -1 : contentType.indexOf("charset=");
        return (i == -1) ? "GBK" : contentType.substring(i + 8);
    }

    private String encodeURL(Object target) {
        String result = (target != null) ? target.toString() : "";
        try {
            result = URLEncoder.encode(result, "gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    private Map<String, Object> getCode(String str) throws Exception {
        Map map = new HashMap();
        if ((str != null) && (str.length() > 0)) {
            ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(bais);
            if (document != null) {
                map.put("String", toString(document, "String", Integer.valueOf(0)));
            }
        }
        return map;
    }

    private String toString(Document document, String tagName, Integer item) {
        if (item == null)
            item = Integer.valueOf(0);
        Node node = document.getElementsByTagName(tagName).item(
                                                                (null == item) ? 0 : item
                                                                        .intValue());
        if (node == null)
            return "";
        return node.getFirstChild().getNodeValue();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.security.bo.impl.ValidateUserImpl JD-Core Version: 0.5.4
 */