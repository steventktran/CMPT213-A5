package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.*;

/**
 * This is the course data class. It's responsibilities are to
 * parse and store the information the CSVParse gives it
 */

public class CourseData {
    private int semNumber;
    private String subject;
    private String courseNum;
    private String location;
    private List<String> instructors;
    private List<Component> components;

    //Avoiding magic numbers
    private static final int SEM_FIELD = 0;
    private static final int SUBJECT_FIELD = 1;
    private static final int COURSE_FIELD = 2;
    private static final int LOCATION_FIELD = 3;
    private static final int NUM_OF_INSTRUCTORS = 4;


    public CourseData(List<String> fields) {
        semNumber = parseInt(fields.get(SEM_FIELD));
        subject = fields.get(SUBJECT_FIELD).trim();
        courseNum = fields.get(COURSE_FIELD).trim();
        location = fields.get(LOCATION_FIELD).trim();
        instructors = new ArrayList<>();
        components = new ArrayList<>();
        for(int i = NUM_OF_INSTRUCTORS; i < fields.size()- 1; i++) {
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
