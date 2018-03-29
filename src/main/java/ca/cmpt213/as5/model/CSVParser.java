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

    public void parseFile() {
        try {
            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {
                String[] fields = read.nextLine().split(",");
                courseList.add(new CourseData(fields));
            }
        } catch (FileNotFoundException e){

        }
    }
}
