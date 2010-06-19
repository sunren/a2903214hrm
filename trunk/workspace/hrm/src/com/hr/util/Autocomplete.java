package com.hr.util;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Autocomplete implements Serializable {
    private static final long serialVersionUID = 823501326L;
    private String make;
    private String model;

    public Autocomplete() {
    }

    public Autocomplete(String make, String model) {
        this.make = make;
        this.model = model;
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String toString() {
        return new ToStringBuilder(this).append("make", this.make).append("model", this.model)
                .toString();
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.util.Autocomplete JD-Core Version: 0.5.4
 */