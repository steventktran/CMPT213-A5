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
        while (read.hasNextLine()) {
            String[] fields = read.nextLine().split(",");
            CourseData tobeAdded = new CourseData(fields);
            addToCourseList(tobeAdded);
        }
        read.close();
    }

    public void addToCourseList(CourseData currentCourse) {
        for(CourseData courseData: courseList) {
            if(currentCourse.getSemNumber() == courseData.getSemNumber() &&
                    currentCourse.getSubject().equals(courseData.getSubject()) &&
                    currentCourse.getCourseNum().equals(courseData.getCourseNum()) &&
                    currentCourse.getLocation().equals(courseData.getLocation()) &&
                    currentCourse.getInstructors().equals(courseData.getInstructors()) &&
                    currentCourse.getComponent().equals(courseData.getComponent()))
            {
                // Add to enrollment cap and total because it is an aggregate, do not add as seperate entry to courseLise
                courseData.addEnrollmentCap(currentCourse.getEnrollmentCap());
                courseData.addEnrollmentTotal(currentCourse.getEnrollmentTotal());
                return;
            }
        }
        // If not found already in courseList, it is not a duplicate and needs to be added to courseList
        courseList.add(currentCourse);
    }
}
