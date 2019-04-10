package com.osuskin.cloud.manager.oss;

import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oss")
@ToString
public class OssManager {
    private String accessKey;
    private String accessKeySecret;
    private String endPoint;


}
