package io.renren.modules.fenhuo.config;

import io.renren.config.UploadFileConfig;
import io.renren.datasource.properties.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FenhuoConfig {
    @Bean
    @ConfigurationProperties(prefix = "uploadfileconfig")
    public UploadFileConfig uploadFileConfig() {
        return new UploadFileConfig();
    }
}
