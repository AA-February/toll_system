package com.example.spring_into.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dates")
@Getter
@Setter
public class DurationConfig {
    private int weekend;
    private int week;
    private int month;
    private int year;
}
