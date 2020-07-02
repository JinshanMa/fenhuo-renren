package io.renren.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "uploadfileconfig")
public class UploadFileConfig {
    private static String localuploadpath;

    public static String getLocaluploadpath() {
        return localuploadpath;
    }

    public static void setLocaluploadpath(String localuploadpath) {
        UploadFileConfig.localuploadpath = localuploadpath;
    }
}
