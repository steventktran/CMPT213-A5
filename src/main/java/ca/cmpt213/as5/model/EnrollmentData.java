package ca.cmpt213.as5.model;

/**
 * It stores the data necessary to create a graph.
 */

public class EnrollmentData implements Comparable<EnrollmentData>{
    private int semesterCode;
    private int totalCoursesTaken;

    @Override
    public int compareTo( EnrollmentData other) {
        return semesterCode - other.semesterCode;
    }

    public EnrollmentData(int semesterCode, Department department) {
        this.semesterCode = semesterCode;

        for(Course course: department.getCourseList()) {
            for(Offering offering: course.getOfferingList()) {
                if(offering.getSemesterCode() == semesterCode) {
                    totalCoursesTaken += getEnrolledToLec(offering);
                }
            }
        }
    }

    public int getEnrolledToLec(Offering offering) {
        int enrollment = 0;
        for(Component component: offering.getComponentList()) {
            if(component.getComponent().equals("LEC")) {
                enrollment += component.getEnrollmentTotal();
            }
        }
        return enrollment;
    }

    public int getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(int semesterCode) {
        this.semesterCode = semesterCode;
    }

    public int getTotalCoursesTaken() {
        return totalCoursesTaken;
    }

    public void setTotalCoursesTaken(int totalCoursesTaken) {
        this.totalCoursesTaken = totalCoursesTaken;
    }
}
