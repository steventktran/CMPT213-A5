package ca.cmpt213.as5.model;

import static java.lang.Integer.parseInt;

public class Component {
    private int enrollmentCap;
    private int enrollmentTotal;
    private String component;


    public Component(String[] fields) {
        enrollmentCap = parseInt(fields[0]);
        enrollmentTotal = parseInt(fields[1]);
        component = fields[2].trim();
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public void setEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public void addEnrollmentCap(int aggregateCap) {
        enrollmentCap += aggregateCap;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public void addEnrollmentTotal(int aggregateTotal) {
        enrollmentTotal += aggregateTotal;
    }
}
