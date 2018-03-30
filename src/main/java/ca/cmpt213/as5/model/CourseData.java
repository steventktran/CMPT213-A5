package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.*;

public class CourseData {
    private int semNumber;
    private String subject;
    private String courseNum;
    private String location;
    private int enrollmentCap;
    private int enrollmentTotal;
    private List<String> instructors;
    private String component;

    public CourseData(String[] fields) {
        semNumber = parseInt(fields[0]);
        subject = fields[1].trim();
        courseNum = fields[2].trim();
        location = fields[3].trim();
        enrollmentCap = parseInt(fields[4]);
        enrollmentTotal = parseInt(fields[5]);
        instructors = new ArrayList<>();
        for(int i = 6; i < fields.length - 1; i++) {
            instructors.add(fields[i].trim());
        }
        component = fields[fields.length - 1].trim();
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

    public String getInstructors() {
        String instructorString = new String();
        for(int i = 0; i < instructors.size(); i++) {
            if(i == 0) {
                instructorString += instructors.get(i);
            } else {
                instructorString += ", " + instructors.get(i);
            }
        }
        return instructorString;
    }

    public void setInstructors(List<String> instructors) {
        this.instructors = instructors;
    }


    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
}
