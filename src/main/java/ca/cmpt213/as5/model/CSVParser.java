package ca.cmpt213.as5.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CSVParser {
    private List<Department> departments = new ArrayList<>();
    private File file;

    private static final int SEMESTER_CODE_INDEX = 0;
    private static final int DEPARTMENT_INDEX = 1;
    private static final int COURSE_NUMBER_INDEX = 2;
    private static final int LOCATION_INDEX = 3;
    private static final int ENROLLMENT_CAP_INDEX = 4;
    private static final int ENROLLMENT_TOTAL_INDEX = 5;
    private static final int INSTRUCTOR_INDEX = 6;


    public CSVParser(File file) {
        this.file = file;
    }

    public void parseFile() throws FileNotFoundException {
        Scanner read = new Scanner(file);

        //Headers is never used because it's sole purpose is to skip the header of the CSVFile
        read.nextLine();

        //Reads in the fields of the CSV File
        while (read.hasNextLine()) {
            String[] fields = read.nextLine().split(",");
            final int COMPONENT_INDEX = fields.length - 1;

            List<String> courseOfferingFields = new ArrayList<>();
            courseOfferingFields.add(fields[LOCATION_INDEX]);
            for(int i = INSTRUCTOR_INDEX; i <= COMPONENT_INDEX; i++) {
                courseOfferingFields.add(fields[i]);
            }
            courseOfferingFields.add(fields[SEMESTER_CODE_INDEX]);

            List<String> componentFields = new ArrayList<>();
            componentFields.add(fields[ENROLLMENT_CAP_INDEX]);
            componentFields.add(fields[ENROLLMENT_TOTAL_INDEX]);
            componentFields.add(fields[fields.length - 1]);

            Department department = new Department(fields[DEPARTMENT_INDEX]);
            Course course = new Course(fields[COURSE_NUMBER_INDEX]);
            Offering offering = new Offering(courseOfferingFields);
            Component component = new Component(componentFields);

            addToCourseList(department, course, offering, component);
        }
        read.close();

        Collections.sort(departments);
        for(Department department: departments) {
            Collections.sort(department.getCourseList());
            for(Course course: department.getCourseList()) {
                Collections.sort(course.getOfferingList());
                for(Offering offering: course.getOfferingList()) {
                    Collections.sort(offering.getComponentList());
                }
            }
        }
    }

    public void addToCourseList(Department newDepartment, Course newCourse, Offering newOffering, Component newComponent) {
        //Check for duplicate departments; if there is, let Course check for duplicates
        for(Department department: departments) {
            if(newDepartment.getName().equals(department.getName())) {
                department.addToCourseList(newCourse, newOffering, newComponent);
                return;
            }
        }
        //If not duplicate, add to departments list
        newDepartment.setDeptId(departments.size());
        departments.add(newDepartment);
    }

    //Print all the courses in the list
    public String printCourseList() {
        StringBuilder builder = new StringBuilder();

        for(Department department: departments) {
            for(Course course: department.getCourseList()) {
                builder.append(department.getName() + " " + course.getCatalogNumber() + "\n");
                for(Offering offering: course.getOfferingList()) {
                    builder.append("\t" + offering.getSemesterCode() + " in "
                            + offering.getLocation() + " by " + offering.getInstructors() + "\n");
                    for(Component component: offering.getComponentList()) {
                        builder.append("\t\t" + "Type=" + component.getComponent() + ", Enrollment="
                                + component.getEnrollmentTotal() + "/"
                                + component.getEnrollmentCap() + "\n");
                    }
                }
            }
        }
        return builder.toString();
    }
}
