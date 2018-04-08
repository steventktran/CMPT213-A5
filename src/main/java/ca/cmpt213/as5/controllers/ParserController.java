package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.exceptions.DepartmentNotFoundException;
import ca.cmpt213.as5.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * The controller class that utilizes the parse object and outputs it to the
 * server terminal
 */

@RestController
public class ParserController {
    private static final String FILE_PATH = "data/course_data_2018.csv";
    private About aboutNotice = new About();
    private File filePath = new File(FILE_PATH);
    private CSVParser theParser;

    public ParserController() {
        try {
            theParser = new CSVParser(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Get Mapping
    @GetMapping("/api/dump-model")
    public String getDumpModel() throws FileNotFoundException{
        return theParser.printCourseList();
    }

    //Handles a file not found exception.
    @ResponseStatus(value = HttpStatus.BAD_REQUEST,
            reason = "File not found.")
    @ExceptionHandler(FileNotFoundException.class)
    public void firstMoveExceptionExceptionHandler() {

    }

    //Get Mapping for about
    @GetMapping("/api/about")
    public About getAboutMessage() {
        return aboutNotice;
    }

    //Get mapping for list of departments
    @GetMapping("/api/departments")
    public List<Department> getDepartments() {
        return theParser.getDepartments();
    }

    //Get mapping for a list of courses
    @GetMapping("/api/departments/{id}/courses")
    public List<Course> getCourses(@PathVariable("id") int deptID) {
        return theParser.getDepartments()
                .get(deptID)
                .getCourseList();
    }

    //Get mapping for a list of offerings
    @GetMapping("/api/departments/{dId}/courses/{cId}/offerings")
    public List<Offering> getOfferings(@PathVariable("dId") int deptID,
                                       @PathVariable("cId") int courseID) {
        return theParser.getDepartments()
                .get(deptID)
                .getCourseList()
                .get(courseID)
                .getOfferingList();
    }

    //Get Mapping for list of sections of a particular offering
    @GetMapping("/api/departments/{dId}/courses/{cId}/offerings/{oId}")
    public List<Component> getOfferings(@PathVariable("dId") int deptID,
                                       @PathVariable("cId") int courseID,
                                       @PathVariable("oId") int offeringID) {
        return theParser.getDepartments()
                .get(deptID)
                .getCourseList()
                .get(courseID)
                .getOfferingList()
                .get(offeringID)
                .getComponentList();
    }



    @PostMapping("/api/addoffering")
    public void addOffering(@RequestBody int semesterCode,
                            @RequestBody String subjectName,
                            @RequestBody String catalogNumber,
                            @RequestBody String location,
                            @RequestBody int enrollmentCap,
                            @RequestBody String component,
                            @RequestBody int enrollmentTotal,
                            @RequestBody String instructor) throws DepartmentNotFoundException {
        boolean foundDepartment = false;

        for(Department department : getDepartments()) {
            if(department.getName().equals(subjectName)) {
                foundDepartment = true;
                Course newCourse = new Course(catalogNumber);
                newCourse.setCourseId(department.getCourseList().size());

                //Create an list of strings for fields to avoid creating a new constructor
                List<String> courseComponentFields = new ArrayList<>();
                courseComponentFields.add("" + enrollmentCap);
                courseComponentFields.add("" + enrollmentTotal);
                courseComponentFields.add(component);

                Component courseComponent = new Component(courseComponentFields);

                //Create an list of strings for fields to avoid creating a new constructor
                List<String> offeringFields = new ArrayList<>();
                offeringFields.add("" + semesterCode);
                offeringFields.add(location);
                offeringFields.add(instructor);

                Offering newOffering = new Offering(offeringFields);

                department.addToCourseList(newCourse, newOffering, courseComponent);
            }
        }

        if(!foundDepartment) {
            throw new DepartmentNotFoundException();
        }
    }




    //Handle Exception for when the department cannot be found
    @ResponseStatus(value = HttpStatus.BAD_REQUEST,
            reason = "Department cannot be found.")
    @ExceptionHandler(DepartmentNotFoundException.class)
    public void departmentNotFoundExceptionHandler() {

    }



}
