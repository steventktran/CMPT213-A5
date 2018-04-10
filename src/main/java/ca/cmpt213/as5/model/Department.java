package ca.cmpt213.as5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class representing a Department, which contains a department ID, the name of the department, as well as
 * all Courses that are taught by this department.
 */


public class Department implements Comparable<Department>, Iterable<Course>{
    private long deptId;
    private String name;
    private List<Course> courseList = new ArrayList<>();

    @Override
    public int compareTo(Department other) {
        return name.compareTo(other.name);
    }

    @Override
    public Iterator<Course> iterator() {
        return courseList.iterator();
    }

    public Department() { }
    public Department(String name) {
        this.name = name.trim();
    }

    @JsonIgnore
    public List<Course> getCourseList() {
        return courseList;
    }

    public Course getCourse(long courseId) {
        for(Course course: courseList) {
            if(course.getCourseId() == courseId) {
                return course;
            }
        }
        return null;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addToCourseList(Course newCourse, Offering newOffering, Component newComponent) {
        //Check for duplicate course; if there is, let Offering check for duplicates
        for(Course course: courseList) {
            if(course.getCatalogNumber().equals(newCourse.getCatalogNumber())) {
                course.addToOfferingList(newOffering, newComponent);
                return;
            }
        }
        //If not duplicate, add to course list
        newCourse.addToOfferingList(newOffering, newComponent);
        courseList.add(newCourse);
    }

    public int getFirstSemesterCode() {
        int firstSemester = 0;
            for(Course course: courseList) {
                int semesterCode = course.getOfferingList().get(0).getSemesterCode();
                if(semesterCode < firstSemester || firstSemester == 0) {
                    firstSemester = semesterCode;
                }
            }

        return firstSemester;
    }

    public int getLastSemesterCode() {
        int lastSemester = 0;
            for(Course course: courseList) {
                int semesterCode = course.getOfferingList().get(course.getOfferingList().size() - 1).getSemesterCode();
                if(semesterCode > lastSemester) {
                    lastSemester = semesterCode;
                }
            }
        return lastSemester;
    }
}
