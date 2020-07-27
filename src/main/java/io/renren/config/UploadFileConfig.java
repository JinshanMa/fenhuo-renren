package io.renren.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


public class UploadFileConfig {
    private String localuploadpath;

    public  String getLocaluploadpath() {
        return localuploadpath;
    }

    public void setLocaluploadpath(String localuploadpath) {
        this.localuploadpath = localuploadpath;
    }
}
