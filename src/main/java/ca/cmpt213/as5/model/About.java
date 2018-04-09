package ca.cmpt213.as5.model;

public class About {
    private String appName;
    private String authorName;

    public About() {
        this.appName = "Our Course Manager";
        this.authorName = "Andy Wu, Steven Tran";
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
