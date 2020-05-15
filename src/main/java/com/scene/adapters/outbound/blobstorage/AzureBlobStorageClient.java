package com.scene.adapters.outbound.blobstorage;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.scene.domain.file.StorageClient;
import com.scene.domain.file.StaticFileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

@Slf4j
@Component
public class AzureBlobStorageClient implements StorageClient {

    private static final int ZERO_UPDATE_DATA_OFFSET = 0;

    private CloudBlobClient cloudBlobClient = initialBlobClient();

    private final AzureBlobConfiguration azureBlobConfiguration;

    public AzureBlobStorageClient(AzureBlobConfiguration azureBlobConfiguration) {
        this.azureBlobConfiguration = azureBlobConfiguration;
    }

    private CloudBlobClient initialBlobClient() {
        assert azureBlobConfiguration != null;
        String format = String.format(azureBlobConfiguration.connectionFormat, azureBlobConfiguration.accountName, azureBlobConfiguration.accountKey);
        try {
            return CloudStorageAccount.parse(format).createCloudBlobClient();
        } catch (URISyntaxException | InvalidKeyException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void uploadFile(byte[] data, StaticFileInfo fileInfo) {
        try {
            CloudBlockBlob blockBlobReference = cloudBlobClient
                    .getContainerReference(azureBlobConfiguration.containerName)
                    .getBlockBlobReference(fileInfo.getName());
            blockBlobReference.getProperties().setContentType(fileInfo.getMediaType());
            blockBlobReference.uploadFromByteArray(data, ZERO_UPDATE_DATA_OFFSET, data.length);
        } catch (URISyntaxException | StorageException | IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getFilePath(String fileName) {
        return azureBlobConfiguration.getFilePath(fileName);
    }
}
