package com.scene.domain.file;

public interface IStorageClient {

    void uploadFile(byte[] data, StaticFileInfo fileInfo);

    String getFilePath(String fileName);
}