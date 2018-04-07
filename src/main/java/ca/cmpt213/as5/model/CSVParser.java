package ca.cmpt213.as5.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

/**
 * This is the CSVParse class. It's responsibilities are to parse the given file
 * and contains a list of all courses.
 */

public class CSVParser {
    private List<CourseData> courseList = new ArrayList<>();
    private File file;

    private static final int SEMESTER_NUMBER_INDEX = 0;
    private static final int SUBJECT_INDEX = 1;
    private static final int COURSE_NUMBER_INDEX = 2;
    private static final int LOCATION_INDEX = 3;
    private static final int ENROLLMENT_CAP_INDEX = 4;
    private static final int ENROLLMENT_TOTAL_INDEX = 5;
    private static final int INSTRUCTOR_INDEX = 6;


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
            for(int i = SUBJECT_INDEX ; i <= COURSE_NUMBER_INDEX; i++) {
                courseFields.add(fields[i]);
            }

            List<String> classOfferingFields = new ArrayList<>();
            classOfferingFields.add(fields[SEMESTER_NUMBER_INDEX]);
            classOfferingFields.add(fields[LOCATION_INDEX]);
            //From all Instructors to components
            for(int i = INSTRUCTOR_INDEX; i <= fields.length - 1; i++) {
                classOfferingFields.add(fields[i]);
            }

            //Get the enrollment cap and enrollment total put them together into one object.
            String[] componentFields = {fields[ENROLLMENT_CAP_INDEX], fields[ENROLLMENT_TOTAL_INDEX],
                                        fields[fields.length - 1]};


            CourseData courseToAdd = new CourseData(courseFields);
            Component componentToAdd = new Component(componentFields);
            ClassOffering offeringToAdd = new ClassOffering(classOfferingFields);

            addToCourseList(courseToAdd, offeringToAdd, componentToAdd);
        }
        read.close();

        for(CourseData course: courseList) {
          List<ClassOffering> classes = course.getClassOfferings();
          Collections.sort(classes);
          for(Component component: classes.getComponents()) {
            Collections.sort(component);
          }
        }
    }

    public void addToCourseList(CourseData courseToBeAdded, ClassOffering offeringToBeAdded, Component componentToBeAdded) {

        for(CourseData courseData: courseList) {
            //If a duplicate course, check to see if also duplicate class offering
            if(courseData.getSubject().equals(courseToBeAdded.getSubject())
                    && courseData.getCourseNum().equals(courseToBeAdded.getCourseNum()))
            {
                courseData.addClassOffering(offeringToBeAdded, componentToBeAdded);
                return;
            }
        }
        //If not a duplicate course, add into course list.
        courseToBeAdded.addClassOffering(offeringToBeAdded, componentToBeAdded);
        courseList.add(courseToBeAdded);
    }



    //Print all the courses in the list
    public String printCourseList() {
        StringBuilder builder = new StringBuilder();

        for(CourseData course: courseList) {
            builder.append(course.getSubject() + " " + course.getCourseNum() + "\n");
            for(ClassOffering classOffering: course.getClassOfferings()) {
                builder.append("\t" + classOffering.getSemNumber() + " in "
                        + classOffering.getLocation() + " by "
                        + classOffering.getInstructors() + "\n");
                for(Component component: classOffering.getComponents()) {
                    builder.append("\t\t" + "Type=" + component.getComponent() + ", Enrollment="
                            + component.getEnrollmentTotal() + "/"
                            + component.getEnrollmentCap() + "\n");
                }
            }

        }
        return builder.toString();
    }
}
