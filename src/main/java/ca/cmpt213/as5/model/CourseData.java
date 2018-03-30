package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.*;

public class CourseData {
    private int semNumber;
    private String subject;
    private String courseNum;
    private String location;
    private List<String> instructors;
    private List<Component> components;

    public CourseData(List<String> fields) {
        semNumber = parseInt(fields.get(0));
        subject = fields.get(1).trim();
        courseNum = fields.get(2).trim();
        location = fields.get(3).trim();
        instructors = new ArrayList<>();
        components = new ArrayList<>();
        for(int i = 4; i < fields.size()- 1; i++) {
            instructors.add(fields.get(i).trim());
        }
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

    public void addEnrollment(Component tobeAdded) {
        for(Component component: components) {
            if(component.getComponent().equals(tobeAdded.getComponent())) {
                component.addEnrollmentCap(tobeAdded.getEnrollmentCap());
                component.addEnrollmentTotal(tobeAdded.getEnrollmentTotal());
                return;
            }
        }
        components.add(tobeAdded);
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

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public void addComponent(Component component) {
        components.add(component);
    }
}
