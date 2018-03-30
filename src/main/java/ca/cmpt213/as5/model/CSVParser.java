package ca.cmpt213.as5.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is the CSVParse class. It's responsibilities are to parse the given file
 * and contains a list of all courses.
 */

public class CSVParser {
    private List<CourseData> courseList = new ArrayList<>();
    private File file;
    private static final int NUMBER_OF_FIELDS_BEFORE_ENROLLMENT = 4;
    private static final int ENROLLMENT_FIELD = 4;
    private static final int ENROLLMENT_TOTAL_FIELD = 5;
    private static final int INSTRUCTOR_FIELD = 6;


    public CSVParser(File file) {
        this.file = file;
    }

    public void parseFile() throws FileNotFoundException{

        //Headers is never used because it's sole purpose is to skip the header of the CSVFile
        Scanner read = new Scanner(file);
        String[] headers = read.nextLine().split(",");

        //Reads in the fields of the CSV File
        while (read.hasNextLine()) {
            String[] fields = read.nextLine().split(",");
            List<String> courseFields = new ArrayList<>();

            //From SEMESTER to LOCATION add the fields
            for(int i = 0; i <NUMBER_OF_FIELDS_BEFORE_ENROLLMENT; i++) {
                courseFields.add(fields[i]);
            }

            //From all Instructors to components
            for(int i = INSTRUCTOR_FIELD; i <= fields.length - 1; i++) {
                courseFields.add(fields[i]);
            }

            //Get the enrollment cap and enrollment total put them together into one object.
            String[] componentFields = {fields[ENROLLMENT_FIELD], fields[ENROLLMENT_TOTAL_FIELD],
                                        fields[fields.length - 1]};
            CourseData courseToAdd = new CourseData(courseFields);
            Component componentToAdd = new Component(componentFields);
            addToCourseList(courseToAdd, componentToAdd);
        }
        read.close();
    }

    public void addToCourseList(CourseData courseToBeAdded, Component componentToBeAdded) {

        //Let's cycle through all the courses and add them if they match with the course we are given
        //Avoids duplication
        for(CourseData courseData: courseList) {
            if(courseToBeAdded.getSemNumber() == courseData.getSemNumber() &&
                    courseToBeAdded.getSubject().equals(courseData.getSubject()) &&
                    courseToBeAdded.getCourseNum().equals(courseData.getCourseNum()) &&
                    courseToBeAdded.getLocation().equals(courseData.getLocation()) &&
                    courseToBeAdded.getInstructors().equals(courseData.getInstructors()) )
            {
                // Add to enrollment cap and total because it is an aggregate, do not add as seperate entry to courseLise
                courseData.addEnrollment(componentToBeAdded);
                return;
            }
        }
        // If not found already in courseList, it is not a duplicate and needs to be added to courseList
        courseToBeAdded.addComponent(componentToBeAdded);
        courseList.add(courseToBeAdded);
    }

    //Print all the courses in the list
    public void printCourseList() {
        List<CourseData> copiedList = new ArrayList(courseList);
        for(CourseData course: courseList) {
            System.out.println(course.getSubject() + " " + course.getCourseNum());
            System.out.println("\t" + course.getSemNumber() + " in " + course.getLocation() + " by " + course.getInstructors());
            for(Component component: course.getComponents()) {
                System.out.println("\t\t" + "Type=" + component.getComponent() + ", Enrollment=" + component.getEnrollmentTotal() + "/" + component.getEnrollmentCap());
            }
        }
    }
}
