package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.*;

/**
 * This is the course data class. It's responsibilities are to
 * parse and store the information the CSVParse gives it
 */

public class CourseData implements Comparable<CourseData>{
    private String subject;
    private String courseNum;
    private List<ClassOffering> classOfferings;

    //Avoiding magic numbers
    private static final int SUBJECT_FIELD = 0;
    private static final int COURSE_FIELD = 1;

    @Override
    public int compareTo(CourseData other) {
      return subject.compareTo(other.subject);
    }

    public CourseData(List<String> fields) {
        subject = fields.get(SUBJECT_FIELD).trim();
        courseNum = fields.get(COURSE_FIELD).trim();
        classOfferings = new ArrayList<>();
    }

    public CourseData() {

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

    public List<ClassOffering> getClassOfferings() {
        return classOfferings;
    }

    public void addClassOffering(ClassOffering offeringToBeAdded, Component componentToBeAdded) {
        //Check for duplicate class offerings. If there are, addComponent will check if it is also trying to add a duplicate component
        for(ClassOffering classOffering: classOfferings) {
            if(classOffering.getSemNumber() == offeringToBeAdded.getSemNumber()
                    && classOffering.getInstructors().equals(offeringToBeAdded.getInstructors()))
            {
                classOffering.addComponent(componentToBeAdded);
                return;
            }
        }
        //If not a duplicate class offering, then add to classOfferings list.
        offeringToBeAdded.addComponent(componentToBeAdded);
        classOfferings.add(offeringToBeAdded);
    }
}
