package me.myattaw.website;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.myattaw.website.config.WebsiteConfiguration;
import me.myattaw.website.controllers.HomeController;
import me.myattaw.website.model.RepositoryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author : Michael
 * @since : 5/31/2022, Tuesday
 **/

@Service
public class GithubDataService {

    @Autowired
    private WebsiteConfiguration configuration; // main application configuration

    private static final int MAX_REPOSITORIES_PER_PAGE = 3; // the max amount of repository to display per page

    private List<List<RepositoryData>> repositoryDataList = new ArrayList<>();

    /**
     * List of repositories grouped together by the size of MAX_REPOSITORIES_PER_PAGE
     * @return a list of repository data formatted in smaller groups
     */
    public List<List<RepositoryData>> getRepositoryDataList() {
        return repositoryDataList;
    }

    /**
     * Parses repository data from the configurable username
     * @throws IOException
     */
    @PostConstruct
    public void parseRepositoryData() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();

        String githubAPI = String.format("https://api.github.com/users/%s/repos",configuration.getGithubUsername());
        ResponseEntity<String> response = restTemplate.getForEntity(URI.create(githubAPI), String.class);

        int count = 0;
        List<RepositoryData> repositoryData = new ArrayList<>();
        Iterator jsonNodeIterator = mapper.readTree(response.getBody()).iterator();

        while (jsonNodeIterator.hasNext()) {
            JsonNode currNode = (JsonNode) jsonNodeIterator.next();
            if (!currNode.get("fork").asBoolean()) {
                String name = currNode.get("name").asText();
                String desc = currNode.get("description").asText();
                String lang = currNode.get("language").asText();
                String htmlUrl = currNode.get("html_url").asText();
                Date date = mapper.convertValue(currNode.get("updated_at"), Date.class);
                repositoryData.add(RepositoryData.create(name, desc, lang, htmlUrl, date));
                if (++count == MAX_REPOSITORIES_PER_PAGE || !jsonNodeIterator.hasNext()) {
                    count = 0;
                    repositoryDataList.add(repositoryData);
                    repositoryData = new ArrayList<>();
                }
            }
        }
    }

    /**
     * The main application configuration
     * @return instance of the configuration data
     */
    public WebsiteConfiguration getConfiguration() {
        return configuration;
    }
}
