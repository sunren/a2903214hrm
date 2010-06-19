package com.hr.security.web.action;

public class ClientThread {
    private static ThreadLocal thlocal = new ThreadLocal();

    public static void setClient(String newClient) {
        getImpl().setClient(newClient);
    }

    public static String getClient() {
        return getImpl().getClient();
    }

    private static ClientThreadImpl getImpl() {
        ClientThreadImpl impl = (ClientThreadImpl) thlocal.get();
        if (impl == null) {
            impl = new ClientThreadImpl();
            thlocal.set(impl);
        }
        return impl;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.security.web.action.ClientThread JD-Core Version: 0.5.4
 */