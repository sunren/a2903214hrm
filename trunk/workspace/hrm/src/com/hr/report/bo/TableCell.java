package com.hr.report.bo;

public class TableCell {
    private String x;
    private String y;
    private double value;
    private double xTotal;
    private double yTotal;

    public TableCell(String inputX) {
        this.x = inputX;
    }

    public TableCell(String inputX, String inputY) {
        this.x = inputX;
        this.y = inputY;
    }

    public TableCell(String inputX, double inputValue) {
        this.x = inputX;
        this.value = inputValue;
    }

    public TableCell(String inputX, String inputY, double inputValue) {
        this.x = inputX;
        this.y = inputY;
        this.value = inputValue;
    }

    public TableCell(String inputX, String inputY, double inputValue, double inputXtotal,
            double inputYtotal) {
        this.x = inputX;
        this.y = inputY;
        this.value = inputValue;
        this.xTotal = inputXtotal;
        this.yTotal = inputYtotal;
    }

    public String getX() {
        return this.x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return this.y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getXTotal() {
        return this.xTotal;
    }

    public void setXTotal(double total) {
        this.xTotal = total;
    }

    public double getYTotal() {
        return this.yTotal;
    }

    public void setYTotal(double total) {
        this.yTotal = total;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.bo.TableCell JD-Core Version: 0.5.4
 */