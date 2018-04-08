package ca.cmpt213.as5.model;

import ca.cmpt213.as5.exceptions.CourseNotFoundException;
import ca.cmpt213.as5.exceptions.DepartmentNotFoundException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Watcher implements Observer{
    private long watcherID;
    private Department department;
    //Course if our observable...
    private Course course;
    private String[] events;
    private int arrayIndex = 0;

    @Override
    public void addUpdate(Offering newOffering, Component newComponent) {
        Date date = new Date();
        String currentDate = date.toString();
        String type = newComponent.getComponent();
        String enrollmentTotal = "" + newComponent.getEnrollmentTotal();
        String enrollmentCap = "" + newComponent.getEnrollmentCap();
        String term = newOffering.getTerm();
        String year = "" + newOffering.getYear();

        String eventMessage = currentDate + ": " + "Added section " + type + " with Enrollment"
                                + " (" + enrollmentTotal + " / " + enrollmentCap + ") "
                                + "to offering " + term + " " + year + ".";

        events[arrayIndex] = eventMessage;
        arrayIndex++;
    }


    public Watcher(long watcherID, long deptID, long courseID, CSVParser csvParser) throws DepartmentNotFoundException, CourseNotFoundException {
        this.watcherID = watcherID;

        //Instantiate our department. Look for the corresponding department
        for(Department department : csvParser.getDepartments()) {
            if(department.getDeptId() == deptID) {
                this.department = department;
            }
        }

        if(this.department == null) {
            throw new DepartmentNotFoundException();
        }


        //Instantiate the course. Look for the corresponding course.
        for(Course course : this.department.getCourseList()) {
            if(course.getCourseId() == courseID) {
                this.course = course;
            }
        }

        if(this.course == null) {
            throw new CourseNotFoundException();
        }

    }

    public String[] getEvents() {
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
