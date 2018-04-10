package ca.cmpt213.as5.model;

import ca.cmpt213.as5.exceptions.CourseNotFoundException;
import ca.cmpt213.as5.exceptions.DepartmentNotFoundException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Watcher class. It implements observer and is our observer. It watches over courses
 * and upon being notified by the Course observable adds tracks the offering and component
 * added to the list.
 */

public class Watcher implements Observer{

    //Private members of watcher
    private long watcherID;
    private Department department;

    //Course if our observable...
    private Course course;
    private List<String> events = new ArrayList<>();

    //Overriding/Implementing the addUpdate method()
    @Override
    public void addUpdate(Offering newOffering, Component newComponent) {

        //Break the string into multiple components and pull necessary data from the given information
        Date date = new Date();
        String currentDate = date.toString();
        String type = newComponent.getType();
        String enrollmentTotal = "" + newComponent.getEnrollmentTotal();
        String enrollmentCap = "" + newComponent.getEnrollmentCap();
        String term = newOffering.getTerm();
        String year = "" + newOffering.getYear();

        //Build a complete message and add it to our list of events
        String eventMessage = currentDate + ": " + "Added section " + type + " with Enrollment"
                                + " (" + enrollmentTotal + " / " + enrollmentCap + ") "
                                + "to offering " + term + " " + year + ".";

        events.add(eventMessage);
    }

    //Parameterized constructor which accepts
    public Watcher(long watcherID, long deptID, long courseID, CSVParser csvParser) throws DepartmentNotFoundException, CourseNotFoundException {
        this.watcherID = watcherID;

        //Instantiate our department. Look for the corresponding department
        for(Department department : csvParser.getDepartments()) {
            if(department.getDeptId() == deptID) {
                this.department = department;
            }
        }

        //If you cannot find the department. Something went wrong...
        if(this.department == null) {
            throw new DepartmentNotFoundException();
        }


        //Instantiate the course. Look for the corresponding course.
        for(Course course : this.department.getCourseList()) {
            if(course.getCourseId() == courseID) {
                this.course = course;
            }
        }

        //If you can't find the course something went wrong.
        if(this.course == null) {
            throw new CourseNotFoundException();
        }

    }

    //Default constructor needed for calling an empty watcher
    public Watcher() { };


    //Setters and getters for private fields.
    public List<String> getEvents() {
        return events;
    }

    public long getWatcherID() {
        return watcherID;
    }

    public Department getDepartment() {
        return department;
    }

    public Course getCourse() {
        return course;
    }
}
