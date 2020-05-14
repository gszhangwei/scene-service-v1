package com.scene.domain.file;

import org.springframework.stereotype.Component;

@Component
public interface IStorageClient {

    void uploadFile(byte[] data, StaticFileInfo fileInfo);

    String getFilePath(String fileName);
}
