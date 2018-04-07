package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ClassOffering implements Comparable<ClassOffering>{
    private int semNumber;
    private String location;
    private List<String> instructors;
    private List<Component> components;

    private static final int SEM_FIELD = 0;
    private static final int LOCATION_FIELD = 1;
    private static final int NUM_OF_INSTRUCTORS = 2;

    @Override
    public int compareTo(ClassOffering other) {
        return  semNumber - other.getSemNumber() ;
    }

    public ClassOffering(List<String> fields) {
        semNumber = parseInt(fields.get(SEM_FIELD));
        location = fields.get(LOCATION_FIELD).trim();
        instructors = new ArrayList<>();
        components = new ArrayList<>();
        for(int i = NUM_OF_INSTRUCTORS; i < fields.size()- 1; i++) {
            instructors.add(fields.get(i).replace("\"", " ").trim());
        }
    }

    public int getSemNumber() {
        return semNumber;
    }

    public void setSemNumber(int semNumber) {

        this.semNumber = semNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
                    instructorString += instructors.get(i).substring(1);
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

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public void addComponent(Component tobeAdded) {
        //Check for duplicate component, if it is then simply add their enrollment caps and totals together
        for(Component component: components) {
            if(component.getComponent().equals(tobeAdded.getComponent())) {
                component.addEnrollmentCap(tobeAdded.getEnrollmentCap());
                component.addEnrollmentTotal(tobeAdded.getEnrollmentTotal());
                return;
            }
        }
        //If not a duplicate component, add to components list
        components.add(tobeAdded);
    }

}
