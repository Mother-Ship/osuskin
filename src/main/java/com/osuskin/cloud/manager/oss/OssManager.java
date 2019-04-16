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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    private OSSClient CLIENT;

    {
        //设置超时、错误重试
        CONFIG.setConnectionTimeout(60 * 1000);
        CONFIG.setConnectionTTL(600 * 1000);
        CONFIG.setMaxErrorRetry(2);
        CONFIG.setSocketTimeout(15 * 1000);
    }

    @PostConstruct
    public void setClient() {
        CLIENT = new OSSClient(endPoint, accessKey, accessKeySecret, CONFIG);
    }


    public boolean isSkinComponentExist(String md5Filename) {
        return CLIENT.doesObjectExist(skinBucket, md5Filename);
    }

    public InputStream getSkinComponent(String md5Filename) {
        try (OSSObject ossObject = CLIENT.getObject(skinBucket, md5Filename);
             InputStream rawData = ossObject.getObjectContent()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(rawData, baos);
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (IOException ignore) {
            return null;
        }
    }

    public String addSkinComponent(byte[] data) {
        String md5 = md5Sign(data);
        CLIENT.putObject(skinBucket, md5, new ByteArrayInputStream(data));
        return md5;
    }

    private String md5Sign(byte[] data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(data, 0, data.length);
            byte[] hash = md.digest();
            StringBuilder outStrBuf = new StringBuilder(32);
            for (byte b : hash) {
                int v = b & 0xFF;
                if (v < 16) {
                    outStrBuf.append('0');
                }
                outStrBuf.append(Integer.toString(v, 16).toLowerCase());
            }
            return outStrBuf.toString();
        } catch (NoSuchAlgorithmException ignore) {
            return null;
        }
    }

}
