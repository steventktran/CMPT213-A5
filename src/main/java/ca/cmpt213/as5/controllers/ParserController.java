package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.model.CSVParser;
import ca.cmpt213.as5.model.CourseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ParserController {
    private static final String FILE_PATH = "data/course_data_2018.csv";
    private CSVParser theParser;

    //Get Mapping
    @GetMapping("/dump-model")
    public List<CourseData> getDumpModel() {
        List<CourseData> data = null;
        return data;
    }


}
