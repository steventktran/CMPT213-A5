package ca.cmpt213.as5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Course implements Comparable<Course>, Iterable<Offering>, Observable{
    private long courseId;
    private String catalogNumber;
    private List<Offering> offeringList = new ArrayList<>();
    private List<Observer> listOfObservers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        listOfObservers.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        listOfObservers.remove(observer);
    }

    @Override
    public void notifyAddObservers(Offering newOffering, Component newComponent) {
        for(Observer observer : listOfObservers) {
            observer.addUpdate(newOffering, newComponent);
        }
    }

    @Override
    public int compareTo(Course other) {
        return catalogNumber.compareTo(other.catalogNumber);
    }

    @Override
    public Iterator<Offering> iterator() {
        return offeringList.iterator();
    }

    public Course() { }

    public Course(String catalogNumber, long courseId) {
        this.catalogNumber = catalogNumber.trim();
        this.courseId = courseId;
    }

    public Offering getOffering(long courseOfferingId) {
        for(Offering offering: offeringList) {
            if(offering.getCourseOfferingId() == courseOfferingId) {
                return offering;
            }
        }
        return null;
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

    @JsonIgnore
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

//                Check to see if only instructors were missing; if so, add missing instructors to list of instructors
                if(!offering.getInstructorList().equals(newOffering.getInstructorList())) {
                    offering.addInstructor(newOffering.getInstructorList());
                    return;
                } else {
                    offering.addToComponentList((newComponent));
                    return;
                }

//                offering.addToComponentList((newComponent));
//                return;
            }
        }

        //If not duplicate, add to course list
        newOffering.setCourseOfferingId(offeringList.size());
        newOffering.addToComponentList(newComponent);
        offeringList.add(newOffering);
    }

}
