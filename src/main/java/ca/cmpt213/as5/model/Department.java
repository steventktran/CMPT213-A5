package ca.cmpt213.as5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Department implements Comparable<Department>{
    private long deptId;
    private String name;
    private List<Course> courseList = new ArrayList<>();

    @Override
    public int compareTo(Department other) {
        return name.compareTo(other.name);
    }

    public Department() { }
    public Department(String name) {
        this.name = name.trim();
    }

    @JsonIgnore
    public List<Course> getCourseList() {
        return courseList;
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
        newCourse.setCourseId(courseList.size());
        newCourse.addToOfferingList(newOffering, newComponent);
        courseList.add(newCourse);
    }
}
