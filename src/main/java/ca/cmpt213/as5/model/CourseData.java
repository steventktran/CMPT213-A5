package ca.cmpt213.as5.model;

import static java.lang.Integer.*;

public class CourseData {
    private int semNumber;
    private String subject;
    private String courseNum;
    private String location;
    private int enrollmentCap;
    private int enrollmentTotal;
    private String component;

    public CourseData(String[] fields) {
        semNumber = parseInt(fields[0]);
        subject = fields[1];
        courseNum = fields[2];
        location = fields[3];
        enrollmentCap = parseInt(fields[4]);
        enrollmentTotal = parseInt(fields[5]);
        component = fields[6];
    }

    public CourseData() {

    }

    public int getSemNumber() {
        return semNumber;
    }

    public void setSemNumber(int semNumber) {
        this.semNumber = semNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public void setEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
}
