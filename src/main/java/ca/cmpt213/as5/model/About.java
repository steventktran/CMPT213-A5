package ca.cmpt213.as5.model;

public class About {
    private String name;
    private String description;

    public About() {
        this.name = "Andy Wu, Steven Tran";
        this.description = "Welcome to the Course Manager";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
