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
            List<String> courseFields = new ArrayList<>();
            for(int i = 0; i < 4; i++) {
                courseFields.add(fields[i]);
            }
            for(int i = 6; i <= fields.length - 1; i++) {
                courseFields.add(fields[i]);
            }
            String[] componentFields = {fields[4], fields[5], fields[fields.length - 1]};
            CourseData courseToAdd = new CourseData(courseFields);
            Component componentToAdd = new Component(componentFields);
            addToCourseList(courseToAdd, componentToAdd);
        }
        read.close();
    }

    public void addToCourseList(CourseData courseToBeAdded, Component componentToBeAdded) {
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
