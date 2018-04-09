package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.exceptions.CourseNotFoundException;
import ca.cmpt213.as5.exceptions.DepartmentNotFoundException;
import ca.cmpt213.as5.exceptions.WatcherNotFoundException;
import ca.cmpt213.as5.model.*;
import ca.cmpt213.as5.placeHolderJsonObjects.OfferingsPlaceholder;
import ca.cmpt213.as5.placeHolderJsonObjects.WatcherPlaceholder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
    private List<Watcher> listOfWatchers = new ArrayList<>();
    private AtomicLong nextWatcherID = new AtomicLong();

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

//    @GetMapping("/api/stats/students-per-semester?")


    @PostMapping("/api/addoffering")
    public void addOffering(@RequestBody OfferingsPlaceholder placeholder) {
        boolean foundDepartment = false;

        System.out.println(placeholder.toString());

        for(Department department : theParser.getDepartments()) {
            if(department.getName().equals(placeholder.subjectName)) {
                foundDepartment = true;
                utilityHelpOfferingMethod(placeholder, department);
            }
        }

        if(!foundDepartment) {
            Department newDepartment = new Department(placeholder.subjectName);
            utilityHelpOfferingMethod(placeholder, newDepartment);
            theParser.getDepartments().add(newDepartment);

        }
    }

    private void utilityHelpOfferingMethod(@RequestBody OfferingsPlaceholder placeholder, Department department) {
        Course newCourse = new Course(placeholder.catalogNumber);
        newCourse.setCourseId(department.getCourseList().size());


        //Create an list of strings for fields to avoid creating a new constructor
        List<String> courseComponentFields = new ArrayList<>();
        courseComponentFields.add("" + placeholder.enrollmentCap);
        courseComponentFields.add("" + placeholder.enrollmentTotal);
        courseComponentFields.add(placeholder.component);

        Component courseComponent = new Component(courseComponentFields);

        //Create an list of strings for fields to avoid creating a new constructor
        List<String> offeringFields = new ArrayList<>();
        offeringFields.add(placeholder.location);
        offeringFields.add(placeholder.instructor);
        offeringFields.add("" + placeholder.semester);

        Offering newOffering = new Offering(offeringFields);

        for(Course course : department.getCourseList()) {
            if(course.getCatalogNumber().equals(placeholder.catalogNumber)) {
                course.notifyAddObservers(newOffering, courseComponent);
            }
        }

        department.addToCourseList(newCourse, newOffering, courseComponent);
    }


    //Returns the list of all the watchers that were created.
    @GetMapping("/api/watchers")
    public List<Watcher> getAllWatchers() {
        return listOfWatchers;
    }


    //Adds a watcher to the list, Notifies the course that there is an observer.
    @PostMapping("/api/watchers")
    public void addWatcher(@RequestBody WatcherPlaceholder placeholder) throws CourseNotFoundException, DepartmentNotFoundException {

        Watcher watcher = new Watcher(nextWatcherID.incrementAndGet(), placeholder.deptId, placeholder.courseId, theParser);

        boolean foundDepartment = false;
        boolean foundCourse = false;


        for(Department department : theParser.getDepartments()) {
            if(department.getDeptId() == placeholder.deptId) {
                foundDepartment = true;

                for(Course course: department.getCourseList()) {
                    if(course.getCourseId() == placeholder.courseId) {
                        foundCourse = true;
                        course.add(watcher);
                        listOfWatchers.add(watcher);
                    }
                }
            }
        }

        if(!foundDepartment) {
            throw new DepartmentNotFoundException();
        }

        if(!foundCourse) {
            throw new CourseNotFoundException();
        }
    }

    @GetMapping("/api/watchers/{id}")
    public Watcher getListOfEventsFromWatcher(@PathVariable("id") long watcherID) throws WatcherNotFoundException {
        for(Watcher watcher : listOfWatchers) {
            if(watcher.getWatcherID() == watcherID) {
                return watcher;
            }
        }

        throw new WatcherNotFoundException();
    }

    @DeleteMapping("/api/watchers/{id}")
    public void deleteWatcher(@PathVariable("id") long watcherID) throws WatcherNotFoundException {

        boolean foundWatcher = false;

        for(Watcher watcher : listOfWatchers) {
            if(watcher.getWatcherID() == watcherID) {
                foundWatcher = true;
                //1. We have to remove the observer from the course list.
                for(Department department : theParser.getDepartments()) {
                    //It's impossible to get a course not found or a department not found because the watcher
                    //Must be in the list somewhere.
                    if(department == watcher.getDepartment()) {
                        for(Course course: department.getCourseList()) {
                            if(course.getCourseId() == watcher.getCourse().getCourseId()) {
                                course.delete(watcher);
                                listOfWatchers.remove(watcher);
                            }
                        }
                    }
                }

            }
        }

        if(!foundWatcher) {
            throw new WatcherNotFoundException();
        }
    }



    //Handle Exception for when the watcher cannot be found
    @ResponseStatus(value = HttpStatus.BAD_REQUEST,
            reason = "Watcher cannot be found.")
    @ExceptionHandler(WatcherNotFoundException.class)
    public void watcherNotFoundExceptionHandler() {

    }


    //Handle Exception for when the department cannot be found
    @ResponseStatus(value = HttpStatus.BAD_REQUEST,
            reason = "Department cannot be found.")
    @ExceptionHandler(DepartmentNotFoundException.class)
    public void departmentNotFoundExceptionHandler() {

    }

    //Handle Exception for when the course cannot be found
    @ResponseStatus(value = HttpStatus.BAD_REQUEST,
            reason = "Course cannot be found.")
    @ExceptionHandler(CourseNotFoundException.class)
    public void courseNotFoundExceptionHandler() {

    }


}
