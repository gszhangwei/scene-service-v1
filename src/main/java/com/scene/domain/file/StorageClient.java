package com.scene.domain.file;

import org.springframework.stereotype.Component;

@Component
public interface StorageClient {

    void uploadFile(byte[] data, StaticFileInfo fileInfo);

    String getFilePath(String fileName);
}
