package ru.netology.ddima.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "operations.processing")
public class OperationProcessingProperties {
    public int sleepMilliSeconds;
}
