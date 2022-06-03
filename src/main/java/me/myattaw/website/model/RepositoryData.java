package me.myattaw.website.model;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : Michael
 * @since : 5/31/2022, Tuesday
 **/
@Data
public class RepositoryData {

    private String name; // the name of the repository
    private String description; // the description of the repository
    private String language; // the main programming language used in the repository
    private String htmlUrl; // the url to the repository
    private Date lastUpdatedAt; // last time the repo was updated

    private RepositoryData(String name, String description, String language, String htmlUrl, Date lastUpdatedAt) {
        this.name = name;
        this.description = description.equals("null") ? "Project has no description :(" : description;
        this.language = language.equals("null") ? "None" : language;
        this.htmlUrl = htmlUrl;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public static RepositoryData create(String name, String description, String language, String htmlUrl, Date lastUpdatedAt) {
        return new RepositoryData(name, description, language, htmlUrl, lastUpdatedAt);
    }

    public String getSimpleDate() {
        return new SimpleDateFormat("MM/dd/yyyy").format(lastUpdatedAt);
    }

}
