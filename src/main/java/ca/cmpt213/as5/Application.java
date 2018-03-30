package ca.cmpt213.as5;

import ca.cmpt213.as5.model.CSVParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;

//@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        /*
        SpringApplication.run(Application.class, args);
        */
        String fileDirectory = "data/course_data_2018.csv";
        File file = new File(fileDirectory);
        CSVParser test = new CSVParser(file);
        try {
            test.parseFile();
            test.printCourseList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
