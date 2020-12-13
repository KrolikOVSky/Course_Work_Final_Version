package com.backEnd;

/**
 * Класс описывающий объект "Итог 1"
 */
public class ResultOne {
    private String type;
    private double min;
    private double max;

    public ResultOne(String type, double min, double max) {
        this.type = type;
        this.min = min;
        this.max = max;
    }

    public ResultOne() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
