package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.List;

public class Course implements Comparable<Course>{
    private long courseId;
    private String catalogNumber;
    private List<Offering> offeringList = new ArrayList<>();

    @Override
    public int compareTo(Course other) {
        return catalogNumber.compareTo(other.catalogNumber);
    }

    public Course(String catalogNumber) {
        this.catalogNumber = catalogNumber.trim();
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public List<Offering> getOfferingList() {
        return offeringList;
    }

    public void setOfferingList(List<Offering> offeringList) {
        this.offeringList = offeringList;
    }

    public void addToOfferingList(Offering newOffering, Component newComponent) {
        //Check for duplicate course; if there is, let Offering check for duplicates
        for(Offering offering: offeringList) {
            if(offering.getLocation().equals(newOffering.getLocation())
                    && offering.getSemesterCode() == newOffering.getSemesterCode()) {

                //Check to see if only instructors were missing; if so, add missing instructors to list of instructors
                if(!offering.getInstructorList().equals(newOffering.getInstructorList())) {
                    offering.addInstructor(newOffering.getInstructorList());
                    return;
                } else {
                    offering.addToComponentList((newComponent));
                    return;
                }
            }
        }
        //If not duplicate, add to course list
        newOffering.setCourseOfferingId(offeringList.size());
        newOffering.addToComponentList(newComponent);
        offeringList.add(newOffering);
    }
}
