package me.myattaw.website.controllers;

import me.myattaw.website.GithubDataService;
import me.myattaw.website.config.WebsiteConfiguration;
import me.myattaw.website.model.RepositoryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author : Michael
 * @since : 5/31/2022, Tuesday
 **/
@Controller
public class HomeController {

    @Autowired
    private GithubDataService githubDataService;

    /**
     * The main page for the website
     * @param model data storage used for the html
     * @return
     */
    @GetMapping()
    public String home(Model model) {
        List<List<RepositoryData>> repositoryData = githubDataService.getRepositoryDataList();
        WebsiteConfiguration config = githubDataService.getConfiguration();
        model.addAttribute("repositories", repositoryData);
        model.addAttribute("fullName", config.getFullName());
        model.addAttribute("description", String.join(" ", config.getDescription()));
        model.addAttribute("githubUrl", String.format("https://github.com/%s/", config.getGithubUsername()));
        model.addAttribute("linkedinUrl", String.format("https://www.linkedin.com/in/%s/", config.getLinkedInUsername()));
        return "home";
    }

}