package com.scene.adapters.outbound.blobstorage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "azure.blob-storage")
public class AzureBlobConfiguration {

    @NotNull
    public String accountName;

    @NotNull
    public String accountKey;

    @NotNull
    public String containerName;

    @NotNull
    public String connectionFormat;

    @NotNull
    public String basePath;

    public String getFilePath(String fileName) {
        return String.format("%s/%s/%s", basePath, containerName, fileName);
    }

}
