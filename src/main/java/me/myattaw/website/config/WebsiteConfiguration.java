package me.myattaw.website.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author : Michael
 * @since : 6/3/2022, Friday
 **/

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Data
public class WebsiteConfiguration {

    private String fullName;
    private List<String> description;
    private String githubUsername;
    private String linkedInUsername;

}
