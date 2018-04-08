package ca.cmpt213.as5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

//        String fileDirectory = "data/course_data_2018.csv";
//        File file = new File(fileDirectory);
//        CSVParser test = new CSVParser(file);
//        try {
//            test.parseFile();
//            test.printCourseList();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
