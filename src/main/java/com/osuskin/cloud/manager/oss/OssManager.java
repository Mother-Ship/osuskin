package com.osuskin.cloud.manager.oss;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import lombok.Data;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@ConfigurationProperties(prefix = "oss")
@Data
public class OssManager {
    private String accessKey;
    private String accessKeySecret;
    private String endPoint;
    private String mappingBucket;
    private String skinBucket;
    private ClientConfiguration CONFIG = new ClientConfiguration();

    {
        //设置超时、错误重试
        CONFIG.setConnectionTimeout(60 * 1000);
        CONFIG.setConnectionTTL(600 * 1000);
        CONFIG.setMaxErrorRetry(2);
        CONFIG.setSocketTimeout(15 * 1000);
    }

    private OSSClient CLIENT ;

    @PostConstruct
    public void setClient() {
        CLIENT = new OSSClient(endPoint, accessKey, accessKeySecret, CONFIG);
    }



    public boolean isSkinComponentExist(String filename) {
        return CLIENT.doesObjectExist(skinBucket, filename);
    }

    public InputStream readSkinComponent(String filename) {
        try (OSSObject ossObject = CLIENT.getObject(skinBucket, filename);
             InputStream rawData = ossObject.getObjectContent()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(rawData, baos);
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (IOException ignore) {
            return null;
        }
    }





}
