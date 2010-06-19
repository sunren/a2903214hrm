package com.hr.base.io;

import java.util.ArrayList;

public class VirtualHashTable {
    private ArrayList<Container> list = new ArrayList();

    public void clear() {
        this.list.clear();
    }

    public int size() {
        return this.list.size();
    }

    public void put(String key, String value) {
        Container container = new Container(key, value);
        this.list.add(container);
    }

    public boolean containsKey(String key) {
        int listSize = this.list.size();
        for (int i = 0; i < listSize; ++i) {
            if (((Container) this.list.get(i)).getKey().equals(key))
                return true;
        }
        return false;
    }

    public boolean containsValue(String value) {
        int listSize = this.list.size();
        for (int i = 0; i < listSize; ++i) {
            if (((Container) this.list.get(i)).getValue().equals(value))
                return true;
        }
        return false;
    }

    public String getValueString() {
        int listSize = this.list.size() - 2;
        String listString = " ";
        for (int i = 0; i < listSize; ++i) {
            listString = listString + "\"" + ((Container) this.list.get(i)).getValue() + "\",";
        }
        if (listSize >= 0)
            listString = listString + "\"" + ((Container) this.list.get(listSize)).getValue()
                    + "\" 或者 \"" + ((Container) this.list.get(listSize + 1)).getValue()
                    + "\".";
        else if (listSize == -1)
            listString = listString + "\"" + ((Container) this.list.get(listSize + 1)).getValue()
                    + "\".";
        return listString;
    }

    public String get(String key) {
        int listSize = this.list.size();
        for (int i = 0; i < listSize; ++i) {
            if (((Container) this.list.get(i)).getKey().equals(key))
                return ((Container) this.list.get(i)).getValue();
        }
        return null;
    }

    public String getKey(int i) {
        if (i < this.list.size()) {
            return ((Container) this.list.get(i)).getKey();
        }
        return null;
    }

    public int indexOf(String key) {
        int listSize = this.list.size();
        for (int i = 0; i < listSize; ++i) {
            if (((Container) this.list.get(i)).getKey().equals(key))
                return i;
        }
        return 0;
    }

    public String getKey(String value) {
        int listSize = this.list.size();
        for (int i = 0; i < listSize; ++i) {
            if (((Container) this.list.get(i)).getValue().equals(value))
                return ((Container) this.list.get(i)).getKey();
        }
        return null;
    }

    private class Container {
        private String key = null;

        private String value = null;

        private Container(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.base.io.VirtualHashTable JD-Core Version: 0.5.4
 */