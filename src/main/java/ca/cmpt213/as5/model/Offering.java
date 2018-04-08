package ca.cmpt213.as5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Offering implements Comparable<Offering>{
    private long courseOfferingId;
    private String location;
    private List<String> instructors = new ArrayList<>();
    private String term;
    private int semesterCode;
    private int year;
    private List<Component> componentList = new ArrayList<>();

    @Override
    public int compareTo(Offering other) {
        return semesterCode - other.semesterCode;
    }


    public Offering(List<String> fields) {
        //to be completed
        location = fields.get(0).trim();
        for(int i = 1; i < fields.size() - 1; i++) {
            instructors.add(fields.get(i).replace("\"", " ").trim());
        }

        String semesterField = fields.get(fields.size() - 1);

        int century = Integer.parseInt(semesterField.substring(0, 1));
        int year = Integer.parseInt(semesterField.substring(1,3));
        this.year = 1900 + (century * 100) + year;

        if(semesterField.substring(semesterField.length() - 1).equals("1")) {
            term = "Spring";
        } else if(semesterField.substring(semesterField.length() - 1).equals("4")) {
            term = "Summer";
        } else if(semesterField.substring(semesterField.length() - 1).equals("7")) {
            term = "Fall";
        }
        semesterCode = Integer.parseInt(semesterField);


    }

    public long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getInstructorList() {
        return instructors;
    }

    public String getInstructors() {
        String instructorString = new String();
        if(instructors.size() == 1) {
            instructorString = instructors.get(0);
            return instructorString;
        }

        else {
            for(int i = 0; i < instructors.size(); i++) {
                if(i == 0) {
                    instructorString += instructors.get(i);
                }
                else {
                    instructorString += ", " + instructors.get(i);
                }
            }
        }
        return instructorString;
    }

    public void setInstructors(List<String> instructors) {
        this.instructors = instructors;
    }

    public void addInstructor(List<String> instructorList) {
        for(String newInstructor: instructorList) {
            if(!instructors.contains(newInstructor)) {
                instructors.add(newInstructor);
            }
        }

    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(int semesterCode) {
        this.semesterCode = semesterCode;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @JsonIgnore
    public List<Component> getComponentList() {
        return componentList;
    }

    public void setComponentList(List<Component> componentList) {
        this.componentList = componentList;
    }

    public void addToComponentList(Component newComponent) {
        //Check for duplicate component, if it is then simply add their enrollment caps and totals together
        for(Component component: componentList) {
            if(component.getComponent().equals(newComponent.getComponent())) {
                component.addEnrollmentCap(newComponent.getEnrollmentCap());
                component.addEnrollmentTotal(newComponent.getEnrollmentTotal());
                return;
            }
        }
        //If not a duplicate component, add to components list
        componentList.add(newComponent);
    }
}
