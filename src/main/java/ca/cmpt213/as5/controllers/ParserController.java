package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.model.CSVParser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * The controller class that utilizes the parse object and outputs it to the
 * server terminal
 */

@RestController
public class ParserController {
    private static final String FILE_PATH = "data/course_data_2018.csv";


    //Get Mapping
    @GetMapping("/api/dump-model")
    public String getDumpModel() throws FileNotFoundException{
        File filePath = new File(FILE_PATH);
        CSVParser theParser = new CSVParser(filePath);
        theParser.parseFile();
        System.out.println("Parsing Success!");
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
    public String getAboutMessage() {
        return "Suberb Course game written by Andy Wu!";
    }


}
