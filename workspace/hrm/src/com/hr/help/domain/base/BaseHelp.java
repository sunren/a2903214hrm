package com.hr.help.domain.base;

import com.hr.base.BaseDomain;
import com.hr.help.domain.Help;
import com.hr.help.domain.Helpclass;
import java.io.Serializable;

public abstract class BaseHelp extends BaseDomain implements Serializable {
    public static String REF = "Help";
    public static String PROP_HELP_CLASS = "helpClass";
    public static String PROP_HELP_DESC = "helpDesc";
    public static String PROP_HELP_TITLE = "helpTitle";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private String id;
    private String helpDesc;
    private String helpTitle;
    private Helpclass helpClass;

    public BaseHelp() {
        initialize();
    }

    public BaseHelp(String id) {
        setId(id);
        initialize();
    }

    public BaseHelp(String id, Helpclass helpClass, String helpDesc, String helpTitle) {
        setId(id);
        setHelpClass(helpClass);
        setHelpDesc(helpDesc);
        setHelpTitle(helpTitle);
        initialize();
    }

    protected void initialize() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHelpDesc() {
        return this.helpDesc;
    }

    public void setHelpDesc(String helpDesc) {
        this.helpDesc = helpDesc;
    }

    public String getHelpTitle() {
        return this.helpTitle;
    }

    public void setHelpTitle(String helpTitle) {
        this.helpTitle = helpTitle;
    }

    public Helpclass getHelpClass() {
        return this.helpClass;
    }

    public void setHelpClass(Helpclass helpClass) {
        this.helpClass = helpClass;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Help))
            return false;

        Help help = (Help) obj;
        if ((null == getId()) || (null == help.getId()))
            return false;
        return getId().equals(help.getId());
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
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.help.domain.base.BaseHelp JD-Core Version: 0.5.4
 */