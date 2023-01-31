package pojo.GetCoursesDeserialization;

import pojo.GetCoursesDeserialization.Courses;

public class GetCourse {

    private String url;
    private String services;
    private String expertise;
    private Courses courses;// courses is a nested json in the payload, so we create a pojo class Coursed for the nested/sub json
    private String instructor;
    private String linkedin;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

}
