package ca.cmpt213.as5.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVParser {
    private List<CourseData> courseList = new ArrayList<>();
    private File file;

    public CSVParser(File file) {
        this.file = file;
    }

    public void parseFile() throws FileNotFoundException{
        Scanner read = new Scanner(file);
        String[] headers = read.nextLine().split(",");
        while (read.hasNextLine()) {
            String[] fields = read.nextLine().split(",");
            CourseData tobeAdded = new CourseData(fields);
            addToCourseList(tobeAdded);
        }
        read.close();
    }

    public void addToCourseList(CourseData tobeAdded) {
        for(CourseData courseData: courseList) {
            if(tobeAdded.getSemNumber() == courseData.getSemNumber() &&
                    tobeAdded.getSubject().equals(courseData.getSubject()) &&
                    tobeAdded.getCourseNum().equals(courseData.getCourseNum()) &&
                    tobeAdded.getLocation().equals(courseData.getLocation()) &&
                    tobeAdded.getInstructors().equals(courseData.getInstructors()) &&
                    tobeAdded.getComponent().equals(courseData.getComponent()))
            {
                // Add to enrollment cap and total because it is an aggregate, do not add as seperate entry to courseLise
                courseData.addEnrollmentCap(tobeAdded.getEnrollmentCap());
                courseData.addEnrollmentTotal(tobeAdded.getEnrollmentTotal());
                return;
            }
        }
        // If not found already in courseList, it is not a duplicate and needs to be added to courseList
        courseList.add(tobeAdded);
    }

    public void printCourseList() {
        for(CourseData course: courseList) {
            System.out.println(course.getSubject() + " " + course.getCourseNum());
            System.out.println("\t" + course.getSemNumber() + " in " + course.getLocation() + " by " + course.getInstructors());
            System.out.println("\t\t" + "Type=" + course.getComponent() + ", Enrollment=" + course.getEnrollmentTotal() + "/" + course.getEnrollmentCap());
        }
    }
}
