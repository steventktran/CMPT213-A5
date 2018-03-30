package ca.cmpt213.as5.model;

import static java.lang.Integer.parseInt;

public class Component {
    private String component;
    private int enrollmentCap;
    private int enrollmentTotal;

    public Component(String[] fields) {
        component = fields[0].trim();
        enrollmentCap = parseInt(fields[1]);
        enrollmentTotal = parseInt(fields[2]);
    }

}
